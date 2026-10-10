package com.mkchtv.cleantemplate.auth

import app.cash.turbine.test
import com.mkchtv.cleantemplate.domain.auth.entity.AuthState
import com.mkchtv.cleantemplate.domain.auth.usecase.AuthStateFlow
import com.mkchtv.cleantemplate.domain.auth.usecase.OnAuthenticated
import com.mkchtv.cleantemplate.testing.MainDispatcherExtension
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith

@OptIn(ExperimentalCoroutinesApi::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MainDispatcherExtension::class)
class AuthViewModelTest {

    @MockK
    private lateinit var mockAuthStateFlow: AuthStateFlow

    @MockK
    private lateinit var mockOnAuthenticated: OnAuthenticated

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
    fun `ui state has no auth state until the store answers, so the gate shows a spinner`() =
        runTest {
            // [GIVEN] a store that has not answered yet
            every { mockAuthStateFlow() } returns MutableSharedFlow()

            // [WHEN]
            val viewModel = viewModel()

            // [THEN]
            viewModel.uiState.test {
                Assertions.assertEquals(UiState(authState = null), awaitItem())
            }
        }

    @Test
    fun `ui state carries the stored auth state`() = runTest {
        // [GIVEN]
        every { mockAuthStateFlow() } returns flowOf(AuthState.AUTH_REQUIRED)

        // [WHEN]
        val viewModel = viewModel()

        // [THEN]
        viewModel.uiState.test {
            Assertions.assertEquals(UiState(authState = AuthState.AUTH_REQUIRED), awaitItem())
        }
    }

    @Test
    fun `a login in the store reaches the ui state`() = runTest {
        // [GIVEN]
        val stored = MutableStateFlow(AuthState.AUTH_REQUIRED)
        every { mockAuthStateFlow() } returns stored
        val viewModel = viewModel()

        // [WHEN]
        // [THEN]
        viewModel.uiState.test {
            Assertions.assertEquals(UiState(authState = AuthState.AUTH_REQUIRED), awaitItem())

            stored.value = AuthState.AUTHENTICATED

            Assertions.assertEquals(UiState(authState = AuthState.AUTHENTICATED), awaitItem())
        }
    }

    @Test
    fun `the authenticate click logs in`() = runTest {
        // [GIVEN]
        every { mockAuthStateFlow() } returns flowOf(AuthState.AUTH_REQUIRED)
        val viewModel = viewModel()

        // [WHEN]
        viewModel.onIntent(Intent.AuthenticateClick)

        // [THEN]
        coVerify(exactly = 1) { mockOnAuthenticated() }
    }

    // Built per test, after the stubs, because it asks for the store's flow on construction.
    private fun TestScope.viewModel() = AuthViewModel(
        appIoScope = appIoScope(),
        authStateFlow = mockAuthStateFlow,
        onAuthenticated = mockOnAuthenticated,
    )

    /**
     * The app scope, unconfined, so the login write runs at once instead of waiting for the test
     * body to suspend. It still ends with the test, like the real one ends with the process.
     */
    private fun TestScope.appIoScope(): CoroutineScope =
        CoroutineScope(backgroundScope.coroutineContext + UnconfinedTestDispatcher(testScheduler))
}
