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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.EventNote
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.balamitra.core.localization.LocalAppStrings
import com.balamitra.core.localization.getDayOfWeekLocalized
import com.balamitra.core.model.Language
import com.balamitra.ui.state.BalamitraUiState
import com.balamitra.ui.state.BalamitraViewModel
import com.balamitra.ui.theme.DeepBlack
import java.util.Calendar

@Composable
fun AttendanceDialog(
    state: BalamitraUiState,
    viewModel: BalamitraViewModel,
    modifier: Modifier = Modifier
) {
    val s = LocalAppStrings.current
    val calendar = Calendar.getInstance()
    val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
    val dayName = getDayOfWeekLocalized(dayOfWeek, state.currentLanguage)
    val formattedDate = java.text.SimpleDateFormat("dd MMMM yyyy", java.util.Locale.getDefault()).format(calendar.time)

    val totalChildren = state.children.size
    val presentCount = state.attendanceMap.values.count { it }
    val absentCount = totalChildren - presentCount

    AlertDialog(
        onDismissRequest = { viewModel.setAttendanceDialogOpen(false) },
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.EventNote,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = s.attendanceDialogTitle,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = DeepBlack
                    )
                    Text(
                        text = "$dayName • $formattedDate",
                        fontSize = 11.5.sp,
                        color = Color(0xFF566573)
                    )
                }
            }
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                // Summary & Quick Actions
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0xFFF4F6F7))
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF27AE60))
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "$presentCount " + when (state.currentLanguage) {
                                    Language.TELUGU -> "హాజరు"
                                    Language.HINDI -> "उपस्थित"
                                    Language.ENGLISH -> "Present"
                                },
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF196F3D)
                            )
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFFE74C3C))
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "$absentCount " + when (state.currentLanguage) {
                                    Language.TELUGU -> "గైర్హాజరు"
                                    Language.HINDI -> "अनुपस्थित"
                                    Language.ENGLISH -> "Absent"
                                },
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF922B21)
                            )
                        }
                    }

                    OutlinedButton(
                        onClick = { viewModel.markAllPresent() },
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF1E824C))
                    ) {
                        Icon(Icons.Default.DoneAll, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(s.markAllPresent, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Scrollable List of Children
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 340.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.children) { child ->
                        val isPresent = state.attendanceMap[child.id] ?: true

                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = if (isPresent) Color(0xFFF9FDF9) else Color(0xFFFDF7F7),
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (isPresent) Color(0xFFD5F5E3) else Color(0xFFFADBD8)
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { viewModel.toggleAttendance(child.id) }
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 10.dp, vertical = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(32.dp)
                                            .clip(CircleShape)
                                            .background(
                                                if (isPresent) Color(0xFFE8F8F5) else Color(0xFFFDEDEC)
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = child.name.take(1),
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 14.sp,
                                            color = if (isPresent) Color(0xFF0E6251) else Color(0xFF78281F)
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Column {
                                        Text(
                                            text = "${child.name} (${child.ageYears} yrs)",
                                            fontSize = 12.5.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = DeepBlack
                                        )
                                        Text(
                                            text = "${s.fatherName}: ${child.fatherName}",
                                            fontSize = 10.sp,
                                            color = Color(0xFF7F8C8D)
                                        )
                                    }
                                }

                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(if (isPresent) Color(0xFF27AE60) else Color(0xFFE74C3C))
                                        .clickable { viewModel.toggleAttendance(child.id) }
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = if (isPresent) Icons.Default.Check else Icons.Default.Close,
                                            contentDescription = null,
                                            tint = Color.White,
                                            modifier = Modifier.size(12.dp)
                                        )
                                        Spacer(modifier = Modifier.width(3.dp))
                                        Text(
                                            text = if (isPresent) s.presentLabel else s.absentLabel,
                                            color = Color.White,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { viewModel.setAttendanceDialogOpen(false) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(s.saveAttendance, fontWeight = FontWeight.Bold, fontSize = 12.sp)
            }
        }
    )
}
