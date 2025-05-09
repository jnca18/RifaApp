package com.example.rifaapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rifaapp.data.RifaEntity
import com.example.rifaapp.repository.RifaRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * ViewModel que actúa como intermediario entre la interfaz de usuario (UI)
 * y la lógica de datos proporcionada por el repositorio.
 *
 * Se encarga de exponer datos observables a la UI mediante Flows,
 * y ejecutar operaciones como insertar, eliminar o buscar rifas.
 *
 * El uso de viewModelScope asegura que las operaciones suspendidas
 * se ejecuten dentro del ciclo de vida adecuado.
 *
 * @param repository Instancia del repositorio que contiene el acceso a la base de datos.
 */
class RifaViewModel(private val repository: RifaRepository) : ViewModel() {

    /**
     * Flujo que contiene todas las rifas almacenadas en la base de datos.
     * Se convierte en un StateFlow para que pueda observarse desde la UI de forma reactiva.
     */
    val rifas = repository.getAllRifas()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    /**
     * Flujo mutable que contiene los resultados filtrados de búsqueda de rifas.
     * Se actualiza dinámicamente cada vez que se realiza una búsqueda.
     */
    val searchResults = MutableStateFlow<List<RifaEntity>>(emptyList())

    /**
     * Realiza una búsqueda en la base de datos por nombre o fecha de rifa.
     * Actualiza el estado de `searchResults` de forma reactiva.
     *
     * @param query Texto de búsqueda ingresado por el usuario.
     */
    fun search(query: String) {
        repository.searchRifas(query)
            .onEach { searchResults.value = it }
            .launchIn(viewModelScope)
    }

    /**
     * Inserta una nueva rifa o actualiza una existente.
     *
     * @param rifa Objeto RifaEntity que se va a guardar en la base de datos.
     */
    fun insert(rifa: RifaEntity) = viewModelScope.launch {
        repository.insert(rifa)
    }

    /**
     * Elimina una rifa de la base de datos.
     *
     * @param rifa Rifa que se desea eliminar.
     */
    fun delete(rifa: RifaEntity) = viewModelScope.launch {
        repository.delete(rifa)
    }

    /**
     * Obtiene una rifa específica por su ID como un flujo observable.
     *
     * @param id Identificador único de la rifa.
     * @return Flujo que emite la rifa correspondiente, o null si no existe.
     */
    fun getRifaById(id: Int): Flow<RifaEntity?> = repository.getRifa(id)
}

