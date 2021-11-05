package com.faris.providtracker.view.ui.activity.add_habit

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.faris.providtracker.R
import com.faris.providtracker.databinding.ActivityAddReminderBinding
import com.faris.providtracker.view.local.entities.Habit
import com.faris.providtracker.view.ui.ViewModelFactory
import com.faris.providtracker.view.utils.AlarmReceiver
import com.faris.providtracker.view.utils.DateUtil
import java.util.*

class AddHabitActivity : AppCompatActivity() {
    private lateinit var viewModel: AddHabitViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityAddReminderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreference = getSharedPreferences(getString(R.string.auth), MODE_PRIVATE)
        val viewModelFactory = ViewModelFactory(application, sharedPreference)
        viewModel = ViewModelProvider(this, viewModelFactory)[AddHabitViewModel::class.java]

        val c = Calendar.getInstance()
        val habit = intent.getParcelableExtra<Habit>(getString(R.string.habit))

        if (habit != null) {
            c.time = habit.dateTime
            binding.etName.setText(habit.name)
            binding.btnChooseDate.text =
                "${getString(R.string.selected_date)} ${DateUtil.formatDate(c.time)}"
            binding.btnChooseTime.text =
                "${getString(R.string.selected_time)} ${c.get(Calendar.HOUR_OF_DAY)}, ${
                    c.get(
                        Calendar.MINUTE
                    )
                }"
        }

        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        binding.apply {
            btnChooseDate.setOnClickListener {
                val datePickerDialog =
                    DatePickerDialog(this@AddHabitActivity, { _, year, monthOfYear, dayOfMonth ->
                        c.set(year, monthOfYear, dayOfMonth)
                        btnChooseDate.text =
                            "${getString(R.string.selected_date)} ${DateUtil.formatDate(c.time)}"
                    }, year, month, day)

                datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000
                datePickerDialog.show()
            }
            btnChooseTime.setOnClickListener {
                val timePickerDialog =
                    TimePickerDialog(
                        this@AddHabitActivity,
                        { _, selectedHour, selectedMinute ->
                            c.set(Calendar.HOUR_OF_DAY, selectedHour)
                            c.set(Calendar.MINUTE, selectedMinute)

                            btnChooseTime.text =
                                "${getString(R.string.selected_time)} $selectedHour, $selectedMinute"
                        }, hour, minute, true
                    )
                timePickerDialog.show()
            }
            btnSave.setOnClickListener {
                val name = etName.text.toString()

                if (name.isEmpty()
                    || btnChooseDate.text == getString(R.string.choose_date)
                    || btnChooseTime.text == getText(R.string.choose_time)
                ) {
                    Toast.makeText(
                        this@AddHabitActivity,
                        getString(R.string.please_fill_fields),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    val username = viewModel.getFromPref(getString(R.string.logged_in))
                    val id = habit?.id ?: 0
                    val newHabit = Habit(id, name, c.time, false, username!!)

                    viewModel.insertHabit(newHabit)
                    startAlarm(c, newHabit)

                    finish()
                }
            }
        }
    }

    private fun startAlarm(calendar: Calendar, habit: Habit) {
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

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }
}