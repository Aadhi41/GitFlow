package com.example.gitflow.ui.address

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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
        Surface(
            shape = RoundedCornerShape(20.dp),
            tonalElevation = 8.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Add Address",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                OutlinedTextField(
                    value = mobileNumber,
                    onValueChange = { mobileNumber = it },
                    label = { Text("Mobile Number") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                OutlinedTextField(
                    value = pincode,
                    onValueChange = {
                        pincode = it
                        if (pincode.length == 6) {
                            viewModel.fetchCityStateFromPincode(pincode)
                        }
                    },
                    label = { Text("PIN Code") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                OutlinedTextField(
                    value = addressLine1,
                    onValueChange = { addressLine1 = it },
                    label = { Text("Address Line 1") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = addressLine2,
                    onValueChange = { addressLine2 = it },
                    label = { Text("Address Line 2") },
                    modifier = Modifier.fillMaxWidth()
                )

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedTextField(
                        value = city,
                        onValueChange = {},
                        label = { Text("City") },
                        readOnly = true,
                        modifier = Modifier.weight(1f)
                    )

                    OutlinedTextField(
                        value = state,
                        onValueChange = {},
                        label = { Text("State") },
                        readOnly = true,
                        modifier = Modifier.weight(1f)
                    )
                }

                Button(
                    onClick = { viewModel.fetchCurrentLocation() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.LocationOn, contentDescription = "Locate Me")
                    Spacer(Modifier.width(8.dp))
                    Text("Locate Me")
                }

                HorizontalDivider(thickness = 1.dp)

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Cancel")
                    }

                    Button(
                        onClick = {
                            val newAddress = AddressEntity(
                                id = 0,
                                name = name,
                                mobileNumber = mobileNumber,
                                pincode = pincode,
                                addressLine1 = addressLine1,
                                addressLine2 = addressLine2,
                                city = city,
                                state = state,
                                addressType = "Home",
                                isDefault = true
                            )
                            onSave(newAddress)
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Save")
                    }
                }
            }
        }
    }
}






