package com.byjus.assignment.byjusAssignment.utils

import java.text.SimpleDateFormat

class DateUtils {
    companion object{
        const val DEFAULT_DATE_TEMPLATE = "yyyy-MM-dd'T'HH:mm:ssZ"

        fun getFormattedDate(date: String?, format: String?): String? {
            var readableDate = date
            val dateFormat = SimpleDateFormat(DEFAULT_DATE_TEMPLATE)
            val formatRequired = SimpleDateFormat(format)
            try {
                val date1 = dateFormat.parse(date)
                readableDate = formatRequired.format(date1)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return readableDate
        }

        fun getFormattedDateString(date: String): String {
            var str = ""
            if(date[10].isDigit()) str = date.substring(0,10)
            else str = date.substring(0,8) +"0"+date[9]
            return str
        }
    }
}