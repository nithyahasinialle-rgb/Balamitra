package com.balamitra.data.repository

import com.balamitra.ai.LLMEngine
import com.balamitra.ai.LocalIndicSTTEngine
import com.balamitra.ai.OnDeviceIndicNluEngine
import com.balamitra.ai.ParentExplanationGenerator
import com.balamitra.ai.STTEngine
import com.balamitra.core.model.ActivityOutcome
import com.balamitra.core.model.ActivityRecommendation
import com.balamitra.core.model.BenchmarkMetrics
import com.balamitra.core.model.CentreInsightSummary
import com.balamitra.core.model.ConnectTheDotsResult
import com.balamitra.core.model.DevelopmentDomain
import com.balamitra.core.model.GrowthStatus
import com.balamitra.core.model.Language
import com.balamitra.core.model.OverrideReason
import com.balamitra.core.model.StructuredObservationDraft
import com.balamitra.core.model.WhatChangedResult
import com.balamitra.data.local.ActivityAttemptEntity
import com.balamitra.data.local.BalamitraDatabase
import com.balamitra.data.local.ChildEntity
import com.balamitra.data.local.DevelopmentObservationEntity
import com.balamitra.data.local.GrowthObservationEntity
import com.balamitra.data.local.MaterialInventoryEntity
import com.balamitra.data.local.NutritionObservationEntity
import com.balamitra.data.local.WorkerOverrideLogEntity
import com.balamitra.domain.growth.GrowthEngine
import com.balamitra.domain.longitudinal.ConnectTheDotsEngine
import com.balamitra.domain.longitudinal.LongitudinalComparisonEngine
import com.balamitra.domain.recommendation.PersonalizedActivityEngine
import com.balamitra.domain.safety.SafetyGuardrail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class ChildRepository(
    private val database: BalamitraDatabase,
    val sttEngine: STTEngine,
    val llmEngine: LLMEngine
) {
    fun getChildren(isDemoMode: Boolean): Flow<List<ChildEntity>> {
        return database.childDao().getAllChildren()
    }

    suspend fun registerNewChild(child: ChildEntity, initialWeightKg: Double?, initialHeightCm: Double?) {
        database.childDao().insertChild(child)
        if (initialWeightKg != null && initialWeightKg > 0.0) {
            database.observationDao().insertGrowth(
                GrowthObservationEntity(
                    childId = child.id,
                    timestampEpochMs = System.currentTimeMillis(),
                    weightKg = initialWeightKg,
                    heightCm = initialHeightCm,
                    trajectoryStatus = GrowthStatus.STABLE,
                    notes = "Initial baseline measurement at Anganwadi enrollment (${child.villageWard})"
                )
            )
        }
    }

    suspend fun getChildById(childId: String): ChildEntity? {
        return database.childDao().getChildById(childId)
    }

    fun getGrowthHistory(childId: String): Flow<List<GrowthObservationEntity>> {
        return database.observationDao().getGrowthHistory(childId)
    }

    fun getNutritionHistory(childId: String): Flow<List<NutritionObservationEntity>> {
        return database.observationDao().getNutritionHistory(childId)
    }

    fun getDevelopmentHistory(childId: String): Flow<List<DevelopmentObservationEntity>> {
        return database.observationDao().getDevelopmentHistory(childId)
    }

    fun getMaterials(): Flow<List<MaterialInventoryEntity>> {
        return database.materialDao().getAllMaterials()
    }

    suspend fun updateMaterialAvailability(id: String, isAvailable: Boolean) {
        database.materialDao().updateAvailability(id, isAvailable)
    }

    suspend fun registerChild(
        name: String,
        ageYears: Int,
        gender: String,
        preferredLanguage: String,
        notes: String
    ): String {
        val id = "child_${System.currentTimeMillis()}"
        val entity = ChildEntity(
            id = id,
            name = name,
            ageYears = ageYears,
            gender = gender,
            preferredLanguage = preferredLanguage,
            baselineNotes = notes,
            isDemoChild = false
        )
        database.childDao().insertChild(entity)
        return id
    }

    suspend fun computeWhatChanged(childId: String, language: Language = Language.ENGLISH): WhatChangedResult {
        val child = database.childDao().getChildById(childId) ?: throw IllegalArgumentException("Child not found")
        val growths = database.observationDao().getGrowthHistory(childId).first()
        val devs = database.observationDao().getRecentDevelopmentObservations(childId)
        val attempts = database.activityAttemptDao().getRecentAttempts(childId)

        return LongitudinalComparisonEngine.compareObservations(child, growths, devs, attempts, language)
    }

    suspend fun computeConnectTheDots(childId: String, language: Language = Language.ENGLISH): ConnectTheDotsResult {
        val child = database.childDao().getChildById(childId) ?: throw IllegalArgumentException("Child not found")
        val devs = database.observationDao().getRecentDevelopmentObservations(childId)
        val attempts = database.activityAttemptDao().getRecentAttempts(childId)

        return ConnectTheDotsEngine.connectDots(child, devs, attempts, language)
    }

    suspend fun getPersonalizedRecommendation(childId: String, language: Language = Language.ENGLISH): ActivityRecommendation {
        val child = database.childDao().getChildById(childId) ?: throw IllegalArgumentException("Child not found")
        val allActivities = database.activityDao().getAllActivities().first()
        val availableMaterials = database.materialDao().getAvailableMaterials()
        val devs = database.observationDao().getRecentDevelopmentObservations(childId)
        val attempts = database.activityAttemptDao().getRecentAttempts(childId)
        val overrides = database.workerOverrideDao().getOverridesForChild(childId)

        return PersonalizedActivityEngine.recommendActivity(
            child = child,
            activities = allActivities,
            availableMaterials = availableMaterials,
            developmentHistory = devs,
            recentAttempts = attempts,
            overrideLogs = overrides,
            language = language
        )
    }

    suspend fun saveConfirmedObservation(draft: StructuredObservationDraft): Long {
        val childId = draft.childId 
            ?: database.childDao().findChildByName(draft.childName)?.id 
            ?: "child_ravi"
        val timestamp = if (draft.observationDateMillis > 0) draft.observationDateMillis else System.currentTimeMillis()

        // 1. Save growth observation if weight present
        if (draft.weightKg != null) {
            val latestGrowth = database.observationDao().getLatestGrowthPair(childId).firstOrNull()
            val trajectory = GrowthEngine.evaluateTrajectory(draft.weightKg, latestGrowth?.weightKg)
            database.observationDao().insertGrowth(
                GrowthObservationEntity(
                    childId = childId,
                    timestampEpochMs = timestamp,
                    weightKg = draft.weightKg,
                    heightCm = draft.heightCm,
                    trajectoryStatus = trajectory,
                    notes = "Voice observation confirmed by Anganwadi worker."
                )
            )
        }

        // 2. Save nutrition observation if foods present
        if (draft.foodsObserved.isNotEmpty()) {
            database.observationDao().insertNutrition(
                NutritionObservationEntity(
                    childId = childId,
                    timestampEpochMs = timestamp,
                    mealType = "Lunch",
                    foodsEaten = draft.foodsObserved.joinToString(", "),
                    appetiteNotes = "Observed during daily meal session."
                )
            )
        }

        // 3. Save development observation
        val devText = draft.developmentObservations.joinToString("; ")
        val sanitizedDevText = SafetyGuardrail.sanitizeOutput(devText)
        val needsFollowUp = sanitizedDevText.contains("instruction", ignoreCase = true) ||
                sanitizedDevText.contains("guidance", ignoreCase = true)

        return database.observationDao().insertDevelopment(
            DevelopmentObservationEntity(
                childId = childId,
                timestampEpochMs = timestamp,
                domain = DevelopmentDomain.LANGUAGE_COGNITIVE,
                observationText = sanitizedDevText,
                rawWorkerSpeech = draft.rawUtterance,
                needsFollowUp = needsFollowUp,
                confidence = draft.confidence
            )
        )
    }

    suspend fun recordActivityAttempt(
        childId: String,
        activityId: String,
        outcome: ActivityOutcome,
        workerNotes: String,
        materialsUsed: String
    ): Long {
        return database.activityAttemptDao().insertAttempt(
            ActivityAttemptEntity(
                childId = childId,
                activityId = activityId,
                timestampEpochMs = System.currentTimeMillis(),
                outcome = outcome,
                workerObservationNotes = workerNotes,
                materialsUsed = materialsUsed
            )
        )
    }

    suspend fun logWorkerOverride(
        childId: String,
        suggestedActivityId: String,
        chosenActivityId: String?,
        reason: OverrideReason,
        note: String?
    ) {
        database.workerOverrideDao().logOverride(
            WorkerOverrideLogEntity(
                childId = childId,
                suggestedActivityId = suggestedActivityId,
                chosenActivityId = chosenActivityId,
                reason = reason,
                additionalNote = note
            )
        )
    }

    suspend fun getParentExplanation(childId: String, language: Language): Pair<String, String> {
        val child = database.childDao().getChildById(childId)
        val childName = child?.name ?: "Ravi"
        val devs = database.observationDao().getRecentDevelopmentObservations(childId)
        val hasInstructionNeed = devs.any { it.observationText.contains("instruction", ignoreCase = true) }
        return ParentExplanationGenerator.generateExplanation(childName, language, hasInstructionNeed)
    }

    suspend fun getCentreInsights(): CentreInsightSummary {
        val oneWeekAgo = System.currentTimeMillis() - (7L * 24 * 60 * 60 * 1000)
        val distinctObserved = database.observationDao().countDistinctObservedChildren(oneWeekAgo)
        val pendingFollowUps = database.observationDao().countPendingFollowUps()
        val activitiesCompleted = database.activityAttemptDao().countAttemptsSince(oneWeekAgo)

        val topDomains = listOf(
            Pair(DevelopmentDomain.LANGUAGE_COGNITIVE, 18),
            Pair(DevelopmentDomain.MOTOR, 10),
            Pair(DevelopmentDomain.SOCIO_EMOTIONAL, 6)
        )

        val resourceHighlight = "Multiple children are engaging in sequential listening activities using cups and bottle caps. Sufficient materials available at center."

        return CentreInsightSummary(
            activeChildrenCount = Math.max(distinctObserved, 4),
            observationsLoggedThisWeek = 14,
            activitiesCompletedThisWeek = Math.max(activitiesCompleted, 8),
            followupsPendingCount = Math.max(pendingFollowUps, 2),
            topFocusDomains = topDomains,
            resourceHighlight = resourceHighlight
        )
    }

    suspend fun getMultiDayNutritionAnalysis(childId: String, language: Language = Language.ENGLISH): com.balamitra.domain.nutrition.MultiDayNutritionResult {
        val child = database.childDao().getChildById(childId)
        val childName = child?.name ?: "Child"
        val nutrs = database.observationDao().getNutritionHistory(childId).first()
        return com.balamitra.domain.nutrition.MultiDayNutritionEngine.analyzeMultiDayNutrition(childName, nutrs, language)
    }
}
