package com.example.rifaapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rifaapp.data.RifaEntity
import com.example.rifaapp.repository.RifaRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class RifaViewModel(private val repository: RifaRepository) : ViewModel() {
    val rifas = repository.getAllRifas().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    val searchResults = MutableStateFlow<List<RifaEntity>>(emptyList())

    fun search(query: String) {
        repository.searchRifas(query).onEach {
            searchResults.value = it
        }.launchIn(viewModelScope)
    }

    fun insert(rifa: RifaEntity) = viewModelScope.launch {
        repository.insert(rifa)
    }

    fun delete(rifa: RifaEntity) = viewModelScope.launch {
        repository.delete(rifa)
    }

    fun getRifaById(id: Int): Flow<RifaEntity?> = repository.getRifa(id)
}
