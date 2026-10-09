package com.mkchtv.cleantemplate.domain.settings.usecase

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
class SetDynamicColorTest {

    @MockK
    private lateinit var mockRepository: SettingsRepository

    private lateinit var setDynamicColor: SetDynamicColor

    @BeforeAll
    fun `setup mocks`() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @BeforeEach
    fun `setup use case`() {
        setDynamicColor = SetDynamicColor(repository = mockRepository)
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
    fun `set dynamic color hands true to the repository`() = runTest {
        // [GIVEN]
        val enabled = true

        // [WHEN]
        setDynamicColor(enabled)

        // [THEN]
        coVerify(exactly = 1) { mockRepository.setDynamicColor(true) }
    }

    @Test
    fun `set dynamic color hands false to the repository, not inverted`() = runTest {
        // [GIVEN]
        val enabled = false

        // [WHEN]
        setDynamicColor(enabled)

        // [THEN]
        coVerify(exactly = 1) { mockRepository.setDynamicColor(false) }
    }
}
