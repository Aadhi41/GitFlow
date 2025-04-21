package com.example.gitflow.data.repository

import com.example.gitflow.data.dao.FavouriteDao
import com.example.gitflow.domain.FavouriteEntity
import kotlinx.coroutines.flow.Flow

class FavouriteRepository(private val favouriteDao: FavouriteDao) {

    // Add a favourite item
    suspend fun addFavourite(furniture: FavouriteEntity) {
        favouriteDao.addFavourite(furniture)
    }

    // Remove a favourite item
    suspend fun removeFavourite(furniture: FavouriteEntity) {
        favouriteDao.removeFavourite(furniture)
    }


    // Get all favourite items
    fun getAllFavourites(): Flow<List<FavouriteEntity>> {
        return favouriteDao.getAllFavourites()
    }
}
