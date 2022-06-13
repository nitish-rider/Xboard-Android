package com.xboard.xboardandroid.viewmodel

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xboard.xboardandroid.utils.API
import kotlinx.coroutines.launch

class MainViewModel(context: Context):ViewModel() {
    var myMessages : MutableLiveData<List<Pair<String,String>>> = MutableLiveData()
    private val myClipBoard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager


    fun getMessages(channelId : String){
        viewModelScope.launch {
            val channel = API.api.getTextChannelById(channelId)
            channel.ifPresent{
                val messages = it.getMessages(20).get()
                val msg : ArrayList<Pair<String,String>> = ArrayList()
                messages.forEach {mess->
                    if(mess.content.isNotEmpty()){
                        when {
                            mess.content.equals("Connected") -> {
                                msg.add(Pair(mess.content,""))
                            }
                            mess.content.startsWith("#Windows",true) -> {
                                var text = mess.content.substring(20).trim()
                                val myClip = ClipData.newPlainText("text",text)
                                text = "Desktop->Android: $text"
                                msg.add(Pair(text,""))
                                myClipBoard.setPrimaryClip(myClip)
                            }
                            mess.content.startsWith("#Android",true) -> {
                                var text = mess.content.substring(20).trim()
                                text = "Android->Desktop: $text"
                                msg.add(Pair(text,""))
                            }
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