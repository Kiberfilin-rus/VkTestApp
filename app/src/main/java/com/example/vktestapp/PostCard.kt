package com.example.vktestapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_NIGHT_NO
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vktestapp.domain.FeedPost
import com.example.vktestapp.domain.StatisticItem
import com.example.vktestapp.domain.StatisticType
import com.example.vktestapp.ui.theme.VkTestAppTheme

@Composable
fun PostCard(
    modifier: Modifier = Modifier,
    feedPost: FeedPost,
    onStatisticsItemClickListener: (StatisticItem) -> Unit
) {
    Card(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            ShapkaPosta(
                pictureResId = feedPost.avatarResId,
                groupName = feedPost.communityName,
                date = feedPost.publicationDate
            )
            Spacer(modifier = Modifier.height(8.dp))
            TeloPosta(text = feedPost.contentText, feedPost.contentImageResId)
            Spacer(modifier = Modifier.height(8.dp))
            PodvalPosta(
                statistics = feedPost.statistics,
                onItemClickListener = onStatisticsItemClickListener
            )
        }
    }
}

@Composable
private fun Counter(
    drawableId: Int,
    count: String,
    onItemClickListener: () -> Unit
) {
    Row(
        modifier = Modifier.clickable {
            onItemClickListener()
        },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(20.dp),
            painter = painterResource(drawableId),
            tint = MaterialTheme.colorScheme.onSecondary,
            contentDescription = stringResource(R.string.icon_description)
        )
        Text(
            modifier = Modifier.padding(start = 4.dp),
            text = count,
            color = MaterialTheme.colorScheme.onSecondary
        )
    }
}

@Preview(name = "Counter Light Mode", showBackground = true, uiMode = UI_MODE_NIGHT_NO)
@Preview(name = "Counter Dark Mode", showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ShowCounter() {
    VkTestAppTheme {
        Counter(drawableId = R.drawable.ic_like, "43", onItemClickListener = {})
    }
}

@Composable
private fun TeloPosta(
    text: String,
    pictureResId: Int
) {
    Text(
        text = text,
        color = MaterialTheme.colorScheme.onPrimary,
        lineHeight = 16.sp
    )
    Image(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(top = 8.dp),
        painter = painterResource(pictureResId),
        contentDescription = stringResource(R.string.post_picture_description),
        contentScale = ContentScale.FillWidth
    )
}

@Composable
private fun PodvalPosta(
    statistics: List<StatisticItem>,
    onItemClickListener: (StatisticItem) -> Unit
) {
    Row {
        Row(modifier = Modifier.weight(1f)) {
            val viewsItem = statistics.getItemByType(StatisticType.VIEWS)
            Counter(
                drawableId = R.drawable.ic_views_count,
                count = viewsItem.count.toString(),
                onItemClickListener = {
                    onItemClickListener(viewsItem)
                }
            )
        }
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val sharesItem = statistics.getItemByType(StatisticType.SHARES)
            Counter(
                drawableId = R.drawable.ic_share,
                count = sharesItem.count.toString(),
                onItemClickListener = {
                    onItemClickListener(sharesItem)
                }
            )
            val commentsItem = statistics.getItemByType(StatisticType.COMMENTS)
            Counter(
                drawableId = R.drawable.ic_comment,
                count = commentsItem.count.toString(),
                onItemClickListener = {
                    onItemClickListener(commentsItem)
                }
            )
            val likesItem = statistics.getItemByType(StatisticType.LIKES)
            Counter(
                drawableId = R.drawable.ic_like,
                count = likesItem.count.toString(),
                onItemClickListener = {
                    onItemClickListener(likesItem)
                }
            )
        }
    }
}

private fun List<StatisticItem>.getItemByType(type: StatisticType): StatisticItem {
    return this.find { it.type == type } ?: throw IllegalStateException()
}

@Composable
private fun ShapkaPosta(
    pictureResId: Int,
    groupName: String,
    date: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .size(50.dp)
                .clip(shape = CircleShape)
                .background(color = MaterialTheme.colorScheme.background),
            painter = painterResource(pictureResId),
            contentDescription = "Фотка сообщества"
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = groupName,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = date,
                color = MaterialTheme.colorScheme.onSecondary
            )
        }
        Icon(
            imageVector = Icons.Rounded.MoreVert,
            contentDescription = "Меню",
            tint = MaterialTheme.colorScheme.onSecondary
        )
    }
}

@Preview(name = "PostCard Light Mode", showBackground = true, uiMode = UI_MODE_NIGHT_NO)
@Preview(name = "PostCard Dark Mode", showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ShowPostCard() {
    VkTestAppTheme {
        PostCard(modifier = Modifier, feedPost = FeedPost(), onStatisticsItemClickListener = {})
    }
}