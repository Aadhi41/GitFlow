package com.example.gitflow.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_furniture")
data class FavouriteEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // Add an id field for Room to use
    val title: String,
    val price: Double,
    val imageUrl: String, // Assume you're storing the URL of the image
    val description: String // Other fields you need from Furniture
)
