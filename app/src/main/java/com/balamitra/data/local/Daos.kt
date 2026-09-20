package com.balamitra.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ChildDao {
    @Query("SELECT * FROM children ORDER BY name ASC")
    fun getAllChildren(): Flow<List<ChildEntity>>

    @Query("SELECT * FROM children WHERE isDemoChild = :isDemo ORDER BY name ASC")
    fun getChildrenByMode(isDemo: Boolean): Flow<List<ChildEntity>>

    @Query("SELECT * FROM children WHERE id = :childId LIMIT 1")
    suspend fun getChildById(childId: String): ChildEntity?

    @Query("SELECT * FROM children WHERE LOWER(name) LIKE '%' || LOWER(:name) || '%' LIMIT 1")
    suspend fun findChildByName(name: String): ChildEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChild(child: ChildEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChildren(children: List<ChildEntity>)
}

@Dao
interface ObservationDao {
    // Growth
    @Query("SELECT * FROM growth_observations WHERE childId = :childId ORDER BY timestampEpochMs DESC")
    fun getGrowthHistory(childId: String): Flow<List<GrowthObservationEntity>>

    @Query("SELECT * FROM growth_observations WHERE childId = :childId ORDER BY timestampEpochMs DESC LIMIT 2")
    suspend fun getLatestGrowthPair(childId: String): List<GrowthObservationEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGrowth(observation: GrowthObservationEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGrowths(observations: List<GrowthObservationEntity>)

    // Nutrition
    @Query("SELECT * FROM nutrition_observations WHERE childId = :childId ORDER BY timestampEpochMs DESC")
    fun getNutritionHistory(childId: String): Flow<List<NutritionObservationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNutrition(observation: NutritionObservationEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNutritions(observations: List<NutritionObservationEntity>)

    // Development
    @Query("SELECT * FROM development_observations WHERE childId = :childId ORDER BY timestampEpochMs DESC")
    fun getDevelopmentHistory(childId: String): Flow<List<DevelopmentObservationEntity>>

    @Query("SELECT * FROM development_observations WHERE childId = :childId ORDER BY timestampEpochMs DESC LIMIT 5")
    suspend fun getRecentDevelopmentObservations(childId: String): List<DevelopmentObservationEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDevelopment(observation: DevelopmentObservationEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDevelopments(observations: List<DevelopmentObservationEntity>)

    // Aggregates for Centre Insights
    @Query("SELECT COUNT(DISTINCT childId) FROM development_observations WHERE timestampEpochMs >= :sinceEpochMs")
    suspend fun countDistinctObservedChildren(sinceEpochMs: Long): Int

    @Query("SELECT COUNT(*) FROM development_observations WHERE needsFollowUp = 1")
    suspend fun countPendingFollowUps(): Int
}

@Dao
interface ActivityDao {
    @Query("SELECT * FROM activities")
    fun getAllActivities(): Flow<List<ActivityEntity>>

    @Query("SELECT * FROM activities WHERE id = :activityId LIMIT 1")
    suspend fun getActivityById(activityId: String): ActivityEntity?

    @Query("SELECT * FROM activities WHERE minAgeYears <= :age AND maxAgeYears >= :age")
    suspend fun getActivitiesForAge(age: Int): List<ActivityEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActivities(activities: List<ActivityEntity>)
}

@Dao
interface ActivityAttemptDao {
    @Query("SELECT * FROM activity_attempts WHERE childId = :childId ORDER BY timestampEpochMs DESC")
    fun getAttemptsForChild(childId: String): Flow<List<ActivityAttemptEntity>>

    @Query("SELECT * FROM activity_attempts WHERE childId = :childId ORDER BY timestampEpochMs DESC LIMIT 5")
    suspend fun getRecentAttempts(childId: String): List<ActivityAttemptEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAttempt(attempt: ActivityAttemptEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAttempts(attempts: List<ActivityAttemptEntity>)

    @Query("SELECT COUNT(*) FROM activity_attempts WHERE timestampEpochMs >= :sinceEpochMs")
    suspend fun countAttemptsSince(sinceEpochMs: Long): Int
}

@Dao
interface MaterialDao {
    @Query("SELECT * FROM materials_inventory")
    fun getAllMaterials(): Flow<List<MaterialInventoryEntity>>

    @Query("SELECT * FROM materials_inventory WHERE isAvailable = 1")
    suspend fun getAvailableMaterials(): List<MaterialInventoryEntity>

    @Query("UPDATE materials_inventory SET isAvailable = :isAvailable WHERE id = :id")
    suspend fun updateAvailability(id: String, isAvailable: Boolean)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMaterials(materials: List<MaterialInventoryEntity>)
}

@Dao
interface WorkerOverrideDao {
    @Insert
    suspend fun logOverride(override: WorkerOverrideLogEntity)

    @Query("SELECT * FROM worker_override_logs WHERE childId = :childId ORDER BY timestampEpochMs DESC")
    suspend fun getOverridesForChild(childId: String): List<WorkerOverrideLogEntity>
}
