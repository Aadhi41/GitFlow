import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import com.example.gitflow.domain.Furniture
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import coil.compose.AsyncImage
import com.example.gitflow.R


// Data class for storing reviews
data class Review(val text: String, val rating: Int, val imageUri: Uri? = null)

@Composable
fun ReviewScreen(navController: NavController, furniture: Furniture) {
    var reviewText by remember { mutableStateOf(TextFieldValue()) }
    var rating by remember { mutableIntStateOf(0) }
    var reviews by remember { mutableStateOf<List<Review>>(emptyList()) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current

    val imagePickerLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        selectedImageUri = uri
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Back Button and Title
        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
                Text(text = "Write a Review", style = MaterialTheme.typography.headlineSmall)
            }
        }

        // Furniture Details with Image
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = furniture.images.firstOrNull(), // Ensure image URL is valid
                    contentDescription = furniture.title,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.LightGray),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(text = furniture.title, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
                    Text(text = furniture.description, style = MaterialTheme.typography.bodySmall)
                }
            }
        }

        // Star Rating
        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Rate this Product:", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(8.dp))
                for (i in 1..5) {
                    Icon(
                        painter = painterResource(id = if (i <= rating) R.drawable.heart_2 else R.drawable.heart_1),
                        contentDescription = "Star $i",
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { rating = i }
                    )
                    Spacer(modifier = Modifier.width(4.dp)) // Add spacing between stars
                }
            }
        }

        // Upload Photo/Video Section
        item {
            Column {
                Text(text = "Add Photo", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .background(Color.Gray.copy(alpha = 0.1f), shape = RoundedCornerShape(8.dp))
                        .clickable { imagePickerLauncher.launch("image/*") }, // Open image picker
                    contentAlignment = Alignment.Center
                ) {
                    if (selectedImageUri == null) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.Add, contentDescription = "Upload", tint = Color.Gray, modifier = Modifier.size(32.dp))
                            Text(text = "Click here to upload", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                        }
                    } else {
                        AsyncImage(
                            model = selectedImageUri,
                            contentDescription = "Uploaded Image",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }

        item {
            Column {
                Text(text = "Write your Review", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
                OutlinedTextField(
                    value = reviewText,
                    onValueChange = {
                        if (it.text.length <= 400) reviewText = it
                    },
                    placeholder = { Text("Would you like to write anything about this product?") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 4
                )
                Text(
                    text = "${400 - reviewText.text.length} characters remaining",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    modifier = Modifier.align(Alignment.End)
                )
            }
        }

        // Submit Button
        item {
            Button(
                onClick = {
                    if (rating > 0) {
                        reviews = reviews + Review(reviewText.text, rating, selectedImageUri)
                        reviewText = TextFieldValue()
                        rating = 0
                        selectedImageUri = null
                        Toast.makeText(context, "Review submitted!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Please select a star rating!", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00798C)),
                enabled = rating > 0 // Button is disabled if no stars are selected
            ) {
                Text("Submit Review", color = Color.White)
            }

        }

        // Display Submitted Reviews
        items(reviews) { review ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Row {
                        for (i in 1..5) {
                            Icon(
                                painter = painterResource(id = if (i <= review.rating) R.drawable.heart_2 else R.drawable.heart_1),
                                contentDescription = "Star",
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = review.text, style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.height(8.dp))

                    // Show uploaded image if available
                    review.imageUri?.let { uri ->
                        AsyncImage(
                            model = uri,
                            contentDescription = "Review Image",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }
    }
}




