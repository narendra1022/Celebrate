package com.example.assignment.utils

import androidx.room.TypeConverter
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

