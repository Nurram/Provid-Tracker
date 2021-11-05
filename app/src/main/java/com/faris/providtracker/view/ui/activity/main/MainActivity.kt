package com.faris.providtracker.view.ui.activity.main

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.faris.providtracker.R
import com.faris.providtracker.view.ui.activity.dashboard.DashboardActivity
import com.faris.providtracker.view.ui.activity.login.LoginActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        Handler(Looper.getMainLooper()).postDelayed({
            val sharedPreference = getSharedPreferences(getString(R.string.auth), MODE_PRIVATE)
            val loggedInUser = sharedPreference.getString(getString(R.string.logged_in), "")

            if (loggedInUser.isNullOrBlank()) {
                val i = Intent(this, LoginActivity::class.java)
                startActivity(i)
                finish()
            } else {
                val i = Intent(this, DashboardActivity::class.java)
                i.putExtra(getString(R.string.username), loggedInUser)
                startActivity(i)
                finish()
            }
        }, 2000)
    }
}