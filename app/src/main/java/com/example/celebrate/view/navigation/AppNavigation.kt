package com.example.assignment.view.navigation


import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.assignment.view.anniversary.AnniversaryScreen
import com.example.assignment.view.birthday.BirthDayScreen
import com.example.flobiz.presentation.dashboard.DashboardScreen
import com.example.flobiz.presentation.transactionEntryScreen.EntryScreen
import com.exyte.animatednavbar.AnimatedNavigationBar
import com.exyte.animatednavbar.animation.balltrajectory.Parabolic
import com.exyte.animatednavbar.animation.indendshape.Height
import com.exyte.animatednavbar.animation.indendshape.shapeCornerRadius
import com.exyte.animatednavbar.utils.noRippleClickable

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController()
) {

    // State to track selected index for AnimatedNavigationBar
    var selectedIndex by remember { mutableStateOf(0) }


    val navItems = listOf(
        NavigationItem("Dashboard", Icons.Default.Home, Screen.DashBoard.route),
        NavigationItem("Birthdays", Icons.Default.Face, Screen.BirthDay.route),
        NavigationItem("Anniversaries", Icons.Default.Favorite, Screen.Anniversary.route),
        NavigationItem("Add", Icons.Default.AddCircle, Screen.AddTask.route)
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 40.dp)

            ) {
                AnimatedNavigationBar(
                    modifier = Modifier.fillMaxWidth(),
                    selectedIndex = selectedIndex,
                    barColor = MaterialTheme.colorScheme.surfaceVariant,
                    ballColor = MaterialTheme.colorScheme.primary,
                    cornerRadius = shapeCornerRadius(cornerRadius = 34.dp),
                    ballAnimation = Parabolic(tween(300)),
                    indentAnimation = Height(tween(200))
                ) {
                    navItems.forEachIndexed { index, item ->
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .noRippleClickable {
                                    selectedIndex = index
                                    navController.navigate(item.route) {
                                        popUpTo(navController.graph.startDestinationId) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.label,
                                tint = if (selectedIndex == index)
                                    MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)

        ) {
            NavHost(
                navController = navController,
                startDestination = Screen.DashBoard.route
            ) {

                composable(Screen.DashBoard.route) {
                    DashboardScreen(
                        onAddEvent = {
                            selectedIndex = 3
                            navController.navigate(Screen.AddTask.route)
                        }
                    )
                }

                composable(Screen.BirthDay.route) {
                    BirthDayScreen()
                }

                composable(Screen.Anniversary.route) {
                    AnniversaryScreen()
                }

                composable(Screen.AddTask.route) {
                    EntryScreen(
                        onEventSaved = {
                            selectedIndex = 0
                            navController.navigateUp()
                        }
                    )
                }
            }
        }
    }
}


