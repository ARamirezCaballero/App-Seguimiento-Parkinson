package com.example.demo.ui.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProfileAvatarButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    //Icons.Default.AccountCircle fondo #DCD8FE icono persona #4C0099
    //tamaño tactil accesible pero apto para estar en header
    //al pulsarlo navega a PerfilScreen
    Box(
        modifier = modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(Color(0xFFDCD8FE))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "👤",
            fontSize = 24.sp,
            color = Color(0xFF4C0099)
        )
    }
}
