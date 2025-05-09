package com.udistrital.gestorrifas.datos.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udistrital.gestorrifas.datos.local.entidad.Rifa

@Dao
interface RifaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarRifa(rifa: Rifa)

    @Query("SELECT * FROM rifa")
    fun obtenerRifasLive(): LiveData<List<Rifa>>

    @Query("SELECT * FROM Rifa WHERE nombre = :nombre LIMIT 1")
    suspend fun obtenerRifaPorNombre(nombre: String): Rifa?

    @Delete
    suspend fun eliminar(rifa: Rifa)

}
