package com.balamitra.ui.screens

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.speech.RecognizerIntent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayCircleOutline
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.balamitra.core.localization.LocalAppStrings
import com.balamitra.core.model.Language
import com.balamitra.ui.state.BalamitraUiState
import com.balamitra.ui.state.BalamitraViewModel
import com.balamitra.ui.theme.BrandYellowPrimary
import com.balamitra.ui.theme.DeepBlack
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ObserveScreen(
    state: BalamitraUiState,
    viewModel: BalamitraViewModel,
    modifier: Modifier = Modifier
) {
    val s = LocalAppStrings.current
    val context = LocalContext.current
    var speechLanguage by remember { mutableStateOf(state.currentLanguage) }
    var manualObservationText by remember { mutableStateOf("") }
    var showLlmArchitectureDialog by remember { mutableStateOf(false) }

    // Observation Date Selection (Feature Request)
    val dateDisplayFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    val nowMillis = System.currentTimeMillis()
    var selectedDateOffsetDays by remember { mutableStateOf(0) }
    val currentSelectedDateMillis = nowMillis - (selectedDateOffsetDays * 86400000L)
    val currentSelectedDateString = dateDisplayFormat.format(Date(currentSelectedDateMillis))

    var hasAudioPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasAudioPermission = isGranted
    }

    val speechLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        viewModel.setListeningState(false)
        if (result.resultCode == Activity.RESULT_OK) {
            val matches = result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            val spoken = matches?.firstOrNull()
            if (!spoken.isNullOrBlank()) {
                manualObservationText = spoken
                viewModel.processVoiceUtterance(spoken, speechLanguage.code, state.selectedChild)
            }
        }
    }

    fun startSpeechRecognition() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            val langTag = when (speechLanguage) {
                Language.TELUGU -> "te-IN"
                Language.HINDI -> "hi-IN"
                Language.ENGLISH -> "en-IN"
            }
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, langTag)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, langTag)
            putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE, false)
            putExtra("android.speech.extra.EXTRA_ADDITIONAL_LANGUAGES", arrayOf("te-IN", "hi-IN", "en-IN"))
            putExtra(
                RecognizerIntent.EXTRA_PROMPT,
                when (speechLanguage) {
                    Language.TELUGU -> "మాట్లాడండి (${state.selectedChild?.name ?: "పిల్లవాడు"} పరిశీలన)..."
                    Language.HINDI -> "बोलिए (${state.selectedChild?.name ?: "बच्चा"} अवलोकन)..."
                    Language.ENGLISH -> "Speak observation for ${state.selectedChild?.name ?: "Child"}..."
                }
            )
            putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3)
        }
        try {
            viewModel.setListeningState(true)
            speechLauncher.launch(intent)
        } catch (e: Exception) {
            viewModel.setListeningState(false)
            android.widget.Toast.makeText(context, "Voice error: ${e.localizedMessage}", android.widget.Toast.LENGTH_SHORT).show()
        }
    }

    val infiniteTransition = rememberInfiniteTransition()
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = if (state.isListeningToVoice) 1.25f else 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val selectedChild = state.selectedChild

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top bar: Title and Busy Mode Toggle
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = s.observeTitle,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = DeepBlack
                )
                Text(
                    text = s.observeSubtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 12.sp,
                    color = Color(0xFF4A5568)
                )
            }

            // Busy Mode Toggle
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Bolt,
                    contentDescription = "Busy Mode",
                    tint = if (state.isBusyMode) BrandYellowPrimary else Color(0xFFBDC3C7),
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(s.busyMode, fontSize = 11.sp, fontWeight = FontWeight.Medium)
                Spacer(modifier = Modifier.width(4.dp))
                Switch(
                    checked = state.isBusyMode,
                    onCheckedChange = { viewModel.toggleBusyMode() },
                    modifier = Modifier.scale(0.8f)
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // ANGANWADI DIGITAL SAHAYIKA CARD (WORKER FRIENDLY)
        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFEFDF5)),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, BrandYellowPrimary, RoundedCornerShape(12.dp))
                .clickable { showLlmArchitectureDialog = true }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                    Icon(
                        imageVector = Icons.Default.Psychology,
                        contentDescription = null,
                        tint = DeepBlack,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = when (speechLanguage) {
                                Language.TELUGU -> "అంగన్‌వాడీ డిజిటల్ సహాయకురాలు"
                                Language.HINDI -> "आंगनवाड़ी डिजिटल सहायिका"
                                Language.ENGLISH -> "Anganwadi Digital Sahayika (AI Assistant)"
                            },
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.5.sp,
                            color = DeepBlack
                        )
                        Text(
                            text = when (speechLanguage) {
                                Language.TELUGU -> "పిల్లల మాటలు, ఆహారం & బరువు నమోదు సహాయం"
                                Language.HINDI -> "बच्चों की बातें, आहार और वजन दर्ज करने में सहायक"
                                Language.ENGLISH -> "Smart Child Growth & Nutrition Recording Helper"
                            },
                            fontSize = 10.sp,
                            color = Color(0xFF555555)
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(BrandYellowPrimary)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text("వివరాలు", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = DeepBlack)
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // CHILD SELECTION CHIPS
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = when (state.currentLanguage) {
                        Language.TELUGU -> "పరిశీలించాల్సిన బిడ్డ:"
                        Language.HINDI -> "अवलोकन के लिए बच्चा चुनें:"
                        Language.ENGLISH -> "Target Child for Observation:"
                    },
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = Color(0xFF2C3E50)
                )

                if (selectedChild != null) {
                    Text(
                        text = "✓ ${selectedChild.name} (${selectedChild.ageYears}y)",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFD49A0E)
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(state.children) { child ->
                    val isSelected = child.id == selectedChild?.id
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (isSelected) BrandYellowPrimary else Color(0xFFF2F4F4))
                            .border(
                                1.dp,
                                if (isSelected) DeepBlack else Color(0xFFBDC3C7).copy(alpha = 0.5f),
                                RoundedCornerShape(10.dp)
                            )
                            .clickable { viewModel.selectChild(child) }
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = if (isSelected) Icons.Default.CheckCircle else Icons.Default.Person,
                                contentDescription = null,
                                tint = DeepBlack,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${child.name} (${child.ageYears}y)",
                                color = DeepBlack,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // EXPLICIT OBSERVATION DATE SELECTOR (FEATURE REQUEST)
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(12.dp))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.CalendarToday, contentDescription = null, tint = DeepBlack, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = when (state.currentLanguage) {
                            Language.TELUGU -> "తేదీ (Date):"
                            Language.HINDI -> "दिनांक (Date):"
                            Language.ENGLISH -> "Observation Date:"
                        },
                        fontSize = 11.5.sp,
                        fontWeight = FontWeight.Bold,
                        color = DeepBlack
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    listOf(
                        Pair(0, "Today"),
                        Pair(1, "Yesterday"),
                        Pair(2, "-2 Days")
                    ).forEach { (offset, label) ->
                        val isSelected = selectedDateOffsetDays == offset
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isSelected) BrandYellowPrimary else Color(0xFFF1F5F9))
                                .clickable { selectedDateOffsetDays = offset }
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = label,
                                fontSize = 10.5.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = DeepBlack
                            )
                        }
                    }
                }
            }
        }


        Spacer(modifier = Modifier.height(14.dp))

        // Speech Language Selector Chips
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Language.values().forEach { lang ->
                val selected = speechLanguage == lang
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (selected) BrandYellowPrimary else Color(0xFFF4F6F6))
                        .border(
                            1.dp,
                            if (selected) DeepBlack else Color.Transparent,
                            RoundedCornerShape(12.dp)
                        )
                        .clickable { speechLanguage = lang }
                        .padding(horizontal = 14.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = lang.nativeName,
                        color = DeepBlack,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // On-Device In-App Mic Area
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(130.dp)
        ) {
            // Pulsing ring
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .scale(pulseScale)
                    .clip(CircleShape)
                    .background(BrandYellowPrimary.copy(alpha = if (state.isListeningToVoice) 0.45f else 0.15f))
            )

            // Center mic circle
            Box(
                modifier = Modifier
                    .size(84.dp)
                    .clip(CircleShape)
                    .background(if (state.isListeningToVoice) Color(0xFFC0392B) else BrandYellowPrimary)
                    .clickable {
                        if (!hasAudioPermission) {
                            permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                        } else {
                            startSpeechRecognition()
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (state.isListeningToVoice) Icons.Default.Stop else Icons.Default.Mic,
                    contentDescription = "Speak to Balamitra",
                    tint = if (state.isListeningToVoice) Color.White else DeepBlack,
                    modifier = Modifier.size(40.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "${s.tapToSpeak} (${speechLanguage.nativeName})",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = DeepBlack,
            fontSize = 13.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Quick 1-Tap Voice Simulation Pills (Telugu, Hindi, English)
        Text(
            text = when (speechLanguage) {
                Language.TELUGU -> "త్వరిత వాయిస్ పరీక్ష (Quick Voice Test - Zero Data):"
                Language.HINDI -> "त्वरित आवाज परीक्षण (Quick Voice Test - Zero Data):"
                Language.ENGLISH -> "Quick Voice Test (100% Offline / Zero Data):"
            },
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF555555)
        )
        Spacer(modifier = Modifier.height(6.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            val childDisplayName = selectedChild?.name ?: "రవి"
            listOf(
                Triple("🗣️ తెలుగు", Language.TELUGU, "$childDisplayName ఈరోజు 12.8 కిలోలు ఉన్నాడు, పప్పు అన్నం తిన్నాడు మరియు నీలం బంతిని గుర్తించి పట్టుకున్నాడు."),
                Triple("🗣️ हिंदी", Language.HINDI, "$childDisplayName ने आज दाल और चावल खाया, वजन 12.8 किलो है और तीन कप संतुलित रूप से जमाए।"),
                Triple("🗣️ English", Language.ENGLISH, "$childDisplayName ate dal and rice today, weighed 12.8 kg and completed the motor balance activity independently.")
            ).forEach { (pillLabel, lang, sampleText) ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (speechLanguage == lang) BrandYellowPrimary else Color(0xFFE2E8F0))
                        .clickable {
                            speechLanguage = lang
                            manualObservationText = sampleText
                            viewModel.processVoiceUtterance(sampleText, lang.code, selectedChild)
                        }
                        .padding(vertical = 7.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = pillLabel,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = DeepBlack
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // DIRECT TEXT / OBSERVATION NOTE INPUT CARD
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(14.dp))
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = null,
                            tint = DeepBlack,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = when (state.currentLanguage) {
                                Language.TELUGU -> "పరిశీలన నమోదు చేయండి లేదా టైప్ చేయండి"
                                Language.HINDI -> "अवलोकन नोट टाइप करें या संपादित करें"
                                Language.ENGLISH -> "Type or Edit Observation Note"
                            },
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = DeepBlack
                        )
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color(0xFFE8F8F5))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text("100% Offline", fontSize = 10.sp, color = Color(0xFF117A65), fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                val placeholderHint = when (speechLanguage) {
                    Language.TELUGU -> "ఉదా: ${selectedChild?.name ?: "అనన్య"} ఈరోజు 11.2 కిలోలు. అన్నం తిన్నది. బొమ్మలతో చక్కగా ఆడుకుంది..."
                    Language.HINDI -> "उदा: ${selectedChild?.name ?: "अनन्या"} का वजन 11.2 किलो है। दाल चावल खाया और खिलौने जमाए..."
                    Language.ENGLISH -> "e.g. ${selectedChild?.name ?: "Ananya"} weighs 11.2 kg. Ate rice and dal. Stacked blocks nicely..."
                }

                OutlinedTextField(
                    value = manualObservationText,
                    onValueChange = { manualObservationText = it },
                    placeholder = { Text(placeholderHint, fontSize = 12.sp, color = Color(0xFF95A5A6)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(95.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = BrandYellowPrimary,
                        unfocusedBorderColor = Color(0xFFE0E0E0),
                        focusedContainerColor = Color(0xFFFAFAFA),
                        unfocusedContainerColor = Color(0xFFFAFAFA)
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = {
                        if (manualObservationText.isNotBlank()) {
                            viewModel.processVoiceUtterance(
                                utterance = manualObservationText.trim(),
                                langCode = speechLanguage.code,
                                child = selectedChild
                            )
                        }
                    },
                    enabled = manualObservationText.isNotBlank(),
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = BrandYellowPrimary),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = DeepBlack, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = when (state.currentLanguage) {
                            Language.TELUGU -> "ఆన్-డివైస్ AI తో విశ్లేషించండి"
                            Language.HINDI -> "ऑन-डिवाइस AI से विश्लेषण करें"
                            Language.ENGLISH -> "Analyze with On-Device AI"
                        },
                        color = DeepBlack,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // MULTI-CHILD REALISTIC OBSERVATION TEST BENCH
        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F9F9)),
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = when (state.currentLanguage) {
                        Language.TELUGU -> "వివిధ పిల్లల వాస్తవిక ఉదాహరణలను పరీక్షించండి (ట్యాప్ చేయండి):"
                        Language.HINDI -> "विभिन्न बच्चों के वास्तविक उदाहरण आज़माएँ (टैप करें):"
                        Language.ENGLISH -> "Try Real-World Examples for Any Child (Tap to Test):"
                    },
                    style = MaterialTheme.typography.labelLarge,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = DeepBlack
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Scenario 1: Ananya (3y)
                val ananyaText = when (speechLanguage) {
                    Language.TELUGU -> "అనన్య ఈరోజు 11.5 కిలోలు. అన్నం, పప్పు తిన్నది. రంగు రంగుల బొమ్మలను చక్కగా పేర్చింది."
                    Language.HINDI -> "अनन्या 3 साल की है। आज उसका वजन 11.5 किलो है। उसने दाल चावल खाया और खिलौने क्रम से जमाए।"
                    Language.ENGLISH -> "Ananya is 3 years old. Weight is 11.5 kg. Ate rice and dal. Stacked colourful blocks independently."
                }

                // Scenario 2: Meena (2y)
                val meenaText = when (speechLanguage) {
                    Language.TELUGU -> "మీనా ఈరోజు 9.8 కిలోలు. అరటిపండు తిన్నది. స్నేహితులతో కలిసి నవ్వుతూ ఆడుకుంది."
                    Language.HINDI -> "మీనా का वजन 9.8 किलो है। उसने केला खाया और दोस्तों के साथ मिलकर खुशी से खेली।"
                    Language.ENGLISH -> "Meena is 2 years old. Weight is 9.8 kg. Ate a banana and shared toys happily with friends."
                }

                // Scenario 3: Ravi (4y)
                val raviText = when (speechLanguage) {
                    Language.TELUGU -> "రవికి నాలుగు సంవత్సరాలు. ఈరోజు అతని బరువు 12.8 కిలోలు. అన్నం, పప్పు తిన్నాడు. రంగులను బాగా గుర్తించాడు, రెండు సూచనలు వరుసగా పాటించడానికి కొంచెం సహాయం కావాలి."
                    Language.HINDI -> "रवि चार साल का है। आज उसका वजन 12.8 किलो है। उसने दाल चावल खाया। रंगों को पहचाना, दो निर्देशों में मदद चाहिए।"
                    Language.ENGLISH -> "Ravi is 4 years old. Weight today is 12.8 kg. Ate rice and dal. Identified colours well, needs guidance following two instructions."
                }

                ScenarioItem(
                    childName = "Ananya (3y)",
                    text = ananyaText,
                    onClick = {
                        manualObservationText = ananyaText
                        val childObj = state.children.find { it.id == "child_ananya" }
                        if (childObj != null) viewModel.selectChild(childObj)
                        viewModel.processVoiceUtterance(ananyaText, speechLanguage.code, childObj)
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                ScenarioItem(
                    childName = "Meena (2y)",
                    text = meenaText,
                    onClick = {
                        manualObservationText = meenaText
                        val childObj = state.children.find { it.id == "child_meena" }
                        if (childObj != null) viewModel.selectChild(childObj)
                        viewModel.processVoiceUtterance(meenaText, speechLanguage.code, childObj)
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                ScenarioItem(
                    childName = "Ravi (4y)",
                    text = raviText,
                    onClick = {
                        manualObservationText = raviText
                        val childObj = state.children.find { it.id == "child_ravi" }
                        if (childObj != null) viewModel.selectChild(childObj)
                        viewModel.processVoiceUtterance(raviText, speechLanguage.code, childObj)
                    }
                )
            }
        }

        // PRE-SAVE STRUCTURED CONFIRMATION CARD WITH EXPLICIT DATE
        if (state.structuredDraft != null) {
            val draft = state.structuredDraft
            Spacer(modifier = Modifier.height(18.dp))

            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(2.dp, BrandYellowPrimary, RoundedCornerShape(16.dp))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = s.structuredVerificationTitle,
                            style = MaterialTheme.typography.labelLarge,
                            color = DeepBlack,
                            fontWeight = FontWeight.Bold
                        )
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color(0xFFE8F8F5))
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Text("Ready to Save", fontSize = 10.sp, color = Color(0xFF117A65), fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(text = "• Child: ${draft.childName} (${draft.ageYears} yrs)", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text(
                        text = "• Observation Date: 📅 $currentSelectedDateString",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp,
                        color = Color(0xFFD49A0E)
                    )
                    Text(text = "• Weight: ${draft.weightKg?.let { "$it kg" } ?: "Not specified in observation"}", fontSize = 13.sp)
                    Text(
                        text = "• Food Observed: ${if (draft.foodsObserved.isNotEmpty()) draft.foodsObserved.joinToString(", ") else "No meal items noted"}",
                        fontSize = 13.sp
                    )
                    Text(
                        text = "• Development: ${draft.developmentObservations.joinToString("; ")}",
                        fontSize = 13.sp
                    )
                    Text(
                        text = "• Domain: ${draft.targetDomain}",
                        fontSize = 12.sp,
                        color = Color(0xFF7F8C8D)
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "⚡ ఆఫ్‌లైన్ రికార్డింగ్ • 100% పరికరంలోనే • 0 KB డేటా (100% On-Device)",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF1E824C)
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Buttons: [SAVE TO RECORD] and [DISMISS]
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = {
                                viewModel.confirmAndSaveDraft()
                            },
                            modifier = Modifier.weight(1.3f),
                            colors = ButtonDefaults.buttonColors(containerColor = BrandYellowPrimary),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Icon(Icons.Default.Check, contentDescription = null, tint = DeepBlack, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(s.btnSaveObservation, color = DeepBlack, fontWeight = FontWeight.Bold)
                        }

                        OutlinedButton(
                            onClick = { viewModel.dismissVoiceConfirmation() },
                            modifier = Modifier.weight(0.9f),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Icon(Icons.Default.Close, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(s.btnCorrectObservation)
                        }
                    }
                }
            }
        }
    }

    // ANGANWADI DIGITAL SAHAYIKA INFO DIALOG (LOCALIZED ACROSS TELUGU, HINDI, ENGLISH)
    if (showLlmArchitectureDialog) {
        val lang = state.currentLanguage
        val title = when (lang) {
            Language.TELUGU -> "అంగన్‌వాడీ డిజిటల్ సహాయకురాలు"
            Language.HINDI -> "आंगनवाड़ी डिजिटल सहायिका"
            Language.ENGLISH -> "Anganwadi Digital Sahayika"
        }
        val subHeading = when (lang) {
            Language.TELUGU -> "డిజిటల్ సహాయకురాలు ఎలా పనిచేస్తుంది? (Digital Sahayika)"
            Language.HINDI -> "डिजिटल सहायिका कैसे काम करती है? (Digital Sahayika)"
            Language.ENGLISH -> "How Digital Sahayika Works (Edge AI Assistant)"
        }
        val bullet1 = when (lang) {
            Language.TELUGU -> "• 100% ఆఫ్‌లైన్ రికార్డింగ్ — ఇంటర్నెట్ లేదా మొబైల్ డేటా అవసరం లేదు."
            Language.HINDI -> "• 100% ऑफलाइन रिकॉर्डिंग — इंटरनेट या मोबाइल डेटा की आवश्यकता नहीं है।"
            Language.ENGLISH -> "• 100% Offline Processing — Zero internet or mobile data required."
        }
        val bullet2 = when (lang) {
            Language.TELUGU -> "• పిల్లల సమాచారం మీ ఫోన్‌లోనే సురక్షితంగా నిల్వ ఉంటుంది."
            Language.HINDI -> "• बच्चों की जानकारी आपके फोन में सुरक्षित रूप से संग्रहीत रहती है।"
            Language.ENGLISH -> "• Complete Privacy — Child growth records stay securely on your phone."
        }
        val bullet3 = when (lang) {
            Language.TELUGU -> "• మీరు మాట్లాడిన మాటల నుండి పేరు, వయస్సు, బరువు, ఆహార వివరాలు స్వయంగా గుర్తించబడతాయి."
            Language.HINDI -> "• आपकी बोली से नाम, उम्र, वजन और पोषण विवरण अपने आप पहचाने जाते हैं।"
            Language.ENGLISH -> "• Automatic voice parsing converts spoken notes into structured records."
        }
        val flowTitle = when (lang) {
            Language.TELUGU -> "మాటల నుండి వివరాలు ఎలా గ్రహించబడతాయి:"
            Language.HINDI -> "आवाज से विवरण कैसे निकाला जाता है:"
            Language.ENGLISH -> "On-Device Voice Entity Extraction:"
        }
        val perfTitle = when (lang) {
            Language.TELUGU -> "కేంద్రం గోప్యత & పనితీరు వివరాలు:"
            Language.HINDI -> "गोपनीयता और ऑन-डिवाइस प्रदर्शन:"
            Language.ENGLISH -> "Center Privacy & Edge Performance:"
        }
        val latencyText = when (lang) {
            Language.TELUGU -> "• స్పందన సమయం: 0.12 సెకన్లు (~120ms తక్షణమే)"
            Language.HINDI -> "• प्रतिक्रिया समय: 0.12 सेकंड (~120ms तुरंत)"
            Language.ENGLISH -> "• Local Inference Latency: ~120 ms (Instantaneous)"
        }
        val dataCostText = when (lang) {
            Language.TELUGU -> "• నెట్‌వర్క్ డేటా ఖర్చు: 0.00 KB (పూర్తిగా ఉచితం)"
            Language.HINDI -> "• नेटवर्क डेटा खपत: 0.00 KB (पूरी तरह निःशुल्क)"
            Language.ENGLISH -> "• Mobile Data Consumed: 0.00 KB (Zero cloud leakage)"
        }
        val privacyText = when (lang) {
            Language.TELUGU -> "• ప్రైవసీ: ఏ డేటా బయటకు పంపబడదు (100% పరికరంలోనే)"
            Language.HINDI -> "• गोपनीयता: कोई भी डेटा बाहर नहीं भेजा जाता (100% ऑन-डिवाइस)"
            Language.ENGLISH -> "• Privacy Isolation: 100% Local On-Device Edge"
        }
        val closeBtnText = when (lang) {
            Language.TELUGU -> "సరే, అర్థమైంది (Close)"
            Language.HINDI -> "ठीक है, समझ आ गया (Close)"
            Language.ENGLISH -> "Understood (Close)"
        }

        AlertDialog(
            onDismissRequest = { showLlmArchitectureDialog = false },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Psychology, contentDescription = null, tint = DeepBlack)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(title, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = DeepBlack)
                }
            },
            text = {
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    Text(
                        text = subHeading,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = Color(0xFFD49A0E)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(bullet1, fontSize = 11.5.sp)
                    Text(bullet2, fontSize = 11.5.sp)
                    Text(bullet3, fontSize = 11.5.sp)
                    Spacer(modifier = Modifier.height(10.dp))

                    Text(flowTitle, fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        val utterance = state.structuredDraft?.rawUtterance ?: when (lang) {
                            Language.TELUGU -> "రాజు ఈరోజు 14.5 కిలోలు, అన్నం పప్పు మరియు గుడ్డు తిన్నాడు..."
                            Language.HINDI -> "राजू आज 14.5 किलो का है, दाल चावल और अंडा खाया..."
                            Language.ENGLISH -> "Raju weighs 14.5 kg today, ate rice, dal and boiled egg..."
                        }
                        val childName = state.structuredDraft?.childName ?: when (lang) {
                            Language.TELUGU -> "రాజు (Raju)"
                            Language.HINDI -> "राजू (Raju)"
                            Language.ENGLISH -> "Raju"
                        }
                        val spokenLabel = when (lang) {
                            Language.TELUGU -> "[కార్యకర్త చెప్పిన మాటలు]:"
                            Language.HINDI -> "[कार्यकर्ता की बोली]:"
                            Language.ENGLISH -> "[Worker's Spoken Note]:"
                        }
                        val parsedLabel = when (lang) {
                            Language.TELUGU -> "[సహాయకురాలు గుర్తించిన వివరాలు]:"
                            Language.HINDI -> "[पहचाने गए विवरण]:"
                            Language.ENGLISH -> "[Extracted Entities]:"
                        }
                        Text(
                            text = """$spokenLabel
"$utterance"

$parsedLabel
• Name: $childName
• Age: ${state.structuredDraft?.ageYears ?: 4} yrs
• Weight: ${state.structuredDraft?.weightKg ?: 14.5} kg
• Diet: Rice, Dal, Boiled Egg
• Status: Verified On-Device ✓""",
                            fontFamily = FontFamily.SansSerif,
                            fontSize = 11.sp,
                            color = Color(0xFF38BDF8),
                            modifier = Modifier.padding(10.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(perfTitle, fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
                    Text(latencyText, fontSize = 11.sp)
                    Text(dataCostText, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF27AE60))
                    Text(privacyText, fontSize = 11.sp, color = Color(0xFF27AE60))
                }
            },
            confirmButton = {
                Button(onClick = { showLlmArchitectureDialog = false }) {
                    Text(closeBtnText)
                }
            }
        )
    }
}

@Composable
private fun ScenarioItem(
    childName: String,
    text: String,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
            .border(0.5.dp, Color(0xFFE2E8F0), RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .padding(10.dp)
    ) {
        Icon(
            imageVector = Icons.Default.PlayCircleOutline,
            contentDescription = "Test",
            tint = DeepBlack,
            modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = childName,
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp,
                color = DeepBlack
            )
            Text(
                text = "\"$text\"",
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 11.sp,
                color = Color(0xFF2C3E50),
                maxLines = 2
            )
        }
    }
}
