package com.balamitra.ai

import com.balamitra.core.model.BenchmarkMetrics
import com.balamitra.core.model.StructuredObservationDraft

interface LLMEngine {
    suspend fun extractStructuredObservation(
        rawUtterance: String,
        targetLanguageCode: String
    ): Pair<StructuredObservationDraft, BenchmarkMetrics>

    fun getModelName(): String
    fun isLocalInference(): Boolean
}
