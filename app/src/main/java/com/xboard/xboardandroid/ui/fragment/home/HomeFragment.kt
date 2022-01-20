package com.xboard.xboardandroid.ui.fragment.home

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.xboard.xboardandroid.R
import com.xboard.xboardandroid.Utils.API.api
import com.xboard.xboardandroid.databinding.FragmentHomeBinding
import com.xboard.xboardandroid.databinding.FragmentRegisterNameBinding


class HomeFragment : Fragment() {

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onDestroy() {
        removeChannel()
        super.onDestroy()
    }

    override fun onStop() {
        super.onStop()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun removeChannel() {
        val serverById = api.getServerById("840446892638863382")

        serverById.ifPresent { server->
//            Log.d("server",channelName)
            Log.d("server",server.getChannelsByName(channelName).toString())
        }
    }
    private lateinit var channelName:String
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val sharedPref: SharedPreferences =requireActivity().getSharedPreferences("register", Context.MODE_PRIVATE)
        if(sharedPref.getString("registered_name","")!=""){
            val name=sharedPref.getString("registered_name","")
            val otp=sharedPref.getString("registered_otp","")
            channelName=name+otp
            val serverById = api.getServerById("840446892638863382")
            serverById.ifPresent { server->
                val createTextChannelBuilder = server.createTextChannelBuilder()
                createTextChannelBuilder.setName(name+otp)
                val channelCategoryById = server.getChannelCategoryById("933694087138267187")
                channelCategoryById.ifPresent { category->
                    createTextChannelBuilder.setCategory(category)
                    createTextChannelBuilder.create()
//                    removeChannel()
                }
            }
        }
        return binding.root
    }


}