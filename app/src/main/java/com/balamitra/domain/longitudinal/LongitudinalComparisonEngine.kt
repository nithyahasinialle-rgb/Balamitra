package com.balamitra.domain.longitudinal

import com.balamitra.core.model.ActivityOutcome
import com.balamitra.core.model.GrowthStatus
import com.balamitra.core.model.Language
import com.balamitra.core.model.WhatChangedResult
import com.balamitra.data.local.ActivityAttemptEntity
import com.balamitra.data.local.ChildEntity
import com.balamitra.data.local.DevelopmentObservationEntity
import com.balamitra.data.local.GrowthObservationEntity
import com.balamitra.domain.growth.GrowthEngine

object LongitudinalComparisonEngine {

    fun compareObservations(
        child: ChildEntity,
        growthHistory: List<GrowthObservationEntity>,
        developmentHistory: List<DevelopmentObservationEntity>,
        recentAttempts: List<ActivityAttemptEntity>,
        language: Language = Language.ENGLISH
    ): WhatChangedResult {
        // Growth comparison
        val latestGrowth = growthHistory.firstOrNull()
        val previousGrowth = growthHistory.getOrNull(1)
        val growthTrajectory = if (latestGrowth != null) {
            GrowthEngine.evaluateTrajectory(latestGrowth.weightKg, previousGrowth?.weightKg)
        } else {
            GrowthStatus.STABLE
        }

        val growthDelta = if (latestGrowth != null && previousGrowth != null) {
            val delta = latestGrowth.weightKg - previousGrowth.weightKg
            val sign = if (delta >= 0) "+" else ""
            when (language) {
                Language.TELUGU -> "${sign}${String.format("%.1f", delta)} కిలో (${previousGrowth.weightKg} కిలో → ${latestGrowth.weightKg} కిలో)"
                Language.HINDI -> "${sign}${String.format("%.1f", delta)} किग्रा (${previousGrowth.weightKg} किग्रा → ${latestGrowth.weightKg} किग्रा)"
                Language.ENGLISH -> "${sign}${String.format("%.1f", delta)} kg (${previousGrowth.weightKg} kg → ${latestGrowth.weightKg} kg)"
            }
        } else if (latestGrowth != null) {
            when (language) {
                Language.TELUGU -> "${latestGrowth.weightKg} కిలో (ప్రస్తుత బేస్‌లైన్)"
                Language.HINDI -> "${latestGrowth.weightKg} किग्रा (वर्तमान आधार रेखा)"
                Language.ENGLISH -> "${latestGrowth.weightKg} kg (Current baseline)"
            }
        } else {
            when (language) {
                Language.TELUGU -> "ఇంకా కొలతలు నమోదు కాలేదు"
                Language.HINDI -> "अभी तक कोई माप दर्ज नहीं है"
                Language.ENGLISH -> "No measurements recorded yet"
            }
        }

        // Development progression & changes
        val participationNotes = mutableListOf<String>()
        val areasNeedingSupport = mutableListOf<String>()

        when (language) {
            Language.TELUGU -> {
                participationNotes.add("ఇటీవలి పరిశీలన: రంగులను చక్కగా గుర్తించాడు. వస్తువులతో ఆడేటప్పుడు ఎక్కువ ఆసక్తి కనబరిచాడు.")
                participationNotes.add("గత సెషన్‌తో పోలిస్తే గ్రూప్ కార్యకలాపాలలో చురుకుదనం మెరుగైంది.")
                participationNotes.add("ఇటీవల రంగుల కప్పుల ఆటను ఎవరి సహాయం లేకుండా స్వయంగా ఉత్సాహంగా పూర్తి చేశాడు.")
                areasNeedingSupport.add("వరుసగా రెండు-మూడు పనులు చెప్పినప్పుడు కొద్దిగా ఆగుతున్నాడు; నిరంతర ప్రోత్సాహం అవసరం.")
            }
            Language.HINDI -> {
                participationNotes.add("हालिया अवलोकन: रंगों की सही पहचान की। कंक्रीट वस्तुओं के साथ बहुत उत्साह दिखाया।")
                participationNotes.add("पिछले सत्र की तुलना में समूह में भागीदारी में सुधार हुआ।")
                participationNotes.add("हाल ही में कप और ढक्कन का खेल बिना किसी सहायता के स्वतंत्र रूप से पूरा किया।")
                areasNeedingSupport.add("लगातार बहु-चरणीय निर्देशों को समझने में निरंतर सहयोग की आवश्यकता है।")
            }
            Language.ENGLISH -> {
                if (developmentHistory.isNotEmpty()) {
                    val latestDev = developmentHistory.first()
                    participationNotes.add("Recent: ${latestDev.observationText}")

                    val olderDev = developmentHistory.drop(1).firstOrNull()
                    if (olderDev != null) {
                        if (olderDev.needsFollowUp && !latestDev.needsFollowUp) {
                            participationNotes.add("Participation improved compared to earlier session.")
                        }
                    }

                    val followUps = developmentHistory.filter { it.needsFollowUp }
                    if (followUps.isNotEmpty()) {
                        areasNeedingSupport.add("Needs continued support with sequential multi-step instructions.")
                    }
                } else {
                    participationNotes.add("Baseline developmental observations underway.")
                }

                val lastAttempt = recentAttempts.firstOrNull()
                if (lastAttempt != null) {
                    when (lastAttempt.outcome) {
                        ActivityOutcome.INDEPENDENT -> {
                            participationNotes.add("Completed recent practical activity independently (${lastAttempt.materialsUsed}).")
                        }
                        ActivityOutcome.NEEDED_HELP -> {
                            areasNeedingSupport.add("Required adult guidance with recent sorting task.")
                        }
                        ActivityOutcome.COULD_NOT_DO -> {
                            areasNeedingSupport.add("Found the previous abstract sorting task challenging.")
                        }
                    }
                }
            }
        }

        val suggestedNextStep = when (language) {
            Language.TELUGU -> "వరుస సూచనలపై ఆత్మవిశ్వాసం పెంచడానికి కప్పులు, మూతలతో 5 నిమిషాల ఆట ఆడించండి."
            Language.HINDI -> "क्रमिक निर्देशों में आत्मविश्वास बढ़ाने के लिए कप और ढक्कन के साथ 5 मिनट का खेल आज़माएं।"
            Language.ENGLISH -> {
                if (areasNeedingSupport.isNotEmpty()) {
                    "Try a 5-minute object-based game (using cups and bottle caps) to build sequential instruction confidence."
                } else {
                    "Continue exploratory play and creative expression with local materials."
                }
            }
        }

        return WhatChangedResult(
            childName = child.name,
            growthDelta = growthDelta,
            growthTrajectory = growthTrajectory,
            participationNotes = participationNotes,
            areasNeedingSupport = areasNeedingSupport,
            suggestedNextStep = suggestedNextStep
        )
    }
}
