package com.example.rifaapp.data

import androidx.room.TypeConverter

/**
 * Clase que define convertidores personalizados (TypeConverters) para Room.
 *
 * Room solo permite almacenar tipos de datos primitivos o compatibles con SQLite
 * directamente. Por ejemplo, no permite almacenar listas (List<Int>) de forma directa.
 *
 * Para solucionar esto, usamos @TypeConverter para transformar entre tipos complejos
 * (como listas) y tipos simples (como String), que sí se pueden guardar en la base de datos.
 */
class Converters {

    /**
     * Convierte una lista de enteros (List<Int>) a una cadena de texto (String)
     * separada por comas, para poder almacenarla como un campo de texto en la base de datos.
     * @param list Lista de enteros a convertir.
     * @return Cadena de texto representando los enteros separados por comas.
     */
    @TypeConverter
    fun fromList(list: List<Int>): String = list.joinToString(",")

    /**
     * Convierte una cadena de texto (String) separada por comas en una lista de enteros.

     * Si la cadena está vacía, se retorna una lista vacía.
     *
     * @param data Cadena de texto con los números separados por comas.
     * @return Lista de enteros resultante.
     */
    @TypeConverter
    fun toList(data: String): List<Int> =
        if (data.isEmpty()) emptyList() else data.split(",").map { it.toInt() }
}
