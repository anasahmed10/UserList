package com.example.userlist.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.userlist.R
import com.example.userlist.presenter.MainPresenter
import com.example.userlist.presenter.RecyclerAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment() {
     lateinit var recyclerView: RecyclerView
     lateinit var fab: FloatingActionButton
     private lateinit var mainPresenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainPresenter = MainPresenter(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_main, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = requireActivity().findViewById<RecyclerView>(R.id.recyclerView)
        fab = requireActivity().findViewById<FloatingActionButton>(R.id.create_fab)

        recyclerView.adapter = RecyclerAdapter(mainPresenter.totalUsers.value)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        fab.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.flContent, AddFragment()).addToBackStack(null).commit()
        }

    }

    override fun onStart() {
        super.onStart()

        mainPresenter.totalUsers.observe(viewLifecycleOwner, Observer { users ->
            recyclerView.adapter = RecyclerAdapter(users)
        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ExampleFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            MainFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

}