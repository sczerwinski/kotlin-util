package it.czerwinski.kotlin.util

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class NoneTest {

    @Test
    @Throws(Exception::class)
    fun shouldBeEmpty() {
        // given:
        val option: Option<String> = None
        // when:
        val result = option.isEmpty
        // then:
        assertTrue(result)
    }

    @Test
    @Throws(Exception::class)
    fun shouldNotBeDefined() {
        // given:
        val option: Option<String> = None
        // when:
        val result = option.isDefined
        // then:
        assertFalse(result)
    }
}
