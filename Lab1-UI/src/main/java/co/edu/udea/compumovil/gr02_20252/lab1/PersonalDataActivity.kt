package co.edu.udea.compumovil.gr02_20252.lab1

import android.app.DatePickerDialog
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import java.util.Calendar

@Composable
fun FormScreen(navController: NavHostController) {
    var name by rememberSaveable { mutableStateOf("") }
    var lastName by rememberSaveable { mutableStateOf("") }
    var gender by rememberSaveable { mutableStateOf("Masculino") }
    var fecha by rememberSaveable { mutableStateOf("") }
    var schooling by rememberSaveable { mutableStateOf("Primaria") }
    val context = LocalContext.current

    val isLandscape = LocalConfiguration.current.orientation ==
            android.content.res.Configuration.ORIENTATION_LANDSCAPE

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding() // respeta notch y status bar
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        if (isLandscape) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()) {
                Icon(Icons.Filled.Person, contentDescription = "Persona",  modifier = Modifier.size(36.dp))
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    modifier = Modifier.weight(0.8f),
                    label = { Text("Nombre") },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        imeAction = ImeAction.Next
                    )
                )

                Icon(Icons.Filled.Person, contentDescription = "Persona",  modifier = Modifier.size(36.dp))
                OutlinedTextField(
                    value = lastName,
                    onValueChange = { lastName = it },
                    modifier = Modifier.weight(0.8f),
                    label = { Text("Apellido") },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        imeAction = ImeAction.Next
                    )
                )
            }
        } else {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()){
            Icon(Icons.Filled.Person, contentDescription = "Persona",  modifier = Modifier.size(36.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Nombre") },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    imeAction = ImeAction.Next
                )
            )
            }


            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()) {
                Icon(
                    Icons.Filled.Person,
                    contentDescription = "Persona",
                    modifier = Modifier.size(36.dp)
                )

                OutlinedTextField(
                    value = lastName,
                    onValueChange = { lastName = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Apellido") },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        imeAction = ImeAction.Next
                    )
                )
            }
        }

        Spacer(Modifier.height(12.dp))
        SexSelector(gender) { gender = it }

        Spacer(Modifier.height(12.dp))
        BirthDate(fecha) { fecha = it }

        Spacer(Modifier.height(12.dp))
        SchoolSelector(schooling) { schooling = it }

        Spacer(Modifier.height(24.dp))
        Button(onClick = {
            if (name.isNotEmpty() && lastName.isNotEmpty() && fecha.isNotEmpty()) {
                navController.navigate("contact_data_screen")
            } else {
                Log.e("ValidationError", "Rellena todos los campos")
                Toast.makeText(
                    context,
                    "Por favor completa todos los campos obligatorios",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }) {
            Text("Siguiente")
        }
    }
}

@Composable
fun SexSelector(selected: String, onSelected: (String) -> Unit) {
    val options = listOf("Masculino", "Femenino")
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(Icons.Filled.Person, contentDescription = "Persona",modifier = Modifier.size(36.dp))
        Text("Sexo: ")
        options.forEach { option ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { onSelected(option) }
            ) {
                RadioButton(
                    selected = (selected == option),
                    onClick = { onSelected(option) }
                )
                Text(option)
            }
        }
    }
}

@Composable
fun BirthDate(selectedDate: String, onDateSelected: (String) -> Unit) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context,
        { _, y, m, d -> onDateSelected("$d/${m + 1}/$y") },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(Icons.Filled.DateRange, contentDescription = "Cumpleaños", modifier = Modifier.size(36.dp))
        Text("Nacimiento: $selectedDate")
        Button(onClick = { datePickerDialog.show() }) {
            Text("Cambiar")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SchoolSelector(selected: String, onSelected: (String) -> Unit) {
    val options = listOf("Primaria", "Secundaria", "Bachillerato", "Universidad")
    var expanded by remember { mutableStateOf(false) }

    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)){  Icon(Icons.Filled.School, contentDescription = "Educacion", modifier = Modifier.size(36.dp))

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = selected,
                onValueChange = {},
                readOnly = true,
                label = { Text("Escolaridad") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },

            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            onSelected(option)
                            expanded = false
                        }
                    )
                }
            }
        }}


}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun FormScreenPreview() {
    FormScreen(navController = rememberNavController())
}
