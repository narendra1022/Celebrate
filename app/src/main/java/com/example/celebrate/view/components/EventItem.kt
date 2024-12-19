package com.example.celebrate.view.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.assignment.utils.formatDate
import com.example.flobiz.data.model.Card
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit


@Composable
fun EventItem(
    card: Card,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    val dateFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH)
    val eventDate = remember(card.date) {
        try {
            card.date.toString().let { dateFormat.parse(it) }
        } catch (e: Exception) {
            null
        }
    }

    val currentDate = remember { Date() }
    val isUpcoming = eventDate?.after(currentDate) ?: false
    val isBirthday = card.type.toString() == "BIRTHDAY"

    val birthdayGradient = Brush.linearGradient(
        colors = listOf(
            Color(0x9AFFA07A),
            Color(0xFFFF69B4),
            Color(0x87FFD700)
        )
    )

    val anniversaryGradient = Brush.linearGradient(
        colors = listOf(
            Color(0xFF20B2AA),
            Color(0xD04169E1),
            Color(0xFF9370DB)
        )
    )

    val scale by animateFloatAsState(
        targetValue = if (isUpcoming) 1f else 0.98f,
        animationSpec = spring(dampingRatio = 0.8f)
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
            .scale(scale)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isUpcoming) 8.dp else 2.dp
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(if (isBirthday) birthdayGradient else anniversaryGradient)
        ) {
            if (isBirthday) {
                BirthdayDecorations()
            } else {
                AnniversaryDecorations()
            }

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .alpha(if (isUpcoming) 1f else 0.7f)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = card.description.ifEmpty { "No Description" },
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    EventIcon()
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = if (isBirthday) "Birthday" else "Anniversary",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White.copy(alpha = 0.9f)
                        )
                        Text(
                            text = formatDate(card.date.toString()),
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White.copy(alpha = 0.9f)
                        )
                    }

                    if (isUpcoming && eventDate != null) {
                        DaysRemainingBadge(currentDate, eventDate)
                    }
                }

                Row(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (isUpcoming) {
                        StatusChip(
                            text = "Upcoming!",
                            backgroundColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    } else {
                        StatusChip(
                            text = "Completed!",
                            backgroundColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun BirthdayDecorations() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "🎈",
            fontSize = 30.sp,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(12.dp)
                .alpha(0.9f)
        )
    }
}

@Composable
private fun AnniversaryDecorations() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "💑",
            fontSize = 30.sp,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(8.dp)
                .alpha(0.9f)
        )
    }
}

@Composable
private fun EventIcon() {

    val rotation by rememberInfiniteTransition().animateFloat(
        initialValue = -10f,
        targetValue = 10f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        )
    )

    Text(
        text = "🎉",
        modifier = Modifier
            .rotate(rotation)
            .scale(1.2f),
        fontSize = 24.sp
    )
}

@Composable
private fun DaysRemainingBadge(currentDate: Date, eventDate: Date) {
    val daysRemaining = TimeUnit.DAYS.convert(
        eventDate.time - currentDate.time,
        TimeUnit.MILLISECONDS
    )
    Surface(
        color = MaterialTheme.colorScheme.secondaryContainer,
        shape = MaterialTheme.shapes.medium
    ) {
        Text(
            text = "$daysRemaining days left",
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
    }
}

@Composable
private fun StatusChip(text: String, backgroundColor: Color) {
    Surface(
        color = backgroundColor,
        shape = MaterialTheme.shapes.small
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelSmall,
            color = contentColorFor(backgroundColor)
        )
    }
}


