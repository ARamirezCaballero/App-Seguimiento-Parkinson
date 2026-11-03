Intructivo para correr la demo en un emulador:

Requisitos previos:
- Android Studio (versión reciente, preferiblemente Ladybug o superior)
- JDK 17
- Emulador con  API 34 o superior (recomendado API 35 o 36) y Google Play
- Conexión a internet la primera vez (descarga de dependencias)
  
1. Clonar el repositorio de github en Android Studio
2. Abrir el proyecto y esperar a que Gradle finalice la sincronización del proyecto (puede tardar)
3. Crear o seleccionar un emulador:
   - Dispositivo: cualquier Pixel o Medium Phone
   - System image: API 34+ con Google Play (Health Connect viene integrado de sistema)
4. Seleccionar el modulo de app y correr el programa.

Notas:
- La base de datos está cifrada con SQLCipher + Android KeyStore; no requiere configuración adicional.
- Si la sincronización de Gradle falla, prueba: File → Invalidate Caches → Restart.
