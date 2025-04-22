package com.example.gitflow.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_furniture")
data class FavouriteEntity(
    @PrimaryKey val title: String,
    val price: Double,
    val imageUrl: String,
    val description: String
)

