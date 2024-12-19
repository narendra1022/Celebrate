package com.example.celebrate.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flobiz.data.model.Card
import com.example.flobiz.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BirthDayViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _birthdays = MutableStateFlow<List<Card>>(emptyList())
    val birthdays: StateFlow<List<Card>> = _birthdays

    init {
        fetchAllBirthdays()
    }

    private fun fetchAllBirthdays() {
        viewModelScope.launch {
            repository.getAllBirthdays().collect {
                _birthdays.value = it
            }
        }
    }

    fun deleteEvent(transactionId: String) {
        viewModelScope.launch {
            repository.deleteEvent(transactionId.toLong())
        }
    }
}