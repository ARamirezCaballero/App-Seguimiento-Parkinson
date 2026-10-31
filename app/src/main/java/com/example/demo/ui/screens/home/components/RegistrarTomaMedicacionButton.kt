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
fun RegisterMedicationIntakeButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    //tarjeta color #95DA4B cuadrado y bordes redondeados
    //Icono blanco de pildora arriba del texto "Registrar Toma Medicamento" todo centrado y texto blanco, tipografia poppins semibold
    //Al pulsar navega a RegistroTomaScreen
    Card(
        modifier = modifier
            .aspectRatio(1f)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF95DA4B))
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.registrar_medicamento_icon),
                contentDescription = "Registrar Toma Medicamento",
                tint = Color.White,
                modifier = Modifier
                    .padding(bottom = 12.dp)
                    .size(56.dp)
            )
            Text(
                text = "Registrar Toma Medicamento",
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}
