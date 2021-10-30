package com.mkchtv.cleantemplate.domain.details

import com.mkchtv.cleantemplate.domain.base.BaseUILogic
import com.mkchtv.cleantemplate.domain.base.UILogic
import com.mkchtv.cleantemplate.domain.di.AppIoScope
import com.mkchtv.cleantemplate.domain.entity.Element
import com.mkchtv.cleantemplate.domain.repository.ElementsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface ElementDetailsLogic : UILogic {

    fun elementFlow(elementId: Int): Flow<Element>

    fun onCreateUpdateConfirmed(element: Element)

}

@ExperimentalCoroutinesApi
class DefaultElementDetailsLogic @Inject constructor(
    private val repository: ElementsRepository,
    @AppIoScope private val appIoScope: CoroutineScope
) : BaseUILogic(), ElementDetailsLogic {

    override fun elementFlow(elementId: Int): Flow<Element> =
        repository.elementFlow(elementId)

    override fun onCreateUpdateConfirmed(element: Element) {
        appIoScope.launch {
            repository.createOrUpdate(element)
        }
    }

}