package com.faris.providtracker.view.ui.activity.register

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.faris.providtracker.view.local.entities.Habit
import com.faris.providtracker.view.local.entities.User
import com.faris.providtracker.view.repos.MainRepository
import kotlinx.coroutines.launch
import java.util.*

class RegisterViewModel(
    private val repository: MainRepository,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    fun getUserByEmail(username: String) = repository.getUserByUsername(username)

    fun insertHabit(habit: Habit) {
        viewModelScope.launch { repository.insertHabit(habit) }
    }

    fun insertUser(user: User) {
        viewModelScope.launch {
            repository.insertUser(user)
        }
    }

    fun saveToPref(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun initHabit(): List<Habit> {
        val c = Calendar.getInstance()
        c.set(Calendar.HOUR_OF_DAY, 7)
        c.set(Calendar.MINUTE, 50)

        return listOf(
            Habit(1, "Pakai Masker", c.time, false, "all"),
            Habit(2, "Tidak Melepas Masker", c.time, false, "all"),
            Habit(3, "Corona Finger Hand Extension", c.time, false, "all"),
            Habit(4, "Membawa Handsanitizer", c.time, false, "all"),
            Habit(5, "Top-Up E-Wallet", c.time, false, "all"),
            Habit(6, "Bersih-Bersih Setelah Keluar Rumah", c.time, false, "all"),
        )
    }
}