package com.example.rifaapp.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface RifaDao {
    @Query("SELECT * FROM rifas")
    fun getAll(): Flow<List<RifaEntity>>

    @Query("SELECT * FROM rifas WHERE id = :id")
    fun getById(id: Int): Flow<RifaEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(rifa: RifaEntity)

    @Delete
    suspend fun delete(rifa: RifaEntity)

    @Query("SELECT * FROM rifas WHERE nombre LIKE '%' || :query || '%' OR fecha LIKE '%' || :query || '%'")
    fun search(query: String): Flow<List<RifaEntity>>
}
