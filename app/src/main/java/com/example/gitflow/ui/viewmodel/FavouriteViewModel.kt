package com.example.gitflow.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
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
        viewModelScope.launch {
            repository.getAllFavourites().collect { list ->
                _favouriteList.value = list
            }
        }
    }

    fun addToFavourite(furniture: Furniture) {
        val entity = FavouriteEntity(
            title = furniture.title,
            price = furniture.price,
            imageUrl = furniture.images.firstOrNull() ?: "",
            description = furniture.description
        )
        viewModelScope.launch {
            repository.addFavourite(entity)
        }
    }

    fun removeFromFavourite(furniture: Furniture) {
        viewModelScope.launch {
            repository.removeFavourite(furniture.title)
        }
    }

    fun isFavourite(furniture: Furniture): Boolean {
        return _favouriteList.value.any { it.title == furniture.title }
    }
}







