package com.balamitra.data.model

data class PoshanTrackerRecord(
    val beneficiaryId: String,
    val name: String,
    val ageMonths: Int,
    val gender: String,
    val fatherName: String,
    val motherName: String,
    val aadhaarLast4: String,
    val weightKg: Double,
    val heightCm: Double,
    val growthCategory: String,
    val wastingCategory: String,
    val stuntingCategory: String,
    val thrDistributed: String = "Yes",
    val hotCookedMeal: String = "Yes",
    val syncStatus: String = "Verified"
)

object PoshanTrackerRepository {
    val sampleRecords = listOf(
        PoshanTrackerRecord("TG-HYD-BCH-01", "Raju", 14, "Male", "Sri K. Venkata Rao", "Smt. K. Lakshmi", "4819", 9.2, 74.5, "Normal", "Normal", "Normal"),
        PoshanTrackerRecord("TG-HYD-BCH-02", "Lakshmi", 18, "Female", "Sri T. Ramana", "Smt. T. Anasuya", "3921", 10.1, 79.0, "Normal", "Normal", "Normal"),
        PoshanTrackerRecord("TG-HYD-BCH-03", "Chitti", 24, "Female", "Sri M. Srinivasulu", "Smt. M. Parvathi", "7742", 11.0, 84.5, "MAM", "Moderate Wasting", "Normal"),
        PoshanTrackerRecord("TG-HYD-BCH-04", "Suresh", 28, "Male", "Sri B. Nageswara Rao", "Smt. B. Padmavati", "8814", 11.8, 87.0, "Normal", "Normal", "Normal"),
        PoshanTrackerRecord("TG-HYD-BCH-05", "Anita", 32, "Female", "Sri S. Mallesh", "Smt. S. Renuka", "6195", 12.0, 90.5, "Normal", "Normal", "Normal"),
        PoshanTrackerRecord("TG-HYD-BCH-06", "Ramesh", 36, "Male", "Sri J. Yadaiah", "Smt. J. Komalatha", "5523", 13.2, 94.0, "Normal", "Normal", "Normal"),
        PoshanTrackerRecord("TG-HYD-BCH-07", "Bhavani", 40, "Female", "Sri G. Chandraiah", "Smt. G. Sarada", "4412", 13.8, 96.5, "Normal", "Normal", "Normal"),
        PoshanTrackerRecord("TG-HYD-BCH-08", "Gopi", 44, "Male", "Sri V. Krishna", "Smt. V. Satyavati", "9931", 14.1, 98.0, "MAM", "Moderate Wasting", "Normal"),
        PoshanTrackerRecord("TG-HYD-BCH-09", "Durga", 48, "Female", "Sri D. Satyanarayana", "Smt. D. Mangamma", "7729", 15.0, 102.0, "Normal", "Normal", "Normal"),
        PoshanTrackerRecord("TG-HYD-BCH-10", "Shiva", 52, "Male", "Sri P. Shankaraiah", "Smt. P. Vijayalakshmi", "6318", 15.6, 104.5, "Normal", "Normal", "Normal"),
        PoshanTrackerRecord("TG-HYD-BCH-11", "Radha", 54, "Female", "Sri K. Sambaiah", "Smt. K. Lalitha", "2156", 15.4, 105.0, "SAM", "Severe Wasting", "Stunted"),
        PoshanTrackerRecord("TG-HYD-BCH-12", "Mahesh", 58, "Male", "Sri Ch. Ramulu", "Smt. Ch. Devamma", "8403", 17.5, 109.0, "Normal", "Normal", "Normal"),
        PoshanTrackerRecord("TG-HYD-BCH-13", "Anji", 62, "Male", "Sri K. Balaswamy", "Smt. K. Nagamma", "1945", 18.0, 111.5, "Normal", "Normal", "Normal"),
        PoshanTrackerRecord("TG-HYD-BCH-14", "Venkatesh", 66, "Male", "Sri R. Lingaiah", "Smt. R. Venkatamma", "3377", 18.8, 113.0, "Normal", "Normal", "Normal"),
        PoshanTrackerRecord("TG-HYD-BCH-15", "Swapna", 70, "Female", "Sri B. Thirupathi", "Smt. B. Pushpalatha", "9261", 19.5, 115.5, "Normal", "Normal", "Normal")
    )

    val csvHeader: String = "Beneficiary_ID,Child_Name,Age_Months,Gender,Father_Name,Mother_Name,Aadhaar_Last4,Weight_Kg,Height_Cm,Growth_Status,Wasting_Category,Stunting_Category,THR_Given,Meal_Provided,Sync_Status"

    fun generateCsvString(): String {
        val sb = StringBuilder()
        sb.append(csvHeader).append("\n")
        sampleRecords.forEach { r ->
            sb.append("${r.beneficiaryId},${r.name},${r.ageMonths},${r.gender},${r.fatherName},${r.motherName},${r.aadhaarLast4},${r.weightKg},${r.heightCm},${r.growthCategory},${r.wastingCategory},${r.stuntingCategory},${r.thrDistributed},${r.hotCookedMeal},${r.syncStatus}")
                .append("\n")
        }
        return sb.toString()
    }
}
