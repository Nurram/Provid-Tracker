package com.faris.providtracker.view.ui

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.faris.providtracker.view.local.MainDb
import com.faris.providtracker.view.repos.MainRepository
import com.faris.providtracker.view.ui.activity.add_habit.AddHabitViewModel
import com.faris.providtracker.view.ui.activity.login.LoginViewModel
import com.faris.providtracker.view.ui.activity.register.RegisterViewModel
import com.faris.providtracker.view.ui.fragment.home.HomeViewModel

class ViewModelFactory(
    private val application: Application,
    private val sharedPreferences: SharedPreferences
) : ViewModelProvider.NewInstanceFactory() {

    private val db = MainDb.getDb(application)
    private val mainRepository = MainRepository(db)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(mainRepository, sharedPreferences) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(mainRepository, sharedPreferences) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(mainRepository, sharedPreferences) as T
            }
            modelClass.isAssignableFrom(AddHabitViewModel::class.java) -> {
                AddHabitViewModel(mainRepository, sharedPreferences) as T
            }
            else -> {
                LoginViewModel(mainRepository, sharedPreferences) as T
            }
        }
    }
}