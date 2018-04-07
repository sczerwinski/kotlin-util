package it.czerwinski.kotlin.util

import org.junit.Assert.*
import org.junit.Test

class LeftProjectionTest {

    @Test
    @Throws(Exception::class)
    fun getShouldReturnLeftValue() {
        // given:
        val either: Either<Int, Float> = Left(256)
        // when:
        val result = either.left.get()
        // then:
        assertEquals(256, result)
    }

    @Test(expected = NoSuchElementException::class)
    @Throws(Exception::class)
    fun getShouldThrowExceptionIfRight() {
        // given:
        val either: Either<Int, Float> = Right(3.14f)
        // when:
        either.left.get()
    }

    @Test
    @Throws(Exception::class)
    fun getOrNullShouldReturnLeftValue() {
        // given:
        val either: Either<Int, Float> = Left(256)
        // when:
        val result = either.left.getOrNull()
        // then:
        assertEquals(256, result)
    }

    @Test
    @Throws(Exception::class)
    fun getOrNullShouldReturnNullIfRight() {
        // given:
        val either: Either<Int, Float> = Right(3.14f)
        // when:
        val result = either.left.getOrNull()
        // then:
        assertNull(result)
    }

    @Test
    @Throws(Exception::class)
    fun getOrElseShouldReturnLeftValue() {
        // given:
        val either: Either<Int, Float> = Left(256)
        // when:
        val result = either.left.getOrElse { -1 }
        // then:
        assertEquals(256, result)
    }

    @Test
    @Throws(Exception::class)
    fun getOrElseShouldReturnDefaultValueIfRight() {
        // given:
        val either: Either<Int, Float> = Right(3.14f)
        // when:
        val result = either.left.getOrElse { -1 }
        // then:
        assertEquals(-1, result)
    }
}
