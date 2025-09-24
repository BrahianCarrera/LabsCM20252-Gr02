package co.edu.udea.compumovil.gr02_20252.lab1

import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import java.util.Calendar
import androidx.compose.runtime.saveable.rememberSaveable

@Composable
fun FormScreen(navController: NavHostController) {
    var name by
    rememberSaveable { mutableStateOf("") }
    var lastName by rememberSaveable { mutableStateOf("") }
    var gender  by rememberSaveable { mutableStateOf("Masculino") }
    var fecha by rememberSaveable { mutableStateOf("") }
    var schooling by rememberSaveable { mutableStateOf("Primaria") }

    Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 32.dp ).verticalScroll(rememberScrollState())){
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words,
                autoCorrectEnabled = false,
                keyboardType = KeyboardType.Unspecified, imeAction = ImeAction.Next
            ),
            label = { Text("Nombre") }
        )

        OutlinedTextField(
            value = lastName,
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words,
                autoCorrectEnabled = false,
                keyboardType = KeyboardType.Unspecified, imeAction = ImeAction.Next
            ),
            onValueChange = { lastName = it },
            label = { Text("Apellido") }
        )

        Spacer(Modifier.height(12.dp))
        SexSelector(gender) { gender = it }

        Spacer(Modifier.height(12.dp))
        birthDate (fecha) { fecha = it }

        Spacer(Modifier.height(12.dp))
        SchoolSelector(schooling) { schooling = it }

        Button(onClick = {
            navController.navigate("contact_data_screen")
        }) {
            Text("Siguiente")
        }
    }
}
@Composable
fun SexSelector(selected: String, onSelected: (String) -> Unit) {
    val options = listOf("Masculino", "Femenino")

    Column {
        Text("Sexo")
        options.forEach { option ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(4.dp)
            ) {
                RadioButton(
                    selected = (selected == option),
                    onClick = { onSelected(option) }
                )
                Text(option, modifier = Modifier.clickable { onSelected(option) })
            }
        }
    }
}

@Composable
fun birthDate(selectedDate: String, onDateSelected: (String) -> Unit) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        context,
        { _, y, m, d -> onDateSelected("$d/${m + 1}/$y") },
        year, month, day
    )

    Column {
        Text("Fecha de nacimiento: $selectedDate")
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

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selected,
            onValueChange = {},
            readOnly = true,
            label = { Text("Escolaridad") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor()
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
    }
}

