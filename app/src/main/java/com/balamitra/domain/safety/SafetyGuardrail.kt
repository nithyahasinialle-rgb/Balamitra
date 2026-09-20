package com.balamitra.domain.safety

/**
 * Safety Guardrails for BALAMITRA.
 *
 * Core rule:
 * BALAMITRA strictly distinguishes OBSERVATION from INTERPRETATION from DIAGNOSIS.
 * The system NEVER generates or displays medical, developmental, or psychiatric diagnoses.
 * Diagnostic attempts are transformed into ethical observational and referral language.
 */
object SafetyGuardrail {

    private val DIAGNOSTIC_PATTERNS = listOf(
        Regex("(?i)\\b(autism|autistic)\\b") to "needs further observation in social communication; follow referral protocol if concerns persist",
        Regex("(?i)\\b(developmental delay|delayed child)\\b") to "patterns worth observing longitudinally with the Anganwadi supervisor",
        Regex("(?i)\\b(malnourished|malnutrition|stunted|wasted)\\b") to "growth patterns that warrant dietary observation and review",
        Regex("(?i)\\b(adhd|hyperactive disorder)\\b") to "high energy and attention patterns that benefit from structured short games",
        Regex("(?i)\\b(mental retardation|retarded|intellectual disability)\\b") to "individual learning pace worth supporting with concrete materials",
        Regex("(?i)\\b(has a disease|is sick with|diagnosed with)\\b") to "observed physical indicators to discuss with primary health centre staff"
    )

    /**
     * Sanitizes AI or model text to ensure no diagnostic claims are made.
     * Preserves worker observations while rewriting diagnostic interpretations into safe referral language.
     */
    fun sanitizeOutput(text: String): String {
        var sanitized = text
        for ((pattern, replacement) in DIAGNOSTIC_PATTERNS) {
            sanitized = sanitized.replace(pattern, replacement)
        }
        return sanitized
    }

    /**
     * Standard safety reminder attached to AI reasoning insights.
     */
    fun getSafetyProtocolNotice(): String {
        return "BALAMITRA surfaces patterns worth observing and supports appropriate follow-up. Always follow standard health centre referral protocols if concerns continue."
    }

    /**
     * Verifies whether an observation string is purely observational vs attempting clinical diagnostic labeling.
     */
    fun containsDiagnosticClaims(text: String): Boolean {
        return DIAGNOSTIC_PATTERNS.any { (pattern, _) -> pattern.containsMatchIn(text) }
    }
}
