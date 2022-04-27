package com.xboard.xboardandroid.ui.fragment.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.xboard.xboardandroid.HomeActivity
import com.xboard.xboardandroid.databinding.FragmentHomeBinding
import com.xboard.xboardandroid.utils.API.api
import com.xboard.xboardandroid.utils.API.myChannelId
import com.xboard.xboardandroid.utils.CONSTANTS.Category_ID
import com.xboard.xboardandroid.utils.CONSTANTS.Server_ID


class HomeFragment : Fragment() {

    private lateinit var channelName:String
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    @SuppressLint("RestrictedApi")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val sharedPref: SharedPreferences =requireActivity().getSharedPreferences("register", Context.MODE_PRIVATE)
        val editor=sharedPref.edit()

        if(sharedPref.getString("registered_name","")!=""){
            val name=sharedPref.getString("registered_name","")
            val otp=sharedPref.getString("registered_otp","")
            channelName=name+otp
            val serverById = api.getServerById(Server_ID)
            serverById.ifPresent { server->
                val createTextChannelBuilder = server.createTextChannelBuilder()
                createTextChannelBuilder.setName(name+otp)
                val channelCategoryById = server.getChannelCategoryById(Category_ID)
                channelCategoryById.ifPresent { category->
                    createTextChannelBuilder.setCategory(category)
                    val channelData=createTextChannelBuilder.create()
                    editor.putString("Channel_id",channelData.get().id.toString())
                    editor.apply()
                    myChannelId = channelData.get().id.toString()
                    Log.d("channel",channelData.get().id.toString()+"added")
                    val channel = api.getTextChannelById(channelData.get().id.toString())
                    channel.ifPresent{textChannel->
                        textChannel.sendMessage(otp)
                        startActivity(Intent(requireContext(),HomeActivity::class.java))
                    }
                }
            }

//            api.addMessageCreateListener {
//                val channelId = it.channel.id.toString()
//                if(channelId == myChannelId){
//                    mainViewModel.getMessages(myChannelId)
//                }
//            }
//
//            mainViewModel.myMessages.observe(requireActivity()) {
//                binding.messageRv.adapter = MessageAdapter(it,requireActivity(),binding)
//            }
//
//            binding.sendToCbBt.setOnClickListener {
//                val channel = api.getTextChannelById(myChannelId)
//                channel.ifPresent{textChannel->
//                    textChannel.sendMessage("#c ${clipBoardText?.text}")
//                }
//            }
//
//            binding.sendToTxBt.setOnClickListener {
//                val channel = api.getTextChannelById(myChannelId)
//                channel.ifPresent{textChannel->
//                    textChannel.sendMessage("#p ${clipBoardText?.text}")
//                }
//            }
//
//            binding.getSSBt.setOnClickListener {
//                val channel = api.getTextChannelById(myChannelId)
//                channel.ifPresent{textChannel->
//                    textChannel.sendMessage("s")
//                }
//            }
        }
        return binding.root
    }


}