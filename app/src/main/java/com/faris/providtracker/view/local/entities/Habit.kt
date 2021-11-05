package com.faris.providtracker.view.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.faris.providtracker.view.local.DateConverter
import java.util.*

@Entity
@TypeConverters(DateConverter::class)
data class Habit(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val dateTime: Date,
    var isDone: Boolean,
    val email: String
)