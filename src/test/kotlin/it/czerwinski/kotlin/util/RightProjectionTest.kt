package it.czerwinski.kotlin.util

import org.junit.Assert.*
import org.junit.Test

class RightProjectionTest {

    @Test(expected = NoSuchElementException::class)
    @Throws(Exception::class)
    fun getShouldThrowExceptionIfLeft() {
        // given:
        val either: Either<Int, Float> = Left(256)
        // when:
        either.right.get()
    }

    @Test
    @Throws(Exception::class)
    fun getShouldReturnRightValue() {
        // given:
        val either: Either<Int, Float> = Right(3.14f)
        // when:
        val result = either.right.get()
        // then:
        assertEquals(3.14f, result)
    }

    @Test
    @Throws(Exception::class)
    fun getOrNullShouldReturnNullIfLeft() {
        // given:
        val either: Either<Int, Float> = Left(256)
        // when:
        val result = either.right.getOrNull()
        // then:
        assertNull(result)
    }

    @Test
    @Throws(Exception::class)
    fun getOrNullShouldReturnRightValue() {
        // given:
        val either: Either<Int, Float> = Right(3.14f)
        // when:
        val result = either.right.getOrNull()
        // then:
        assertEquals(3.14f, result)
    }

    @Test
    @Throws(Exception::class)
    fun getOrElseShouldReturnDefaultValueIfLeft() {
        // given:
        val either: Either<Int, Float> = Left(256)
        // when:
        val result = either.right.getOrElse { -1f }
        // then:
        assertEquals(-1f, result)
    }

    @Test
    @Throws(Exception::class)
    fun getOrElseShouldReturnRightValue() {
        // given:
        val either: Either<Int, Float> = Right(3.14f)
        // when:
        val result = either.right.getOrElse { -1f }
        // then:
        assertEquals(3.14f, result)
    }

    @Test
    @Throws(Exception::class)
    fun forEachShouldNotBeCalledIfLeft() {
        // given:
        val either: Either<Int, Float> = Left(256)
        var result = "default"
        // when:
        either.right.forEach { result = it.toString() }
        // then:
        assertEquals("default", result)
    }

    @Test
    @Throws(Exception::class)
    fun forEachShouldBeCalledIfRight() {
        // given:
        val either: Either<Int, Float> = Right(3.14f)
        var result = "default"
        // when:
        either.right.forEach { result = it.toString() }
        // then:
        assertEquals("3.14", result)
    }

    @Test
    @Throws(Exception::class)
    fun mapShouldNotTransformLeft() {
        // given:
        val either: Either<Int, Float> = Left(256)
        // when:
        val result = either.right.map { it.toString() }
        // then:
        assertEquals(either, result)
    }

    @Test
    @Throws(Exception::class)
    fun fmapShouldTransformRight() {
        // given:
        val either: Either<Int, Float> = Right(3.14f)
        // when:
        val result = either.right.map { it.toString() }
        // then:
        assertEquals(Right("3.14"), result)
    }

    @Test
    @Throws(Exception::class)
    fun flatMapShouldNotTransformLeft() {
        // given:
        val either: Either<Int, Float> = Left(256)
        // when:
        val result: Either<Int, String> = either.right.flatMap { Right(it.toString()) }
        // then:
        assertEquals(either, result)
    }

    @Test
    @Throws(Exception::class)
    fun flatMapShouldTransformRightToLeft() {
        // given:
        val either: Either<Int, Float> = Right(3.14f)
        // when:
        val result: Either<Int, String> = either.right.flatMap { Left(it.toInt()) }
        // then:
        assertEquals(Left(3), result)
    }

    @Test
    @Throws(Exception::class)
    fun flatMapShouldTransformRightToRight() {
        // given:
        val either: Either<Int, Float> = Right(3.14f)
        // when:
        val result: Either<Int, String> = either.right.flatMap { Right(it.toString()) }
        // then:
        assertEquals(Right("3.14"), result)
    }
}
