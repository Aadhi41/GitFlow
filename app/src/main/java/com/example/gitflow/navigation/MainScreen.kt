package com.example.gitflow.navigation

import ReviewScreen
import android.app.Application
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gitflow.data.database.AppDatabase
import com.example.gitflow.data.loginandregistration.LoginScreen
import com.example.gitflow.data.loginandregistration.SignUpScreen
import com.example.gitflow.data.repository.AddressRepository
import com.example.gitflow.domain.Furniture
import com.example.gitflow.navigation.bottomnavbar.CustomBottomNavigationBar
import com.example.gitflow.ui.address.AddressScreen
import com.example.gitflow.ui.ar.ARScreen
import com.example.gitflow.ui.cartscreen.CartScreen
import com.example.gitflow.ui.chatbot.ChatbotScreen
import com.example.gitflow.ui.furnitureScreens.DetailScreen
import com.example.gitflow.ui.furnitureScreens.FavouriteScreen
import com.example.gitflow.ui.mainScreens.HomeScreen
import com.example.gitflow.ui.profilescreen.ProfileScreen
import com.example.gitflow.ui.viewmodel.*
import com.example.loginandregistration.SplashScreen

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavHost(navController, startDestination = "splash") {

                composable("splash") {
                    SplashScreen(navController)
                }

                composable("login") {
                    LoginScreen(navController)
                }

                composable("signup") {
                    SignUpScreen(navController)
                }
                composable("main") {
                    val favouriteViewModel: FavouriteViewModel = viewModel()
                    val cartViewModel: CartViewModel = viewModel()

                    val bottomBarNavController =
                        rememberNavController()
                    val bottomBarScreens = listOf("home", "cart", "favorites", "profile")
                    var currentRoute by remember { mutableStateOf("home") }

                    LaunchedEffect(bottomBarNavController) {
                        bottomBarNavController.currentBackStackEntryFlow.collect { backStackEntry ->
                            currentRoute = backStackEntry.destination.route ?: "home"
                        }
                    }

                    Scaffold(
                        bottomBar = {
                            if (currentRoute in bottomBarScreens) {
                                CustomBottomNavigationBar(bottomBarNavController)
                            }
                        }
                    ) { innerPadding ->
                        Box(modifier = Modifier.padding(innerPadding)) {
                            NavHost(
                                navController = bottomBarNavController,
                                startDestination = "home"
                            ) {
                                composable("home") {
                                    HomeScreen(bottomBarNavController)
                                }

                                composable("favorites") {
                                    FavouriteScreen(bottomBarNavController, favouriteViewModel)
                                }

                                composable("cart") {
                                    CartScreen(bottomBarNavController, cartViewModel)
                                }

                                composable("profile") {
                                    val context =
                                        LocalContext.current.applicationContext as Application
                                    val database = AppDatabase.getDatabase(context)
                                    val repository =
                                        AddressRepository(context, database.addressDao())
                                    val addressViewModel: AddressViewModel = viewModel(
                                        factory = AddressViewModelFactory(context, repository)
                                    )
                                    val address by addressViewModel.addressState.collectAsState()

                                    if (address != null) {
                                        ProfileScreen(savedAddress = address!!)
                                    } else {
                                        CircularProgressIndicator(modifier = Modifier.fillMaxSize())
                                    }
                                }

                                composable("review_screen") {
                                    val furniture = bottomBarNavController.previousBackStackEntry
                                        ?.savedStateHandle
                                        ?.get<Furniture>("furniture")

                                    if (furniture != null) {
                                        ReviewScreen(bottomBarNavController, furniture)
                                    }
                                }

                                composable("chatbot") {
                                    ChatbotScreen {
                                        bottomBarNavController.popBackStack()
                                    }
                                }

                                composable("detailScreen") {
                                    DetailScreen(
                                        navController = bottomBarNavController,
                                        cartViewModel = cartViewModel,
                                        viewModel = viewModel()
                                    )
                                }

                                composable("ar_screen") {
                                    ARScreen(bottomBarNavController)
                                }

                                composable("address") {
                                    val context =
                                        LocalContext.current.applicationContext as Application
                                    val database = AppDatabase.getDatabase(context)
                                    val repository =
                                        AddressRepository(context, database.addressDao())
                                    val addressViewModel: AddressViewModel = viewModel(
                                        factory = AddressViewModelFactory(context, repository)
                                    )
                                    AddressScreen(viewModel = addressViewModel)
                                }
                            }
                        }
                    }
                }

            }
        }
    }
}



