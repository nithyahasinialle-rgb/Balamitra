package com.balamitra.app

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.ChildCare
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.balamitra.core.localization.LocalAppStrings
import com.balamitra.core.localization.getStrings
import com.balamitra.ui.components.ConnectTheDotsDialog
import com.balamitra.ui.components.HeaderBar
import com.balamitra.ui.components.ParentExplainDialog
import com.balamitra.ui.components.ReObserveDialog
import com.balamitra.ui.components.WhatChangedDialog
import com.balamitra.ui.components.WhyEvidenceDialog
import com.balamitra.ui.components.WorkerOverrideDialog
import com.balamitra.ui.screens.ChildrenScreen
import com.balamitra.ui.screens.HomeScreen
import com.balamitra.ui.screens.InsightsScreen
import com.balamitra.ui.screens.LoginScreen
import com.balamitra.ui.screens.ObserveScreen
import com.balamitra.ui.state.AppTab
import com.balamitra.ui.state.BalamitraViewModel
import com.balamitra.ui.state.BalamitraViewModelFactory
import com.balamitra.ui.theme.BalamitraTheme

class MainActivity : ComponentActivity() {

    private val viewModel: BalamitraViewModel by viewModels {
        val app = application as BalamitraApplication
        BalamitraViewModelFactory(app.repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val state by viewModel.uiState.collectAsState()
            val strings = getStrings(state.currentLanguage)

            val currentDensity = androidx.compose.ui.platform.LocalDensity.current

            CompositionLocalProvider(
                LocalAppStrings provides strings,
                androidx.compose.ui.platform.LocalDensity provides androidx.compose.ui.unit.Density(
                    density = currentDensity.density,
                    fontScale = currentDensity.fontScale * state.textScale
                )
            ) {
                BalamitraTheme {
                    if (!state.isAuthenticated) {
                        LoginScreen(viewModel = viewModel)
                    } else {
                        BalamitraApp(
                            viewModel = viewModel,
                            onShowToast = { msg ->
                                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BalamitraApp(
    viewModel: BalamitraViewModel,
    onShowToast: (String) -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    val s = LocalAppStrings.current

    LaunchedEffect(state.toastMessage) {
        state.toastMessage?.let { msg ->
            onShowToast(msg)
            viewModel.dismissToast()
        }
    }

    Scaffold(
        topBar = {
            HeaderBar(state = state, viewModel = viewModel)
        },
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                contentColor = MaterialTheme.colorScheme.primary
            ) {
                // Tab 1: TODAY
                NavigationBarItem(
                    selected = state.activeTab == AppTab.TODAY,
                    onClick = { viewModel.setActiveTab(AppTab.TODAY) },
                    icon = { Icon(Icons.Default.Home, contentDescription = s.navToday) },
                    label = {
                        Text(
                            text = s.navToday,
                            fontSize = 11.sp,
                            fontWeight = if (state.activeTab == AppTab.TODAY) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )

                // Tab 2: CHILDREN
                NavigationBarItem(
                    selected = state.activeTab == AppTab.CHILDREN,
                    onClick = { viewModel.setActiveTab(AppTab.CHILDREN) },
                    icon = { Icon(Icons.Default.ChildCare, contentDescription = s.navChildren) },
                    label = {
                        Text(
                            text = s.navChildren,
                            fontSize = 11.sp,
                            fontWeight = if (state.activeTab == AppTab.CHILDREN) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )

                // Tab 3: OBSERVE
                NavigationBarItem(
                    selected = state.activeTab == AppTab.OBSERVE,
                    onClick = { viewModel.setActiveTab(AppTab.OBSERVE) },
                    icon = { Icon(Icons.Default.Mic, contentDescription = s.navObserve) },
                    label = {
                        Text(
                            text = s.navObserve,
                            fontSize = 11.sp,
                            fontWeight = if (state.activeTab == AppTab.OBSERVE) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )

                // Tab 4: INSIGHTS
                NavigationBarItem(
                    selected = state.activeTab == AppTab.INSIGHTS,
                    onClick = { viewModel.setActiveTab(AppTab.INSIGHTS) },
                    icon = { Icon(Icons.Default.Analytics, contentDescription = s.navInsights) },
                    label = {
                        Text(
                            text = s.navInsights,
                            fontSize = 11.sp,
                            fontWeight = if (state.activeTab == AppTab.INSIGHTS) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (state.activeTab) {
                AppTab.TODAY -> HomeScreen(state = state, viewModel = viewModel)
                AppTab.CHILDREN -> ChildrenScreen(state = state, viewModel = viewModel)
                AppTab.OBSERVE -> ObserveScreen(state = state, viewModel = viewModel)
                AppTab.INSIGHTS -> InsightsScreen(state = state, viewModel = viewModel)
            }

            // Hosted Dialogs
            if (state.isWhatChangedDialogOpen) {
                WhatChangedDialog(
                    result = state.whatChangedResult,
                    language = state.currentLanguage,
                    onDismiss = { viewModel.closeWhatChanged() }
                )
            }

            if (state.isConnectTheDotsDialogOpen) {
                ConnectTheDotsDialog(
                    result = state.connectTheDotsResult,
                    language = state.currentLanguage,
                    onDismiss = { viewModel.closeConnectTheDots() }
                )
            }

            if (state.isWhyDialogOpen) {
                WhyEvidenceDialog(
                    whyResult = state.currentRecommendation?.whyEvidence,
                    onDismiss = { viewModel.closeWhyDialog() }
                )
            }

            if (state.isOverrideDialogOpen) {
                WorkerOverrideDialog(
                    onDismiss = { viewModel.closeOverrideDialog() },
                    onConfirmOverride = { reason, note ->
                        viewModel.recordWorkerOverride(reason, note)
                    }
                )
            }

            if (state.isReObserveDialogOpen) {
                ReObserveDialog(
                    childName = state.selectedChild?.name ?: "Child",
                    activityTitle = state.currentRecommendation?.title ?: "Activity",
                    onDismiss = { viewModel.closeReObserveDialog() },
                    onOutcomeSelected = { outcome ->
                        viewModel.submitReObservation(outcome)
                    }
                )
            }

            if (state.isParentExplainDialogOpen) {
                ParentExplainDialog(
                    childName = state.selectedChild?.name ?: "Child",
                    messageText = state.parentExplanationText,
                    homeTip = state.parentHomeTip,
                    currentLanguage = state.parentExplainLanguage,
                    onLanguageSelected = { lang ->
                        viewModel.setParentExplainLanguage(lang)
                    },
                    onDismiss = { viewModel.closeParentExplainDialog() }
                )
            }


            if (state.isAddChildDialogOpen) {
                com.balamitra.ui.components.AddChildDialog(
                    state = state,
                    viewModel = viewModel
                )
            }

            com.balamitra.ui.components.DataUsageMonitorDialog(
                state = state,
                viewModel = viewModel
            )
        }
    }
}
