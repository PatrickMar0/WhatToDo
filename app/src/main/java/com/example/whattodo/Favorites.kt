package com.example.whattodo

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.whattodo.adapter.EventAdapter
import com.example.whattodo.data.DataSource
import com.example.whattodo.databinding.FragmentFavoritesBinding
import com.example.whattodo.model.Events
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Favorites : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var  auth: FirebaseAuth
    private var user: FirebaseUser? = null
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        val view = binding.root

        //Get User
        auth = FirebaseAuth.getInstance()
        user = auth?.currentUser

        //If the user in null, navigate to the login fragment
        navController = findNavController()
        if (user == null) {
            navController.navigate(R.id.action_global_loginFragment)
        }


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        var myDataset = emptyList<Events>()
        //If the user in null, navigate to the login fragment
        navController = findNavController()
        if (user == null) {
            navController.navigate(R.id.action_global_loginFragment)
        }
        else {
            DataSource().getUser(user!!.uid) {
                Log.d("Loading: ", "Loading Favorites")
                Log.d("User Favorites: ", it.favorites.toString())
                DataSource().loadFavoriteEvents(it, fun(events: List<Events>) {
                    Log.d("onViewCreated: ", events.toString())
                    recyclerView.adapter = EventAdapter(requireContext(), events)
                })
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}