package com.example.demo.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.demo.ui.screens.configuracionmedicacion.ConfiguracionMedicacionScreen
import com.example.demo.ui.screens.evolucion.EvolucionScreen
import com.example.demo.ui.screens.historial.HistorialScreen
import com.example.demo.ui.screens.home.HomeScreen
import com.example.demo.ui.screens.informe.InformeScreen
import com.example.demo.ui.screens.perfil.PerfilScreen
import com.example.demo.ui.screens.registroestadomotor.RegistroEstadoMotorScreen
import com.example.demo.ui.screens.registrosintomas.RegistroSintomasScreen
import com.example.demo.ui.screens.registrosintomas.TipoSintomaScreen
import com.example.demo.ui.screens.registrotoma.RegistroTomaScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToPerfil = { navController.navigate(Screen.Perfil.route) },
                onNavigateToTipoSintomas = { navController.navigate(Screen.TipoSintoma.route) },
                onNavigateToRegistroEstadoMotor = { navController.navigate(Screen.RegistroEstadoMotor.route) },
                onNavigateToRegistroToma = { navController.navigate(Screen.RegistroToma.route) },
                onNavigateToConfiguracionMedicacion = { navController.navigate(Screen.ConfiguracionMedicacion.route) },
                onNavigateToEvolucion = { navController.navigate(Screen.Evolucion.route) },
                onNavigateToInforme = { navController.navigate(Screen.Informe.route) },
                onNavigateToHistorial = { navController.navigate(Screen.Historial.route) }
            )
        }

        composable(Screen.Perfil.route) {
            PerfilScreen(
                onCancelar = { navController.popBackStack() }
            )
        }

        composable(Screen.TipoSintoma.route) {
            TipoSintomaScreen(
                onMotoresClick = {
                    navController.navigate(Screen.RegistroSintomas.createRoute(esMotor = true))
                },
                onNoMotoresClick = {
                    navController.navigate(Screen.RegistroSintomas.createRoute(esMotor = false))
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.RegistroSintomas.route,
            arguments = listOf(
                navArgument("esMotor") {
                    type = NavType.BoolType
                    defaultValue = true
                }
            )
        ) { backStackEntry ->
            val esMotor = backStackEntry.arguments?.getBoolean("esMotor") ?: true
            RegistroSintomasScreen(
                esMotor = esMotor,
                onVolverClick = { navController.popBackStack() }
            )
        }

        composable(Screen.RegistroEstadoMotor.route) {
            RegistroEstadoMotorScreen(
                onVolverClick = { navController.popBackStack() }
            )
        }

        composable(Screen.RegistroToma.route) {
            RegistroTomaScreen(
                onVolverClick = { navController.popBackStack() }
            )
        }

        composable(Screen.ConfiguracionMedicacion.route) {
            ConfiguracionMedicacionScreen(
                onVolverClick = { navController.popBackStack() }
            )
        }

        composable(Screen.Evolucion.route) {
            EvolucionScreen()
        }

        composable(Screen.Informe.route) {
            InformeScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Screen.Historial.route) {
            HistorialScreen(
                onVolverClick = { navController.popBackStack() }
            )
        }
    }
}
