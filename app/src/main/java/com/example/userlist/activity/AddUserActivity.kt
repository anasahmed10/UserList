package com.example.userlist.activity

import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
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


        setupBackButton()

        mainPresenter = MainPresenter(application)

        // Attempts to create a User using the text field and insert it into LiveData
        addButton.setOnClickListener {
            if(fieldCheck()) {
                // Uses a coroutine to add to the LiveData asynchronously
                lifecycleScope.launch {
                    val newUser: User = User(userEditText.text.toString(), formatPhoneNumber(phoneEditText.text.toString()))
                    mainPresenter.insert(newUser)
                }
                finish()
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

    // this event will enable the back function to the button on press
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setupBackButton() {
        // Displays "Add User" as title
        supportActionBar?.title = "Add User"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    // Checks editText fields to make sure they are valid
    // Username should be not blank and contain no spaces
    // Phone Number should be 10 digits
    private fun fieldCheck() : Boolean {
        var isFilled = true
        if(userEditText.text.toString().equals("", true)) {
            userEditText.setError("Please enter a username")
            isFilled = false
        }
        if(userEditText.text.toString().contains(" ", ignoreCase = true)) {
            userEditText.setError("Please enter a username with no spaces")
            isFilled = false
        }
        if(phoneEditText.text.toString().equals("", true)) {
            phoneEditText.setError("Please enter a phone number")
            isFilled = false
        }
        if(phoneEditText.text.toString().length != 10) {
            phoneEditText.setError("Please enter a 10 digit number")
            isFilled = false
        }
        if(phoneEditText.text.toString().contains("-") || phoneEditText.text.toString().contains(" ") || phoneEditText.text.toString().contains("*") || phoneEditText.text.toString().contains("#")) {
            phoneEditText.setError("Please only enter numbers")
            isFilled = false
        }
        return isFilled
    }

    // Formats phone number to proper format
    private fun formatPhoneNumber(phone: String): String {
        var newPhone : String = phone
        newPhone = StringBuilder(newPhone).insert(3, "-").toString()
        newPhone = StringBuilder(newPhone).insert(7, "-").toString()

        return newPhone
    }
}