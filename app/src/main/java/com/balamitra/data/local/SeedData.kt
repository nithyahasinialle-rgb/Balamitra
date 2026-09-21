package com.balamitra.data.local

import com.balamitra.core.model.ActivityOutcome
import com.balamitra.core.model.DevelopmentDomain
import com.balamitra.core.model.GrowthStatus
import com.balamitra.core.model.MaterialLocation

object SeedData {
    val children = listOf(
        ChildEntity(
            id = "child_raju",
            name = "Raju",
            ageYears = 1,
            gender = "M",
            preferredLanguage = "te",
            baselineNotes = "14 months old. Smiling, responds to name, grasps rattle firmly, walking with support.",
            fatherName = "Sri G. Venkatesh (Daily Wage)",
            motherName = "Smt. G. Lakshmi",
            enrollmentId = "ICDS-TG-HYD-2025-001",
            villageWard = "Bachupally Ward-1",
            aadhaarLast4 = "1024",
            isDemoChild = true
        ),
        ChildEntity(
            id = "child_lakshmi",
            name = "Lakshmi",
            ageYears = 1,
            gender = "F",
            preferredLanguage = "te",
            baselineNotes = "18 months old. Steady walking, points to familiar objects, feeds self with fingers.",
            fatherName = "Sri M. Yadaiah (Auto Driver)",
            motherName = "Smt. M. Renuka",
            enrollmentId = "ICDS-TG-HYD-2025-002",
            villageWard = "Bachupally Ward-2",
            aadhaarLast4 = "3819",
            isDemoChild = true
        ),
        ChildEntity(
            id = "child_chitti",
            name = "Chitti",
            ageYears = 2,
            gender = "F",
            preferredLanguage = "te",
            baselineNotes = "24 months old. Loves nursery action songs, jumps with both feet, uses 2-word sentences.",
            fatherName = "Sri K. Balu (Painter)",
            motherName = "Smt. K. Padma",
            enrollmentId = "ICDS-TG-HYD-2024-003",
            villageWard = "Bachupally Ward-1",
            aadhaarLast4 = "5521",
            isDemoChild = true
        ),
        ChildEntity(
            id = "child_suresh",
            name = "Suresh",
            ageYears = 2,
            gender = "M",
            preferredLanguage = "te",
            baselineNotes = "28 months old. Enjoys stacking small bowls and sorting wooden blocks by shape.",
            fatherName = "Sri B. Narayana (Carpenter)",
            motherName = "Smt. B. Manjula",
            enrollmentId = "ICDS-TG-HYD-2024-004",
            villageWard = "Bachupally Ward-3",
            aadhaarLast4 = "9042",
            isDemoChild = true
        ),
        ChildEntity(
            id = "child_anita",
            name = "Anita",
            ageYears = 2,
            gender = "F",
            preferredLanguage = "te",
            baselineNotes = "30 months old. Very observant during circle time, walks backwards steadily, mimics sounds.",
            fatherName = "Sri S. Ramu (Vegetable Vendor)",
            motherName = "Smt. S. Kavitha",
            enrollmentId = "ICDS-TG-HYD-2024-005",
            villageWard = "Bachupally Ward-2",
            aadhaarLast4 = "7731",
            isDemoChild = true
        ),
        ChildEntity(
            id = "child_ramesh",
            name = "Ramesh",
            ageYears = 3,
            gender = "M",
            preferredLanguage = "te",
            baselineNotes = "36 months old. High energy, stacks 6+ blocks, names primary colors (yellow, red).",
            fatherName = "Sri T. Shankaraiah (Electrician)",
            motherName = "Smt. T. Parvathi",
            enrollmentId = "ICDS-TG-HYD-2023-006",
            villageWard = "Bachupally Ward-4",
            aadhaarLast4 = "4819",
            isDemoChild = true
        ),
        ChildEntity(
            id = "child_bhavani",
            name = "Bhavani",
            ageYears = 3,
            gender = "F",
            preferredLanguage = "te",
            baselineNotes = "40 months old. Enjoys bead stringing, balances on one foot for 3 seconds, counts 1 to 5.",
            fatherName = "Sri V. Mallesh (Plumber)",
            motherName = "Smt. V. Geetha",
            enrollmentId = "ICDS-TG-HYD-2023-007",
            villageWard = "Bachupally Ward-3",
            aadhaarLast4 = "6210",
            isDemoChild = true
        ),
        ChildEntity(
            id = "child_gopi",
            name = "Gopi",
            ageYears = 3,
            gender = "M",
            preferredLanguage = "te",
            baselineNotes = "42 months old. Catches large ball with both hands, matches picture cards of farm animals.",
            fatherName = "Sri D. Krishna (Dairy Worker)",
            motherName = "Smt. D. Radha",
            enrollmentId = "ICDS-TG-HYD-2023-008",
            villageWard = "Bachupally Ward-1",
            aadhaarLast4 = "1945",
            isDemoChild = true
        ),
        ChildEntity(
            id = "child_durga",
            name = "Durga",
            ageYears = 4,
            gender = "F",
            preferredLanguage = "te",
            baselineNotes = "48 months old. Traces circles in sand, sorts lentils by size, hops on one foot.",
            fatherName = "Sri Ch. Nagesh (Tailor)",
            motherName = "Smt. Ch. Sridevi",
            enrollmentId = "ICDS-TG-HYD-2022-009",
            villageWard = "Bachupally Ward-2",
            aadhaarLast4 = "8834",
            isDemoChild = true
        ),
        ChildEntity(
            id = "child_shiva",
            name = "Shiva",
            ageYears = 4,
            gender = "M",
            preferredLanguage = "te",
            baselineNotes = "52 months old. Enjoys paper folding (origami boat), role playing teacher, narrates 3-line story.",
            fatherName = "Sri P. Satyam (Mason)",
            motherName = "Smt. P. Anasuya",
            enrollmentId = "ICDS-TG-HYD-2022-010",
            villageWard = "Bachupally Ward-4",
            aadhaarLast4 = "3391",
            isDemoChild = true
        ),
        ChildEntity(
            id = "child_radha",
            name = "Radha",
            ageYears = 4,
            gender = "F",
            preferredLanguage = "hi",
            baselineNotes = "54 months old. Buttons shirt with large buttons, draws human figures with head and limbs.",
            fatherName = "Sri E. Gopal (Security Guard)",
            motherName = "Smt. E. Pushpa",
            enrollmentId = "ICDS-TG-HYD-2022-011",
            villageWard = "Bachupally Ward-3",
            aadhaarLast4 = "7128",
            isDemoChild = true
        ),
        ChildEntity(
            id = "child_mahesh",
            name = "Mahesh",
            ageYears = 5,
            gender = "M",
            preferredLanguage = "te",
            baselineNotes = "60 months old. Traces Telugu and English letters on slate, cuts paper along straight line.",
            fatherName = "Sri K. Sailu (Mechanic)",
            motherName = "Smt. K. Sujatha",
            enrollmentId = "ICDS-TG-HYD-2021-012",
            villageWard = "Bachupally Ward-1",
            aadhaarLast4 = "5092",
            isDemoChild = true
        ),
        ChildEntity(
            id = "child_anji",
            name = "Anji",
            ageYears = 5,
            gender = "M",
            preferredLanguage = "te",
            baselineNotes = "64 months old. Skips rope rhythmically, cooperative in group tag games, counts up to 15.",
            fatherName = "Sri J. Srinivas (Shop Assistant)",
            motherName = "Smt. J. Bhagya",
            enrollmentId = "ICDS-TG-HYD-2021-013",
            villageWard = "Bachupally Ward-2",
            aadhaarLast4 = "6481",
            isDemoChild = true
        ),
        ChildEntity(
            id = "child_venkatesh",
            name = "Venkatesh",
            ageYears = 5,
            gender = "M",
            preferredLanguage = "te",
            baselineNotes = "68 months old. Solves 6-piece jigsaw puzzles, explains daily routine, simple addition with seeds.",
            fatherName = "Sri R. Yadagiri (Farmer)",
            motherName = "Smt. R. Swaroopa",
            enrollmentId = "ICDS-TG-HYD-2021-014",
            villageWard = "Bachupally Ward-4",
            aadhaarLast4 = "9903",
            isDemoChild = true
        ),
        ChildEntity(
            id = "child_swapna",
            name = "Swapna",
            ageYears = 6,
            gender = "F",
            preferredLanguage = "te",
            baselineNotes = "72 months old. Writes her name on slate, ties simple knots, demonstrates school readiness.",
            fatherName = "Sri N. Chandraiah (Weaver)",
            motherName = "Smt. N. Sravanthi",
            enrollmentId = "ICDS-TG-HYD-2020-015",
            villageWard = "Bachupally Ward-3",
            aadhaarLast4 = "2215",
            isDemoChild = true
        )
    )

    private val now = System.currentTimeMillis()
    private val oneDay = 86400000L

    val growthObservations = listOf(
        GrowthObservationEntity(childId = "child_raju", timestampEpochMs = now - (60 * oneDay), weightKg = 9.2, heightCm = 74.0, trajectoryStatus = GrowthStatus.STABLE, notes = "Healthy early toddler growth"),
        GrowthObservationEntity(childId = "child_raju", timestampEpochMs = now - (30 * oneDay), weightKg = 9.5, heightCm = 75.5, trajectoryStatus = GrowthStatus.STABLE, notes = "Steady linear gain"),
        GrowthObservationEntity(childId = "child_raju", timestampEpochMs = now, weightKg = 9.8, heightCm = 76.5, trajectoryStatus = GrowthStatus.STABLE, notes = "Active, eating soft khichdi"),

        GrowthObservationEntity(childId = "child_lakshmi", timestampEpochMs = now - (60 * oneDay), weightKg = 9.6, heightCm = 78.5, trajectoryStatus = GrowthStatus.STABLE, notes = "Age appropriate growth"),
        GrowthObservationEntity(childId = "child_lakshmi", timestampEpochMs = now, weightKg = 10.2, heightCm = 81.0, trajectoryStatus = GrowthStatus.STABLE, notes = "Healthy weight and height"),

        GrowthObservationEntity(childId = "child_chitti", timestampEpochMs = now - (30 * oneDay), weightKg = 11.0, heightCm = 85.0, trajectoryStatus = GrowthStatus.STABLE, notes = "Normal 2y baseline"),
        GrowthObservationEntity(childId = "child_chitti", timestampEpochMs = now, weightKg = 11.4, heightCm = 86.5, trajectoryStatus = GrowthStatus.STABLE, notes = "Good appetite for Anganwadi meal"),

        GrowthObservationEntity(childId = "child_suresh", timestampEpochMs = now - (60 * oneDay), weightKg = 11.8, heightCm = 87.0, trajectoryStatus = GrowthStatus.STABLE, notes = "Baseline check"),
        GrowthObservationEntity(childId = "child_suresh", timestampEpochMs = now, weightKg = 12.1, heightCm = 89.0, trajectoryStatus = GrowthStatus.STABLE, notes = "Active motor development"),

        GrowthObservationEntity(childId = "child_anita", timestampEpochMs = now - (45 * oneDay), weightKg = 11.5, heightCm = 88.5, trajectoryStatus = GrowthStatus.STABLE, notes = "Slightly low weight, monitored"),
        GrowthObservationEntity(childId = "child_anita", timestampEpochMs = now, weightKg = 12.0, heightCm = 90.5, trajectoryStatus = GrowthStatus.STABLE, notes = "Recovered well with egg and dal"),

        GrowthObservationEntity(childId = "child_ramesh", timestampEpochMs = now - (60 * oneDay), weightKg = 12.5, heightCm = 92.0, trajectoryStatus = GrowthStatus.STABLE, notes = "Baseline 3y record"),
        GrowthObservationEntity(childId = "child_ramesh", timestampEpochMs = now - (30 * oneDay), weightKg = 12.9, heightCm = 93.0, trajectoryStatus = GrowthStatus.STABLE, notes = "Plateaued slightly"),
        GrowthObservationEntity(childId = "child_ramesh", timestampEpochMs = now, weightKg = 13.2, heightCm = 94.0, trajectoryStatus = GrowthStatus.STABLE, notes = "Weight gained steadily"),

        GrowthObservationEntity(childId = "child_bhavani", timestampEpochMs = now - (30 * oneDay), weightKg = 13.2, heightCm = 95.0, trajectoryStatus = GrowthStatus.STABLE, notes = "Good progress"),
        GrowthObservationEntity(childId = "child_bhavani", timestampEpochMs = now, weightKg = 13.8, heightCm = 96.5, trajectoryStatus = GrowthStatus.STABLE, notes = "Very energetic"),

        GrowthObservationEntity(childId = "child_gopi", timestampEpochMs = now, weightKg = 14.1, heightCm = 98.0, trajectoryStatus = GrowthStatus.STABLE, notes = "Normal growth curve"),
        GrowthObservationEntity(childId = "child_durga", timestampEpochMs = now, weightKg = 15.0, heightCm = 102.0, trajectoryStatus = GrowthStatus.STABLE, notes = "Healthy 4y child"),
        GrowthObservationEntity(childId = "child_shiva", timestampEpochMs = now, weightKg = 15.6, heightCm = 104.5, trajectoryStatus = GrowthStatus.STABLE, notes = "Normal height and weight"),
        GrowthObservationEntity(childId = "child_radha", timestampEpochMs = now, weightKg = 15.4, heightCm = 105.0, trajectoryStatus = GrowthStatus.STABLE, notes = "Nutrition follow-up ongoing"),
        GrowthObservationEntity(childId = "child_mahesh", timestampEpochMs = now, weightKg = 17.5, heightCm = 109.0, trajectoryStatus = GrowthStatus.STABLE, notes = "Strong growth trajectory"),
        GrowthObservationEntity(childId = "child_anji", timestampEpochMs = now, weightKg = 18.0, heightCm = 111.5, trajectoryStatus = GrowthStatus.STABLE, notes = "Active preschooler"),
        GrowthObservationEntity(childId = "child_venkatesh", timestampEpochMs = now, weightKg = 18.8, heightCm = 113.0, trajectoryStatus = GrowthStatus.STABLE, notes = "Ready for primary school transition"),
        GrowthObservationEntity(childId = "child_swapna", timestampEpochMs = now, weightKg = 19.5, heightCm = 115.5, trajectoryStatus = GrowthStatus.STABLE, notes = "School readiness assessment complete")
    )

    
    val nutritionObservations = listOf(
        NutritionObservationEntity(childId = "child_raju", timestampEpochMs = now - (8 * oneDay), mealType = "Lunch", foodsEaten = "Rice, Dal, boiled egg", appetiteNotes = "Finished full meal happily"),
        NutritionObservationEntity(childId = "child_lakshmi", timestampEpochMs = now - (1 * oneDay), mealType = "Lunch", foodsEaten = "Rice, Sambar, Banana", appetiteNotes = "Ate well, asked for extra banana"),
        NutritionObservationEntity(childId = "child_chitti", timestampEpochMs = now - (2 * oneDay), mealType = "Morning Snack", foodsEaten = "Chana sundal", appetiteNotes = "Ate half portion, needed encouragement"),
        NutritionObservationEntity(childId = "child_suresh", timestampEpochMs = now - (1 * oneDay), mealType = "Lunch", foodsEaten = "Rice, Dal, Egg", appetiteNotes = "Good appetite"),
        NutritionObservationEntity(childId = "child_anita", timestampEpochMs = now - (2 * oneDay), mealType = "Morning Snack", foodsEaten = "Boiled egg, Milk", appetiteNotes = "Improved nutrition intake")
    )

    val developmentObservations = listOf(
        DevelopmentObservationEntity(childId = "child_raju", timestampEpochMs = now - (7 * oneDay), domain = DevelopmentDomain.MOTOR, observationText = "Grasping rattles with palm, rolling over easily", rawWorkerSpeech = null, needsFollowUp = false),
        DevelopmentObservationEntity(childId = "child_lakshmi", timestampEpochMs = now - (5 * oneDay), domain = DevelopmentDomain.MOTOR, observationText = "Walking steadily without falling, climbing low steps", rawWorkerSpeech = null, needsFollowUp = false),
        DevelopmentObservationEntity(childId = "child_chitti", timestampEpochMs = now - (4 * oneDay), domain = DevelopmentDomain.LANGUAGE_COGNITIVE, observationText = "Repeats simple animal sounds (cow, dog) with joy", rawWorkerSpeech = null, needsFollowUp = false),
        DevelopmentObservationEntity(childId = "child_suresh", timestampEpochMs = now - (3 * oneDay), domain = DevelopmentDomain.LANGUAGE_COGNITIVE, observationText = "Identifies circle and square wooden blocks accurately", rawWorkerSpeech = null, needsFollowUp = false),
        DevelopmentObservationEntity(childId = "child_anita", timestampEpochMs = now - (6 * oneDay), domain = DevelopmentDomain.MOTOR, observationText = "Walks backward 3 steps when guided during rhyme game", rawWorkerSpeech = null, needsFollowUp = false),
        DevelopmentObservationEntity(childId = "child_ramesh", timestampEpochMs = now - (2 * oneDay), domain = DevelopmentDomain.LANGUAGE_COGNITIVE, observationText = "Stacks 6 wooden blocks independently and names yellow/red cups", rawWorkerSpeech = null, needsFollowUp = false),
        DevelopmentObservationEntity(childId = "child_bhavani", timestampEpochMs = now - (3 * oneDay), domain = DevelopmentDomain.MOTOR, observationText = "Strings large wooden beads on nylon cord steadily", rawWorkerSpeech = null, needsFollowUp = false),
        DevelopmentObservationEntity(childId = "child_gopi", timestampEpochMs = now - (5 * oneDay), domain = DevelopmentDomain.SOCIO_EMOTIONAL, observationText = "Shares toy cups with peers during free play", rawWorkerSpeech = null, needsFollowUp = false),
        DevelopmentObservationEntity(childId = "child_durga", timestampEpochMs = now - (4 * oneDay), domain = DevelopmentDomain.LANGUAGE_COGNITIVE, observationText = "Traces circle in sand and sorts big vs small lentils", rawWorkerSpeech = null, needsFollowUp = false),
        DevelopmentObservationEntity(childId = "child_shiva", timestampEpochMs = now - (2 * oneDay), domain = DevelopmentDomain.LANGUAGE_COGNITIVE, observationText = "Folds paper boat with assistance, tells 3-sentence story", rawWorkerSpeech = null, needsFollowUp = false),
        DevelopmentObservationEntity(childId = "child_radha", timestampEpochMs = now - (3 * oneDay), domain = DevelopmentDomain.MOTOR, observationText = "Buttons large buttons independently", rawWorkerSpeech = null, needsFollowUp = false),
        DevelopmentObservationEntity(childId = "child_mahesh", timestampEpochMs = now - (1 * oneDay), domain = DevelopmentDomain.LANGUAGE_COGNITIVE, observationText = "Traces Telugu vowels on slate with chalk", rawWorkerSpeech = null, needsFollowUp = false),
        DevelopmentObservationEntity(childId = "child_anji", timestampEpochMs = now - (2 * oneDay), domain = DevelopmentDomain.MOTOR, observationText = "Skips rope on both feet and hops 5 times", rawWorkerSpeech = null, needsFollowUp = false),
        DevelopmentObservationEntity(childId = "child_venkatesh", timestampEpochMs = now - (3 * oneDay), domain = DevelopmentDomain.LANGUAGE_COGNITIVE, observationText = "Solves 6-piece jigsaw puzzle in under 2 minutes", rawWorkerSpeech = null, needsFollowUp = false),
        DevelopmentObservationEntity(childId = "child_swapna", timestampEpochMs = now - (1 * oneDay), domain = DevelopmentDomain.LANGUAGE_COGNITIVE, observationText = "Writes her first name on slate cleanly", rawWorkerSpeech = null, needsFollowUp = false)
    )

    val activities = listOf(
        // Age 1 - 2 Years Activities
        ActivityEntity(
            id = "act_rattle_rhythm",
            title = "Rattle & Clap Rhythm",
            domain = DevelopmentDomain.MOTOR,
            minAgeYears = 1,
            maxAgeYears = 2,
            durationMinutes = 4,
            requiredMaterialsList = "Rattle or Bottle with dry lentils, Hands",
            stepsList = "Shake rattle gently to left and right|Observe if child tracks with head and eyes|Clap hands together and invite child to clap",
            whatToObserve = "Does the child turn their head to track the sound and attempt to clap with both palms?",
            parentExplanationEn = "Today we practiced hearing and clapping to sounds. At home, clap your hands while singing to encourage hand coordination.",
            parentExplanationHi = "आज हमने ताली बजाने और आवाज़ सुनने का अभ्यास किया। घर पर बच्चे के सामने ताली बजाकर गाने गाएं।",
            parentExplanationTe = "ఈరోజు శబ్దం వినడం, చప్పట్లు కొట్టడం ప్రాక్టీస్ చేశాము. ఇంట్లో కూడా పాటకు చప్పట్లు కొట్టించండి."
        ),
        ActivityEntity(
            id = "act_roll_soft_ball",
            title = "Gentle Rolling Ball",
            domain = DevelopmentDomain.MOTOR,
            minAgeYears = 1,
            maxAgeYears = 2,
            durationMinutes = 5,
            requiredMaterialsList = "Soft cloth ball or sponge ball",
            stepsList = "Sit on mat opposite to child|Roll ball gently toward child's hands|Encourage child to push or roll it back",
            whatToObserve = "Can the child stop the rolling ball with hands and push it back forward?",
            parentExplanationEn = "Rolling a ball develops balance and arm strength. Practice rolling small fruits or soft toys at home.",
            parentExplanationHi = "गेंद लुढ़काने से बच्चे का संतुलन और हाथों की ताकत बढ़ती है। घर पर भी हल्के खिलौने लुढ़काने का खेल खेलें।",
            parentExplanationTe = "బంతిని దొర్లించడం వల్ల బ్యాలెన్స్ మరియు చేతుల బలం పెరుగుతుంది. ఇంట్లో కూడా మెత్తటి వస్తువులతో ఈ ఆట ఆడించండి."
        ),

        // Age 2 - 3 Years Activities
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
            parentExplanationHi = "आज हमने बड़े और छोटे पत्तों को अलग करना सीखा। घर पर भी बच्चे से बड़े और छोटे चम्मच अलग करवाएं।",
            parentExplanationTe = "ఈరోజు పెద్ద మరియు చిన్న ఆకులను వేరు చేయడం ప్రాక్టీస్ చేశాము. ఇంట్లో పెద్ద ఉల్లిపాయలు, చిన్న ఉల్లిపాయలు వేరు చేయించండి."
        ),
        ActivityEntity(
            id = "act_animal_sounds",
            title = "Animal Sound Safari",
            domain = DevelopmentDomain.LANGUAGE_COGNITIVE,
            minAgeYears = 2,
            maxAgeYears = 3,
            durationMinutes = 5,
            requiredMaterialsList = "Animal picture cards or gestures",
            stepsList = "Make a cow sound ('Ambaa') and show action|Ask child: 'How does the puppy bark?'|Celebrate each sound the child repeats",
            whatToObserve = "Does the child mimic sounds and connect them with familiar animals?",
            parentExplanationEn = "Making animal sounds expands speech vocabulary and listening skills. Play this game during daily chores.",
            parentExplanationHi = "जानवरों की आवाज़ें निकालने से बच्चे की बोलने की क्षमता बढ़ती है। घर पर भी बिल्ली या गाय की आवाज़ का खेल खेलें।",
            parentExplanationTe = "జంతువుల శబ్దాలు పలకడం వల్ల పిల్లల భాషా పరిజ్ఞానం పెరుగుతుంది. ఇంట్లో ఆవు, పిల్లి శబ్దాలు పలికించండి."
        ),

        // Age 3 - 4 Years Activities
        ActivityEntity(
            id = "act_color_cups",
            title = "Colour Cup Challenge",
            domain = DevelopmentDomain.LANGUAGE_COGNITIVE,
            minAgeYears = 3,
            maxAgeYears = 5,
            durationMinutes = 5,
            requiredMaterialsList = "Cups, Bottle caps",
            stepsList = "Place a red cup and a blue cup on the mat|Ask child: 'Put two caps in the red cup, then bring the blue cup to me'|Smile and observe if both steps are done in order without repeating",
            whatToObserve = "Can the child hold two sequential instructions in memory and execute them with physical objects?",
            parentExplanationEn = "Today the child practiced following two actions in order. You can ask at home: 'Pick up your spoon and put it on the plate'.",
            parentExplanationHi = "आज बच्चे ने दो निर्देशों को क्रम से पूरा करना सीखा। घर पर कहें: 'चम्मच उठाओ और थाली में रखो'।",
            parentExplanationTe = "ఈరోజు రెండు పనులను క్రమంలో చేయడం నేర్చుకున్నారు. ఇంట్లో: 'స్పూన్ తీసి ప్లేటులో పెట్టు' అని చెప్పి చూడండి."
        ),
        ActivityEntity(
            id = "act_thread_beads",
            title = "Bottle Cap Threading",
            domain = DevelopmentDomain.MOTOR,
            minAgeYears = 3,
            maxAgeYears = 5,
            durationMinutes = 7,
            requiredMaterialsList = "Bottle caps with holes, Thread",
            stepsList = "Use caps with small holes or empty thread spools|Guide the child to thread string through 3 items|Praise steady finger control",
            whatToObserve = "Pincer grasp and hand-eye coordination stability.",
            parentExplanationEn = "Your child practiced threading thread through bottle caps. This builds finger strength for holding pencils later.",
            parentExplanationHi = "बच्चे ने धागे में ढक्कन पिरोने का अभ्यास किया। इससे भविष्य में पेंसिल पकड़ने के लिए उंगलियों में मजबूती आती है।",
            parentExplanationTe = "దారంలో మూతలు గుచ్చడం సాధన చేశారు. ఇది భవిష్యత్తులో పెన్సిల్ సరిగ్గా పట్టుకోవడానికి చేతి బలాన్ని ఇస్తుంది."
        ),

        // Age 4 - 5 Years Activities
        ActivityEntity(
            id = "act_sand_shapes",
            title = "Sand & Rangoli Shapes",
            domain = DevelopmentDomain.LANGUAGE_COGNITIVE,
            minAgeYears = 4,
            maxAgeYears = 5,
            durationMinutes = 6,
            requiredMaterialsList = "Clean sand or dry rice flour tray",
            stepsList = "Draw a circle in the sand with your index finger|Ask child to draw a circle next to it|Next draw a triangle or sun and ask child to copy",
            whatToObserve = "Can the child replicate geometric shapes with their index finger?",
            parentExplanationEn = "Drawing shapes in sand strengthens hand muscles and spatial reasoning before formal writing starts.",
            parentExplanationHi = "रेत या रंगोली में आकृतियां बनाना बच्चों को लिखना सीखने से पहले उंगलियों को मजबूत बनाता है।",
            parentExplanationTe = "ఇసుకలో లేదా పిండిలో ఆకారాలు గీయడం వల్ల రాయడానికి అవసరమైన వేళ్ల కండరాలు దృఢపడతాయి."
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
            parentExplanationHi = "हमने सुबह के कामों के क्रम पर बात की। घर पर भी बच्चे से पूछें कि नहाने के बाद क्या करते हैं।",
            parentExplanationTe = "ఉదయం మొదట ఏం చేస్తాం, తరువాత ఏం చేస్తాం అని అడిగాము. ఇంట్లో స్నానం తర్వాత ఏం చేస్తారో చెప్పించండి."
        ),

        // Age 5 - 6 Years Activities (School Readiness)
        ActivityEntity(
            id = "act_letter_tracing",
            title = "Slate Letter Tracing & Sounds",
            domain = DevelopmentDomain.LANGUAGE_COGNITIVE,
            minAgeYears = 5,
            maxAgeYears = 6,
            durationMinutes = 8,
            requiredMaterialsList = "Slate, Chalk",
            stepsList = "Write first letter of child's name in large font on slate|Say the sound clearly and guide child's finger over it|Ask child to trace with chalk 3 times",
            whatToObserve = "Can the child maintain correct finger posture and trace the curve without lifting chalk abruptly?",
            parentExplanationEn = "Tracing letters on slate prepares children for primary school writing and phonics.",
            parentExplanationHi = "स्लेट पर अक्षरों का अभ्यास बच्चे को पहली कक्षा में लिखने और पढ़ने के लिए तैयार करता है।",
            parentExplanationTe = "పలకపై అక్షరాల దిద్దుడు పిల్లలను 1వ తరగతి చదువుకు సిద్ధం చేస్తుంది."
        ),
        ActivityEntity(
            id = "act_rope_jump_count",
            title = "Rope Hop & Count Challenge",
            domain = DevelopmentDomain.MOTOR,
            minAgeYears = 5,
            maxAgeYears = 6,
            durationMinutes = 6,
            requiredMaterialsList = "Jumping rope or chalk line on ground",
            stepsList = "Place rope flat on ground|Ask child to jump forward and backward across rope|Count together: 1, 2, 3... up to 10 with each jump",
            whatToObserve = "Bilateral jumping coordination and synchronous verbal counting.",
            parentExplanationEn = "Hopping while counting builds core physical balance and early math numeracy skills.",
            parentExplanationHi = "कूदते हुए गिनती करना शारीरिक संतुलन और गणित की बुनियादी समझ को बढ़ाता है।",
            parentExplanationTe = "గెంతుతూ లెక్కించడం వల్ల శరీర సమతుల్యత మరియు ప్రాథమిక గణిత నైపుణ్యాలు అలవడతాయి."
        )
    )

    val activityAttempts = listOf(
        ActivityAttemptEntity(
            childId = "child_ramesh",
            activityId = "act_object_sorting",
            timestampEpochMs = now - (7 * oneDay),
            outcome = ActivityOutcome.NEEDED_HELP,
            workerObservationNotes = "Ramesh hesitated with leaf sorting when multiple sizes were mixed. Needed prompt.",
            materialsUsed = "Leaves, Paper"
        ),
        ActivityAttemptEntity(
            childId = "child_ramesh",
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
        MaterialInventoryEntity("mat_pebbles", "Smooth Pebbles", MaterialLocation.BOTH, true),
        MaterialInventoryEntity("mat_rope", "Rope / Cord", MaterialLocation.ANGANWADI, true)
    )
}
