package com.example.gitflow.ui.apiscreens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.gitflow.domain.Furniture
import com.example.gitflow.ui.viewmodel.FurnitureViewmodel

@Composable
fun FurnitureListScreen(viewModel: FurnitureViewmodel) {
    val furnitureState by viewModel.furniture.collectAsState()
    var selectedCategory by remember { mutableStateOf<String?>(null) }

    if (furnitureState == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Color(0xFFFFA500))
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            // Categories Horizontal Scroll (LazyRow)
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                contentPadding = PaddingValues(bottom = 10.dp)
            ) {
                // "All" category button
                item {
                    CategoryItem(
                        categoryName = "All",
                        isSelected = selectedCategory == null
                    ) { selectedCategory = null }
                }

                // Other categories
                items(furnitureState!!.categories) { category ->
                    CategoryItem(
                        categoryName = category.name,
                        isSelected = category.name == selectedCategory
                    ) {
                        selectedCategory = category.name
                    }
                }
            }

            // Fetch and shuffle furniture items
            val filteredFurniture = if (selectedCategory == null) {
                furnitureState!!.categories.flatMap { it.furnitures }.shuffled() // Shuffle all items
            } else {
                furnitureState!!.categories.find { it.name == selectedCategory }
                    ?.furnitures?.shuffled() ?: emptyList() // Shuffle category items
            }

            if (filteredFurniture.isNotEmpty()) {
                LazyColumn {
                    items(filteredFurniture) { furniture ->
                        FurnitureCard(furniture)
                    }
                }
            } else {
                Text(
                    text = "No items available",
                    fontSize = 18.sp,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}


// Category Item (Horizontal Scroll)
@Composable
fun CategoryItem(categoryName: String, isSelected: Boolean, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .clickable { onClick() }, // Fetch furniture when clicked
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color(0xFFFFA500) else Color.LightGray, // Highlight selected category
            contentColor = Color.White
        )
    ) {
        Box(
            modifier = Modifier
                .height(50.dp)
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = categoryName,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

// Furniture Card UI
@Composable
fun FurnitureCard(furniture: Furniture) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            AsyncImage(
                model = furniture.images.firstOrNull(),
                contentDescription = furniture.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(furniture.title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text("Price: $${furniture.price}", fontSize = 16.sp, color = Color.Gray)
        }
    }
}