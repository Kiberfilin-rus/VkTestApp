package com.example.vktestapp

import androidx.compose.animation.core.keyframesWithSpline
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_NIGHT_NO
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vktestapp.domain.FeedPost
import com.example.vktestapp.ui.theme.VkTestAppTheme

@Composable
fun MainScreen(modifier: Modifier, viewModel: MainViewModel) {
    Scaffold(
        modifier = modifier,
        bottomBar = {
            NavigationBar {
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
        }
    ) { paddingValues ->
        val feedPostState = viewModel.feedPosts.observeAsState(listOf())
        LazyColumn(
            modifier = Modifier.padding(paddingValues),
            contentPadding = PaddingValues(
                top = 16.dp,
                bottom = 8.dp,
                start = 8.dp,
                end = 8.dp,
            ),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                items = feedPostState.value,
                key = { it.id }
            ) {feedPost: FeedPost ->
                val dismissThresholds = with(receiver = LocalDensity.current) {
                    LocalConfiguration.current.screenWidthDp.dp.toPx() * 0.3F
                }
                val dismissState = rememberSwipeToDismissBoxState(
                    positionalThreshold = { dismissThresholds },
                )
                SwipeToDismissBox(
                    modifier = Modifier.animateItem(
                        placementSpec = keyframesWithSpline {
                            durationMillis = 1000
                            IntOffset(0, 0) at 0
                            IntOffset(500, 100) at 100
                            IntOffset(400, 50) at 150
                        }, fadeInSpec = tween(), fadeOutSpec = tween()
                    ),
                    state = dismissState,
                    enableDismissFromEndToStart = true,
                    enableDismissFromStartToEnd = false,
                    backgroundContent = {
                        Box(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxSize()
                                .background(Color.Red.copy(alpha = 0.7F)),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            Text(
                                modifier = Modifier.padding(16.dp),
                                text = "Delete item",
                                color = Color.White,
                                fontSize = 24.sp
                            )
                        }
                    },
                    onDismiss = {
                        viewModel.delete(feedPost)
                    }
                ) {
                    PostCard(
                    feedPost = feedPost,
                    onLikeClickListener = viewModel::updateCount,
                    onShareClickListener = viewModel::updateCount,
                    onCommentClickListener = viewModel::updateCount,
                    onViewsClickListener = viewModel::updateCount
                )
                }
            }
        }
    }
}

@Preview(name = "MainScreen Light Mode", showBackground = true, uiMode = UI_MODE_NIGHT_NO)
@Preview(name = "MainScreen Dark Mode", showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ShowMainScreen() {
    VkTestAppTheme {
        MainScreen(modifier = Modifier, viewModel = MainViewModel())
    }
}