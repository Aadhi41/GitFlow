package com.example.gitflow.ui.furnitureScreens

import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import com.example.gitflow.R
import com.example.gitflow.domain.Category
import androidx.compose.foundation.pager.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.ui.platform.LocalContext
import com.example.gitflow.ui.viewmodel.CartViewModel
import com.example.gitflow.ui.viewmodel.FavouriteViewModel

@Composable
fun DetailScreen(
    navController: NavController,
    viewModel: FavouriteViewModel,
    cartViewModel: CartViewModel,


) {
//    var furniture by rememberSaveable {
//        mutableStateOf(navController.previousBackStackEntry?.savedStateHandle?.get<Furniture>("furniture"))
//    }
    val furniture = navController.previousBackStackEntry?.savedStateHandle?.get<Furniture>("furniture")
    var categories by rememberSaveable {
        mutableStateOf(navController.previousBackStackEntry?.savedStateHandle?.get<List<Category>>("categories"))
    }
    val cartItems = cartViewModel.cartItems.collectAsState().value
    val isInCart = furniture?.let { cartItems.any { it.title == furniture!!.title } } ?: false
    val context = LocalContext.current
     val category = categories?.find { it.furnitures.contains(furniture) }
     val isFirstInCategory = category?.furnitures?.firstOrNull() == furniture
//    val isFavourite = viewModel.favouriteList.collectAsState().value.contains(furniture)

    val favouriteList = viewModel.favouriteList.collectAsState().value
    val isFavourite = favouriteList.any { it.title == furniture?.title }




    if (furniture == null) {
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
                .verticalScroll(rememberScrollState())
                .padding(bottom = 16.dp)
        ) {
            val images = furniture?.images ?: emptyList()
            val pagerState = rememberPagerState { images.size }
            val coroutineScope = rememberCoroutineScope()

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(280.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.LightGray)
            ) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize()
                ) { page ->
                    AsyncImage(
                        model = images[page],
                        contentDescription = furniture?.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier
                            .size(40.dp)
                            .background(Color.White.copy(alpha = 0.7f), shape = CircleShape)
                            .padding(6.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.back),
                            contentDescription = "Back",
                            tint = Color.Black,
                            modifier = Modifier.size(24.dp)
                        )
                    }



                    IconButton(
                        onClick = {
                            furniture?.let {
                                if (viewModel.isFavourite(it)) {
                                    viewModel.removeFromFavourite(it)
                                } else {
                                    viewModel.addToFavourite(it)
                                }
                            }
                        }
                    ) {
                        Icon(
                            painter = painterResource(
                                id = if (viewModel.isFavourite(furniture!!)) R.drawable.heart_2 else R.drawable.heart_1
                            ),
                            contentDescription = "Favourite Icon",
                            tint = Color.Red,
                            modifier = Modifier.size(28.dp)
                        )
                    }



                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(images.size) { index ->
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .size(if (pagerState.currentPage == index) 12.dp else 8.dp)
                            .background(
                                if (pagerState.currentPage == index) Color.DarkGray else Color.Gray,
                                shape = CircleShape
                            )
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = furniture?.title.orEmpty(),
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = "Price: $${furniture?.price}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF4CAF50),
                    modifier = Modifier
                        .padding(top = 6.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "⭐ ${furniture?.rating}  |  ${furniture?.reviews} Reviews",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Text(
                text = furniture?.description.orEmpty(),
                fontSize = 16.sp,
                color = Color.DarkGray,
                textAlign = TextAlign.Justify,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            if (isFirstInCategory) {
                Button(
                    onClick = {
                        navController.currentBackStackEntry?.savedStateHandle?.set("furniture", furniture)
                        navController.currentBackStackEntry?.savedStateHandle?.set("category", category?.name)
                        navController.navigate("ar_screen")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .height(55.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.augmentedreality),
                        contentDescription = "View in AR",
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "View in AR", fontSize = 18.sp)
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { navController.navigate("address") },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 6.dp)
                        .height(55.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(text = "Buy Now", fontSize = 18.sp)
                }

                Button(
                    onClick = {
                        furniture?.let {
                            cartViewModel.toggleCart(it)
                            val msg = if (cartViewModel.isInCart(it)) {
                                "Added to Cart"
                            } else {
                                "Removed from Cart"
                            }
                            if (msg.isNotEmpty()) {
                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                            }

                        }
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 6.dp)
                        .height(55.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800))
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.shoppingbag),
                        contentDescription = "Add to Cart",
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = if (isInCart) "In Cart" else "Add to Cart", fontSize = 18.sp)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    navController.currentBackStackEntry?.savedStateHandle?.set("furniture", furniture)
                    navController.navigate("review_screen")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(55.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF03A9F4))
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.feedback),
                    contentDescription = "Leave a Review",
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Leave a Review", fontSize = 18.sp)
            }
        }
    }
}

