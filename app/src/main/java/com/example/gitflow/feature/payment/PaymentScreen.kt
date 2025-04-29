package com.example.gitflow.feature.payment

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import java.util.Calendar


@Composable
fun PaymentScreen() {
    var selectedMethod by remember { mutableStateOf("Card") }
    var cardNumber by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }
    var upiAddress by remember { mutableStateOf("") }
    var showProcessing by remember { mutableStateOf(false) }
    var orderPlaced by remember { mutableStateOf(false) }
    var isCvvVisible by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)

    fun showDatePicker() {
        DatePickerDialog(
            context,
            { _, selectedYear, selectedMonth, _ ->
                expiryDate = String.format("%02d/%02d", selectedMonth + 1, selectedYear % 100)
            },
            year,
            month,
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Select Payment Method",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            listOf("Card", "UPI", "COD").forEach { method ->
                Button(
                    onClick = { selectedMethod = method },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedMethod == method) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondaryContainer
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(method, color = if (selectedMethod == method) Color.White else Color.Black)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        AnimatedVisibility(visible = selectedMethod == "Card") {
            Column(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = cardNumber,
                    onValueChange = {
                        val digitsOnly = it.replace("[^\\d]".toRegex(), "")
                        if (digitsOnly.length <= 16) {
                            cardNumber = digitsOnly.chunked(4).joinToString(" ")
                        }
                    },
                    label = { Text("Card Number") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = expiryDate,
                    onValueChange = { input ->
                        if (input.length <= 5 && input.matches(Regex("^\\d{0,2}/?\\d{0,2}$"))) {
                            expiryDate = if (input.length == 2 && !input.contains("/")) "$input/" else input
                        }
                    },
                    label = { Text("Expiry Date (MM/YY)") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showDatePicker() }
                )

                OutlinedTextField(
                    value = cvv,
                    onValueChange = {
                        if (it.length <= 3) cvv = it.filter { char -> char.isDigit() }
                    },
                    label = { Text("CVV") },
                    trailingIcon = {
                        IconButton(onClick = { isCvvVisible = !isCvvVisible }) {
                            Icon(
                                imageVector = if (isCvvVisible) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = "Toggle CVV Visibility"
                            )
                        }
                    },
                    visualTransformation = if (isCvvVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        AnimatedVisibility(visible = selectedMethod == "UPI") {
            Column(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = upiAddress,
                    onValueChange = { upiAddress = it },
                    label = { Text("UPI Address") },
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    "Or scan QR code",
                    color = Color.Blue,
                    modifier = Modifier.clickable { /* Handle QR code scanning */ }
                )
            }
        }

        AnimatedVisibility(visible = selectedMethod == "COD") {
            Text("Cash on Delivery selected", style = MaterialTheme.typography.bodyMedium)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { showProcessing = true },
            enabled = when (selectedMethod) {
                "Card" -> cardNumber.replace(" ", "").length == 16 && expiryDate.length == 5 && cvv.length == 3
                "UPI" -> upiAddress.isNotEmpty()
                "COD" -> true
                else -> false
            },
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Pay Now")
        }

        AnimatedVisibility(visible = showProcessing) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgressIndicator()
                LaunchedEffect(Unit) {
                    delay(2000)
                    orderPlaced = true
                    showProcessing = false
                }
            }
        }

        AnimatedVisibility(visible = orderPlaced) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.CheckCircle, contentDescription = "Success", tint = Color.Green)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Order Placed Successfully!", color = Color.Green)
            }
        }
    }
}

@Composable
@Preview
fun PreviewPaymentScreen() {
    PaymentScreen()
}

