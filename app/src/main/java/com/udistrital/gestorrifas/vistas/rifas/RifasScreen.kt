import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@Composable
fun RifasScreen(onGuardar: (nombre: String, fecha: LocalDate) -> Unit, Menu: () -> Unit) {
    var nombre by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf<LocalDate?>(null) }

    val context = LocalContext.current
    val today = Calendar.getInstance()
    val datePicker = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, day: Int ->
            fecha = LocalDate.of(year, month + 1, day)
        },
        today.get(Calendar.YEAR),
        today.get(Calendar.MONTH),
        today.get(Calendar.DAY_OF_MONTH)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        // Título
        Text(
            text = "Crear nueva Rifa",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Campo Nombre
        Text(
            text = "Nombre",
            color = MaterialTheme.colorScheme.primary,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            placeholder = { Text("Ingresa el nombre") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors()
        )

        // Campo Fecha de rifa
        Text(
            text = "Fecha de rifa",
            color = MaterialTheme.colorScheme.primary,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
        OutlinedTextField(
            value = fecha?.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) ?: "",
            onValueChange = { },
            placeholder = { Text("Selecciona una fecha") },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { datePicker.show() }
                .padding(bottom = 32.dp),
            singleLine = true,
            enabled = false,
            colors = OutlinedTextFieldDefaults.colors()
        )

        Spacer(modifier = Modifier.weight(1f))

        // Botón Guardar



        Column(
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.fillMaxSize()
        ){
            Button(
                onClick = {
                    fecha?.let { onGuardar(nombre, it) }
                    Menu()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text("Guardar")
            }

            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ){
                Button(onClick = {/*Volver vista a Menu*/Menu()},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)) {
                    Text("Volver")
                }
            }
        }
    }
}

/*
@Preview(showBackground = true)
@Composable
fun PreviewCrearNuevaRifaScreen() {
    CrearNuevaRifaScreen { nombre, fecha ->
        // Acción de prueba al guardar
    }
}*/
