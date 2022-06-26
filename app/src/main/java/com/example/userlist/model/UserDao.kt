package com.example.userlist.model

import androidx.lifecycle.LiveData
import androidx.room.*

// Interface that is implemented by MainPresenter to push Users to LiveData
@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)

    @Query("Select * from userTable")
    fun getAllUsers(): LiveData<List<User>>

    @Update
    fun updateUser(user: User)

    @Delete
    fun deleteUser(user: User)

}