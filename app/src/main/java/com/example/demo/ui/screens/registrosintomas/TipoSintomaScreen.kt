package com.example.demo.ui.screens.registrosintomas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.demo.ui.screens.registrosintomas.components.TipoSintomaContent
import com.example.demo.ui.screens.registrosintomas.components.TipoSintomaHeader
import com.example.demo.ui.theme.DemoTheme

@Composable
fun TipoSintomaScreen(
    onMotoresClick: () -> Unit,
    onNoMotoresClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = Color.White,
        topBar = {
            TipoSintomaHeader(
                onBackClick = onBackClick
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            TipoSintomaContent(
                onMotoresClick = onMotoresClick,
                onNoMotoresClick = onNoMotoresClick
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TipoSintomaScreenPreview() {
    DemoTheme {
        TipoSintomaScreen(
            onMotoresClick = {},
            onNoMotoresClick = {},
            onBackClick = {}
        )
    }
}
