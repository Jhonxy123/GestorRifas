package com.udistrital.gestorrifas

import org.junit.Test
import org.junit.Assert.*

class ExampleUnitTest {

    // Función simulada que cuenta cuántas boletas están ocupadas (valen 1)
    fun contarBoletasOcupadas(boletas: List<Int>): Int {
        return boletas.count { it == 1 }
    }

    @Test
    fun testContarBoletasOcupadas() {
        // Lista de prueba con 3 boletas ocupadas
        val boletas = listOf(0, 1, 0, 1, 0, 1)

        // Esperamos que el conteo sea 3
        val resultado = contarBoletasOcupadas(boletas)

        // Afirmamos que el resultado es correcto
        assertEquals(3, resultado)
    }
}
