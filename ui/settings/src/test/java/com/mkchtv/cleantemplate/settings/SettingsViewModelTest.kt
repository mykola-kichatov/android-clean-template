package com.mkchtv.cleantemplate.settings

import app.cash.turbine.test
import com.mkchtv.cleantemplate.domain.auth.usecase.Logout
import com.mkchtv.cleantemplate.domain.settings.entity.Appearance
import com.mkchtv.cleantemplate.domain.settings.entity.ThemeMode
import com.mkchtv.cleantemplate.domain.settings.usecase.ObserveAppearance
import com.mkchtv.cleantemplate.domain.settings.usecase.SetDynamicColor
import com.mkchtv.cleantemplate.domain.settings.usecase.SetThemeMode
import com.mkchtv.cleantemplate.testing.MainDispatcherExtension
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
class SettingsViewModelTest {

    @MockK
    private lateinit var mockObserveAppearance: ObserveAppearance

    @MockK
    private lateinit var mockSetThemeMode: SetThemeMode

    @MockK
    private lateinit var mockSetDynamicColor: SetDynamicColor

    @MockK
    private lateinit var mockLogout: Logout

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
    fun `ui state carries the stored appearance`() = runTest {
        // [GIVEN]
        every { mockObserveAppearance() } returns
            flowOf(Appearance(themeMode = ThemeMode.DARK, dynamicColor = false))

        // [WHEN]
        val viewModel = viewModel()

        // [THEN]
        viewModel.uiState.test {
            Assertions.assertEquals(
                UiState(themeMode = ThemeMode.DARK, dynamicColor = false),
                awaitItem(),
            )
        }
    }

    @Test
    fun `a change in the store reaches the ui state`() = runTest {
        // [GIVEN]
        val stored = MutableStateFlow(Appearance())
        every { mockObserveAppearance() } returns stored
        val viewModel = viewModel()

        // [WHEN]
        // [THEN]
        viewModel.uiState.test {
            Assertions.assertEquals(UiState(), awaitItem())

            stored.value = Appearance(themeMode = ThemeMode.LIGHT, dynamicColor = false)

            Assertions.assertEquals(
                UiState(themeMode = ThemeMode.LIGHT, dynamicColor = false),
                awaitItem(),
            )
        }
    }

    @Test
    fun `picking a theme mode writes it`() = runTest {
        // [GIVEN]
        every { mockObserveAppearance() } returns flowOf(Appearance())
        val viewModel = viewModel()

        // [WHEN]
        viewModel.onIntent(Intent.ThemeModePicked(ThemeMode.DARK))

        // [THEN]
        coVerify(exactly = 1) { mockSetThemeMode(ThemeMode.DARK) }
    }

    @Test
    fun `toggling dynamic color writes it`() = runTest {
        // [GIVEN]
        every { mockObserveAppearance() } returns flowOf(Appearance())
        val viewModel = viewModel()

        // [WHEN]
        viewModel.onIntent(Intent.DynamicColorToggled(enabled = false))

        // [THEN]
        coVerify(exactly = 1) { mockSetDynamicColor(false) }
    }

    @Test
    fun `confirming logout logs out`() = runTest {
        // [GIVEN]
        every { mockObserveAppearance() } returns flowOf(Appearance())
        val viewModel = viewModel()

        // [WHEN]
        viewModel.onIntent(Intent.LogoutConfirmed)

        // [THEN]
        coVerify(exactly = 1) { mockLogout() }
    }

    // Built per test, after the stubs, because it reads the store on construction.
    private fun TestScope.viewModel() = SettingsViewModel(
        appIoScope = appIoScope(),
        observeAppearance = mockObserveAppearance,
        setThemeMode = mockSetThemeMode,
        setDynamicColor = mockSetDynamicColor,
        logout = mockLogout,
    )

    /**
     * The app scope, unconfined, so a fire-and-forget write runs at once instead of waiting for
     * the test body to suspend. It still ends with the test, like the real one ends with the
     * process.
     */
    private fun TestScope.appIoScope(): CoroutineScope =
        CoroutineScope(backgroundScope.coroutineContext + UnconfinedTestDispatcher(testScheduler))
}
