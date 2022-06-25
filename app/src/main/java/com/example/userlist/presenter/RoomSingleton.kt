package com.example.userlist.presenter

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.example.userlist.model.User
import com.example.userlist.model.UserDao


@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class RoomSingleton : RoomDatabase(){
    abstract fun userDao():UserDao

    companion object{
        private var INSTANCE: RoomSingleton? = null
        fun getInstance(context:Context): RoomSingleton{
            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context,
                    RoomSingleton::class.java,
                    "roomdb")
                    .allowMainThreadQueries()
                    .build()
            }

            return INSTANCE as RoomSingleton
        }
    }
}