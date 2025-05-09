package com.udistrital.gestorrifas.vistas.viewmodel

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
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

    var rifa by mutableStateOf<Rifa?>(null)
        private set

    fun cargarRifa(nombre: String) {
        viewModelScope.launch {
            rifa = repositorio.obtenerRifa(nombre)
        }
    }
    fun insertarRifaDePrueba() {
        val boletas = List(100) { if (it in listOf(5, 20, 77)) 1 else 0 }
        val rifa = Rifa(
            nombre = "RifaDePrueba",
            fecha = LocalDate.now(),
            boletas = boletas
        )

        viewModelScope.launch {
            repositorio.guardarRifa(rifa)
        }
    }

    fun actualizarBoleta(numero: Int, ocupado: Boolean) {
        rifa?.let { rifaActual ->
            val nuevasBoletas = rifaActual.boletas.toMutableList()
            nuevasBoletas[numero] = if (ocupado) 1 else 0
            val rifaModificada = rifaActual.copy(boletas = nuevasBoletas)
            viewModelScope.launch {
                repositorio.guardarRifa(rifaModificada)
                rifa = rifaModificada // actualiza la referencia local también
            }
        }
    }
    fun eliminarRifaPorNombre(nombre: String) {
        viewModelScope.launch {
            // Buscar la rifa por nombre
            val rifa = repositorio.obtenerRifa(nombre)
            rifa?.let {
                repositorio.eliminarRifa(it) // Eliminar rifa por nombre
                // Limpiar la referencia local
                this@RifaViewModel.rifa = null
            }
        }
    }

    /*
    fun consultarTodasLasRifas() {
        viewModelScope.launch {
            val lista = repositorio.obtenerRifas()
            Log.d("ConsultaRifas", "📦 Rifas en la BD:")
            lista.forEach {
                Log.d("ConsultaRifas", "🔹 \${it.nombre} - \${it.fecha}")
            }
        }
    }*/

    fun guardarRifa(nombre: String, fecha: LocalDate) {
        viewModelScope.launch {
            repositorio.guardarRifa(Rifa(nombre = nombre, fecha = fecha))
            Log.d("RifaViewModel", "Rifa guardada: $nombre / $fecha")
        }
    }
}
