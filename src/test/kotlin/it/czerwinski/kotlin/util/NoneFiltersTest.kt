package it.czerwinski.kotlin.util

import org.junit.Assert.assertEquals
import org.junit.Test

class NoneFiltersTest {

    @Test
    @Throws(Exception::class)
    fun filterShouldReturnNone() {
        // given:
        val option: Option<Int> = None
        // when:
        val result = option.filter { it > 0 }
        // then:
        assertEquals(None, result)
    }

    @Test
    @Throws(Exception::class)
    fun filterNotShouldReturnNone() {
        // given:
        val option: Option<Int> = None
        // when:
        val result = option.filterNot { it > 0 }
        // then:
        assertEquals(None, result)
    }

    @Test
    @Throws(Exception::class)
    fun filterIsInstanceShouldReturnNone() {
        // given:
        val option: Option<Int> = None
        // when:
        val result = option.filterIsInstance<Int>()
        // then:
        assertEquals(None, result)
    }
}
