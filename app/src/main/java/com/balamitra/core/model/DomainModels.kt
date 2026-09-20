package com.balamitra.core.model

import kotlinx.serialization.Serializable

enum class Language(val code: String, val displayName: String, val nativeName: String) {
    ENGLISH("en", "English", "English"),
    HINDI("hi", "Hindi", "हिंदी"),
    TELUGU("te", "Telugu", "తెలుగు")
}

enum class GrowthStatus {
    STABLE,
    INCREASING,
    DECREASING,
    NEEDS_REVIEW
}

enum class DevelopmentDomain(val label: String) {
    MOTOR("Motor Skills"),
    LANGUAGE_COGNITIVE("Language & Cognitive"),
    SOCIO_EMOTIONAL("Social & Emotional"),
    SELF_CARE("Daily Self-Care")
}

enum class ActivityOutcome(val label: String, val emoji: String) {
    INDEPENDENT("Did Independently", "😊"),
    NEEDED_HELP("Needed Help", "😐"),
    COULD_NOT_DO("Could Not Do", "😟")
}

enum class OverrideReason(val label: String) {
    NOT_SUITABLE_TODAY("Not suitable today"),
    CHILD_TIRED("Child is tired"),
    ALREADY_DID_THIS("Child already did this"),
    DIFFERENT_PREFERRED("Different activity preferred"),
    OTHER("Other reason")
}

enum class MaterialLocation {
    ANGANWADI,
    HOME,
    BOTH
}

@Serializable
data class StructuredObservationDraft(
    val childId: String? = null,
    val childName: String,
    val ageYears: Int,
    val weightKg: Double? = null,
    val heightCm: Double? = null,
    val foodsObserved: List<String> = emptyList(),
    val developmentObservations: List<String> = emptyList(),
    val targetDomain: String = "LANGUAGE_COGNITIVE",
    val rawUtterance: String = "",
    val detectedLanguage: String = "en",
    val confidence: Double = 0.94,
    val observationDateMillis: Long = System.currentTimeMillis(),
    val observationDateFormatted: String = ""
)

data class WhatChangedResult(
    val childName: String,
    val growthDelta: String,
    val growthTrajectory: GrowthStatus,
    val participationNotes: List<String>,
    val areasNeedingSupport: List<String>,
    val suggestedNextStep: String
)

data class ConnectTheDotsResult(
    val title: String,
    val patternIdentified: String,
    val historicalEvidence: List<String>,
    val recommendation: String,
    val domain: DevelopmentDomain
)

data class WhyEvidenceResult(
    val title: String,
    val evidencePoints: List<String>,
    val conclusion: String
)

data class ActivityRecommendation(
    val id: String,
    val title: String,
    val domain: DevelopmentDomain,
    val durationMinutes: Int,
    val requiredMaterials: List<String>,
    val steps: List<String>,
    val whatToObserve: String,
    val whyEvidence: WhyEvidenceResult,
    val parentExplanation: String
)

data class CentreInsightSummary(
    val activeChildrenCount: Int,
    val observationsLoggedThisWeek: Int,
    val activitiesCompletedThisWeek: Int,
    val followupsPendingCount: Int,
    val topFocusDomains: List<Pair<DevelopmentDomain, Int>>,
    val resourceHighlight: String
)

data class BenchmarkMetrics(
    val sttLatencyMs: Long = 0,
    val nluLatencyMs: Long = 0,
    val structuredJsonValid: Boolean = true,
    val memoryUsageMb: Double = 0.0,
    val isOfflineEngine: Boolean = true
)
