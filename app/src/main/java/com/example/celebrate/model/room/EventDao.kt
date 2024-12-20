package com.example.assignment.model.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.assignment.model.models.CardEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface eventDao {

    @Insert
    suspend fun addEvent(cardEntity: CardEntity)

    @Delete
    suspend fun deleteEvent(cardEntity: CardEntity)

    @Query("SELECT * FROM events WHERE id = :eventId LIMIT 1")
    suspend fun getEventById(eventId: Long): CardEntity?

    @Query("SELECT * FROM events order by date desc")
    fun getAllEvents(): Flow<List<CardEntity>>

    @Query("SELECT * FROM events where eventType='BIRTHDAY' order by date desc")
    fun getAllBirthdays(): Flow<List<CardEntity>>

    @Query("SELECT * FROM events where eventType='ANNIVERSARY' order by date desc")
    fun getAllAnniversariess(): Flow<List<CardEntity>>

}