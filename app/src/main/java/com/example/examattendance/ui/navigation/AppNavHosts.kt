package com.example.examattendance.ui.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.examattendance.ui.dashboard.DashboardScreen
import com.example.examattendance.ui.report.ReportScreen
import com.example.examattendance.ui.scanner.ScannerScreen
import com.example.examattendance.ui.splash.SplashScreen


@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.Splash.route,
        enterTransition = {
            EnterTransition.None 
        },
        exitTransition = {
            ExitTransition.None 
        }
           ) {
        composable(NavRoutes.Splash.route) {
            SplashScreen(navController)
        }
        composable(NavRoutes.Dashboard.route) {
            DashboardScreen(navController)
        }
        composable(
            route = "scanner/{examId}",
            arguments = listOf(navArgument("examId") { type = NavType.StringType })
        ) { backStackEntry ->
            val examId = backStackEntry.arguments?.getString("examId")
            if(examId!=null)
            ScannerScreen( navController, examId)
        }
        composable(NavRoutes.Report.route) {
            ReportScreen(navController)
        }
    }
}
