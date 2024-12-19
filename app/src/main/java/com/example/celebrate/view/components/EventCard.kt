package com.example.celebrate.view.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.flobiz.data.model.Card
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventCard(
    card: Card,
    onDeleteEvent: (Card) -> Unit
) {
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
                                onDeleteEvent(card)
                            }
                            true
                        }

                        else -> false
                    }
                }
            ),
            backgroundContent = { DeleteBackground() },
            content = {
                EventItem(card = card)
            }
        )
    }
}