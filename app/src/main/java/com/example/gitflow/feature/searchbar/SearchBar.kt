package com.example.gitflow.feature.searchbar

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.gitflow.R
import com.example.gitflow.domain.Furniture
import com.example.gitflow.ui.theme.GitFlowTheme
import com.example.gitflow.ui.viewmodel.SearchViewModel
import kotlinx.coroutines.delay

@Composable
fun SearchScreen(navController: NavController, viewModel: SearchViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val searchResults by viewModel.searchResults.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    var searchText by remember { mutableStateOf(TextFieldValue("")) }
    var expanded by remember { mutableStateOf(false) }

    LaunchedEffect(searchText.text) {
        if (searchText.text.length > 2) {
            delay(300) // Debounce input
            viewModel.searchFurniture(searchText.text)
            expanded = true
        } else {
            expanded = false
        }
    }

    Column {
        // Search Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = searchText,
                onValueChange = { searchText = it },
                placeholder = { Text("Search furniture", fontSize = 16.sp, color = Color.Gray) },
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
                    .clip(RoundedCornerShape(25.dp))
                    .background(Color.White)
                    .padding(horizontal = 16.dp),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                ),
                trailingIcon = {
                    if (searchText.text.isNotEmpty()) {
                        Icon(
                            painter = painterResource(id = R.drawable.clear),
                            contentDescription = "Clear Icon",
                            tint = Color.Gray,
                            modifier = Modifier
                                .size(24.dp)
                                .clickable {
                                    searchText = TextFieldValue("")
                                    expanded = false
                                }
                        )
                    } else {
                        Icon(
                            painter = painterResource(id = R.drawable.search),
                            contentDescription = "Search Icon",
                            tint = Color.Gray,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            )
        }

        // Loading Indicator
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(8.dp),
                color = MaterialTheme.colorScheme.primary
            )
        }

        // Search Results Dropdown with Animation
        AnimatedVisibility(visible = expanded, enter = fadeIn(), exit = fadeOut()) {
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, shape = RoundedCornerShape(12.dp))
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                searchResults?.categories?.flatMap { it.furnitures }?.forEach { furniture ->
                    DropdownMenuItem(
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                AsyncImage(
                                    model = furniture.images.firstOrNull(),
                                    contentDescription = furniture.title,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = furniture.title,
                                    fontSize = 16.sp,
                                    color = Color.Black
                                )
                            }
                        },
                        onClick = {
                            expanded = false
                            navController.currentBackStackEntry?.savedStateHandle?.set("furniture", furniture)
                            navController.navigate("detailScreen")
                        }
                    )
                    Divider()
                }
            }
        }
    }
}

@Preview
@Composable
fun SearchScreenPreview() {
    GitFlowTheme {
        SearchScreen(navController = NavController(LocalContext.current))
    }
}
