package com.xboard.xboardandroid.ui.fragment.register

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.xboard.xboardandroid.R
import com.xboard.xboardandroid.utils.API.api
import com.xboard.xboardandroid.utils.CONSTANTS.Server_ID
import com.xboard.xboardandroid.databinding.FragmentRegisterNameBinding
import java.util.*


class RegisterName : Fragment() {

    private var _binding: FragmentRegisterNameBinding? = null
    private val binding get() = _binding!!


    @SuppressLint("RestrictedApi")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterNameBinding.inflate(inflater, container, false)

        val sharedPreferences =
            requireActivity().getSharedPreferences("register", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val serverById = api.getServerById(Server_ID)
        serverById.ifPresent { server->
            Log.d("server",server.channelCategories.toString())
        }


        binding.registerNameBt.setOnClickListener {
            val name = binding.registerNameEt.text.toString()
            val otp = binding.registerOtp.text.toString()
            if (binding.textInputLayoutotp.isVisible) {
                if (name.isNotEmpty() && otp.isNotEmpty()) {
                    binding.textInputLayout.isErrorEnabled = false
                    binding.textInputLayoutotp.isErrorEnabled = false
                    editor.putString("registered_name", name.lowercase(Locale.getDefault()))
                    editor.putString("registered_otp",otp)
                    editor.apply()
                    findNavController().navigate(R.id.action_register_name2_to_homeFragment)
                } else {
                    if (name.isEmpty()) {
                        binding.textInputLayout.error = "Please enter a valid name"
                    }
                    if (otp.isEmpty()) {
                        binding.textInputLayoutotp.error = "Please enter a valid OTP"
                    }
                }
            } else {
                if (name.isNotEmpty()) {
                    binding.textInputLayout.isErrorEnabled = false
                    Toast.makeText(requireContext(), "Hello $name, please enter the otp from the desktop app....", Toast.LENGTH_LONG).show()
                    binding.textInputLayoutotp.visibility = View.VISIBLE
                } else {
                    binding.textInputLayout.error = "Please enter a valid name"
                }
            }
        }

        return binding.root
    }

}