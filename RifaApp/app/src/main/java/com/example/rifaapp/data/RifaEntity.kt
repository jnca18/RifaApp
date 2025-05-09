package com.example.rifaapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rifas")
data class RifaEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val fecha: String,
    val boletosOcupados: List<Int> = emptyList(),
    val boletoGanador: Int? = null
)
