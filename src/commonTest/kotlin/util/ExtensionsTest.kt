package it.czerwinski.kotlin.util

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertSame

class ExtensionsTest {

    @Test
    fun flattenSuccessOfSomeShouldReturnInnerSome() {
        // given:
        val success: Try<Option<String>> = Success(Some("text"))
        // when:
        val result = success.flatten()
        // then:
        assertEquals(Some("text"), result)
    }

    @Test
    fun flattenSuccessOfNoneShouldReturnNone() {
        // given:
        val success: Try<Option<String>> = Success(None)
        // when:
        val result = success.flatten()
        // then:
        assertSame(None, result)
    }

    @Test
    fun flattenFailureShouldReturnNone() {
        // given:
        val failure: Try<Option<String>> = Failure(RuntimeException("Test exception"))
        // when:
        val result = failure.flatten()
        // then:
        assertSame(None, result)
    }

    @Test
    fun flattenSomeOfSuccessShouldReturnSome() {
        // given:
        val option: Option<Try<String>> = Some(Success("text"))
        // when:
        val result: Option<String> = option.flatten()
        // then:
        assertEquals(Some("text"), result)
    }

    @Test
    fun flattenSomeOfFailureShouldReturnNone() {
        // given:
        val option: Option<Try<String>> = Some(Failure(RuntimeException()))
        // when:
        val result: Option<String> = option.flatten()
        // then:
        assertEquals(None, result)
    }

    @Test
    fun flattenNoneOfTryShouldReturnNone() {
        // given:
        val option: Option<Try<String>> = None
        // when:
        val result: Option<String> = option.flatten()
        // then:
        assertEquals(None, result)
    }

    @Test
    fun flattenSomeOfListShouldReturnInnerList() {
        // given:
        val option: Option<List<String>> = Some(listOf("text1", "text2", "text3"))
        // when:
        val result: List<String> = option.flatten()
        // then:
        assertEquals(listOf("text1", "text2", "text3"), result)
    }

    @Test
    fun flattenNoneOfListShouldReturnNone() {
        // given:
        val option: Option<List<String>> = None
        // when:
        val result: List<String> = option.flatten()
        // then:
        assertEquals(emptyList<String>(), result)
    }

    @Test
    fun flattenListOfOptionsShouldReturnListOfValues() {
        // given:
        val option: Iterable<Option<String>> =
            listOf(Some("text1"), None, Some("text3"), Some("text4"), None)
        // when:
        val result: List<String> = option.flatten()
        // then:
        assertEquals(listOf("text1", "text3", "text4"), result)
    }

    @Test
    fun evertSuccessOfSomeShouldReturnSomeOfSuccess() {
        // given:
        val success: Try<Option<String>> = Success(Some("text"))
        // when:
        val result = success.evert()
        // then:
        assertEquals(Some(Success("text")), result)
    }

    @Test
    fun evertSuccessOfNoneShouldReturnNone() {
        // given:
        val success: Try<Option<String>> = Success(None)
        // when:
        val result = success.evert()
        // then:
        assertEquals(None, result)
    }

    @Test
    fun evertFailureShouldReturnSomeOfFailure() {
        // given:
        val exception = RuntimeException("Test exception")
        val success: Try<Option<String>> = Failure(exception)
        // when:
        val result = success.evert()
        // then:
        assertEquals(Some(Failure(exception)), result)
    }

    @Test
    fun evertSomeOfSuccessShouldReturnSuccessOfSome() {
        // given:
        val success: Option<Try<String>> = Some(Success("text"))
        // when:
        val result = success.evert()
        // then:
        assertEquals(Success(Some("text")), result)
    }

    @Test
    fun evertSomeOfFailureShouldReturnFailure() {
        // given:
        val exception = RuntimeException("Test exception")
        val success: Option<Try<String>> = Some(Failure(exception))
        // when:
        val result = success.evert()
        // then:
        assertEquals(Failure(exception), result)
    }

    @Test
    fun evertNoneShouldReturnSuccessOfNone() {
        // given:
        val success: Option<Try<String>> = None
        // when:
        val result = success.evert()
        // then:
        assertEquals(Success(None), result)
    }
}
