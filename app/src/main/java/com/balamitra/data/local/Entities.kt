package com.balamitra.data.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.balamitra.core.model.ActivityOutcome
import com.balamitra.core.model.DevelopmentDomain
import com.balamitra.core.model.GrowthStatus
import com.balamitra.core.model.MaterialLocation
import com.balamitra.core.model.OverrideReason

@Entity(tableName = "children")
data class ChildEntity(
    @PrimaryKey val id: String,
    val name: String,
    val ageYears: Int,
    val gender: String, // "M" or "F"
    val preferredLanguage: String, // "en", "hi", "te"
    val baselineNotes: String,
    val fatherName: String = "Sri K. Venkata Rao",
    val motherName: String = "Smt. K. Lakshmi",
    val enrollmentId: String = "ICDS-TG-HYD-2022-048",
    val villageWard: String = "Bachupally Ward-1",
    val aadhaarLast4: String = "4819",
    val isDemoChild: Boolean = false,
    val createdAtEpochMs: Long = System.currentTimeMillis()
)

@Entity(
    tableName = "growth_observations",
    foreignKeys = [
        ForeignKey(
            entity = ChildEntity::class,
            parentColumns = ["id"],
            childColumns = ["childId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("childId")]
)
data class GrowthObservationEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val childId: String,
    val timestampEpochMs: Long,
    val weightKg: Double,
    val heightCm: Double?,
    val trajectoryStatus: GrowthStatus,
    val notes: String
)

@Entity(
    tableName = "nutrition_observations",
    foreignKeys = [
        ForeignKey(
            entity = ChildEntity::class,
            parentColumns = ["id"],
            childColumns = ["childId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("childId")]
)
data class NutritionObservationEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val childId: String,
    val timestampEpochMs: Long,
    val mealType: String, // Breakfast, Lunch, Snack
    val foodsEaten: String, // Comma-separated or JSON
    val appetiteNotes: String
)

@Entity(
    tableName = "development_observations",
    foreignKeys = [
        ForeignKey(
            entity = ChildEntity::class,
            parentColumns = ["id"],
            childColumns = ["childId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("childId")]
)
data class DevelopmentObservationEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val childId: String,
    val timestampEpochMs: Long,
    val domain: DevelopmentDomain,
    val observationText: String,
    val rawWorkerSpeech: String?,
    val needsFollowUp: Boolean = false,
    val confidence: Double = 0.95
)

@Entity(tableName = "activities")
data class ActivityEntity(
    @PrimaryKey val id: String,
    val title: String,
    val domain: DevelopmentDomain,
    val minAgeYears: Int,
    val maxAgeYears: Int,
    val durationMinutes: Int,
    val requiredMaterialsList: String, // Comma separated: "cups, bottle caps"
    val stepsList: String, // Pipe separated: "Step 1|Step 2"
    val whatToObserve: String,
    val parentExplanationEn: String,
    val parentExplanationHi: String,
    val parentExplanationTe: String
)

@Entity(
    tableName = "activity_attempts",
    foreignKeys = [
        ForeignKey(
            entity = ChildEntity::class,
            parentColumns = ["id"],
            childColumns = ["childId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("childId")]
)
data class ActivityAttemptEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val childId: String,
    val activityId: String,
    val timestampEpochMs: Long,
    val outcome: ActivityOutcome,
    val workerObservationNotes: String,
    val materialsUsed: String
)

@Entity(tableName = "materials_inventory")
data class MaterialInventoryEntity(
    @PrimaryKey val id: String,
    val name: String,
    val location: MaterialLocation,
    val isAvailable: Boolean
)

@Entity(tableName = "worker_override_logs")
data class WorkerOverrideLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val childId: String,
    val suggestedActivityId: String,
    val chosenActivityId: String?,
    val reason: OverrideReason,
    val additionalNote: String?,
    val timestampEpochMs: Long = System.currentTimeMillis()
)
