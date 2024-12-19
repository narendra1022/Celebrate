package com.example.assignment.model.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.assignment.utils.EventType
import java.util.Date

@Entity(tableName = "events")
data class CardEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val date: Date,
    val eventType: EventType,
    val description: String
)