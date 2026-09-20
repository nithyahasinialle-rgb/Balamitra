package com.balamitra.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.CompareArrows
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.balamitra.core.localization.LocalAppStrings
import com.balamitra.core.model.GrowthStatus
import com.balamitra.core.model.Language
import com.balamitra.core.model.OverrideReason
import com.balamitra.data.local.ChildEntity
import com.balamitra.ui.state.BalamitraUiState
import com.balamitra.ui.state.BalamitraViewModel
import com.balamitra.ui.theme.BrandYellowPrimary
import com.balamitra.ui.theme.DeepBlack
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ChildStoryScreen(
    child: ChildEntity,
    state: BalamitraUiState,
    viewModel: BalamitraViewModel,
    modifier: Modifier = Modifier
) {
    val s = LocalAppStrings.current
    val rec = state.currentRecommendation
    val fullDateFormat = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Child Profile Header Card
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                                .background(BrandYellowPrimary.copy(alpha = 0.25f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = child.name.take(1),
                                style = MaterialTheme.typography.headlineMedium,
                                color = DeepBlack,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.width(14.dp))
                        Column {
                            Text(
                                text = child.name,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = s.ageGenderNative(child.ageYears, child.gender, child.preferredLanguage.uppercase()),
                                style = MaterialTheme.typography.bodyMedium,
                                fontSize = 12.sp,
                                color = Color(0xFF566573)
                            )
                            Text(
                                text = "${s.fatherName}: ${child.fatherName} • ${s.motherName}: ${child.motherName}",
                                style = MaterialTheme.typography.bodyMedium,
                                fontSize = 11.sp,
                                color = Color(0xFF7F8C8D)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // 3 Status Indicators: Growth, Nutrition, Development
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        val latestGrowth = state.growthHistory.firstOrNull()
                        StatusBadge(
                            label = s.childGrowth,
                            status = when (latestGrowth?.trajectoryStatus) {
                                GrowthStatus.INCREASING -> s.statusIncreasing
                                GrowthStatus.DECREASING -> s.statusDecreasing
                                else -> s.statusStable
                            },
                            color = Color(0xFF27AE60)
                        )

                        StatusBadge(
                            label = s.childNutrition,
                            status = s.statusStable,
                            color = Color(0xFF27AE60)
                        )

                        val needsDev = state.developmentHistory.any { it.needsFollowUp }
                        StatusBadge(
                            label = s.childDevelopment,
                            status = if (needsDev) s.statusNeedsReview else s.statusStable,
                            color = if (needsDev) Color(0xFFE67E22) else Color(0xFF27AE60)
                        )
                    }
                }
            }
        }

        // MULTI-DAY LONGITUDINAL NUTRITION INTELLIGENCE CARD
        if (state.multiDayNutritionResult != null) {
            val nut = state.multiDayNutritionResult
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.5.dp, BrandYellowPrimary.copy(alpha = 0.8f), RoundedCornerShape(16.dp))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Restaurant,
                                    contentDescription = null,
                                    tint = Color(0xFFD49A0E),
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = when (state.currentLanguage) {
                                        Language.TELUGU -> "5-రోజుల పోషకాహార విశ్లేషణ"
                                        Language.HINDI -> "5-दिवसीय पोषण विश्लेषण व सुझाव"
                                        Language.ENGLISH -> "Multi-Day Nutrition Intelligence"
                                    },
                                    style = MaterialTheme.typography.labelLarge,
                                    color = DeepBlack,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(BrandYellowPrimary)
                                    .padding(horizontal = 8.dp, vertical = 3.dp)
                            ) {
                                Text(
                                    text = "DDS: ${nut.dietaryDiversityScore}/5",
                                    fontSize = 10.5.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = DeepBlack
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = nut.diversityRating,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFFB7950B)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // 5-Day Strip
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            items(nut.daysStrip) { day ->
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(Color(0xFFFBF9F4))
                                        .border(0.5.dp, Color(0xFFE2E8F0), RoundedCornerShape(8.dp))
                                        .padding(horizontal = 8.dp, vertical = 6.dp)
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text(text = day.dayLabel, fontSize = 9.sp, fontWeight = FontWeight.Bold, color = DeepBlack)
                                        Text(
                                            text = day.foods.joinToString(", ").take(16),
                                            fontSize = 9.sp,
                                            color = Color(0xFF4A5568)
                                        )
                                        Row(modifier = Modifier.padding(top = 2.dp)) {
                                            if (day.hasProtein) Text("🍗", fontSize = 8.sp)
                                            if (day.hasVegetables) Text("🥬", fontSize = 8.sp)
                                            if (day.hasFruits) Text("🍌", fontSize = 8.sp)
                                        }
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // AI Observation & Suggestion
                        Text(
                            text = nut.mainObservation,
                            style = MaterialTheme.typography.bodyMedium,
                            fontSize = 12.sp,
                            color = Color(0xFF2D3748)
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFBEB)),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text(
                                    text = when (state.currentLanguage) {
                                        Language.TELUGU -> "💡 కార్యకర్తకు కార్యాచరణ సూచన:"
                                        Language.HINDI -> "💡 कार्यकर्ता के लिए पोषण सुझाव:"
                                        Language.ENGLISH -> "💡 Actionable Nutrition Guidance:"
                                    },
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF92400E)
                                )
                                Spacer(modifier = Modifier.height(3.dp))
                                Text(
                                    text = nut.actionableSuggestion,
                                    fontSize = 11.5.sp,
                                    color = Color(0xFF78350F),
                                    lineHeight = 16.sp
                                )
                            }
                        }
                    }
                }
            }
        }

        // HERO BUTTONS ROW: "WHAT CHANGED?" + "CONNECT THE DOTS"
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Button 1: WHAT CHANGED?
                Button(
                    onClick = { viewModel.openWhatChanged() },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = BrandYellowPrimary),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.CompareArrows,
                        contentDescription = "What Changed",
                        tint = DeepBlack,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = s.btnWhatChanged,
                        color = DeepBlack,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Button 2: CONNECT THE DOTS
                Button(
                    onClick = { viewModel.openConnectTheDots() },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = DeepBlack),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = "Connect Dots",
                        tint = BrandYellowPrimary,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = s.btnConnectDots,
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // SUGGESTED NEXT ACTION CARD with WHY?, CAMERA VERIFIER, and WORKER OVERRIDE
        if (rec != null) {
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = s.suggestedActionTitle,
                                style = MaterialTheme.typography.labelLarge,
                                color = Color(0xFFD49A0E),
                                fontWeight = FontWeight.Bold
                            )

                            // WHY? Button
                            OutlinedButton(
                                onClick = { viewModel.openWhyDialog() },
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.HelpOutline,
                                    contentDescription = "Why",
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(s.btnWhyThis, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = rec.title,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "Duration: ${rec.durationMinutes} mins • Materials: ${rec.requiredMaterials.joinToString(", ")}",
                            style = MaterialTheme.typography.bodyMedium,
                            fontSize = 12.sp,
                            color = Color(0xFF27AE60),
                            fontWeight = FontWeight.Medium
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = s.howToConduct,
                            style = MaterialTheme.typography.labelLarge,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        rec.steps.forEachIndexed { idx, step ->
                            Text(
                                text = "${idx + 1}. $step",
                                style = MaterialTheme.typography.bodyMedium,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(vertical = 2.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))


                        // Worker Authority Controls: USE THIS / CHANGE / SKIP
                        Text(
                            text = s.workerDecisionTitle,
                            style = MaterialTheme.typography.labelLarge,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF7F8C8D)
                        )
                        Spacer(modifier = Modifier.height(6.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = { viewModel.openReObserveDialog() },
                                modifier = Modifier.weight(1.3f),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF27AE60)),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(s.btnDoActivity, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }

                            OutlinedButton(
                                onClick = { viewModel.openOverrideDialog() },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Icon(Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(s.btnChange, fontSize = 11.sp)
                            }

                            OutlinedButton(
                                onClick = { viewModel.recordWorkerOverride(OverrideReason.NOT_SUITABLE_TODAY, "Skipped by worker") },
                                modifier = Modifier.weight(0.8f),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(s.btnSkip, fontSize = 11.sp)
                            }
                        }
                    }
                }
            }
        }

        // EXPLAIN TO PARENT BUTTON
        item {
            Button(
                onClick = { viewModel.openParentExplainDialog() },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = DeepBlack),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.RecordVoiceOver,
                    contentDescription = "Explain to parent",
                    tint = BrandYellowPrimary,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = s.btnExplainParent,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
            }
        }

        // LONGITUDINAL OBSERVATION TIMELINE WITH EXPLICIT DATES
        item {
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = s.timelineTitle,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = DeepBlack
            )
            Text(
                text = s.timelineSubtitle,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 11.sp
            )
        }

        items(state.developmentHistory) { dev ->
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "📅 ${fullDateFormat.format(Date(dev.timestampEpochMs))}",
                            style = MaterialTheme.typography.labelLarge,
                            color = Color(0xFFD49A0E),
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.5.sp
                        )

                        if (dev.needsFollowUp) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(Color(0xFFFEF9E7))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(s.areaWorthObserving, color = Color(0xFFB7950B), fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = dev.observationText,
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 13.sp,
                        color = Color(0xFF2C3E50)
                    )

                    if (!dev.rawWorkerSpeech.isNullOrBlank()) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Spoken: \"${dev.rawWorkerSpeech}\"",
                            style = MaterialTheme.typography.bodyMedium,
                            fontSize = 11.sp,
                            color = Color(0xFF7F8C8D)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun StatusBadge(
    label: String,
    status: String,
    color: Color
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(color.copy(alpha = 0.12f))
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = label,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )
            Text(
                text = status,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = color
            )
        }
    }
}
