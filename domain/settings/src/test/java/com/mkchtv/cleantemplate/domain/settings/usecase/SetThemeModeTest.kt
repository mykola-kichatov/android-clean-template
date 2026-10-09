package com.mkchtv.cleantemplate.domain.settings.usecase

import com.mkchtv.cleantemplate.domain.settings.entity.ThemeMode
import com.mkchtv.cleantemplate.domain.settings.repository.SettingsRepository
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SetThemeModeTest {

    @MockK
    private lateinit var mockRepository: SettingsRepository

    private lateinit var setThemeMode: SetThemeMode

    @BeforeAll
    fun `setup mocks`() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @BeforeEach
    fun `setup use case`() {
        setThemeMode = SetThemeMode(repository = mockRepository)
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
    fun `set theme mode hands the mode to the repository`() = runTest {
        // [GIVEN]
        val themeMode = ThemeMode.DARK

        // [WHEN]
        setThemeMode(themeMode)

        // [THEN]
        coVerify(exactly = 1) { mockRepository.setThemeMode(ThemeMode.DARK) }
    }
}
