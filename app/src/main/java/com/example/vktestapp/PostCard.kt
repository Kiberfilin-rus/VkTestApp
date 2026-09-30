package com.example.vktestapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_NIGHT_NO
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.vktestapp.ui.theme.VkTestAppTheme

@Composable
fun PostCard(modifier: Modifier) {
    Card(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .size(50.dp)
                    .clip(shape = CircleShape)
                    .background(color = MaterialTheme.colorScheme.background),
                painter = painterResource(R.drawable.comuniti_pic),
                contentDescription = "Фотка сообщества"
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Название группы вк",
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "14:00",
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
}

@Preview(name = "PostCard Light Mode", showBackground = true, uiMode = UI_MODE_NIGHT_NO)
@Preview(name = "PostCard Dark Mode", showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ShowPostCard() {
    VkTestAppTheme {
        PostCard(modifier = Modifier)
    }
}