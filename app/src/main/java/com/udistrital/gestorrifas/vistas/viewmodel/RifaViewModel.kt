// Paquete donde se encuentra esta clase ViewModel
package com.udistrital.gestorrifas.vistas.viewmodel

// Importaciones necesarias para ViewModel, Compose, LiveData y corrutinas
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

// ViewModel que maneja la lógica de las rifas y expone datos a la UI
class RifaViewModel(app: Application) : AndroidViewModel(app) {

    // Acceso al DAO desde la base de datos
    private val dao = BaseDeDatos.obtenerInstancia(app).rifaDao()

    // Repositorio que encapsula el DAO
    private val repositorio = RepositorioRifa(dao)

    // 1) MutableLiveData para el texto de búsqueda
    private val _query = MutableLiveData<String>("")         // Valor inicial vacío
    fun setQuery(q: String) = _query.postValue(q)             // Método para actualizar el valor de búsqueda

    // 2) LiveData que cambia automáticamente según _query
    val rifas: LiveData<List<Rifa>> = _query.switchMap { q -> // switchMap permite reaccionar al cambio de _query
        if (q.isBlank()) {
            repositorio.obtenerRifasLive()                    // Si la búsqueda está vacía, se consultan todas
        } else {
            repositorio.searchRifasByName(q).asLiveData()     // Si hay texto, se filtra por nombre
        }
    }

    // Estado local para una rifa individual
    var rifa by mutableStateOf<Rifa?>(null)
        private set                                              // Solo modificable dentro del ViewModel

    // Cargar una rifa específica por nombre
    fun cargarRifa(nombre: String) {
        viewModelScope.launch {
            rifa = repositorio.obtenerRifa(nombre)              // Se asigna la rifa obtenida del repositorio
        }
    }

    // Método para insertar una rifa de prueba con boletas predefinidas
    fun insertarRifaDePrueba() {
        val boletas = List(100) { if (it in listOf(5, 20, 77)) 1 else 0 } // 100 boletas, ocupadas en 3 posiciones
        val rifa = Rifa(
            nombre = "RifaDePrueba",                             // Nombre de la rifa
            fecha = LocalDate.now(),                             // Fecha actual
            boletas = boletas                                    // Lista de boletas
        )

        viewModelScope.launch {
            repositorio.guardarRifa(rifa)                        // Guardar en la base de datos
        }
    }

    // Actualizar el estado de una boleta individual (ocupada o libre)
    fun actualizarBoleta(numero: Int, ocupado: Boolean) {
        rifa?.let { rifaActual ->                                // Verifica que haya una rifa cargada
            val nuevasBoletas = rifaActual.boletas.toMutableList()        // Copia mutable de boletas
            nuevasBoletas[numero] = if (ocupado) 1 else 0                 // Actualiza el estado de la boleta
            val rifaModificada = rifaActual.copy(boletas = nuevasBoletas) // Crea nueva rifa con boletas actualizadas
            viewModelScope.launch {
                repositorio.guardarRifa(rifaModificada)                   // Guarda la rifa modificada
                rifa = rifaModificada                                     // Actualiza el estado local
            }
        }
    }

    // Eliminar una rifa por su nombre
    fun eliminarRifaPorNombre(nombre: String) {
        viewModelScope.launch {
            // Buscar la rifa por nombre
            val rifa = repositorio.obtenerRifa(nombre)
            rifa?.let {
                repositorio.eliminarRifa(it)       // Eliminar la rifa si existe
                this@RifaViewModel.rifa = null     // Limpiar la rifa cargada en memoria
            }
        }
    }



    // Guardar una rifa nueva con nombre y fecha
    fun guardarRifa(nombre: String, fecha: LocalDate) {
        viewModelScope.launch {
            repositorio.guardarRifa(Rifa(nombre = nombre, fecha = fecha)) // Crear y guardar la rifa
            Log.d("RifaViewModel", "Rifa guardada: $nombre / $fecha")     // Mensaje de depuración
        }
    }
}
