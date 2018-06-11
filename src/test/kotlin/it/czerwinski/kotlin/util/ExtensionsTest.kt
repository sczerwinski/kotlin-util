package it.czerwinski.kotlin.util

import org.junit.Assert
import org.junit.Test

class ExtensionsTest {

    @Test
    @Throws(Exception::class)
    fun flattenSuccessOfSomeShouldReturnInnerSome() {
        // given:
        val success: Try<Option<String>> = Success(Some("text"))
        // when:
        val result = success.flatten()
        // then:
        Assert.assertEquals(Some("text"), result)
    }

    @Test
    @Throws(Exception::class)
    fun flattenSuccessOfNoneShouldReturnNone() {
        // given:
        val success: Try<Option<String>> = Success(None)
        // when:
        val result = success.flatten()
        // then:
        Assert.assertSame(None, result)
    }

    @Test
    @Throws(Exception::class)
    fun flattenFailureShouldReturnNone() {
        // given:
        val failure: Try<Option<String>> = Failure(RuntimeException("Test exception"))
        // when:
        val result = failure.flatten()
        // then:
        Assert.assertSame(None, result)
    }

    @Test
    @Throws(Exception::class)
    fun flattenSomeOfSuccessShouldReturnSome() {
        // given:
        val option: Option<Try<String>> = Some(Success("text"))
        // when:
        val result: Option<String> = option.flatten()
        // then:
        Assert.assertEquals(Some("text"), result)
    }

    @Test
    @Throws(Exception::class)
    fun flattenSomeOfFailureShouldReturnNone() {
        // given:
        val option: Option<Try<String>> = Some(Failure(RuntimeException()))
        // when:
        val result: Option<String> = option.flatten()
        // then:
        Assert.assertEquals(None, result)
    }

    @Test
    @Throws(Exception::class)
    fun flattenNoneOfTryShouldReturnNone() {
        // given:
        val option: Option<Try<String>> = None
        // when:
        val result: Option<String> = option.flatten()
        // then:
        Assert.assertEquals(None, result)
    }

    @Test
    @Throws(Exception::class)
    fun flattenSomeOfListShouldReturnInnerList() {
        // given:
        val option: Option<List<String>> = Some(listOf("text1", "text2", "text3"))
        // when:
        val result: List<String> = option.flatten()
        // then:
        Assert.assertEquals(listOf("text1", "text2", "text3"), result)
    }

    @Test
    @Throws(Exception::class)
    fun flattenNoneOfListShouldReturnNone() {
        // given:
        val option: Option<List<String>> = None
        // when:
        val result: List<String> = option.flatten()
        // then:
        Assert.assertEquals(emptyList<String>(), result)
    }
}