package com.xboard.xboardandroid

import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.xboard.xboardandroid.adapter.MessageAdapter
import com.xboard.xboardandroid.databinding.ActivityHomeBinding
import com.xboard.xboardandroid.utils.API.api
import com.xboard.xboardandroid.utils.API.myChannelId
import com.xboard.xboardandroid.viewmodel.MainViewModel

class HomeActivity : AppCompatActivity() {

    private lateinit var binding:ActivityHomeBinding
    private val mainViewModel = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val myClipBoard = this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipBoardText = myClipBoard.primaryClip?.getItemAt(0)

        binding.messageRv.layoutManager = LinearLayoutManager(this)

        api.addMessageCreateListener {
            val channelId = it.channel.id.toString()
            if(channelId == myChannelId){
                mainViewModel.getMessages(myChannelId)
            }
        }

        mainViewModel.myMessages.observe(this) {
            binding.messageRv.adapter = MessageAdapter(it,this,binding)
        }

        binding.sendToCbBt.setOnClickListener {
            val channel = api.getTextChannelById(myChannelId)
            channel.ifPresent{textChannel->
                textChannel.sendMessage("#c ${clipBoardText?.text}")
            }
        }

        binding.sendToTxBt.setOnClickListener {
            val channel = api.getTextChannelById(myChannelId)
            channel.ifPresent{textChannel->
                textChannel.sendMessage("#p ${clipBoardText?.text}")
            }
        }

        binding.getSSBt.setOnClickListener {
            val channel = api.getTextChannelById(myChannelId)
            channel.ifPresent{textChannel->
                textChannel.sendMessage("s")
            }
        }


    }
}