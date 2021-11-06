package com.faris.providtracker.view.ui.activity.register

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.faris.providtracker.R
import com.faris.providtracker.databinding.ActivityRegisterBinding
import com.faris.providtracker.view.local.entities.Habit
import com.faris.providtracker.view.local.entities.User
import com.faris.providtracker.view.ui.ViewModelFactory
import com.faris.providtracker.view.ui.activity.dashboard.DashboardActivity
import com.faris.providtracker.view.ui.activity.login.LoginActivity
import com.faris.providtracker.view.utils.AlarmReceiver
import java.util.*

class RegisterActivity : AppCompatActivity() {
    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreference = getSharedPreferences(getString(R.string.auth), MODE_PRIVATE)
        val viewModelFactory = ViewModelFactory(application, sharedPreference)
        viewModel = ViewModelProvider(this, viewModelFactory)[RegisterViewModel::class.java]

        binding.apply {
            btnRegister.setOnClickListener {
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()
                val confPassword = etConfirmPassword.text.toString()

                var users: List<User> = listOf()
                viewModel.getUserByEmail(email)?.observe(this@RegisterActivity) { users = it }

                if (email.isBlank() || password.isBlank() || confPassword.isBlank()) {
                    makeToast(getString(R.string.please_fill_fields))
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    makeToast(getString(R.string.enter_valid_email))
                } else if (password.length < 6) {
                    makeToast(getString(R.string.password_short))
                } else if (password != confPassword) {
                    makeToast(getString(R.string.password_not_match))
                } else if (users.isNotEmpty()) {
                    makeToast(getString(R.string.email_used))
                } else {
                    viewModel.insertUser(User(email.lowercase(), password))
                    viewModel.saveToPref(getString(R.string.logged_in), email)

                    val habits = viewModel.initHabit()
                    var currentDate = habits[0].dateTime

                    for(habit in habits) {
                        val c = Calendar.getInstance()
                        c.time = currentDate
                        c.add(Calendar.HOUR_OF_DAY, 1)
                        habit.dateTime = c.time
                        currentDate = c.time

                        viewModel.insertHabit(habit)
                        startRepeatingAlarm(habit)
                    }

                    val i = Intent(this@RegisterActivity, DashboardActivity::class.java)
                    startActivity(i)
                    finish()
                }
            }
            btnLogin.setOnClickListener {
                val i = Intent(this@RegisterActivity, LoginActivity::class.java)
                startActivity(i)
                finish()
            }
        }
    }

    private fun makeToast(string: String) {
        Toast.makeText(
            this@RegisterActivity,
            string,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun startRepeatingAlarm(habit: Habit) {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java)
        intent.putExtra(getString(R.string.habit), habit.name)
        intent.putExtra(getString(R.string.email), habit.email)

        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.getBroadcast(
                this,
                habit.id,
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
        } else {
            PendingIntent.getBroadcast(this, habit.id, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        if(habit.dateTime.time <= System.currentTimeMillis()) {
            habit.dateTime.time += (AlarmManager.INTERVAL_DAY + 1)
        }

        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            habit.dateTime.time,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }
}