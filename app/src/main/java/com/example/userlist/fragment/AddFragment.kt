package com.example.userlist.fragment

import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.telephony.PhoneNumberUtils
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import com.example.userlist.R
import com.example.userlist.model.User
import com.example.userlist.presenter.MainPresenter
import kotlinx.coroutines.launch


/**
 * A simple [Fragment] subclass.
 * Use the [AddFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddFragment : Fragment() {
    lateinit var users: LiveData<List<User>>
    lateinit var userEditText: EditText
    lateinit var phoneEditText: EditText
    lateinit var addButton: Button
    lateinit var mainPresenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_add, container, false)
        userEditText = view.findViewById(R.id.editTextUsername)
        phoneEditText = view.findViewById(R.id.editTextPhone)
        addButton = view.findViewById(R.id.add_user_button)

        mainPresenter = MainPresenter(requireActivity().application)

        // Originally was supposed to automatically format the phone number
        // Did not work
        phoneEditText.addTextChangedListener(PhoneNumberFormattingTextWatcher())

        setHasOptionsMenu(true)
        setupBackButton()
        return view
    }

    override fun onStart() {
        super.onStart()


        addButton.setOnClickListener {
            if(fieldCheck()) {
                lifecycleScope.launch {
                    val newUser: User = User(userEditText.text.toString(), formatPhoneNumber(phoneEditText.text.toString()))
                    mainPresenter.insert(newUser)
                }
                // requireActivity().supportFragmentManager.popBackStack()
                requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
            }
            }
            // requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
    }

    // this event will enable the back function to the button on press
    fun onSupportNavigateUp() {
        requireActivity().onBackPressed()
    }

    private fun setupBackButton() {
        if (activity is AppCompatActivity) {
            requireActivity().actionBar?.title = "Add User"
        }
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