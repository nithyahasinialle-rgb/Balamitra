package com.balamitra.ui.components

import android.Manifest
import android.app.Activity
import com.balamitra.domain.ChildEnrollmentVoiceParser
import android.content.Intent
import android.content.pm.PackageManager
import android.speech.RecognizerIntent
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChildCare
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Height
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Male
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.ContextCompat
import com.balamitra.core.model.Language
import com.balamitra.ui.state.BalamitraUiState
import com.balamitra.ui.state.BalamitraViewModel
import com.balamitra.ui.theme.BrandYellowPrimary
import com.balamitra.ui.theme.DeepBlack
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun AddChildDialog(
    state: BalamitraUiState,
    viewModel: BalamitraViewModel
) {
    if (!state.isAddChildDialogOpen) return

    val context = LocalContext.current
    val lang = state.currentLanguage

    // Live Date and Time
    val realTimeFormatted = remember {
        val sdf = SimpleDateFormat("dd MMMM yyyy, hh:mm a", Locale.getDefault())
        sdf.format(Date())
    }

    var name by remember { mutableStateOf("") }
    var ageYearsText by remember { mutableStateOf("3") }
    var gender by remember { mutableStateOf("M") }
    var fatherName by remember { mutableStateOf("") }
    var motherName by remember { mutableStateOf("") }
    var villageWard by remember { mutableStateOf("Bachupally Ward-1") }
    var weightKgText by remember { mutableStateOf("12.5") }
    var heightCmText by remember { mutableStateOf("92.0") }
    var baselineNotes by remember { mutableStateOf("") }
    var recognizedSpokenText by remember { mutableStateOf("") }

    // ICDS Criteria Checkboxes
    var residentInCatchment by remember { mutableStateOf(true) }
    var mcpCardAvailable by remember { mutableStateOf(true) }
    var eligibleForMealRation by remember { mutableStateOf(true) }

    // Comprehensive Voice Parser for Child Enrollment
    fun parseVoiceToEnrollment(spoken: String) {
        val clean = spoken.trim()
        recognizedSpokenText = clean
        baselineNotes = clean

        val parsed = ChildEnrollmentVoiceParser.parse(clean)
        parsed.name?.let { name = it }
        parsed.ageYears?.let { ageYearsText = it.toString() }
        gender = parsed.gender
        parsed.weightKg?.let { weightKgText = it.toString() }
        parsed.heightCm?.let { heightCmText = it.toString() }
        parsed.fatherName?.let { fatherName = it }
        parsed.motherName?.let { motherName = it }
        parsed.villageWard?.let { villageWard = it }

        val displayInfo = buildString {
            append("✓ వివరాలు నింపబడ్డాయి:\n")
            if (name.isNotBlank()) append("పేరు: $name • ")
            append("$ageYearsText సం ($gender)")
            if (weightKgText.isNotBlank()) append(" • $weightKgText kg")
        }
        Toast.makeText(context, displayInfo, Toast.LENGTH_LONG).show()
    }

    val speechLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val matches = result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            val spoken = matches?.firstOrNull()
            if (!spoken.isNullOrBlank()) {
                parseVoiceToEnrollment(spoken)
            } else {
                Toast.makeText(context, "మాటలు స్పష్టంగా వినబడలేదు. మళ్ళీ ప్రయత్నించండి.", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context, "వాయిస్ రికగ్నిషన్ ఆగిపోయింది. కింద ఉన్న 'ఆటో-ఫిల్' వాడండి.", Toast.LENGTH_SHORT).show()
        }
    }

    var hasAudioPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED
        )
    }

    val audioPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasAudioPermission = isGranted
        if (isGranted) {
            val langTag = when (lang) {
                Language.TELUGU -> "te-IN"
                Language.HINDI -> "hi-IN"
                Language.ENGLISH -> "en-IN"
            }
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                putExtra(RecognizerIntent.EXTRA_LANGUAGE, langTag)
                putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, langTag)
                putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE, false)
                putExtra("android.speech.extra.EXTRA_ADDITIONAL_LANGUAGES", arrayOf("te-IN", "hi-IN", "en-IN"))
                putExtra(RecognizerIntent.EXTRA_PROMPT, "పిల్లవాడి పేరు, వయస్సు, బరువు చెప్పండి...")
                putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3)
            }
            try {
                speechLauncher.launch(intent)
            } catch (e: Exception) {
                Toast.makeText(context, "Voice input unavailable: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context, "Microphone permission is required for voice input", Toast.LENGTH_SHORT).show()
        }
    }

    fun triggerVoiceInput() {
        if (!hasAudioPermission) {
            audioPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
        } else {
            val langTag = when (lang) {
                Language.TELUGU -> "te-IN"
                Language.HINDI -> "hi-IN"
                Language.ENGLISH -> "en-IN"
            }
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                putExtra(RecognizerIntent.EXTRA_LANGUAGE, langTag)
                putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, langTag)
                putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE, false)
                putExtra("android.speech.extra.EXTRA_ADDITIONAL_LANGUAGES", arrayOf("te-IN", "hi-IN", "en-IN"))
                putExtra(RecognizerIntent.EXTRA_PROMPT, "పిల్లవాడి పేరు, వయస్సు, బరువు చెప్పండి...")
                putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3)
            }
            try {
                speechLauncher.launch(intent)
            } catch (e: Exception) {
                Toast.makeText(context, "Voice input unavailable: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    Dialog(
        onDismissRequest = { viewModel.closeAddChildDialog() },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
                .imePadding()
                .padding(horizontal = 14.dp, vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.92f)
                    .clip(RoundedCornerShape(20.dp)),
                color = Color.White,
                shadowElevation = 10.dp
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    // 1. PINNED HEADER (Always Visible at Top)
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                            .padding(horizontal = 16.dp, vertical = 12.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(38.dp)
                                        .clip(CircleShape)
                                        .background(BrandYellowPrimary),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(Icons.Default.ChildCare, contentDescription = null, tint = DeepBlack, modifier = Modifier.size(22.dp))
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = when (lang) {
                                            Language.TELUGU -> "కొత్త విద్యార్థి నమోదు"
                                            Language.HINDI -> "नया बच्चा नामांकन"
                                            Language.ENGLISH -> "New Child Enrollment"
                                        },
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = DeepBlack
                                    )
                                    Text(
                                        text = "Bachupally Anganwadi Center",
                                        fontSize = 11.sp,
                                        color = Color(0xFF555555)
                                    )
                                }
                            }

                            IconButton(onClick = { viewModel.closeAddChildDialog() }) {
                                Icon(Icons.Default.Close, contentDescription = "Close", tint = DeepBlack)
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        // Real-Time Date & Timestamp Banner
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFFEFDF5)),
                            shape = RoundedCornerShape(10.dp),
                            border = BorderStroke(1.dp, BrandYellowPrimary.copy(alpha = 0.5f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 12.dp, vertical = 6.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "నమోదు సమయం (Real-Time Timestamp):",
                                    fontSize = 10.5.sp,
                                    color = Color(0xFF555555),
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    text = realTimeFormatted,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = DeepBlack
                                )
                            }
                        }
                    }

                    HorizontalDivider(thickness = 1.dp, color = Color(0xFFE2E8F0))

                    // 2. SCROLLABLE MIDDLE BODY
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .verticalScroll(rememberScrollState())
                            .padding(horizontal = 16.dp, vertical = 12.dp)
                    ) {
                        // Voice-Assisted Enrollment Bar
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F5F9)),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Icon(Icons.Default.Mic, contentDescription = null, tint = DeepBlack, modifier = Modifier.size(18.dp))
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = when (lang) {
                                                Language.TELUGU -> "వాయిస్ ద్వారా నమోదు చేయండి"
                                                Language.HINDI -> "आवाज से बच्चे का विवरण दर्ज करें"
                                                Language.ENGLISH -> "Voice-Assisted Enrollment"
                                            },
                                            fontSize = 11.5.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = DeepBlack
                                        )
                                    }

                                    Button(
                                        onClick = { triggerVoiceInput() },
                                        colors = ButtonDefaults.buttonColors(containerColor = BrandYellowPrimary),
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Icon(Icons.Default.Mic, contentDescription = null, tint = DeepBlack, modifier = Modifier.size(14.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("Speak / మాట్లాడు", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = DeepBlack)
                                    }
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                // Quick Voice 1-Tap Presets (Triggers Real Parser)
                                Text(
                                    text = "త్వరిత నమూనా వాయిస్ వాక్యాలు (Quick Voice Samples - 1 Tap Auto-Fill):",
                                    fontSize = 10.sp,
                                    color = Color(0xFF555555),
                                    fontWeight = FontWeight.Medium
                                )
                                Spacer(modifier = Modifier.height(4.dp))

                                Row(
                                    modifier = Modifier.horizontalScroll(rememberScrollState()),
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    listOf(
                                        Pair("విహాన్ (3y బాలుడు)", "విహాన్, 3 సంవత్సరాలు, బాలుడు, బరువు 12.8 కేజీలు, ఎత్తు 93 సెం.మీ, తండ్రి రాజేష్ రెడ్డి, తల్లి ప్రియ, బచ్చుపల్లి వార్డ్ 4"),
                                        Pair("సాయిశ్రీ (4y బాలిక)", "సాయిశ్రీ, 4 సంవత్సరాలు, బాలిక, బరువు 13.5 కేజీలు, ఎత్తు 98 సెం.మీ, తండ్రి ప్రసాద్, తల్లి లత, బచ్చుపల్లి వార్డ్ 2"),
                                        Pair("ఆరవ్ (3y బాలుడు)", "ఆరవ్, 3 సంవత్సరాలు, బాలుడు, బరువు 13.0 కేజీలు, ఎత్తు 94 సెం.మీ, తండ్రి వెంకట్ రావు, తల్లి అనీత, బచ్చుపల్లి వార్డ్ 1"),
                                        Pair("అన్విత (2y బాలిక)", "అన్విత, 2 సంవత్సరాలు, బాలిక, బరువు 11.2 కేజీలు, ఎత్తు 85 సెం.మీ, తండ్రి మహేష్, తల్లి కవిత, బచ్చుపల్లి వార్డ్ 3")
                                    ).forEach { (label, phrase) ->
                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(6.dp))
                                                .background(Color.White)
                                                .border(BorderStroke(1.dp, Color(0xFFCBD5E1)), RoundedCornerShape(6.dp))
                                                .clickable {
                                                    parseVoiceToEnrollment(phrase)
                                                }
                                                .padding(horizontal = 8.dp, vertical = 5.dp)
                                        ) {
                                            Text("🗣️ $label", fontSize = 10.5.sp, color = DeepBlack, fontWeight = FontWeight.SemiBold)
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                // Spoken / Typed Utterance Field + Auto-Fill Button
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    OutlinedTextField(
                                        value = recognizedSpokenText,
                                        onValueChange = { recognizedSpokenText = it },
                                        placeholder = { Text("ఉదా: రవి 3 ఏళ్ళు బాలుడు 12 కేజీలు తండ్రి సురేష్", fontSize = 10.5.sp, color = Color(0xFF888888)) },
                                        modifier = Modifier.weight(1f),
                                        singleLine = true
                                    )
                                    Button(
                                        onClick = {
                                            if (recognizedSpokenText.isNotBlank()) {
                                                parseVoiceToEnrollment(recognizedSpokenText)
                                            } else {
                                                triggerVoiceInput()
                                            }
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = BrandYellowPrimary),
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = DeepBlack, modifier = Modifier.size(14.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("ఆటో-ఫిల్", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = DeepBlack)
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Child Basic Info Fields
                        OutlinedTextField(
                            value = name,
                            onValueChange = { name = it },
                            label = { Text("పిల్లవాడి పేరు (Child Name)") },
                            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, modifier = Modifier.size(18.dp)) },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            OutlinedTextField(
                                value = ageYearsText,
                                onValueChange = { ageYearsText = it },
                                label = { Text("వయస్సు (Age Yrs)") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier.weight(1f),
                                singleLine = true
                            )

                            // Gender Selector
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(top = 8.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(if (gender == "M") BrandYellowPrimary else Color(0xFFF1F5F9))
                                        .clickable { gender = "M" }
                                        .padding(vertical = 10.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("👦 బాలుడు", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = DeepBlack)
                                }
                                Spacer(modifier = Modifier.width(6.dp))
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(if (gender == "F") BrandYellowPrimary else Color(0xFFF1F5F9))
                                        .clickable { gender = "F" }
                                        .padding(vertical = 10.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("👧 బాలిక", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = DeepBlack)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            OutlinedTextField(
                                value = weightKgText,
                                onValueChange = { weightKgText = it },
                                label = { Text("బరువు (Weight kg)") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                                modifier = Modifier.weight(1f),
                                singleLine = true
                            )
                            OutlinedTextField(
                                value = heightCmText,
                                onValueChange = { heightCmText = it },
                                label = { Text("ఎత్తు (Height cm)") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                                modifier = Modifier.weight(1f),
                                singleLine = true
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedTextField(
                            value = fatherName,
                            onValueChange = { fatherName = it },
                            label = { Text("తండ్రి పేరు (Father's Name)") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedTextField(
                            value = motherName,
                            onValueChange = { motherName = it },
                            label = { Text("తల్లి పేరు (Mother's Name)") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedTextField(
                            value = villageWard,
                            onValueChange = { villageWard = it },
                            label = { Text("ప్రాంతం / నివాసం (Village / Ward)") },
                            leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = null, modifier = Modifier.size(18.dp)) },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        // ICDS Joining Requirements Checklist
                        Text(
                            text = "అంగన్‌వాడీ చేరిక అర్హత ప్రమాణాలు (ICDS Joining Requirements):",
                            fontSize = 11.5.sp,
                            fontWeight = FontWeight.Bold,
                            color = DeepBlack
                        )
                        Spacer(modifier = Modifier.height(6.dp))

                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)),
                            shape = RoundedCornerShape(10.dp),
                            border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Checkbox(checked = residentInCatchment, onCheckedChange = { residentInCatchment = it })
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("1. బచ్చుపల్లి పరిధిలో స్థానిక నివాసం (Local Catchment Resident)", fontSize = 11.sp, color = DeepBlack)
                                }
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Checkbox(checked = mcpCardAvailable, onCheckedChange = { mcpCardAvailable = it })
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("2. మాతా-శిశు సంరక్షణ / టీకాల కార్డు (MCP Card Available)", fontSize = 11.sp, color = DeepBlack)
                                }
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Checkbox(checked = eligibleForMealRation, onCheckedChange = { eligibleForMealRation = it })
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("3. వేడి భోజనం & రేషన్ అర్హత (Eligible for Meal / Ration)", fontSize = 11.sp, color = DeepBlack)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    // 3. PINNED BOTTOM BUTTON BAR (Always Visible at Bottom)
                    HorizontalDivider(thickness = 1.dp, color = Color(0xFFE2E8F0))

                    Surface(
                        color = Color(0xFFF8FAFC),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 12.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Button(
                                onClick = {
                                    val parsedAge = ageYearsText.toIntOrNull() ?: 3
                                    val parsedWeight = weightKgText.toDoubleOrNull()
                                    val parsedHeight = heightCmText.toDoubleOrNull()
                                    val finalName = if (name.isNotBlank()) name else "Child Student"
                                    viewModel.registerChild(
                                        name = finalName,
                                        ageYears = parsedAge,
                                        gender = gender,
                                        fatherName = fatherName,
                                        motherName = motherName,
                                        villageWard = villageWard,
                                        weightKg = parsedWeight,
                                        heightCm = parsedHeight,
                                        notes = baselineNotes
                                    )
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = BrandYellowPrimary,
                                    contentColor = DeepBlack
                                ),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier
                                    .weight(1.3f)
                                    .height(48.dp)
                            ) {
                                Icon(Icons.Default.CheckCircle, contentDescription = null, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "విద్యార్థిని చేర్చు (Enroll)",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Button(
                                onClick = { viewModel.closeAddChildDialog() },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE2E8F0)),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier
                                    .weight(0.7f)
                                    .height(48.dp)
                            ) {
                                Text("రద్దు (Cancel)", color = DeepBlack, fontSize = 12.5.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}
