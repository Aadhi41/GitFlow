package com.example.gitflow.navigation

import ReviewScreen
import android.app.Application
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gitflow.data.database.AppDatabase
import com.example.gitflow.data.repository.AddressRepository
import com.example.gitflow.navigation.bottomnavbar.CustomBottomNavigationBar
import com.example.gitflow.ui.ar.ARScreen
import com.example.gitflow.ui.cartscreen.CartScreen
import com.example.gitflow.ui.chatbot.ChatbotScreen
import com.example.gitflow.ui.favoritesscreen.FavoritesScreen
import com.example.gitflow.ui.furnitureScreens.DetailScreen
import com.example.gitflow.ui.mainScreens.HomeScreen
import com.example.gitflow.ui.profilescreen.ProfileScreen
import com.example.gitflow.domain.Furniture
import com.example.gitflow.ui.address.AddressScreen
import com.example.gitflow.ui.viewmodel.AddressViewModel
import com.example.gitflow.ui.viewmodel.AddressViewModelFactory

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    // Define screens where the bottom nav bar should be visible
    val bottomBarScreens = listOf("home", "cart", "favorites", "profile")

    // State to track the current route
    var currentRoute by remember { mutableStateOf("home") }

    // Update the route whenever navigation changes
    LaunchedEffect(navController) {
        navController.currentBackStackEntryFlow.collect { backStackEntry ->
            currentRoute = backStackEntry.destination.route ?: "home"
        }
    }

    Scaffold(
        bottomBar = {
            if (currentRoute in bottomBarScreens) {
                CustomBottomNavigationBar(navController)
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavHost(navController, startDestination = "home") {
                composable("home") { HomeScreen(navController) }
                composable("favorites") { FavoritesScreen() }
                composable("cart") { CartScreen() }
                composable("profile") { ProfileScreen() }

                composable("review_screen") { backStackEntry ->
                    val furniture = navController.previousBackStackEntry?.savedStateHandle?.get<Furniture>("furniture")
                    if (furniture != null) {
                        ReviewScreen(navController, furniture)
                    }
                }
                composable("chatbot") { ChatbotScreen { navController.popBackStack() } }
                composable("detailScreen") { DetailScreen(navController) }
                composable("ar_screen") { ARScreen(navController) }

                composable("address") {
                    val context = LocalContext.current.applicationContext as Application
                    val database = AppDatabase.getDatabase(context)
                    val repository = AddressRepository(context, database.addressDao())

                    val addressViewModel: AddressViewModel = viewModel(
                        factory = AddressViewModelFactory(context, repository)
                    )

                    AddressScreen(viewModel = addressViewModel)
                }
            }
        }
    }
}
