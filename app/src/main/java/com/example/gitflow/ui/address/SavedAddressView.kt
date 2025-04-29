package com.example.gitflow.ui.address

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.gitflow.domain.AddressEntity
import com.example.gitflow.domain.Furniture
import com.example.gitflow.feature.payment.PaymentScreen


@Composable
fun SavedAddressView(address: AddressEntity, onUpdateClick: () -> Unit) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(scrollState)
            .fillMaxSize()
    ) {
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
                Text("${address.name}, ${address.mobileNumber}", fontSize = 16.sp)
                Spacer(modifier = Modifier.height(8.dp))

                Text("${address.addressLine1}, ${address.addressLine2}", fontSize = 16.sp)
                Spacer(modifier = Modifier.height(8.dp))

                Text("${address.city}, ${address.state} - ${address.pincode}", fontSize = 16.sp)
                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = onUpdateClick, modifier = Modifier.fillMaxWidth()) {
                    Text("Update")
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        PaymentScreen()
    }
}




