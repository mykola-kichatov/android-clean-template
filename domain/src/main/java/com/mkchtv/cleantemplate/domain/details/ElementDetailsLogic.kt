package com.mkchtv.cleantemplate.domain.details

import com.mkchtv.cleantemplate.domain.base.BaseUILogic
import com.mkchtv.cleantemplate.domain.base.UILogic
import com.mkchtv.cleantemplate.domain.common.Constants
import com.mkchtv.cleantemplate.domain.common.L10N
import com.mkchtv.cleantemplate.domain.common.UIMessageNotifier
import com.mkchtv.cleantemplate.domain.entity.Element
import com.mkchtv.cleantemplate.domain.entity.isNew
import com.mkchtv.cleantemplate.domain.repository.ElementsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface ElementDetailsLogic : UILogic {

    fun elementFlow(elementId: Int): Flow<Element>

    fun createUpdateIsInProgressFlow(): Flow<Boolean>

    fun createUpdateIsCompletedFlow(): Flow<Boolean>

    fun onCreateUpdateConfirmed(element: Element)

}

@ExperimentalCoroutinesApi
class DefaultElementDetailsLogic @Inject constructor(
    private val repository: ElementsRepository,
    private val uiMessageNotifier: UIMessageNotifier,
    private val l10N: L10N
) : BaseUILogic(), ElementDetailsLogic {

    private val _createUpdateIsInProgressState = MutableStateFlow(false)
    private val _createUpdateIsCompletedState = MutableStateFlow(false)

    override fun elementFlow(elementId: Int): Flow<Element> =
        repository.elementFlow(elementId)

    override fun createUpdateIsInProgressFlow(): Flow<Boolean> =
        _createUpdateIsInProgressState

    override fun createUpdateIsCompletedFlow(): Flow<Boolean> =
        _createUpdateIsCompletedState

    override fun onCreateUpdateConfirmed(element: Element) {
        uiScope.launch {
            _createUpdateIsInProgressState.value = true
            repository.createOrUpdate(element)
            val info = if (element.isNew())
                l10N.getString(Constants.KEY_INFO_MESSAGE_ELEMENT_CREATED)
            else
                l10N.getString(Constants.KEY_INFO_MESSAGE_ELEMENT_UPDATED)
            uiMessageNotifier.notifyInfo(info)
            _createUpdateIsInProgressState.value = false
            _createUpdateIsCompletedState.value = true
        }
    }

}