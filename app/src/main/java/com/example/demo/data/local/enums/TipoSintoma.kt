package com.example.demo.data.local.enums

enum class TipoSintoma (
    val titulo: String,
    val descripcion: String,
    val esMotor: Boolean
    ) {
    // Síntomas motores
    Bradicinesia(
        "Bradicinesia",
        "Lentitud y disminución de la amplitud de los movimientos.",
        true
    ),
    Temblor(
        "Temblor",
        "Movimientos rítmicos involuntarios que pueden aparecer en reposo o durante una actividad.",
        true
    ),
    Rigidez(
        "Rigidez muscular",
        "Resistencia involuntaria al movimiento de las extremidades o del tronco.",
        true
    ),

    Freezing(
        "Congelación de la marcha e inestabilidad postural",
        "Dificultad repentina para iniciar o continuar la marcha, o para mantener el equilibrio.",
        true
    ),

    // Síntomas no motores
    Fatiga(
        "Fatiga",
        "Sensación de agotamiento físico o mental que no se corresponde con la actividad realizada.",
        false
    ),
    Dolor_calambre_distonia(
        "Dolor, calambres o distonía dolorosa",
        "Dolor o contracciones musculares involuntarias que pueden generar molestias.",
        false
    ),
    Gastrointestinales(
        "Trastornos gastrointestinales",
        "Alteraciones intestinales, como estreñimiento o dificultad para evacuar.",
        false
    ),
    Estado_animo(
        "Alteraciones del estado de ánimo",
        "Manifestaciones como ansiedad, tristeza, apatía o falta de motivación.",
        false
    ),
    Autonomicos(
        "Síntomas autonómicos",
        "Manifestaciones como mareos al ponerse de pie, que pueden relacionarse con cambios en la presión arterial.",
        false
    )
}