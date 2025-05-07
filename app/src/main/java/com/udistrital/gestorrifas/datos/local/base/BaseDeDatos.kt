package com.udistrital.gestorrifas.datos.local.base

import android.content.Context
import androidx.room.*
import com.udistrital.gestorrifas.datos.local.dao.RifaDao
import com.udistrital.gestorrifas.datos.local.entidad.Rifa
import com.udistrital.gestorrifas.util.ConversorBoletas
import com.udistrital.gestorrifas.util.ConversorFecha

@Database(entities = [Rifa::class], version = 1)
@TypeConverters(ConversorFecha::class, ConversorBoletas::class)
abstract class BaseDeDatos : RoomDatabase() {
    abstract fun rifaDao(): RifaDao

    companion object {
        @Volatile
        private var INSTANCIA: BaseDeDatos? = null

        fun obtenerInstancia(contexto: Context): BaseDeDatos {
            return INSTANCIA ?: synchronized(this) {
                val instancia = Room.databaseBuilder(
                    contexto.applicationContext,
                    BaseDeDatos::class.java,
                    "rifas_bd"
                ).build()
                INSTANCIA = instancia
                instancia
            }
        }
    }
}
