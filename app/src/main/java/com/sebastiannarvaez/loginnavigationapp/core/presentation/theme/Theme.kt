package com.sebastiannarvaez.loginnavigationapp.core.presentation.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    // Colores Principales (El Verde Neón)
    primary = NeonLime,
    onPrimary = Black, // Texto negro sobre el verde neón para contraste
    primaryContainer = NeonLimeDark, // Contenedores de baja prioridad
    onPrimaryContainer = Black,
    inversePrimary = NeonLime,

    // Colores Secundarios (El Azul Eléctrico)
    secondary = ElectricBlue,
    onSecondary = White, // Texto blanco sobre el azul
    secondaryContainer = DeepBlue,
    onSecondaryContainer = White,

    // Colores Terciarios (Usaremos un gris neutro o el azul suave si es necesario)
    tertiary = LightSurface,
    onTertiary = White,
    tertiaryContainer = LightSurface,
    onTertiaryContainer = White,

    // Fondos y Superficies (La base del Dark Mode)
    background = BlackBackground,
    onBackground = White,
    surface = DarkSurface, // Tarjetas
    onSurface = White,

    // Variantes de superficie (ej. Cards secundarias, Inputs)
    surfaceVariant = LightSurface,
    onSurfaceVariant = White80, // Texto grisáceo/secundario

    // Bordes y líneas divisorias
    outline = Color(0xFF3E3E42),
    outlineVariant = Color(0xFF2C2C2E),

    // Errores
    error = ErrorRed,
    onError = White,
    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD6),

    // Scrim (Sombra detrás de modales)
    scrim = Color.Black
)

private val LightColorScheme = lightColorScheme(
    primary = ElectricBlue, // En modo claro, el azul suele funcionar mejor como primario
    onPrimary = White,
    primaryContainer = Color(0xFFD6E4FF),
    onPrimaryContainer = Color(0xFF001B3D),

    secondary = NeonLimeDark, // Usamos el verde oscuro para que se vea en fondo blanco
    onSecondary = White,
    secondaryContainer = Color(0xFFE8F5B8), // Un verde muy pálido
    onSecondaryContainer = Color(0xFF151D00),

    tertiary = Color(0xFF7D5260),
    onTertiary = White,

    background = Color(0xFFF2F2F7), // Gris muy claro estilo iOS
    onBackground = Black,
    surface = White,
    onSurface = Black,
    surfaceVariant = Color(0xFFE1E2EC),
    onSurfaceVariant = Color(0xFF44474F),

    outline = Color(0xFF74777F),
    error = Color(0xFFBA1A1A),
    onError = White
)
@Composable
fun LoginNavigationAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}