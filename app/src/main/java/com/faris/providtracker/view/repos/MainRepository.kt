package com.faris.providtracker.view.repos

import com.faris.providtracker.view.local.MainDb
import com.faris.providtracker.view.local.entities.Habit
import com.faris.providtracker.view.local.entities.User

class MainRepository(private val db: MainDb?) {

    suspend fun insertUser(user: User) = db?.userDao?.insertUser(user)

    fun getUserByEmailAndPassword(username: String, password: String) =
        db?.userDao?.getUserByEmailAndPassword(username, password)

    fun getUserByUsername(username: String) =
        db?.userDao?.getUserByUsername(username)

    suspend fun insertHabit(habit: Habit) = db?.habitDao?.insertHabit(habit)

    suspend fun updateHabit(habit: Habit) = db?.habitDao?.updateHabit(habit)

    fun getHabitByEmail(email: String) = db?.habitDao?.getHabitByEmail(email)
}