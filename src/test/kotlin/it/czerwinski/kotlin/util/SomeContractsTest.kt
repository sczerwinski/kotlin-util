package it.czerwinski.kotlin.util

import org.junit.Assert.*
import org.junit.Test
import kotlin.contracts.ExperimentalContracts

class SomeContractsTest {

    @Test
    @Throws(Exception::class)
    @ExperimentalContracts
    fun shouldNotBeEmpty() {
        // given:
        val option: Option<String> = Some("text")
        // when:
        val result = option.isEmpty()
        // then:
        assertFalse(result)
        if (!option.isEmpty()) {
            assertEquals("text", option.value)
        }
    }
}
