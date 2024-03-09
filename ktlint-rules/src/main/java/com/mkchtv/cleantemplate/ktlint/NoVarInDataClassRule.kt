package com.mkchtv.cleantemplate.ktlint

import com.pinterest.ktlint.rule.engine.core.api.ElementType.CLASS
import com.pinterest.ktlint.rule.engine.core.api.ElementType.DATA_KEYWORD
import com.pinterest.ktlint.rule.engine.core.api.ElementType.MODIFIER_LIST
import com.pinterest.ktlint.rule.engine.core.api.ElementType.PRIMARY_CONSTRUCTOR
import com.pinterest.ktlint.rule.engine.core.api.ElementType.VALUE_PARAMETER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.VALUE_PARAMETER_LIST
import com.pinterest.ktlint.rule.engine.core.api.Rule
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import com.pinterest.ktlint.rule.engine.core.api.children
import org.jetbrains.kotlin.com.intellij.lang.ASTNode

class NoVarInDataClassRule : Rule(RuleId("custom:no-vars-in-data-class"), About()) {

    override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit,
    ) {
        if (node.isDataClass()) {
            node.getParameterWithVar()?.let { param ->
                emit(param.startOffset, "Unexpected var in Data class, use val instead", false)
            }
        }
    }
}

private fun ASTNode.isDataClass() = elementType == CLASS && findChildByType(MODIFIER_LIST)
    ?.children()
    .orEmpty()
    .any { it.elementType == DATA_KEYWORD }

private fun ASTNode.getParameterWithVar(): ASTNode? =
    getPrimaryConstructorParameterListOrNull()
        ?.children()
        .orEmpty()
        .filter { it.elementType == VALUE_PARAMETER }
        .firstOrNull { it.text.contains("var") }

private fun ASTNode.getPrimaryConstructorParameterListOrNull() =
    findChildByType(PRIMARY_CONSTRUCTOR)?.findChildByType(VALUE_PARAMETER_LIST)