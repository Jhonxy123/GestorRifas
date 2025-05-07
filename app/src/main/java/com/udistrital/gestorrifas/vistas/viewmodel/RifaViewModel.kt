package com.udistrital.gestorrifas.vistas.viewmodel

import android.app.Application
import android.util.Log
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
