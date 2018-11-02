package it.czerwinski.kotlin.util

import org.junit.Assert.*
import org.junit.Test

class SomeTest {

    @Test
    @Throws(Exception::class)
    fun toStringShouldReturnSomeOfValue() {
        // given:
        val option: Option<String> = Some("text")
        // when:
        val result = option.toString()
        // then:
        assertEquals("Some(value=text)", result)
    }

    @Test(expected = IllegalArgumentException::class)
    @Throws(Exception::class)
    fun shouldFailToCreateSomeWithNull() {
        // given:
        val value = null
        // when:
        @Suppress("UNUSED_VARIABLE")
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

    @Test
    @Throws(Exception::class)
    fun shouldBeNotEmpty() {
        // given:
        val option: Option<String> = Some("text")
        // when:
        val result = option.isNotEmpty
        // then:
        assertTrue(result)
    }

    @Test
    @Throws(Exception::class)
    fun iteratorShouldProduceSingleElement() {
        // given:
        val option: Option<String> = Some("text")
        val result = mutableListOf<String>()
        // when:
        option.iterator.forEach { result.add(it) }
        // then:
        assertEquals(listOf("text"), result)
    }

    @Test
    @Throws(Exception::class)
    fun getShouldReturnValue() {
        // given:
        val option: Option<String> = Some("text")
        // when:
        val result = option.get()
        // then:
        assertEquals("text", result)
    }

    @Test
    @Throws(Exception::class)
    fun getOrNullShouldReturnValue() {
        // given:
        val option: Option<String> = Some("text")
        // when:
        val result = option.getOrNull()
        // then:
        assertEquals("text", result)
    }

    @Test
    @Throws(Exception::class)
    fun getOrElseShouldReturnValue() {
        // given:
        val option: Option<String> = Some("text")
        // when:
        val result = option.getOrElse { "default" }
        // then:
        assertEquals("text", result)
    }

    @Test
    @Throws(Exception::class)
    fun orElseShouldReturnThis() {
        // given:
        val option: Option<String> = Some("text")
        // when:
        val result = option.orElse { Some("default") }
        // then:
        assertEquals(Some("text"), result)
    }

    @Test
    @Throws(Exception::class)
    fun forEachShouldBeCalled() {
        // given:
        val option: Option<String> = Some("text")
        var result = "default"
        // when:
        option.forEach { result = it }
        // then:
        assertEquals("text", result)
    }

    @Test
    @Throws(Exception::class)
    fun mapShouldReturnNewSome() {
        // given:
        val option: Option<Int> = Some(123)
        // when:
        val result: Option<String> = option.map { it.toString() }
        // then:
        assertEquals(Some("123"), result)
    }

    @Test
    @Throws(Exception::class)
    fun flatMapShouldReturnNewSome() {
        // given:
        val option: Option<Int> = Some(123)
        // when:
        val result: Option<String> = option.flatMap { Option(it.toString()) }
        // then:
        assertEquals(Some("123"), result)
    }

    @Test
    @Throws(Exception::class)
    fun flatMapShouldReturnNone() {
        // given:
        val option: Option<Int> = Some(123)
        // when:
        val result: Option<String> = option.flatMap { None }
        // then:
        assertEquals(None, result)
    }

    @Test
    @Throws(Exception::class)
    fun flattenShouldReturnInnerSome() {
        // given:
        val option: Option<Option<String>> = Some(Some("text"))
        // when:
        val result: Option<String> = option.flatten()
        // then:
        assertEquals(Some("text"), result)
    }

    @Test
    @Throws(Exception::class)
    fun flattenShouldReturnInnerNone() {
        // given:
        val option: Option<Option<String>> = Some(None)
        // when:
        val result: Option<String> = option.flatten()
        // then:
        assertEquals(None, result)
    }

    @Test
    @Throws(Exception::class)
    fun allShouldReturnTrueIfPredicateIsTrue() {
        // given:
        val option: Option<Int> = Some(1)
        // when:
        val result = option.all { it > 0 }
        // then:
        assertTrue(result)
    }

    @Test
    @Throws(Exception::class)
    fun allShouldReturnFalseIfPredicateIsFalse() {
        // given:
        val option: Option<Int> = Some(-1)
        // when:
        val result = option.all { it > 0 }
        // then:
        assertFalse(result)
    }

    @Test
    @Throws(Exception::class)
    fun anyShouldReturnTrueIfPredicateIsTrue() {
        // given:
        val option: Option<Int> = Some(1)
        // when:
        val result = option.any { it > 0 }
        // then:
        assertTrue(result)
    }

    @Test
    @Throws(Exception::class)
    fun anyShouldReturnFalseIfPredicateIsFalse() {
        // given:
        val option: Option<Int> = Some(-1)
        // when:
        val result = option.any { it > 0 }
        // then:
        assertFalse(result)
    }

    @Test
    @Throws(Exception::class)
    fun noneShouldReturnFalseIfPredicateIsTrue() {
        // given:
        val option: Option<Int> = Some(1)
        // when:
        val result = option.none { it > 0 }
        // then:
        assertFalse(result)
    }

    @Test
    @Throws(Exception::class)
    fun noneShouldReturnTrueIfPredicateIsFalse() {
        // given:
        val option: Option<Int> = Some(-1)
        // when:
        val result = option.none { it > 0 }
        // then:
        assertTrue(result)
    }

    @Test
    @Throws(Exception::class)
    fun foldShouldReturnTransformedValue() {
        // given:
        val option: Option<Int> = Some(123)
        // when:
        val result = option.fold("text") { it.toString() }
        // then:
        assertEquals("123", result)
    }

    @Test
    @Throws(Exception::class)
    fun foldWithDefaultLambdaShouldReturnTransformedValue() {
        // given:
        val option: Option<Int> = Some(123)
        // when:
        val result = option.fold({ "text" }) { it.toString() }
        // then:
        assertEquals("123", result)
    }

    @Test
    @Throws(Exception::class)
    fun zipSomeShouldReturnSome() {
        // given:
        val option1: Option<Int> = Some(123)
        val option2: Option<String> = Some("text")
        // when:
        val result = option1 zip option2
        // then:
        assertEquals(Some(123 to "text"), result)
    }

    @Test
    @Throws(Exception::class)
    fun zipNoneShouldReturnNone() {
        // given:
        val option1: Option<Int> = Some(123)
        val option2: Option<String> = None
        // when:
        val result = option1 zip option2
        // then:
        assertEquals(None, result)
    }

    @Test
    @Throws(Exception::class)
    fun zipSomeWithTransformShouldReturnSome() {
        // given:
        val option1: Option<Int> = Some(2)
        val option2: Option<Float> = Some(3.14f)
        // when:
        val result = option1.zip(option2) { a, b -> a * b }
        // then:
        assertEquals(Some(6.28f), result)
    }

    @Test
    @Throws(Exception::class)
    fun zipNoneWithTransformShouldReturnNone() {
        // given:
        val option1: Option<Int> = Some(2)
        val option2: Option<Float> = None
        // when:
        val result = option1.zip(option2) { a, b -> a * b }
        // then:
        assertEquals(None, result)
    }

    @Test
    @Throws(Exception::class)
    fun containsShouldReturnTrueIfValueEqualsElement() {
        // given:
        val option: Option<Int> = Some(123)
        // when:
        val result = 123 in option
        // then:
        assertTrue(result)
    }

    @Test
    @Throws(Exception::class)
    fun containsShouldReturnFalseIfValueNotEqualsElement() {
        // given:
        val option: Option<Int> = Some(123)
        // when:
        val result = 135 in option
        // then:
        assertFalse(result)
    }

    @Test
    @Throws(Exception::class)
    fun containsShouldReturnFalseIfValueOfDifferentType() {
        // given:
        val option: Option<Int> = Some(123)
        // when:
        val result = 3.14f in option
        // then:
        assertFalse(result)
    }

    @Test
    @Throws(Exception::class)
    fun toListShouldReturnListContainingValue() {
        // given:
        val option: Option<Int> = Some(123)
        // when:
        val result = option.toList()
        // then:
        assertEquals(listOf(123), result)
    }

    @Test
    @Throws(Exception::class)
    fun toLeftShouldReturnLeftContainingValue() {
        // given:
        val option: Option<Int> = Some(123)
        // when:
        val result: Either<Int, String> = option.toLeft { "text" }
        // then:
        assertEquals(Left(123), result)
    }

    @Test
    @Throws(Exception::class)
    fun toRightShouldReturnRightContainingValue() {
        // given:
        val option: Option<Int> = Some(123)
        // when:
        val result: Either<String, Int> = option.toRight { "text" }
        // then:
        assertEquals(Right(123), result)
    }

    @Test
    @Throws(Exception::class)
    fun asIterableShouldReturnIterableReturningValue() {
        // given:
        val option: Option<Int> = Some(123)
        // when:
        val result: Iterable<Int> = option.asIterable()
        // then:
        assertEquals(listOf(123), result.toList())
    }

    @Test
    @Throws(Exception::class)
    fun asSequenceShouldReturnSequenceReturningValue() {
        // given:
        val option: Option<Int> = Some(123)
        // when:
        val result: Sequence<Int> = option.asSequence()
        // then:
        assertEquals(listOf(123), result.toList())
    }
}
