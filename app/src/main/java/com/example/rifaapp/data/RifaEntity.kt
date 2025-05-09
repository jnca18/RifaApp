package com.example.rifaapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Clase de datos que representa la entidad "Rifa" en la base de datos local.
 *
 * Esta clase se convierte en una tabla de SQLite gracias a la anotación @Entity,
 * y Room genera automáticamente el esquema y las operaciones necesarias.
 *
 * @Entity(tableName = "rifas")
 * Define que esta clase representa una tabla llamada "rifas".
 */
@Entity(tableName = "rifas")
data class RifaEntity(

    /**
     * Identificador único de la rifa.
     * Se genera automáticamente por Room.
     */
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    /**
     * Nombre de la rifa.
     * Este valor es definido por el usuario al crear la rifa.
     */
    val nombre: String,

    /**
     * Fecha en la que se realizará la rifa.
     * Representada como texto, por ejemplo: "10/05/2025"
     */
    val fecha: String,

    /**
     * Lista de boletos ocupados.
     * Cada número representa un boleto seleccionado por algún participante.
     *
     * Como Room no soporta List<Int> directamente, se usa un TypeConverter.
     */
    val boletosOcupados: List<Int> = emptyList(),

    /**
     * Número del boleto ganador.
     * Puede ser nulo si aún no se ha seleccionado el ganador.
     */
    val boletoGanador: Int? = null
)


