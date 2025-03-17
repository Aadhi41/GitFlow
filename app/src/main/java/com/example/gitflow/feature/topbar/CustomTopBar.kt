package com.example.gitflow.feature.topbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gitflow.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopBar() {
    TopAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(75.dp),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        title = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween, // Pushes title & image to opposite ends
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Furnishing Furnitures",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    ),
                    color = MaterialTheme.colorScheme.onPrimary
                )

                Image(
                    painter = painterResource(id = R.drawable.pfp),
                    contentDescription = "User profile picture",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(35.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color.White, CircleShape)
                )
            }
        },
        navigationIcon = {} // Empty to avoid unwanted spacing
    )
}











@Preview
@Composable
fun CustomTopBarPreview() {
    MaterialTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            CustomTopBar()
        }
    }
}
