package com.example.mobile_tourist_guide.settingsFragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.mobile_tourist_guide.R
import com.example.mobile_tourist_guide.abstractClasses.BaseFragment
import com.example.mobile_tourist_guide.databinding.FragmentSettingsBinding
import java.io.File


class SettingsFragment : BaseFragment() {
    private lateinit var binding: FragmentSettingsBinding
    private val viewModel: SettingsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_settings, container, false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imageBack.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_profileFragment, null, NavOptions.Builder()
                .setPopUpTo(R.id.mainFragment, true)
                .build())
        }

        binding.changeName.setOnClickListener{
            changeName()
        }

        binding.changeImage.setOnClickListener{
           goToChangeImage()
        }

        binding.report.setOnClickListener{
            report()
        }

        binding.not.setOnClickListener{
            info("Powiadomienia")
        }

        binding.anim.setOnClickListener{
            info("Animacje")
        }

        binding.language.setOnClickListener{
            info("Zmień język")
        }

        binding.logout.setOnClickListener{
            viewModel.logout()
            goToRegister()
        }

        binding.deleteAccount.setOnClickListener {
            viewModel.delete()
            goToRegister()
            File(context?.cacheDir!!.path).deleteRecursively()
        }

    }

    private fun changeName(){
        val alertDialog = AlertDialog.Builder(requireContext())
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.alert_change_name, null)
        val editText = dialogLayout.findViewById<EditText>(R.id.newName)

        with(alertDialog){
            setTitle("Zmień nazwę")
            setPositiveButton("Akceptuj"){ _, _ ->
                val newName = editText.text.toString()
                if(newName.isNotEmpty()){
                    viewModel.updateName(newName)
                }
            }
            setNegativeButton("Anuluj"){ _, _ ->

            }
            setView(dialogLayout)
            show()
        }
    }

    private fun report(){
        val alertDialog = AlertDialog.Builder(requireContext())
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.alert_report, null)
        val editText = dialogLayout.findViewById<EditText>(R.id.reportAlert)

        with(alertDialog){
            setTitle("Zgłoś błędy")
            setPositiveButton("Akceptuj"){ _, _ ->
                val report = editText.text.toString()
                if(report.isNotEmpty()){
                    viewModel.report(report)
                }
            }
            setNegativeButton("Anuluj"){ _, _ ->

            }
            setView(dialogLayout)
            show()
        }
    }

    private fun info(tittle: String){
        val alertDialog = AlertDialog.Builder(requireContext())
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.alert_language, null)

        with(alertDialog){
            setTitle(tittle)
            setPositiveButton("Akceptuj"){ _, _ ->

            }
            setNegativeButton("Anuluj"){ _, _ ->

            }
            setView(dialogLayout)
            show()
        }
    }
}