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
}
