package com.mkchtv.cleantemplate.home

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.mkchtv.cleantemplate.home.entity.BottomNavigationItem

@ExperimentalMaterial3Api
@Composable
internal fun HomeBottomNavigation(
    selectedItem: BottomNavigationItem,
    onItemClick: (BottomNavigationItem) -> Unit,
) = NavigationBar {
    NavButton(
        item = BottomNavigationItem.ELEMENT_LIST,
        selected = selectedItem == BottomNavigationItem.ELEMENT_LIST,
        onClick = {
            onItemClick(BottomNavigationItem.ELEMENT_LIST)
        },
    )
    NavButton(
        item = BottomNavigationItem.SETTINGS,
        selected = selectedItem == BottomNavigationItem.SETTINGS,
        onClick = {
            onItemClick(BottomNavigationItem.SETTINGS)
        },
    )
}

@Composable
private fun RowScope.NavButton(
    item: BottomNavigationItem,
    selected: Boolean,
    onClick: () -> Unit,
) = NavigationBarItem(
    selected = selected,
    onClick = onClick,
    icon = {
        Icon(
            imageVector = item.imageVector,
            contentDescription = stringResource(id = item.contentDescriptionResId),
        )
    },
)

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun ElementListSelectedPreview() {
    Surface {
        HomeBottomNavigation(
            selectedItem = BottomNavigationItem.ELEMENT_LIST,
            onItemClick = {},
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun SettingsSelectedPreview() {
    Surface {
        HomeBottomNavigation(
            selectedItem = BottomNavigationItem.SETTINGS,
            onItemClick = {},
        )
    }
}
