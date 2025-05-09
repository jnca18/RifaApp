package com.example.rifaapp.ui

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rifaapp.viewmodel.RifaViewModel
import com.example.rifaapp.data.RifaEntity
import androidx.navigation.NavController
import java.util.*

/**
 * Composable que representa la pantalla para crear una nueva rifa.
 *
 * Permite al usuario ingresar el nombre y la fecha de la rifa, y luego guardarla
 * en la base de datos local usando Room. La pantalla incluye un diseño estilizado
 * con un encabezado personalizado y validación básica de campos.
 *
 * @param viewModel ViewModel que maneja la lógica de inserción de rifas.
 * @param navController Controlador de navegación para regresar a la pantalla anterior.
 */
@Composable
fun RifaCreateScreen(viewModel: RifaViewModel, navController: NavController) {
    // Estado del campo de nombre
    var nombre by remember { mutableStateOf("") }

    // Estado del campo de fecha
    var fecha by remember { mutableStateOf("") }

    // Contexto necesario para el DatePickerDialog
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    // Diálogo para seleccionar la fecha de la rifa
    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, day: Int ->
            fecha = "$day/${month + 1}/$year"
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE3F2FD)) // fondo azul claro
            .padding(16.dp)
    ) {
        // Encabezado con logo y título
        HeaderRifasUd()

        // Campo de texto para el nombre de la rifa
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre de la rifa") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF1565C0),
                cursorColor = Color(0xFF1565C0)
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Campo de texto no editable para seleccionar la fecha
        OutlinedTextField(
            value = fecha,
            onValueChange = {},
            label = { Text("Fecha de rifa") },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { datePickerDialog.show() }, // muestra el selector de fecha
            enabled = false,
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Botón para guardar la rifa si ambos campos no están vacíos
        Button(
            onClick = {
                if (nombre.isNotEmpty() && fecha.isNotEmpty()) {
                    viewModel.insert(RifaEntity(nombre = nombre, fecha = fecha))
                    navController.popBackStack() // vuelve a la pantalla anterior
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF0D47A1)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Guardar Rifa", color = Color.White)
        }
    }
}


