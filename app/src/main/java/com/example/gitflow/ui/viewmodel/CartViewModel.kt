package com.example.gitflow.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.gitflow.domain.Furniture
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CartViewModel : ViewModel() {

    private val _cartItems = MutableStateFlow<List<Furniture>>(emptyList())
    val cartItems: StateFlow<List<Furniture>> = _cartItems

    private fun addToCart(item: Furniture) {
        _cartItems.value += item
    }

    fun removeFromCart(item: Furniture) {
        _cartItems.value = _cartItems.value.filter { it.title != item.title }
    }

    fun isInCart(furniture: Furniture): Boolean {
        return _cartItems.value.any { it.title == furniture.title }
    }

    fun toggleCart(furniture: Furniture) {
        if (isInCart(furniture)) {
            removeFromCart(furniture)
        } else {
            addToCart(furniture)
        }
    }
}

