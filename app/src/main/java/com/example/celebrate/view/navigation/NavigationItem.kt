package com.example.assignment.view.navigation

import androidx.compose.ui.graphics.vector.ImageVector

// Data class to represent navigation items
data class NavigationItem(
    val label: String,
    val icon: ImageVector,
    val route: String
)