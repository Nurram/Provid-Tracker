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

    @Query("select * from habit where email=:email or email='all'")
    fun getHabitByEmail(email: String): LiveData<List<Habit>>

    @Query("update habit set isDone=0 where id in (:ids)")
    suspend fun setDefaultHabitToFalse(ids: List<Int>)
}