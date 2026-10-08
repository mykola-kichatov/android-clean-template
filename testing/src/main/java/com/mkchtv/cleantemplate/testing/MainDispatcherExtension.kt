package com.mkchtv.cleantemplate.testing

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext

/**
 * Sets [Dispatchers.Main] to [testDispatcher] around each test, so `viewModelScope` work runs on
 * the test thread. Without it, lifecycle silently runs that work on a background thread.
 *
 * Use `@ExtendWith(MainDispatcherExtension::class)`, or `@RegisterExtension` to pass a
 * `StandardTestDispatcher`.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class MainDispatcherExtension(
    val testDispatcher: TestDispatcher = UnconfinedTestDispatcher(),
) : BeforeEachCallback,
    AfterEachCallback {

    override fun beforeEach(context: ExtensionContext) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun afterEach(context: ExtensionContext) {
        Dispatchers.resetMain()
    }
}
