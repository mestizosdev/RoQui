package dev.mestizos.roqui.util

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class DateUtil {

    companion object {
        fun toDate(date: String): Date {
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
            val dateInDateType: Date = simpleDateFormat.parse(date)

            return dateInDateType
        }

        fun accessKeyToDate(accessKey: String): Date {
            val dateAccessKey = accessKey.substring(0, 8)
            val formatter: DateFormat = SimpleDateFormat("ddMMyyyy")
            return formatter.parse(dateAccessKey)
        }
    }
}