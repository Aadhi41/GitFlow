package com.example.gitflow.data

data class ChatOption(val message: String, val options: List<String>?)

val chatbotResponses = mapOf(
    "hello" to ChatOption("Hi! How can I assist you?", listOf("Shop Furniture", "Track My Order", "Contact Support")),
    "shop furniture" to ChatOption("What category are you looking for?", listOf("Sofas", "Beds", "Chairs", "Tables")),
    "track my order" to ChatOption("Enter your order ID in the 'Orders' section.", null),
    "contact support" to ChatOption("You can reach us at support@yourapp.com or call 1800-123-456.", null),
    "sofas" to ChatOption("We have a variety of sofas available. Check our catalog!", null),
    "beds" to ChatOption("We offer king, queen, and single beds. Check our catalog!", null),
    "bye" to ChatOption("Goodbye! Have a great day.", null)
)

// Function to get chatbot response
fun getChatbotResponse(message: String): ChatOption {
    return chatbotResponses[message.lowercase()] ?: ChatOption("Sorry, I didn't understand. Can you rephrase?", null)
}