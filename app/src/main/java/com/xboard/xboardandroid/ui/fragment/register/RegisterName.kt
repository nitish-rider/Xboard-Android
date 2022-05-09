package com.xboard.xboardandroid.ui.fragment.register

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.xboard.xboardandroid.HomeActivity
import com.xboard.xboardandroid.R
import com.xboard.xboardandroid.databinding.FragmentRegisterNameBinding
import com.xboard.xboardandroid.utils.API
import com.xboard.xboardandroid.utils.API.api
import com.xboard.xboardandroid.utils.API.myChannelId
import com.xboard.xboardandroid.utils.API.verified
import com.xboard.xboardandroid.utils.CONSTANTS
import com.xboard.xboardandroid.utils.CONSTANTS.Server_ID
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*


class RegisterName : Fragment() {

    private var _binding: FragmentRegisterNameBinding? = null
    private val binding get() = _binding!!
    private lateinit var channelName: String


    @SuppressLint("RestrictedApi")
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

                    binding.registerNameBt.visibility = View.GONE
                    binding.animationLoading.visibility = View.VISIBLE
                    binding.textInputLayout.isErrorEnabled = false
                    binding.textInputLayoutotp.isErrorEnabled = false

                    editor.putString("registered_name", name.lowercase())
                    editor.putString("registered_otp", otp)
                    editor.apply()
                    channelName = name.lowercase() + otp
                    val serverById = api.getServerById(Server_ID)
                    serverById.ifPresent { server ->
                        val createTextChannelBuilder = server.createTextChannelBuilder()
                        createTextChannelBuilder.setName(channelName)
                        val channelCategoryById =
                            server.getChannelCategoryById(CONSTANTS.Category_ID)
                        channelCategoryById.ifPresent { category ->
                            createTextChannelBuilder.setCategory(category)
                            val channelData = createTextChannelBuilder.create()
                            editor.putString("Channel_id", channelData.get().id.toString())
                            editor.apply()
                            myChannelId = channelData.get().id.toString()
                            Log.d("channel", channelData.get().id.toString() + "added")
                            val channel =
                                api.getTextChannelById(myChannelId)
                            channel.ifPresent { textChannel ->
                                textChannel.sendMessage(otp)

                                val chann = api.getTextChannelById(API.myChannelId)
                                    chann.ifPresent{message->
                                        val messages = message.getMessages(2).get()
                                        messages.forEach { message ->
                                            if(message.content.contains("Connected",true)){
                                                Toast.makeText(requireContext(),"Verification Done",Toast.LENGTH_SHORT).show()
                                                verified = true
                                            }else{
                                                message.delete()
                                            }
                                        }
                                        lifecycleScope.launch{
                                            delay(2000)
                                            if(verified){
                                                startActivity(Intent(requireContext(), HomeActivity::class.java))
                                                requireActivity().finish()
                                            }else{
                                                findNavController().navigate(R.id.action_register_name_to_homeFragment)
                                            }
                                        }
                                    }
                            }
                        }
                    }
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
                    Toast.makeText(
                        requireContext(),
                        "Hello $name, please enter the otp from the desktop app....",
                        Toast.LENGTH_LONG
                    ).show()
                    binding.textInputLayoutotp.visibility = View.VISIBLE
                } else {
                    binding.textInputLayout.error = "Please enter a valid name"
                }
            }
        }
        return binding.root
    }

}