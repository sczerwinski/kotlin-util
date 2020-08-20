package it.czerwinski.kotlin.util

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertSame

class OptionTest {

    @Test
    fun shouldCreateSome() {
        // given:
        val text: String? = "text"
        // when:
        val result: Option<String> = Option(text)
        // then:
        assertEquals(Some(text), result)
    }

    @Test
    fun shouldReturnNone() {
        // given:
        val text: String? = null
        // when:
        val result: Option<String> = Option(text)
        // then:
        assertSame(None, result)
    }

    @Test
    fun emptyShouldReturnNone() {
        // when:
        val result: Option<String> = Option.empty()
        // then:
        assertSame(None, result)
    }

    @Test
    fun toOptionShouldCreateSome() {
        // given:
        val text: String? = "text"
        // when:
        val result: Option<String> = text.asOption()
        // then:
        assertEquals(Some(text), result)
    }

    @Test
    fun toOptionShouldReturnNone() {
        // given:
        val text: String? = null
        // when:
        val result: Option<String> = text.asOption()
        // then:
        assertSame(None, result)
    }
}
