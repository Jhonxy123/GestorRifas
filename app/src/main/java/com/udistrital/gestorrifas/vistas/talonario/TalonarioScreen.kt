package com.udistrital.gestorrifas.vistas.rifas

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.udistrital.gestorrifas.vistas.viewmodel.RifaViewModel

@Composable
fun TalonarioScreen(
    rifaName: String,
    viewModel: RifaViewModel = viewModel(),
    Menu: () -> Unit
) {
    var winningTicket by remember { mutableStateOf("") }
    var disabled by remember { mutableStateOf(false) }

    val rifa = viewModel.rifa

    // Carga inicial de la rifa
    LaunchedEffect(rifaName) {
        viewModel.cargarRifa(rifaName)
    }

    if (rifa == null) {
        Text("Cargando rifa...")
        return
    }

    // Se actualiza automáticamente cuando cambia viewModel.rifa
    val unavailableNumbers = rifa.boletas.mapIndexedNotNull { index, value ->
        if (value != 0) index else null
    }.toSet()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = rifaName,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Medium
        )

        Spacer(Modifier.height(16.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(10),
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
        ) {
            items((0 until 100).toList()) { number ->
                val isUnavailable = unavailableNumbers.contains(number)
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .aspectRatio(1f)
                        .padding(2.dp)
                        .then(
                            if (isUnavailable) {
                                Modifier
                                    .clip(CircleShape)
                                    .background(Color(0xFFFFAEC9))
                            } else {
                                Modifier
                                    .border(1.dp, Color.Gray, CircleShape)
                            }
                        )
                ) {
                    Text(
                        text = "%02d".format(number),
                        fontSize = 12.sp,
                        color = if (isUnavailable) Color.White else Color.Black
                    )
                }
            }
        }

        Spacer(Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFFFAEC9))
            )
            Spacer(Modifier.width(8.dp))
            Text("Números no disponibles", fontSize = 12.sp)
        }

        Spacer(Modifier.height(24.dp))

        OutlinedTextField(
            value = winningTicket,
            onValueChange = { winningTicket = it },
            label = { Text("Boleto ganador") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        // Switch Inhabilitar
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Habilitar", modifier = Modifier.weight(1f))
            Switch(
                checked = disabled,
                onCheckedChange = { disabled = it }
            )
        }

        Spacer(Modifier.height(24.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = {
                    val numero = winningTicket.toIntOrNull()
                    if (numero != null && numero in 0..99) {
                        viewModel.actualizarBoleta(numero, ocupado = !disabled)
                        winningTicket = ""
                    }
                }
            ) {
                Icon(Icons.Default.Done, contentDescription = "Guardar")
                Spacer(Modifier.width(4.dp))
                Text("Guardar")
            }

            Button(onClick = {
                viewModel.eliminarRifaPorNombre(rifaName)  // Elimina por el nombre de la rifa
                Menu()
            }) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                Spacer(Modifier.width(4.dp))
                Text("Eliminar")
            }
        }
    }
}
