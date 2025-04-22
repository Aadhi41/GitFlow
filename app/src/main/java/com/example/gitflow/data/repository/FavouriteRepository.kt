package com.example.gitflow.data.repository

import com.example.gitflow.data.dao.FavouriteDao
import com.example.gitflow.domain.FavouriteEntity
import kotlinx.coroutines.flow.Flow

class FavouriteRepository(private val favouriteDao: FavouriteDao) {

    suspend fun addFavourite(furniture: FavouriteEntity) {
        favouriteDao.addFavourite(furniture)
    }

    suspend fun removeFavourite(title: String) {
        favouriteDao.removeByTitle(title)
    }

    fun getAllFavourites(): Flow<List<FavouriteEntity>> {
        return favouriteDao.getAllFavourites()
    }
}

