
# Errores comunes

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
 inicializará con el mainScreen

