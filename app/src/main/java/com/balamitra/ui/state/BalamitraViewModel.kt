package com.balamitra.ui.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.balamitra.ai.OnDeviceIndicNluEngine
import com.balamitra.core.model.ActivityOutcome
import com.balamitra.core.model.ActivityRecommendation
import com.balamitra.core.model.BenchmarkMetrics
import com.balamitra.core.model.CentreInsightSummary
import com.balamitra.core.model.ConnectTheDotsResult
import com.balamitra.core.model.Language
import com.balamitra.core.model.OverrideReason
import com.balamitra.core.model.StructuredObservationDraft
import com.balamitra.core.model.WhatChangedResult
import com.balamitra.domain.nutrition.MultiDayNutritionResult
import com.balamitra.data.local.ChildEntity
import com.balamitra.data.local.DevelopmentObservationEntity
import com.balamitra.data.local.GrowthObservationEntity
import com.balamitra.data.local.MaterialInventoryEntity
import com.balamitra.data.local.NutritionObservationEntity
import com.balamitra.data.model.AnganwadiWorker
import com.balamitra.data.model.CenterDailyStatus
import com.balamitra.data.model.DefaultWorkerSession
import com.balamitra.data.repository.ChildRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class AppTab {
    TODAY,
    CHILDREN,
    OBSERVE,
    INSIGHTS
}

data class BalamitraUiState(
    val isAuthenticated: Boolean = false, // Shows login screen initially for teacher authentication
    val attendanceMap: Map<String, Boolean> = emptyMap(),
    val isAttendanceDialogOpen: Boolean = false,
    val currentWorker: AnganwadiWorker = DefaultWorkerSession.defaultWorker,
    val centerStatus: CenterDailyStatus = DefaultWorkerSession.defaultDailyStatus,
    val currentLanguage: Language = Language.ENGLISH,
    val activeTab: AppTab = AppTab.TODAY,
    val isOfflineProofActive: Boolean = true,
    val isDemoMode: Boolean = true,
    val isBusyMode: Boolean = false,
    val children: List<ChildEntity> = emptyList(),
    val selectedChild: ChildEntity? = null,
    val growthHistory: List<GrowthObservationEntity> = emptyList(),
    val nutritionHistory: List<NutritionObservationEntity> = emptyList(),
    val developmentHistory: List<DevelopmentObservationEntity> = emptyList(),
    val materials: List<MaterialInventoryEntity> = emptyList(),
    val currentRecommendation: ActivityRecommendation? = null,
    val whatChangedResult: WhatChangedResult? = null,
    val connectTheDotsResult: ConnectTheDotsResult? = null,
    val isWhatChangedDialogOpen: Boolean = false,
    val isConnectTheDotsDialogOpen: Boolean = false,
    val isWhyDialogOpen: Boolean = false,
    val isOverrideDialogOpen: Boolean = false,
    val isReObserveDialogOpen: Boolean = false,
    val isParentExplainDialogOpen: Boolean = false,
    val parentExplanationText: String = "",
    val parentHomeTip: String = "",
    val parentExplainLanguage: Language = Language.ENGLISH,
    val isListeningToVoice: Boolean = false,
    val voiceTranscript: String = "",
    val audioAmplitude: Float = 0f,
    val structuredDraft: StructuredObservationDraft? = null,
    val isVoiceConfirmationOpen: Boolean = false,
    val centreInsights: CentreInsightSummary? = null,
    val benchmarkMetrics: BenchmarkMetrics? = null,
    val toastMessage: String? = null,
    val textScale: Float = 1.0f,
    val isCameraActivityDialogOpen: Boolean = false,
    val multiDayNutritionResult: MultiDayNutritionResult? = null,
    val dataUsedTodayBytes: Long = 0L,
    val dataSavedVsCloudBytes: Long = 44_920_000L,
    val cloudSyncQueueCount: Int = 3,
    val isDataMonitorDialogOpen: Boolean = false,
    val isCloudSyncSimulating: Boolean = false,
    val isAddChildDialogOpen: Boolean = false
)

class BalamitraViewModel(private val repository: ChildRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(BalamitraUiState())
    val uiState: StateFlow<BalamitraUiState> = _uiState.asStateFlow()

    init {
        loadChildren()
        loadMaterials()
        refreshCentreInsights()
    }

    fun login(workerId: String, centerCode: String, pin: String) {
        _uiState.update {
            it.copy(
                isAuthenticated = true,
                toastMessage = "Welcome, ${it.currentWorker.name} (${it.currentWorker.centerName})"
            )
        }
    }

    fun logout() {
        _uiState.update { it.copy(isAuthenticated = false) }
    }

    fun setLanguage(language: Language) {
        _uiState.update { it.copy(currentLanguage = language, parentExplainLanguage = language) }
        _uiState.value.selectedChild?.let { selectChild(it) }
    }

    fun setActiveTab(tab: AppTab) {
        _uiState.update { it.copy(activeTab = tab) }
        if (tab == AppTab.INSIGHTS) {
            refreshCentreInsights()
        }
    }

    fun toggleOfflineProof() {
        _uiState.update { it.copy(isOfflineProofActive = !it.isOfflineProofActive) }
    }

    fun toggleDemoMode() {
        val newDemo = !_uiState.value.isDemoMode
        _uiState.update { it.copy(isDemoMode = newDemo) }
        loadChildren()
    }

    fun toggleBusyMode() {
        _uiState.update { it.copy(isBusyMode = !it.isBusyMode) }
    }

    private fun loadChildren() {
        viewModelScope.launch {
            repository.getChildren(_uiState.value.isDemoMode).collectLatest { list ->
                _uiState.update { state ->
                    state.copy(
                        children = list,
                        selectedChild = state.selectedChild ?: list.find { it.id == "child_ravi" } ?: list.firstOrNull()
                    )
                }
                _uiState.value.selectedChild?.let { selectChild(it) }
            }
        }
    }

    private fun loadMaterials() {
        viewModelScope.launch {
            repository.getMaterials().collectLatest { mats ->
                _uiState.update { it.copy(materials = mats) }
            }
        }
    }

    fun selectChild(child: ChildEntity) {
        _uiState.update { it.copy(selectedChild = child) }
        viewModelScope.launch {
            val growths = repository.getGrowthHistory(child.id).first()
            val nutrs = repository.getNutritionHistory(child.id).first()
            val devs = repository.getDevelopmentHistory(child.id).first()
            val rec = repository.getPersonalizedRecommendation(child.id, _uiState.value.currentLanguage)
            val nutritionAnalysis = repository.getMultiDayNutritionAnalysis(child.id, _uiState.value.currentLanguage)

            _uiState.update {
                it.copy(
                    growthHistory = growths,
                    nutritionHistory = nutrs,
                    developmentHistory = devs,
                    currentRecommendation = rec,
                    multiDayNutritionResult = nutritionAnalysis
                )
            }
        }
    }

    fun openWhatChanged() {
        viewModelScope.launch {
            val childId = _uiState.value.selectedChild?.id ?: return@launch
            val result = repository.computeWhatChanged(childId, _uiState.value.currentLanguage)
            _uiState.update {
                it.copy(whatChangedResult = result, isWhatChangedDialogOpen = true)
            }
        }
    }

    fun closeWhatChanged() {
        _uiState.update { it.copy(isWhatChangedDialogOpen = false) }
    }

    fun openConnectTheDots() {
        viewModelScope.launch {
            val childId = _uiState.value.selectedChild?.id ?: return@launch
            val result = repository.computeConnectTheDots(childId, _uiState.value.currentLanguage)
            _uiState.update {
                it.copy(connectTheDotsResult = result, isConnectTheDotsDialogOpen = true)
            }
        }
    }

    fun closeConnectTheDots() {
        _uiState.update { it.copy(isConnectTheDotsDialogOpen = false) }
    }

    fun openWhyDialog() {
        _uiState.update { it.copy(isWhyDialogOpen = true) }
    }

    fun closeWhyDialog() {
        _uiState.update { it.copy(isWhyDialogOpen = false) }
    }

    fun openOverrideDialog() {
        _uiState.update { it.copy(isOverrideDialogOpen = true) }
    }

    fun closeOverrideDialog() {
        _uiState.update { it.copy(isOverrideDialogOpen = false) }
    }

    fun recordWorkerOverride(reason: OverrideReason, note: String?) {
        viewModelScope.launch {
            val child = _uiState.value.selectedChild ?: return@launch
            val currentRec = _uiState.value.currentRecommendation ?: return@launch
            repository.logWorkerOverride(child.id, currentRec.id, null, reason, note)
            val updatedRec = repository.getPersonalizedRecommendation(child.id, _uiState.value.currentLanguage)
            _uiState.update {
                it.copy(
                    currentRecommendation = updatedRec,
                    isOverrideDialogOpen = false,
                    toastMessage = "Worker preference saved. Suggestion adapted."
                )
            }
        }
    }

    fun openReObserveDialog() {
        _uiState.update { it.copy(isReObserveDialogOpen = true) }
    }

    fun closeReObserveDialog() {
        _uiState.update { it.copy(isReObserveDialogOpen = false) }
    }

    fun submitReObservation(outcome: ActivityOutcome) {
        viewModelScope.launch {
            val child = _uiState.value.selectedChild ?: return@launch
            val currentRec = _uiState.value.currentRecommendation ?: return@launch

            val note = when (outcome) {
                ActivityOutcome.INDEPENDENT -> "Child completed the activity with high focus and independence."
                ActivityOutcome.NEEDED_HELP -> "Child required adult guidance and physical demonstrations."
                ActivityOutcome.COULD_NOT_DO -> "Child paused or felt frustrated; simpler scaffold needed."
            }

            repository.recordActivityAttempt(
                childId = child.id,
                activityId = currentRec.id,
                outcome = outcome,
                workerNotes = note,
                materialsUsed = currentRec.requiredMaterials.joinToString(", ")
            )

            val newRec = repository.getPersonalizedRecommendation(child.id)
            _uiState.update {
                it.copy(
                    currentRecommendation = newRec,
                    isReObserveDialogOpen = false,
                    toastMessage = "BALAMITRA learned: Recorded outcome to adapt future activities."
                )
            }
        }
    }

    fun openParentExplainDialog() {
        viewModelScope.launch {
            val childId = _uiState.value.selectedChild?.id ?: return@launch
            val lang = _uiState.value.parentExplainLanguage
            val (text, homeTip) = repository.getParentExplanation(childId, lang)
            _uiState.update {
                it.copy(
                    parentExplanationText = text,
                    parentHomeTip = homeTip,
                    isParentExplainDialogOpen = true
                )
            }
        }
    }

    fun setParentExplainLanguage(lang: Language) {
        _uiState.update { it.copy(parentExplainLanguage = lang) }
        openParentExplainDialog()
    }

    fun closeParentExplainDialog() {
        _uiState.update { it.copy(isParentExplainDialogOpen = false) }
    }

    fun toggleMaterial(id: String, isAvailable: Boolean) {
        viewModelScope.launch {
            repository.updateMaterialAvailability(id, isAvailable)
            _uiState.value.selectedChild?.let { child ->
                val newRec = repository.getPersonalizedRecommendation(child.id)
                _uiState.update { it.copy(currentRecommendation = newRec) }
            }
        }
    }

    fun setListeningState(isListening: Boolean) {
        _uiState.update { it.copy(isListeningToVoice = isListening) }
    }

    fun showToast(message: String) {
        _uiState.update { it.copy(toastMessage = message) }
    }

    fun startVoiceObservation(language: Language) {
        viewModelScope.launch {
            _uiState.update { it.copy(isListeningToVoice = true, voiceTranscript = "") }
            try {
                repository.sttEngine.startListening(language).collectLatest { utterance ->
                    _uiState.update { it.copy(voiceTranscript = utterance, isListeningToVoice = false) }
                    processVoiceUtterance(utterance, language.code, _uiState.value.selectedChild)
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isListeningToVoice = false,
                        toastMessage = "Could not record audio. Please try again or type observation below."
                    )
                }
            }
        }
    }

    fun processVoiceUtterance(utterance: String, langCode: String, child: ChildEntity? = null) {
        viewModelScope.launch {
            val targetChild = child ?: _uiState.value.selectedChild
            val (draft, metrics) = (repository.llmEngine as? OnDeviceIndicNluEngine)?.extractStructuredObservation(
                rawUtterance = utterance,
                targetLanguageCode = langCode,
                contextChild = targetChild
            ) ?: repository.llmEngine.extractStructuredObservation(utterance, langCode)

            _uiState.update {
                it.copy(
                    voiceTranscript = utterance,
                    structuredDraft = draft,
                    benchmarkMetrics = metrics,
                    isVoiceConfirmationOpen = true
                )
            }
        }
    }

    fun confirmAndSaveDraft() {
        viewModelScope.launch {
            val draft = _uiState.value.structuredDraft ?: return@launch
            repository.saveConfirmedObservation(draft)

            val matchedChild = _uiState.value.children.find {
                it.id == draft.childId || it.name.equals(draft.childName, ignoreCase = true)
            } ?: _uiState.value.selectedChild

            matchedChild?.let { selectChild(it) }

            _uiState.update {
                it.copy(
                    isVoiceConfirmationOpen = false,
                    structuredDraft = null,
                    voiceTranscript = "",
                    toastMessage = "Observation for ${draft.childName} verified & saved!"
                )
            }
        }
    }

    fun dismissVoiceConfirmation() {
        _uiState.update { it.copy(isVoiceConfirmationOpen = false, structuredDraft = null) }
    }

    fun dismissToast() {
        _uiState.update { it.copy(toastMessage = null) }
    }

    fun setTextScale(scale: Float) {
        _uiState.update { it.copy(textScale = scale) }
    }

    fun cycleTextScale() {
        val next = when (_uiState.value.textScale) {
            1.0f -> 1.18f
            1.18f -> 1.35f
            else -> 1.0f
        }
        _uiState.update { it.copy(textScale = next) }
    }

    fun openCameraActivityDialog() {
        _uiState.update { it.copy(isCameraActivityDialogOpen = true) }
    }

    fun closeCameraActivityDialog() {
        _uiState.update { it.copy(isCameraActivityDialogOpen = false) }
    }

    fun saveCameraActivityOutcome(outcome: ActivityOutcome, evidenceNote: String) {
        viewModelScope.launch {
            val child = _uiState.value.selectedChild ?: return@launch
            val currentRec = _uiState.value.currentRecommendation
            repository.recordActivityAttempt(
                childId = child.id,
                activityId = currentRec?.id ?: "act_camera_verified",
                outcome = outcome,
                workerNotes = evidenceNote,
                materialsUsed = "Camera-verified child action (Computer Vision + LLM)"
            )
            selectChild(child)
            _uiState.update {
                it.copy(
                    isCameraActivityDialogOpen = false,
                    toastMessage = "AI Camera Milestone logged for ${child.name}!"
                )
            }
        }
    }

    fun openDataMonitorDialog() {
        _uiState.update { it.copy(isDataMonitorDialogOpen = true) }
    }

    fun closeDataMonitorDialog() {
        _uiState.update { it.copy(isDataMonitorDialogOpen = false) }
    }

    fun simulateCloudSync() {
        viewModelScope.launch {
            _uiState.update { it.copy(isCloudSyncSimulating = true) }
            kotlinx.coroutines.delay(1200)
            _uiState.update {
                it.copy(
                    isCloudSyncSimulating = false,
                    cloudSyncQueueCount = 0,
                    dataUsedTodayBytes = 620L,
                    toastMessage = "Delta JSON (~0.6 KB) synced to State ICDS Server!"
                )
            }
        }
    }

    fun openAddChildDialog() {
        _uiState.update { it.copy(isAddChildDialogOpen = true) }
    }

    fun closeAddChildDialog() {
        _uiState.update { it.copy(isAddChildDialogOpen = false) }
    }

    fun registerChild(
        name: String,
        ageYears: Int,
        gender: String,
        fatherName: String,
        motherName: String,
        villageWard: String,
        weightKg: Double?,
        heightCm: Double?,
        notes: String
    ) {
        viewModelScope.launch {
            val randomSuffix = (1000..9999).random()
            val newChild = ChildEntity(
                id = "child_${name.lowercase().filter { it.isLetter() }}_$randomSuffix",
                name = name,
                ageYears = ageYears,
                gender = gender,
                preferredLanguage = _uiState.value.currentLanguage.code,
                baselineNotes = notes.ifBlank { "Newly enrolled student at Bachupally Anganwadi Center." },
                fatherName = fatherName.ifBlank { "Sri Guardian" },
                motherName = motherName.ifBlank { "Smt. Guardian" },
                enrollmentId = "ICDS-TG-BCH-2026-$randomSuffix",
                villageWard = villageWard.ifBlank { "Bachupally Ward-1" },
                aadhaarLast4 = "$randomSuffix",
                isDemoChild = false,
                createdAtEpochMs = System.currentTimeMillis()
            )
            repository.registerNewChild(newChild, weightKg, heightCm)
            selectChild(newChild)
            _uiState.update {
                it.copy(
                    isAddChildDialogOpen = false,
                    toastMessage = "✓ $name was successfully enrolled in Anganwadi!"
                )
            }
        }
    }

    fun setAttendanceDialogOpen(open: Boolean) {
        _uiState.update { it.copy(isAttendanceDialogOpen = open) }
    }

    fun toggleAttendance(childId: String) {
        val currentMap = _uiState.value.attendanceMap.toMutableMap()
        val currentVal = currentMap[childId] ?: true
        currentMap[childId] = !currentVal
        _uiState.update { it.copy(attendanceMap = currentMap) }
    }

    fun markAllPresent() {
        val allMap = _uiState.value.children.associate { it.id to true }
        _uiState.update { it.copy(attendanceMap = allMap) }
    }

    private fun refreshCentreInsights() {
        viewModelScope.launch {
            val insights = repository.getCentreInsights()
            _uiState.update { it.copy(centreInsights = insights) }
        }
    }
}

class BalamitraViewModelFactory(private val repository: ChildRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BalamitraViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BalamitraViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
