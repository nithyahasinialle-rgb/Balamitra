package com.balamitra.domain

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class ChildAdmissionVoiceParserTest {

    @Test
    fun testTeluguEnrollmentUtteranceWithAllFields() {
        val spoken = "\u0C2A\u0C47\u0C30\u0C41: \u0C30\u0C2E\u0C47\u0C37\u0C4D, \u0C35\u0C2F\u0C38\u0C4D\u0C38\u0C41: 3 \u0C38\u0C02\u0C35\u0C24\u0C4D\u0C38\u0C30\u0C3E\u0C32\u0C41, \u0C32\u0C3F\u0C02\u0C17\u0C02: \u0C2C\u0C3E\u0C2C\u0C41, \u0C2C\u0C30\u0C41\u0C35\u0C41: 12.8 \u0C15\u0C3F\u0C32\u0C4B\u0C32\u0C41, \u0C0E\u0C24\u0C4D\u0C24\u0C41: 93 \u0C38\u0C46\u0C02.\u0C2E\u0C40, \u0C24\u0C02\u0C21\u0C4D\u0C30\u0C3F: \u0C30\u0C3E\u0C2E\u0C41, \u0C24\u0C32\u0C4D\u0C32\u0C3F: \u0C32\u0C15\u0C4D\u0C37\u0C4D\u0C2E\u0C3F, \u0C35\u0C3E\u0C30\u0C4D\u0C21\u0C41: 4"
        val parsed = ChildEnrollmentVoiceParser.parse(spoken)

        assertEquals("\u0C30\u0C2E\u0C47\u0C37\u0C4D", parsed.name)
        assertEquals(3, parsed.ageYears)
        assertEquals("M", parsed.gender)
        assertEquals(12.8, parsed.weightKg ?: 0.0, 0.01)
        assertEquals(93.0, parsed.heightCm ?: 0.0, 0.01)
        assertEquals("\u0C30\u0C3E\u0C2E\u0C41", parsed.fatherName)
        assertEquals("\u0C32\u0C15\u0C4D\u0C37\u0C4D\u0C2E\u0C3F", parsed.motherName)
        assertEquals("4", parsed.villageWard)
    }

    @Test
    fun testTeluguWrittenNumberWords() {
        val spoken = "\u0C2A\u0C47\u0C30\u0C41 \u0C05\u0C28\u0C3F\u0C24, \u0C2A\u0C3E\u0C2A, \u0C35\u0C2F\u0C38\u0C4D\u0C38\u0C41 \u0C28\u0C3E\u0C32\u0C41\u0C17\u0C41 \u0C38\u0C02\u0C35\u0C24\u0C4D\u0C38\u0C30\u0C3E\u0C32\u0C41, \u0C2C\u0C30\u0C41\u0C35\u0C41 13.5 \u0C15\u0C47\u0C1C\u0C40\u0C32\u0C41"
        val parsed = ChildEnrollmentVoiceParser.parse(spoken)

        assertEquals("\u0C05\u0C28\u0C3F\u0C24", parsed.name)
        assertEquals(4, parsed.ageYears)
        assertEquals("F", parsed.gender)
        assertEquals(13.5, parsed.weightKg ?: 0.0, 0.01)
    }

    @Test
    fun testEnglishEnrollmentUtterance() {
        val spoken = "Child name is Aarav, age 4 years, weight 15 kg, height 98 cm, father Suresh, mother Priya, ward Bachupally 2"
        val parsed = ChildEnrollmentVoiceParser.parse(spoken)

        assertEquals("Aarav", parsed.name)
        assertEquals(4, parsed.ageYears)
        assertEquals("M", parsed.gender)
        assertEquals(15.0, parsed.weightKg ?: 0.0, 0.01)
        assertEquals(98.0, parsed.heightCm ?: 0.0, 0.01)
        assertEquals("Suresh", parsed.fatherName)
        assertEquals("Priya", parsed.motherName)
        assertEquals("Bachupally 2", parsed.villageWard)
    }

    @Test
    fun testHindiEnrollmentUtterance() {
        val spoken = "\u0928\u093E\u092E \u092A\u094D\u0930\u093F\u092F\u093E, \u0909\u092E\u094D\u0930 3 \u0938\u093E\u0932, \u0932\u0921\u093C\u0915\u0940, \u0935\u091C\u0928 12 \u0915\u093F\u0932\u094B, \u092A\u093F\u0924\u093E \u0930\u093E\u091C\u0947\u0938\u094D, \u092E\u093E\u0924\u093E \u0938\u0941\u0928\u0940\u0924\u093E, \u0935\u093E\u0930\u094D\u0921 5"
        val parsed = ChildEnrollmentVoiceParser.parse(spoken)

        assertEquals("\u092A\u094D\u0930\u093F\u092F\u093E", parsed.name)
        assertEquals(3, parsed.ageYears)
        assertEquals("F", parsed.gender)
        assertEquals(12.0, parsed.weightKg ?: 0.0, 0.01)
        assertEquals("\u0930\u093E\u091C\u0947\u0938\u094D", parsed.fatherName)
        assertEquals("\u0938\u0941\u0928\u0940\u0924\u093E", parsed.motherName)
        assertEquals("5", parsed.villageWard)
    }
}