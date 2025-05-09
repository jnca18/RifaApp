package com.example.rifaapp.repository

import com.example.rifaapp.data.RifaDao
import com.example.rifaapp.data.RifaEntity
import kotlinx.coroutines.flow.Flow

/**
 * Repositorio que actúa como intermediario entre el ViewModel y la capa de datos (DAO).
 *
 * Su responsabilidad principal es abstraer el origen de los datos, centralizar el acceso
 * a la base de datos y delegar operaciones al DAO (Data Access Object).
 *
 * En una arquitectura limpia (como MVVM), esta clase mejora la escalabilidad y testabilidad
 * del código, permitiendo desacoplar el ViewModel del acceso directo a la base de datos.
 *
 * @property dao Instancia de RifaDao para acceder a las funciones de Room.
 */
class RifaRepository(private val dao: RifaDao) {

    /**
     * Obtiene todas las rifas almacenadas en la base de datos.
     *
     * @return Un flujo reactivo con la lista de rifas.
     */
    fun getAllRifas() = dao.getAll()

    /**
     * Realiza una búsqueda de rifas por nombre o fecha usando una cadena de texto.
     *
     * @param query Texto ingresado por el usuario.
     * @return Un flujo con la lista de rifas coincidentes.
     */
    fun searchRifas(query: String) = dao.search(query)

    /**
     * Recupera una rifa específica por su ID como flujo (Flow).
     *
     * @param id Identificador de la rifa.
     * @return Un flujo que emite la rifa si existe, o null si no se encuentra.
     */
    fun getRifa(id: Int): Flow<RifaEntity?> = dao.getById(id)

    /**
     * Inserta una rifa en la base de datos o la actualiza si ya existe.
     *
     * @param rifa Objeto RifaEntity a insertar o reemplazar.
     */
    suspend fun insert(rifa: RifaEntity) = dao.insert(rifa)

    /**
     * Elimina una rifa existente de la base de datos.
     *
     * @param rifa Objeto RifaEntity a eliminar.
     */
    suspend fun delete(rifa: RifaEntity) = dao.delete(rifa)
}


