package com.example.userlist.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import com.example.userlist.R
import com.example.userlist.fragment.MainFragment
import com.example.userlist.model.User
import com.example.userlist.presenter.RoomSingleton

class FragActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frag)
    }

    override fun onStart() {
        super.onStart()
        supportFragmentManager.beginTransaction()
            .replace(R.id.flContent, MainFragment())
            .addToBackStack(null)
            .commit()
    }

    companion object{
        private var INSTANCE: RoomSingleton? = null
        fun getInstance(context: Context): RoomSingleton{
            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context,
                    RoomSingleton::class.java,
                    "roomdb")
                    .build()
            }

            return INSTANCE as RoomSingleton
        }
    }

}

interface MainView {
    fun showProgress()
    fun hideProgress()
    fun setData(arrUpdates: List<User>)
    fun setDataError(strError: String)
}