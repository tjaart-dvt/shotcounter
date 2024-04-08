package com.shotcounter.ui.navigation

import androidx.navigation.NavHostController

fun NavHostController.observe(screen: Screen?, onNavigationRequested: () -> Unit = {}) {
    screen?.let {
        this.navigate(it.name)
        onNavigationRequested()
    }
}