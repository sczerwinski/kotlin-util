package it.czerwinski.kotlin.util

import org.junit.Assert.assertEquals
import org.junit.Assert.assertSame
import org.junit.Test

class OptionTest {

    @Test
    @Throws(Exception::class)
    fun shouldCreateSome() {
        // given:
        val text: String? = "text"
        // when:
        val result: Option<String> = Option(text)
        // then:
        assertEquals(Some(text), result)
    }

    @Test
    @Throws(Exception::class)
    fun shouldReturnNone() {
        // given:
        val text: String? = null
        // when:
        val result: Option<String> = Option(text)
        // then:
        assertSame(None, result)
    }

    @Test
    @Throws(Exception::class)
    fun emptyShouldReturnNone() {
        // when:
        val result: Option<String> = Option.empty()
        // then:
        assertSame(None, result)
    }

    @Test
    @Throws(Exception::class)
    fun toOptionShouldCreateSome() {
        // given:
        val text: String? = "text"
        // when:
        val result: Option<String> = text.toOption()
        // then:
        assertEquals(Some(text), result)
    }

    @Test
    @Throws(Exception::class)
    fun toOptionShouldReturnNone() {
        // given:
        val text: String? = null
        // when:
        val result: Option<String> = text.toOption()
        // then:
        assertSame(None, result)
    }
}