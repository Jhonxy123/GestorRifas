package com.udistrital.gestorrifas.vistas.viewmodel


import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class RifaViewModelFactory(private val app: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RifaViewModel::class.java)) {
            return RifaViewModel(app) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
