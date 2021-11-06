package com.faris.providtracker.view.ui.fragment.home

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.faris.providtracker.view.local.entities.Habit
import com.faris.providtracker.view.repos.MainRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    private val mainRepository: MainRepository,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    fun updateHabit(habit: Habit) {
        viewModelScope.launch { mainRepository.updateHabit(habit) }
    }

    fun getHabitByEmail(email: String) = mainRepository.getHabitByEmail(email)

    fun setDefaultHabitToFalse(ids: List<Int>) =
        viewModelScope.launch { mainRepository.setDefaultHabitToFalse(ids) }

    fun getFromPref(key: String) = sharedPreferences.getString(key, "")

    fun removeFromPref(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }

    fun putToPref(key: String, value: String) {
        sharedPreferences.edit().putString(key,value).apply()
    }
}