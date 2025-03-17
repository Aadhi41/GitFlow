package com.example.gitflow.ui.viewmodel


import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gitflow.data.repository.AddressRepository

class AddressViewModelFactory(
    private val application: Application,
    private val repository: AddressRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddressViewModel::class.java)) {
            return AddressViewModel(application, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}



