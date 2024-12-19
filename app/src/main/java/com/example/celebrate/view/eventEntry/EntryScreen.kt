package com.example.flobiz.presentation.transactionEntryScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.assignment.utils.EventType
import com.example.flobiz.data.model.Card
import com.example.assignment.viewmodel.DashBoardViewModel
import com.example.celebrate.R
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryScreen(
    dashBoardViewModel: DashBoardViewModel = hiltViewModel(),
    onEventSaved: () -> Unit
) {
    var description by remember { mutableStateOf("") }
    var eventType by remember { mutableStateOf(EventType.BIRTHDAY) }
    var selectedDate by remember { mutableStateOf(Date()) }
    var isDatePickerVisible by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Row {
            Row(modifier = Modifier.fillMaxWidth()) {
                RadioButton(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    selected = eventType == EventType.BIRTHDAY,
                    onClick = { eventType = EventType.BIRTHDAY }
                )
                Text("BirthDay", modifier = Modifier.align(Alignment.CenterVertically))

                RadioButton(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    selected = eventType == EventType.ANNIVERSARY,
                    onClick = { eventType = EventType.ANNIVERSARY }
                )
                Text("Anniversary", modifier = Modifier.align(Alignment.CenterVertically))
            }
        }

        // Date Selection
        OutlinedTextField(
            value = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(selectedDate),
            onValueChange = {},
            label = { Text("Date") },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { isDatePickerVisible = true }) {
                    Icon(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(5.dp),
                        painter = painterResource(id = R.drawable.calendar),
                        contentDescription = "Change Date"
                    )
                }
            }
        )

        // Description Input
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 2
        )

        // Save Button
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.purple_200),
                contentColor = Color.White
            ),
            onClick = {
                if (description.isNotEmpty()) {
                    val card = Card(
                        date = selectedDate,
                        type = eventType,
                        description = description
                    )
                    dashBoardViewModel.addEvent(card)
                    onEventSaved()
                } else {
                    scope.launch {
                        snackbarHostState.showSnackbar("Write Description")
                        // showSnackbar - suspend function
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                "Save Event",
                color = Color.Black
            )
        }
    }

    // Date Picker Dialog
    if (isDatePickerVisible) {
        val datePickerState = rememberDatePickerState()
        DatePickerDialog(
            onDismissRequest = { isDatePickerVisible = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        val selectedDateMillis = datePickerState.selectedDateMillis
                        if (selectedDateMillis != null) {
                            selectedDate = Date(selectedDateMillis)
                            isDatePickerVisible = false
                        }
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { isDatePickerVisible = false }) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }


    }
}
