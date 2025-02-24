package com.example.gitflow.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gitflow.ui.chatbot.ChatbotScreen
import com.example.gitflow.ui.mainScreens.HomeScreen

@Composable
fun MainScreen() {
    val navController = rememberNavController()
<<<<<<< HEAD



=======
>>>>>>> 4bb4818 (API, Category & Updates)
    NavHost(navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("chatbot") { ChatbotScreen { navController.popBackStack() } }
    }
}