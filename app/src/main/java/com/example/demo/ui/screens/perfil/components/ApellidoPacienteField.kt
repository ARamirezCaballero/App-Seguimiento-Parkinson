package com.example.demo.ui.screens.perfil.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

//etiqueta Apellido con indicador de obligatorio (*)
//Campo de entrada de texto con placeholder "ingresa tu apellido"
//Mensaje de validacion por campo obligatorio vacio
@Composable
fun ApellidoPacienteField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    errorMessage: String? = null,
) {
    Column(modifier = modifier) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            label = {
                //etiqueta Apellido con indicador de obligatorio (*)
                Text(text = "Apellido *")
            },
            placeholder = {
                //Campo de entrada de texto con placeholder "ingresa tu apellido"
                Text(text = "ingresa tu apellido")
            },
            isError = errorMessage != null,
            supportingText = {
                //Mensaje de validacion por campo obligatorio vacio
                if (errorMessage != null) {
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            singleLine = true
        )
    }
}
