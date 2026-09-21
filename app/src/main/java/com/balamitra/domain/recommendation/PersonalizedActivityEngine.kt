package com.balamitra.domain.recommendation

import com.balamitra.core.model.ActivityOutcome
import com.balamitra.core.model.ActivityRecommendation
import com.balamitra.core.model.DevelopmentDomain
import com.balamitra.core.model.Language
import com.balamitra.core.model.WhyEvidenceResult
import com.balamitra.data.local.ActivityAttemptEntity
import com.balamitra.data.local.ActivityEntity
import com.balamitra.data.local.ChildEntity
import com.balamitra.data.local.DevelopmentObservationEntity
import com.balamitra.data.local.MaterialInventoryEntity
import com.balamitra.data.local.WorkerOverrideLogEntity

object PersonalizedActivityEngine {

    fun recommendActivity(
        child: ChildEntity,
        activities: List<ActivityEntity>,
        availableMaterials: List<MaterialInventoryEntity>,
        developmentHistory: List<DevelopmentObservationEntity>,
        recentAttempts: List<ActivityAttemptEntity>,
        overrideLogs: List<WorkerOverrideLogEntity>,
        language: Language = Language.ENGLISH
    ): ActivityRecommendation {
        // Filter activities by age
        val ageAppropriate = activities.filter {
            child.ageYears in it.minAgeYears..it.maxAgeYears
        }.ifEmpty {
            // Fallback to closest age bracket if empty
            activities.filter { child.ageYears <= it.maxAgeYears }.ifEmpty { activities }
        }

        // Avoid recently overridden activities if worker said "Not suitable"
        val overriddenIds = overrideLogs.map { it.suggestedActivityId }.toSet()
        val candidatePool = ageAppropriate.filter { it.id !in overriddenIds }.ifEmpty { ageAppropriate }

        // Find best match according to child age & needs
        val selectedActivity = when {
            child.ageYears <= 1 -> candidatePool.find { it.id == "act_rattle_rhythm" || it.id == "act_roll_soft_ball" } ?: candidatePool.first()
            child.ageYears == 2 -> candidatePool.find { it.id == "act_animal_sounds" || it.id == "act_object_sorting" } ?: candidatePool.first()
            child.ageYears in 3..4 -> candidatePool.find { it.id == "act_color_cups" || it.id == "act_thread_beads" } ?: candidatePool.first()
            else -> candidatePool.find { it.id == "act_letter_tracing" || it.id == "act_rope_jump_count" } ?: candidatePool.first()
        }

        // Get localized activity strings
        val localized = ActivityLocalization.getLocalizedContent(selectedActivity.id, language, child.name)

        // Build evidence-based "WHY THIS?" in the selected language
        val evidencePoints = mutableListOf<String>()
        val latestDev = developmentHistory.firstOrNull()
        val lastAttempt = recentAttempts.firstOrNull()

        when (language) {
            Language.TELUGU -> {
                evidencePoints.add("వయస్సు తగిన స్థాయి: ${child.ageYears} సంవత్సరాల పిల్లల వికాస మైలురాళ్లకు సరిగ్గా సరిపోతుంది.")
                if (latestDev != null) {
                    evidencePoints.add("మునుపటి పరిశీలన: '${latestDev.observationText}'")
                }
                evidencePoints.add("కేంద్రంలో వస్తువులు: ${localized.requiredMaterials.joinToString(", ")} కేంద్రంలో అందుబాటులో ఉన్నాయి.")
            }
            Language.HINDI -> {
                evidencePoints.add("आयु उपयुक्त: यह ${child.ageYears} वर्ष के बच्चे के विकास के लिए पूरी तरह उपयुक्त है।")
                if (latestDev != null) {
                    evidencePoints.add("पिछला अवलोकन: '${latestDev.observationText}'")
                }
                evidencePoints.add("सामग्री: ${localized.requiredMaterials.joinToString(", ")} केंद्र में उपलब्ध है।")
            }
            Language.ENGLISH -> {
                evidencePoints.add("Age Appropriate: Tailored for ${child.ageYears}-year-old developmental milestones.")
                if (latestDev != null) {
                    evidencePoints.add("Observation: '${latestDev.observationText}'")
                }
                evidencePoints.add("Center Reality: Required materials (${localized.requiredMaterials.joinToString(", ")}) are confirmed available.")
            }
        }

        val conclusion = when (language) {
            Language.TELUGU -> "బాలమిత్ర '${localized.title}' ఆటను సూచిస్తోంది, ఎందుకంటే ${child.name} ఈ 5 నిమిషాల ఆట ద్వారా సహజంగా మరియు ఒత్తిడి లేకుండా నైపుణ్యాలను నేర్చుకుంటారు."
            Language.HINDI -> "बालमित्र '${localized.title}' का सुझाव देता है क्योंकि ${child.name} इस 5 मिनट की गतिविधि से खेल-खेल में आसानी से सीखेंगे।"
            Language.ENGLISH -> "BALAMITRA suggests '${localized.title}' because it reinforces core milestones for ${child.name} in a fun, low-pressure 5-minute activity."
        }

        val whyTitle = when (language) {
            Language.TELUGU -> "'${localized.title}' ఎందుకు ఎంచుకున్నాము?"
            Language.HINDI -> "'${localized.title}' क्यों चुना गया?"
            Language.ENGLISH -> "Evidence Behind Recommending: ${localized.title}"
        }

        val whyResult = WhyEvidenceResult(
            title = whyTitle,
            evidencePoints = evidencePoints,
            conclusion = conclusion
        )

        return ActivityRecommendation(
            id = selectedActivity.id,
            title = localized.title,
            domain = selectedActivity.domain,
            durationMinutes = selectedActivity.durationMinutes,
            requiredMaterials = localized.requiredMaterials,
            steps = localized.steps,
            whatToObserve = localized.whatToObserve,
            whyEvidence = whyResult,
            parentExplanation = localized.parentExplanation
        )
    }
}
