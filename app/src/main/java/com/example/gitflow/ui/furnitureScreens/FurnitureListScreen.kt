package com.example.gitflow.ui.furnitureScreens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gitflow.ui.furnitureScreens.furnituresections.CategoryItem
import com.example.gitflow.ui.furnitureScreens.furnituresections.FurnitureCard
import com.example.gitflow.ui.viewmodel.FurnitureViewModel

@Composable
fun FurnitureListScreen(navController: NavController, viewModel: FurnitureViewModel) {
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
            val filteredFurniture = when (selectedCategory) {
                null -> furnitureState!!.categories.flatMap { it.furnitures }.shuffled() // Shuffle only for "All"
                else -> furnitureState!!.categories.find { it.name == selectedCategory }?.furnitures ?: emptyList()
            }


            if (filteredFurniture.isNotEmpty()) {
                LazyColumn {
                    items(filteredFurniture) { furniture ->
                        FurnitureCard(furniture) {
                            // Pass data using savedStateHandle
                            navController.currentBackStackEntry?.savedStateHandle?.set("furniture", furniture)
                            navController.navigate("detailScreen")
                        }
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

