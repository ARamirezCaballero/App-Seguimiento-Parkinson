package com.example.demo.ui.screens.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.demo.R

@Composable
fun RegisterMotorStateCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    //Card color #7162FB cuadrada con bordes redondeados
    //incono de silueta humana con lupa blanco arriba del texto "Registrar Estado Motor" todo centrado y texto blanco, tipografia poppins semibold
    //Al pulsar navega a RegistroEstadoMotorScreen
    Card(
        modifier = modifier
            .aspectRatio(1f)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF7162FB))
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.registrar_estado_motor_icon),
                contentDescription = "Registrar Estado Motor",
                tint = Color.White,
                modifier = Modifier
                    .padding(bottom = 12.dp)
                    .size(56.dp)
            )
            Text(
                text = "Registrar Estado Motor",
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}
