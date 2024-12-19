package com.example.flobiz.data.model

import com.example.assignment.utils.EventType
import java.util.Date

data class Card(
    val id: String = "",
    val date: Date = Date(),
    var type: EventType = EventType.BIRTHDAY,
    val description: String = ""
)
