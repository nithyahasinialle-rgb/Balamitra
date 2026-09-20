package com.balamitra.app

import android.app.Application
import com.balamitra.ai.LocalIndicSTTEngine
import com.balamitra.ai.OnDeviceIndicNluEngine
import com.balamitra.data.local.BalamitraDatabase
import com.balamitra.data.repository.ChildRepository

class BalamitraApplication : Application() {

    lateinit var repository: ChildRepository
        private set

    override fun onCreate() {
        super.onCreate()
        val database = BalamitraDatabase.getInstance(this)
        val sttEngine = LocalIndicSTTEngine(this)
        val llmEngine = OnDeviceIndicNluEngine()

        repository = ChildRepository(
            database = database,
            sttEngine = sttEngine,
            llmEngine = llmEngine
        )
    }
}
