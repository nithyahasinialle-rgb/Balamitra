package com.balamitra.ui.components

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import com.balamitra.app.R
import com.balamitra.core.localization.LocalAppStrings
import com.balamitra.core.model.Language
import com.balamitra.ui.state.BalamitraUiState
import com.balamitra.ui.state.BalamitraViewModel
import com.balamitra.ui.theme.BrandYellowPrimary
import com.balamitra.ui.theme.DeepBlack

@Composable
fun HeaderBar(
    state: BalamitraUiState,
    viewModel: BalamitraViewModel,
    modifier: Modifier = Modifier
) {
    val s = LocalAppStrings.current
    var languageMenuOpen by remember { mutableStateOf(false) }

    Surface(
        color = BrandYellowPrimary,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp),
        shadowElevation = 3.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            // Row 1: App Identity (Serif Header in Deep Black) & Accessibility Text Scaler + Language Selector
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.balamitra_logo),
                        contentDescription = "Balamitra Logo",
                        modifier = Modifier
                            .size(42.dp)
                            .clip(RoundedCornerShape(10.dp))
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = s.appName,
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp,
                            color = DeepBlack,
                            letterSpacing = 0.5.sp
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = s.tagline,
                            fontFamily = FontFamily.SansSerif,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF2C3E50),
                            fontSize = 11.5.sp,
                            lineHeight = 15.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Accessibility Font Scaling Button (Aa)
                    val scaleLabel = when (state.textScale) {
                        1.18f -> "Aa+"
                        1.35f -> "Aa++"
                        else -> "Aa"
                    }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(DeepBlack.copy(alpha = 0.12f))
                            .border(BorderStroke(1.dp, DeepBlack.copy(alpha = 0.25f)), RoundedCornerShape(16.dp))
                            .clickable { viewModel.cycleTextScale() }
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.FormatSize,
                                contentDescription = "Adjust Font Size",
                                tint = DeepBlack,
                                modifier = Modifier.size(15.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = scaleLabel,
                                color = DeepBlack,
                                fontFamily = FontFamily.SansSerif,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(6.dp))

                    // Language Selector Pill
                    Box {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .background(DeepBlack.copy(alpha = 0.12f))
                                .border(BorderStroke(1.dp, DeepBlack.copy(alpha = 0.25f)), RoundedCornerShape(16.dp))
                                .clickable { languageMenuOpen = true }
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Translate,
                                contentDescription = "Language",
                                tint = DeepBlack,
                                modifier = Modifier.size(15.dp)
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = state.currentLanguage.nativeName,
                                color = DeepBlack,
                                fontFamily = FontFamily.SansSerif,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.width(2.dp))
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = null,
                                tint = DeepBlack,
                                modifier = Modifier.size(16.dp)
                            )
                        }

                        DropdownMenu(
                            expanded = languageMenuOpen,
                            onDismissRequest = { languageMenuOpen = false }
                        ) {
                            Language.values().forEach { lang ->
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = "${lang.nativeName} (${lang.displayName})",
                                            fontWeight = if (lang == state.currentLanguage) FontWeight.Bold else FontWeight.Normal,
                                            color = if (lang == state.currentLanguage) Color(0xFFD49A0E) else Color.Unspecified
                                        )
                                    },
                                    onClick = {
                                        viewModel.setLanguage(lang)
                                        languageMenuOpen = false
                                    }
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Row 2: Anganwadi Context Ribbon (Worker, Center, Location, Offline Badge & Data Monitor)
            val worker = state.currentWorker
            Surface(
                color = DeepBlack.copy(alpha = 0.08f),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 7.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(DeepBlack.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                tint = DeepBlack,
                                modifier = Modifier.size(15.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "${worker.name} • ${worker.centerCode}",
                                color = DeepBlack,
                                fontSize = 11.5.sp,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = "${worker.mandal}, ${worker.district}",
                                color = Color(0xFF2C3E50),
                                fontSize = 10.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }

                    var liveDateTime by remember {
                        mutableStateOf(
                            java.text.SimpleDateFormat("EEE, dd MMM yyyy • hh:mm:ss a", java.util.Locale.getDefault()).format(java.util.Date())
                        )
                    }
                    androidx.compose.runtime.LaunchedEffect(Unit) {
                        while (true) {
                            liveDateTime = java.text.SimpleDateFormat("EEE, dd MMM yyyy • hh:mm:ss a", java.util.Locale.getDefault()).format(java.util.Date())
                            kotlinx.coroutines.delay(1000)
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    // Live Real-Time Date & Time Clock
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(DeepBlack.copy(alpha = 0.08f))
                            .padding(horizontal = 8.dp, vertical = 3.5.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Schedule,
                            contentDescription = "Live Date and Time",
                            tint = DeepBlack,
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = liveDateTime,
                            color = DeepBlack,
                            fontSize = 10.5.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.SansSerif
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Offline proof chip
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(if (state.isOfflineProofActive) Color(0xFF1E824C) else Color(0xFF2980B9))
                                .clickable { viewModel.toggleOfflineProof() }
                                .padding(horizontal = 7.dp, vertical = 3.5.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .clip(CircleShape)
                                    .background(Color.White)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = if (state.isOfflineProofActive) s.statusOffline else s.statusOnline,
                                color = Color.White,
                                fontSize = 9.5.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        // Data Usage Monitor Pill (Feature Request: 0.00 KB)
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(DeepBlack.copy(alpha = 0.12f))
                                .border(BorderStroke(1.dp, DeepBlack.copy(alpha = 0.2f)), RoundedCornerShape(10.dp))
                                .clickable { viewModel.openDataMonitorDialog() }
                                .padding(horizontal = 7.dp, vertical = 3.5.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.CloudOff,
                                contentDescription = "Data Usage",
                                tint = DeepBlack,
                                modifier = Modifier.size(11.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Data: 0.00 KB",
                                color = DeepBlack,
                                fontSize = 9.5.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}
