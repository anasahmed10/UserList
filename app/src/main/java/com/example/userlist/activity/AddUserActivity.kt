package com.example.userlist.activity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.userlist.R
import com.example.userlist.model.User
import com.example.userlist.presenter.MainPresenter
import kotlinx.coroutines.launch

class AddUserActivity : AppCompatActivity() {
    lateinit var userEditText: EditText
    lateinit var phoneEditText: EditText
    lateinit var addButton: Button
    lateinit var mainPresenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user)
        userEditText = findViewById(R.id.editTextUsernameActivity)
        phoneEditText = findViewById(R.id.editTextPhoneActivity)
        addButton = findViewById(R.id.add_user_button_activity)

        mainPresenter = MainPresenter(application)

        addButton.setOnClickListener {
            if(fieldCheck()) {
                lifecycleScope.launch {
                    val newUser: User = User(userEditText.text.toString(), phoneEditText.text.toString())
                    mainPresenter.insert(newUser)
                    finish()
                }
                /* val intent: Intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent) */
            }
        }

        setupBackButton()
    }

    // For avoiding runtime errors the LiveData Observers need to be removed onStop and onDestroy
    override fun onStop() {
        mainPresenter.totalUsers.removeObservers(this)
        super.onStop()
    }

    override fun onDestroy() {
        mainPresenter.totalUsers.removeObservers(this)
        super.onDestroy()
    }

    private fun setupBackButton() {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun fieldCheck() : Boolean {
        var isFilled = true
        if(userEditText.text.toString().equals("", true)) {
            userEditText.setError("Please enter a username")
            isFilled = false
        }
        if(phoneEditText.text.toString().equals("", true)) {
            phoneEditText.setError("Please enter a phone number")
            isFilled = false
        }
        return isFilled
    }
}