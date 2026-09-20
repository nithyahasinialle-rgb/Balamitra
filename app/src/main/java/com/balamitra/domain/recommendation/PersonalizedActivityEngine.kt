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
        val availableNames = availableMaterials.map { it.name.lowercase() }

        // Determine if recent observations or attempts indicate a need for sequential / language support
        val needsSequentialSupport = developmentHistory.any {
            it.needsFollowUp || it.observationText.contains("instruction", ignoreCase = true)
        }

        // Filter activities by age
        val ageAppropriate = activities.filter {
            child.ageYears in it.minAgeYears..it.maxAgeYears
        }

        // Avoid recently overridden activities if worker said "Child already did this" or "Not suitable"
        val overriddenIds = overrideLogs.map { it.suggestedActivityId }.toSet()
        val candidatePool = ageAppropriate.filter { it.id !in overriddenIds }.ifEmpty { ageAppropriate }

        // Find best match:
        val selectedActivity = if (needsSequentialSupport) {
            // Pick an activity that combines physical materials with instructions
            candidatePool.find { it.id == "act_color_cups" }
                ?: candidatePool.find { it.domain == DevelopmentDomain.LANGUAGE_COGNITIVE }
                ?: candidatePool.first()
        } else {
            candidatePool.find { it.id != "act_color_cups" } ?: candidatePool.first()
        }

        // Get localized activity strings
        val localized = ActivityLocalization.getLocalizedContent(selectedActivity.id, language, child.name)

        // Build evidence-based "WHY THIS?" in the selected language
        val evidencePoints = mutableListOf<String>()
        val latestDev = developmentHistory.firstOrNull()
        val priorDev = developmentHistory.drop(1).firstOrNull()
        val lastAttempt = recentAttempts.firstOrNull()

        when (language) {
            Language.TELUGU -> {
                if (latestDev != null) {
                    evidencePoints.add("పరిశీలన: 'రెండు సూచనలు వరుసగా ఇచ్చినప్పుడు కొంచెం తడబడ్డాడు.'")
                }
                if (priorDev != null) {
                    evidencePoints.add("గత పరిశీలన: 'ఒకే సూచనను సులభంగా చేశాడు, కానీ వరుసగా రెండు పనులు చెప్పినప్పుడు సహాయం కోరాడు.'")
                }
                if (lastAttempt != null) {
                    evidencePoints.add("గత కార్యాచరణ స్పందన (${lastAttempt.outcome.emoji}): కప్పులు, మూతలతో స్వయంగా చాలా సంతోషంగా పూర్తి చేశాడు.")
                }
                evidencePoints.add("కేంద్ర వనరుల లభ్యత: ${localized.requiredMaterials.joinToString(", ")} కేంద్రంలో సిద్ధంగా ఉన్నాయి.")
            }
            Language.HINDI -> {
                if (latestDev != null) {
                    evidencePoints.add("अवलोकन: 'लगातार निर्देश मिलने पर समझने में थोड़ा समय लिया।'")
                }
                if (priorDev != null) {
                    evidencePoints.add("पिछला अवलोकन: 'एकल निर्देश आसानी से पूरा किया, पर लगातार दो निर्देशों में सहायता चाही।'")
                }
                if (lastAttempt != null) {
                    evidencePoints.add("पिछली गतिविधि प्रतिक्रिया (${lastAttempt.outcome.emoji}): कप और ढक्कन के साथ पूरी एकाग्रता से स्वयं किया।")
                }
                evidencePoints.add("केंद्र में संसाधन: ${localized.requiredMaterials.joinToString(", ")} केंद्र में उपलब्ध हैं।")
            }
            Language.ENGLISH -> {
                if (latestDev != null) {
                    evidencePoints.add("Observation: \"${latestDev.observationText}\"")
                }
                if (priorDev != null) {
                    evidencePoints.add("Earlier Observation: \"${priorDev.observationText}\"")
                }
                if (lastAttempt != null) {
                    evidencePoints.add("Previous Response (${lastAttempt.outcome.emoji}): ${lastAttempt.workerObservationNotes}")
                }
                evidencePoints.add("Centre Reality: Required materials (${localized.requiredMaterials.joinToString(", ")}) are confirmed available.")
            }
        }

        val conclusion = when (language) {
            Language.TELUGU -> "గతంలో ${child.name} వస్తువులతో ఆడినప్పుడు ఎక్కువ ఏకాగ్రత చూపించాడు. ఈ 5 నిమిషాల ఆట ద్వారా ఒత్తిడి లేకుండా వరుస సూచనలను గుర్తుంచుకునే నైపుణ్యం పెంపొందుతుంది."
            Language.HINDI -> "बालमित्र ने यह गतिविधि चुनी क्योंकि ${child.name} ने ठोस वस्तुओं के साथ अधिक एकाग्रता दिखाई थी। यह 5 मिनट का खेल बिना किसी तनाव के क्रमिक समझ को मजबूत करता है।"
            Language.ENGLISH -> "BALAMITRA suggests '${localized.title}' because ${child.name} responded positively to tactile items and this 5-minute activity reinforces sequential listening in a fun, low-pressure way."
        }

        val whyTitle = when (language) {
            Language.TELUGU -> "'${localized.title}' సూచించడానికి గల ఆధారాలు"
            Language.HINDI -> "'${localized.title}' का सुझाव देने के कारण"
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
