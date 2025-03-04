package com.example.gitflow.ui.furnitureScreens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.gitflow.domain.Furniture
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import com.example.gitflow.domain.Category
import com.example.gitflow.domain.Model
@Composable
fun DetailScreen(navController: NavController) {
    val furniture = navController.previousBackStackEntry?.savedStateHandle?.get<Furniture>("furniture")
    val categories = navController.previousBackStackEntry?.savedStateHandle?.get<List<Category>>("categories")

    // ✅ Check if the selected furniture is the first product in any category
    val isFirstInCategory = categories?.any { it.furnitures.firstOrNull() == furniture } == true

    furniture?.let {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = it.images.firstOrNull(),
                contentDescription = it.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = it.title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Color.Black,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Price: $${it.price}",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF4CAF50)
            )

            Text(
                text = "⭐ ${it.rating}  |  ${it.reviews} Reviews",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Gray,
                modifier = Modifier.padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = it.description,
                fontSize = 16.sp,
                color = Color.DarkGray,
                textAlign = TextAlign.Justify
            )

            Spacer(modifier = Modifier.height(20.dp))

            // ✅ Show AR Button Only for First Product in Each Category
            if (isFirstInCategory) {
                Button(
                    onClick = { navController.navigate("ar_screen") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(text = "View in AR", fontSize = 18.sp)
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(text = "Back", fontSize = 18.sp)
            }
        }
    } ?: run {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("No Data Available", fontSize = 18.sp, color = Color.Gray)
        }
    }
}


