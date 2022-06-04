package com.m2lifeApps.hepsiburada.core.common

import java.text.SimpleDateFormat
import java.util.*

/**
 * @user: murat.balci
 */

object DateHelper {
    fun formatServerTime(date: String): String {
        return try {
            val date1 =
                SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).parse(date)
            val calendar = Calendar.getInstance().apply { time = date1!! }
            val year = calendar.get(Calendar.YEAR)
            val month = String.format("%02d", calendar.get(Calendar.MONTH) + 1)
            val day = String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH))
            val hour = String.format("%02d", calendar.get(Calendar.HOUR))
            val min = String.format("%02d", calendar.get(Calendar.MINUTE))
            "$day/$month/$year $hour:$min"
        } catch (e: Exception) {
            "-"
        }
    }
}
