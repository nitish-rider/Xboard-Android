package com.xboard.xboardandroid.ui.fragment.register

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import com.xboard.xboardandroid.databinding.FragmentRegisterNameBinding


class RegisterName : Fragment() {

    private var _binding: FragmentRegisterNameBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterNameBinding.inflate(inflater, container, false)

        val sharedPreferences =
            requireActivity().getSharedPreferences("register", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        binding.registerNameBt.setOnClickListener {
            val name = binding.registerNameEt.text.toString()
            val otp = binding.registerOtp.text.toString()
            if (binding.textInputLayoutotp.isVisible) {
                if (name.isNotEmpty() && otp.isNotEmpty()) {
                    binding.textInputLayout.isErrorEnabled = false
                    binding.textInputLayoutotp.isErrorEnabled = false
                    editor.putString("registered_name", name)
                    editor.apply()
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