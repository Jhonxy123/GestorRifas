
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.livedata.observeAsState
import com.udistrital.gestorrifas.datos.local.entidad.Rifa
import com.udistrital.gestorrifas.vistas.viewmodel.RifaViewModel

@Composable
fun MenuScreen(viewModel: RifaViewModel = viewModel(), NuevaRifa: () -> Unit) {
    var searchText by remember { mutableStateOf("") }

    // Observar rifas desde el ViewModel (LiveData)
    val rifasDb by viewModel.rifas.observeAsState(emptyList())

    // Convertir rifas a UI model + filtrado por texto
    val rifas = remember(searchText, rifasDb) {
        rifasDb.map {
            RifaUI(
                nombre = it.nombre,
                fecha = it.fecha.toString(),
                inscritos = it.boletas.count { b -> b != 0 }
            )
        }.filter {
            it.nombre.contains(searchText, ignoreCase = true) ||
                    it.fecha.contains(searchText)
        }
    }

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
                onValueChange = { searchText = it },
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp),
                placeholder = { Text("Buscar por nombre o fecha") },
                singleLine = true,
                leadingIcon = {
                    Icon(Icons.Filled.Search, contentDescription = null)
                }
            )
            Spacer(Modifier.width(8.dp))
            Button(
                onClick = { /* el filtrado ya es reactivo */ },
                modifier = Modifier.height(56.dp)
            ) {
                Text("Buscar")
            }
        }

        Spacer(Modifier.height(24.dp))

        // Encabezado
        Row(Modifier.fillMaxWidth()) {
            Text(
                "Nombre",
                Modifier.weight(1f),
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
            Text(
                "Inscritos",
                Modifier.weight(1f),
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
            Text(
                "Fecha Rifa",
                Modifier.weight(1f),
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }
        Divider(thickness = 1.dp)

        // Lista
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(top = 8.dp)
        ) {
            items(rifas) { rifa ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Text(rifa.nombre, Modifier.weight(1f), fontSize = 14.sp)
                    Text(rifa.inscritos.toString(), Modifier.weight(1f), fontSize = 14.sp)
                    Text(rifa.fecha, Modifier.weight(1f), fontSize = 14.sp)
                }
                Divider(thickness = 0.5.dp)
            }
        }

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {NuevaRifa()},
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .align(Alignment.CenterHorizontally)
        ) {
            Text("Nueva")
        }
    }
}

// Modelo para mostrar en la UI
data class RifaUI(val nombre: String, val inscritos: Int, val fecha: String)

/*
@Preview(showBackground = true)
@Composable
fun PreviewMenuScreen() {
    // Solo para vista previa sin datos reales
    val ejemplo = listOf(
        RifaUI("Rifa 1", 10, "2025-12-25"),
        RifaUI("Rifa 2", 5, "2025-12-26")
    )
    Column(modifier = Modifier.padding(16.dp)) {
        ejemplo.forEach {
            Text("${it.nombre} - ${it.inscritos} inscritos - ${it.fecha}")
        }
    }
}
*/