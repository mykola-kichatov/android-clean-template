package com.mkchtv.cleantemplate.details

import android.app.Application
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.lifecycle.SavedStateHandle
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mkchtv.cleantemplate.domain.common.Constants
import com.mkchtv.cleantemplate.domain.details.ElementDetailsLogic
import com.mkchtv.cleantemplate.domain.entity.Element
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ElementDetailsScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun elementInfoIsDisplayed() {
        composeTestRule.setContent {
            val app = LocalContext.current.applicationContext as Application
            val logic = mockk<ElementDetailsLogic>()
            every { logic.elementFlow(Constants.NEW_ELEMENT_ID) } answers {
                flow {
                    emit(Element(Constants.NEW_ELEMENT_ID, "fake name", "fake desc"))
                }
            }
            every { logic.onUIInit(any()) } just Runs
            val savedStateHandle = SavedStateHandle()
            savedStateHandle.set(Constants.ARG_KEY_ELEMENT_ID, Constants.NEW_ELEMENT_ID)
            val viewModel = ElementDetailsViewModel(app, logic, savedStateHandle)
            MaterialTheme {
                ElementDetailsScreen(viewModel)
            }
        }
        composeTestRule.onNodeWithText("fake name").assertIsDisplayed()
        composeTestRule.onNodeWithText("fake desc").assertIsDisplayed()
    }

}