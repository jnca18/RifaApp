package com.example.rifaapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.rifaapp.viewmodel.RifaViewModel


@Composable
fun RifaNavHost(navController: NavHostController, viewModel: RifaViewModel) {
    NavHost(navController = navController, startDestination = "list") {
        composable("list") {
            RifaListScreen(viewModel = viewModel, navController = navController)
        }
        composable("create") {
            RifaCreateScreen(viewModel = viewModel, navController = navController)
        }
        composable(
            route = "detail/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val rifaId = backStackEntry.arguments?.getInt("id") ?: return@composable
            val rifa = viewModel.getRifaById(rifaId).collectAsState(initial = null).value
            rifa?.let {
                RifaDetailScreen(rifa = it, viewModel = viewModel, navController = navController)
            }
        }
    }
}

