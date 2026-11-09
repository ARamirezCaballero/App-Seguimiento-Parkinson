package com.example.demo.util

import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import com.example.demo.dominio.model.InformeSeguimiento
import com.example.demo.dominio.model.enums.EstadoMotor
import java.io.OutputStream
import java.util.Locale
import javax.inject.Inject

class PdfExporter @Inject constructor() {

    fun exportarPdf(informe: InformeSeguimiento, outputStream: OutputStream) {
        val pdfDocument = PdfDocument()
        val pageWidth = 595
        val pageHeight = 842
        val marginLeft = 40f
        val marginRight = 555f
        val contentWidth = marginRight - marginLeft

        // Fuentes de texto (Times New Roman / Serif)
        val fontSerifNormal = Typeface.create("serif", Typeface.NORMAL)
        val fontSerifBold = Typeface.create("serif", Typeface.BOLD)
        val fontSerifItalic = Typeface.create("serif", Typeface.ITALIC)

        // Estilos de texto
        val titlePaint = Paint().apply {
            typeface = fontSerifBold
            textSize = 14f
            color = Color.BLACK
            isAntiAlias = true
        }

        val sectionTitlePaint = Paint().apply {
            typeface = fontSerifBold
            textSize = 12f
            color = Color.rgb(40, 40, 80)
            isAntiAlias = true
        }

        val subtitlePaint = Paint().apply {
            typeface = fontSerifItalic
            textSize = 12f
            color = Color.DKGRAY
            isAntiAlias = true
        }

        val normalPaint = Paint().apply {
            typeface = fontSerifNormal
            textSize = 12f
            color = Color.BLACK
            isAntiAlias = true
        }

        val boldNormalPaint = Paint().apply {
            typeface = fontSerifBold
            textSize = 12f
            color = Color.BLACK
            isAntiAlias = true
        }

        val italicPaint = Paint().apply {
            typeface = fontSerifItalic
            textSize = 12f
            color = Color.BLACK
            isAntiAlias = true
        }

        val disclaimerPaint = Paint().apply {
            typeface = fontSerifItalic
            textSize = 10f
            color = Color.DKGRAY
            isAntiAlias = true
        }

        val linePaint = Paint().apply {
            color = Color.LTGRAY
            strokeWidth = 1f
            isAntiAlias = true
        }

        val tableHeaderBgPaint = Paint().apply {
            color = Color.rgb(240, 242, 245)
            style = Paint.Style.FILL
        }

        val tableBorderPaint = Paint().apply {
            color = Color.rgb(200, 200, 200)
            style = Paint.Style.STROKE
            strokeWidth = 1f
            isAntiAlias = true
        }

        var pageNumber = 1
        var pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, pageNumber).create()
        var page = pdfDocument.startPage(pageInfo)
        var canvas = page.canvas
        var y = 45f

        fun checkNewPage(neededHeight: Float) {
            if (y + neededHeight > pageHeight - 45f) {
                pdfDocument.finishPage(page)
                pageNumber++
                pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, pageNumber).create()
                page = pdfDocument.startPage(pageInfo)
                canvas = page.canvas
                y = 45f
            }
        }

        fun drawWrappedText(
            text: String,
            paint: Paint,
            x: Float = marginLeft,
            maxWidth: Float = contentWidth,
            lineSpacing: Float = 14f
        ) {
            val words = text.split(" ")
            var currentLine = StringBuilder()

            for (word in words) {
                val testLine = if (currentLine.isEmpty()) word else "$currentLine $word"
                val measure = paint.measureText(testLine)
                if (measure > maxWidth && currentLine.isNotEmpty()) {
                    checkNewPage(lineSpacing)
                    canvas.drawText(currentLine.toString(), x, y, paint)
                    y += lineSpacing
                    currentLine = StringBuilder(word)
                } else {
                    currentLine = StringBuilder(testLine)
                }
            }

            if (currentLine.isNotEmpty()) {
                checkNewPage(lineSpacing)
                canvas.drawText(currentLine.toString(), x, y, paint)
                y += lineSpacing
            }
        }

        fun drawTableOnCanvas(
            headers: List<String>,
            rows: List<List<String>>,
            colWidths: List<Float>,
            startX: Float,
            headerPaint: Paint = boldNormalPaint,
            cellPaint: Paint = normalPaint,
            rowHeight: Float = 22f
        ) {
            val totalWidth = colWidths.sum()
            val totalHeight = (rows.size + 1) * rowHeight

            // Fondo del encabezado
            val headerRect = RectF(startX, y, startX + totalWidth, y + rowHeight)
            canvas.drawRect(headerRect, tableHeaderBgPaint)

            // Texto de los encabezados
            var currentX = startX
            for (i in headers.indices) {
                val cellText = headers[i]
                val cellWidth = colWidths[i]
                val textY = y + rowHeight / 2f + (headerPaint.textSize / 3f) - 1f
                canvas.drawText(cellText, currentX + 8f, textY, headerPaint)
                currentX += cellWidth
            }

            // Texto de las filas
            var currentY = y + rowHeight
            for (row in rows) {
                currentX = startX
                for (i in row.indices) {
                    val cellText = row[i]
                    val cellWidth = colWidths[i]
                    val textY = currentY + rowHeight / 2f + (cellPaint.textSize / 3f) - 1f
                    canvas.drawText(cellText, currentX + 8f, textY, cellPaint)
                    currentX += cellWidth
                }
                currentY += rowHeight
            }

            // Bordes exteriores
            canvas.drawRect(RectF(startX, y, startX + totalWidth, y + totalHeight), tableBorderPaint)

            // Líneas horizontales
            for (r in 1..rows.size) {
                val lineY = y + r * rowHeight
                canvas.drawLine(startX, lineY, startX + totalWidth, lineY, tableBorderPaint)
            }

            // Líneas verticales
            var vX = startX
            for (w in colWidths.dropLast(1)) {
                vX += w
                canvas.drawLine(vX, y, vX, y + totalHeight, tableBorderPaint)
            }

            y += totalHeight
        }

        // 1. Encabezado del informe
        checkNewPage(60f)
        canvas.drawText("Informe de Seguimiento Longitudinal", marginLeft, y, titlePaint)
        y += 18f
        canvas.drawText("Monitorización de la Enfermedad de Parkinson", marginLeft, y, subtitlePaint)
        y += 25f
        canvas.drawLine(marginLeft, y, marginRight, y, linePaint)
        y += 20f

        // 2. Datos Del Paciente
        checkNewPage(90f)
        canvas.drawText("Datos Del Paciente", marginLeft, y, sectionTitlePaint)
        y += 20f

        canvas.drawText("Nombre completo: ${informe.pacienteNombreCompleto}", marginLeft, y, normalPaint)
        y += 18f
        canvas.drawText("Fecha de diagnóstico: ${informe.fechaDiagnostico}", marginLeft, y, normalPaint)
        y += 18f
        canvas.drawText("Período de evaluación: ${informe.fechaInicio} a ${informe.fechaFin}", marginLeft, y, normalPaint)
        y += 25f
        canvas.drawLine(marginLeft, y, marginRight, y, linePaint)
        y += 20f

        // 3. Adherencia Farmacológica
        checkNewPage(120f)
        canvas.drawText("1. Adherencia Farmacológica", marginLeft, y, sectionTitlePaint)
        y += 20f

        val adh = informe.adherencia
        canvas.drawText("• Tomas a horario: ${adh.tomasAHorario}", marginLeft + 10f, y, normalPaint)
        y += 18f
        canvas.drawText("• Tomas fuera de horario: ${adh.tomasDeshorario}", marginLeft + 10f, y, normalPaint)
        y += 18f
        canvas.drawText("• Tomas omitidas: ${adh.tomasOmitidas}", marginLeft + 10f, y, normalPaint)
        y += 18f
        val desfaseStr = String.format(Locale.getDefault(), "%.1f", adh.desfasePromedioMinutos)
        canvas.drawText("• Desfase horario promedio: $desfaseStr minutos", marginLeft + 10f, y, normalPaint)
        y += 18f
        canvas.drawText("Origen de datos: ${adh.mensaje}", marginLeft + 10f, y, italicPaint)
        y += 25f
        canvas.drawLine(marginLeft, y, marginRight, y, linePaint)
        y += 20f

        // 4. Estado Motor y Fluctuaciones
        checkNewPage(240f)
        canvas.drawText("2. Estado Motor y Fluctuaciones", marginLeft, y, sectionTitlePaint)
        y += 20f

        val em = informe.estadoMotorYSintomas
        val dist = em.distribucionPorcentualMotor
        val dur = em.duracionMinutosPorEstado

        val pctOn = dist[EstadoMotor.ON] ?: 0.0
        val minOn = dur[EstadoMotor.ON] ?: 0L
        val pctOff = dist[EstadoMotor.OFF] ?: 0.0
        val minOff = dur[EstadoMotor.OFF] ?: 0L
        val pctDisc = dist[EstadoMotor.Discinesia] ?: 0.0
        val minDisc = dur[EstadoMotor.Discinesia] ?: 0L
        val pctDorm = dist[EstadoMotor.Dormido] ?: 0.0
        val minDorm = dur[EstadoMotor.Dormido] ?: 0L
        val pctSin = em.porcentajeSinInformacion
        val minSin = em.minutosSinInformacion

        // Tabla de distribución de estados motores
        canvas.drawText("Distribución de los estados motores registrados", marginLeft, y, boldNormalPaint)
        y += 18f

        val headersEstadoMotor = listOf("Estado motor", "Duración acumulada", "Porcentaje")
        val colWidthsEstadoMotor = listOf(170f, 175f, 170f)
        val rowsEstadoMotor = mutableListOf(
            listOf("ON", "$minOn min", "${String.format(Locale.getDefault(), "%.1f", pctOn)}%"),
            listOf("OFF", "$minOff min", "${String.format(Locale.getDefault(), "%.1f", pctOff)}%"),
            listOf("Discinesia", "$minDisc min", "${String.format(Locale.getDefault(), "%.1f", pctDisc)}%")
        )
        if (pctDorm > 0 || minDorm > 0) {
            rowsEstadoMotor.add(listOf("Dormido", "$minDorm min", "${String.format(Locale.getDefault(), "%.1f", pctDorm)}%"))
        }
        rowsEstadoMotor.add(listOf("Sin información", "$minSin min", "${String.format(Locale.getDefault(), "%.1f", pctSin)}%"))

        drawTableOnCanvas(
            headers = headersEstadoMotor,
            rows = rowsEstadoMotor,
            colWidths = colWidthsEstadoMotor,
            startX = marginLeft
        )

        y += 12f
        canvas.drawText(
            "Nota. La distribución se calcula a partir de los registros de estado motor y sus intervalos temporales.",
            marginLeft,
            y,
            disclaimerPaint
        )
        y += 25f

        // Representación gráfica mediante barra apilada
        checkNewPage(50f)
        val barHeight = 22f
        var currentX = marginLeft

        val colorOn = Color.rgb(76, 175, 80)     // Verde
        val colorOff = Color.rgb(244, 67, 54)    // Rojo
        val colorDisc = Color.rgb(255, 152, 0)   // Naranja
        val colorDorm = Color.rgb(63, 81, 181)    // Índigo
        val colorSin = Color.rgb(189, 189, 189)   // Gris

        val totalPct = (pctOn + pctOff + pctDisc + pctDorm + pctSin).coerceAtLeast(1.0)

        fun drawBarSegment(pct: Double, color: Int) {
            if (pct > 0) {
                val segWidth = (pct / totalPct * contentWidth).toFloat()
                val rect = RectF(currentX, y, currentX + segWidth, y + barHeight)
                val barPaint = Paint().apply { this.color = color; style = Paint.Style.FILL }
                canvas.drawRect(rect, barPaint)
                currentX += segWidth
            }
        }

        drawBarSegment(pctOn, colorOn)
        drawBarSegment(pctOff, colorOff)
        drawBarSegment(pctDisc, colorDisc)
        drawBarSegment(pctDorm, colorDorm)
        drawBarSegment(pctSin, colorSin)

        y += barHeight + 15f

        // Leyenda del gráfico de barra
        val legendPaint = Paint().apply {
            typeface = fontSerifNormal
            textSize = 10f
            color = Color.BLACK
            isAntiAlias = true
        }

        fun drawLegendItem(lx: Float, ly: Float, label: String, color: Int): Float {
            val sqSize = 8f
            val sqPaint = Paint().apply { this.color = color; style = Paint.Style.FILL }
            canvas.drawRect(RectF(lx, ly - 7f, lx + sqSize, ly + 1f), sqPaint)
            canvas.drawText(label, lx + sqSize + 4f, ly, legendPaint)
            return lx + sqSize + 4f + legendPaint.measureText(label) + 15f
        }

        var legX = marginLeft
        legX = drawLegendItem(legX, y, "ON", colorOn)
        legX = drawLegendItem(legX, y, "OFF", colorOff)
        legX = drawLegendItem(legX, y, "Discinesia", colorDisc)
        if (pctDorm > 0 || minDorm > 0) {
            legX = drawLegendItem(legX, y, "Dormido", colorDorm)
        }
        drawLegendItem(legX, y, "Sin info", colorSin)

        y += 20f
        canvas.drawText("Origen de datos: ${em.mensaje}", marginLeft + 10f, y, italicPaint)
        y += 25f
        canvas.drawLine(marginLeft, y, marginRight, y, linePaint)
        y += 20f

        // 5. Registro y Severidad de Síntomas
        checkNewPage(260f)
        canvas.drawText("3. Registro y Severidad de Síntomas", marginLeft, y, sectionTitlePaint)
        y += 20f

        // Tabla centrada de escala de severidad
        val tableSeveridadWidth = 280f
        val tableSeveridadStartX = (pageWidth - tableSeveridadWidth) / 2f
        val headersSeveridad = listOf("Valor", "Clasificación")
        val colWidthsSeveridad = listOf(90f, 190f)
        val rowsSeveridad = listOf(
            listOf("0", "Normal"),
            listOf("1", "Leve"),
            listOf("2", "Moderado"),
            listOf("3", "Grave"),
            listOf("4", "Muy grave")
        )

        drawTableOnCanvas(
            headers = headersSeveridad,
            rows = rowsSeveridad,
            colWidths = colWidthsSeveridad,
            startX = tableSeveridadStartX
        )

        y += 25f

        // Categoría 1: Motores
        canvas.drawText("Síntomas Motores:", marginLeft + 10f, y, boldNormalPaint)
        y += 18f
        val sevMotStr = String.format(Locale.getDefault(), "%.2f", em.severidadPromedioMotores)
        canvas.drawText("• Severidad promedio: $sevMotStr", marginLeft + 20f, y, normalPaint)
        y += 18f
        val masFreqMot = em.sintomasMotoresMasFrecuentes.joinToString(", ").ifEmpty { "Sin registros" }
        canvas.drawText("• Más frecuentes: $masFreqMot", marginLeft + 20f, y, normalPaint)
        y += 22f

        // Categoría 2: No Motores
        canvas.drawText("Síntomas No Motores:", marginLeft + 10f, y, boldNormalPaint)
        y += 18f
        val sevNoMotStr = String.format(Locale.getDefault(), "%.2f", em.severidadPromedioNoMotores)
        canvas.drawText("• Severidad promedio: $sevNoMotStr", marginLeft + 20f, y, normalPaint)
        y += 18f
        val masFreqNoMot = em.sintomasNoMotoresMasFrecuentes.joinToString(", ").ifEmpty { "Sin registros" }
        canvas.drawText("• Más frecuentes: $masFreqNoMot", marginLeft + 20f, y, normalPaint)
        y += 25f
        canvas.drawLine(marginLeft, y, marginRight, y, linePaint)
        y += 20f

        // 6. Resumen de Sueño
        checkNewPage(140f)
        canvas.drawText("4. Resumen de Sueño", marginLeft, y, sectionTitlePaint)
        y += 20f

        val sueno = informe.resumenSueno
        val durPromMin = sueno.duracionPromedioMinutos
        val durPromHrsStr = String.format(Locale.getDefault(), "%.1f", durPromMin / 60.0)
        canvas.drawText("• Duración promedio por sesión: $durPromHrsStr horas (${durPromMin.toInt()} min)", marginLeft + 10f, y, normalPaint)
        y += 18f
        val pctIntStr = String.format(Locale.getDefault(), "%.1f", sueno.porcentajeConInterrupciones)
        canvas.drawText("• Sesiones con interrupciones (>30 min despierto): $pctIntStr%", marginLeft + 10f, y, normalPaint)
        y += 18f
        val pctSinIntStr = String.format(Locale.getDefault(), "%.1f", sueno.porcentajeSinInterrupciones)
        canvas.drawText("• Sesiones sin interrupciones significativas: $pctSinIntStr%", marginLeft + 10f, y, normalPaint)
        y += 18f
        canvas.drawText("• Cantidad total de sesiones registradas: ${sueno.sesionesCount}", marginLeft + 10f, y, normalPaint)
        y += 18f
        canvas.drawText("Origen de datos: ${sueno.mensaje}", marginLeft + 10f, y, italicPaint)
        y += 25f
        canvas.drawLine(marginLeft, y, marginRight, y, linePaint)
        y += 20f

        // 7. Nota Metodológica y Descargo de Responsabilidad
        checkNewPage(90f)
        canvas.drawText("5. Nota Métrica y Descargo de Responsabilidad", marginLeft, y, sectionTitlePaint)
        y += 20f

        val notaDisclaimer = "Este informe consolida registros aportados de forma activa por el paciente/cuidador y telemetría pasiva recopilada mediante dispositivos de salud comerciales. No reemplaza el diagnóstico médico presencial ni constituye una indicación de cambio de tratamiento automático."
        drawWrappedText(notaDisclaimer, disclaimerPaint, lineSpacing = 14f)

        pdfDocument.finishPage(page)
        pdfDocument.writeTo(outputStream)
        pdfDocument.close()
    }
}
