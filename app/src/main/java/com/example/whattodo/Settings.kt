package com.example.whattodo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import androidx.navigation.fragment.NavHostFragment
import com.google.android.gms.common.SignInButton
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_settings.*


class Settings : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var btn_out: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentUser = auth.currentUser




        btn_out = requireView().findViewById(com.example.whattodo.R.id.sign_out_btn);
        btn_out.setOnClickListener {
            auth.signOut()
            val fragNavController = NavHostFragment.findNavController(this)
            fragNavController.navigate(com.example.whattodo.R.id.action_global_content)
        }
    }


}