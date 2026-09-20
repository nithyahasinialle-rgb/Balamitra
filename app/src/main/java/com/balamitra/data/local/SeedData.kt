package com.balamitra.data.local

import com.balamitra.core.model.ActivityOutcome
import com.balamitra.core.model.DevelopmentDomain
import com.balamitra.core.model.GrowthStatus
import com.balamitra.core.model.MaterialLocation

object SeedData {
    val children = listOf(
        ChildEntity(
            id = "child_ravi",
            name = "Ravi",
            ageYears = 4,
            gender = "M",
            preferredLanguage = "te",
            baselineNotes = "Enjoys concrete physical objects. Very observant during circle time.",
            fatherName = "Sri K. Venkata Rao (Farmer)",
            motherName = "Smt. K. Lakshmi",
            enrollmentId = "ICDS-TG-HYD-2022-048",
            villageWard = "Bachupally Ward-2",
            aadhaarLast4 = "4819",
            isDemoChild = true
        ),
        ChildEntity(
            id = "child_ananya",
            name = "Ananya",
            ageYears = 3,
            gender = "F",
            preferredLanguage = "hi",
            baselineNotes = "Active, loves songs and rhymes. Nutrition follow-up scheduled.",
            fatherName = "Sri Rajesh Sharma (Artisan)",
            motherName = "Smt. Sunita Sharma",
            enrollmentId = "ICDS-TG-HYD-2023-019",
            villageWard = "Bachupally Ward-4",
            aadhaarLast4 = "7312",
            isDemoChild = true
        ),
        ChildEntity(
            id = "child_meena",
            name = "Meena",
            ageYears = 2,
            gender = "F",
            preferredLanguage = "te",
            baselineNotes = "Developing gross motor coordination. Enjoys outdoor movement.",
            fatherName = "Sri M. Srinivasulu",
            motherName = "Smt. M. Saraswathi",
            enrollmentId = "ICDS-TG-HYD-2024-006",
            villageWard = "Bachupally Ward-1",
            aadhaarLast4 = "9041",
            isDemoChild = true
        ),
        ChildEntity(
            id = "child_aarav",
            name = "Aarav",
            ageYears = 3,
            gender = "M",
            preferredLanguage = "en",
            baselineNotes = "Enjoys picture cards and sorting objects by size.",
            fatherName = "Sri P. Ramesh",
            motherName = "Smt. P. Anitha",
            enrollmentId = "ICDS-TG-HYD-2023-088",
            villageWard = "Bachupally Ward-3",
            aadhaarLast4 = "6238",
            isDemoChild = true
        )
    )

    private val now = System.currentTimeMillis()
    private val oneDay = 24L * 60 * 60 * 1000

    val growthObservations = listOf(
        // Ravi's longitudinal growth
        GrowthObservationEntity(
            childId = "child_ravi",
            timestampEpochMs = now - (20 * oneDay),
            weightKg = 12.5,
            heightCm = 93.5,
            trajectoryStatus = GrowthStatus.STABLE,
            notes = "Monthly weighing. Height and weight stable."
        ),
        GrowthObservationEntity(
            childId = "child_ravi",
            timestampEpochMs = now - (8 * oneDay),
            weightKg = 12.7,
            heightCm = 94.0,
            trajectoryStatus = GrowthStatus.INCREASING,
            notes = "Positive weight gain. Appetite reported good."
        ),
        GrowthObservationEntity(
            childId = "child_ravi",
            timestampEpochMs = now - (1 * oneDay),
            weightKg = 12.8,
            heightCm = 94.2,
            trajectoryStatus = GrowthStatus.INCREASING,
            notes = "Weight steady at 12.8 kg."
        ),
        // Ananya
        GrowthObservationEntity(
            childId = "child_ananya",
            timestampEpochMs = now - (5 * oneDay),
            weightKg = 11.2,
            heightCm = 89.0,
            trajectoryStatus = GrowthStatus.STABLE,
            notes = "Regular measurement."
        )
    )

    val nutritionObservations = listOf(
        NutritionObservationEntity(
            childId = "child_ravi",
            timestampEpochMs = now - (8 * oneDay),
            mealType = "Lunch",
            foodsEaten = "Rice, Dal, boiled egg",
            appetiteNotes = "Finished full meal happily"
        ),
        NutritionObservationEntity(
            childId = "child_ravi",
            timestampEpochMs = now - (1 * oneDay),
            mealType = "Lunch",
            foodsEaten = "Rice, Sambar, Banana",
            appetiteNotes = "Ate well, asked for extra banana"
        ),
        NutritionObservationEntity(
            childId = "child_ananya",
            timestampEpochMs = now - (2 * oneDay),
            mealType = "Morning Snack",
            foodsEaten = "Chana sundal",
            appetiteNotes = "Ate half portion, needed encouragement"
        )
    )

    val developmentObservations = listOf(
        // Ravi's historical observations showing the sequential instruction pattern
        DevelopmentObservationEntity(
            childId = "child_ravi",
            timestampEpochMs = now - (14 * oneDay),
            domain = DevelopmentDomain.LANGUAGE_COGNITIVE,
            observationText = "Participated quietly during group circle. Needed verbal help with two-step instruction (pick up book and place on shelf).",
            rawWorkerSpeech = "రవి రెండు సూచనలు ఒకేసారి అర్థం చేసుకోవడానికి కొంచెం కష్టపడ్డాడు",
            needsFollowUp = true
        ),
        DevelopmentObservationEntity(
            childId = "child_ravi",
            timestampEpochMs = now - (8 * oneDay),
            domain = DevelopmentDomain.LANGUAGE_COGNITIVE,
            observationText = "Completed single instruction with ease, but paused and hesitated when given consecutive instructions.",
            rawWorkerSpeech = "రెండు సూచనలు వరుసగా ఇచ్చినప్పుడు ఆగిపోయాడు",
            needsFollowUp = true
        ),
        DevelopmentObservationEntity(
            childId = "child_ravi",
            timestampEpochMs = now - (3 * oneDay),
            domain = DevelopmentDomain.LANGUAGE_COGNITIVE,
            observationText = "Identified colors accurately (red, yellow, blue). Highly engaged when using concrete physical items like cups and caps.",
            rawWorkerSpeech = "రంగులు చక్కగా గుర్తించాడు, వస్తువులతో బాగా ఆడాడు",
            needsFollowUp = false
        ),
        // Ananya
        DevelopmentObservationEntity(
            childId = "child_ananya",
            timestampEpochMs = now - (3 * oneDay),
            domain = DevelopmentDomain.SOCIO_EMOTIONAL,
            observationText = "Shared crayons with peer during free play without prompting.",
            rawWorkerSpeech = "अनन्या ने खुद अपनी रंगीन पेंसिल सहेली को दी",
            needsFollowUp = false
        )
    )

    val activities = listOf(
        ActivityEntity(
            id = "act_color_cups",
            title = "Colour Cup Challenge",
            domain = DevelopmentDomain.LANGUAGE_COGNITIVE,
            minAgeYears = 3,
            maxAgeYears = 5,
            durationMinutes = 5,
            requiredMaterialsList = "Cups, Bottle caps",
            stepsList = "Place a red cup and a blue cup on the mat|Ask the child: 'Put two caps in the red cup, then bring the blue cup to me'|Smile and observe if both steps are done in order without repeating",
            whatToObserve = "Can the child hold two sequential instructions in memory and execute them with physical objects?",
            parentExplanationEn = "Today Ravi played a 5-minute cup game where he practiced following two small actions in order. You can ask him at home: 'Pick up your spoon and put it on the plate'.",
            parentExplanationHi = "आज रवि ने 5 मिनट का एक खेल खेला जिसमें उसने दो निर्देश एक के बाद एक पूरे करने का अभ्यास किया। आप घर पर कह सकते हैं: 'चम्मच उठाओ और थाली में रखो'।",
            parentExplanationTe = "ఈరోజు రవి 5 నిమిషాల కప్పుల ఆట ఆడాడు. వరుసగా రెండు పనులు చేయడం సాధన చేశాడు. మీరు ఇంట్లో కూడా: 'స్పూన్ తీసి ప్లేటులో పెట్టు' వంటి సరళమైన పనులు చెప్పి ప్రోత్సహించవచ్చు."
        ),
        ActivityEntity(
            id = "act_object_sorting",
            title = "Big & Small Leaf Matching",
            domain = DevelopmentDomain.LANGUAGE_COGNITIVE,
            minAgeYears = 2,
            maxAgeYears = 4,
            durationMinutes = 5,
            requiredMaterialsList = "Leaves, Paper",
            stepsList = "Collect 3 large leaves and 3 small leaves|Draw two circles on paper (one big, one small)|Ask child to sort the leaves into their matching circle",
            whatToObserve = "Does the child differentiate relative size and sort independently?",
            parentExplanationEn = "Today we practiced sorting leaves by size. At home, ask the child to help separate big and small onions or spoons.",
            parentExplanationHi = "आज हमने पत्तों को छोटे-बड़े आकार में अलग करने का खेल खेला। घर पर आप बड़े और छोटे चम्मच अलग करवा सकते हैं।",
            parentExplanationTe = "ఈరోజు పెద్ద మరియు చిన్న ఆకులను వేరు చేసే ఆట ఆడాము. ఇంట్లో కూడా పెద్ద స్పూన్లు, చిన్న స్పూన్లను వేరు చేసే ఆట ఆడించవచ్చు."
        ),
        ActivityEntity(
            id = "act_thread_beads",
            title = "Bottle Cap Threading",
            domain = DevelopmentDomain.MOTOR,
            minAgeYears = 3,
            maxAgeYears = 5,
            durationMinutes = 7,
            requiredMaterialsList = "Bottle caps, Thread",
            stepsList = "Use caps with small holes or empty thread spools|Guide the child to thread string through 3 items|Praise steady finger control",
            whatToObserve = "Pincer grasp and hand-eye coordination stability.",
            parentExplanationEn = "Your child practiced threading thread through bottle caps. This builds finger strength for holding pencils later.",
            parentExplanationHi = "बच्चे ने धागे में ढक्कन पिरोने का अभ्यास किया। इससे आगे चलकर पेंसिल पकड़ने में मदद मिलती है।",
            parentExplanationTe = "పిల్లవాడు దారంలో మూతలను గుచ్చడం సాధన చేశాడు. ఇది భవిష్యత్తులో పెన్సిల్ పట్టుకోవడానికి చేతి వేళ్లకు బలాన్ని ఇస్తుంది."
        ),
        ActivityEntity(
            id = "act_story_sequence",
            title = "Two-Picture Story Clues",
            domain = DevelopmentDomain.LANGUAGE_COGNITIVE,
            minAgeYears = 3,
            maxAgeYears = 5,
            durationMinutes = 5,
            requiredMaterialsList = "Paper, Chalk",
            stepsList = "Draw sun rising, then child brushing teeth on slate|Ask child: 'What do we do first? What do we do next?'|Encourage child to explain sequence in their words",
            whatToObserve = "Can the child verbalize what happens first and what happens next?",
            parentExplanationEn = "We talked about what we do first and next in the morning. Ask your child what happens after bath time.",
            parentExplanationHi = "हमने बात की कि सुबह पहले क्या करते हैं और फिर क्या। घर पर भी ऐसे सवाल पूछें।",
            parentExplanationTe = "ఉదయం మొదట ఏమి చేస్తాము, తర్వాత ఏమి చేస్తాము అనే వరుసక్రమం మాట్లాడాము."
        )
    )

    val activityAttempts = listOf(
        ActivityAttemptEntity(
            childId = "child_ravi",
            activityId = "act_object_sorting",
            timestampEpochMs = now - (7 * oneDay),
            outcome = ActivityOutcome.NEEDED_HELP,
            workerObservationNotes = "Ravi hesitated with leaf sorting when multiple sizes were mixed. Needed prompt.",
            materialsUsed = "Leaves, Paper"
        ),
        ActivityAttemptEntity(
            childId = "child_ravi",
            activityId = "act_color_cups",
            timestampEpochMs = now - (3 * oneDay),
            outcome = ActivityOutcome.INDEPENDENT,
            workerObservationNotes = "Completed object matching game independently using cups and colored caps with high enthusiasm.",
            materialsUsed = "Cups, Bottle caps"
        )
    )

    val materials = listOf(
        MaterialInventoryEntity("mat_cups", "Plastic / Paper Cups", MaterialLocation.BOTH, true),
        MaterialInventoryEntity("mat_spoons", "Spoons (Steel / Plastic)", MaterialLocation.BOTH, true),
        MaterialInventoryEntity("mat_caps", "Clean Bottle Caps", MaterialLocation.BOTH, true),
        MaterialInventoryEntity("mat_paper", "Scrap / Plain Paper", MaterialLocation.BOTH, true),
        MaterialInventoryEntity("mat_thread", "Thread / String", MaterialLocation.BOTH, true),
        MaterialInventoryEntity("mat_boxes", "Small Cardboard Boxes", MaterialLocation.BOTH, true),
        MaterialInventoryEntity("mat_chalk", "Chalk / Slate", MaterialLocation.ANGANWADI, true),
        MaterialInventoryEntity("mat_leaves", "Leaves / Twigs", MaterialLocation.BOTH, true),
        MaterialInventoryEntity("mat_pebbles", "Smooth Pebbles", MaterialLocation.BOTH, true)
    )
}
