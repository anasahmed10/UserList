package com.example.userlist.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userTable")
data class User(
    @PrimaryKey
    @ColumnInfo(name = "user")
    var userName: String,

    @ColumnInfo(name = "phone")
    var phoneNumber: String
    )