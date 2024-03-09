package com.mkchtv.cleantemplate.ktlint

import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import org.junit.Test

class NoVarInDataClassRuleTest {

    private val ruleAssertThat = assertThatRule { NoVarInDataClassRule() }

    @Test
    fun `data class with var contains lint error`() {
        val dataClass = """
        data class Foo(
            val id: Int,
            var name: String,
            val description: String,
        )
        """.trimIndent()
        ruleAssertThat(dataClass).hasLintViolationWithoutAutoCorrect(
            3,
            5,
            "Unexpected var in Data class, use val instead"
        )
    }

    @Test
    fun `data class with val params only doesn't have lint errors`() {
        val dataClass = """
        data class Foo(
            val id: Int,
            val name: String,
            val description: String,
        )
        """.trimIndent()
        ruleAssertThat(dataClass).hasNoLintViolations()
    }
}