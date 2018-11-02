package it.czerwinski.kotlin.util

import org.junit.Assert.*
import org.junit.Test
import kotlin.contracts.ExperimentalContracts

class NoneContractsTest {

    @Test
    @Throws(Exception::class)
    @ExperimentalContracts
    fun shouldBeEmpty() {
        // given:
        val option: Option<String> = None
        // when:
        val result = option.isEmpty()
        // then:
        assertTrue(result)
    }
}
