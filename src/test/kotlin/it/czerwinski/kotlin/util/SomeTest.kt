package it.czerwinski.kotlin.util

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class SomeTest {

    @Test(expected = IllegalArgumentException::class)
    @Throws(Exception::class)
    fun shouldFailToCreateSomeWithNull() {
        // given:
        val value = null
        // when:
        val some: Option<String?> = Some(value)
    }

    @Test
    @Throws(Exception::class)
    fun shouldBeDefined() {
        // given:
        val option: Option<String> = Some("text")
        // when:
        val result = option.isDefined
        // then:
        assertTrue(result)
    }

    @Test
    @Throws(Exception::class)
    fun shouldNotBeEmpty() {
        // given:
        val option: Option<String> = Some("text")
        // when:
        val result = option.isEmpty
        // then:
        assertFalse(result)
    }
}
