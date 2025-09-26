package co.edu.udea.compumovil.gr02_20252.lab1

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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
fun TelField(
    value: String,
    onValueChange: (String) -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Icons.Filled.Phone,
            contentDescription = "Teléfono",
            modifier = Modifier.size(36.dp).padding(end = 8.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text("Teléfono") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = Modifier.fillMaxWidth()
        )
    }
    Spacer(Modifier.height(16.dp))
}

@Composable
fun AddressField(
    value: String,
    onValueChange: (String) -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Icons.Filled.Home,
            contentDescription = "Dirección",
            modifier = Modifier.size(36.dp).padding(end = 8.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
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
fun EmailField(
    value: String,
    onValueChange: (String) -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Icons.Filled.Email,
            contentDescription = "Email",
            modifier = Modifier.size(36.dp).padding(end = 8.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )
    }
    Spacer(Modifier.height(16.dp))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Country(
    paises: List<String>,
    value: String,
    onValueChange: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

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
                value = value,
                onValueChange = {
                    onValueChange(it)
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
                    it.contains(value, ignoreCase = true)
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
                                onValueChange(pais)
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
    var telefono by rememberSaveable { mutableStateOf("") }
    var direccion by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var pais by rememberSaveable { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 48.dp)
                .align(Alignment.TopCenter)
        ) {
            TelField(
                value = telefono,
                onValueChange = { telefono = it }
            )
            AddressField(
                value = direccion,
                onValueChange = { direccion = it }
            )
            EmailField(
                value = email,
                onValueChange = { email = it }
            )
            Country(
                paises = paisesLatinoamerica,
                value = pais,
                onValueChange = { pais = it }
            )
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
