package com.example.rifaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.rifaapp.data.AppDatabase
import com.example.rifaapp.repository.RifaRepository
import com.example.rifaapp.ui.RifaNavHost
import com.example.rifaapp.ui.theme.RifaAppTheme
import com.example.rifaapp.viewmodel.RifaViewModel

/**
 * Actividad principal de la aplicación.
 *
 * Es el punto de entrada de la app donde se inicializan:
 * - La base de datos Room.
 * - El repositorio.
 * - El ViewModel.
 * - La interfaz de usuario con Jetpack Compose.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializa la base de datos Room con el nombre "rifas-db"
        // .fallbackToDestructiveMigration() permite eliminar y recrear la base de datos si hay un cambio de versión sin migración
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "rifas-db"
        ).fallbackToDestructiveMigration().build()

        // Se crea el repositorio, que contiene la lógica de acceso a datos a través del DAO
        val repository = RifaRepository(db.rifaDao())

        // Se instancia el ViewModel con el repositorio
        val viewModel = RifaViewModel(repository)

        // Se inicia la interfaz gráfica utilizando Jetpack Compose
        setContent {
            // Se aplica el tema personalizado definido en ui.theme.RifaAppTheme
            RifaAppTheme {
                // Se crea un controlador de navegación
                val navController = rememberNavController()

                // Se inicializa el host de navegación con las rutas de la app
                RifaNavHost(navController = navController, viewModel = viewModel)
            }
        }
    }
}





