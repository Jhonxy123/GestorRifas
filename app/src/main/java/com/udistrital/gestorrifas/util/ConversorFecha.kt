package com.udistrital.gestorrifas.util

import androidx.room.TypeConverter
import java.time.LocalDate

class ConversorFecha {
    @TypeConverter
    fun desdeFecha(fecha: LocalDate): String = fecha.toString()

    @TypeConverter
    fun aFecha(fechaCadena: String): LocalDate = LocalDate.parse(fechaCadena)
}
