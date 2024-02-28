package com.example.mobile_tourist_guide.registerScreen

import androidx.lifecycle.ViewModel
import com.example.mobile_tourist_guide.data.User
import com.example.mobile_tourist_guide.repository.FirebaseRepository
import java.util.regex.Pattern

class RegisterViewModel: ViewModel() {

    private val repository = FirebaseRepository()
    var flag = 0

    private fun validationEmailReg(email: String): Boolean{
        val dictionary = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9+_.-]+\\.[A-Za-z]{2,6}\$"
        )
        val matcher = dictionary.matcher(email)
        return matcher.matches()
    }


    private fun validationPasswordReg(password: String): Boolean{
        if (password.filter { it.isLetter() }.firstOrNull { it.isLowerCase() } == null) return false
        if (password.firstOrNull { it.isDigit() } == null) return false
        if (password.length < 8) return false
        if (password.filter { it.isLetter() }.firstOrNull { it.isUpperCase() } == null) return false
        if (password.firstOrNull { !it.isLetterOrDigit() } == null) return false

        return true
    }


    private fun dataIsNotEmpty(email: String, password: String, repPass: String): Boolean{
        return email.isEmpty() || password.isEmpty() || repPass.isEmpty()
    }

    private fun checkPassword(password: String, repPass: String): Boolean {
        return password==repPass
    }


    fun dataValidation(email: String, password: String, repPass: String, checked: Boolean):Int{
        if (!dataIsNotEmpty(email, password, repPass)) ++flag else return flag
        if (validationEmailReg(email)) ++flag else return flag
        if (validationPasswordReg(password)) ++flag else return flag
        if (checkPassword(password, repPass)) ++flag else return flag
        if (checked) ++flag else return flag
        return flag
    }


    fun makeMessage():String{
        when(flag){
            0 -> return "Błąd rejstracji! Uzupełnij dane!"
            1 -> return "Błąd rejstracji! Nieprawidłowy email!"
            2 -> return "Błąd rejstracji! Hasło musi zawirać duże," +
                    " małe litery, cyfry oraz znaki specjalne!"
            3 -> return "Błąd rejstracji! Hasła nie są takie same!"
            4 -> return  "Błąd rejstracji! Niezakceptowano regulaminu!"
        }
        return "Już zarejstrowano. Zaloguj się!"
    }


    fun insertProfileUser(user: User){
        repository.insertProfileUser(user)
    }


    init{
        flag = 0
    }
}