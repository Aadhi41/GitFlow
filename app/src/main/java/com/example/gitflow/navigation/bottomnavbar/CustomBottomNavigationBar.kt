package com.example.gitflow.navigation.bottomnavbar

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.gitflow.ui.theme.WarmOrange

@Composable
fun CustomBottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItem("Home", Icons.Default.Home, "home"),
        BottomNavItem("Favorites", Icons.Default.Favorite, "favorites"),
        BottomNavItem("Cart", Icons.Default.ShoppingCart, "cart"),
        BottomNavItem("Profile", Icons.Default.Person, "profile")
    )

    var selectedItem by remember { mutableStateOf(0) }
    val hapticFeedback = LocalHapticFeedback.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(18.dp))
            .shadow(10.dp, RoundedCornerShape(18.dp))
            .background(Color.White)
    ) {
        NavigationBar(
            modifier = Modifier
                .border(1.dp, WarmOrange, RoundedCornerShape(18.dp))
                .fillMaxWidth()
                .height(90.dp),
            containerColor = Color.Transparent
        ) {
            items.forEachIndexed { index, item ->
                val isSelected = selectedItem == index
                val animatedScale by animateFloatAsState(
                    targetValue = if (isSelected) 1.2f else 1f, label = "icon_scale"
                )

                NavigationBarItem(
                    selected = isSelected,
                    onClick = {
                        selectedItem = index
                        hapticFeedback.performHapticFeedback(androidx.compose.ui.hapticfeedback.HapticFeedbackType.LongPress)
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title,
                                tint = if (isSelected) Color(0xFFEB5A3C) else Color.Gray,
                                modifier = Modifier
                                    .size(if (isSelected) 28.dp else 24.dp)
                                    .graphicsLayer(scaleX = animatedScale, scaleY = animatedScale)
                            )
                            Text(
                                text = item.title,
                                color = if (isSelected) Color(0xFFEB5A3C) else Color.Gray,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                fontSize = if (isSelected) 14.sp else 12.sp,
                            )
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        unselectedIconColor = Color.Gray,
                        indicatorColor = Color.Transparent
                    )
                )
            }
        }
    }
}



data class BottomNavItem(val title: String, val icon: ImageVector, val route: String)

@Preview(showBackground = true)
@Composable
fun PreviewCustomBottomNavigationBar() {
    val navController = rememberNavController()
    CustomBottomNavigationBar(navController)
}
