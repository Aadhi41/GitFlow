package com.example.gitflow.ui.address

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gitflow.domain.AddressEntity

@Composable
fun SavedAddressView(address: AddressEntity, onUpdateClick: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        // Title
        Text(
            text = "Saved Address",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Address", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))

                Text("${address.name}, ${address.mobileNumber}", fontSize = 16.sp)
                Text("${address.addressLine1}, ${address.addressLine2}", fontSize = 16.sp)
                Text("${address.city}, ${address.state} - ${address.pincode}", fontSize = 16.sp)

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = onUpdateClick, modifier = Modifier.fillMaxWidth()) {
                    Text("Update")
                }
            }
        }
    }
}
