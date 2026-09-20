package com.balamitra.domain.recommendation

import com.balamitra.core.model.Language

data class LocalizedActivityContent(
    val title: String,
    val requiredMaterials: List<String>,
    val steps: List<String>,
    val whatToObserve: String,
    val parentExplanation: String
)

object ActivityLocalization {

    fun getLocalizedContent(activityId: String, language: Language, childName: String): LocalizedActivityContent {
        return when (activityId) {
            "act_color_cups" -> when (language) {
                Language.TELUGU -> LocalizedActivityContent(
                    title = "రంగుల కప్పుల ఆట (Colour Cup Challenge)",
                    requiredMaterials = listOf("ప్లాస్టిక్ / కాగితపు కప్పులు", "బాటిల్ మూతలు"),
                    steps = listOf(
                        "చాపపై ఒక ఎరుపు కప్పు, ఒక నీలం కప్పు ఉంచండి",
                        "బిడ్డతో నెమ్మదిగా చెప్పండి: 'ఎరుపు కప్పులో రెండు మూతలు వేసి, తర్వాత నీలం కప్పును నా వద్దకు తీసుకురా'",
                        "ఒత్తిడి లేకుండా, బిడ్డ రెండు సూచనలను సరైన వరుసలో పూర్తి చేశారో లేదో పరిశీలించండి"
                    ),
                    whatToObserve = "బిడ్డ రెండు వరుస సూచనలను గుర్తుంచుకుని, వస్తువులతో స్వయంగా చేయగలుగుతున్నారా?",
                    parentExplanation = "ఈరోజు రవి 5 నిమిషాల కప్పుల ఆట ఆడాడు. వరుసగా రెండు పనులు చేయడం సాధన చేశాడు. మీరు ఇంట్లో కూడా: 'స్పూన్ తీసి ప్లేటులో పెట్టు' వంటి సరళమైన పనులు చెప్పి ప్రోత్సహించవచ్చు."
                )
                Language.HINDI -> LocalizedActivityContent(
                    title = "रंग-बिरंगे कप गतिविधि (Colour Cup Challenge)",
                    requiredMaterials = listOf("प्लास्टिक कप", "बोतल के ढक्कन"),
                    steps = listOf(
                        "चटाई पर एक लाल कप और एक नीला कप रखें",
                        "बच्चे से कहें: 'लाल कप में दो ढक्कन रखो, फिर नीला कप मेरे पास लाओ'",
                        "मुस्कुराते हुए देखें कि क्या दोनों काम क्रम से पूरे किए गए"
                    ),
                    whatToObserve = "क्या बच्चा दो लगातार निर्देशों को याद रखकर वस्तुओं के साथ पूरा कर पाता है?",
                    parentExplanation = "आज रवि ने 5 मिनट का एक खेल खेला जिसमें उसने दो निर्देश एक के बाद एक पूरे करने का अभ्यास किया। आप घर पर कह सकते हैं: 'चम्मच उठाओ और थाली में रखो'।"
                )
                Language.ENGLISH -> LocalizedActivityContent(
                    title = "Colour Cup Challenge",
                    requiredMaterials = listOf("Cups", "Bottle caps"),
                    steps = listOf(
                        "Place a red cup and a blue cup on the mat",
                        "Ask the child: 'Put two caps in the red cup, then bring the blue cup to me'",
                        "Smile and observe if both steps are done in order without repeating"
                    ),
                    whatToObserve = "Can the child hold two sequential instructions in memory and execute them with physical objects?",
                    parentExplanation = "Today $childName played a 5-minute cup game where he practiced following two small actions in order. You can ask him at home: 'Pick up your spoon and put it on the plate'."
                )
            }
            "act_object_sorting" -> when (language) {
                Language.TELUGU -> LocalizedActivityContent(
                    title = "పెద్ద & చిన్న ఆకుల వర్గీకరణ",
                    requiredMaterials = listOf("ఆకులు", "కాగితం"),
                    steps = listOf(
                        "3 పెద్ద ఆకులు, 3 చిన్న ఆకులు సేకరించండి",
                        "కాగితంపై రెండు వృత్తాలు (ఒకటి పెద్దది, ఒకటి చిన్నది) గీయండి",
                        "బిడ్డను ఆకులను వాటి పరిమాణానికి తగిన వృత్తంలో ఉంచమని చెప్పండి"
                    ),
                    whatToObserve = "బిడ్డ పరిమాణాల మధ్య తేడాను గుర్తించి స్వయంగా వర్గీకరించగలుగుతున్నారా?",
                    parentExplanation = "ఈరోజు పెద్ద మరియు చిన్న ఆకులను వేరు చేసే ఆట ఆడాము. ఇంట్లో కూడా పెద్ద స్పూన్లు, చిన్న స్పూన్లను వేరు చేసే ఆట ఆడించవచ్చు."
                )
                Language.HINDI -> LocalizedActivityContent(
                    title = "छोटे और बड़े पत्तों की पहचान",
                    requiredMaterials = listOf("पत्ते", "कागज़"),
                    steps = listOf(
                        "3 बड़े पत्ते और 3 छोटे पत्ते इकट्ठा करें",
                        "कागज़ पर दो गोले (एक बड़ा, एक छोटा) बनाएं",
                        "बच्चे से पत्तों को उनके आकार के अनुसार सही गोले में रखने को कहें"
                    ),
                    whatToObserve = "क्या बच्चा आकार के अंतर को समझकर खुद अलग कर पाता है?",
                    parentExplanation = "आज हमने पत्तों को छोटे-बड़े आकार में अलग करने का खेल खेला। घर पर आप बड़े और छोटे चम्मच अलग करवा सकते हैं।"
                )
                Language.ENGLISH -> LocalizedActivityContent(
                    title = "Big & Small Leaf Matching",
                    requiredMaterials = listOf("Leaves", "Paper"),
                    steps = listOf(
                        "Collect 3 large leaves and 3 small leaves",
                        "Draw two circles on paper (one big, one small)",
                        "Ask child to sort the leaves into their matching circle"
                    ),
                    whatToObserve = "Does the child differentiate relative size and sort independently?",
                    parentExplanation = "Today we practiced sorting leaves by size. At home, ask the child to help separate big and small onions or spoons."
                )
            }
            "act_thread_beads" -> when (language) {
                Language.TELUGU -> LocalizedActivityContent(
                    title = "దారంలో మూతలు గుచ్చడం",
                    requiredMaterials = listOf("రంధ్రాలున్న మూతలు", "దారం"),
                    steps = listOf(
                        "రంధ్రాలు ఉన్న బాటిల్ మూతలు లేదా దారపు రీళ్లు తీసుకోండి",
                        "బిడ్డకు దారంలో 3 మూతలను గుచ్చడానికి సహాయం చేయండి",
                        "వేళ్ల పట్టు, ఏకాగ్రతను మెచ్చుకోండి"
                    ),
                    whatToObserve = "వేళ్ల పట్టు (పిన్సర్ గ్రాస్ప్) మరియు కంటి-చేతి సమన్వయం ఎలా ఉంది?",
                    parentExplanation = "పిల్లవాడు దారంలో మూతలను గుచ్చడం సాధన చేశాడు. ఇది భవిష్యత్తులో పెన్సిల్ పట్టుకోవడానికి చేతి వేళ్లకు బలాన్ని ఇస్తుంది."
                )
                Language.HINDI -> LocalizedActivityContent(
                    title = "धागे में ढक्कन पिरोना",
                    requiredMaterials = listOf("ढक्कन", "धागा"),
                    steps = listOf(
                        "छेद वाले ढक्कन या धागे की खाली रील लें",
                        "बच्चे को धागे में 3 वस्तुएं पिरोने के लिए प्रेरित करें",
                        "उंगलियों की पकड़ और स्थिरता की प्रशंसा करें"
                    ),
                    whatToObserve = "उंगलियों की पकड़ और हाथ-आंखों का समन्वय कैसा है?",
                    parentExplanation = "बच्चे ने धागे में ढक्कन पिरोने का अभ्यास किया। इससे आगे चलकर पेंसिल पकड़ने में मदद मिलती है।"
                )
                Language.ENGLISH -> LocalizedActivityContent(
                    title = "Bottle Cap Threading",
                    requiredMaterials = listOf("Bottle caps", "Thread"),
                    steps = listOf(
                        "Use caps with small holes or empty thread spools",
                        "Guide the child to thread string through 3 items",
                        "Praise steady finger control"
                    ),
                    whatToObserve = "Pincer grasp and hand-eye coordination stability.",
                    parentExplanation = "Your child practiced threading thread through bottle caps. This builds finger strength for holding pencils later."
                )
            }
            else -> when (language) {
                Language.TELUGU -> LocalizedActivityContent(
                    title = "రెండు చిత్రాల కథా క్రమం",
                    requiredMaterials = listOf("స్లేటు", "సుద్దముక్క"),
                    steps = listOf(
                        "స్లేటుపై సూర్యోదయం, తర్వాత పళ్ళు తోముకోవడం బొమ్మలు గీయండి",
                        "బిడ్డను అడగండి: 'మనం మొదట ఏమి చేస్తాం? తర్వాత ఏమి చేస్తాం?'",
                        "బిడ్డ తన సొంత మాటల్లో వరుసక్రమం చెప్పేలా ప్రోత్సహించండి"
                    ),
                    whatToObserve = "మొదట ఏది జరుగుతుంది, తర్వాత ఏది జరుగుతుందో బిడ్డ మాటల్లో చెప్పగలుగుతున్నారా?",
                    parentExplanation = "ఉదయం మొదట ఏమి చేస్తాము, తర్వాత ఏమి చేస్తాము అనే వరుసక్రమం మాట్లాడాము."
                )
                Language.HINDI -> LocalizedActivityContent(
                    title = "दो चित्रों से कहानी का क्रम",
                    requiredMaterials = listOf("स्लेट", "चॉक"),
                    steps = listOf(
                        "स्लेट पर सूरज उगने और ब्रश करने का चित्र बनाएं",
                        "बच्चे से पूछें: 'पहले हम क्या करते हैं? फिर उसके बाद क्या?'",
                        "बच्चे को अपने शब्दों में क्रम समझाने के लिए प्रोत्साहित करें"
                    ),
                    whatToObserve = "क्या बच्चा पहले और बाद की घटना को अपने शब्दों में व्यक्त कर पाता है?",
                    parentExplanation = "हमने बात की कि सुबह पहले क्या करते हैं और फिर क्या। घर पर भी ऐसे सवाल पूछें।"
                )
                Language.ENGLISH -> LocalizedActivityContent(
                    title = "Two-Picture Story Clues",
                    requiredMaterials = listOf("Paper", "Chalk"),
                    steps = listOf(
                        "Draw sun rising, then child brushing teeth on slate",
                        "Ask child: 'What do we do first? What do we do next?'",
                        "Encourage child to explain sequence in their words"
                    ),
                    whatToObserve = "Can the child verbalize what happens first and what happens next?",
                    parentExplanation = "We talked about what we do first and next in the morning. Ask your child what happens after bath time."
                )
            }
        }
    }
}
