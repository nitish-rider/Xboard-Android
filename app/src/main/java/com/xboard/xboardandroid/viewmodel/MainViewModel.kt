package com.xboard.xboardandroid.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xboard.xboardandroid.utils.API
import kotlinx.coroutines.launch

class MainViewModel:ViewModel() {
    var myMessages : MutableLiveData<List<Pair<String,String>>> = MutableLiveData()

    fun getMessages(channelId : String){
        viewModelScope.launch {
            val channel = API.api.getTextChannelById(channelId)
            channel.ifPresent{
                val messages = it.getMessages(20).get()
                val msg : ArrayList<Pair<String,String>> = ArrayList()
                messages.forEach {mess->
                    if(mess.content.isNotEmpty()){
                        if(!mess.content.startsWith("#Android",true)){
                            msg.add(Pair(mess.content,""))
                        }
                        else if(mess.content.startsWith("#Android",true)){
                            val text = mess.content.substring(20)
                            msg.add(Pair(text,""))
                        }
                    }
                    else{
                        val attachments = mess.attachments
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