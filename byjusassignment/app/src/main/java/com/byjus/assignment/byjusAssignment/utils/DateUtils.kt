package com.byjus.assignment.byjusAssignment.utils

import java.text.SimpleDateFormat

class DateUtils {
    companion object{
        const val DEFAULT_DATE_TEMPLATE = "yyyy-MM-dd'T'HH:mm:ssZ"


        fun getFormattedDateString(date: String): String {
            var str = ""
            if(date.length>5) {
                if(date[10].isDigit()) str = date.substring(0,10)
                else str = date.substring(0,8) +"0"+date[9]
            }
            return str
        }
    }
}