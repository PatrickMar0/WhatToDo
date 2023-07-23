package com.example.whattodo

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.whattodo.adapter.EventAdapter
import com.example.whattodo.data.DataSource
import com.example.whattodo.databinding.FragmentContentBinding
import com.example.whattodo.model.Events

class Content : Fragment() {

    private var _binding: FragmentContentBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView

    private companion object LoginActivity {
        private const val TAG = "LoginActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentContentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        // call the function to get the data from firestore
        // pass setRecyclerViewAdapter as callback function
        DataSource().getEvents(fun(events: List<Events>) {
            recyclerView.adapter = EventAdapter(requireContext(), events)
        })

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}