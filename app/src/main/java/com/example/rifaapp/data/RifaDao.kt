package com.example.rifaapp.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * Interfaz DAO (Data Access Object) para acceder y manipular los datos de la tabla "rifas".
 *
 * Room genera automáticamente la implementación de esta interfaz en tiempo de compilación.
 *
 * Cada método anotado corresponde a una consulta (SQL) que se puede ejecutar en la base de datos.
 */
@Dao
interface RifaDao {

    /**
     * Obtiene todas las rifas almacenadas en la base de datos.
     *
     * @return Un flujo (Flow) que emite la lista de rifas y se actualiza en tiempo real.
     */
    @Query("SELECT * FROM rifas")
    fun getAll(): Flow<List<RifaEntity>>

    /**
     * Obtiene una rifa específica por su ID.
     *
     * @param id Identificador único de la rifa.
     * @return Un flujo que emite la rifa encontrada o null si no existe.
     */
    @Query("SELECT * FROM rifas WHERE id = :id")
    fun getById(id: Int): Flow<RifaEntity?>

    /**
     * Inserta una nueva rifa en la base de datos o reemplaza la existente si ya tiene el mismo ID.
     * @param rifa La rifa a insertar o actualizar.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(rifa: RifaEntity)

    /**
     * Elimina una rifa específica de la base de datos.
     * @param rifa La rifa que se desea eliminar.
     */
    @Delete
    suspend fun delete(rifa: RifaEntity)

    /**
     * Busca rifas que contengan el texto ingresado en el campo nombre o fecha.
     * Se realiza una búsqueda parcial utilizando el operador LIKE.
     * @param query Texto a buscar dentro del nombre o fecha de la rifa.
     * @return Un flujo que emite la lista de rifas coincidentes.
     */
    @Query("SELECT * FROM rifas WHERE nombre LIKE '%' || :query || '%' OR fecha LIKE '%' || :query || '%'")
    fun search(query: String): Flow<List<RifaEntity>>
}

