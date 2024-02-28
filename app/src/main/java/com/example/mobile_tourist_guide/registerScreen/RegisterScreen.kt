package com.example.mobile_tourist_guide.registerScreen

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.mobile_tourist_guide.R
import com.example.mobile_tourist_guide.abstractClasses.BaseFragment
import com.example.mobile_tourist_guide.data.User
import com.example.mobile_tourist_guide.databinding.FragmentRegisterScreenBinding
import com.example.mobile_tourist_guide.network.FirebaseAPI
import com.google.android.material.snackbar.Snackbar


class RegisterScreen : BaseFragment() {

    private lateinit var binding: FragmentRegisterScreenBinding
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //nadmuchiwanie layoutu dla tego fragmentu
       binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_register_screen, container, false
        )

        //ustawienie linku do regulaminu
        binding.acceptReg.movementMethod = LinkMovementMethod.getInstance()

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //zaprogramowanie działania setOnClickListnerów przycisków fragmentu
        binding.reg.setOnClickListener{
            regButtonClick()
        }
    }


    //funkcja przekazująca dane z text inputów do viewmodelu, walidacja danych i rejstracja
    private fun regButtonClick(){
        val email = binding.textEmail.text.toString()
        val password = binding.textPasswordReg.text.toString()
        val repPass = binding.textPasswordRegRep.text.toString()
        val accept = binding.acceptReg.isChecked
        val name = binding.textUserName.text.toString()
        if (viewModel.dataValidation(email, password, repPass, accept) == 5){
            register(email, password, name)
        }
    }
    //pokazanie informacji zwrotnej użytkonikowi
    private fun showDialog(){
        Snackbar.make(requireView(), viewModel.makeMessage(),
            Snackbar.LENGTH_SHORT).show()
    }
    //rejstracja użytkownika w bazie
    private fun register(email: String, password: String, name: String){
        FirebaseAPI.authFirebase.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { authResult ->
                if(authResult.user!=null) {
                    val newUser = User(
                        authResult!!.user?.uid,
                        name,
                        listOf(),
                        email,
                        "",
                        "Polski"
                    )
                    viewModel.insertProfileUser(newUser)
                    goToCreateProfile()
                }
            }
            .addOnFailureListener{
                showDialog()
            }
    }
}