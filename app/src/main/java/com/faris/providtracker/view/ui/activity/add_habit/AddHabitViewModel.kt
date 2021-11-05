package com.faris.providtracker.view.ui.activity.add_habit

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.faris.providtracker.view.local.entities.Habit
import com.faris.providtracker.view.repos.MainRepository
import kotlinx.coroutines.launch

class AddHabitViewModel(
    private val mainRepository: MainRepository,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    fun insertHabit(habit: Habit) {
        viewModelScope.launch { mainRepository.insertHabit(habit) }
    }

    fun getFromPref(key: String) = sharedPreferences.getString(key, "")
}