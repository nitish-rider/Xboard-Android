package com.xboard.xboardandroid

import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.xboard.xboardandroid.Utils.API.api
import com.xboard.xboardandroid.Utils.CONSTANTS.Channel_ID
import com.xboard.xboardandroid.Utils.CONSTANTS.channelEvent
import org.javacord.api.DiscordApi
import kotlin.random.Random


@RequiresApi(Build.VERSION_CODES.N)
class MainActivity : AppCompatActivity() {
    var otp: String = ""
    var i=0
    override fun onDestroy() {
        super.onDestroy()
        api.disconnect()
    }

    override fun onStop() {
        super.onStop()
        api.disconnect()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        onReady(api)

        api.addMessageCreateListener {event->
            if(event.messageContent.equals(otp)){
                Channel_ID=event.channel.id.toString()
                channelEvent=event
                println("Login Done")
                onLogin()
            }
        }

    }

    fun onReady(api: DiscordApi) {
        otp = Random.nextInt(1000, 9999).toString()
        val dialog= Dialog(this)
        dialog.setContentView(R.layout.alert_dialog)
        dialog.findViewById<TextView>(R.id.otp).text=otp
        dialog.findViewById<Button>(R.id.dialogButtonCopy).setOnClickListener {
            val clipBoardManager: ClipboardManager?= ContextCompat.getSystemService(
                this,
                CLIPBOARD_SERVICE::class.java
            ) as ClipboardManager?
            val clipData= ClipData.newPlainText("otp",otp)
            clipBoardManager?.setPrimaryClip(clipData)
            dialog.dismiss()
        }
        dialog.show()
    }
    fun onLogin(){
        channelEvent?.channel?.sendMessage("Hello There!!!")
    }
}