package com.example.demo.ui.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun HomeNavigationSection(
    modifier: Modifier = Modifier,
    onEvolucionClick: () -> Unit = {},
    onInformeClick: () -> Unit = {},
) {
    //Ubicacion en parte inferior de la pantalla
    //Fondo blanco como el fondo general de la app
    //separadores finos color #CCCCCC
    //Contiene EvolucionButton e InformeButton
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        HorizontalDivider(color = Color(0xFFCCCCCC), thickness = 1.dp)
        //Distribución horizontal de dos botones
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            EvolucionButton(
                onClick = onEvolucionClick,
                modifier = Modifier.weight(1f)
            )
            VerticalDivider(
                color = Color(0xFFCCCCCC),
                thickness = 1.dp,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(vertical = 4.dp)
            )
            InformeButton(
                onClick = onInformeClick,
                modifier = Modifier.weight(1f)
            )
        }
    }
}
