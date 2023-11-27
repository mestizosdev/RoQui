package dev.mestizos.roqui.util

import java.text.SimpleDateFormat
import java.util.*

class DateUtil {

    companion object {
        fun toDate(fecha: String): Date {
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
            val fechaInDateType: Date
            fechaInDateType = simpleDateFormat.parse(fecha)

            return fechaInDateType
        }
    }
}