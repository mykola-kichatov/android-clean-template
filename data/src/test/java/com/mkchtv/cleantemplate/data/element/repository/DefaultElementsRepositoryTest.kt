package com.mkchtv.cleantemplate.data.element.repository

import com.mkchtv.cleantemplate.data.element.dao.ElementsDao
import com.mkchtv.cleantemplate.data.element.entity.ElementEntity
import com.mkchtv.cleantemplate.data.element.mapper.toDbEntity
import com.mkchtv.cleantemplate.data.element.mapper.toDomain
import com.mkchtv.cleantemplate.data.element.network.ElementResponse
import com.mkchtv.cleantemplate.data.element.network.ElementsService
import com.mkchtv.cleantemplate.domain.common.Constants
import com.mkchtv.cleantemplate.domain.element.entity.Element
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.flow.first
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
    lateinit var mockDao: ElementsDao

    @MockK
    lateinit var mockService: ElementsService

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
        val entity = ElementEntity(0, "test_name", "test_desc")
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
        val entity = ElementEntity(0, "test_name", "test_desc")
        every { mockDao.elementFlow(0) } returns flowOf(entity)
        val expected = entity.toDomain()

        // [WHEN]
        val actual = repository.elementFlow(0).first()

        // [THEN]
        verify { mockDao.elementFlow(0) }
        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `element flow gives new element in case of no stored element found`() = runTest {
        // [GIVEN]
        every { mockDao.elementFlow(any()) } returns flowOf(null)
        val expected = Element.NEW

        // [WHEN]
        val actual = repository.elementFlow(0).first()

        // [THEN]
        verify { mockDao.elementFlow(0) }
        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `create element happy path`() = runTest {
        // [GIVEN]
        val element = Element(Constants.NEW_ELEMENT_ID, "test_name", "test_desc")
        val entity = element.toDbEntity()

        // [WHEN]
        repository.createOrUpdate(element)

        // [THEN]
        coVerify { mockDao.insert(entity) }
    }

    @Test
    fun `update element happy path`() = runTest {
        // [GIVEN]
        val element = Element(123, "test_name", "test_desc")
        val entity = element.toDbEntity()

        // [WHEN]
        repository.createOrUpdate(element)

        // [THEN]
        coVerify { mockDao.update(entity) }
    }

    @Test
    fun `fetch new element happy path`() = runTest {
        // [GIVEN]
        val response = ElementResponse("test_name", "test_desc")
        val entity = response.toDbEntity()
        coEvery { mockService.getRandomElement() } returns response

        // [WHEN]
        repository.fetchNewElement()

        // [THEN]
        coVerify { mockDao.insert(entity) }
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
