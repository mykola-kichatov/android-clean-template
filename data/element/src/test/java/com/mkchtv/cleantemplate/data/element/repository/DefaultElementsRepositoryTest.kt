package com.mkchtv.cleantemplate.data.element.repository

import com.mkchtv.cleantemplate.data.element.dao.ElementsDao
import com.mkchtv.cleantemplate.data.element.entity.ElementEntity
import com.mkchtv.cleantemplate.data.element.extension.toDbEntity
import com.mkchtv.cleantemplate.data.element.extension.toPullData
import com.mkchtv.cleantemplate.data.element.mapper.toDomain
import com.mkchtv.cleantemplate.data.element.network.ElementResponse
import com.mkchtv.cleantemplate.data.element.network.ElementsService
import com.mkchtv.cleantemplate.domain.element.entity.EditedElementData
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
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
class DefaultElementsRepositoryTest {

    @MockK
    private lateinit var mockDao: ElementsDao

    @MockK
    private lateinit var mockService: ElementsService

    private lateinit var repository: DefaultElementsRepository

    @BeforeAll
    fun `setup mocks`() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @BeforeEach
    fun `setup repository`() {
        repository = DefaultElementsRepository(
            dao = mockDao,
            service = mockService,
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
    fun `elements flow works correctly`() = runTest {
        // [GIVEN]
        val entity = ElementEntity(
            id = 0,
            name = "test_name",
            description = "test_desc",
            imageUrl = "test_desc",
        )
        every { mockDao.elementsFlow() } returns flowOf(listOf(entity))
        val expected = listOf(entity.toDomain())

        // [WHEN]
        val actual = repository.elementsFlow().first()

        // [THEN]
        verify { mockDao.elementsFlow() }
        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `element flow happy path`() = runTest {
        // [GIVEN]
        val entity = ElementEntity(
            id = 0,
            name = "test_name",
            description = "test_desc",
            imageUrl = "test_desc",
        )
        every { mockDao.elementFlow(0) } returns flowOf(entity)
        val expected = entity.toDomain()

        // [WHEN]
        val actual = repository.elementFlow(0).first()

        // [THEN]
        verify { mockDao.elementFlow(0) }
        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `element flow gives null in case of no stored element found`() = runTest {
        // [GIVEN]
        every { mockDao.elementFlow(any()) } returns flowOf(null)

        // [WHEN]
        val actual = repository.elementFlow(0).firstOrNull()

        // [THEN]
        verify { mockDao.elementFlow(0) }
        Assertions.assertNull(actual)
    }

    @Test
    fun `create element happy path`() = runTest {
        // [GIVEN]
        val data = EditedElementData(
            name = "test_name",
            description = "test_desc",
            imageUrl = "test_image",
        )
        val entity = data.toDbEntity()

        // [WHEN]
        repository.create(data)

        // [THEN]
        coVerify { mockDao.insert(entity) }
    }

    @Test
    fun `update element happy path`() = runTest {
        // [GIVEN]
        val data = EditedElementData(
            name = "test_name",
            description = "test_desc",
            imageUrl = "test_image",
        )
        val entity = data.toDbEntity(elementId = 123)

        // [WHEN]
        repository.update(123, data)

        // [THEN]
        coVerify { mockDao.update(entity) }
    }

    @Test
    fun `pull element happy path`() = runTest {
        // [GIVEN]
        val response = ElementResponse("test_name", "test_desc")
        val expected = response.toPullData()
        coEvery { mockService.getRandomElement() } returns response

        // [WHEN]
        val actual = repository.pullElement()

        // [THEN]
        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `delete element happy path`() = runTest {
        // [GIVEN]

        // [WHEN]
        repository.delete(123)

        // [THEN]
        coVerify { mockDao.delete(123) }
    }
}
