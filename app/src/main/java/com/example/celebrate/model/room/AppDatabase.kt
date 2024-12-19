package com.example.assignment.model.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.assignment.utils.Utils
import com.example.assignment.model.models.CardEntity

@Database(entities = [CardEntity::class], version = 1)
@TypeConverters(Utils::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun eventDao(): eventDao
}
