package com.example.demo.ui.screens.informe.components

import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun ReportGenerationStatus(
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    SnackbarHost(
        hostState = snackbarHostState,
        modifier = modifier
    ) { snackbarData ->
        Snackbar(
            containerColor = Color(0xFF323235),
            contentColor = Color.White,
            action = snackbarData.visuals.actionLabel?.let { actionLabel ->
                {
                    TextButton(onClick = { snackbarData.performAction() }) {
                        Text(
                            text = actionLabel,
                            color = Color(0xFFDCD8FE)
                        )
                    }
                }
            }
        ) {
            Text(text = snackbarData.visuals.message)
        }
    }
}
