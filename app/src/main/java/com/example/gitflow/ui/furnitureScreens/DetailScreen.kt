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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import com.example.gitflow.domain.Category
import com.example.gitflow.domain.Model
@Composable
fun DetailScreen(navController: NavController) {
    var furniture by rememberSaveable { mutableStateOf(navController.previousBackStackEntry?.savedStateHandle?.get<Furniture>("furniture")) }
    var categories by rememberSaveable { mutableStateOf(navController.previousBackStackEntry?.savedStateHandle?.get<List<Category>>("categories")) }

    // ✅ Find the category this furniture belongs to
    val category = categories?.find { it.furnitures.contains(furniture) }
    val isFirstInCategory = category?.furnitures?.firstOrNull() == furniture

    if (furniture == null) {
        // ✅ Show only if data is truly null
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("No Data Available", fontSize = 18.sp, color = Color.Gray)
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = furniture?.images?.firstOrNull(),
                contentDescription = furniture?.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = furniture?.title.orEmpty(),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Color.Black,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Price: $${furniture?.price}",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF4CAF50)
            )

            Text(
                text = "⭐ ${furniture?.rating}  |  ${furniture?.reviews} Reviews",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Gray,
                modifier = Modifier.padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = furniture?.description.orEmpty(),
                fontSize = 16.sp,
                color = Color.DarkGray,
                textAlign = TextAlign.Justify
            )

            Spacer(modifier = Modifier.height(20.dp))

            // ✅ Show AR Button Only for First Product in Each Category
            if (isFirstInCategory) {
                Button(
                    onClick = {
                        navController.currentBackStackEntry?.savedStateHandle?.set("furniture", furniture)
                        navController.currentBackStackEntry?.savedStateHandle?.set("category", category?.name)
                        navController.navigate("ar_screen")
                    },
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
                onClick = {
                    navController.popBackStack()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(text = "Back", fontSize = 18.sp)
            }
        }
    }
}





