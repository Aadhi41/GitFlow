package com.example.gitflow.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gitflow.data.remote.RetrofitInstance
import com.example.gitflow.domain.Model
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    private val _searchResults = MutableStateFlow<Model>(Model(emptyList()))
    val searchResults: StateFlow<Model> = _searchResults

    fun searchFurniture(query: String) {
        viewModelScope.launch {
            try {
                val result = RetrofitInstance.apiService.getFurniture()
                _searchResults.emit(
                    result.copy(
                        categories = result.categories.map { category ->
                            category.copy(
                                furnitures = category.furnitures.filter {
                                    it.title.contains(query, ignoreCase = true)
                                }
                            )
                        }
                    )
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

