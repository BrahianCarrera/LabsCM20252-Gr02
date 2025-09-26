package co.edu.udea.compumovil.gr02_20252.lab1

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

val paisesLatinoamerica = listOf(
    "Argentina", "Bolivia", "Brasil", "Chile", "Colombia", "Costa Rica",
    "Cuba", "Ecuador", "El Salvador", "Guatemala", "Honduras", "México",
    "Nicaragua", "Panamá", "Paraguay", "Perú", "Puerto Rico",
    "República Dominicana", "Uruguay", "Venezuela"
)

@Composable
fun TelField() {
    var tel by remember { mutableStateOf("") }
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Icons.Filled.Phone,
            contentDescription = "Teléfono",
            modifier = Modifier.size(36.dp).padding(end = 8.dp)
        )
        OutlinedTextField(
            value = tel,
            onValueChange = { tel = it },
            label = { Text("Teléfono") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = Modifier.fillMaxWidth()
        )
    }
    Spacer(Modifier.height(16.dp))
}

@Composable
fun AddressField() {
    var address by remember { mutableStateOf("") }
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Icons.Filled.Home,
            contentDescription = "Dirección",
            modifier = Modifier.size(36.dp).padding(end = 8.dp)
        )
        OutlinedTextField(
            value = address,
            onValueChange = { address = it },
            label = { Text("Dirección") },
            keyboardOptions = KeyboardOptions(
                autoCorrect = false,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
    Spacer(Modifier.height(16.dp))
}

@Composable
fun EmailField() {
    var email by remember { mutableStateOf("") }
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Icons.Filled.Email,
            contentDescription = "Email",
            modifier = Modifier.size(36.dp).padding(end = 8.dp)
        )
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )
    }
    Spacer(Modifier.height(16.dp))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Country(paises: List<String>) {
    var expanded by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf("") }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Icons.Filled.Public,
            contentDescription = "País",
            modifier = Modifier.size(36.dp).padding(end = 8.dp)
        )
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = text,
                onValueChange = {
                    text = it
                    expanded = true
                },
                label = { Text("País") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor().fillMaxWidth()
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
    Spacer(Modifier.height(16.dp))
}

@Composable
fun ContactDataScreen(navController: NavHostController) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 48.dp)
                .align(Alignment.TopCenter)
        ) {
            TelField()
            AddressField()
            EmailField()
            Country(paisesLatinoamerica)
        }

        // Botón abajo a la derecha
        Button(
            onClick = { navController.navigate("form_screen") },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp)
        ) {
            Text("Atrás")
        }
    }
}
