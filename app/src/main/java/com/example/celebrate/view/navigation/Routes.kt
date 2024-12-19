package com.example.assignment.view.navigation

// Navigation routes
sealed class Screen(val route: String) {
    object DashBoard : Screen("dashboard")
    object BirthDay : Screen("birthday")
    object Anniversary : Screen("anniversary")
    object AddTask : Screen("add_task")
}