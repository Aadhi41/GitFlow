package com.example.gitflow.ui.address

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.gitflow.domain.AddressEntity
import com.example.gitflow.ui.viewmodel.AddressViewModel

@Composable
fun AddressFormPopup(
    addressState: AddressEntity?,
    onDismiss: () -> Unit,
    onSave: (AddressEntity) -> Unit,
    viewModel: AddressViewModel
) {
    var name by remember { mutableStateOf(addressState?.name ?: "") }
    var mobileNumber by remember { mutableStateOf(addressState?.mobileNumber ?: "") }
    var pincode by remember { mutableStateOf(addressState?.pincode ?: "") }
    var addressLine1 by remember { mutableStateOf(addressState?.addressLine1 ?: "") }
    var addressLine2 by remember { mutableStateOf(addressState?.addressLine2 ?: "") }
    var city by remember { mutableStateOf(addressState?.city ?: "") }
    var state by remember { mutableStateOf(addressState?.state ?: "") }

    val updatedAddress by viewModel.addressState.collectAsState()

    LaunchedEffect(updatedAddress) {
        city = updatedAddress?.city ?: ""
        state = updatedAddress?.state ?: ""
        pincode = updatedAddress?.pincode ?: ""
        addressLine1 = updatedAddress?.addressLine1 ?: ""
    }

    Dialog(onDismissRequest = onDismiss) {
        Surface(shape = MaterialTheme.shapes.medium) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Add Address", style = MaterialTheme.typography.titleLarge)

                TextField(value = name, onValueChange = { name = it }, label = { Text("Name") })
                TextField(value = mobileNumber, onValueChange = { mobileNumber = it }, label = { Text("Mobile Number") })

                TextField(
                    value = pincode,
                    onValueChange = {
                        pincode = it
                        if (pincode.length == 6) {
                            viewModel.fetchCityStateFromPincode(pincode)
                        }
                    },
                    label = { Text("PIN Code") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                TextField(value = addressLine1, onValueChange = { addressLine1 = it }, label = { Text("Address Line 1") })
                TextField(value = addressLine2, onValueChange = { addressLine2 = it }, label = { Text("Address Line 2") })
                TextField(value = city, onValueChange = {}, label = { Text("City") }, readOnly = true)
                TextField(value = state, onValueChange = {}, label = { Text("State") }, readOnly = true)

                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = { viewModel.fetchCurrentLocation() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Locate Me")
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row {
                    Button(onClick = onDismiss, modifier = Modifier.weight(1f)) {
                        Text("CANCEL")
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Button(
                        onClick = {
                            val newAddress = AddressEntity(
                                id = 0, name, mobileNumber, pincode, addressLine1, addressLine2, city, state, "Home", true
                            )
                            onSave(newAddress)
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("SAVE")
                    }
                }
            }
        }
    }
}





