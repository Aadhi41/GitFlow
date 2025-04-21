package com.example.gitflow.ui.profilescreen

import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gitflow.R
import com.example.gitflow.domain.AddressEntity


@Composable
fun ProfileScreen(
    savedAddress: AddressEntity
) {
    val context = LocalContext.current
    var profileImageUri by remember { mutableStateOf<Uri?>(null) }
    var userName by remember { mutableStateOf("John Doe") }
    var userEmail by remember { mutableStateOf("johndoe@example.com") }
    var isEditing by remember { mutableStateOf(false) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? -> profileImageUri = uri }

    val orderHistory = listOf("Order #1234 - ₹2,999", "Order #5678 - ₹1,499", "Order #9101 - ₹899")
    val settings = listOf("Account Settings", "Privacy & Security", "Notification Preferences")

    var isOrderExpanded by remember { mutableStateOf(false) }
    var isAddressExpanded by remember { mutableStateOf(false) }
    var isSettingsExpanded by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Profile Section
        item {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.secondary)
                    .clickable { imagePickerLauncher.launch("image/*") },
                contentAlignment = Alignment.Center
            ) {
                profileImageUri?.let { uri ->
                    val bitmap = remember(uri) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                            val source = ImageDecoder.createSource(context.contentResolver, uri)
                            ImageDecoder.decodeBitmap(source)
                        } else {
                            MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
                        }
                    }
                    Image(
                        bitmap = bitmap.asImageBitmap(),
                        contentDescription = "Selected Profile Picture",
                        modifier = Modifier.size(120.dp)
                    )
                } ?: Image(
                    painter = painterResource(id = R.drawable.pfp),
                    contentDescription = "Profile Picture",
                    modifier = Modifier.size(120.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (isEditing) {
                OutlinedTextField(
                    value = userName,
                    onValueChange = { userName = it },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = userEmail,
                    onValueChange = { userEmail = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                Text(text = userName, fontSize = 20.sp, style = MaterialTheme.typography.titleMedium)
                Text(
                    text = userEmail,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row {
                Button(
                    onClick = { isEditing = !isEditing },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(if (isEditing) "Save" else "Edit Profile")
                }
                Spacer(modifier = Modifier.width(10.dp))
                OutlinedButton(
                    onClick = { /* Handle Logout */ },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Logout")
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
        item {
            ExpandableSection("Order Details", Icons.Default.Menu, isOrderExpanded, { isOrderExpanded = !isOrderExpanded }) {
                orderHistory.forEach { order -> ListItem(text = order) }
            }
        }

        item {
            ExpandableSection("Saved Addresses", Icons.Default.LocationOn, isAddressExpanded, { isAddressExpanded = !isAddressExpanded }) {
                SimpleSavedAddressView(address = savedAddress)
            }
        }

        item {
            ExpandableSection("Settings & Preferences", Icons.Default.Settings, isSettingsExpanded, { isSettingsExpanded = !isSettingsExpanded }) {
                settings.forEach { setting -> ListItem(text = setting) }
            }
        }
    }
}




@Composable
fun ListItem(text: String) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            modifier = Modifier.padding(12.dp),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
@Composable
fun ExpandableSection(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isExpanded: Boolean,
    onExpandChange: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onExpandChange() }
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = icon, contentDescription = title, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = title, fontSize = 18.sp, style = MaterialTheme.typography.titleMedium)
        }

        Column(modifier = Modifier.animateContentSize()) {
            if (isExpanded) {
                content()
            }
        }
    }
}

@Composable
fun SimpleSavedAddressView(address: AddressEntity) {
    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
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
            }
        }
    }
}


