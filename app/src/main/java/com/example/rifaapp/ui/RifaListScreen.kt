package com.example.rifaapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.rifaapp.viewmodel.RifaViewModel

/**
 * Pantalla principal que muestra la lista de rifas creadas.
 * Permite al usuario:
 * - Buscar rifas por nombre o fecha.
 * - Ver los detalles de una rifa seleccionándola.
 * - Crear una nueva rifa.
 *
 * @param viewModel ViewModel que maneja la lógica de consulta y búsqueda.
 * @param navController Controlador de navegación para moverse entre pantallas.
 */
@Composable
fun RifaListScreen(viewModel: RifaViewModel, navController: NavController) {
    // Estado para el campo de búsqueda
    var search by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE3F2FD)) // Fondo azul claro
            .padding(16.dp)
    ) {
        // Encabezado con logo y título
        HeaderRifasUd()

        // Barra de búsqueda
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = search,
                onValueChange = { search = it },
                label = { Text("Buscar por nombre") },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFF1565C0),
                    cursorColor = Color(0xFF1565C0)
                )
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { viewModel.search(search) }) {
                Icon(Icons.Default.Search, contentDescription = "Buscar", tint = Color.White)
                Spacer(modifier = Modifier.width(4.dp))
                Text("Buscar", color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Lista de rifas (si no hay resultados de búsqueda, se muestran todas)
        val rifas = viewModel.searchResults.collectAsState().value.ifEmpty {
            viewModel.rifas.collectAsState().value
        }

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(rifas) { rifa ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .clickable { navController.navigate("detail/${rifa.id}") },
                    backgroundColor = Color.White,
                    elevation = 4.dp,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("🎟️ ${rifa.nombre}", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("📅 Fecha: ${rifa.fecha}")
                        Text("👥 Inscritos: ${rifa.boletosOcupados.size}")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Botón para crear una nueva rifa
        Button(
            onClick = { navController.navigate("create") },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF0D47A1)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Agregar", tint = Color.White)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Nueva Rifa", color = Color.White)
        }
    }
}






