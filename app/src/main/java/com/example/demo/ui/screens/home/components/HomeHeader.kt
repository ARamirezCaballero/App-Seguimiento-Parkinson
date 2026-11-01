package com.example.demo.ui.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun HomeHeader(
    onPerfilClick: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    //fondo blanco
    //Espacio superior necesario para separar encabezado del contenido
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            //Boton de perfil alineado a la derecha
            ProfileAvatarButton(onClick = onPerfilClick)
        }
        HorizontalDivider(color = Color(0xFFCCCCCC), thickness = 1.dp)
    }
}
