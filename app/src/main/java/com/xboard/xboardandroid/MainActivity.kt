package com.xboard.xboardandroid

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import com.xboard.xboardandroid.utils.CONSTANTS.BOT_KEY
import org.javacord.api.DiscordApi
import org.javacord.api.DiscordApiBuilder



class MainActivity : AppCompatActivity() {
    lateinit var api: DiscordApi
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

        api = DiscordApiBuilder()
            .setToken(BOT_KEY)
            .login().join()
        Log.d("Channel",api.channels.toString())
        api.addMessageCreateListener {event->
            event.channel.sendMessage("Hello")
        }
    }
}