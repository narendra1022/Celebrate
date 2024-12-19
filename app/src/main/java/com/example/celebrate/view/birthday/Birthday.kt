package com.example.assignment.view.birthday

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.assignment.viewmodel.DashBoardViewModel
import com.example.flobiz.data.model.Card
import com.example.flobiz.presentation.dashboard.EventItem
import java.util.Date

@Composable
fun BirthDayScreen(viewModel: DashBoardViewModel = hiltViewModel()) {
    val allEvents by viewModel.transactions.collectAsState(initial = emptyList())
    val upcomingEvents = remember(allEvents) {
        allEvents.filter { it.date.after(Date()) }
            .sortedBy { it.date }
            .take(4)
    }
    val remainingEvents = remember(allEvents) {
        allEvents.filterNot { upcomingEvents.contains(it) }
            .sortedBy { it.date }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Upcoming Events",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(16.dp)
        )

        Column {
            upcomingEvents.forEach { card ->
                EventItem(
                    card = card,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        }

        Text(
            text = "All Events",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(16.dp)
        )

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(remainingEvents) { card ->
                EventItem(
                    card = card,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        }
    }
}

