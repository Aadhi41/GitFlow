package com.example.gitflow.domain


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "address_table")
data class AddressEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val mobileNumber: String,
    val pincode: String,
    val addressLine1: String,
    val addressLine2: String,
    val city: String,
    val state: String,
    val addressType: String,
    val isDefault: Boolean
)





