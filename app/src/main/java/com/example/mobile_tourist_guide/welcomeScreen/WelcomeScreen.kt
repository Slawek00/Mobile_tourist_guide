package com.example.mobile_tourist_guide.welcomeScreen

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mobile_tourist_guide.R
import com.example.mobile_tourist_guide.databinding.FragmentWelcomeScreenBinding


class WelcomeScreen : Fragment() {
    private lateinit var binding: FragmentWelcomeScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // nadmuchiwanie layoutu fragmentu
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_welcome_screen, container, false
        )
        //ustawianie koloru przycisków
        binding.login.setBackgroundColor(Color.rgb(0,142,204))
        binding.register.setBackgroundColor(Color.rgb(0,142,204))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.register.setOnClickListener {
            navigateToRegister()
        }

        binding.login.setOnClickListener {
            navigateToLogin()
        }
    }


    private fun navigateToLogin(){
        findNavController().navigate(R.id.action_welcomeScreen_to_loginScreen)
    }


    private fun navigateToRegister(){
        findNavController().navigate(R.id.action_welcomeScreen_to_registerScreen)
    }

}

