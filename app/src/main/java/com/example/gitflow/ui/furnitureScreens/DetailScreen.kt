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


@Composable
fun DetailScreen(navController: NavController) {
    val furniture = navController.previousBackStackEntry?.savedStateHandle?.get<Furniture>("furniture")

    furniture?.let {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Image
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

            // Title
            Text(
                text = it.title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Color.Black,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Price & Rating
            Text(
                text = "Price: $${it.price}",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF4CAF50) // Green shade for price
            )

            Text(
                text = "⭐ ${it.rating}  |  ${it.reviews} Reviews",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Gray,
                modifier = Modifier.padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Description
            Text(
                text = it.description,
                fontSize = 16.sp,
                color = Color.DarkGray,
                textAlign = TextAlign.Justify
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Back Button
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
