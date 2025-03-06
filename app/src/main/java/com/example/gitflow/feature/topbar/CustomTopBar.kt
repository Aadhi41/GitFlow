package com.example.gitflow.feature.topbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gitflow.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopBar() {
    TopAppBar(
        title = {
            Text(text = "Supellex",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold)

        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFFEB5A3C)
        ),
        actions = {
            Image(
                painter = painterResource(id = R.drawable.pfp),
                contentDescription = "Profile Picture",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.White, CircleShape)
            )
        },
        modifier = Modifier
            .height(70.dp)
            //.padding(vertical = 10.dp)
    )
}
