package com.mkchtv.cleantemplate.domain.element.usecase

import com.mkchtv.cleantemplate.domain.element.entity.EditedElementData
import com.mkchtv.cleantemplate.domain.element.entity.PullElementData
import com.mkchtv.cleantemplate.domain.element.repository.ElementRepository
import com.mkchtv.cleantemplate.domain.element.repository.ImageUrlProvider
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
import java.io.IOException
import kotlin.coroutines.cancellation.CancellationException

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PullElementTest {

    @MockK
    private lateinit var mockRepository: ElementRepository

    private lateinit var pullElement: PullElement

    @BeforeAll
    fun `setup mocks`() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @BeforeEach
    fun `setup use case`() {
        pullElement = PullElement(
            getRandomImageUrl = GetRandomImageUrl(ImageUrlProvider { "test_image_url" }),
            repository = mockRepository,
        )
    }

    @AfterEach
    fun `clear mocks`() {
        clearAllMocks()
    }

    @AfterAll
    fun teardown() {
        unmockkAll()
    }

    @Test
    fun `pull element saves the pulled data with a random image`() = runTest {
        // [GIVEN]
        coEvery { mockRepository.pull() } returns PullElementData(
            name = "test_name",
            description = "test_desc",
        )

        // [WHEN]
        pullElement()

        // [THEN]
        coVerify {
            mockRepository.create(
                EditedElementData(
                    name = "test_name",
                    description = "test_desc",
                    imageUrl = "test_image_url",
                ),
            )
        }
    }

    @Test
    fun `pull element throws the error as is and saves nothing when the pull fails`() = runTest {
        // [GIVEN]
        val error = IOException("test_error")
        coEvery { mockRepository.pull() } throws error

        // [WHEN]
        // [THEN]
        val thrown = assertThrows<IOException> { pullElement() }
        Assertions.assertSame(error, thrown)
        coVerify(exactly = 0) { mockRepository.create(any()) }
    }

    @Test
    fun `pull element passes cancellation on and saves nothing`() = runTest {
        // [GIVEN]
        coEvery { mockRepository.pull() } throws CancellationException("test_cancel")

        // [WHEN]
        // [THEN]
        assertThrows<CancellationException> { pullElement() }
        coVerify(exactly = 0) { mockRepository.create(any()) }
    }
}
