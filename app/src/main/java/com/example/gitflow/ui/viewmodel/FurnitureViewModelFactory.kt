package com.example.gitflow.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gitflow.data.repository.FurnitureRepository

class FurnitureViewModelFactory(private val furnitureRepository: FurnitureRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FurnitureViewmodel::class.java)) {
            return FurnitureViewmodel(furnitureRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }
}