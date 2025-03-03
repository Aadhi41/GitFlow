package com.example.gitflow.feature.banners

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gitflow.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AutoScrollingBanner() {
    val images: List<Int> = listOf(
        R.drawable.image_banner1,
        R.drawable.image_banner2,
        R.drawable.image_banner3
    )

    val pagerState = rememberPagerState { images.size }
    val coroutineScope = rememberCoroutineScope()

    // Auto-scroll every 3 seconds
    LaunchedEffect(Unit) {
        while (true) {
            delay(3000)
            coroutineScope.launch {
                val nextPage = (pagerState.currentPage + 1) % images.size
                pagerState.animateScrollToPage(nextPage)
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .padding(16.dp)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            Card(
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = images[page]),
                    contentDescription = "Banner",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        // Indicators positioned **inside** the image banner (bottom center)
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp) // Adjust distance from bottom
                .background(Color.Black.copy(alpha = 0.3f), shape = RoundedCornerShape(50)) // Semi-transparent background
                .padding(horizontal = 12.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            images.indices.forEach { index ->
                val indicatorSize by animateFloatAsState(
                    targetValue = if (pagerState.currentPage == index) 12f else 6f,
                    label = "Indicator Animation"
                )

                Box(
                    modifier = Modifier
                        .size(indicatorSize.dp)
                        .padding(horizontal = 4.dp)
                        .background(
                            color = if (pagerState.currentPage == index) Color.White else Color.Gray,
                            shape = CircleShape
                        )
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewAutoScrollingBanner() {
    AutoScrollingBanner()
}
