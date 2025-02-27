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

@Composable
fun DetailScreen(navController: NavController) {
    val furniture = navController.previousBackStackEntry?.savedStateHandle?.get<Furniture>("furniture")

    furniture?.let {
        Column(modifier = Modifier.padding(16.dp)) {
            AsyncImage(
                model = it.images.firstOrNull(),
                contentDescription = it.title,
                modifier = Modifier.fillMaxWidth().height(250.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(it.title, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Text("Price: $${it.price}", fontSize = 18.sp)
            Text("Rating: ${it.rating} ⭐", fontSize = 18.sp)
            Text("Reviews: ${it.reviews}", fontSize = 18.sp)
            Spacer(modifier = Modifier.height(10.dp))
            Text(it.description, fontSize = 16.sp)
        }
    } ?: Text("No Data Available")
}
