package com.example.gitflow.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gitflow.ui.ar.ARScreen
import com.example.gitflow.ui.chatbot.ChatbotScreen
import com.example.gitflow.ui.furnitureScreens.DetailScreen
import com.example.gitflow.ui.mainScreens.HomeScreen

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("chatbot") { ChatbotScreen { navController.popBackStack() } }
        composable("detailScreen") {
            DetailScreen(navController)
        }
        composable("ar_screen") {
            ARScreen()
        }

    }
}