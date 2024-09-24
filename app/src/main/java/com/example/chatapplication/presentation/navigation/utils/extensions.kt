package com.example.chatapplication.presentation.navigation.utils

import androidx.navigation.NavHostController

fun NavHostController.navigateAndClearBackStack(route: String) {
    this.navigate(route) {
        popUpTo(0) {
            inclusive = true
        }
    }
}