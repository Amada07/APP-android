package com.example.inventory.model
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.inventory.model.Product
import kotlin.random.Random

class ProductViewModel: ViewModel() {


        private val _product = MutableLiveData(Product("Audifonos", 21.00F))
        val product: LiveData<Product> get() = _product

        private val colors = listOf(
                0xFF1E88E5.toInt(), // Azul
                0xFFD32F2F.toInt(), // Rojo
                0xFF388E3C.toInt(), // Verde
                0xFFFBC02D.toInt()  // Amarillo
        )

        private val _nameColor = MutableLiveData(colors.first())
        val nameColor: LiveData< Int > get() = _nameColor

        fun changeProduct() {
                val products = listOf(
                        Product("Teclado", 45.50F),
                        Product("Mouse Gamer", 15.00F),
                        Product("Monitor", 199.99F),
                        Product("Laptop", 899.00F)
                )
                _product.value = products.random()
                _nameColor.value = colors.random()
        }
}