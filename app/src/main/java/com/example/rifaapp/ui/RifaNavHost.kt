package com.example.rifaapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.rifaapp.viewmodel.RifaViewModel

/**
 * Composable que define la estructura de navegación de la aplicación.
 *
 * Utiliza Navigation Compose para declarar las rutas (pantallas) disponibles y
 * cómo se conectan entre sí mediante el NavController.
 *
 * Rutas definidas:
 * - "list": Muestra la lista de rifas registradas.
 * - "create": Permite crear una nueva rifa.
 * - "detail/{id}": Muestra el detalle de una rifa seleccionada.
 *
 * @param navController Controlador de navegación que administra la pila de pantallas.
 * @param viewModel ViewModel compartido entre las pantallas.
 */
@Composable
fun RifaNavHost(navController: NavHostController, viewModel: RifaViewModel) {
    // Definición del host de navegación con su pantalla de inicio ("list")
    NavHost(navController = navController, startDestination = "list") {

        // Pantalla principal: Lista de rifas
        composable("list") {
            RifaListScreen(viewModel = viewModel, navController = navController)
        }

        // Pantalla para crear una nueva rifa
        composable("create") {
            RifaCreateScreen(viewModel = viewModel, navController = navController)
        }

        // Pantalla de detalle de una rifa específica (usando su ID)
        composable(
            route = "detail/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType }) // Argumento obligatorio: id de la rifa
        ) { backStackEntry ->
            // Recuperar el ID de la rifa desde la navegación
            val rifaId = backStackEntry.arguments?.getInt("id") ?: return@composable

            // Obtener los datos de la rifa desde el ViewModel de forma reactiva
            val rifa = viewModel.getRifaById(rifaId).collectAsState(initial = null).value

            // Mostrar pantalla de detalle solo si la rifa existe
            rifa?.let {
                RifaDetailScreen(rifa = it, viewModel = viewModel, navController = navController)
            }
        }
    }
}


