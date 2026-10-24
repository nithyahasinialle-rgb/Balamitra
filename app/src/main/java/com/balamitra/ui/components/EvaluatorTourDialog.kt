package com.balamitra.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ChildCare
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.EventNote
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.balamitra.ui.state.AppTab
import com.balamitra.ui.state.BalamitraUiState
import com.balamitra.ui.state.BalamitraViewModel
import com.balamitra.ui.theme.BrandYellowPrimary
import com.balamitra.ui.theme.DeepBlack

data class TourStepData(
    val stepNumber: Int,
    val title: (com.balamitra.core.model.Language) -> String,
    val badge: (com.balamitra.core.model.Language) -> String,
    val description: (com.balamitra.core.model.Language) -> String,
    val icon: ImageVector,
    val accentColor: Color,
    val liveActionText: (com.balamitra.core.model.Language) -> String,
    val onLiveAction: (BalamitraViewModel) -> Unit
)

@Composable
fun EvaluatorTourDialog(
    state: BalamitraUiState,
    viewModel: BalamitraViewModel,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val lang = state.currentLanguage

    val steps = listOf(
        TourStepData(
            stepNumber = 1,
            title = { l ->
                when (l) {
                    com.balamitra.core.model.Language.TELUGU -> "హ్యాండ్స్-ఫ్రీ వాయిస్ రికార్డింగ్"
                    com.balamitra.core.model.Language.HINDI -> "हैंड्स-फ्री वॉयस इनटेक"
                    com.balamitra.core.model.Language.ENGLISH -> "Hands-Free Indic Voice Intake"
                }
            },
            badge = { l ->
                when (l) {
                    com.balamitra.core.model.Language.TELUGU -> "15 సెకన్ల వాయిస్ vs 5 నిమిషాల లెడ్జర్"
                    com.balamitra.core.model.Language.HINDI -> "15-सेकंड वॉयस vs 5-मिनट रजिस्टर"
                    com.balamitra.core.model.Language.ENGLISH -> "15-Sec Voice vs 5-Min Paper Ledger"
                }
            },
            description = { l ->
                when (l) {
                    com.balamitra.core.model.Language.TELUGU -> "అంగన్‌వాడీ కార్యకర్తలు తెలుగు, హిందీ లేదా ఇంగ్లీషులో సహజంగా మాట్లాడవచ్చు. ఆన్-డివైస్ NLP పిల్లల పేరు, వయస్సు, బరువు, భోజనాన్ని వెంటనే విశ్లేషిస్తుంది."
                    com.balamitra.core.model.Language.HINDI -> "आंगनवाड़ी कार्यकर्ता स्वाभाविक रूप से हिंदी, तेलुगु या अंग्रेजी में बोल सकती हैं। ऑन-डिवाइस NLP बिना इंटरनेट के नाम, उम्र, वजन और भोजन को रिकॉर्ड करता है।"
                    com.balamitra.core.model.Language.ENGLISH -> "Anganwadi workers speak naturally in Telugu, Hindi, or English. On-device local NLP extracts child name, age, weight, and meal into structured records with zero cloud dependency."
                }
            },
            icon = Icons.Default.Mic,
            accentColor = Color(0xFFD4AC0D),
            liveActionText = { l ->
                when (l) {
                    com.balamitra.core.model.Language.TELUGU -> "వాయిస్ పరిశీలన ట్యాబ్‌ను చూడండి"
                    com.balamitra.core.model.Language.HINDI -> "वॉयस अवलोकन आज़माएं"
                    com.balamitra.core.model.Language.ENGLISH -> "Try Voice Intake Tab"
                }
            },
            onLiveAction = {
                it.setActiveTab(AppTab.OBSERVE)
                it.setEvaluatorTourOpen(false)
            }
        ),
        TourStepData(
            stepNumber = 2,
            title = { l ->
                when (l) {
                    com.balamitra.core.model.Language.TELUGU -> "దీర్ఘకాలిక శారీరక ఎదుగుదల విశ్లేషణ"
                    com.balamitra.core.model.Language.HINDI -> "दीर्घकालिक विकास विश्लेषण"
                    com.balamitra.core.model.Language.ENGLISH -> "Longitudinal Growth Intelligence"
                }
            },
            badge = { l ->
                when (l) {
                    com.balamitra.core.model.Language.TELUGU -> "WHO LMS ప్రమాణాల ఆధారితం"
                    com.balamitra.core.model.Language.HINDI -> "WHO LMS मानक इंजन"
                    com.balamitra.core.model.Language.ENGLISH -> "WHO LMS Formulation Engine"
                }
            },
            description = { l ->
                when (l) {
                    com.balamitra.core.model.Language.TELUGU -> "కేవలం ఒక్కరోజు బరువుతో కాకుండా, వారాల తరబడి మార్పులను గమనిస్తుంది. తీవ్ర పోషకాహార లోపం రాకముందే నివారణ మార్గాలను చూపుతుంది."
                    com.balamitra.core.model.Language.HINDI -> "बालमित्र हफ्तों के पैटर्न को जोड़ता है। कुपोषण गंभीर होने से पहले ही मामूली रुकावटों को पहचान लेता है, बिना किसी डरावनी चिकित्सकीय भाषा के।"
                    com.balamitra.core.model.Language.ENGLISH -> "Unlike one-off screenings, Balamitra's edge reasoning links patterns across weeks. It spots subtle growth plateauing before malnutrition becomes severe, without alarmist clinical jargon."
                }
            },
            icon = Icons.Default.Analytics,
            accentColor = Color(0xFF2980B9),
            liveActionText = { l ->
                when (l) {
                    com.balamitra.core.model.Language.TELUGU -> "పిల్లల ప్రొఫైల్‌లను పరిశీలించండి"
                    com.balamitra.core.model.Language.HINDI -> "बच्चों की प्रोफ़ाइल देखें"
                    com.balamitra.core.model.Language.ENGLISH -> "Explore Children Profiles"
                }
            },
            onLiveAction = {
                it.setActiveTab(AppTab.CHILDREN)
                it.setEvaluatorTourOpen(false)
            }
        ),
        TourStepData(
            stepNumber = 3,
            title = { l ->
                when (l) {
                    com.balamitra.core.model.Language.TELUGU -> "వయస్సుకు తగిన ఆటలు & తల్లులకు కౌన్సెలింగ్"
                    com.balamitra.core.model.Language.HINDI -> "आयु-अनुकूल खेल और परामर्श"
                    com.balamitra.core.model.Language.ENGLISH -> "Age-Tailored ECD & Empathetic Counseling"
                }
            },
            badge = { l ->
                when (l) {
                    com.balamitra.core.model.Language.TELUGU -> "10 వికాస ఆటలు • 1 నుండి 6 సంవత్సరాలు"
                    com.balamitra.core.model.Language.HINDI -> "10 विकासात्मक गतिविधियां • 1 से 6 वर्ष"
                    com.balamitra.core.model.Language.ENGLISH -> "10 Activities • 1 to 6 Years"
                }
            },
            description = { l ->
                when (l) {
                    com.balamitra.core.model.Language.TELUGU -> "ఇంటి వద్ద లభించే కప్పులు, ఆకులు, గులకరాళ్లతో సులభంగా ఆడే ఆటలను సూచిస్తుంది. తల్లులకు ధైర్యం చెప్పే సులభమైన వివరణలు అందిస్తుంది."
                    com.balamitra.core.model.Language.HINDI -> "घर की सरल वस्तुओं (कप, चम्मच, पत्ते) से विकासात्मक गतिविधियां सुझाता है और ग्रामीण माताओं को सशक्त बनाने वाले परामर्श संदेश देता है।"
                    com.balamitra.core.model.Language.ENGLISH -> "Suggests developmental activities using frugal household materials (cups, spoons, leaves) with culturally empathetic counseling scripts that empower rural mothers."
                }
            },
            icon = Icons.Default.ChildCare,
            accentColor = Color(0xFF8E44AD),
            liveActionText = { l ->
                when (l) {
                    com.balamitra.core.model.Language.TELUGU -> "నేటి ప్రాధాన్యత పనిని చూడండి"
                    com.balamitra.core.model.Language.HINDI -> "आज की प्राथमिकता देखें"
                    com.balamitra.core.model.Language.ENGLISH -> "View Today's Priority Action"
                }
            },
            onLiveAction = {
                it.setActiveTab(AppTab.TODAY)
                it.setEvaluatorTourOpen(false)
            }
        ),
        TourStepData(
            stepNumber = 4,
            title = { l ->
                when (l) {
                    com.balamitra.core.model.Language.TELUGU -> "లైవ్ క్యాలెండర్ హాజరు సమకాలీకరణ"
                    com.balamitra.core.model.Language.HINDI -> "लाइव कैलेंडर उपस्थिति ट्रैकिंग"
                    com.balamitra.core.model.Language.ENGLISH -> "Synchronized Real-Time Attendance"
                }
            },
            badge = { l ->
                when (l) {
                    com.balamitra.core.model.Language.TELUGU -> "ఖచ్చితమైన తేదీ • 15 మంది పిల్లలు"
                    com.balamitra.core.model.Language.HINDI -> "वास्तविक दिन • 15 ग्रामीण बच्चे"
                    com.balamitra.core.model.Language.ENGLISH -> "Live Calendar • 15 Indian Children"
                }
            },
            description = { l ->
                when (l) {
                    com.balamitra.core.model.Language.TELUGU -> "నేటి వారం, తేదీకి ఖచ్చితంగా సరిపోయే లైవ్ క్యాలెండర్. 15 మంది పిల్లల హాజరును ఒకే ట్యాప్‌తో సులభంగా నమోదు చేసుకోవచ్చు."
                    com.balamitra.core.model.Language.HINDI -> "सप्ताह के वास्तविक दिन के साथ वास्तविक समय का सिंक, 15 वास्तविक भारतीय बच्चों और इंटरैक्टिव उपस्थिति रजिस्टर के साथ।"
                    com.balamitra.core.model.Language.ENGLISH -> "Real-time calendar synchronization matching actual days of the week, with 15 authentic rural Indian children and an interactive attendance marking register."
                }
            },
            icon = Icons.Default.EventNote,
            accentColor = Color(0xFF27AE60),
            liveActionText = { l ->
                when (l) {
                    com.balamitra.core.model.Language.TELUGU -> "హాజరు పట్టికను తెరవండి"
                    com.balamitra.core.model.Language.HINDI -> "उपस्थिति रजिस्टर खोलें"
                    com.balamitra.core.model.Language.ENGLISH -> "Open Attendance Register"
                }
            },
            onLiveAction = {
                it.setAttendanceDialogOpen(true)
                it.setEvaluatorTourOpen(false)
            }
        ),
        TourStepData(
            stepNumber = 5,
            title = { l ->
                when (l) {
                    com.balamitra.core.model.Language.TELUGU -> "జాతీయ పోషణ్ ట్రాకర్ CSV ఎగుమతి"
                    com.balamitra.core.model.Language.HINDI -> "पोषण ट्रैकर राष्ट्रीय एक्सपोर्ट"
                    com.balamitra.core.model.Language.ENGLISH -> "National Poshan Tracker Integration"
                }
            },
            badge = { l ->
                when (l) {
                    com.balamitra.core.model.Language.TELUGU -> "MoWCD / ICDS అధికారిక రిజిస్టర్"
                    com.balamitra.core.model.Language.HINDI -> "MoWCD / ICDS आधिकारिक प्रारूप"
                    com.balamitra.core.model.Language.ENGLISH -> "MoWCD / ICDS Compliant Export"
                }
            },
            description = { l ->
                when (l) {
                    com.balamitra.core.model.Language.TELUGU -> "రెండుసార్లు డేటా ఎంట్రీ చేసే శ్రమను తొలగిస్తుంది. కేంద్ర ప్రభుత్వం ఆమోదించిన పోషణ్ ట్రాకర్ ఫార్మాట్‌లో CSV ఫైల్‌ను తక్షణమే తయారుచేస్తుంది."
                    com.balamitra.core.model.Language.HINDI -> "दोहरी डेटा प्रविष्टि को समाप्त करता है। आधिकारिक महिला एवं बाल विकास मंत्रालय के प्रारूप में WHO विकास श्रेणियों के साथ CSV बनाता है।"
                    com.balamitra.core.model.Language.ENGLISH -> "Eliminates double data entry. Generates official Ministry of Women and Child Development CSV registers pre-populated with calibrated WHO growth categories."
                }
            },
            icon = Icons.Default.VerifiedUser,
            accentColor = Color(0xFF16A085),
            liveActionText = { l ->
                when (l) {
                    com.balamitra.core.model.Language.TELUGU -> "పోషణ్ ట్రాకర్ CSV ని చూడండి"
                    com.balamitra.core.model.Language.HINDI -> "पोषण ट्रैकर CSV देखें"
                    com.balamitra.core.model.Language.ENGLISH -> "View Poshan Tracker CSV"
                }
            },
            onLiveAction = {
                it.setPoshanCsvDialogOpen(true)
                it.setEvaluatorTourOpen(false)
            }
        )
    )

    val currentStep = steps.getOrNull(state.evaluatorTourStep - 1) ?: steps[0]

    val tourTitle = when (lang) {
        com.balamitra.core.model.Language.TELUGU -> "మూల్యాంకన టూర్ మోడ్"
        com.balamitra.core.model.Language.HINDI -> "मूल्यांकनकर्ता टूर मोड"
        com.balamitra.core.model.Language.ENGLISH -> "Evaluator Tour Mode"
    }
    val stepProgressText = when (lang) {
        com.balamitra.core.model.Language.TELUGU -> "దశ ${currentStep.stepNumber} / 5"
        com.balamitra.core.model.Language.HINDI -> "चरण ${currentStep.stepNumber} / 5"
        com.balamitra.core.model.Language.ENGLISH -> "Step ${currentStep.stepNumber} of 5"
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(BrandYellowPrimary),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = DeepBlack,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = tourTitle,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = DeepBlack
                        )
                        Text(
                            text = stepProgressText,
                            fontSize = 11.sp,
                            color = Color(0xFF566573)
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFF2F4F4))
                        .clickable { onDismiss() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Tour",
                        tint = DeepBlack,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                // Step Progress Indicator Dots
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    steps.forEach { s ->
                        val isSelected = s.stepNumber == currentStep.stepNumber
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 4.dp)
                                .size(if (isSelected) 18.dp else 8.dp, 8.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(if (isSelected) BrandYellowPrimary else Color(0xFFBDC3C7))
                                .let {
                                    if (isSelected) it.border(1.dp, DeepBlack, RoundedCornerShape(4.dp))
                                    else it
                                }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Card with Step Content
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = Color(0xFFFDFEFE),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE5E7EB)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(currentStep.accentColor.copy(alpha = 0.15f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = currentStep.icon,
                                    contentDescription = null,
                                    tint = currentStep.accentColor,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = currentStep.title(lang),
                                    fontSize = 13.5.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = DeepBlack
                                )
                                Text(
                                    text = currentStep.badge(lang),
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = currentStep.accentColor
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = currentStep.description(lang),
                            fontSize = 11.5.sp,
                            lineHeight = 17.sp,
                            color = Color(0xFF2C3E50)
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        Button(
                            onClick = { currentStep.onLiveAction(viewModel) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = currentStep.accentColor,
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(currentStep.liveActionText(lang), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        },
        confirmButton = {
            val backText = when (lang) {
                com.balamitra.core.model.Language.TELUGU -> "వెనుకకు"
                com.balamitra.core.model.Language.HINDI -> "पीछे"
                com.balamitra.core.model.Language.ENGLISH -> "Back"
            }
            val nextText = when (lang) {
                com.balamitra.core.model.Language.TELUGU -> "తదుపరి"
                com.balamitra.core.model.Language.HINDI -> "आगे"
                com.balamitra.core.model.Language.ENGLISH -> "Next Step"
            }
            val doneText = when (lang) {
                com.balamitra.core.model.Language.TELUGU -> "పూర్తయింది"
                com.balamitra.core.model.Language.HINDI -> "पूर्ण"
                com.balamitra.core.model.Language.ENGLISH -> "Done"
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                if (currentStep.stepNumber > 1) {
                    OutlinedButton(
                        onClick = { viewModel.setEvaluatorTourStep(currentStep.stepNumber - 1) },
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null, modifier = Modifier.size(13.dp))
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(backText, fontSize = 11.sp)
                    }
                }

                if (currentStep.stepNumber < steps.size) {
                    Button(
                        onClick = { viewModel.setEvaluatorTourStep(currentStep.stepNumber + 1) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = BrandYellowPrimary,
                            contentColor = DeepBlack
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(nextText, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.width(3.dp))
                        Icon(Icons.Default.ArrowForward, contentDescription = null, modifier = Modifier.size(13.dp))
                    }
                } else {
                    Button(
                        onClick = onDismiss,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF27AE60),
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(13.dp))
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(doneText, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        },
        dismissButton = {
            val exitText = when (lang) {
                com.balamitra.core.model.Language.TELUGU -> "నిష్క్రమించు"
                com.balamitra.core.model.Language.HINDI -> "बंद करें"
                com.balamitra.core.model.Language.ENGLISH -> "Exit Tour"
            }
            OutlinedButton(
                onClick = onDismiss,
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(exitText, fontSize = 11.sp)
            }
        }
    )
}
