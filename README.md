Objetivo
Construir una aplicación Android con Jetpack Compose aplicando el patrón de arquitectura MVVM (Model–View–ViewModel).
La aplicación debe:
•	Mostrar un producto con nombre y precio.
•	Cambiar el producto al presionar un botón.
•	Observar el estado desde un ViewModel usando LiveData.
•	Demostrar el uso de recomposición en Compose.

Tecnologías utilizadas
•	Lenguaje: Kotlin
•	UI toolkit: Jetpack Compose
•	Arquitectura: MVVM
•	Observabilidad: LiveData
•	IDE: Android Studio Ladybug | 2024.2.1
•	SDK: minSdk 24, targetSdk 34

 Estructura del Proyecto
Cuando Android Studio crea el proyecto, genera varias carpetas clave:
Inventory/
│
├── manifests/
│   └── AndroidManifest.xml   → Configuración de la app (nombre, permisos, actividades).
│
├── java/
│   └── com.example.inventory/
│       ├── MainActivity.kt   → Punto de entrada principal de la app.
│       ├── model/            → Clases de datos (Model).
│       ├── vm/               → ViewModels (lógica de estado).
│       └── ui/               → Composables y pantallas (View).
│
├── res/
│   ├── drawable/             → Iconos, imágenes.
│   ├── layout/               → (No usado en Compose).
│   ├── mipmap/               → Iconos del launcher.
│   └── values/               → colores.xml, strings.xml, themes.xml.
│
└── build.gradle (Module)     → Configuración de dependencias.

Descubrimiento: conceptos clave
¿Qué es Jetpack Compose?
	-Kit moderno de UI declarativa para Android.
	-Reemplaza a los layouts XML tradicionales.
	-La UI se describe en funciones con la anotación @Composable.
	-Beneficio: menos código, UI reactiva al estado.

¿Qué es MVVM?
	-Patrón de arquitectura para separar responsabilidades:
	-Model → Datos (ejemplo: clase Product).
	-ViewModel → Lógica de negocio, conserva estado y expone observables.
	-View (UI) → Dibuja en pantalla, observa al ViewModel y se actualiza automáticamente.

 ¿Qué es ViewModel?
	-Clase de Android Jetpack que sobrevive a cambios de configuración (ej. rotación).
	-Ideal para almacenar y manejar datos de UI.

 ¿Qué es LiveData?
	-Contenedor observable que notifica cambios de datos a la UI.
        -Cuando el ViewModel cambia un valor, la UI se actualiza automáticamente.

 ¿Qué es el archivo build.gradle (Module)?
•	Archivo de configuración de dependencias y características de la app.
•	Para habilitar Compose y MVVM agregamos:
buildFeatures {
    compose true
}
composeOptions {
    kotlinCompilerExtensionVersion = "1.5.14"
}
dependencies {
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.7.0")
}

Construcción paso a paso
Hito 1 — Modelo
Archivo: model/Product.kt
package com.example.inventory.model

data class Product(
    val name: String,
    val price: Float
)

Hito 2 — ViewModel
Archivo: vm/ProductViewModel.kt
package com.example.inventory.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.inventory.model.Product

class ProductViewModel : ViewModel() {

    private val _product = MutableLiveData(Product("Audífonos", 21.99F))
    val product: LiveData<Product> get() = _product

    private val colors = listOf(
        0xFFE53935.toInt(), // rojo
        0xFF1E88E5.toInt(), // azul
        0xFF43A047.toInt(), // verde
        0xFFFDD835.toInt()  // amarillo
    )

    private val _nameColor = MutableLiveData(colors.first())
    val nameColor: LiveData<Int> get() = _nameColor

    fun changeProduct() {
        val products = listOf(
            Product("Laptop", 899.99F),
            Product("Mouse", 15.50F),
            Product("Teclado", 35.75F),
            Product("Monitor", 250.00F)
        )
        _product.value = products.random()
        _nameColor.value = colors.random()
    }
}

Hito 3 — MainActivity y UI
Archivo: MainActivity.kt
class MainActivity : ComponentActivity() {

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
Archivo: ui/MainScreen.kt
@Composable
fun MainScreen(vm: ProductViewModel) {
    val product = vm.product.observeAsState(Product("Cargando...", 0F)).value
    val nameColor = vm.nameColor.observeAsState().value ?: 0xFF000000.toInt()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Nombre: ${product.name}",
            color = Color(nameColor)
        )
        Text(text = "Precio: $${product.price}")

        Button(onClick = { vm.changeProduct() }) {
            Text("Cambiar producto")
        }
    }
}

Hito 4 — Color dinámico
 El nombre cambia de color cada vez que presionamos el botón.
 texto en rojo, azul, verde, etc.

Hito 5 — Documentación
Se agregó en este README cada hito con explicación + capturas.
En /docs/errores-comunes.md se incluyen los problemas frecuentes.
Errores comunes
Archivo: /docs/errores-comunes.md
# Errores comunes en Inventory App

## 1. Pantalla en blanco
- **Causa**: `setContent {}` vacío o sin Composable.
- **Solución**: asegurarse de invocar `MainScreen(vm)` en `MainActivity`.

## 2. Error "Unresolved reference: observeAsState"
- **Causa**: falta dependencia `lifecycle-runtime-compose`.
- **Solución**: agregar en `build.gradle`:
  implementation("androidx.lifecycle:lifecycle-runtime-compose:2.7.0")

## 3. Error de versiones Compose
- **Causa**: `kotlinCompilerExtensionVersion` no coincide con Compose BOM.
- **Solución**: actualizar Compose BOM y Kotlin en Gradle.

