package com.example.flobiz.presentation.dashboard

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.celebrate.R
import com.example.flobiz.data.model.Card
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun EventItem(
    card: Card,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(5.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = card.description.ifEmpty { "No Description" },
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = if (card.type.toString() == "BIRTHDAY") "BirthDay" else "Anniversary",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = formatDate(card.date.toString()),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}


@Composable
fun DeleteBackground() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color.Red),
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            painter = painterResource(id = R.drawable.delete),
            contentDescription = "Delete",
            tint = Color.White,
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(0.3f)
                .padding(end = 5.dp)
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventList(
    cards: List<Card>,
    onDeleteEvent: (Card) -> Unit
) {
    LazyColumn {
        items(
            items = cards,
            key = { it.id }
        ) { event ->
            var isDeleted by remember { mutableStateOf(false) }
            val scope = rememberCoroutineScope()

            AnimatedVisibility(
                visible = !isDeleted,
                exit = fadeOut() + shrinkVertically()
            ) {
                SwipeToDismissBox(
                    modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
                    state = rememberSwipeToDismissBoxState(
                        confirmValueChange = { dismissDirection ->
                            when (dismissDirection) {
                                SwipeToDismissBoxValue.StartToEnd,
                                SwipeToDismissBoxValue.EndToStart -> {
                                    isDeleted = true
                                    scope.launch {
                                        delay(300) // Allow animation to complete
                                        onDeleteEvent(event)
                                    }
                                    true
                                }

                                else -> false
                            }
                        }
                    ),
                    backgroundContent = { DeleteBackground() },
                    content = {
                        EventItem(card = event)
                    }
                )
            }
        }
    }
}


fun formatDate(input: String): String {
    val inputFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH)
    val date = inputFormat.parse(input)

    val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH)
    return date?.let { outputFormat.format(it) } ?: "Invalid date"
}
