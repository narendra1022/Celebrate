package com.example.assignment.view.anniversary

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.celebrate.view.components.EventCard
import com.example.celebrate.viewmodel.AnniversaryViewModel
import java.util.Date


@Composable
fun AnniversaryScreen(anniversaryViewModel: AnniversaryViewModel = hiltViewModel()) {
    val allEvents by anniversaryViewModel.anniversaries.collectAsState(initial = emptyList())
    val upcomingEvents = remember(allEvents) {
        allEvents.filter { it.date.after(Date()) }
            .sortedBy { it.date }
            .take(4)
    }
    val remainingEvents = remember(allEvents) {
        allEvents.filterNot { upcomingEvents.contains(it) }
            .sortedBy { it.date }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        item {
            Text(
                text = "Upcoming Anniversaries",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(16.dp)
            )
        }

        if (upcomingEvents.isEmpty()) {
            item {
                Text(
                    text = "No upcoming Anniversaries",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Gray,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentSize(Alignment.Center)
                )
            }
        } else {
            items(
                items = upcomingEvents,
                key = { it.id }
            ) { event ->
                EventCard(
                    card = event,
                    onDeleteEvent = { anniversaryViewModel.deleteEvent(event.id) }
                )
            }
        }

        item {
            Text(
                text = "Recent ",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(16.dp)
            )
        }

        if (remainingEvents.isEmpty()) {
            item {
                Text(
                    text = "No Recent Anniversaries",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentSize(Alignment.Center)
                )
            }
        } else {
            items(
                items = remainingEvents,
                key = { it.id }
            ) { event ->
                EventCard(
                    card = event,
                    onDeleteEvent = { anniversaryViewModel.deleteEvent(event.id) }
                )
            }
        }
    }
}

