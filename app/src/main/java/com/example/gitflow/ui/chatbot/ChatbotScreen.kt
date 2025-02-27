package com.example.gitflow.ui.chatbot

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.gitflow.domain.getChatbotResponse
import com.example.gitflow.ui.chatbot.chatbot_sections.ChatBubble
import com.example.gitflow.ui.chatbot.chatbot_sections.ChatbotTopBar
import kotlinx.coroutines.delay

@Composable
fun ChatbotScreen(onClose: () -> Unit) {
    var chatMessages by remember { mutableStateOf(listOf<Pair<String, Boolean>>()) }
    var options by remember { mutableStateOf(listOf("Hello")) }
    var pendingUserMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        chatMessages = listOf(
            "👋 Hi there, welcome to Pepperfry! \nI am Pep, here to help you with your orders 📦 \nJust type in your query or pick an option from the menu 👇" to false
        )
    }

    LaunchedEffect(pendingUserMessage) {
        pendingUserMessage?.let { userMessage ->
            delay(500)
            val response = getChatbotResponse(userMessage)
            chatMessages = chatMessages + (response.message to false)
            options = response.options ?: emptyList()
            pendingUserMessage = null
        }
    }

    Scaffold(
        topBar = {
            Column {
                Spacer(modifier = Modifier.height(18.dp))
                ChatbotTopBar(onClose)
            }
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    reverseLayout = true
                ) {
                    items(chatMessages.reversed()) { (message, isUser) ->
                        ChatBubble(message, isUser)
                    }
                }

                options?.let {
                    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
                        it.forEach { option ->
                            Button(
                                onClick = {
                                    chatMessages = chatMessages + (option to true)
                                    options = emptyList()
                                    pendingUserMessage = option
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6600), contentColor = Color.White),
                                shape = RoundedCornerShape(20.dp),
                                modifier = Modifier.fillMaxWidth().padding(4.dp)
                            ) {
                                Text(option, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ChatbotPreview() {
    ChatbotScreen(onClose = {})
}
