package com.example.gitflow.ui.furnitureScreens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.gitflow.domain.Furniture
import com.example.gitflow.ui.favoritesscreen.WishlistScreenUI
import com.example.gitflow.ui.viewmodel.FavouriteViewModel

@Composable
fun FavouriteScreen(
    navController: NavController,
    viewModel: FavouriteViewModel
) {
    val favouriteList = viewModel.favouriteList.collectAsState().value

    if (favouriteList.isEmpty()) {
        WishlistScreenUI(navController)
    } else {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(favouriteList) { item ->
                Card(
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            // Navigate to the detail screen if needed
                        }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(12.dp)
                    ) {
                        AsyncImage(
                            model = item.imageUrl,
                            contentDescription = item.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(90.dp)
                                .clip(RoundedCornerShape(12.dp))
                        )
                        Spacer(modifier = Modifier.width(12.dp))

                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = item.title,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "₹${item.price}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Gray
                            )
                        }

                        // Add a Remove Button to unfavourite the item
//                        IconButton(
//                            onClick = { viewModel.removeFromFavourite(item) }
//                        ) {
//                            Icon(
//                                imageVector = Icons.Default.Delete,
//                                contentDescription = "Remove from favourites",
//                                tint = Color.Red
//                            )
//                        }

                    }
            }
        }
    }
}

}





