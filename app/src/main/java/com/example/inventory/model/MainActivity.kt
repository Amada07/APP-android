package com.example.inventory.model


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.inventory.model.ui.MainScreen
import com.example.inventory.model.ProductViewModel

class MainActivity: ComponentActivity() {
    private val vm: ProductViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface {
                    MainScreen(vm = vm)
                }
            }
        }
    }
}