package com.example.flobiz.presentation.dashboard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.assignment.viewmodel.DashBoardViewModel

@Composable
fun DashboardScreen(
    viewModel: DashBoardViewModel = hiltViewModel(),
    onAddEvent: () -> Unit
) {
    val events by viewModel.transactions.collectAsState()
    val filterQuery by viewModel.filterQuery.collectAsState()


    // Clearing filter when the screen is recomposed
    LaunchedEffect(Unit) {
        viewModel.updateFilterQuery("")
    }

    Box(
        modifier = Modifier
            .fillMaxSize()

    ) {
        Column {
            OutlinedTextField(
                value = filterQuery,
                textStyle = TextStyle(
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold
                ),
                onValueChange = { query ->
                    viewModel.updateFilterQuery(query)
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search"
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 8.dp),
                placeholder = {
                    Text(
                        text = "Search",
                        fontWeight = FontWeight.SemiBold
                    )
                },
                singleLine = true
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 5.dp),
                text = "All Events",
                color = Color.Gray,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(5.dp))
            EventList(
                cards = events,
                onDeleteEvent = { transaction ->
                    viewModel.deleteTransaction(transaction.id)
                }
            )
        }

    }
}

