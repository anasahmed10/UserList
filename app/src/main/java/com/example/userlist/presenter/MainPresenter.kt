package com.example.userlist.presenter

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.userlist.model.User

// Presenter class that handles the database functions called in MainActivity
// Can either insert or delete users
class MainPresenter(application: Application)  {

    private val db : RoomSingleton = RoomSingleton.getInstance(application)
    internal val totalUsers : LiveData<List<User>> = db.userDao().getAllUsers()

    fun insert(user : User) {
        db.userDao().insertUser(user)
    }

    fun delete(user: User) {
        db.userDao().deleteUser(user)
    }

}