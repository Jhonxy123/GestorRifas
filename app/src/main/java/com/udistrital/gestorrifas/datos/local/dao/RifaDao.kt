package com.udistrital.gestorrifas.datos.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udistrital.gestorrifas.datos.local.entidad.Rifa
import kotlinx.coroutines.flow.Flow


@Dao
interface RifaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarRifa(rifa: Rifa)

    @Query("SELECT * FROM rifa ORDER BY nombre")
    fun obtenerRifasLive(): LiveData<List<Rifa>>


    @Query("SELECT * FROM Rifa WHERE nombre = :nombre LIMIT 1")
    suspend fun obtenerRifaPorNombre(nombre: String): Rifa?

    @Delete
    suspend fun eliminar(rifa: Rifa)

    // Asegúrate de llamar a la misma tabla ("rifa")
    @Query("SELECT * FROM rifa WHERE nombre LIKE '%' || :query || '%' ORDER BY nombre")
    fun searchRifasByName(query: String): Flow<List<Rifa>>

}

