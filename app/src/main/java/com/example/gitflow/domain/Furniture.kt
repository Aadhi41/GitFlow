package com.example.gitflow.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Furniture(
    val description: String,
    val images: List<String>,
    val price: Double,
    val rating: String,
    val reviews: Int,
    val title: String
) : Parcelable
