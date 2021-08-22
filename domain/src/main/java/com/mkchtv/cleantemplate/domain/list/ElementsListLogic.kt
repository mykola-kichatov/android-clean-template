package com.mkchtv.cleantemplate.domain.list

import com.mkchtv.cleantemplate.domain.base.BaseUILogic
import com.mkchtv.cleantemplate.domain.base.UILogic
import com.mkchtv.cleantemplate.domain.common.Constants
import com.mkchtv.cleantemplate.domain.common.L10N
import com.mkchtv.cleantemplate.domain.entity.Element
import com.mkchtv.cleantemplate.domain.repository.ElementsRepository
import com.mkchtv.cleantemplate.domain.common.UIMessageNotifier
import kotlinx.coroutines.launch
import javax.inject.Inject

interface ElementsListLogic : UILogic {

    fun onDeleteConfirmed(element: Element)

}

class DefaultElementsListLogic @Inject constructor(
    private val repository: ElementsRepository,
    private val uiMessageNotifier: UIMessageNotifier,
    private val l10N: L10N
) : BaseUILogic(), ElementsListLogic {

    override fun onDeleteConfirmed(element: Element) {
        uiScope.launch {
            repository.delete(element.id)
            val info = l10N.getString(Constants.KEY_INFO_MESSAGE_ELEMENT_DELETED)
            uiMessageNotifier.notifyInfo(info)
        }
    }
}