package com.mkchtv.cleantemplate.element.list

import androidx.lifecycle.viewModelScope
import app.cash.turbine.test
import com.mkchtv.cleantemplate.domain.element.usecase.ElementsFlow
import com.mkchtv.cleantemplate.domain.element.usecase.PullElement
import com.mkchtv.cleantemplate.testing.MainDispatcherExtension
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import java.net.UnknownHostException
import com.mkchtv.cleantemplate.ui.common.R as commonRes

@OptIn(ExperimentalCoroutinesApi::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MainDispatcherExtension::class)
class ElementListViewModelTest {

    @MockK
    private lateinit var mockElementsFlow: ElementsFlow

    @MockK
    private lateinit var mockPullElement: PullElement

    private lateinit var viewModel: ElementListViewModel

    @BeforeAll
    fun `setup mocks`() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @BeforeEach
    fun `setup view model`() {
        every { mockElementsFlow() } returns flowOf(emptyList())
        viewModel = ElementListViewModel(
            elementsFlow = mockElementsFlow,
            pullElement = mockPullElement,
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
    fun `a failed pull shows the message for the error`() = runTest {
        // [GIVEN]
        coEvery { mockPullElement() } throws UnknownHostException("dummyjson.com")

        // [WHEN]
        // [THEN]
        viewModel.effects.test {
            viewModel.onIntent(Intent.PullNewElement)
            Assertions.assertEquals(
                Effect.ShowError(commonRes.string.error_no_connection),
                awaitItem(),
            )
        }
    }

    @Test
    fun `leaving the screen during a pull shows nothing`() = runTest {
        // [GIVEN]
        coEvery { mockPullElement() } coAnswers { awaitCancellation() }

        // [WHEN]
        // [THEN]
        viewModel.effects.test {
            viewModel.onIntent(Intent.PullNewElement)
            // what leaving the screen does: the ViewModel is cleared and its scope cancelled
            viewModel.viewModelScope.cancel()
            expectNoEvents()
        }
    }
}
