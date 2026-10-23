package com.example.vktestapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_NIGHT_NO
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Preview
import com.example.vktestapp.ui.theme.VkTestAppTheme

@Composable
fun MainScreen(modifier: Modifier, viewModel: MainViewModel) {
    val selectedNavItem by viewModel.selectedNavItem.observeAsState(NavigationItem.Home)
    Scaffold(
        modifier = modifier,
        bottomBar = {
            NavigationBar {
                val items =
                    listOf(NavigationItem.Home, NavigationItem.Favorite, NavigationItem.Profile)
                items.forEach { item ->
                    NavigationBarItem(
                        selected = selectedNavItem == item,
                        onClick = { viewModel.selectNavItem(item) },
                        icon = {
                            Icon(item.icon, null)
                        },
                        label = { Text(text = stringResource(item.titleResId)) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                            selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                            unselectedIconColor = MaterialTheme.colorScheme.onSecondary,
                            unselectedTextColor = MaterialTheme.colorScheme.onSecondary,
                            indicatorColor = MaterialTheme.colorScheme.primary
                        )
                    )
                }
            }
        }
    ) { paddingValues ->
        when (selectedNavItem) {
            NavigationItem.Home -> HomeScreen(
                modifier = Modifier.padding(paddingValues),
                viewModel = viewModel
            )

            NavigationItem.Favorite -> TextCounter(
                modifier = Modifier.padding(paddingValues),
                selectedNavItem
            )

            NavigationItem.Profile -> TextCounter(
                modifier = Modifier.padding(paddingValues),
                selectedNavItem
            )
        }

    }
}

@Composable
private fun TextCounter(modifier: Modifier, selectedItem: NavigationItem) {
    var count by remember { mutableIntStateOf(0) }
    Text(
        modifier = modifier.clickable(onClick = { count++ }),
        text = "${stringResource(selectedItem.titleResId)} count = $count"
    )
}

@Preview(name = "MainScreen Light Mode", showBackground = true, uiMode = UI_MODE_NIGHT_NO)
@Preview(name = "MainScreen Dark Mode", showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ShowMainScreen() {
    VkTestAppTheme {
        MainScreen(modifier = Modifier, viewModel = MainViewModel())
    }
}