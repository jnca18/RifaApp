package com.example.rifaapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rifaapp.R

/**
 * Composable reutilizable que representa el encabezado común de la aplicación.
 *
 * Este encabezado se muestra en la parte superior de cada pantalla de la app
 * e incluye el logotipo institucional y el título "RIFAS UD".
 *
 * Está diseñado para mantener coherencia visual y reforzar la identidad
 * del proyecto en todas las vistas.
 */
@Composable
fun HeaderRifasUd() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        // Imagen del logotipo cargado desde los recursos (res/drawable/logo_ud.png)
        Image(
            painter = painterResource(id = R.drawable.logo_ud),
            contentDescription = "Logo Ud",
            modifier = Modifier
                .size(48.dp) // Tamaño del logo
                .padding(end = 8.dp), // Espaciado a la derecha del logo
            contentScale = ContentScale.Fit
        )

        // Título principal con estilo visual institucional
        Text(
            text = "RIFAS UD", // Título visible
            fontSize = 24.sp, // Tamaño de fuente
            fontWeight = FontWeight.Bold, // Texto en negrita
            color = Color(0xFF1565C0) // Azul institucional
        )
    }
}

