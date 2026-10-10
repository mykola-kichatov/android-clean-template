package com.mkchtv.cleantemplate.element.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import app.cash.turbine.test
import app.cash.turbine.turbineScope
import com.mkchtv.cleantemplate.domain.element.entity.Element
import com.mkchtv.cleantemplate.domain.element.usecase.CreateElement
import com.mkchtv.cleantemplate.domain.element.usecase.DeleteElement
import com.mkchtv.cleantemplate.domain.element.usecase.ElementFlow
import com.mkchtv.cleantemplate.domain.element.usecase.UpdateElement
import com.mkchtv.cleantemplate.testing.MainDispatcherExtension
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.job
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import com.mkchtv.cleantemplate.ui.common.R as commonRes

@OptIn(ExperimentalCoroutinesApi::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MainDispatcherExtension::class)
class ElementDetailsViewModelTest {

    @MockK
    private lateinit var mockElementFlow: ElementFlow

    @MockK
    private lateinit var mockCreateElement: CreateElement

    @MockK
    private lateinit var mockUpdateElement: UpdateElement

    @MockK
    private lateinit var mockDeleteElement: DeleteElement

    @BeforeAll
    fun `setup mocks`() {
        MockKAnnotations.init(this, relaxUnitFun = true)
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
    fun `a save that works closes the screen`() = runTest {
        // [GIVEN]
        val viewModel = viewModel()

        // [WHEN]
        // [THEN]
        viewModel.effects.test {
            viewModel.onIntent(UPDATE)
            Assertions.assertEquals(Effect.NavigateBack, awaitItem())
        }
        coVerify(exactly = 1) {
            mockUpdateElement(ELEMENT.id, UPDATE.name, UPDATE.description, UPDATE.imageUrl)
        }
    }

    @Test
    fun `a failed save shows the message for the error and keeps the screen open`() = runTest {
        // [GIVEN]
        coEvery { mockUpdateElement(any(), any(), any(), any()) } throws
            IllegalStateException("database or disk is full")
        val viewModel = viewModel()

        // [WHEN]
        // [THEN]
        turbineScope {
            val states = viewModel.uiState.testIn(backgroundScope)
            viewModel.effects.test {
                viewModel.onIntent(UPDATE)
                Assertions.assertEquals(
                    Effect.ShowError(commonRes.string.error_unknown),
                    awaitItem(),
                )
                expectNoEvents()
            }
            // the button is back, so the user can try again
            Assertions.assertFalse(states.expectMostRecentItem().isSaving)
        }
    }

    @Test
    fun `while a save runs the screen is saving and takes no second save`() = runTest {
        // [GIVEN]
        coEvery { mockUpdateElement(any(), any(), any(), any()) } coAnswers { delay(3_000) }
        val viewModel = viewModel()

        // [WHEN]
        // [THEN]
        turbineScope {
            val states = viewModel.uiState.testIn(backgroundScope)
            viewModel.onIntent(UPDATE)
            Assertions.assertTrue(states.expectMostRecentItem().isSaving)
            // a second tap, or the keyboard's Done key
            viewModel.onIntent(UPDATE)
            advanceTimeBy(3_001)
        }
        coVerify(exactly = 1) { mockUpdateElement(any(), any(), any(), any()) }
    }

    @Test
    fun `leaving the screen during a save shows nothing, and the save still finishes`() = runTest {
        // [GIVEN]
        var saved = false
        coEvery { mockUpdateElement(any(), any(), any(), any()) } coAnswers {
            delay(3_000)
            saved = true
        }
        val viewModel = viewModel()

        // [WHEN]
        // [THEN]
        viewModel.effects.test {
            viewModel.onIntent(UPDATE)
            // what leaving the screen does: the ViewModel is cleared and its scope cancelled
            viewModel.viewModelScope.cancel()
            advanceTimeBy(3_001)
            expectNoEvents()
        }
        Assertions.assertTrue(saved)
    }

    // Built per test, after the stubs, because it reads the element on construction.
    private fun TestScope.viewModel(): ElementDetailsViewModel {
        every { mockElementFlow(ELEMENT.id) } returns flowOf(ELEMENT)
        return ElementDetailsViewModel(
            savedStateHandle = SavedStateHandle(mapOf(ARG_KEY_ELEMENT_ID to ELEMENT.id)),
            appIoScope = appIoScope(),
            elementFlow = mockElementFlow,
            createElement = mockCreateElement,
            updateElement = mockUpdateElement,
            deleteElement = mockDeleteElement,
        )
    }

    /**
     * The app scope: unconfined, as in SettingsViewModelTest, and with its own SupervisorJob, like
     * the real one. On the bare backgroundScope job, runTest reports a failed write as uncaught,
     * though the ViewModel caught it from await. It still ends with the test.
     */
    private fun TestScope.appIoScope(): CoroutineScope = CoroutineScope(
        backgroundScope.coroutineContext +
            SupervisorJob(backgroundScope.coroutineContext.job) +
            UnconfinedTestDispatcher(testScheduler),
    )

    private companion object {
        val ELEMENT = Element(
            id = 7,
            name = "test_name",
            description = "test_desc",
            imageUrl = "test_url",
        )
        val UPDATE = Intent.UpdateElement(
            name = "new_name",
            description = "new_desc",
            imageUrl = "test_url",
        )
    }
}
