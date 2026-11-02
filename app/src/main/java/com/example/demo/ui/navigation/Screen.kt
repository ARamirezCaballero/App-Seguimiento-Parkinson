package com.example.demo.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Perfil : Screen("perfil")
    object TipoSintoma : Screen("tipo_sintoma")
    object RegistroSintomas : Screen("registro_sintomas/{esMotor}") {
        fun createRoute(esMotor: Boolean) = "registro_sintomas/$esMotor"
    }
    object RegistroEstadoMotor : Screen("registro_estado_motor")
    object RegistroToma : Screen("registro_toma")
    object ConfiguracionMedicacion : Screen("configuracion_medicacion")
    object Evolucion : Screen("evolucion")
    object Informe : Screen("informe")
    object Historial : Screen("historial")
}
