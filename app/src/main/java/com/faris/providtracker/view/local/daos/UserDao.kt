package com.faris.providtracker.view.local.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.faris.providtracker.view.local.entities.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Query("select * from user where username=:username and password=:password")
    fun getUserByEmailAndPassword(username: String, password: String): LiveData<List<User>>

    @Query("select * from user where username=:username")
    fun getUserByUsername(username: String): LiveData<List<User>>
}