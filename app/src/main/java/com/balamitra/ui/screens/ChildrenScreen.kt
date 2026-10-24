package com.balamitra.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.balamitra.core.localization.LocalAppStrings
import com.balamitra.core.model.Language
import com.balamitra.data.local.ChildEntity
import com.balamitra.ui.state.BalamitraUiState
import com.balamitra.ui.state.BalamitraViewModel
import com.balamitra.ui.theme.BrandYellowPrimary
import com.balamitra.ui.theme.DeepBlack
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

enum class ChildRegisterFilter {
    ALL,
    THR_6M_3Y,
    HCM_3Y_6Y,
    SPECIAL_NEEDS
}

@Composable
fun ChildrenScreen(
    state: BalamitraUiState,
    viewModel: BalamitraViewModel,
    modifier: Modifier = Modifier
) {
    val s = LocalAppStrings.current
    val lang = state.currentLanguage
    var viewingStoryOfChild by remember { mutableStateOf<ChildEntity?>(state.selectedChild) }
    var searchQuery by remember { mutableStateOf("") }
    var activeFilter by remember { mutableStateOf(ChildRegisterFilter.ALL) }
    val enrollmentDateFormat = remember { SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH) }

    androidx.compose.runtime.LaunchedEffect(state.selectedChild) {
        if (state.selectedChild != null) {
            viewingStoryOfChild = state.selectedChild
        }
    }

    if (viewingStoryOfChild != null) {
        Column(modifier = modifier.fillMaxSize()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(horizontal = 8.dp, vertical = 6.dp)
            ) {
                IconButton(onClick = { viewingStoryOfChild = null }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                Column {
                    Text(
                        text = when (lang) {
                            Language.TELUGU -> "అంగన్‌వాడీ విద్యార్థి ప్రొఫైల్"
                            Language.HINDI -> "आंगनवाड़ी विद्यार्थी प्रोफ़ाइल"
                            Language.ENGLISH -> "Anganwadi Student Profile"
                        },
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = when (lang) {
                            Language.TELUGU -> "నమోదు రిజిస్టర్ 1 & పోషణ్ ట్రాకర్ రికార్డు"
                            Language.HINDI -> "प्रवेश रजिस्टर 1 एवं पोषण ट्रैकर रिकॉर्ड"
                            Language.ENGLISH -> "ICDS Register 1 & Poshan Tracker Record"
                        },
                        fontSize = 11.sp,
                        color = Color(0xFF7F8C8D)
                    )
                }
            }
            ChildStoryScreen(
                child = viewingStoryOfChild!!,
                state = state,
                viewModel = viewModel
            )
        }
    } else {
        // Filter and Search logic
        val filteredChildren = remember(state.children, searchQuery, activeFilter) {
            state.children.filter { child ->
                val passesFilter = when (activeFilter) {
                    ChildRegisterFilter.ALL -> true
                    ChildRegisterFilter.THR_6M_3Y -> child.ageYears < 3
                    ChildRegisterFilter.HCM_3Y_6Y -> child.ageYears >= 3
                    ChildRegisterFilter.SPECIAL_NEEDS -> !child.specialNeedsTitle.isNullOrBlank()
                }
                if (!passesFilter) return@filter false

                if (searchQuery.isBlank()) return@filter true

                val q = searchQuery.trim().lowercase()
                child.name.lowercase().contains(q) ||
                child.fatherName.lowercase().contains(q) ||
                child.motherName.lowercase().contains(q) ||
                child.enrollmentId.lowercase().contains(q) ||
                child.villageWard.lowercase().contains(q) ||
                (child.specialNeedsTitle?.lowercase()?.contains(q) == true) ||
                child.aadhaarLast4.contains(q)
            }
        }

        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 14.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Header Row: Register Title & "+ Add Child"
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = when (lang) {
                                Language.TELUGU -> "అంగన్‌వాడీ పిల్లల రిజిస్టర్"
                                Language.HINDI -> "आंगनवाड़ी बाल पंजीयन रजिस्टर"
                                Language.ENGLISH -> "Anganwadi Master Register"
                            },
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = when (lang) {
                                Language.TELUGU -> "ఐసీడీఎస్ రిజిస్టర్ 1 & 4 (కుటుంబ సర్వే & హాజరు)"
                                Language.HINDI -> "ICDS रजिस्टर 1 व 4 (पारिवारिक सर्वेक्षण व उपस्थिति)"
                                Language.ENGLISH -> "ICDS Register 1 & 4 (Family Survey & Enrollment)"
                            },
                            style = MaterialTheme.typography.bodyMedium,
                            fontSize = 11.5.sp,
                            color = Color(0xFF7F8C8D)
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = { viewModel.openAddChildDialog() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = BrandYellowPrimary,
                            contentColor = DeepBlack
                        ),
                        shape = RoundedCornerShape(10.dp),
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.PersonAdd,
                            contentDescription = "Add Child",
                            tint = DeepBlack,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = when (lang) {
                                Language.TELUGU -> "+ కొత్త నమోదు"
                                Language.HINDI -> "+ नया पंजीयन"
                                Language.ENGLISH -> "+ Add Child"
                            },
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = DeepBlack
                        )
                    }
                }
            }

            // Real Anganwadi Center Master Record Banner
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF9FBF7)),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.5.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Color(0xFF27AE60).copy(alpha = 0.35f), RoundedCornerShape(12.dp))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0xFFD5F5E3)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("🏛️", fontSize = 16.sp)
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = when (lang) {
                                    Language.TELUGU -> "బాచుపల్లి-1 అంగన్‌వాడీ కేంద్రం (కోడ్: 3601050201)"
                                    Language.HINDI -> "बाचुपल्ली-1 आंगनवाड़ी केंद्र (कोड: 3601050201)"
                                    Language.ENGLISH -> "Bachupally-1 AWC Centre (Code: 3601050201)"
                                },
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1E8449)
                            )
                            Text(
                                text = when (lang) {
                                    Language.TELUGU -> "మేడ్చల్ సెక్టార్ • కుత్బుల్లాపూర్ ప్రాజెక్ట్ • మొత్తం నమోదు: ${state.children.size} మంది"
                                    Language.HINDI -> "मेडचल सेक्टर • कुथबुल्लापुर प्रोजेक्ट • कुल पंजीकृत: ${state.children.size} बच्चे"
                                    Language.ENGLISH -> "Medchal Sector • Quthbullapur Project • Enrolled: ${state.children.size} children"
                                },
                                fontSize = 10.5.sp,
                                color = Color(0xFF2C3E50)
                            )
                        }
                    }
                }
            }

            // Interactive Search Option for Children
            item {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = Color(0xFF7F8C8D),
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { searchQuery = "" }) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "Clear",
                                    tint = Color(0xFF7F8C8D),
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    },
                    placeholder = {
                        Text(
                            text = when (lang) {
                                Language.TELUGU -> "పేరు, ఐడీ, తల్లిదండ్రులు, వార్డ్ ద్వారా వెతకండి..."
                                Language.HINDI -> "नाम, आईडी, माता-पिता या वार्ड से खोजें..."
                                Language.ENGLISH -> "Search by name, ID, parents, ward..."
                            },
                            fontSize = 12.sp,
                            color = Color(0xFF95A5A6)
                        )
                    },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = Color(0xFFBDC3C7)
                    )
                )
            }

            // Real AWC Statutory Filter Chips
            item {
                val thrCount = state.children.count { it.ageYears < 3 }
                val hcmCount = state.children.count { it.ageYears >= 3 }
                val specialCount = state.children.count { !it.specialNeedsTitle.isNullOrBlank() }

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    item {
                        FilterChip(
                            selected = activeFilter == ChildRegisterFilter.ALL,
                            onClick = { activeFilter = ChildRegisterFilter.ALL },
                            label = {
                                Text(
                                    text = when (lang) {
                                        Language.TELUGU -> "మొత్తం (${state.children.size})"
                                        Language.HINDI -> "सभी (${state.children.size})"
                                        Language.ENGLISH -> "All (${state.children.size})"
                                    },
                                    fontSize = 11.sp,
                                    fontWeight = if (activeFilter == ChildRegisterFilter.ALL) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = BrandYellowPrimary,
                                selectedLabelColor = DeepBlack
                            )
                        )
                    }

                    item {
                        FilterChip(
                            selected = activeFilter == ChildRegisterFilter.THR_6M_3Y,
                            onClick = { activeFilter = ChildRegisterFilter.THR_6M_3Y },
                            label = {
                                Text(
                                    text = when (lang) {
                                        Language.TELUGU -> "📦 6నెలలు-3సం: రేషన్ ($thrCount)"
                                        Language.HINDI -> "📦 6माह-3वर्ष: राशन ($thrCount)"
                                        Language.ENGLISH -> "📦 6m-3y: THR ($thrCount)"
                                    },
                                    fontSize = 11.sp,
                                    fontWeight = if (activeFilter == ChildRegisterFilter.THR_6M_3Y) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Color(0xFFD4E6F1),
                                selectedLabelColor = Color(0xFF1B4F72)
                            )
                        )
                    }

                    item {
                        FilterChip(
                            selected = activeFilter == ChildRegisterFilter.HCM_3Y_6Y,
                            onClick = { activeFilter = ChildRegisterFilter.HCM_3Y_6Y },
                            label = {
                                Text(
                                    text = when (lang) {
                                        Language.TELUGU -> "🍲 3-6సం: భోజనం & పూర్వ విద్య ($hcmCount)"
                                        Language.HINDI -> "🍲 3-6वर्ष: भोजन व शिक्षा ($hcmCount)"
                                        Language.ENGLISH -> "🍲 3-6y: HCM ($hcmCount)"
                                    },
                                    fontSize = 11.sp,
                                    fontWeight = if (activeFilter == ChildRegisterFilter.HCM_3Y_6Y) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Color(0xFFD5F5E3),
                                selectedLabelColor = Color(0xFF196F3D)
                            )
                        )
                    }

                    item {
                        FilterChip(
                            selected = activeFilter == ChildRegisterFilter.SPECIAL_NEEDS,
                            onClick = { activeFilter = ChildRegisterFilter.SPECIAL_NEEDS },
                            label = {
                                Text(
                                    text = when (lang) {
                                        Language.TELUGU -> "🌟 ప్రత్యేక శ్రద్ధ ($specialCount)"
                                        Language.HINDI -> "🌟 विशेष आवश्यकता ($specialCount)"
                                        Language.ENGLISH -> "🌟 Special Care ($specialCount)"
                                    },
                                    fontSize = 11.sp,
                                    fontWeight = if (activeFilter == ChildRegisterFilter.SPECIAL_NEEDS) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Color(0xFFE8F8F5),
                                selectedLabelColor = Color(0xFF117A65)
                            )
                        )
                    }
                }
            }

            // Results summary / count
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = when (lang) {
                            Language.TELUGU -> "రిజిస్టర్ రికార్డులు: ${filteredChildren.size} పిల్లలు"
                            Language.HINDI -> "पंजीकृत रिकॉर्ड: ${filteredChildren.size} बच्चे"
                            Language.ENGLISH -> "Active Records: ${filteredChildren.size} children"
                        },
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF566573)
                    )

                    if (searchQuery.isNotBlank() || activeFilter != ChildRegisterFilter.ALL) {
                        Text(
                            text = when (lang) {
                                Language.TELUGU -> "ఫిల్టర్లు క్లియర్ చేయి"
                                Language.HINDI -> "फ़िल्टर हटाएं"
                                Language.ENGLISH -> "Clear Filters"
                            },
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.clickable {
                                searchQuery = ""
                                activeFilter = ChildRegisterFilter.ALL
                            }
                        )
                    }
                }
            }

            // Empty state if search finds no children
            if (filteredChildren.isEmpty()) {
                item {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 20.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("🔍", fontSize = 32.sp)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = when (lang) {
                                    Language.TELUGU -> "శోధన ఫలితాలు లేవు"
                                    Language.HINDI -> "कोई परिणाम नहीं मिला"
                                    Language.ENGLISH -> "No Records Found"
                                },
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = when (lang) {
                                    Language.TELUGU -> "\"$searchQuery\" సరిపోయే రికార్డు లేదు. వేరే పేరుతో వెతకండి."
                                    Language.HINDI -> "\"$searchQuery\" से मेल खाता कोई रिकॉर्ड नहीं मिला।"
                                    Language.ENGLISH -> "No child matched \"$searchQuery\". Try checking the spelling."
                                },
                                fontSize = 11.5.sp,
                                color = Color(0xFF7F8C8D)
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Button(
                                onClick = {
                                    searchQuery = ""
                                    activeFilter = ChildRegisterFilter.ALL
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = BrandYellowPrimary)
                            ) {
                                Text("మొత్తం చూడండి (View All)", color = DeepBlack, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }

            // Real Anganwadi Register Card List
            itemsIndexed(filteredChildren) { index, child ->
                val enrolledDateFormatted = enrollmentDateFormat.format(Date(child.createdAtEpochMs))
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(14.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            viewModel.selectChild(child)
                            viewingStoryOfChild = child
                        }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Avatar
                            Box(
                                modifier = Modifier
                                    .size(46.dp)
                                    .clip(CircleShape)
                                    .background(
                                        if (child.gender == "F") Color(0xFFFDEDEC) else Color(0xFFEBF5FB)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = child.name.take(1),
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = if (child.gender == "F") Color(0xFFC0392B) else Color(0xFF2980B9)
                                )
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            // Main Details
                            Column(modifier = Modifier.weight(1f)) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = child.name,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = DeepBlack
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))

                                    // Age & Gender pill
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(6.dp))
                                            .background(Color(0xFFF4F6F6))
                                            .padding(horizontal = 6.dp, vertical = 2.dp)
                                    ) {
                                        Text(
                                            text = s.ageGenderNative(child.ageYears, child.gender, child.preferredLanguage.uppercase()),
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = Color(0xFF566573)
                                        )
                                    }

                                    Spacer(modifier = Modifier.width(4.dp))

                                    // SNP Entitlement Pill (THR vs HCM)
                                    if (child.ageYears < 3) {
                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(6.dp))
                                                .background(Color(0xFFEBF5FB))
                                                .padding(horizontal = 5.dp, vertical = 2.dp)
                                        ) {
                                            Text(
                                                text = "📦 THR",
                                                fontSize = 9.5.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color(0xFF2980B9)
                                            )
                                        }
                                    } else {
                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(6.dp))
                                                .background(Color(0xFFE8F8F5))
                                                .padding(horizontal = 5.dp, vertical = 2.dp)
                                        ) {
                                            Text(
                                                text = "🍲 HCM",
                                                fontSize = 9.5.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color(0xFF1E8449)
                                            )
                                        }
                                    }
                                }

                                // Special Needs Badge if present
                                if (!child.specialNeedsTitle.isNullOrBlank()) {
                                    Spacer(modifier = Modifier.height(3.dp))
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(6.dp))
                                            .background(Color(0xFFFEF9E7))
                                            .border(0.5.dp, Color(0xFFF1C40F), RoundedCornerShape(6.dp))
                                            .padding(horizontal = 6.dp, vertical = 2.dp)
                                    ) {
                                        Text(
                                            text = "🌟 ${child.specialNeedsTitle}",
                                            fontSize = 9.5.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFFB7950B)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(3.dp))

                                // Official Admission / Enrollment Date
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = when (lang) {
                                            Language.TELUGU -> "📅 చేరిన తేదీ: $enrolledDateFormatted"
                                            Language.HINDI -> "📅 प्रवेश तिथि: $enrolledDateFormatted"
                                            Language.ENGLISH -> "📅 Enrolled: $enrolledDateFormatted"
                                        },
                                        fontSize = 10.5.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = Color(0xFF2980B9)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "• రికార్డు: #${index + 1}",
                                        fontSize = 10.sp,
                                        color = Color(0xFF7F8C8D)
                                    )
                                }

                                // Parents & Habitation
                                Text(
                                    text = "👨‍👩‍👧 ${child.fatherName.take(16)} / ${child.motherName.take(16)}",
                                    fontSize = 10.5.sp,
                                    color = Color(0xFF566573)
                                )

                                // Official ICDS / Poshan ID & Aadhaar
                                Text(
                                    text = "🆔 ${child.enrollmentId} • ${child.villageWard} • ఆధార్: •••• ${child.aadhaarLast4}",
                                    fontSize = 10.sp,
                                    color = Color(0xFF95A5A6)
                                )
                            }

                            Icon(
                                imageVector = Icons.Default.ChevronRight,
                                contentDescription = "Open Child Record",
                                tint = Color(0xFFBDC3C7),
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
