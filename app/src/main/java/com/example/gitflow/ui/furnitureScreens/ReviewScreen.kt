import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gitflow.domain.Furniture

@Composable
fun ReviewScreen(navController: NavController, furniture: Furniture) {
    var reviewText by remember { mutableStateOf(TextFieldValue()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Display Furniture details
        Text(text = furniture.title, style = MaterialTheme.typography.headlineMedium)
        Text(text = furniture.description, style = MaterialTheme.typography.bodyMedium)

        // Review input
        OutlinedTextField(
            value = reviewText,
            onValueChange = { reviewText = it },
            label = { Text("Write your review") },
            modifier = Modifier.fillMaxWidth()
        )

        // Submit Button
        Button(
            onClick = {
                // Handle review submission (Store in RoomDB or Firebase)
                navController.popBackStack()
            },
        ) {
            Text("Submit Review")
        }
    }
}


