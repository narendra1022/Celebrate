package com.example.assignment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flobiz.data.model.Card
import com.example.flobiz.data.repository.DashBoardRepository
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
    private val repository: DashBoardRepository
) : ViewModel() {

    private val _transactions = MutableStateFlow<List<Card>>(emptyList())
    private val _filterQuery = MutableStateFlow("")
    val filterQuery: StateFlow<String> = _filterQuery.asStateFlow()
    val transactions: StateFlow<List<Card>> = combine(
        _transactions,
        _filterQuery
    ) { transactions, query ->
        if (query.isEmpty()) transactions
        else transactions.filter {
            it.description.contains(query, ignoreCase = true)
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    init {
        fetchAllTransactions()
    }

    fun updateFilterQuery(query: String) {
        _filterQuery.value = query
    }

    private fun fetchAllTransactions() {
        viewModelScope.launch {
            repository.getAllEvents().collect { transactions ->
                _transactions.value = transactions
            }
        }
    }

    fun addTransaction(card: Card) {
        viewModelScope.launch {
            repository.addEvent(card)
        }
    }

    fun deleteTransaction(transactionId: String) {
        viewModelScope.launch {
            repository.deleteEvent(transactionId.toLong())  // Assuming ID is a Long in Room
        }
    }
}

