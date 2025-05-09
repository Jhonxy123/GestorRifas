package com.udistrital.gestorrifas.repositorio

import androidx.lifecycle.LiveData
import com.udistrital.gestorrifas.datos.local.dao.RifaDao
import com.udistrital.gestorrifas.datos.local.entidad.Rifa
import kotlinx.coroutines.flow.Flow

class RepositorioRifa(private val rifaDao: RifaDao) {
    suspend fun guardarRifa(rifa: Rifa) {
        rifaDao.insertarRifa(rifa)
    }

    /*suspend fun obtenerRifas(): LiveData<List<Rifa>> {
        return rifaDao.obtenerRifasLive()
    }*/


    fun obtenerRifasLive(): LiveData<List<Rifa>> = rifaDao.obtenerRifasLive()

    suspend fun obtenerRifa(nombre: String): Rifa? {
        return rifaDao.obtenerRifaPorNombre(nombre)
    }
    suspend fun eliminarRifa(rifa: Rifa) {
        rifaDao.eliminar(rifa)
    }

    // Exponemos el Flow de búsqueda
    fun searchRifasByName(query: String): Flow<List<Rifa>> =
        rifaDao.searchRifasByName(query)
}
