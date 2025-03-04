package com.example.gitflow.ui.mainScreens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.gitflow.data.remote.RetrofitInstance
import com.example.gitflow.data.repository.FurnitureRepository
import com.example.gitflow.feature.banners.AutoScrollingBanner
import com.example.gitflow.feature.banners.Banner
import com.example.gitflow.feature.searchbar.SearchScreen
import com.example.gitflow.feature.topbar.CustomTopBar
import com.example.gitflow.navigation.bottomnavbar.CustomBottomNavigationBar
import com.example.gitflow.ui.furnitureScreens.FurnitureListScreen
import com.example.gitflow.ui.chatbot.ChatbotButton
import com.example.gitflow.ui.theme.GitFlowTheme
import com.example.gitflow.ui.viewmodel.FurnitureViewModel
import com.example.gitflow.ui.viewmodel.FurnitureViewModelFactory
import com.example.gitflow.ui.viewmodel.SearchViewModel

@Composable
fun HomeScreen(navController: NavController) {
    val furnitureViewModel: FurnitureViewModel =
        viewModel(factory = FurnitureViewModelFactory(FurnitureRepository(RetrofitInstance.apiService)))
    val searchViewModel: SearchViewModel = viewModel()

    Scaffold(
        topBar = { CustomTopBar() },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(5.dp),
                    contentPadding = PaddingValues(bottom = 80.dp)
                ) {
                    item { SearchScreen(navController, searchViewModel) }
                    item { Banner() }
                    item { AutoScrollingBanner(modifier = Modifier.offset(y = (-5).dp)) }
                    item { FurnitureListScreen(navController, furnitureViewModel) }
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    ChatbotButton(navController)
                }
            }
        }
    )
}



@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    GitFlowTheme {
        HomeScreen(navController = rememberNavController())
    }
}
