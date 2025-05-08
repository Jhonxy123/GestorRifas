package com.udistrital.gestorrifas.vistas.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.udistrital.gestorrifas.datos.local.base.BaseDeDatos
import com.udistrital.gestorrifas.datos.local.entidad.Rifa
import com.udistrital.gestorrifas.repositorio.RepositorioRifa
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import java.time.LocalDate

class RifaViewModel(app: Application) : AndroidViewModel(app) {

    private val dao = BaseDeDatos.obtenerInstancia(app).rifaDao()
    private val repositorio = RepositorioRifa(dao)

    // 1) MutableLiveData para el texto de búsqueda
    private val _query = MutableLiveData<String>("")
    fun setQuery(q: String) = _query.postValue(q)

    // 2) LiveData que cambia según _query
    val rifas: LiveData<List<Rifa>> = _query.switchMap { q ->
        if (q.isBlank()) {
            repositorio.obtenerRifasLive()
        } else {
            repositorio.searchRifasByName(q).asLiveData()
        }
    }

    fun guardarRifa(nombre: String, fecha: LocalDate) {
        viewModelScope.launch {
            repositorio.guardarRifa(Rifa(nombre = nombre, fecha = fecha))
            Log.d("RifaViewModel", "Rifa guardada: $nombre / $fecha")
        }
    }
}
