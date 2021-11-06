package com.faris.providtracker.view.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.util.Log
import com.faris.providtracker.R

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        var habitName = intent.getStringExtra(context.getString(R.string.habit))
        val email = intent.getStringExtra(context.getString(R.string.email))

        if (habitName == null) {
            habitName = "null"
        }

        val sharedPreferences =
            context.getSharedPreferences(context.getString(R.string.auth), MODE_PRIVATE)
        val loggedInEmail = sharedPreferences.getString(context.getString(R.string.logged_in), "")
        if (loggedInEmail!! == email || email == "all") {
            val notificationUtils = NotificationUtil(context)
            val notification = notificationUtils.getNotificationBuilder(habitName).build()
            notificationUtils.getManager().notify(150, notification)
        }
    }
}