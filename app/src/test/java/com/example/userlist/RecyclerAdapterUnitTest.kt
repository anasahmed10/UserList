package com.example.userlist

import com.example.userlist.model.User
import com.example.userlist.presenter.RecyclerAdapter
import org.junit.Test
import org.junit.runner.JUnitCore
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

// Simple Unit Test to make sure that the recyclerAdapter is properly initializing
class RecyclerAdapterUnitTest {

    val userList: List<User> = listOf(User("testUser", "1234567890"))

    val recyclerAdapter: RecyclerAdapter = RecyclerAdapter(userList)

    @Test
    fun singleList() {
        assert(userList.size == 1)
        assert(recyclerAdapter.itemCount == 1)
    }
}