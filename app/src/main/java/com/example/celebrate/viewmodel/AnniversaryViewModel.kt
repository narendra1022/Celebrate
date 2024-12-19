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
class AnniversaryViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {


    private val _anniversaries = MutableStateFlow<List<Card>>(emptyList())
    val anniversaries: StateFlow<List<Card>> = _anniversaries

    init {
        fetchAllAnniversariess()
    }

    private fun fetchAllAnniversariess() {
        viewModelScope.launch {
            repository.getAllAnniversariess().collect {
                _anniversaries.value = it
            }
        }
    }

    fun deleteEvent(transactionId: String) {
        viewModelScope.launch {
            repository.deleteEvent(transactionId.toLong())
        }
    }
}