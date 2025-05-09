package com.udistrital.gestorrifas.vistas.viewmodel

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.udistrital.gestorrifas.datos.local.base.BaseDeDatos
import com.udistrital.gestorrifas.datos.local.entidad.Rifa
import com.udistrital.gestorrifas.repositorio.RepositorioRifa
import kotlinx.coroutines.launch
import java.time.LocalDate


class RifaViewModel(aplicacion: Application) : AndroidViewModel(aplicacion) {


    private val rifaDao = BaseDeDatos.obtenerInstancia(aplicacion).rifaDao()
    private val repositorio = RepositorioRifa(rifaDao)
    val rifas: LiveData<List<Rifa>> = repositorio.obtenerRifasLive()
    fun guardarRifa(nombre: String, fecha: LocalDate) {
        val rifa = Rifa(nombre = nombre, fecha = fecha)
        viewModelScope.launch {
            val rifa = Rifa(nombre = nombre, fecha = fecha)
            repositorio.guardarRifa(rifa)
            Log.d("RifaViewModel", "Rifa guardada: \$rifa")
         //   consultarTodasLasRifas()
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

}
