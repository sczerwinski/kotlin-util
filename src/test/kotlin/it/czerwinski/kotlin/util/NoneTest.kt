package it.czerwinski.kotlin.util

import org.junit.Assert.*
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
    fun shouldNotBeNotEmpty() {
        // given:
        val option: Option<String> = None
        // when:
        val result = option.isNotEmpty
        // then:
        assertFalse(result)
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

    @Test
    @Throws(Exception::class)
    fun iteratorShouldBeEmpty() {
        // given:
        val option: Option<String> = None
        // when:
        val result = option.iterator
        // then:
        assertFalse(result.hasNext())
    }

    @Test(expected = NoSuchElementException::class)
    @Throws(Exception::class)
    fun getShouldThrowException() {
        // given:
        val option: Option<String> = None
        // when:
        option.get()
    }

    @Test
    @Throws(Exception::class)
    fun getOrNullShouldReturnNull() {
        // given:
        val option: Option<String> = None
        // when:
        val result = option.getOrNull()
        // then:
        assertNull(result)
    }

    @Test
    @Throws(Exception::class)
    fun getOrElseShouldReturnDefaultValue() {
        // given:
        val option: Option<String> = None
        // when:
        val result = option.getOrElse { "default" }
        // then:
        assertEquals("default", result)
    }

    @Test
    @Throws(Exception::class)
    fun orElseShouldReturnDefaultOption() {
        // given:
        val option: Option<String> = None
        // when:
        val result = option.orElse { Some("default") }
        // then:
        assertEquals(Some("default"), result)
    }

    @Test
    @Throws(Exception::class)
    fun forEachShouldNotBeCalled() {
        // given:
        val option: Option<String> = None
        var result = "default"
        // when:
        option.forEach { result = it }
        // then:
        assertEquals("default", result)
    }

    @Test
    @Throws(Exception::class)
    fun mapShouldReturnNone() {
        // given:
        val option: Option<Int> = None
        // when:
        val result: Option<String> = option.map { it.toString() }
        // then:
        assertEquals(None, result)
    }

    @Test
    @Throws(Exception::class)
    fun flatMapShouldReturnNone() {
        // given:
        val option: Option<Int> = None
        // when:
        val result: Option<String> = option.flatMap { Option(it.toString()) }
        // then:
        assertEquals(None, result)
    }

    @Test
    @Throws(Exception::class)
    fun flattenShouldReturnNone() {
        // given:
        val option: Option<Option<String>> = None
        // when:
        val result: Option<String> = option.flatten()
        // then:
        assertEquals(None, result)
    }

    @Test
    @Throws(Exception::class)
    fun allShouldReturnTrue() {
        // given:
        val option: Option<Int> = None
        // when:
        val result = option.all { it > 0 }
        // then:
        assertTrue(result)
    }

    @Test
    @Throws(Exception::class)
    fun anyShouldReturnFalse() {
        // given:
        val option: Option<Int> = None
        // when:
        val result = option.any { it > 0 }
        // then:
        assertFalse(result)
    }

    @Test
    @Throws(Exception::class)
    fun foldShouldReturnDefaultValue() {
        // given:
        val option: Option<Int> = None
        // when:
        val result = option.fold("text") { it.toString() }
        // then:
        assertEquals("text", result)
    }

    @Test
    @Throws(Exception::class)
    fun foldShouldReturnDefaultResult() {
        // given:
        val option: Option<Int> = None
        // when:
        val result = option.fold({ "text" }) { it.toString() }
        // then:
        assertEquals("text", result)
    }

    @Test
    @Throws(Exception::class)
    fun containsShouldReturnFalse() {
        // given:
        val option: Option<Int> = None
        // when:
        val result = 123 in option
        // then:
        assertFalse(result)
    }

    @Test
    @Throws(Exception::class)
    fun toListShouldReturnEmptyList() {
        // given:
        val option: Option<String> = None
        // when:
        val result = option.toList()
        // then:
        assertTrue(result.isEmpty())
    }

    @Test
    @Throws(Exception::class)
    fun toLeftShouldReturnRightWithDefaultValue() {
        // given:
        val option: Option<Int> = None
        // when:
        val result: Either<Int, String> = option.toLeft { "text" }
        // then:
        assertEquals(Right("text"), result)
    }

    @Test
    @Throws(Exception::class)
    fun toRightShouldReturnLeftWithDefaultValue() {
        // given:
        val option: Option<Int> = None
        // when:
        val result: Either<String, Int> = option.toRight { "text" }
        // then:
        assertEquals(Left("text"), result)
    }
}
