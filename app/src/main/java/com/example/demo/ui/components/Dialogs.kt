package com.example.demo.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.demo.ui.theme.DemoTheme

data class SeverityOption(
    val level: Int,
    val label: String
) {
    val displayText: String
        get() = "$level: $label"
}

val DefaultSeverityOptions = listOf(
    SeverityOption(0, "Normal"),
    SeverityOption(1, "Mínimo"),
    SeverityOption(2, "Leve"),
    SeverityOption(3, "Moderado"),
    SeverityOption(4, "Grave")
)

/**
 * Diálogo genérico reutilizable para selección de gravedad o de opciones con tarjetas apiladas.
 * - Centrado perfectamente en la pantalla.
 * - Tarjetas de opción en color blanco con texto Poppins negro en negrita.
 * - Altura mínima táctil de 56dp (14mm).
 * - Botón "Cancelar" ubicado afuera (debajo) del diálogo principal como texto blanco accesible.
 */
@Composable
fun SeveritySelectionDialog(
    title: String,
    onOptionSelected: (SeverityOption) -> Unit,
    onCancelarClick: () -> Unit,
    modifier: Modifier = Modifier,
    options: List<SeverityOption> = DefaultSeverityOptions,
    containerColor: Color = Color(0xFF7EA6E0)
) {
    Dialog(
        onDismissRequest = onCancelarClick,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            SeveritySelectionDialogContent(
                title = title,
                options = options,
                onOptionSelected = onOptionSelected,
                onCancelarClick = onCancelarClick,
                modifier = modifier,
                containerColor = containerColor
            )
        }
    }
}

@Composable
fun SeveritySelectionDialogContent(
    title: String,
    options: List<SeverityOption>,
    onOptionSelected: (SeverityOption) -> Unit,
    onCancelarClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = Color(0xFF7EA6E0)
) {
    Column(
        modifier = modifier
            .fillMaxWidth(0.9f)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Tarjeta / Diálogo Principal con el Título y las Opciones
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            color = containerColor
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Título centrado
                Text(
                    text = title,
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Default,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Botones rectangulares uno debajo del otro (mínimo 14mm / 56dp)
                options.forEach { option ->
                    Button(
                        onClick = { onOptionSelected(option) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .defaultMinSize(minHeight = 56.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black
                        )
                    ) {
                        Text(
                            text = option.displayText,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            fontFamily = FontFamily.Default,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }

        // Opción de cancelar AFUERA del diálogo principal (solo texto en blanco sin tarjeta)
        TextButton(
            onClick = onCancelarClick,
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 56.dp)
        ) {
            Text(
                text = "Cancelar",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Default,
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SeveritySelectionDialogPreview() {
    DemoTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.25f)),
            contentAlignment = Alignment.Center
        ) {
            SeveritySelectionDialogContent(
                title = "Nivel de Severidad del Síntoma",
                options = DefaultSeverityOptions,
                onOptionSelected = {},
                onCancelarClick = {}
            )
        }
    }
}
