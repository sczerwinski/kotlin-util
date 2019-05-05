package it.czerwinski.kotlin.util

import org.junit.Assert.assertEquals
import org.junit.Test

class SomeFiltersTest {

    @Test
    @Throws(Exception::class)
    fun filterShouldReturnTheSameSomeIfPredicateIsTrue() {
        // given:
        val option: Option<Int> = Some(1)
        // when:
        val result = option.filter { it > 0 }
        // then:
        assertEquals(option, result)
    }

    @Test
    @Throws(Exception::class)
    fun filterShouldReturnNoneIfPredicateIsFalse() {
        // given:
        val option: Option<Int> = Some(-1)
        // when:
        val result = option.filter { it > 0 }
        // then:
        assertEquals(None, result)
    }

    @Test
    @Throws(Exception::class)
    fun filterNotShouldReturnTheSameSomeIfPredicateIsFalse() {
        // given:
        val option: Option<Int> = Some(-1)
        // when:
        val result = option.filterNot { it > 0 }
        // then:
        assertEquals(option, result)
    }

    @Test
    @Throws(Exception::class)
    fun filterNotShouldReturnNoneIfPredicateIsTrue() {
        // given:
        val option: Option<Int> = Some(1)
        // when:
        val result = option.filterNot { it > 0 }
        // then:
        assertEquals(None, result)
    }

    @Test
    @Throws(Exception::class)
    fun filterIsInstanceShouldReturnSomeIfIsInstance() {
        // given:
        val option: Option<Number> = Some(1)
        // when:
        val result: Option<Int> = option.filterIsInstance<Int>()
        // then:
        assertEquals(Some(1), result)
    }

    @Test
    @Throws(Exception::class)
    fun filterIsInstanceShouldReturnNoneIfIsNotInstance() {
        // given:
        val option: Option<Number> = Some(1)
        // when:
        val result: Option<Float> = option.filterIsInstance<Float>()
        // then:
        assertEquals(None, result)
    }
}
