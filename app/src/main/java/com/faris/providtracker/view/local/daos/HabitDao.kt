package com.faris.providtracker.view.local.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.faris.providtracker.view.local.DateConverter
import com.faris.providtracker.view.local.entities.Habit

@Dao
interface HabitDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @TypeConverters(DateConverter::class)
    suspend fun insertHabit(habit: Habit)

    @Update
    @TypeConverters(DateConverter::class)
    suspend fun updateHabit(habit: Habit)

    @Query("select * from habit where email=:email")
    fun getHabitByEmail(email: String): LiveData<List<Habit>>
}