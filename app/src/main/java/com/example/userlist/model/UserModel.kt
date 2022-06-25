package com.example.userlist.model

public class UserModel {
    private var userCount: Int = 0

    private var userList: List<User>? = null

    public fun getUserCount(): Int {
        return userCount
    }

    public fun setUserCount(newCount: Int) {
        this.userCount = newCount
    }

    public fun getUserList(): List<User>? {
        return userList
    }

    public fun getUserListAt(spot: Int): User? {
        return userList?.get(spot)
    }

    public fun setUserList(newUserList: List<User>) {
        this.userList = newUserList
    }

    interface OnFinishedListener {
        fun onResultSuccess(arrUpdates: List<User>)
        fun onResultFail(strError: String)
    }
}