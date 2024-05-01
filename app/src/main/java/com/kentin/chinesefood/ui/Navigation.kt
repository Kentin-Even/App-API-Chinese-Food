package com.kentin.chinesefood.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kentin.chinesefood.model.PictureFood
import com.kentin.chinesefood.ui.screens.DetailScreen
import com.kentin.chinesefood.ui.screens.ListScreen
import com.kentin.chinesefood.viewmodel.MainViewModel

sealed class Routes(val route: String) {
    data object ListScreen : Routes("list")

    data object DetailScreen : Routes("detailScreen/{id}") {
        fun withId(id : Int) = "detailScreen/$id"

        fun withObject(data : PictureFood) = "detailScreen/${data.id}"
    }
}

@Composable
fun AppNavigation() {
    val navHostController : NavHostController = rememberNavController()
    val mainViewModel : MainViewModel = viewModel()

    NavHost(navController = navHostController, startDestination = Routes.ListScreen.route) {
        composable(Routes.ListScreen.route) {
            ListScreen(mainViewModel = mainViewModel, navHostController = navHostController)
        }

        composable(
            route = Routes.DetailScreen.route,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) {
            val id = it.arguments?.getInt("id") ?: 1
            DetailScreen(id, mainViewModel = mainViewModel, navHostController = navHostController)
        }
    }
}