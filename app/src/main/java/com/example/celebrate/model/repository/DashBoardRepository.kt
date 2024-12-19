package com.example.flobiz.data.repository

import com.example.assignment.model.room.eventDao
import com.example.assignment.model.models.CardEntity
import com.example.flobiz.data.model.Card
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DashBoardRepository @Inject constructor(
    private val eventDao: eventDao
) {

    suspend fun addEvent(card: Card) {
        val cardEntity = CardEntity(
            date = card.date,
            eventType = card.type,
            description = card.description
        )
        eventDao.addEvent(cardEntity)
    }

    suspend fun deleteEvent(transactionId: Long) {
        val cardEntity = eventDao.getEventById(transactionId)
        cardEntity?.let {
            eventDao.deleteEvent(it)
        }
    }

    fun getAllEvents(): Flow<List<Card>> = eventDao.getAllEvents().map { entities ->
        entities.map { entity ->
            Card(
                id = entity.id.toString(),
                date = entity.date,
                type = entity.eventType,
                description = entity.description
            )
        }
    }
}

