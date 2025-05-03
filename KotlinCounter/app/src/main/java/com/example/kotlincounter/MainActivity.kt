package com.example.kotlincounter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kotlincounter.ui.theme.KotlinCounterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KotlinCounterTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    KotlinCounterApp()
                }
            }
        }
    }
}

@Composable
fun KotlinCounterApp() {
    val context = LocalContext.current
    val dataStore = remember { DataStoreManager(context) }

    val clickCountFlow by dataStore.clickCountFlow.collectAsState(initial = 0)
    var clickCount by remember { mutableStateOf(clickCountFlow) }

    // Salva o valor sempre que mudar
    LaunchedEffect(clickCount) {
        dataStore.saveClickCount(clickCount)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "You clicked $clickCount times", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = {
            clickCount++
        }) {
            Icon(Icons.Default.TouchApp, contentDescription = "Click")
            Spacer(modifier = Modifier.width(8.dp))
            Text("Click me")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            clickCount = 0
        },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red,       // Cor de fundo
                contentColor = Color.White         // Cor do texto/ícone
            )) {
            Spacer(modifier = Modifier.width(8.dp))
            Text("Reset")
        }
    }
}
