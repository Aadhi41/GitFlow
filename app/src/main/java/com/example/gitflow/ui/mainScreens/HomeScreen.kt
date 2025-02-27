package com.example.gitflow.ui.mainScreens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.gitflow.data.remote.RetrofitInstance
import com.example.gitflow.data.repository.FurnitureRepository
import com.example.gitflow.ui.furnitureScreens.FurnitureListScreen
import com.example.gitflow.ui.chatbot.ChatbotButton
import com.example.gitflow.ui.theme.GitFlowTheme
import com.example.gitflow.ui.viewmodel.FurnitureViewModel
import com.example.gitflow.ui.viewmodel.FurnitureViewModelFactory
@Composable
fun HomeScreen(navController: NavController) {
    val furnitureRepository = FurnitureRepository(RetrofitInstance.apiService)
    val viewModel: FurnitureViewModel = viewModel(factory = FurnitureViewModelFactory(furnitureRepository))

    Scaffold(
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                FurnitureListScreen(navController, viewModel)
                ChatbotButton(navController)
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GitFlowTheme {
        HomeScreen(navController = rememberNavController())
    }
}