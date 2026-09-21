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
    val title: String,
    val badge: String,
    val description: String,
    val icon: ImageVector,
    val accentColor: Color,
    val liveActionText: String,
    val onLiveAction: (BalamitraViewModel) -> Unit
)

@Composable
fun EvaluatorTourDialog(
    state: BalamitraUiState,
    viewModel: BalamitraViewModel,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val steps = listOf(
        TourStepData(
            stepNumber = 1,
            title = "Hands-Free Indic Voice Intake",
            badge = "15-Sec Voice vs 5-Min Paper Ledger",
            description = "Anganwadi workers speak naturally in Telugu, Hindi, or English. On-device local NLP extracts child name, age, weight, and meal into structured records with zero cloud dependency.",
            icon = Icons.Default.Mic,
            accentColor = Color(0xFFD4AC0D),
            liveActionText = "Try Voice Intake Tab",
            onLiveAction = {
                it.setActiveTab(AppTab.OBSERVE)
                it.setEvaluatorTourOpen(false)
            }
        ),
        TourStepData(
            stepNumber = 2,
            title = "Longitudinal Growth Intelligence",
            badge = "WHO LMS Formulation Engine",
            description = "Unlike one-off screenings, Balamitra's edge reasoning links patterns across weeks. It spots subtle growth plateauing before malnutrition becomes severe, without alarmist clinical jargon.",
            icon = Icons.Default.Analytics,
            accentColor = Color(0xFF2980B9),
            liveActionText = "Explore Children Profiles",
            onLiveAction = {
                it.setActiveTab(AppTab.CHILDREN)
                it.setEvaluatorTourOpen(false)
            }
        ),
        TourStepData(
            stepNumber = 3,
            title = "Age-Tailored ECD & Empathetic Counseling",
            badge = "10 Activities • 1 to 6 Years",
            description = "Suggests developmental activities using frugal household materials (cups, spoons, leaves) with culturally empathetic counseling scripts that empower rural mothers.",
            icon = Icons.Default.ChildCare,
            accentColor = Color(0xFF8E44AD),
            liveActionText = "View Today's Priority Action",
            onLiveAction = {
                it.setActiveTab(AppTab.TODAY)
                it.setEvaluatorTourOpen(false)
            }
        ),
        TourStepData(
            stepNumber = 4,
            title = "Synchronized Real-Time Attendance",
            badge = "Live Calendar • 15 Indian Children",
            description = "Real-time calendar synchronization matching actual days of the week, with 15 authentic rural Indian children and an interactive attendance marking register.",
            icon = Icons.Default.EventNote,
            accentColor = Color(0xFF27AE60),
            liveActionText = "Open Attendance Register",
            onLiveAction = {
                it.setAttendanceDialogOpen(true)
                it.setEvaluatorTourOpen(false)
            }
        ),
        TourStepData(
            stepNumber = 5,
            title = "National Poshan Tracker Integration",
            badge = "MoWCD / ICDS Compliant Export",
            description = "Eliminates double data entry. Generates official Ministry of Women and Child Development CSV registers pre-populated with calibrated WHO growth categories.",
            icon = Icons.Default.VerifiedUser,
            accentColor = Color(0xFF16A085),
            liveActionText = "View Poshan Tracker CSV",
            onLiveAction = {
                it.setPoshanCsvDialogOpen(true)
                it.setEvaluatorTourOpen(false)
            }
        )
    )

    val currentStep = steps.getOrNull(state.evaluatorTourStep - 1) ?: steps[0]

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
                            text = "Evaluator Tour Mode",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = DeepBlack
                        )
                        Text(
                            text = "Step ${currentStep.stepNumber} of 5",
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
                                    text = currentStep.title,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = DeepBlack
                                )
                                Text(
                                    text = currentStep.badge,
                                    fontSize = 10.5.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = currentStep.accentColor
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = currentStep.description,
                            fontSize = 12.sp,
                            lineHeight = 18.sp,
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
                            Text(currentStep.liveActionText, fontSize = 11.5.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        },
        confirmButton = {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                if (currentStep.stepNumber > 1) {
                    OutlinedButton(
                        onClick = { viewModel.setEvaluatorTourStep(currentStep.stepNumber - 1) },
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null, modifier = Modifier.size(13.dp))
                        Spacer(modifier = Modifier.width(3.dp))
                        Text("Back", fontSize = 11.sp)
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
                        Text("Next Step", fontSize = 11.sp, fontWeight = FontWeight.Bold)
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
                        Text("Done", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = onDismiss,
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Exit Tour", fontSize = 11.sp)
            }
        }
    )
}
