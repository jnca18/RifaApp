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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "rifas-db"
        ).fallbackToDestructiveMigration().build()

        val repository = RifaRepository(db.rifaDao())
        val viewModel = RifaViewModel(repository)

        setContent {
            RifaAppTheme {
                val navController = rememberNavController()
                RifaNavHost(navController = navController, viewModel = viewModel)
            }
        }
    }
}




