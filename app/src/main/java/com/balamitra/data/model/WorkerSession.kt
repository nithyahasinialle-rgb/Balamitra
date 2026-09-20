package com.balamitra.data.model

data class AnganwadiWorker(
    val workerId: String,
    val name: String,
    val role: String = "Anganwadi Worker (AWW)",
    val centerCode: String,
    val centerName: String,
    val sector: String,
    val mandal: String,
    val district: String,
    val state: String,
    val latitude: Double,
    val longitude: Double,
    val ashaWorkerName: String = "Smt. K. Parvathi (ASHA)",
    val phoneNumber: String = "+91 98480 12345"
)

data class CenterDailyStatus(
    val isOpenToday: Boolean = true,
    val registeredChildrenCount: Int = 22,
    val morningAttendanceCount: Int = 18,
    val hotCookedMealDistributed: Boolean = true,
    val mealMenu: String = "Rice, Sambar, Boiled Egg & Banana",
    val thrDistributionDay: String = "Tuesday",
    val gpsGeotagged: Boolean = true
)

object DefaultWorkerSession {
    val defaultWorker = AnganwadiWorker(
        workerId = "AWW-TG-HYD-108",
        name = "Smt. Lakshmi Devi",
        centerCode = "AWC-TG-HYD-BCH-042",
        centerName = "Bachupally Anganwadi Kendram",
        sector = "Bachupally Sector-1",
        mandal = "Bachupally",
        district = "Medchal-Malkajgiri (Hyderabad)",
        state = "Telangana",
        latitude = 17.5385,
        longitude = 78.3610,
        ashaWorkerName = "Smt. K. Parvathi (ASHA)"
    )

    val defaultDailyStatus = CenterDailyStatus()
}
