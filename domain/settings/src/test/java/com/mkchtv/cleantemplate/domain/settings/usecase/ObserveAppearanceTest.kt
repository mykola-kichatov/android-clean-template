package com.mkchtv.cleantemplate.domain.settings.usecase

import app.cash.turbine.test
import com.mkchtv.cleantemplate.domain.settings.entity.Appearance
import com.mkchtv.cleantemplate.domain.settings.entity.ThemeMode
import com.mkchtv.cleantemplate.domain.settings.repository.SettingsRepository
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ObserveAppearanceTest {

    @MockK
    private lateinit var mockRepository: SettingsRepository

    private lateinit var observeAppearance: ObserveAppearance

    @BeforeAll
    fun `setup mocks`() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @BeforeEach
    fun `setup use case`() {
        observeAppearance = ObserveAppearance(repository = mockRepository)
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
    fun `observe appearance emits what the repository emits, unchanged`() = runTest {
        // [GIVEN]
        val changed = Appearance(themeMode = ThemeMode.DARK, dynamicColor = false)
        every { mockRepository.appearance } returns flowOf(Appearance(), changed)

        // [WHEN]
        // [THEN]
        observeAppearance().test {
            Assertions.assertEquals(Appearance(), awaitItem())
            Assertions.assertEquals(changed, awaitItem())
            awaitComplete()
        }
    }
}
