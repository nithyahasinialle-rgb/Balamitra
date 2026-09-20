package com.balamitra.domain

import com.balamitra.ai.OnDeviceIndicNluEngine
import com.balamitra.core.model.ActivityOutcome
import com.balamitra.core.model.DevelopmentDomain
import com.balamitra.core.model.GrowthStatus
import com.balamitra.core.model.Language
import com.balamitra.core.model.OverrideReason
import com.balamitra.data.local.ActivityAttemptEntity
import com.balamitra.data.local.ActivityEntity
import com.balamitra.data.local.ChildEntity
import com.balamitra.data.local.DevelopmentObservationEntity
import com.balamitra.data.local.GrowthObservationEntity
import com.balamitra.data.local.MaterialInventoryEntity
import com.balamitra.data.local.WorkerOverrideLogEntity
import com.balamitra.domain.growth.GrowthEngine
import com.balamitra.domain.longitudinal.ConnectTheDotsEngine
import com.balamitra.domain.longitudinal.LongitudinalComparisonEngine
import com.balamitra.domain.recommendation.PersonalizedActivityEngine
import com.balamitra.domain.safety.SafetyGuardrail
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class DomainEnginesTest {

    private val testChild = ChildEntity(
        id = "test_ravi",
        name = "Ravi",
        ageYears = 4,
        gender = "M",
        preferredLanguage = "te",
        baselineNotes = "Learning sequential instruction games."
    )

    @Test
    fun testGrowthEngineNeutralTrajectory() {
        val trajectoryIncreasing = GrowthEngine.evaluateTrajectory(12.8, 12.6)
        assertEquals(GrowthStatus.INCREASING, trajectoryIncreasing)

        val trajectoryStable = GrowthEngine.evaluateTrajectory(12.8, 12.8)
        assertEquals(GrowthStatus.STABLE, trajectoryStable)

        val trajectoryDecreasing = GrowthEngine.evaluateTrajectory(12.4, 12.8)
        assertEquals(GrowthStatus.DECREASING, trajectoryDecreasing)

        // Verify that growth summary does not contain medical diagnostic words
        val summary = GrowthEngine.getTrajectorySummary(12.8, 12.6)
        assertFalse(summary.contains("malnourished", ignoreCase = true))
        assertFalse(summary.contains("diagnosis", ignoreCase = true))
    }

    @Test
    fun testSafetyGuardrailConvertsDiagnosisToReferral() {
        val dangerousAiText = "Ravi has developmental delay and is severely malnourished."
        val sanitized = SafetyGuardrail.sanitizeOutput(dangerousAiText)

        assertFalse(sanitized.contains("developmental delay", ignoreCase = true))
        assertFalse(sanitized.contains("malnourished", ignoreCase = true))
        assertTrue(sanitized.contains("patterns worth observing", ignoreCase = true))
    }

    @Test
    fun testLongitudinalComparisonEngine() {
        val now = System.currentTimeMillis()
        val growths = listOf(
            GrowthObservationEntity(1, "test_ravi", now, 12.8, 94.2, GrowthStatus.INCREASING, "Recent"),
            GrowthObservationEntity(2, "test_ravi", now - 86400000, 12.6, 94.0, GrowthStatus.STABLE, "Prior")
        )
        val devs = listOf(
            DevelopmentObservationEntity(1, "test_ravi", now, DevelopmentDomain.LANGUAGE_COGNITIVE, "Recognized colors", null, false),
            DevelopmentObservationEntity(2, "test_ravi", now - 86400000, DevelopmentDomain.LANGUAGE_COGNITIVE, "Needed help with two-step instruction", null, true)
        )
        val attempts = listOf(
            ActivityAttemptEntity(1, "test_ravi", "act_color_cups", now, ActivityOutcome.INDEPENDENT, "Completed independently", "Cups, Bottle caps")
        )

        val result = LongitudinalComparisonEngine.compareObservations(testChild, growths, devs, attempts)
        assertEquals("Ravi", result.childName)
        assertTrue(result.growthDelta.contains("+0.2 kg"))
        assertEquals(GrowthStatus.INCREASING, result.growthTrajectory)
        assertTrue(result.participationNotes.any { it.contains("Recognized colors") })
    }

    @Test
    fun testConnectTheDotsEngineSurfacesEvidence() {
        val now = System.currentTimeMillis()
        val devs = listOf(
            DevelopmentObservationEntity(1, "test_ravi", now - 86400000, DevelopmentDomain.LANGUAGE_COGNITIVE, "Needed verbal help with two-step instruction", null, true),
            DevelopmentObservationEntity(2, "test_ravi", now - 172800000, DevelopmentDomain.LANGUAGE_COGNITIVE, "Hesitated when given two sequential instructions", null, true)
        )
        val attempts = listOf(
            ActivityAttemptEntity(1, "test_ravi", "act_color_cups", now - 86400000, ActivityOutcome.INDEPENDENT, "Loved cups and caps", "Cups, Bottle caps")
        )

        val patternResult = ConnectTheDotsEngine.connectDots(testChild, devs, attempts)
        assertTrue(patternResult.patternIdentified.contains("two-step instructions", ignoreCase = true))
        assertTrue(patternResult.patternIdentified.contains("physical objects", ignoreCase = true))
        assertTrue(patternResult.historicalEvidence.size >= 2)
    }

    @Test
    fun testPersonalizedActivityEngineRespectsMaterialsAndOverrides() {
        val activities = listOf(
            ActivityEntity("act_cups", "Colour Cup Challenge", DevelopmentDomain.LANGUAGE_COGNITIVE, 3, 5, 5, "Cups, Bottle caps", "Step 1|Step 2", "Observe", "En", "Hi", "Te"),
            ActivityEntity("act_leaves", "Leaf Sorting", DevelopmentDomain.LANGUAGE_COGNITIVE, 2, 4, 5, "Leaves, Paper", "Step 1|Step 2", "Observe", "En", "Hi", "Te")
        )
        val materials = listOf(
            MaterialInventoryEntity("mat_cups", "Cups", com.balamitra.core.model.MaterialLocation.BOTH, true),
            MaterialInventoryEntity("mat_caps", "Bottle caps", com.balamitra.core.model.MaterialLocation.BOTH, true)
        )
        val devs = listOf(
            DevelopmentObservationEntity(1, "test_ravi", System.currentTimeMillis(), DevelopmentDomain.LANGUAGE_COGNITIVE, "Needs two-step instruction practice", null, true)
        )
        val attempts = emptyList<ActivityAttemptEntity>()

        // Normal recommendation
        val rec = PersonalizedActivityEngine.recommendActivity(testChild, activities, materials, devs, attempts, emptyList())
        assertEquals("act_cups", rec.id)
        assertTrue(rec.whyEvidence.evidencePoints.isNotEmpty())

        // With worker override (worker indicated they want to change act_cups)
        val overrides = listOf(
            WorkerOverrideLogEntity(1, "test_ravi", "act_cups", null, OverrideReason.ALREADY_DID_THIS, "Done yesterday")
        )
        val adaptedRec = PersonalizedActivityEngine.recommendActivity(testChild, activities, materials, devs, attempts, overrides)
        assertEquals("act_leaves", adaptedRec.id)
    }

    @Test
    fun testOnDeviceIndicNluEngineMultilingualExtraction() = runBlocking {
        val nlu = OnDeviceIndicNluEngine()

        // Test Telugu
        val teUtterance = "రవికి నాలుగు సంవత్సరాలు. ఈరోజు అతని బరువు 12.8 కిలోలు. అన్నం, పప్పు తిన్నాడు. రంగులను బాగా గుర్తించాడు, కానీ రెండు సూచనలు వరుసగా పాటించడానికి కొంచెం సహాయం కావాలి."
        val (teDraft, teMetrics) = nlu.extractStructuredObservation(teUtterance, "te")
        assertEquals("Ravi", teDraft.childName)
        assertEquals(4, teDraft.ageYears)
        assertEquals(12.8, teDraft.weightKg!!, 0.01)
        assertTrue(teDraft.foodsObserved.contains("Rice") || teDraft.foodsObserved.contains("Dal"))
        assertTrue(teMetrics.structuredJsonValid)
        assertTrue(teMetrics.isOfflineEngine)

        // Test Hindi
        val hiUtterance = "रवि चार साल का है। आज उसका वजन 12.8 किलो है। उसने दाल और चावल खाया।"
        val (hiDraft, _) = nlu.extractStructuredObservation(hiUtterance, "hi")
        assertEquals("Ravi", hiDraft.childName)
        assertEquals(4, hiDraft.ageYears)
        assertEquals(12.8, hiDraft.weightKg!!, 0.01)

        // Test English
        val enUtterance = "Ravi is four years old. His weight today is 12.8 kilos. He ate rice and dal."
        val (enDraft, _) = nlu.extractStructuredObservation(enUtterance, "en")
        assertEquals("Ravi", enDraft.childName)
        assertEquals(12.8, enDraft.weightKg!!, 0.01)
    }

    @Test
    fun testAppStringsTrilingualCompleteness() {
        val te = com.balamitra.core.localization.getStrings(Language.TELUGU)
        val hi = com.balamitra.core.localization.getStrings(Language.HINDI)
        val en = com.balamitra.core.localization.getStrings(Language.ENGLISH)

        // Telugu strings must not be blank and contain Telugu script
        assertTrue(te.appName == "బాలమిత్ర")
        assertTrue(te.navToday == "ఈరోజు")
        assertTrue(te.btnWhatChanged == "ఏమి మారింది?")
        assertTrue(te.btnConnectDots == "వివరాలు జోడించండి")
        assertTrue(te.btnWhyThis == "ఇదే ఎందుకు?")
        assertTrue(te.loginTitle.contains("అంగన్‌వాడీ"))

        // Hindi strings must not be blank and contain Devanagari script
        assertTrue(hi.appName == "बालमित्र")
        assertTrue(hi.navToday == "आज का कार्य")
        assertTrue(hi.btnWhatChanged == "क्या बदला?")
        assertTrue(hi.btnConnectDots == "कड़ियों को जोड़ें")
        assertTrue(hi.btnWhyThis == "यही क्यों?")
        assertTrue(hi.loginTitle.contains("आंगनवाड़ी"))

        // English strings
        assertEquals("BALAMITRA", en.appName)
        assertEquals("Today", en.navToday)
    }

    @Test
    fun testWorkerSessionDefaults() {
        val worker = com.balamitra.data.model.DefaultWorkerSession.defaultWorker
        assertEquals("AWW-TG-HYD-108", worker.workerId)
        assertEquals("AWC-TG-HYD-BCH-042", worker.centerCode)
        assertEquals("Bachupally Anganwadi Kendram", worker.centerName)
        assertEquals("Medchal-Malkajgiri (Hyderabad)", worker.district)
        assertEquals(17.5385, worker.latitude, 0.001)
        assertEquals(78.3610, worker.longitude, 0.001)

        val status = com.balamitra.data.model.DefaultWorkerSession.defaultDailyStatus
        assertTrue(status.isOpenToday)
        assertTrue(status.hotCookedMealDistributed)
        assertEquals(18, status.morningAttendanceCount)
    }
}
