package com.example.demo.ui.screens.registrosintomas.components

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
import com.example.demo.dominio.model.enums.TipoSintoma
import com.example.demo.ui.components.DefaultSeverityOptions
import com.example.demo.ui.theme.DemoTheme

// dialog usando SeverityOption que se abre al seleccionar un sintoma (motor o no motor)
// dialog color de la tarjeta del sintoma elegido
// debajo de los botones de severidad, adentro del dialog debe estar la descripción del sintoma en poppins blanco normal
@Composable
fun SeveridadDialog(
    tipoSintoma: TipoSintoma,
    containerColor: Color,
    onSeveridadSelected: (Int) -> Unit,
    onCancelarClick: () -> Unit,
    modifier: Modifier = Modifier
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
            SeveridadDialogContent(
                tipoSintoma = tipoSintoma,
                containerColor = containerColor,
                onSeveridadSelected = onSeveridadSelected,
                onCancelarClick = onCancelarClick,
                modifier = modifier
            )
        }
    }
}

@Composable
fun SeveridadDialogContent(
    tipoSintoma: TipoSintoma,
    containerColor: Color,
    onSeveridadSelected: (Int) -> Unit,
    onCancelarClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth(0.9f)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Tarjeta principal del diálogo con el color del síntoma elegido
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
                // Título del síntoma
                Text(
                    text = tipoSintoma.titulo,
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Default,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Botones de severidad (0: Normal, 1: Mínimo, 2: Leve, 3: Moderado, 4: Grave)
                DefaultSeverityOptions.forEach { option ->
                    Button(
                        onClick = { onSeveridadSelected(option.level) },
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

                Spacer(modifier = Modifier.height(8.dp))

                // Descripción del síntoma debajo de los botones en Poppins blanco normal
                Text(
                    text = tipoSintoma.descripcion,
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = FontFamily.Default,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        // Opción de cancelar AFUERA del diálogo (solo texto blanco)
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
fun SeveridadDialogPreview() {
    DemoTheme {
        SeveridadDialogContent(
            tipoSintoma = TipoSintoma.Bradicinesia,
            containerColor = Color(0xFFFC836B),
            onSeveridadSelected = {},
            onCancelarClick = {}
        )
    }
}
