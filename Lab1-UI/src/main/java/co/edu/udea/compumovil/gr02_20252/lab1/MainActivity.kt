package co.edu.udea.compumovil.gr02_20252.lab1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import co.edu.udea.compumovil.gr02_20252.lab1.ui.theme.Labs2025SGr02Theme
import co.edu.udea.compumovil.gr02_20252.lab1.FormScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Labs2025SGr02Theme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "form_screen") {
                    composable("form_screen") {
                        FormScreen(navController)
                    }
                    composable("contact_data_screen") {
                        ContactDataScreen(navController)
                    }
                }

            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Labs2025SGr02Theme {
        Greeting("Android")
    }
}