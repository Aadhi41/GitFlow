package com.example.gitflow.ui.address

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.gitflow.ui.viewmodel.AddressViewModel

@Composable
fun AddressScreen(viewModel: AddressViewModel) {
    val addressState by viewModel.addressState.collectAsState()

    if (addressState == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator() // Show loading indicator until data is ready
        }
        return
    }

    var showAddressForm by remember { mutableStateOf(addressState == null) }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (!showAddressForm && addressState != null) {
            SavedAddressView(addressState!!) { showAddressForm = true }
        } else {
            AddressFormPopup(
                addressState = addressState,
                onDismiss = { showAddressForm = false },
                onSave = {
                    viewModel.saveAddress(it)
                    showAddressForm = false
                },
                viewModel = viewModel
            )
        }
    }
}













