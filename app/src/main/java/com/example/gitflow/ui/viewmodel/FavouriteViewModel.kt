package com.example.gitflow.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gitflow.data.database.FavouriteDatabase
import com.example.gitflow.data.repository.FavouriteRepository
import com.example.gitflow.domain.FavouriteEntity
import com.example.gitflow.domain.Furniture
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavouriteViewModel(application: Application) : AndroidViewModel(application) {

    private val favouriteDao = FavouriteDatabase.getDatabase(application).favouriteDao()
    private val repository = FavouriteRepository(favouriteDao)

    private val _favouriteList = MutableStateFlow<List<FavouriteEntity>>(emptyList())
    val favouriteList: StateFlow<List<FavouriteEntity>> = _favouriteList

    init {
        // Observe data from Room
        viewModelScope.launch {
            repository.getAllFavourites().collect { list ->
                _favouriteList.value = list
            }
        }
    }

    // Add a furniture item to favourites
    fun addToFavourite(furniture: Furniture) {
        Log.d("FavouriteViewModel", "Adding to favourite: ${furniture.title}")
        val favouriteEntity = FavouriteEntity(
            title = furniture.title,
            price = furniture.price,
            imageUrl = furniture.images.firstOrNull() ?: "",
            description = furniture.description
        )
        viewModelScope.launch {
            repository.addFavourite(favouriteEntity)
        }
    }

    fun removeFromFavourite(furniture: Furniture) {
        // Map Furniture to FavouriteEntity
        val favouriteEntity = FavouriteEntity(
            title = furniture.title,
            price = furniture.price,
            imageUrl = furniture.images.firstOrNull() ?: "",
            description = furniture.description
        )

        // Remove the item from favourites
        viewModelScope.launch {
            repository.removeFavourite(favouriteEntity)
        }
    }

}






