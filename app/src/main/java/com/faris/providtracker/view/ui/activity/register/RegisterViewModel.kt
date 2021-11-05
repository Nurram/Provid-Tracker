package com.faris.providtracker.view.ui.activity.register

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.faris.providtracker.view.local.entities.User
import com.faris.providtracker.view.repos.MainRepository
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val repository: MainRepository,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    fun getUserByEmail(username: String) = repository.getUserByUsername(username)

    fun insertUser(user: User) {
        viewModelScope.launch {
            repository.insertUser(user)
        }
    }

    fun saveToPref(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }
}