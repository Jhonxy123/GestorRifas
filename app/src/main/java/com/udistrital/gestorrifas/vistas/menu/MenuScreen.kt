import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MenuScreen() {
    var searchText by remember { mutableStateOf("") }

    // Ejemplo de datos; en producción vendrían de ViewModel/Repositorio
    val allRifas = listOf(
        Rifa("Rifa 1", 21, "25-12-2025"),
        Rifa("Rifa 2", 65, "25-12-2025"),
        Rifa("Rifa 3", 21, "25-12-2025"),
        Rifa("Rifa 4", 21, "25-12-2025"),
    )

    // Filtrado simple por nombre o fecha
    val rifas = remember(searchText) {
        allRifas.filter {
            it.nombre.contains(searchText, ignoreCase = true)
                    || it.fecha.contains(searchText)
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
                placeholder = { Text("Search by name or date") },
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
                Text("Search")
            }
        }

        Spacer(Modifier.height(24.dp))

        // Encabezado de la tabla (fijo)
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

        // Lista desplazable
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
            onClick = { /* navegar a creación de nueva rifa */ },
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .align(Alignment.CenterHorizontally)
        ) {
            Text("Nueva")
        }
    }
}

data class Rifa(val nombre: String, val inscritos: Int, val fecha: String)


@Preview(showBackground = true)
@Composable
fun PreviewRifasScreen() {
    MenuScreen()
}