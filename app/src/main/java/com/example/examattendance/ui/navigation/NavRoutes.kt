package com.example.examattendance.ui.navigation

sealed class NavRoutes(val route: String) {
    data object Splash : NavRoutes("splash")
    data object Dashboard : NavRoutes("dashboard")
    data object Scanner : NavRoutes("scanner")
    data object Report : NavRoutes("report")
}
