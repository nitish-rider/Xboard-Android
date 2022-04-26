package com.xboard.xboardandroid.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xboard.xboardandroid.utils.API
import kotlinx.coroutines.launch
import org.javacord.api.entity.message.MessageSet

class MainViewModel:ViewModel() {
    var myMessages : MutableLiveData<MessageSet> = MutableLiveData()

    fun getMessages(channelId : String){
        viewModelScope.launch {
            val channel = API.api.getTextChannelById(968512844276588594)
            channel.ifPresent{
                val messages = it.getMessages(20).get();
//                messages.forEach {mess->
//                    Log.d("mess",mess.toString() + mess.attachments)
//
//                }
                myMessages.value = messages
            }
        }
    }
}