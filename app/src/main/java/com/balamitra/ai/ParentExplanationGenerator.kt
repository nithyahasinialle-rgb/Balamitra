package com.balamitra.ai

import com.balamitra.core.model.Language

object ParentExplanationGenerator {

    fun generateExplanation(
        childName: String,
        targetLanguage: Language,
        hasInstructionNeed: Boolean
    ): Pair<String, String> {
        return when (targetLanguage) {
            Language.TELUGU -> {
                val message = if (hasInstructionNeed) {
                    "ఈరోజు $childName అంగన్‌వాడీలో కప్పులు మరియు రంగులతో చాలా ఉత్సాహంగా ఆడాడు. వరుసగా రెండు పనులు (రెండు సూచనలు) చేయడం సాధన చేశాడు. ఇంకా కొంచెం ప్రాక్టీస్ అవసరం."
                } else {
                    "ఈరోజు $childName అంగన్‌వాడీలో స్నేహితులతో కలిసి చాలా చురుగ్గా పాల్గొన్నాడు. రంగులను మరియు వస్తువులను చక్కగా గుర్తించాడు."
                }
                val homeTip = "ఇంట్లో సరళమైన ఆట: '$childName, స్పూన్ తీసి ప్లేటులో పెట్టు' వంటి రెండు చిన్న పనులను సరదాగా చెప్పి ప్రోత్సహించండి."
                Pair(message, homeTip)
            }
            Language.HINDI -> {
                val message = if (hasInstructionNeed) {
                    "आज $childName ने आंगनवाड़ी में कप और ढक्कन के साथ बहुत उत्साह से खेल खेला। उसने दो निर्देश एक साथ पूरे करने का अभ्यास किया, जिसमें थोड़ी मदद की जरूरत पड़ी।"
                } else {
                    "आज $childName ने सभी गतिविधियों में बहुत खुशी से भाग लिया और रंगों को बहुत अच्छे से पहचाना।"
                }
                val homeTip = "घर पर सरल गतिविधि: बच्चे से कहें 'चम्मच उठाओ और थाली में रखो'। इससे बच्चे का ध्यान और सुनने की क्षमता बढ़ती है।"
                Pair(message, homeTip)
            }
            Language.ENGLISH -> {
                val message = if (hasInstructionNeed) {
                    "Today $childName played an interactive game with cups and bottle caps. He practiced following two small instructions in sequence and is making good progress with support."
                } else {
                    "Today $childName participated happily in all center activities and identified familiar colors and objects with confidence."
                }
                val homeTip = "Simple 5-minute game at home: Ask the child 'Pick up the spoon and put it on the plate'. Small sequential games build focus and memory."
                Pair(message, homeTip)
            }
        }
    }
}
