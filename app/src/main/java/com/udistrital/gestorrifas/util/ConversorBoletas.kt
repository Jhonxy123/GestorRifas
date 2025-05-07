package com.udistrital.gestorrifas.util

import androidx.room.TypeConverter

class ConversorBoletas {
    @TypeConverter
    fun deListaEnterosAString(lista: List<Int>): String {
        return lista.joinToString(separator = ",")
    }

    @TypeConverter
    fun deStringAListaEnteros(valor: String): List<Int> {
        return if (valor.isBlank()) List(100) { 0 }
        else valor.split(",").map { it.toInt() }
    }
}
