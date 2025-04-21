package com.example.gitflow.data.dao
import androidx.room.*
import com.example.gitflow.domain.FavouriteEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface FavouriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavourite(furniture: FavouriteEntity)

    @Delete
    suspend fun removeFavourite(furniture: FavouriteEntity)

    @Query("SELECT * FROM favourite_furniture")
    fun getAllFavourites(): Flow<List<FavouriteEntity>>
}

