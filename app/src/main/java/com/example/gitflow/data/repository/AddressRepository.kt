package com.example.gitflow.data.repository


import android.content.Context
import android.location.Geocoder
import android.location.Location
import com.example.gitflow.data.dao.AddressDao
import com.example.gitflow.domain.AddressEntity
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import java.util.*

class AddressRepository(private val context: Context, private val addressDao: AddressDao) {

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    /** 🔹 Fetch City & State from PIN Code */
    suspend fun getCityStateFromPincode(pincode: String): Pair<String, String> {
        return withContext(Dispatchers.IO) {
            val geocoder = Geocoder(context, Locale.getDefault())
            val addresses = geocoder.getFromLocationName(pincode, 1)

            if (addresses?.isNotEmpty() == true) {
                val address = addresses?.get(0)
                (address?.locality ?: "Unknown") to (address?.adminArea ?: "Unknown")
            } else {
                "Unknown" to "Unknown"
            }
        }
    }

    /** 🔹 Save Address in RoomDB (Replace Old Address) */
    suspend fun saveAddressToDB(address: AddressEntity) {
        addressDao.deleteAllAddresses() // Clear previous address
        addressDao.insertAddress(address)
    }

    /** 🔹 Get Saved Address */
    suspend fun getSavedAddress(): AddressEntity? {
        return addressDao.getAddress()
    }
}


