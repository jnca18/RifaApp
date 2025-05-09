package com.example.rifaapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rifaapp.data.RifaEntity
import com.example.rifaapp.viewmodel.RifaViewModel
import androidx.navigation.NavController

/**
 * Pantalla que muestra el detalle de una rifa existente.
 * Permite visualizar el nombre, boletos ocupados, seleccionar el boleto ganador,
 * habilitar/deshabilitar la selección de nuevos boletos y guardar o eliminar la rifa.
 *
 * @param rifa La rifa a mostrar/modificar.
 * @param viewModel ViewModel para acceder al repositorio y realizar operaciones de base de datos.
 * @param navController Controlador de navegación para volver a la pantalla anterior.
 */
@Composable
fun RifaDetailScreen(
    rifa: RifaEntity,
    viewModel: RifaViewModel,
    navController: NavController
) {
    // Estado del campo "boleto ganador" como string editable
    var ganador by remember { mutableStateOf(rifa.boletoGanador?.toString() ?: "") }

    // Set de boletos ocupados originalmente en la base de datos
    val originales = remember { rifa.boletosOcupados.toSet() }

    // Lista editable de boletos ocupados (se puede modificar desde la UI)
    var boletos by remember { mutableStateOf(rifa.boletosOcupados.toMutableList()) }

    // Estado para permitir o bloquear la selección de nuevos boletos
    var inhabilitar by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE3F2FD)) // fondo azul claro
            .padding(16.dp)
    ) {
        // Título
        Text("Detalle de Rifa", fontSize = 24.sp, color = Color(0xFF1565C0))
        Spacer(modifier = Modifier.height(8.dp))

        // Nombre de la rifa
        Text("Nombre: ${rifa.nombre}", fontSize = 18.sp)
        Spacer(modifier = Modifier.height(12.dp))

        // Grilla de selección de boletos (0 a 99)
        LazyVerticalGrid(columns = GridCells.Fixed(10), modifier = Modifier.weight(1f)) {
            items((0..99).toList()) { number ->
                val ocupado = boletos.contains(number)
                val esOriginal = originales.contains(number)
                val isNuevo = ocupado && !esOriginal
                val isAntiguo = ocupado && esOriginal

                // Fondo de color según el estado del boleto
                val fondo = when {
                    isNuevo -> Color(0xFF4CAF50)     // verde para boletos recién seleccionados
                    isAntiguo -> Color(0xFFD32F2F)   // rojo para boletos guardados previamente
                    else -> Color.Transparent
                }

                // Color del texto según el fondo
                val textColor = if (isNuevo || isAntiguo) Color.White else Color.Black

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(36.dp)
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(color = fondo, shape = CircleShape)
                        .border(1.dp, Color.Gray, CircleShape)
                        .clickable {
                            // Solo permite seleccionar/deseleccionar si está habilitado
                            if (inhabilitar) {
                                if (ocupado) boletos.remove(number)
                                else boletos.add(number)
                            }
                        }
                ) {
                    Text(
                        text = number.toString().padStart(2, '0'),
                        color = textColor
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Campo para escribir el boleto ganador (manual)
        OutlinedTextField(
            value = ganador,
            onValueChange = { ganador = it },
            label = { Text("Boleto ganador") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF1565C0),
                cursorColor = Color(0xFF1565C0)
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Interruptor para activar/desactivar la edición de boletos
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Inhabilitar")
            Spacer(modifier = Modifier.width(8.dp))
            Switch(checked = inhabilitar, onCheckedChange = { inhabilitar = it })
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botones inferiores: Guardar y Eliminar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    // Crea una nueva instancia con los cambios y la guarda
                    val rifaActualizada = rifa.copy(
                        boletoGanador = ganador.toIntOrNull(),
                        boletosOcupados = boletos
                    )
                    viewModel.insert(rifaActualizada)
                    navController.popBackStack()
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF0D47A1)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Guardar", color = Color.White)
            }

            Button(
                onClick = {
                    viewModel.delete(rifa)
                    navController.popBackStack()
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFD32F2F)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Eliminar", color = Color.White)
            }
        }
    }
}




