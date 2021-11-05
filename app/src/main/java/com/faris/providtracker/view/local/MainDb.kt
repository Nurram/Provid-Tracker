package com.faris.providtracker.view.local

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.faris.providtracker.view.local.daos.HabitDao
import com.faris.providtracker.view.local.daos.UserDao
import com.faris.providtracker.view.local.entities.Habit
import com.faris.providtracker.view.local.entities.User

@Database(entities = [User::class, Habit::class], version = 1)
abstract class MainDb : RoomDatabase() {
    abstract val userDao: UserDao
    abstract val habitDao: HabitDao

    companion object {
        @Volatile
        private var db: MainDb? = null

        fun getDb(application: Application): MainDb? {
            if (db == null) {
                synchronized(MainDb::class.java) {
                    if (db == null) {
                        db = Room.databaseBuilder(
                            application.applicationContext,
                            MainDb::class.java, "main_db"
                        )
                            .build()
                    }
                }
            }

            return db
        }
    }
}