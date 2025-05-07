package com.udistrital.gestorrifas.datos.local.entidad

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
data class Rifa(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val fecha: LocalDate,
    val boletas: List<Int> = List(100) { 0 }
)
