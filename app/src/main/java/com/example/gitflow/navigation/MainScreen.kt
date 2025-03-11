package com.example.gitflow.navigation
import ReviewScreen
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gitflow.navigation.bottomnavbar.CustomBottomNavigationBar
import com.example.gitflow.ui.ar.ARScreen
import com.example.gitflow.ui.cartscreen.CartScreen
import com.example.gitflow.ui.chatbot.ChatbotScreen
import com.example.gitflow.ui.favoritesscreen.FavoritesScreen
import com.example.gitflow.ui.furnitureScreens.DetailScreen
import com.example.gitflow.ui.mainScreens.HomeScreen
import com.example.gitflow.ui.profilescreen.ProfileScreen
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.navigation.navArgument
import com.example.gitflow.domain.Furniture

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { CustomBottomNavigationBar(navController) }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavHost(navController, startDestination = "home") {
                composable("home") { HomeScreen(navController) }
                composable("favorites") { FavoritesScreen() }
                composable("cart") { CartScreen() }

                // Passing a Parcelable object (Furniture)
                composable("review_screen") { backStackEntry ->
                    val furniture = navController.previousBackStackEntry?.savedStateHandle?.get<Furniture>("furniture")
                    if (furniture != null) {
                        ReviewScreen(navController, furniture)
                    }
                }


                composable("profile") { ProfileScreen() }
                composable("chatbot") { ChatbotScreen { navController.popBackStack() } }
                composable("detailScreen") { DetailScreen(navController) }
                composable("ar_screen") { ARScreen(navController) }
            }
        }
    }
}

