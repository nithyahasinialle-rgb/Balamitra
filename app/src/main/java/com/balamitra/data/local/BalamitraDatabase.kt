package com.balamitra.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        ChildEntity::class,
        GrowthObservationEntity::class,
        NutritionObservationEntity::class,
        DevelopmentObservationEntity::class,
        ActivityEntity::class,
        ActivityAttemptEntity::class,
        MaterialInventoryEntity::class,
        WorkerOverrideLogEntity::class
    ],
    version = 3,
    exportSchema = false
)
abstract class BalamitraDatabase : RoomDatabase() {
    abstract fun childDao(): ChildDao
    abstract fun observationDao(): ObservationDao
    abstract fun activityDao(): ActivityDao
    abstract fun activityAttemptDao(): ActivityAttemptDao
    abstract fun materialDao(): MaterialDao
    abstract fun workerOverrideDao(): WorkerOverrideDao

    companion object {
        @Volatile
        private var INSTANCE: BalamitraDatabase? = null

        fun getInstance(context: Context): BalamitraDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BalamitraDatabase::class.java,
                    "balamitra.db"
                )
                .fallbackToDestructiveMigration()
                .addCallback(object : Callback() {
                    override fun onOpen(db: SupportSQLiteDatabase) {
                        super.onOpen(db)
                        CoroutineScope(Dispatchers.IO).launch {
                            val database = getInstance(context)
                            database.childDao().insertChildren(SeedData.children)
                            database.activityDao().insertActivities(SeedData.activities)
                            database.materialDao().insertMaterials(SeedData.materials)
                        }
                    }
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        CoroutineScope(Dispatchers.IO).launch {
                            val database = getInstance(context)
                            database.childDao().insertChildren(SeedData.children)
                            database.observationDao().insertGrowths(SeedData.growthObservations)
                            database.observationDao().insertNutritions(SeedData.nutritionObservations)
                            database.observationDao().insertDevelopments(SeedData.developmentObservations)
                            database.activityDao().insertActivities(SeedData.activities)
                            database.activityAttemptDao().insertAttempts(SeedData.activityAttempts)
                            database.materialDao().insertMaterials(SeedData.materials)
                        }
                    }
                }).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
