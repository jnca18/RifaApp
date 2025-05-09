package com.example.rifaapp.repository

import com.example.rifaapp.data.RifaDao
import com.example.rifaapp.data.RifaEntity
import kotlinx.coroutines.flow.Flow

class RifaRepository(private val dao: RifaDao) {
    fun getAllRifas() = dao.getAll()
    fun searchRifas(query: String) = dao.search(query)
    fun getRifa(id: Int): Flow<RifaEntity?> = dao.getById(id)
    suspend fun insert(rifa: RifaEntity) = dao.insert(rifa)
    suspend fun delete(rifa: RifaEntity) = dao.delete(rifa)
}

