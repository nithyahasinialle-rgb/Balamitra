package com.balamitra.domain.growth

import com.balamitra.core.model.GrowthStatus

/**
 * Deterministic Growth Engine based on longitudinal weight & height trajectory.
 * Adheres strictly to non-diagnostic, neutral observational language.
 * Growth metrics and thresholds are purely mathematical and deterministic.
 */
object GrowthEngine {

    /**
     * Compares latest weight with previous weight to compute neutral longitudinal trajectory.
     */
    fun evaluateTrajectory(currentWeightKg: Double, previousWeightKg: Double?): GrowthStatus {
        if (previousWeightKg == null) return GrowthStatus.STABLE
        val diff = currentWeightKg - previousWeightKg
        return when {
            diff > 0.05 -> GrowthStatus.INCREASING
            diff < -0.20 -> GrowthStatus.DECREASING
            else -> GrowthStatus.STABLE
        }
    }

    /**
     * Returns a neutral descriptive summary of growth trajectory.
     * Never diagnoses malnutrition or medical deficiency.
     */
    fun getTrajectorySummary(currentWeightKg: Double, previousWeightKg: Double?): String {
        if (previousWeightKg == null) {
            return "Current weight recorded: ${currentWeightKg} kg. Baseline established."
        }
        val diff = currentWeightKg - previousWeightKg
        val formattedDiff = String.format("%.1f", Math.abs(diff))
        return when {
            diff > 0.05 -> "Weight increased by $formattedDiff kg since previous measurement. Trajectory is positive."
            diff < -0.20 -> "Weight decreased by $formattedDiff kg since previous measurement. Trajectory indicates review."
            else -> "Weight is stable ($currentWeightKg kg) relative to previous measurement."
        }
    }

    /**
     * Checks if a measurement requires Anganwadi worker follow-up or review,
     * using standard reference ranges as informational guidelines only.
     */
    fun isReviewRecommended(ageYears: Int, weightKg: Double): Boolean {
        // Broad reference limits (3SD proxy) for 2-5 year olds
        val lowerGuideline = when (ageYears) {
            2 -> 9.0
            3 -> 10.5
            4 -> 11.5
            5 -> 12.5
            else -> 10.0
        }
        return weightKg < lowerGuideline
    }
}
