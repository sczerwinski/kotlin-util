package it.czerwinski.kotlin.util

import kotlin.test.Test
import kotlin.test.assertEquals

class NoneFiltersTest {

    @Test
    fun filterShouldReturnNone() {
        // given:
        val option: Option<Int> = None
        // when:
        val result = option.filter { it > 0 }
        // then:
        assertEquals(None, result)
    }

    @Test
    fun filterNotShouldReturnNone() {
        // given:
        val option: Option<Int> = None
        // when:
        val result = option.filterNot { it > 0 }
        // then:
        assertEquals(None, result)
    }

    @Test
    fun filterIsInstanceShouldReturnNone() {
        // given:
        val option: Option<Int> = None
        // when:
        val result = option.filterIsInstance<Int>()
        // then:
        assertEquals(None, result)
    }
}
