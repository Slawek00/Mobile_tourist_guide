package com.example.mobile_tourist_guide.loginScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.mobile_tourist_guide.R
import com.example.mobile_tourist_guide.abstractClasses.BaseFragment
import com.example.mobile_tourist_guide.databinding.FragmentLoginScreenBinding
import com.example.mobile_tourist_guide.network.FirebaseAPI


class LoginScreen : BaseFragment() {
    private lateinit var binding: FragmentLoginScreenBinding
    private val viewModel: LoginViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //nadmuchiwanie layoutu fragmentu
        binding= DataBindingUtil.inflate(
            inflater, R.layout.fragment_login_screen, container, false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.sign.setOnClickListener{
            loginButtonClick()
        }
        binding.recover.setOnClickListener{
            recover()
        }
    }

    private fun loginButtonClick(){
        val email = binding.textLogin.text.toString()
        val password = binding.textPassword.text.toString()
        if(!viewModel.dataIsNotEmpty(email, password)) login(email, password) else showEmptyAlert()
    }

    private fun showEmptyAlert(){
        Toast.makeText(requireContext(),"Błąd logowania! Puste pola logowania. Wprowadź dane!",
            Toast.LENGTH_LONG).show()
    }

    private fun login(email: String, password: String){
        FirebaseAPI.authFirebase.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {authRes ->
                if(authRes.user!=null) goToActivity()
            }
            .addOnFailureListener {_ ->
                Toast.makeText(requireContext(),"Błąd logowania! Sprawdź poprawność danych lub zarejstruj się!",
                    Toast.LENGTH_LONG).show()
                binding.textLogin.text!!.clear()
                binding.textPassword.text!!.clear()
            }
    }

    private fun recover(){
        val email = binding.textLogin.text.toString()
        val password = binding.textPassword.text.toString()
        if(!viewModel.dataIsNotEmpty(email, password)){
            FirebaseAPI.authFirebase.sendPasswordResetEmail(email)
                .addOnSuccessListener {
                    Toast.makeText(requireContext(),"Wsyłano mail z linkiem resetującym hasło",
                        Toast.LENGTH_LONG).show()
                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(),"Nie ma takiego użytkownika!",
                        Toast.LENGTH_LONG).show()
                }
        }else{
            Toast.makeText(requireContext(),"Nie ma takiego użytkownika!",
                Toast.LENGTH_LONG).show()
        }
    }
}