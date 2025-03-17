package com.example.gitflow.ui.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.location.Geocoder
import android.location.Location
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.gitflow.data.repository.AddressRepository
import com.example.gitflow.domain.AddressEntity
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*

class AddressViewModel(application: Application, private val repository: AddressRepository) : AndroidViewModel(application) {

    private val _addressState = MutableStateFlow<AddressEntity?>(null)
    val addressState: StateFlow<AddressEntity?> = _addressState

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(application)
    private val context = application.applicationContext  // ✅ Fix for "Unresolved reference: context"

    init {
        fetchSavedAddress() // Fetch stored address on ViewModel init
    }

    fun fetchSavedAddress() {
        viewModelScope.launch {
            _addressState.value = repository.getSavedAddress()
        }
    }

    fun fetchCityStateFromPincode(pincode: String) {
        viewModelScope.launch {
            val (city, state) = repository.getCityStateFromPincode(pincode)
            _addressState.value = _addressState.value?.copy(city = city, state = state)
        }
    }

    fun saveAddress(address: AddressEntity) {
        viewModelScope.launch {
            repository.saveAddressToDB(address)
            _addressState.value = address
        }
    }

    /** 🔹 Fetch Current Location and Update Address */
    @SuppressLint("MissingPermission")
    fun fetchCurrentLocation() {
        viewModelScope.launch {
            try {
                val locationResult = fusedLocationClient.lastLocation
                locationResult.addOnSuccessListener { location: Location? ->
                    location?.let {
                        val geocoder = Geocoder(context, Locale.getDefault())
                        val addresses = geocoder.getFromLocation(it.latitude, it.longitude, 1)
                        if (addresses?.isNotEmpty() == true) {
                            val address = addresses[0]
                            _addressState.value = AddressEntity(
                                id = 0,
                                name = _addressState.value?.name ?: "",
                                mobileNumber = _addressState.value?.mobileNumber ?: "",
                                pincode = address.postalCode ?: "",
                                addressLine1 = address.getAddressLine(0) ?: "",
                                addressLine2 = "",
                                city = address.locality ?: "Unknown",
                                state = address.adminArea ?: "Unknown",
                                addressType = "Home",
                                isDefault = true // ✅ Fix for "No value passed for parameter 'isDefault'"
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}










