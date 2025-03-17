package com.example.gitflow.data.dao

import androidx.room.*
import com.example.gitflow.domain.AddressEntity

@Dao
interface AddressDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAddress(address: AddressEntity)

    @Query("SELECT * FROM address_table LIMIT 1")
    suspend fun getAddress(): AddressEntity?

    @Query("DELETE FROM address_table")
    suspend fun deleteAllAddresses()
}


