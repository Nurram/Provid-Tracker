package com.faris.providtracker.view.ui.activity.login

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.faris.providtracker.view.repos.MainRepository

class LoginViewModel(
    private val repository: MainRepository,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    fun getUserByEmailAndPassword(username: String, password: String) =
        repository.getUserByEmailAndPassword(username, password)

    fun saveToPref(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }
}