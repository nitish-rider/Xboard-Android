package com.xboard.xboardandroid.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xboard.xboardandroid.utils.API
import kotlinx.coroutines.launch
import org.javacord.api.entity.message.MessageSet

class MainViewModel:ViewModel() {
    var myMessages : MutableLiveData<List<Pair<String,String>>> = MutableLiveData()

    fun getMessages(channelId : String){
        viewModelScope.launch {
            val channel = API.api.getTextChannelById(channelId)
            channel.ifPresent{
                val messages = it.getMessages(20).get();
                var msg : ArrayList<Pair<String,String>> = ArrayList()
                messages.forEach {mess->
                    if(mess.content.isNotEmpty()){
//                        Log.d("mess",mess.toString())
                        msg.add(Pair(mess.content,""))
                    }
                    else{
//                       Log.d("mess", mess.attachments.toString())
                        val attachments = mess.attachments
//                        Log.d("mess",attachments[0].url.toString())
                        msg.add(Pair("",attachments[0].url.toString()))
                    }

                }
                msg.reverse()
                Log.d("mess",msg.toString())
                myMessages.value = msg.toList()
            }
        }
    }
}