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
            "act_rattle_rhythm" -> when (language) {
                Language.TELUGU -> LocalizedActivityContent(
                    title = "కిలకిల శబ్దం & చప్పట్ల లయ (Rattle & Clap Rhythm)",
                    requiredMaterials = listOf("కిలకిల బొమ్మ / గిలక", "చేతులు"),
                    steps = listOf(
                        "పిల్లల ఎడమ మరియు కుడి వైపు గిలకను నెమ్మదిగా ఊపండి",
                        "పిల్లవాడు తల తిప్పి శబ్దాన్ని గమనిస్తున్నాడో చూడండి",
                        "చేతులతో చప్పట్లు కొడుతూ పిల్లవాడిని కూడా చప్పట్లు కొట్టమని ప్రోత్సహించండి"
                    ),
                    whatToObserve = "పిల్లవాడు శబ్దం వైపు తల తిప్పుతున్నాడా మరియు రెండు చేతులతో చప్పట్లు కొట్టడానికి ప్రయత్నిస్తున్నాడా?",
                    parentExplanation = "ఈరోజు శబ్దం వినడం, చప్పట్లు కొట్టడం ప్రాక్టీస్ చేశాము. ఇంట్లో కూడా పాట పాడుతూ చప్పట్లు కొట్టించండి."
                )
                Language.HINDI -> LocalizedActivityContent(
                    title = "झुनझुना और ताली की लय (Rattle & Clap Rhythm)",
                    requiredMaterials = listOf("झुनझुना", "हाथ"),
                    steps = listOf(
                        "बच्चे के बाएं और दाएं ओर झुनझुना धीरे से हिलाएं",
                        "देखें कि क्या बच्चा आवाज़ की दिशा में सिर घुमाता है",
                        "ताली बजाते हुए बच्चे को भी ताली बजाने के लिए प्रोत्साहित करें"
                    ),
                    whatToObserve = "क्या बच्चा आवाज़ की दिशा में सिर घुमाता है और दोनों हथेलियों से ताली बजाता है?",
                    parentExplanation = "आज हमने ताली बजाने और आवाज़ सुनने का अभ्यास किया। घर पर भी ताली बजाकर गाने गाएं।"
                )
                Language.ENGLISH -> LocalizedActivityContent(
                    title = "Rattle & Clap Rhythm",
                    requiredMaterials = listOf("Rattle", "Hands"),
                    steps = listOf(
                        "Shake rattle gently to left and right",
                        "Observe if child tracks with head and eyes",
                        "Clap hands together and invite child to clap"
                    ),
                    whatToObserve = "Does the child turn head to track the sound and attempt to clap with both palms?",
                    parentExplanation = "Today we practiced hearing and clapping to sounds. At home, clap your hands while singing to encourage hand coordination."
                )
            }

            "act_roll_soft_ball" -> when (language) {
                Language.TELUGU -> LocalizedActivityContent(
                    title = "మెత్తటి బంతి దొర్లించే ఆట (Gentle Rolling Ball)",
                    requiredMaterials = listOf("మెత్తటి గుడ్డ బంతి లేదా స్పాంజ్ బంతి"),
                    steps = listOf(
                        "చాపపై పిల్లవాడికి ఎదురుగా కూర్చోండి",
                        "బంతిని పిల్లవాడి చేతుల వైపు నెమ్మదిగా దొర్లించండి",
                        "పిల్లవాడిని బంతిని తిరిగి ముందుకు తోయమని చెప్పండి"
                    ),
                    whatToObserve = "పిల్లవాడు చేతులతో బంతిని ఆపి మళ్లీ ముందుకు నెట్టగలుగుతున్నాడా?",
                    parentExplanation = "బంతిని దొర్లించడం వల్ల బ్యాలెన్స్ మరియు చేతుల బలం పెరుగుతుంది. ఇంట్లో కూడా మెత్తటి వస్తువులతో ఈ ఆట ఆడించండి."
                )
                Language.HINDI -> LocalizedActivityContent(
                    title = "नरम गेंद लुढ़काने का खेल (Gentle Rolling Ball)",
                    requiredMaterials = listOf("नरम कपड़े की गेंद"),
                    steps = listOf(
                        "चटाई पर बच्चे के सामने बैठें",
                        "गेंद को धीरे से बच्चे के हाथों की ओर लुढ़काएं",
                        "बच्चे को गेंद वापस धकेलने के लिए कहें"
                    ),
                    whatToObserve = "क्या बच्चा दोनों हाथों से गेंद रोककर आगे धकेल पाता है?",
                    parentExplanation = "गेंद लुढ़काने से बच्चे का संतुलन और हाथों की ताकत बढ़ती है।"
                )
                Language.ENGLISH -> LocalizedActivityContent(
                    title = "Gentle Rolling Ball",
                    requiredMaterials = listOf("Soft cloth ball"),
                    steps = listOf(
                        "Sit on mat opposite to child",
                        "Roll ball gently toward child's hands",
                        "Encourage child to push or roll it back"
                    ),
                    whatToObserve = "Can the child stop the rolling ball with hands and push it back forward?",
                    parentExplanation = "Rolling a ball develops balance and arm strength. Practice rolling soft toys at home."
                )
            }

            "act_color_cups" -> when (language) {
                Language.TELUGU -> LocalizedActivityContent(
                    title = "రంగుల కప్పుల ఆట (Colour Cup Challenge)",
                    requiredMaterials = listOf("ప్లాస్టిక్ కప్పులు", "సీసా మూతలు"),
                    steps = listOf(
                        "చాపపై ఒక ఎరుపు కప్పు, ఒక నీలం కప్పు ఉంచండి",
                        "పిల్లవాడిని అడగండి: 'ఎరుపు కప్పులో రెండు మూతలు వేసి, తరువాత నీలం కప్పును నా దగ్గరకు తీసుకురా'",
                        "చిరునవ్వు చిందిస్తూ, రెండు పనులను క్రమంలో పూర్తి చేశాడో లేదో గమనించండి"
                    ),
                    whatToObserve = "పిల్లవాడు రెండు పనుల సూచనలను గుర్తుంచుకుని పూర్తి చేయగలిగాడా?",
                    parentExplanation = "ఈరోజు $childName 5 నిమిషాల కప్పుల ఆట ఆడారు. రెండు పనులను క్రమంగా చేయడం నేర్చుకున్నారు. ఇంట్లో: 'స్పూన్ తీసి ప్లేటులో పెట్టు' అని చెప్పి ప్రాక్టీస్ చేయించండి."
                )
                Language.HINDI -> LocalizedActivityContent(
                    title = "रंग-बिरंगे कप चुनौती (Colour Cup Challenge)",
                    requiredMaterials = listOf("प्लास्टिक कप", "बोतल के ढक्कन"),
                    steps = listOf(
                        "चटाई पर एक लाल और एक नीला कप रखें",
                        "बच्चे से कहें: 'लाल कप में दो ढक्कन रखो, फिर नीला कप मेरे पास लाओ'",
                        "मुस्कुराते हुए देखें कि क्या दोनों निर्देश क्रम से पूरे किए गए"
                    ),
                    whatToObserve = "क्या बच्चा दो निर्देशों को याद रखकर क्रम से पूरा कर पा रहा है?",
                    parentExplanation = "आज $childName ने 5 मिनट का कप खेल खेला। घर पर कहें: 'चम्मच उठाओ और थाली में रखो'।"
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
                    title = "పెద్ద & చిన్న ఆకుల వర్గీకరణ (Big & Small Leaf Matching)",
                    requiredMaterials = listOf("ఆకులు", "కాగితం"),
                    steps = listOf(
                        "3 పెద్ద ఆకులు, 3 చిన్న ఆకులు సేకరించండి",
                        "కాగితంపై రెండు వృత్తాలు (ఒకటి పెద్దది, ఒకటి చిన్నది) గీయండి",
                        "ఆకులను సరైన వృత్తంలో వేరు చేసి పెట్టమని పిల్లవాడిని అడగండి"
                    ),
                    whatToObserve = "పరిమాణ భేదాన్ని గుర్తించి సొంతంగా వేరు చేయగలిగాడా?",
                    parentExplanation = "ఈరోజు పెద్ద మరియు చిన్న ఆకులను వేరు చేయడం ప్రాక్టీస్ చేశాము. ఇంట్లో పెద్ద ఉల్లిపాయలు, చిన్న ఉల్లిపాయలు వేరు చేయించండి."
                )
                Language.HINDI -> LocalizedActivityContent(
                    title = "पत्तियों की पहचान और छंटाई (Big & Small Leaf Matching)",
                    requiredMaterials = listOf("पत्तियां", "कागज"),
                    steps = listOf(
                        "3 बड़ी और 3 छोटी पत्तियां इकट्ठा करें",
                        "कागज पर दो घेरे (बड़ा और छोटा) बनाएं",
                        "बच्चे से पत्तियों को सही घेरे में रखने को कहें"
                    ),
                    whatToObserve = "क्या बच्चा आकार पहचानकर अलग कर पाता है?",
                    parentExplanation = "आज हमने बड़े और छोटे पत्तों को अलग करना सीखा। घर पर भी बड़े और छोटे चम्मच अलग करवाएं।"
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

            "act_animal_sounds" -> when (language) {
                Language.TELUGU -> LocalizedActivityContent(
                    title = "జంతువుల శబ్దాల ఆట (Animal Sound Safari)",
                    requiredMaterials = listOf("జంతువుల బొమ్మలు లేదా చిత్రాలు"),
                    steps = listOf(
                        "ఆవు శబ్దం ('అంబా') చేస్తూ పిల్లవాడికి చూపించండి",
                        "'కుక్క ఎలా మొరుగుతుంది?' అని అడగండి",
                        "పిల్లవాడు పలికే ప్రతి శబ్దాన్ని మెచ్చుకోండి"
                    ),
                    whatToObserve = "పిల్లవాడు శబ్దాలను అనుకరిస్తూ జంతువులతో కనెక్ట్ చేయగలుగుతున్నాడా?",
                    parentExplanation = "జంతువుల శబ్దాలు పలకడం వల్ల పిల్లల భాషా పరిజ్ఞానం పెరుగుతుంది. ఇంట్లో ఆవు, పిల్లి శబ్దాలు పలికించండి."
                )
                Language.HINDI -> LocalizedActivityContent(
                    title = "जानवरों की आवाज़ें (Animal Sound Safari)",
                    requiredMaterials = listOf("जानवरों के चित्र"),
                    steps = listOf(
                        "गाय की आवाज़ ('अंबा') निकालें",
                        "पूछें: 'कुत्ता कैसे भौंकता है?'",
                        "हर आवाज़ पर बच्चे की प्रशंसा करें"
                    ),
                    whatToObserve = "क्या बच्चा आवाज़ों की नकल कर पाता है?",
                    parentExplanation = "जानवरों की आवाज़ें निकालने से बच्चे की बोलने की क्षमता बढ़ती है।"
                )
                Language.ENGLISH -> LocalizedActivityContent(
                    title = "Animal Sound Safari",
                    requiredMaterials = listOf("Animal picture cards"),
                    steps = listOf(
                        "Make a cow sound ('Ambaa') and show action",
                        "Ask child: 'How does the puppy bark?'",
                        "Celebrate each sound the child repeats"
                    ),
                    whatToObserve = "Does the child mimic sounds and connect them with familiar animals?",
                    parentExplanation = "Making animal sounds expands speech vocabulary and listening skills."
                )
            }

            "act_letter_tracing" -> when (language) {
                Language.TELUGU -> LocalizedActivityContent(
                    title = "పలకపై అక్షరాల దిద్దుడు (Slate Letter Tracing)",
                    requiredMaterials = listOf("పలక", "సుద్దబలపం"),
                    steps = listOf(
                        "పలకపై పిల్లవాడి పేరు మొదటి అక్షరం పెద్దగా రాయండి",
                        "అక్షరం శబ్దం చెబుతూ పిల్లవాడి వేలితో దానిపై దిద్దించండి",
                        "బలపంతో 3 సార్లు దిద్దమని ప్రోత్సహించండి"
                    ),
                    whatToObserve = "పిల్లవాడు బలపం సరైన పద్ధతిలో పట్టుకుని అక్షరాన్ని దిద్దగలుగుతున్నాడా?",
                    parentExplanation = "పలకపై అక్షరాల దిద్దుడు పిల్లలను 1వ తరగతి చదువుకు సిద్ధం చేస్తుంది. ఇంట్లో కూడా బలపంతో రాయిస్తూ ఉండండి."
                )
                Language.HINDI -> LocalizedActivityContent(
                    title = "स्लेट पर अक्षर अभ्यास (Slate Letter Tracing)",
                    requiredMaterials = listOf("स्लेट", "चॉक"),
                    steps = listOf(
                        "स्लेट पर बच्चे के नाम का पहला अक्षर बड़ा लिखें",
                        "अक्षर की ध्वनि बोलें और बच्चे की उंगली उसपर फिराएं",
                        "चॉक से 3 बार दोहराने को कहें"
                    ),
                    whatToObserve = "क्या बच्चा सही मुद्रा में चॉक पकड़कर अक्षर बना पाता है?",
                    parentExplanation = "स्लेट पर अक्षरों का अभ्यास बच्चे को पहली कक्षा के लिए तैयार करता है।"
                )
                Language.ENGLISH -> LocalizedActivityContent(
                    title = "Slate Letter Tracing & Sounds",
                    requiredMaterials = listOf("Slate", "Chalk"),
                    steps = listOf(
                        "Write first letter of child's name in large font on slate",
                        "Say the sound clearly and guide child's finger over it",
                        "Ask child to trace with chalk 3 times"
                    ),
                    whatToObserve = "Can the child maintain correct finger posture and trace without lifting chalk abruptly?",
                    parentExplanation = "Tracing letters on slate prepares children for primary school writing and phonics."
                )
            }

            else -> when (language) {
                Language.TELUGU -> LocalizedActivityContent(
                    title = "వికాస ఆట (Developmental Activity)",
                    requiredMaterials = listOf("కేంద్రంలో లభించే వస్తువులు"),
                    steps = listOf(
                        "పిల్లవాడిని చాపపై కూర్చోబెట్టి సరదాగా మాట్లాడండి",
                        "చేతులతో వస్తువులను పట్టుకోవడం సాధన చేయించండి",
                        "ప్రతి విజయానికి చప్పట్లతో ప్రోత్సహించండి"
                    ),
                    whatToObserve = "పిల్లవాడి ఆసక్తి మరియు చురుకుదనం గమనించండి.",
                    parentExplanation = "ఈరోజు పిల్లవాడితో ఆనందదాయకమైన వికాస ఆట ఆడించాము."
                )
                Language.HINDI -> LocalizedActivityContent(
                    title = "बाल विकास गतिविधि (Developmental Activity)",
                    requiredMaterials = listOf("केंद्र में उपलब्ध सामग्री"),
                    steps = listOf(
                        "बच्चे को चटाई पर बैठाकर प्यार से बात करें",
                        "हाथों से वस्तुओं को पकड़ने का अभ्यास कराएं",
                        "बच्चे की हर छोटी सफलता पर ताली बजाएं"
                    ),
                    whatToObserve = "बच्चे की एकाग्रता और रुचि देखें।",
                    parentExplanation = "आज बच्चे के साथ विकास गतिविधि की गई।"
                )
                Language.ENGLISH -> LocalizedActivityContent(
                    title = "Developmental Activity",
                    requiredMaterials = listOf("Center materials"),
                    steps = listOf(
                        "Sit comfortably on mat with the child",
                        "Practice holding and placing physical items",
                        "Praise the child with smiles and claps"
                    ),
                    whatToObserve = "Observe engagement, focus, and hand movement.",
                    parentExplanation = "Today we practiced an engaging developmental learning activity."
                )
            }
        }
    }
}
