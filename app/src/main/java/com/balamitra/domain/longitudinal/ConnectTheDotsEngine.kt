package com.balamitra.domain.longitudinal

import com.balamitra.core.model.ActivityOutcome
import com.balamitra.core.model.ConnectTheDotsResult
import com.balamitra.core.model.DevelopmentDomain
import com.balamitra.core.model.Language
import com.balamitra.data.local.ActivityAttemptEntity
import com.balamitra.data.local.ChildEntity
import com.balamitra.data.local.DevelopmentObservationEntity

object ConnectTheDotsEngine {

    fun connectDots(
        child: ChildEntity,
        developmentHistory: List<DevelopmentObservationEntity>,
        activityAttempts: List<ActivityAttemptEntity>,
        language: Language = Language.ENGLISH
    ): ConnectTheDotsResult {
        val evidencePoints = mutableListOf<String>()

        val sequentialIssues = developmentHistory.filter {
            it.observationText.contains("instruction", ignoreCase = true) ||
            it.observationText.contains("two-step", ignoreCase = true) ||
            it.needsFollowUp
        }

        val tactileSuccess = activityAttempts.filter {
            it.outcome == ActivityOutcome.INDEPENDENT &&
            (it.materialsUsed.contains("cup", ignoreCase = true) ||
             it.materialsUsed.contains("cap", ignoreCase = true) ||
             it.materialsUsed.contains("pebble", ignoreCase = true))
        }

        val abstractStruggles = activityAttempts.filter {
            it.outcome != ActivityOutcome.INDEPENDENT
        }

        when (language) {
            Language.TELUGU -> {
                if (sequentialIssues.isNotEmpty()) {
                    evidencePoints.add("పరిశీలన: 'గుంపులో శాంతంగా ఉన్నాడు. రెండు వరుస సూచనల (పుస్తకం తీసి అరలో పెట్టడం) కోసం సహాయం అవసరమైంది.'")
                    evidencePoints.add("గత పరిశీలన: 'ఒకే సూచనను సులభంగా చేశాడు, కానీ వరుస సూచనలు ఇచ్చినప్పుడు కొద్దిగా తడబడ్డాడు.'")
                }
                tactileSuccess.forEach {
                    evidencePoints.add("గత కార్యాచరణ విజయం (స్వతంత్రంగా): భౌతిక వస్తువులు, కప్పులు, మూతలతో ఎంతో ఉత్సాహంగా ఆడాడు.")
                }
                abstractStruggles.forEach {
                    evidencePoints.add("గత కార్యాచరణ సవాలు: అమూర్త వర్గీకరణ ఆటలో మాటల సహాయం అవసరమైంది.")
                }
            }
            Language.HINDI -> {
                if (sequentialIssues.isNotEmpty()) {
                    evidencePoints.add("अवलोकन: 'समूह में शांत बैठा था। दो-चरणीय निर्देश (किताब उठाकर शेल्फ पर रखना) के लिए सहायता की ज़रूरत पड़ी।'")
                    evidencePoints.add("पिछला अवलोकन: 'एकल निर्देश आसानी से पूरा किया, पर लगातार निर्देश मिलने पर रुका।'")
                }
                tactileSuccess.forEach {
                    evidencePoints.add("पिछली सफलता (स्वयं किया): कप और रंगीन ढक्कन वाली गतिविधि में पूरा उत्साह दिखाया।")
                }
                abstractStruggles.forEach {
                    evidencePoints.add("पिछली चुनौती: वर्गीकरण खेल में मौखिक मार्गदर्शन की आवश्यकता पड़ी।")
                }
            }
            Language.ENGLISH -> {
                sequentialIssues.forEach { obs ->
                    evidencePoints.add("Observation: \"${obs.observationText}\"")
                }
                tactileSuccess.forEach { attempt ->
                    evidencePoints.add("Previous Activity Success: Completed independently using physical objects (${attempt.materialsUsed})")
                }
                abstractStruggles.forEach { attempt ->
                    evidencePoints.add("Previous Activity Challenge: Needed verbal assistance with sorting task (${attempt.materialsUsed})")
                }
            }
        }

        val patternSummary = when (language) {
            Language.TELUGU -> {
                if (sequentialIssues.isNotEmpty() && tactileSuccess.isNotEmpty()) {
                    "గత కొన్ని పరిశీలనలలో ${child.name} రెండు వరుస సూచనలను అర్థం చేసుకోవడానికి కొంచెం సమయం తీసుకున్నాడు, కానీ కప్పులు, మూతల వంటి స్పర్శ వస్తువులను ఉపయోగించినప్పుడు పూర్తి ఏకాగ్రతతో స్వయంగా పూర్తి చేయగలిగాడు."
                } else if (sequentialIssues.isNotEmpty()) {
                    "${child.name} చురుకైన ఆసక్తిని ప్రదర్శిస్తున్నాడు, అయితే ఒకేసారి బహుళ మౌఖిక సూచనలను విన్నప్పుడు కొద్దిగా ఆగుతున్నాడు."
                } else {
                    "${child.name} మోటార్ మరియు భాషా కార్యకలాపాలలో స్థిరమైన, సమతుల్య పురోగతిని ప్రదర్శిస్తున్నాడు."
                }
            }
            Language.HINDI -> {
                if (sequentialIssues.isNotEmpty() && tactileSuccess.isNotEmpty()) {
                    "हाल के अवलोकनों में ${child.name} को दोहरे निर्देशों को समझने में थोड़ी मदद की ज़रूरत पड़ी, लेकिन जब कंक्रीट वस्तुओं (कप और ढक्कन) का उपयोग किया गया तो उसने पूरा ध्यान और स्वतंत्र सफलता दिखाई।"
                } else if (sequentialIssues.isNotEmpty()) {
                    "${child.name} लगातार सक्रिय जिज्ञासा दिखाता है, लेकिन एक साथ कई मौखिक चरणों को सुनते समय थोड़ा रुकता है।"
                } else {
                    "${child.name} शारीरिक और भाषाई गतिविधियों में संतुलित प्रगति दिखा रहा है।"
                }
            }
            Language.ENGLISH -> {
                if (sequentialIssues.isNotEmpty() && tactileSuccess.isNotEmpty()) {
                    "${child.name} has needed support with two-step instructions across recent observations, but responded with significantly higher focus and independent success to activities involving concrete physical objects."
                } else if (sequentialIssues.isNotEmpty()) {
                    "${child.name} consistently demonstrates active curiosity, but pauses when processing multiple verbal steps simultaneously."
                } else {
                    "${child.name} is demonstrating steady, balanced exploration across motor and language activities."
                }
            }
        }

        val recommendation = when (language) {
            Language.TELUGU -> {
                if (sequentialIssues.isNotEmpty()) {
                    "కప్పులు మరియు బాటిల్ మూతల వంటి పరిచిత వస్తువులతో 5 నిమిషాల 2-దశల ఆటను నిర్వహించండి. భౌతిక వస్తువులను తాకడం వల్ల బిడ్డకు జ్ఞాపకశక్తి మరియు వినికిడి క్రమం బలపడుతుంది."
                } else {
                    "కేంద్రంలో సహచరులతో కలిసి పరస్పర ఆటలు ఆడేలా ప్రోత్సహించండి."
                }
            }
            Language.HINDI -> {
                if (sequentialIssues.isNotEmpty()) {
                    "परिचित वस्तुओं (जैसे कप और ढक्कन) के साथ 5 मिनट का 2-चरणीय खेल कराएं। वस्तुओं को छूने से निर्देशों को याद रखना आसान होता है।"
                } else {
                    "कक्षा में साथियों के साथ साझा खेल को प्रोत्साहित करें।"
                }
            }
            Language.ENGLISH -> {
                if (sequentialIssues.isNotEmpty()) {
                    "Consider a short 5-minute activity pairing two sequential instructions with familiar tactile items (e.g. cups and bottle caps). Physical touch anchors verbal memory."
                } else {
                    "Encourage peer-based interactive play with shared classroom objects."
                }
            }
        }

        val title = when (language) {
            Language.TELUGU -> "${child.name} అభివృద్ధి నమూనా (గుర్తించిన అంశాలు)"
            Language.HINDI -> "${child.name} के लिए पहचाना गया पैटर्न"
            Language.ENGLISH -> "Pattern Identified for ${child.name}"
        }

        return ConnectTheDotsResult(
            title = title,
            patternIdentified = patternSummary,
            historicalEvidence = evidencePoints.ifEmpty {
                when (language) {
                    Language.TELUGU -> listOf("ఇటీవలి రోజుల్లో ప్రాథమిక అభివృద్ధి పరిశీలనలు నమోదు చేయబడ్డాయి.")
                    Language.HINDI -> listOf("हाल के दिनों में बुनियादी विकासात्मक अवलोकन दर्ज किए गए हैं।")
                    Language.ENGLISH -> listOf("Baseline developmental observations logged across recent days.")
                }
            },
            recommendation = recommendation,
            domain = DevelopmentDomain.LANGUAGE_COGNITIVE
        )
    }
}
