package com.example.demo.ui.screens.perfil.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.compose.foundation.layout.statusBarsPadding

//Titulo PERFIL DEL PACIENTE, centrado y color prupura #4c0099
//Fondo Blanco
@Composable
fun ProfileHeader(
    modifier: Modifier = Modifier,
) {
    //Fondo Blanco
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .statusBarsPadding()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        //Titulo PERFIL DEL PACIENTE, centrado y color prupura #4c0099
        Text(
            text = "PERFIL DEL PACIENTE",
            color = Color(0xFF4C0099),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}
