package com.example.demo.ui.screens.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.demo.R

@Composable
fun SyncStatus(
    modifier: Modifier = Modifier,
    lastSyncDate: String? = null,
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        //Tituilo "Datos Automáticos" con tipografia Poppins en bold con color #4C0099 aineado a la izquierda debajo de seccion MedicacionSeccion"
        Text(
            text = "Datos Automáticos",
            color = Color(0xFF4C0099),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        //Icono de reloj con fecha y hora de la ultima sincronización exitosa color de texto negro, poppins normal
        //Alineacion a la izquierda
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.reloj_sync_health__connect),
                contentDescription = "Sincronización",
                tint = Color(0xFF1491FB),
                modifier = Modifier
                    .padding(end = 6.dp)
                    .size(20.dp)
            )
            Text(
                text = if (lastSyncDate != null) "Última sincronización: $lastSyncDate" else "Sin datos de sincronización",
                color = Color.Black,
                fontSize = 14.sp
            )
        }
    }
}
