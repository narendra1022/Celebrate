package com.example.assignment.utils

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

class Utils {

    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun toDate(timestamp: Long?): Date? {
        return timestamp?.let { Date(it) }
    }
}

enum class EventType {
    ANNIVERSARY, BIRTHDAY
}


fun formatDate(input: String): String {
    val inputFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH)
    val date = inputFormat.parse(input)

    val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH)
    return date?.let { outputFormat.format(it) } ?: "Invalid date"
}
