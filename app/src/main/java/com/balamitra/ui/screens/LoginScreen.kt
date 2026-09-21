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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.HomeWork
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import com.balamitra.app.R
import com.balamitra.core.localization.LocalAppStrings
import com.balamitra.data.model.DefaultWorkerSession
import com.balamitra.ui.state.BalamitraViewModel

@Composable
fun LoginScreen(
    viewModel: BalamitraViewModel,
    modifier: Modifier = Modifier
) {
    val s = LocalAppStrings.current

    var workerId by remember { mutableStateOf(DefaultWorkerSession.defaultWorker.workerId) }
    var centerCode by remember { mutableStateOf(DefaultWorkerSession.defaultWorker.centerCode) }
    var pin by remember { mutableStateOf("1234") }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = Color(0xFFFDFEFE)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Official Balamitra Logo
            Image(
                painter = painterResource(id = R.drawable.balamitra_logo),
                contentDescription = "Balamitra Logo",
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(20.dp))
            )

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = s.appName,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = s.tagline,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 12.sp,
                color = Color(0xFF566573)
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Government / ICDS badge
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
                Text(
                    text = s.loginSubtitle,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Login Card
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = s.loginTitle,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Prominent Sample Teacher Credentials Display Card (Bachupally AWC 1)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xFFFEF9E7))
                            .border(1.dp, Color(0xFFF9E79F), RoundedCornerShape(10.dp))
                            .padding(12.dp)
                    ) {
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.AccountCircle,
                                        contentDescription = null,
                                        tint = Color(0xFFB7950B),
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = s.sampleCredentialsTitle,
                                        fontSize = 11.5.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF7D6608)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = "• ${s.workerIdLabel}: AWW-TG-HYD-108 (Smt. Lakshmi Devi)\n• ${s.centerCodeLabel}: AWC-TG-HYD-BCH-042 (Bachupally AWC 1)\n• ${s.pinLabel}: 1234",
                                fontSize = 11.sp,
                                color = Color(0xFF4A235A),
                                fontWeight = FontWeight.Medium,
                                lineHeight = 17.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    OutlinedTextField(
                        value = workerId,
                        onValueChange = { workerId = it },
                        label = { Text(s.workerIdLabel, fontSize = 12.sp) },
                        leadingIcon = { Icon(Icons.Default.Badge, contentDescription = null, modifier = Modifier.size(18.dp)) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = centerCode,
                        onValueChange = { centerCode = it },
                        label = { Text(s.centerCodeLabel, fontSize = 12.sp) },
                        leadingIcon = { Icon(Icons.Default.HomeWork, contentDescription = null, modifier = Modifier.size(18.dp)) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = pin,
                        onValueChange = { pin = it },
                        label = { Text(s.pinLabel, fontSize = 12.sp) },
                        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, modifier = Modifier.size(18.dp)) },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    Button(
                        onClick = { viewModel.login(workerId, centerCode, pin) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(s.btnLogin, fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedButton(
                        onClick = { viewModel.login("AWW-TG-HYD-108", "AWC-TG-HYD-BCH-042", "1234") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.secondary)
                    ) {
                        Icon(Icons.Default.Bolt, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(s.btnDemoLogin, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Location Geotag Info Card
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFFE8F8F5))
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Location",
                    tint = Color(0xFF117A65),
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "GPS Geotag: 17.5385° N, 78.3610° E (Bachupally, Hyderabad)",
                    fontSize = 11.sp,
                    color = Color(0xFF0E6251),
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}
