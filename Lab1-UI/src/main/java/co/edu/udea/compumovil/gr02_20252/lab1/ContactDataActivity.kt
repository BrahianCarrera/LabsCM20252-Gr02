package co.edu.udea.compumovil.gr02_20252.lab1

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults.TrailingIcon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

val paisesLatinoamerica = listOf(
    "Argentina", "Bolivia", "Brasil", "Chile", "Colombia", "Costa Rica",
    "Cuba", "Ecuador", "El Salvador", "Guatemala", "Honduras", "México",
    "Nicaragua", "Panamá", "Paraguay", "Perú", "Puerto Rico",
    "República Dominicana", "Uruguay", "Venezuela"
)

@Composable
fun TelField() {
    val tel = remember { mutableStateOf("") }
    OutlinedTextField(
        value = tel.value,
        onValueChange = { tel.value = it },
        label = { Text("Teléfono") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
    )
}

@Composable
fun AdressField() {
    val adress = remember { mutableStateOf("") }
    OutlinedTextField(
        value = adress.value,
        onValueChange = { adress.value = it },
        label = { Text("Dirección") },
        keyboardOptions = KeyboardOptions(
            autoCorrect = false,
            imeAction = ImeAction.Next
        )
    )
}

@Composable
fun EmailField() {
    val email = remember { mutableStateOf("") }
    OutlinedTextField(
        value = email.value,
        onValueChange = { email.value = it },
        label = { Text("Email") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Country(paises: List<String>) {
    var expanded by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf("") }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            value = text,
            onValueChange = {
                text = it
                expanded = true
            },
            label = { Text("País") },
            readOnly = false,
            trailingIcon = {
                TrailingIcon(expanded = expanded)
            },
            modifier = Modifier.menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            val filteredPaises = paises.filter {
                it.contains(text, ignoreCase = true)
            }
            if (filteredPaises.isEmpty()) {
                DropdownMenuItem(
                    text = { Text("No se encontraron resultados") },
                    onClick = { expanded = false }
                )
            } else {
                filteredPaises.forEach { pais ->
                    DropdownMenuItem(
                        text = { Text(pais) },
                        onClick = {
                            text = pais
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}
@Composable
fun ContactDataScreen() {
    Scaffold(){ paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                )
         {

            TelField()
            AdressField()
            EmailField()
            Country(paisesLatinoamerica)


        }
    }
}



