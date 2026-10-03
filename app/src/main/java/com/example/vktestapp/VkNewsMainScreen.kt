package com.example.vktestapp

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_NIGHT_NO
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Preview
import com.example.vktestapp.ui.theme.VkTestAppTheme
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(modifier: Modifier) {
    val snackbarHostState = SnackbarHostState()
    val scope = rememberCoroutineScope()
    val fabIsVisible = remember {
        mutableStateOf(true)
    }
    Scaffold(
        modifier = modifier,
        bottomBar = {
            NavigationBar() {
                val selectedItemPosition = remember { mutableStateOf(0) }
                val items =
                    listOf(NavigationItem.Home, NavigationItem.Favorite, NavigationItem.Profile)
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedItemPosition.value == index,
                        onClick = { selectedItemPosition.value = index },
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
        },
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
        floatingActionButton = {
            if (fabIsVisible.value == true) {
                FloatingActionButton(onClick = {
                    scope.launch {
                        val action = snackbarHostState.showSnackbar(
                            message = "ALL YOUR BASE ARE BELONG TO US",
                            actionLabel = "Hide FAB",
                            duration = SnackbarDuration.Long
                        )
                        if (action == SnackbarResult.ActionPerformed) {
                            fabIsVisible.value = false
                        }
                    }
                }) {
                    Icon(contentDescription = null, imageVector = Icons.Filled.AccountBalance)
                }
            }
        },
    ) { paddingValues ->
    }


}

@Preview(name = "MainScreen Light Mode", showBackground = true, uiMode = UI_MODE_NIGHT_NO)
@Preview(name = "MainScreen Dark Mode", showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ShowMainScreen() {
    VkTestAppTheme {
        MainScreen(modifier = Modifier)
    }
}