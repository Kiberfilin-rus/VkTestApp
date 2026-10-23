package com.example.vktestapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vktestapp.domain.FeedPost
import com.example.vktestapp.domain.StatisticItem
import com.example.vktestapp.domain.StatisticType
import kotlin.random.Random

class MainViewModel : ViewModel() {
    private val initialList: List<FeedPost> = mutableListOf<FeedPost>().apply {
        repeat(10) {
            val postStatistics: List<StatisticItem> = listOf(
                StatisticItem(type = StatisticType.VIEWS, count = Random.nextInt(1, 11000000)),
                StatisticItem(type = StatisticType.SHARES, count = Random.nextInt(1, 1100)),
                StatisticItem(type = StatisticType.COMMENTS, count = Random.nextInt(1, 1100)),
                StatisticItem(type = StatisticType.LIKES, count = Random.nextInt(1, 1100)),
            )
            add(
                FeedPost(
                    id = it,
                    contentText = "Text поста Text поста Text по… $it",
                    statistics = postStatistics
                )
            )
        }
    }
    private val _feedPosts = MutableLiveData<List<FeedPost>>(initialList)
    val feedPosts: LiveData<List<FeedPost>> = _feedPosts
    private val _selectedNavItem: MutableLiveData<NavigationItem> =
        MutableLiveData(NavigationItem.Home)
    val selectedNavItem: LiveData<NavigationItem> = _selectedNavItem

    fun selectNavItem(item: NavigationItem) {
        _selectedNavItem.value = item
    }

    fun updateCount(item: StatisticItem) {
        val oldPost = feedPosts.value?.find {
            it.statistics.contains(item)
        } ?: throw IllegalStateException()
        val newStatistics = oldPost.statistics.toMutableList().apply {
            replaceAll { oldItem: StatisticItem ->
                if (oldItem.type == item.type) {
                    oldItem.copy(
                        count = oldItem.count + 1
                    )
                } else {
                    oldItem
                }
            }
        }
        val newFeedPosts = ArrayList<FeedPost>(_feedPosts.value ?: throw IllegalStateException())
        newFeedPosts.replaceAll {
            if (it.id == oldPost.id) {
                it.copy(
                    statistics = newStatistics
                )
            } else {
                it
            }
        }
        _feedPosts.value = newFeedPosts
    }

    fun delete(feedPost: FeedPost) {
        val modifiedList = _feedPosts.value?.toMutableList() ?: mutableListOf()
        modifiedList.remove(feedPost)
        _feedPosts.value = modifiedList
    }
}