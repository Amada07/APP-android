package com.example.inventory.model.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.livedata.observeAsState

import com.example.inventory.model.Product
import com.example.inventory.model.ProductViewModel

@Composable
fun MainScreen(vm: ProductViewModel) {
    val product by vm.product.observeAsState(initial = Product("Cargando...", 0.0F))
    val nameColor by vm.nameColor.observeAsState(initial = 0xFF000000.toInt())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(160.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Nombre: ${product.name}",
            color = androidx.compose.ui.graphics.Color(nameColor)
        )
        Text(text = "Precio: $${product.price}")

        Button(onClick = { vm.changeProduct() }) {
            Text("Cambiar producto")
        }
    }
}
