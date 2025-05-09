package com.udistrital.gestorrifas.vistas.menu

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.livedata.observeAsState
import com.udistrital.gestorrifas.datos.local.entidad.Rifa
import com.udistrital.gestorrifas.vistas.viewmodel.RifaViewModel

@Composable
fun MenuScreen(
    viewModel: RifaViewModel = viewModel(),
    NuevaRifa: () -> Unit,
    Talonario: (String) -> Unit
) {
    var searchText by remember { mutableStateOf("") }
    // 1) Observamos la lista filtrada que expone el ViewModel
    val rifasEnt by viewModel.rifas.observeAsState(emptyList())

    // 2) Mapeamos a nuestro modelo de UI **fuera** de LazyColumn
    val rifasUI: List<RifaUI> = rifasEnt.map {
        RifaUI(
            nombre = it.nombre,
            inscritos = it.boletas.count { b -> b != 0 },
            fecha = it.fecha.toString()
        )
    }

    Scaffold{innerPadding ->}
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Rifas",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(Modifier.height(16.dp))

        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = searchText,
                onValueChange = {
                    searchText = it
                    viewModel.setQuery(it.trim())    // actualiza filtro
                },
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp),
                placeholder = { Text("Buscar por nombre o fecha") },
                singleLine = true,
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) }
            )
            Spacer(Modifier.width(8.dp))
            Button(
                onClick = { /* no hace falta: ya es reactivo */ },
                modifier = Modifier.height(56.dp)
            ) {
                Text("Buscar")
            }
        }

        Spacer(Modifier.height(24.dp))

        // Encabezado
        Row(Modifier.fillMaxWidth()) {
            Text("Nombre", Modifier.weight(1f), fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Text("Inscritos", Modifier.weight(1f), fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Text("Fecha Rifa", Modifier.weight(1f), fontWeight = FontWeight.Bold, fontSize = 14.sp)
        }
        Divider(thickness = 1.dp)

        // 3) LazyColumn con tipo explícito
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(top = 8.dp)
        ) {
            // filtramos por searchText aquí, o si prefieres en la query de BD
            val mostradas = rifasUI.filter {
                it.nombre.contains(searchText, ignoreCase = true) ||
                        it.fecha.contains(searchText)
            }
            items(items = mostradas) { rifa: RifaUI ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Text(rifa.nombre, Modifier.weight(1f), fontSize = 14.sp)
                    Text(rifa.inscritos.toString(), Modifier.weight(1f), fontSize = 14.sp)
                    Text(rifa.fecha, Modifier.weight(1f), fontSize = 14.sp)
                    IconButton(
                        onClick = { Talonario(rifa.nombre) },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(Icons.Filled.Search, contentDescription = "Ver detalles")
                    }
                }
                Divider(thickness = 0.5.dp)
            }
        }

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = { NuevaRifa() },

            modifier = Modifier
                .fillMaxWidth(0.5f)
                .align(Alignment.CenterHorizontally)
        ) {
            Text("Nueva")
        }
    }
}

// Modelo de UI
data class RifaUI(
    val nombre: String,
    val inscritos: Int,
    val fecha: String
)
