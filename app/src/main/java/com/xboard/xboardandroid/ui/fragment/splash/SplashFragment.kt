package com.xboard.xboardandroid.ui.fragment.splash

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.xboard.xboardandroid.R
import com.xboard.xboardandroid.databinding.FragmentSplashBinding
import com.xboard.xboardandroid.utils.API
import com.xboard.xboardandroid.utils.CONSTANTS
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        removeChannel()
        return binding.root

    }

    private fun removeChannel() {
        Log.d("channel", "inRemove")
        val sharedPref: SharedPreferences =
            requireActivity().getSharedPreferences("register", Context.MODE_PRIVATE)
        val serverById = API.api.getServerById(CONSTANTS.Server_ID)
        serverById.ifPresent { server ->
            val id = sharedPref.getString("Channel_id","")
            val channelById = server.getChannelById(id)
            channelById.ifPresent { channel ->
                channel.delete()
            }
        }
        lifecycleScope.launch {
            delay(3000)
            findNavController().navigate(R.id.action_splashFragment_to_register_name)
        }
    }
}