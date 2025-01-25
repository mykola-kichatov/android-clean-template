package com.mkchtv.cleantemplate.home.entity

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.mkchtv.cleantemplate.element.list.NAV_DESTINATION_LIST
import com.mkchtv.cleantemplate.feature.home.R
import com.mkchtv.cleantemplate.settings.NAV_DESTINATION_SETTINGS

internal enum class BottomNavigationItem(
    val imageVector: ImageVector,
    @StringRes val contentDescriptionResId: Int,
) {
    ELEMENT_LIST(
        imageVector = Icons.AutoMirrored.Filled.List,
        contentDescriptionResId = R.string.cd_bottom_bar_list,
    ),
    SETTINGS(
        imageVector = Icons.Filled.Settings,
        contentDescriptionResId = R.string.cd_bottom_bar_settings,
    );

    companion object {
        fun fromRoute(route: String?): BottomNavigationItem = when (route) {
            NAV_DESTINATION_LIST -> ELEMENT_LIST
            NAV_DESTINATION_SETTINGS -> SETTINGS
            else -> ELEMENT_LIST
        }
    }
}
