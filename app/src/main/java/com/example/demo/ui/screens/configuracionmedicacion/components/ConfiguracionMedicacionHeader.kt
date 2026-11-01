package com.example.demo.ui.screens.configuracionmedicacion.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.demo.ui.components.TopBackHeader

// Boton para ir atrás con drawable outline_arrow_back_ios_new_24.xml en un circulo color #dcd8fe
// el boton debe estar alineado a la izquierda
// linea de separacion entre el header y el cuerpo, justo arriba del titulo del cuerpo
@Composable
fun ConfiguracionMedicacionHeader(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopBackHeader(
        onBackClick = onBackClick,
        modifier = modifier
    )
}
