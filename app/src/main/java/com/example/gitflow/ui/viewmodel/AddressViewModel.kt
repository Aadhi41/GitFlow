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

    private val _isLoading = MutableStateFlow(false)  // To manage loading state
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)  // To manage error state
    val errorMessage: StateFlow<String?> = _errorMessage

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(application)
    private val context = application.applicationContext  // Fix for "Unresolved reference: context"

    init {
        fetchSavedAddress() // Fetch saved address when ViewModel is initialized
    }

    // Fetch saved address from the repository
    fun fetchSavedAddress() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _addressState.value = repository.getSavedAddress()
            } catch (e: Exception) {
                _errorMessage.value = "Failed to fetch saved address"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Fetch city and state using the pincode from the repository
    fun fetchCityStateFromPincode(pincode: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val (city, state) = repository.getCityStateFromPincode(pincode)
                _addressState.value = _addressState.value?.copy(city = city, state = state)
            } catch (e: Exception) {
                _errorMessage.value = "Failed to fetch city and state from pincode"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Save the address to the repository (DB)
    fun saveAddress(address: AddressEntity) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.saveAddressToDB(address)
                _addressState.value = address
            } catch (e: Exception) {
                _errorMessage.value = "Failed to save address"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Fetch the current location and update the address state with it
    @SuppressLint("MissingPermission")
    fun fetchCurrentLocation() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
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
                                isDefault = true
                            )
                        }
                    }
                }.addOnFailureListener {
                    _errorMessage.value = "Failed to fetch location"
                }
            } catch (e: Exception) {
                _errorMessage.value = "An error occurred while fetching location"
            } finally {
                _isLoading.value = false
            }
        }
    }
}











