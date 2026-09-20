package com.balamitra.domain

data class ParsedEnrollment(
    val name: String? = null,
    val ageYears: Int? = null,
    val gender: String = "M",
    val weightKg: Double? = null,
    val heightCm: Double? = null,
    val fatherName: String? = null,
    val motherName: String? = null,
    val villageWard: String? = null
)

object ChildEnrollmentVoiceParser {

    private val numWordMap = mapOf(
        "\u0C12\u0C15\u0C1F\u0C3F" to 1, "\u0C30\u0C46\u0C02\u0C21\u0C41" to 2, "\u0C2E\u0C42\u0C21\u0C41" to 3,
        "\u0C28\u0C3E\u0C32\u0C41\u0C17\u0C41" to 4, "\u0C10\u0C26\u0C41" to 5, "\u0C06\u0C30\u0C41" to 6,
        "one" to 1, "two" to 2, "three" to 3, "four" to 4, "five" to 5, "six" to 6,
        "\u090F\u0915" to 1, "\u0926\u094B" to 2, "\u0924\u0940\u0928" to 3, "\u091A\u093E\u0930" to 4,
        "\u092A\u093E\u0901\u091A" to 5, "\u092A\u093E\u0902\u091A" to 5, "\u091B\u0939" to 6
    )

    fun parse(spoken: String): ParsedEnrollment {
        val clean = spoken.trim()
        val lower = clean.lowercase()

        // 1. Child Name Extraction
        val namePrefixRegex = Regex(
            """(?:child\s*name\s*is|student\s*name\s*is|name\s*is|child\s*name|student\s*name|\u0C2C\u0C3E\u0C2C\u0C41\s*\u0C2A\u0C47\u0C30\u0C41|\u0C2A\u0C3E\u0C2A\s*\u0C2A\u0C47\u0C30\u0C41|\u0C2C\u0C3F\u0C21\u0C4D\u0C21\s*\u0C2A\u0C47\u0C30\u0C41|\u0C35\u0C3F\u0C26\u0C4D\u0C2F\u0C3E\u0C30\u0C4D\u0C25\u0C3F\s*\u0C2A\u0C47\u0C30\u0C41|\u0C2A\u0C47\u0C30\u0C41|naam|name|\u0928\u093E\u092E)\s*[:=\-]?\s*([a-zA-Z\u0C00-\u0C7F\u0900-\u097F\s]+?)(?:,|\.|\s+(?:\u0C35\u0C2F\u0C38\u0C4D\u0C38\u0C41|\u0C35\u0C2F\u0C38\u0C41|age|\u0909\u092E\u094D\u0930|years|\u0C38\u0C02\u0C35\u0C24\u0C4D\u0C38\u0C30\u0C3E\u0C32\u0C41|\u0C2C\u0C30\u0C41\u0C35\u0C41|\u0935\u091C\u0928|weight|kg|\u0C15\u0C3F\u0C32\u0C4B\u0C32\u0C41|\u0C15\u0C47\u0C1C\u0C40|father|\u0C24\u0C02\u0C21\u0C4D\u0C30\u0C3F|mother|\u0C24\u0C32\u0C4D\u0C32\u0C3F|girl|boy|gender|\u0C32\u0C3F\u0C02\u0C17\u0C02)|$)""",
            RegexOption.IGNORE_CASE
        )
        val namePrefixMatch = namePrefixRegex.find(clean)?.groupValues?.get(1)?.trim()

        val extractedName: String? = if (!namePrefixMatch.isNullOrBlank() && namePrefixMatch.length in 2..30) {
            namePrefixMatch
        } else {
            val delimiterRegex = Regex(
                """[,.\n]|(?:\s+(?:\d+|\u0C35\u0C2F\u0C38\u0C4D\u0C38\u0C41|\u0C35\u0C2F\u0C38\u0C41|age|\u0909\u092E\u094D\u0930|years|yrs|\u0C38\u0C02\u0C35\u0C24\u0C4D\u0C38\u0C30\u0C3E\u0C32\u0C41|\u0C2C\u0C30\u0C41\u0C35\u0C41|\u0935\u091C\u0928|weight|\u0C15\u0C3F\u0C32\u0C4B\u0C32\u0C41|\u0C15\u0C47\u0C1C\u0C40|kg|\u0C24\u0C02\u0C21\u0C4D\u0C30\u0C3F|father|\u0C24\u0C32\u0C4D\u0C32\u0C3F|mother|\u0C2A\u0C3E\u0C2A|\u0C2C\u0C3E\u0C2C\u0C41|girl|boy|gender|\u0C32\u0C3F\u0C02\u0C17\u0C02))""",
                RegexOption.IGNORE_CASE
            )
            val firstSegment = clean.split(delimiterRegex).firstOrNull()?.trim() ?: ""
            val sanitized = firstSegment
                .replace(Regex("""^(?:\u0C12\u0C15\u0C1F\u0C3F|\u0C15\u0C4A\u0C24\u0C4D\u0C24\s*\u0C2A\u0C3F\u0C32\u0C4D\u0C32\u0C35\u0C3E\u0C21\u0C41|\u0C35\u0C3F\u0C26\u0C4D\u0C2F\u0C3E\u0C30\u0C4D\u0C25\u0C3F|student|child|boy|girl)\s+""", RegexOption.IGNORE_CASE), "")
                .replace(Regex("""\s+\d+$"""), "")
                .trim()
            if (sanitized.isNotBlank() && sanitized.length in 2..30 && !sanitized.matches(Regex("""^\d+.*"""))) {
                sanitized
            } else null
        }

        // 2. Age Years Extraction
        var extractedAge: Int? = null
        val ageDigitRegex = Regex(
            """(?:\u0C35\u0C2F\u0C38\u0C4D\u0C38\u0C41|\u0C35\u0C2F\u0C38\u0C41|age|\u0909\u092E\u094D\u0930|\u0906\u092F\u0941)\s*[:=\-]?\s*(\d{1,2})|(\d{1,2})\s*(?:\u0C38\u0C02\u0C35\u0C24\u0C4D\u0C38\u0C30\u0C3E\u0C32\u0C41|\u0C38\u0C02\u0C35\u0C24\u0C4D\u0C38\u0C30\u0C3E\u0C32|years|yrs|\u0938\u093E\u0932|\u0935\u0930\u094D\u0937)""",
            RegexOption.IGNORE_CASE
        )
        val ageMatch = ageDigitRegex.find(clean)
        if (ageMatch != null) {
            val numStr = ageMatch.groupValues[1].ifEmpty { ageMatch.groupValues[2] }
            extractedAge = numStr.toIntOrNull()
        }
        if (extractedAge == null) {
            for ((word, num) in numWordMap) {
                if (clean.contains(word)) {
                    extractedAge = num
                    break
                }
            }
        }
        if (extractedAge == null) {
            val singleDigit = Regex("""\b([1-6])\b""").find(clean)?.groupValues?.get(1)
            extractedAge = singleDigit?.toIntOrNull()
        }

        // 3. Gender Extraction
        val extractedGender = if (
            lower.contains("female") || lower.contains("girl") || clean.contains("\u0C2A\u0C3E\u0C2A") ||
            clean.contains("\u0C2C\u0C3E\u0C32\u0C3F\u0C15") || clean.contains("\u0C06\u0C21") ||
            clean.contains("\u0932\u0921\u093C\u0915\u0940") || clean.contains("\u0938\u094D\u0924\u094D\u0930\u0940")
        ) {
            "F"
        } else {
            "M"
        }

        // 4. Weight Extraction (Kg)
        var extractedWeight: Double? = null
        val weightRegex = Regex(
            """(?:\u0C2C\u0C30\u0C41\u0C35\u0C41|weight|\u0935\u091C\u0928|wt)\s*[:=\-]?\s*(\d{1,2}(?:\.\d{1,2})?)|(\d{1,2}(?:\.\d{1,2})?)\s*(?:\u0C15\u0C3F\u0C32\u0C4B\u0C32\u0C41|\u0C15\u0C47\u0C1C\u0C40\u0C32\u0C41|\u0C15\u0C47\u0C1C\u0C40|kg|kgs|\u0915\u093F\u0932\u094B)""",
            RegexOption.IGNORE_CASE
        )
        val weightMatch = weightRegex.find(clean)
        if (weightMatch != null) {
            val numStr = weightMatch.groupValues[1].ifEmpty { weightMatch.groupValues[2] }
            val w = numStr.toDoubleOrNull()
            if (w != null && w in 4.0..45.0) extractedWeight = w
        }

        // 5. Height Extraction (Cm)
        var extractedHeight: Double? = null
        val heightRegex = Regex(
            """(?:\u0C0E\u0C24\u0C4D\u0C24\u0C41|height|\u090A\u0902\u091A\u093E\u0908|\u0915\u0926)\s*[:=\-]?\s*(\d{2,3}(?:\.\d{1,2})?)|(\d{2,3}(?:\.\d{1,2})?)\s*(?:\u0C38\u0C46\u0C02\.\u0C2E\u0C40|\u0C38\u0C46\u0C02\u0C1F\u0C40\u0C2E\u0C40\u0C1F\u0C30\u0C4D\u0C32\u0C41|cm|cms|\u0938\u0947\u0902\u091F\u0940\u092E\u0940\u091F\u0930)""",
            RegexOption.IGNORE_CASE
        )
        val heightMatch = heightRegex.find(clean)
        if (heightMatch != null) {
            val numStr = heightMatch.groupValues[1].ifEmpty { heightMatch.groupValues[2] }
            val h = numStr.toDoubleOrNull()
            if (h != null && h in 40.0..140.0) extractedHeight = h
        }

        // 6. Parents & Ward
        val fatherRegex = Regex(
            """(?:\u0C24\u0C02\u0C21\u0C4D\u0C30\u0C3F|father|\u092A\u093F\u0924\u093E|\u092A\u093E\u092A\u093E)\s*[:=\-]?\s*([a-zA-Z\u0C00-\u0C7F\u0900-\u097F\s]+?)(?:,|\.|\s+(?:\u0C24\u0C32\u0C4D\u0C32\u0C3F|mother|\u092E\u093E\u0924\u093E|\u0C35\u0C3E\u0C30\u0C4D\u0C21\u0C41|ward|\u0935\u093E\u0930\u094D\u0921|\u0C17\u0C4D\u0C30\u0C3E\u0C2E\u0C02)|$)""",
            RegexOption.IGNORE_CASE
        )
        val father = fatherRegex.find(clean)?.groupValues?.get(1)?.trim()

        val motherRegex = Regex(
            """(?:\u0C24\u0C32\u0C4D\u0C32\u0C3F|mother|\u092E\u093E\u0924\u093E|\u092E\u093E\u0901)\s*[:=\-]?\s*([a-zA-Z\u0C00-\u0C7F\u0900-\u097F\s]+?)(?:,|\.|\s+(?:\u0C35\u0C3E\u0C30\u0C4D\u0C21\u0C41|ward|\u0935\u093E\u0930\u094D\u0921|\u0C17\u0C4D\u0C30\u0C3E\u0C2E\u0C02)|$)""",
            RegexOption.IGNORE_CASE
        )
        val mother = motherRegex.find(clean)?.groupValues?.get(1)?.trim()

        val wardRegex = Regex(
            """(?:\u0C35\u0C3E\u0C30\u0C4D\u0C21\u0C41|\u0C17\u0C4D\u0C30\u0C3E\u0C2E\u0C02|ward|village|\u0C15\u0C47\u0C02\u0C26\u0C4D\u0C30\u0C02|\u0935\u093E\u0930\u094D\u0921)\s*[:=\-]?\s*([a-zA-Z0-9\u0C00-\u0C7F\u0900-\u097F\s]+?)(?:,|\.|$)""",
            RegexOption.IGNORE_CASE
        )
        val ward = wardRegex.find(clean)?.groupValues?.get(1)?.trim()

        return ParsedEnrollment(
            name = extractedName,
            ageYears = extractedAge,
            gender = extractedGender,
            weightKg = extractedWeight,
            heightCm = extractedHeight,
            fatherName = if (!father.isNullOrBlank()) father else null,
            motherName = if (!mother.isNullOrBlank()) mother else null,
            villageWard = if (!ward.isNullOrBlank()) ward else null
        )
    }
}