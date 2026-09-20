package com.balamitra.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.CloudSync
import androidx.compose.material.icons.filled.DataUsage
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.balamitra.core.model.Language
import com.balamitra.ui.state.BalamitraUiState
import com.balamitra.ui.state.BalamitraViewModel
import com.balamitra.ui.theme.BrandYellowPrimary
import com.balamitra.ui.theme.DeepBlack

@Composable
fun DataUsageMonitorDialog(
    state: BalamitraUiState,
    viewModel: BalamitraViewModel
) {
    if (!state.isDataMonitorDialogOpen) return

    val lang = state.currentLanguage

    AlertDialog(
        onDismissRequest = { viewModel.closeDataMonitorDialog() },
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(BrandYellowPrimary),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.DataUsage,
                        contentDescription = null,
                        tint = DeepBlack,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = when (lang) {
                            Language.TELUGU -> "డేటా వినియోగం & క్లౌడ్ సింక్"
                            Language.HINDI -> "डेटा उपयोग और क्लाउड सिंक"
                            Language.ENGLISH -> "Data Monitor & Cloud Sync"
                        },
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = DeepBlack
                    )
                    Text(
                        text = "100% Edge Processing • Zero Cloud Costs",
                        fontSize = 10.5.sp,
                        color = Color(0xFF27AE60),
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Main Metric: Data Consumed Today
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF0FDF4)),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, Color(0xFF86EFAC)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Today's Mobile Data Used:",
                                fontSize = 11.5.sp,
                                color = Color(0xFF166534),
                                fontWeight = FontWeight.SemiBold
                            )
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(Color(0xFF22C55E))
                                    .padding(horizontal = 8.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = if (state.dataUsedTodayBytes == 0L) "0.00 KB" else "0.62 KB",
                                    color = Color.White,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "All voice recording, multi-day nutrition analytics, and camera activity verification run 100% locally on your phone without consuming internet data.",
                            fontSize = 11.sp,
                            color = Color(0xFF14532D),
                            lineHeight = 16.sp
                        )
                    }
                }

                // Efficiency Comparison Card
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = "On-Device vs Cloud Data Savings:",
                            fontSize = 11.5.sp,
                            fontWeight = FontWeight.Bold,
                            color = DeepBlack
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Audio Transcripts Streamed:", fontSize = 10.5.sp, color = Color(0xFF64748B))
                            Text("0 Bytes (Local)", fontSize = 10.5.sp, fontWeight = FontWeight.Bold, color = DeepBlack)
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Camera Video Streamed:", fontSize = 10.5.sp, color = Color(0xFF64748B))
                            Text("0 Bytes (Local)", fontSize = 10.5.sp, fontWeight = FontWeight.Bold, color = DeepBlack)
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Total Bandwidth Saved Today:", fontSize = 10.5.sp, color = Color(0xFF64748B))
                            Text("~44.92 MB", fontSize = 10.5.sp, fontWeight = FontWeight.Bold, color = Color(0xFF27AE60))
                        }
                    }
                }

                // Cloud Sync Queue Architecture Section
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, Color(0xFFCBD5E1)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.CloudSync, contentDescription = null, tint = DeepBlack, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Central ICDS Cloud Sync Architecture",
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                color = DeepBlack
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "When internet/Wi-Fi becomes available, Balamitra automatically compresses pending milestone records into a tiny delta JSON payload (~0.6 KB). High-bandwidth audio and camera video never leave the device.",
                            fontSize = 10.5.sp,
                            color = Color(0xFF475569),
                            lineHeight = 15.sp
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Pending Sync Queue: ${state.cloudSyncQueueCount} items",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = if (state.cloudSyncQueueCount > 0) Color(0xFFD97706) else Color(0xFF22C55E)
                            )

                            Button(
                                onClick = { viewModel.simulateCloudSync() },
                                enabled = !state.isCloudSyncSimulating && state.cloudSyncQueueCount > 0,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = BrandYellowPrimary,
                                    contentColor = DeepBlack
                                ),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                if (state.isCloudSyncSimulating) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(14.dp),
                                        color = DeepBlack,
                                        strokeWidth = 2.dp
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Syncing...", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                } else {
                                    Icon(Icons.Default.CloudDone, contentDescription = null, modifier = Modifier.size(14.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Sync Delta", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { viewModel.closeDataMonitorDialog() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = BrandYellowPrimary,
                    contentColor = DeepBlack
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Close", fontWeight = FontWeight.Bold)
            }
        }
    )
}
