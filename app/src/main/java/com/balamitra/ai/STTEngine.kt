package com.balamitra.ai

import com.balamitra.core.model.Language
import kotlinx.coroutines.flow.Flow

interface STTEngine {
    suspend fun startListening(language: Language): Flow<String>
    suspend fun stopListening()
    fun getEngineName(): String
    fun isOfflineCapable(): Boolean
}
