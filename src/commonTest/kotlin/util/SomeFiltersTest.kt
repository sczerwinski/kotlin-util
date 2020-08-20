package it.czerwinski.kotlin.util

import kotlin.test.Test
import kotlin.test.assertEquals

class SomeFiltersTest {

    @Test
    fun filterShouldReturnTheSameSomeIfPredicateIsTrue() {
        // given:
        val option: Option<Int> = Some(1)
        // when:
        val result = option.filter { it > 0 }
        // then:
        assertEquals(option, result)
    }

    @Test
    fun filterShouldReturnNoneIfPredicateIsFalse() {
        // given:
        val option: Option<Int> = Some(-1)
        // when:
        val result = option.filter { it > 0 }
        // then:
        assertEquals(None, result)
    }

    @Test
    fun filterNotShouldReturnTheSameSomeIfPredicateIsFalse() {
        // given:
        val option: Option<Int> = Some(-1)
        // when:
        val result = option.filterNot { it > 0 }
        // then:
        assertEquals(option, result)
    }

    @Test
    fun filterNotShouldReturnNoneIfPredicateIsTrue() {
        // given:
        val option: Option<Int> = Some(1)
        // when:
        val result = option.filterNot { it > 0 }
        // then:
        assertEquals(None, result)
    }

    @Test
    fun filterIsInstanceShouldReturnSomeIfIsInstance() {
        // given:
        val option: Option<Number> = Some(1)
        // when:
        val result: Option<Int> = option.filterIsInstance<Int>()
        // then:
        assertEquals(Some(1), result)
    }

    @Test
    fun filterIsInstanceShouldReturnNoneIfIsNotInstance() {
        // given:
        val option: Option<Any> = Some("1")
        // when:
        val result: Option<Float> = option.filterIsInstance<Float>()
        // then:
        assertEquals(None, result)
    }
}
