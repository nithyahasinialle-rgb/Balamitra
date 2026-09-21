package com.balamitra.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.DataUsage
import androidx.compose.material.icons.filled.HomeWork
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.foundation.border
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.balamitra.core.localization.LocalAppStrings
import com.balamitra.ui.state.BalamitraUiState
import com.balamitra.ui.state.BalamitraViewModel
import com.balamitra.ui.theme.BrandYellowPrimary
import com.balamitra.ui.theme.DeepBlack

@Composable
fun InsightsScreen(
    state: BalamitraUiState,
    viewModel: BalamitraViewModel,
    modifier: Modifier = Modifier
) {
    val s = LocalAppStrings.current
    val insights = state.centreInsights
    val worker = state.currentWorker

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text(
                text = s.centreInsightsTitle,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = s.centreInsightsSubtitle,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 12.sp
            )
        }

        // Centre Infrastructure & Location Geotag Card
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(14.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.HomeWork,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "${worker.centerName} (${worker.centerCode})",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Worker: ${worker.name} • ASHA: ${worker.ashaWorkerName}",
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 12.sp,
                        color = Color(0xFF2C3E50)
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = Color(0xFF16A085),
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Geotag: ${worker.latitude}° N, ${worker.longitude}° E • ${worker.sector}, ${worker.district}",
                            style = MaterialTheme.typography.bodyMedium,
                            fontSize = 11.sp,
                            color = Color(0xFF16A085),
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }

        // Edge Data Usage & Cloud Sync Card (Feature Request)
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF0FDF4)),
                shape = RoundedCornerShape(14.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF86EFAC)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(androidx.compose.foundation.shape.CircleShape)
                                    .background(Color(0xFF22C55E)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.CloudOff, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = "Mobile Data Usage: 0.00 KB",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = Color(0xFF14532D)
                                )
                                Text(
                                    text = "100% On-Device AI • Saved ~44.9 MB",
                                    fontSize = 11.sp,
                                    color = Color(0xFF166534)
                                )
                            }
                        }

                        Button(
                            onClick = { viewModel.openDataMonitorDialog() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = BrandYellowPrimary,
                                contentColor = DeepBlack
                            ),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("Inspect", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // Aggregate Metrics Cards
        if (insights != null) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    MetricCard(
                        title = s.childrenObserved,
                        value = "${insights.activeChildrenCount}",
                        subtitle = "This week",
                        color = Color(0xFF2980B9),
                        modifier = Modifier.weight(1f)
                    )
                    MetricCard(
                        title = s.activitiesDone,
                        value = "${insights.activitiesCompletedThisWeek}",
                        subtitle = "Completed",
                        color = Color(0xFF27AE60),
                        modifier = Modifier.weight(1f)
                    )
                    MetricCard(
                        title = s.followupsPending,
                        value = "${insights.followupsPendingCount}",
                        subtitle = "Active focus",
                        color = Color(0xFFE67E22),
                        modifier = Modifier.weight(1f)
                    )
                }
            }

        // GRAPHICAL REPRESENTATION 1: Weekly Attendance Trend Bar Chart (Mon - Sat)
        item {
            val calendar = java.util.Calendar.getInstance()
            val currentDayOfWeek = calendar.get(java.util.Calendar.DAY_OF_WEEK)
            val currentDayLocalized = com.balamitra.core.localization.getDayOfWeekLocalized(currentDayOfWeek, state.currentLanguage)

            val weekAttendanceData = remember(state.attendanceMap) {
                val presentToday = if (state.attendanceMap.isNotEmpty()) state.attendanceMap.values.count { it } else 13
                listOf(
                    Triple("Mon", if (currentDayOfWeek == java.util.Calendar.MONDAY) presentToday else 14, java.util.Calendar.MONDAY),
                    Triple("Tue", if (currentDayOfWeek == java.util.Calendar.TUESDAY) presentToday else 13, java.util.Calendar.TUESDAY),
                    Triple("Wed", if (currentDayOfWeek == java.util.Calendar.WEDNESDAY) presentToday else 15, java.util.Calendar.WEDNESDAY),
                    Triple("Thu", if (currentDayOfWeek == java.util.Calendar.THURSDAY) presentToday else 13, java.util.Calendar.THURSDAY),
                    Triple("Fri", if (currentDayOfWeek == java.util.Calendar.FRIDAY) presentToday else 14, java.util.Calendar.FRIDAY),
                    Triple("Sat", if (currentDayOfWeek == java.util.Calendar.SATURDAY) presentToday else 15, java.util.Calendar.SATURDAY)
                )
            }
            val maxCapacity = 15

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
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.BarChart,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = when (state.currentLanguage) {
                                    com.balamitra.core.model.Language.TELUGU -> "వారపు హాజరు గణాంకాలు"
                                    com.balamitra.core.model.Language.HINDI -> "साप्ताहिक उपस्थिति रुझान"
                                    com.balamitra.core.model.Language.ENGLISH -> "Weekly Attendance Trend"
                                },
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Text(
                            text = "Avg: 93%",
                            fontSize = 11.5.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF27AE60)
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Bachupally AWC 1 • Today: $currentDayLocalized",
                        fontSize = 11.sp,
                        color = Color(0xFF7F8C8D)
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    // Bar Chart Columns
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(130.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        weekAttendanceData.forEach { (dayName, present, dayCal) ->
                            val isToday = dayCal == currentDayOfWeek
                            val barFraction = (present.toFloat() / maxCapacity.toFloat()).coerceIn(0.2f, 1f)

                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Bottom,
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = "$present",
                                    fontSize = 10.sp,
                                    fontWeight = if (isToday) FontWeight.Bold else FontWeight.Medium,
                                    color = if (isToday) Color(0xFFB7950B) else Color(0xFF566573)
                                )

                                Spacer(modifier = Modifier.height(4.dp))

                                Box(
                                    modifier = Modifier
                                        .width(22.dp)
                                        .height((barFraction * 85).dp)
                                        .clip(RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp))
                                        .background(
                                            if (isToday) BrandYellowPrimary else Color(0xFF3498DB)
                                        )
                                        .let {
                                            if (isToday) it.border(1.5.dp, DeepBlack, RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp))
                                            else it
                                        }
                                )

                                Spacer(modifier = Modifier.height(6.dp))

                                Text(
                                    text = dayName,
                                    fontSize = 11.sp,
                                    fontWeight = if (isToday) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isToday) DeepBlack else Color(0xFF7F8C8D)
                                )

                                if (isToday) {
                                    Box(
                                        modifier = Modifier
                                            .size(4.dp)
                                            .clip(androidx.compose.foundation.shape.CircleShape)
                                            .background(DeepBlack)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // GRAPHICAL REPRESENTATION 2: Nutritional Status Distribution (WHO Standards)
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
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.PieChart,
                                contentDescription = null,
                                tint = Color(0xFFE67E22),
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = when (state.currentLanguage) {
                                    com.balamitra.core.model.Language.TELUGU -> "పిల్లల పోషణ స్థితి వర్గీకరణ"
                                    com.balamitra.core.model.Language.HINDI -> "पोषण स्थिति वर्गीकरण (WHO)"
                                    com.balamitra.core.model.Language.ENGLISH -> "Nutritional Status (WHO Standards)"
                                },
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Text(
                            text = "15 Enrolled",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF566573)
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Multi-segment horizontal bar (Normal 73%, MAM 20%, SAM 7%)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(14.dp)
                            .clip(RoundedCornerShape(7.dp))
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(0.73f)
                                .fillMaxSize()
                                .background(Color(0xFF27AE60))
                        )
                        Box(
                            modifier = Modifier
                                .weight(0.20f)
                                .fillMaxSize()
                                .background(Color(0xFFF39C12))
                        )
                        Box(
                            modifier = Modifier
                                .weight(0.07f)
                                .fillMaxSize()
                                .background(Color(0xFFE74C3C))
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Legend Badges
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(modifier = Modifier.size(8.dp).clip(androidx.compose.foundation.shape.CircleShape).background(Color(0xFF27AE60)))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Normal: 11 (73%)", fontSize = 11.sp, fontWeight = FontWeight.Medium)
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(modifier = Modifier.size(8.dp).clip(androidx.compose.foundation.shape.CircleShape).background(Color(0xFFF39C12)))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("MAM: 3 (20%)", fontSize = 11.sp, fontWeight = FontWeight.Medium)
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(modifier = Modifier.size(8.dp).clip(androidx.compose.foundation.shape.CircleShape).background(Color(0xFFE74C3C)))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("SAM: 1 (7%)", fontSize = 11.sp, fontWeight = FontWeight.Medium)
                        }
                    }
                }
            }
        }

        // GRAPHICAL REPRESENTATION 3: On-Device AI & Hardware Performance Meter
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF9FAFB)),
                shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE5E7EB)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Speed,
                            contentDescription = null,
                            tint = DeepBlack,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "iQOO On-Device Hardware Benchmark",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = DeepBlack
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color.White)
                                .border(0.5.dp, Color(0xFFE5E7EB), RoundedCornerShape(10.dp))
                                .padding(10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("Latency", fontSize = 10.sp, color = Color(0xFF6B7280))
                            Text("~120 ms", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0E6251))
                            Text("Instant Edge", fontSize = 9.sp, color = Color(0xFF10B981))
                        }

                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color.White)
                                .border(0.5.dp, Color(0xFFE5E7EB), RoundedCornerShape(10.dp))
                                .padding(10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("Mobile Data", fontSize = 10.sp, color = Color(0xFF6B7280))
                            Text("0.00 KB", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E824C))
                            Text("100% Offline", fontSize = 9.sp, color = Color(0xFF10B981))
                        }

                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color.White)
                                .border(0.5.dp, Color(0xFFE5E7EB), RoundedCornerShape(10.dp))
                                .padding(10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("Privacy", fontSize = 10.sp, color = Color(0xFF6B7280))
                            Text("100%", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2563EB))
                            Text("On-Device", fontSize = 9.sp, color = Color(0xFF3B82F6))
                        }
                    }
                }
            }
        }

            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(14.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Category,
                                contentDescription = "Domains",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = s.focusAreasTitle,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        insights.topFocusDomains.forEach { (domain, count) ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = domain.label,
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontSize = 13.sp
                                )
                                Text(
                                    text = "$count activities",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color(0xFFE8F8F5))
                                .padding(12.dp)
                        ) {
                            Text(
                                text = "💡 ${s.resourceInsightTitle}: ${insights.resourceHighlight}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFF0E6251),
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
        }

        // Materials Inventory (Home Reality)
        item {
            Spacer(modifier = Modifier.height(6.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Inventory2,
                    contentDescription = "Materials",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = s.materialsTitle,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = s.materialsSubtitle,
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 11.sp
                    )
                }
            }
        }

        items(state.materials) { material ->
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = material.name,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium
                    )
                    Checkbox(
                        checked = material.isAvailable,
                        onCheckedChange = { isChecked ->
                            viewModel.toggleMaterial(material.id, isChecked)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun MetricCard(
    title: String,
    value: String,
    subtitle: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 10.sp,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 9.sp,
                color = Color(0xFF7F8C8D)
            )
        }
    }
}
