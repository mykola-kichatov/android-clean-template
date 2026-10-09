package com.mkchtv.cleantemplate

import com.mkchtv.cleantemplate.domain.settings.entity.Appearance
import com.mkchtv.cleantemplate.domain.settings.entity.ThemeMode
import com.mkchtv.cleantemplate.domain.settings.usecase.ObserveAppearance
import com.mkchtv.cleantemplate.testing.MainDispatcherExtension
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MainDispatcherExtension::class)
class MainViewModelTest {

    @MockK
    private lateinit var mockObserveAppearance: ObserveAppearance

    // The store. It has not answered until a test emits.
    private lateinit var storedAppearance: MutableSharedFlow<Appearance>

    private lateinit var viewModel: MainViewModel

    @BeforeAll
    fun `setup mocks`() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @BeforeEach
    fun `setup view model`() {
        storedAppearance = MutableSharedFlow()
        every { mockObserveAppearance() } returns storedAppearance
        viewModel = MainViewModel(observeAppearance = mockObserveAppearance)
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
    fun `appearance is null until the store answers, so the splash stays`() {
        // [GIVEN] a store that has not answered yet
        // [WHEN]
        // [THEN]
        Assertions.assertNull(viewModel.appearance.value)
    }

    @Test
    fun `the stored appearance arrives with nothing collecting, so the splash can go`() = runTest {
        // [GIVEN]
        val stored = Appearance(themeMode = ThemeMode.DARK, dynamicColor = false)

        // [WHEN] the store answers. Nothing collects: the splash condition only reads the value.
        storedAppearance.emit(stored)

        // [THEN]
        Assertions.assertEquals(stored, viewModel.appearance.value)
    }
}
