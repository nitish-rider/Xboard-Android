package com.xboard.xboardandroid.ui.fragment.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.xboard.xboardandroid.HomeActivity
import com.xboard.xboardandroid.databinding.FragmentHomeBinding
import com.xboard.xboardandroid.utils.API.api
import com.xboard.xboardandroid.utils.API.myChannelId


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    @SuppressLint("RestrictedApi")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = FragmentHomeBinding.inflate(inflater, container, false)



        val channel = api.getTextChannelById(myChannelId)
        channel.ifPresent{
            val messages = it.getMessages(2).get()
            messages.forEach { message ->
                if(message.content.equals("Touch the Camera emoji in this message to take a ss",true)){
                    message.delete()
                    Toast.makeText(requireContext(),"Verification Done",Toast.LENGTH_SHORT).show()
                    startActivity(Intent(requireContext(),HomeActivity::class.java))
                    requireActivity().finish()
                }
                else{
                    binding.animationViewError.playAnimation()
                }
            }
        }
//            api.addMessageCreateListener {event->
//                val channelId = event.channel.id.toString()
//                if(channelId == myChannelId){
//                    if(event.messageContent.equals("Touch the Camera emoji in this message to take a ss",true)){
//                        event.deleteMessage()
//                    }
//                }
//            }
//
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

        return binding.root
    }


}