package com.example.demo.ui.screens.perfil.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

//etiqueta Nombre con indicador de obligatorio (*)
//Campo de entrada de texto con placeholder "ingresa tu nombre"
//Mensaje de validacion por campo obligatorio vacio
@Composable
fun NombrePacienteField(
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
                //etiqueta Nombre con indicador de obligatorio (*)
                Text(text = "Nombre *")
            },
            placeholder = {
                //Campo de entrada de texto con placeholder "ingresa tu nombre"
                Text(text = "ingresa tu nombre")
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
