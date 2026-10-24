package com.balamitra.domain.speech

import com.balamitra.core.model.Language

data class SpeechMilestoneCheck(
    val ageMonthsRange: String,
    val expectedMilestone: String,
    val redFlagIndicator: String
)

data class SpeechTherapyReferralGuideline(
    val ageYears: Int,
    val whenToReferSummary: String,
    val keyIndicators: List<String>,
    val anganwadiAction: String,
    val parentGuidance: String
)

object SpeechTherapyGuidelines {

    val ageMilestones = listOf(
        SpeechMilestoneCheck(
            ageMonthsRange = "12 - 18 Months",
            expectedMilestone = "Babbles with melody ('ba-ba', 'da-da'), responds to name, points to show what they want.",
            redFlagIndicator = "No babbling by 12 months, does not point/gesture by 14 months, no response to sound or name."
        ),
        SpeechMilestoneCheck(
            ageMonthsRange = "18 - 24 Months",
            expectedMilestone = "Uses 6 to 20 single meaningful words ('Amma', 'Paalu', 'Gau'), imitates familiar animal sounds.",
            redFlagIndicator = "Fewer than 6 spoken words by 18 months, does not imitate sounds or gestures, lost previously used words."
        ),
        SpeechMilestoneCheck(
            ageMonthsRange = "2 - 3 Years",
            expectedMilestone = "Combines 2 words ('Mummy come', 'Pappu kaavali'), follows simple 2-step requests, 50% intelligible to family.",
            redFlagIndicator = "Cannot put 2 words together by 24-30 months, relies only on pointing or crying to communicate, speaks in monotone."
        ),
        SpeechMilestoneCheck(
            ageMonthsRange = "3 - 4 Years",
            expectedMilestone = "Uses 3-4 word sentences, asks 'Why?' and 'What?', talks about daily activities, speech is 75% intelligible.",
            redFlagIndicator = "Persistent stuttering/hesitation lasting >6 months, very unclear speech (unintelligible to strangers), difficulty chewing or swallowing."
        ),
        SpeechMilestoneCheck(
            ageMonthsRange = "4 - 6 Years",
            expectedMilestone = "Speaks in full narrative sentences, recounts simple rhymes and stories, social conversation with peers.",
            redFlagIndicator = "Struggles to formulate sentences, severe sound omissions (e.g. dropping start or end consonants), unable to follow routine 2-step verbal instructions."
        )
    )

    fun getReferralGuidance(ageYears: Int, language: Language): SpeechTherapyReferralGuideline {
        return when (language) {
            Language.TELUGU -> when {
                ageYears <= 2 -> SpeechTherapyReferralGuideline(
                    ageYears = ageYears,
                    whenToReferSummary = "18-24 నెలల లోపు కనీసం 6 అర్థవంతమైన పదాలు పలకలేకపోయినా, పేరు పిలిచినా స్పందించకపోయినా స్పీచ్ థెరపీ / ఆడియాలజీ పరిశీలన అవసరం.",
                    keyIndicators = listOf(
                        "పిల్లవాడిని పేరు పెట్టి పిలిచినా తల తిప్పి చూడకపోవడం (వినికిడి లోపం అనుమానం)",
                        "18 నెలలు దాటినా సంజ్ఞలు (సైగలు) లేదా ఏడుపు ద్వారానే అవసరాలు తెలియజేయడం",
                        "ఆవు, కుక్క వంటి పరిచిత జంతువుల శబ్దాలు లేదా తల్లిదండ్రుల స్వరాలను అనుకరించలేకపోవడం",
                        "ఆహారం నమలడంలో లేదా మింగడంలో గమనించదగ్గ అసౌకర్యం"
                    ),
                    anganwadiAction = "కేంద్రంలో రోజువారీ రైమ్స్ మరియు ముఖాముఖి కంటి పరిచయంతో కూడిన ఆటలు ఆడించండి. సమస్య కొనసాగితే ANM/రాష్ట్రీయ బాల స్వాస్థ్య కార్యక్రమం (RBSK) వైద్య బృందానికి నివేదించండి.",
                    parentGuidance = "ఇంట్లో మొబైల్ స్క్రీన్ సమయాన్ని పూర్తిగా తగ్గించి, ప్రతిరోజూ కనీసం 20 నిమిషాలు బిడ్డ ముఖం వైపు చూస్తూ స్పష్టంగా కథలు, పాటలు వినిపించండి."
                )
                ageYears in 3..4 -> SpeechTherapyReferralGuideline(
                    ageYears = ageYears,
                    whenToReferSummary = "3-4 సంవత్సరాల వయస్సులో రెండు పదాలు కలిపి మాట్లాడలేకపోయినా, లేదా మాట్లాడేటప్పుడు విపరీతమైన తడబాటు (నత్తి) 6 నెలలకు పైగా కొనసాగినా నిపుణులను సంప్రదించాలి.",
                    keyIndicators = listOf(
                        "సరళమైన 2-దశల మౌఖిక సూచనలను అర్థం చేసుకోలేకపోవడం",
                        "బయటి వ్యక్తులకు పిల్లవాడి మాటలు 50% కంటే తక్కువ అర్థం కావడం",
                        "మాటల మధ్య తీవ్రమైన తడబాటు లేదా ధ్వనులు పలకడానికి ఇబ్బంది పడటం",
                        "తోటి పిల్లలతో కలవకుండా మాటలు రాక ఒంటరిగా ఉండటం"
                    ),
                    anganwadiAction = "చిత్రపటాల ఆధారంగా సులభమైన ప్రశ్న-జవాబుల ఆటలు నిర్వహించండి. PHC మెడికల్ ఆఫీసర్ లేదా DEIC (జిల్లా ప్రారంభ జోక్య కేంద్రం) స్పీచ్ థెరపిస్ట్‌కు రిఫర్ చేయండి.",
                    parentGuidance = "పిల్లవాడు తడబడినప్పుడు సరిదిద్దడానికి తొందరపడకుండా, ఓపికగా వినండి. నెమ్మదిగా మరియు చిన్న వాక్యాలతో మాట్లాడటం సాధన చేయించండి."
                )
                else -> SpeechTherapyReferralGuideline(
                    ageYears = ageYears,
                    whenToReferSummary = "5-6 సంవత్సరాల పిల్లలు చిన్న కథలు చెప్పలేకపోయినా లేదా పాఠశాల సంసిద్ధతకు అవసరమైన సంభాషణ నైపుణ్యాలు లేకపోయినా ప్రత్యేక స్పీచ్ సపోర్ట్ అవసరం.",
                    keyIndicators = listOf(
                        "పూర్తి వాక్యాలలో భావాలను వ్యక్తపరచలేకపోవడం",
                        "అక్షరాల మొదటి లేదా చివరి శబ్దాలను స్పష్టంగా పలకలేకపోవడం",
                        "తోటి సమవయస్కులతో సహజమైన సంభాషణను ప్రారంభించడంలో లేదా కొనసాగించడంలో ఇబ్బంది"
                    ),
                    anganwadiAction = "పలకపై బొమ్మలతో కథలను తయారుచేయించండి. పాఠశాల ప్రవేశానికి ముందు DEIC వద్ద సమగ్ర భాషా నైపుణ్యాల స్క్రీనింగ్ చేయించండి.",
                    parentGuidance = "రోజూ రాత్రి పడుకునే ముందు కుటుంబంలో చిన్న కథలు చెప్పి, బిడ్డను సొంత మాటల్లో ముగింపు చెప్పమని అడగండి."
                )
            }
            Language.HINDI -> when {
                ageYears <= 2 -> SpeechTherapyReferralGuideline(
                    ageYears = ageYears,
                    whenToReferSummary = "यदि 18-24 महीने तक बच्चा 6 से कम शब्द बोलता है या नाम पुकारने पर मुड़ता नहीं है, तो स्पीच थेरेपी या श्रवण जांच (Audiology) की सिफारिश की जाती है।",
                    keyIndicators = listOf(
                        "नाम पुकारने या आवाज़ पर प्रतिक्रिया न देना (श्रवण क्षमता की जांच जरूरी)",
                        "18 महीने के बाद भी केवल रोने या इशारों से संवाद करना",
                        "सरल ध्वनियों या शब्दों की नकल न कर पाना",
                        "खाना चबाने या निगलने में कठिनाई होना"
                    ),
                    anganwadiAction = "आंखों से संपर्क बनाकर बालगीत सुनाएं। सुधार न दिखने पर RBSK (राष्ट्रीय बाल स्वास्थ्य कार्यक्रम) टीम को सूचित करें।",
                    parentGuidance = "मोबाइल स्क्रीन का उपयोग बिल्कुल बंद करें और दिन में कम से कम 20 मिनट आमने-सामने बैठकर सरल बातचीत करें।"
                )
                ageYears in 3..4 -> SpeechTherapyReferralGuideline(
                    ageYears = ageYears,
                    whenToReferSummary = "3-4 वर्ष की आयु में यदि बच्चा दो शब्दों को जोड़कर वाक्य नहीं बना पाता या 6 महीने से अधिक समय तक हकलाहट बनी रहती है, तो विशेषज्ञ से परामर्श लें।",
                    keyIndicators = listOf(
                        "सरल 2-चरणीय मौखिक निर्देशों को समझने में असमर्थता",
                        "बाहरी लोगों को बच्चे की बात 50% से कम समझ में आना",
                        "बोलने में लगातार झिझक या हकलाहट महसूस होना",
                        "संवाद में कठिनाई के कारण साथियों से अलग-थलग रहना"
                    ),
                    anganwadiAction = "चित्र-कार्ड के माध्यम से बातचीत की गतिविधियां कराएं। प्राथमिक स्वास्थ्य केंद्र (PHC) या जिला प्रारंभिक हस्तक्षेप केंद्र (DEIC) में रेफर करें।",
                    parentGuidance = "बच्चे के बोलते समय बीच में न टोकें। धीरज से सुनें और स्वयं शांत गति से छोटे वाक्यों में बोलें।"
                )
                else -> SpeechTherapyReferralGuideline(
                    ageYears = ageYears,
                    whenToReferSummary = "5-6 वर्ष के बच्चे यदि अपनी बात वाक्यों में नहीं रख पाते या शब्दों के अक्षरों का उच्चारण स्पष्ट नहीं कर पाते, तो स्पीच सपोर्ट आवश्यक है।",
                    keyIndicators = listOf(
                        "सरल कहानी या दिनचर्या को वाक्यों में न सुना पाना",
                        "ध्वनियों और अक्षरों का अधूरा उच्चारण",
                        "कक्षा में साथियों से संवाद शुरू करने में हिचकिचाहट"
                    ),
                    anganwadiAction = "कक्षा में चित्रों के माध्यम से कहानी बनाने का अभ्यास कराएं और विद्यालय प्रवेश से पूर्व DEIC में जांच कराएं।",
                    parentGuidance = "प्रतिदिन रात को छोटी कहानियां सुनाएं और बच्चे से अपने शब्दों में दोहराने को कहें।"
                )
            }
            Language.ENGLISH -> when {
                ageYears <= 2 -> SpeechTherapyReferralGuideline(
                    ageYears = ageYears,
                    whenToReferSummary = "Speech-Language Pathology (SLP) screening is recommended if an 18-24 month child uses fewer than 6 words or does not orient to their name.",
                    keyIndicators = listOf(
                        "Does not turn head when name is called (requires hearing screening first)",
                        "Relies solely on gestures or crying to communicate needs past 18 months",
                        "Does not imitate familiar animal sounds or speech inflections",
                        "Noticeable difficulty with oral motor tasks (chewing or swallowing soft foods)"
                    ),
                    anganwadiAction = "Conduct daily face-to-face nursery rhymes with direct eye contact. Escalate to the RBSK (Rashtriya Bal Swasthya Karyakram) mobile medical team if unaddressed.",
                    parentGuidance = "Eliminate passive mobile screen exposure completely; engage in at least 20 minutes of responsive, interactive talk every day."
                )
                ageYears in 3..4 -> SpeechTherapyReferralGuideline(
                    ageYears = ageYears,
                    whenToReferSummary = "Refer for speech therapy evaluation if a 3-4 year old cannot combine 2-3 words into phrases, or if dysfluency (stuttering) persists beyond 6 months.",
                    keyIndicators = listOf(
                        "Struggles to process routine 2-step verbal instructions without visual cues",
                        "Speech is less than 50-75% intelligible to unfamiliar listeners",
                        "Noticeable struggle, facial tension, or repetitions during speech production",
                        "Withdraws from peer interactions due to communication frustration"
                    ),
                    anganwadiAction = "Use concrete visual picture prompts during circle time. Formulate an observational referral for the District Early Intervention Centre (DEIC) speech therapist.",
                    parentGuidance = "Never interrupt or complete sentences for the child. Speak slowly, model unhurried speech, and maintain positive emotional warmth."
                )
                else -> SpeechTherapyReferralGuideline(
                    ageYears = ageYears,
                    whenToReferSummary = "Children aged 5-6 requiring speech therapy typically show significant phonological sound omissions or difficulty organizing thoughts into basic narratives.",
                    keyIndicators = listOf(
                        "Unable to tell a simple sequential 3-part event (what happened first, next)",
                        "Consistent sound substitutions or omissions (e.g. dropping initial or final consonants)",
                        "Difficulty sustaining conversational turns with classroom peers"
                    ),
                    anganwadiAction = "Engage in story card sequencing with physical objects. Ensure multidisciplinary school-readiness screening at the local PHC/DEIC.",
                    parentGuidance = "Read picture storybooks together nightly; prompt open-ended questions like 'What do you think will happen next?'"
                )
            }
        }
    }
}
