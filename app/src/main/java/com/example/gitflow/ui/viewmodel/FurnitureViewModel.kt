package com.example.gitflow.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gitflow.data.repository.FurnitureRepository
import com.example.gitflow.domain.Model
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FurnitureViewmodel(private val repository: FurnitureRepository) : ViewModel() {
    private val _furniture = MutableStateFlow<Model?>(null)  // Use StateFlow
    val furniture: StateFlow<Model?> = _furniture

    init {
        fetchFurniture()
    }

    private fun fetchFurniture() {
        viewModelScope.launch {
            try {
                val response = repository.FurnitureResponse()
                _furniture.value = response
            } catch (e: Exception) {
                println("Error fetching furniture: ${e.message}")
            }
        }
    }
}