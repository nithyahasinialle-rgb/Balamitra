package com.balamitra.ai

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import com.balamitra.core.model.Language
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.util.Locale

sealed class STTResult {
    data class Partial(val text: String) : STTResult()
    data class Final(val text: String) : STTResult()
    data class RmsChanged(val normalizedLevel: Float) : STTResult()
    data class Error(val message: String, val fallbackText: String) : STTResult()
}

class LocalIndicSTTEngine(private val context: Context) : STTEngine {

    private var speechRecognizer: SpeechRecognizer? = null
    private val mainHandler = Handler(Looper.getMainLooper())

    override suspend fun startListening(language: Language): Flow<String> = callbackFlow {
        val locale = when (language) {
            Language.HINDI -> Locale("hi", "IN")
            Language.TELUGU -> Locale("te", "IN")
            Language.ENGLISH -> Locale("en", "IN")
        }

        mainHandler.post {
            try {
                if (SpeechRecognizer.isRecognitionAvailable(context)) {
                    val recognizer = SpeechRecognizer.createSpeechRecognizer(context)
                    speechRecognizer = recognizer

                    val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                        putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                        putExtra(RecognizerIntent.EXTRA_LANGUAGE, locale.toLanguageTag())
                        putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, locale.toLanguageTag())
                        putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE, locale.toLanguageTag())
                        putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
                        putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3)
                    }

                    recognizer.setRecognitionListener(object : RecognitionListener {
                        override fun onReadyForSpeech(params: Bundle?) {}
                        override fun onBeginningOfSpeech() {}
                        override fun onRmsChanged(rmsdB: Float) {}
                        override fun onBufferReceived(buffer: ByteArray?) {}
                        override fun onEndOfSpeech() {}

                        override fun onError(error: Int) {
                            // Close without sending fake mock data
                            close()
                        }

                        override fun onResults(results: Bundle?) {
                            val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                            val spokenText = matches?.firstOrNull()
                            if (!spokenText.isNullOrBlank()) {
                                trySend(spokenText)
                            }
                            close()
                        }

                        override fun onPartialResults(partialResults: Bundle?) {
                            val matches = partialResults?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                            matches?.firstOrNull()?.let { trySend(it) }
                        }

                        override fun onEvent(eventType: Int, params: Bundle?) {}
                    })

                    recognizer.startListening(intent)
                } else {
                    close()
                }
            } catch (e: Exception) {
                close()
            }
        }

        awaitClose {
            mainHandler.post {
                try {
                    speechRecognizer?.destroy()
                    speechRecognizer = null
                } catch (_: Exception) {}
            }
        }
    }

    override suspend fun stopListening() {
        mainHandler.post {
            try {
                speechRecognizer?.stopListening()
            } catch (_: Exception) {}
        }
    }

    override fun getEngineName(): String = "On-Device Indic Speech Engine (Android Speech & IndicConformer Interface)"

    override fun isOfflineCapable(): Boolean = true

    fun getSampleUtterance(language: Language): String {
        return when (language) {
            Language.TELUGU -> "రవికి నాలుగు సంవత్సరాలు. ఈరోజు అతని బరువు 12.8 కిలోలు. అన్నం, పప్పు, అరటిపండు తిన్నాడు. రంగులను బాగా గుర్తించాడు, కానీ రెండు సూచనలు వరుసగా పాటించడానికి కొంచెం సహాయం కావాలి."
            Language.HINDI -> "रवि चार साल का है। आज उसका वजन 12.8 किलो है। उसने दाल, चावल और केला खाया। उसने रंगों को सही पहचाना, लेकिन दो निर्देशों का एक साथ पालन करने में मदद चाहिए।"
            Language.ENGLISH -> "Ravi is four years old. His weight today is 12.8 kilos. He ate rice, dal and banana. He identified colours but needed help following two instructions."
        }
    }
}
