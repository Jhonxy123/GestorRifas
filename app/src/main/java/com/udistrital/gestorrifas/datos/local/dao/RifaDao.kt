package com.udistrital.gestorrifas.datos.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.udistrital.gestorrifas.datos.local.entidad.Rifa
import kotlinx.coroutines.flow.Flow


@Dao
interface RifaDao {
    @Insert
    suspend fun insertarRifa(rifa: Rifa)

    @Query("SELECT * FROM rifa ORDER BY nombre")
    fun obtenerRifasLive(): LiveData<List<Rifa>>

    // Asegúrate de llamar a la misma tabla ("rifa")
    @Query("SELECT * FROM rifa WHERE nombre LIKE '%' || :query || '%' ORDER BY nombre")
    fun searchRifasByName(query: String): Flow<List<Rifa>>
}

