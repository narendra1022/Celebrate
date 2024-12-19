package com.example.assignment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flobiz.data.model.Card
import com.example.flobiz.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashBoardViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _events = MutableStateFlow<List<Card>>(emptyList())


    private val _filterQuery = MutableStateFlow("")
    val filterQuery: StateFlow<String> = _filterQuery.asStateFlow()

    val events: StateFlow<List<Card>> = combine(
        _events,
        _filterQuery
    ) { events, query ->
        if (query.isEmpty()) events
        else events.filter {
            it.description.contains(query, ignoreCase = true)
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    init {
        fetchAllEventss()
    }

    fun updateFilterQuery(query: String) {
        _filterQuery.value = query
    }

    private fun fetchAllEventss() {
        viewModelScope.launch {
            repository.getAllEvents().collect {
                _events.value = it
            }
        }
    }



    fun addEvent(card: Card) {
        viewModelScope.launch {
            repository.addEvent(card)
        }
    }

    fun deleteEvent(transactionId: String) {
        viewModelScope.launch {
            repository.deleteEvent(transactionId.toLong())
        }
    }
}

