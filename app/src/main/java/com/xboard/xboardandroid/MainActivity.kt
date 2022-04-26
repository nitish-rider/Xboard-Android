package com.xboard.xboardandroid

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.xboard.xboardandroid.utils.API.api
import com.xboard.xboardandroid.utils.CONSTANTS.Server_ID


@RequiresApi(Build.VERSION_CODES.N)
class MainActivity : AppCompatActivity() {
    //    var otp: String = ""
//    var i=0
//    override fun onDestroy() {
//        super.onDestroy()
//        api.disconnect()
//    }
//
//    override fun onStop() {
//        super.onStop()
//        api.disconnect()
//    }


    private fun removeChannel() {
        Log.d("channel", "inRemove")
        val sharedPref: SharedPreferences =
            this.getSharedPreferences("register", Context.MODE_PRIVATE)
        val serverById = api.getServerById(Server_ID)
        serverById.ifPresent { server ->
            val id = sharedPref.getString("Channel_id","")
            val channelById = server.getChannelById(id)
            channelById.ifPresent { channel ->
                channel.delete()
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Navigation.findNavController(this, R.id.nav_host_fragment)
        setupActionBarWithNavController(findNavController(R.id.nav_host_fragment))

        removeChannel()


//        println(api.channels)
//        for(cha in api.channels){
//            Log.d("event",cha.toString())
//        }


//        onReady(api)
        //930088852826234970
//
//        var channel=api.getTextChannelById("840446893216497726")
//        channel.ifPresent { textChannel->
//            textChannel.sendMessage("4298")
//            var chat=textChannel.getMessages(10).thenApply{chatSet->
//             for(singleChat in chatSet){
//                 Log.d("event",singleChat.toString())
//             }
//            }
//
//        }

//        api.addMessageCreateListener {event->
//            if(event.messageContent.equals(otp)){
//                Channel_ID=event.channel.id.toString()
//                channelEvent=event
//                println("Login Done")
//                println(channelEvent.toString())
//                Log.d("event",channelEvent.toString())
//                //org.javacord.core.event.message.MessageCreateEventImpl@41a2ce
//            }
//        }


    }
//
//    override fun onSupportNavigateUp(): Boolean {
//        val navController = findNavController(R.id.nav_host_fragment)
//        return navController.navigateUp() || super.onSupportNavigateUp()
//    }

//    fun onReady(api: DiscordApi) {
//        otp = Random.nextInt(1000, 9999).toString()
//        val dialog= Dialog(this)
//        dialog.setContentView(R.layout.alert_dialog)
//        dialog.findViewById<TextView>(R.id.otp).text=otp
//        dialog.findViewById<Button>(R.id.dialogButtonCopy).setOnClickListener {
//            val clipBoardManager: ClipboardManager?= ContextCompat.getSystemService(
//                this,
//                CLIPBOARD_SERVICE::class.java
//            ) as ClipboardManager?
//            val clipData= ClipData.newPlainText("otp",otp)
//            clipBoardManager?.setPrimaryClip(clipData)
//            dialog.dismiss()
//            onLogin()
//        }
//        dialog.show()
//    }
//    fun onLogin(){
//        channelEvent?.channel?.sendMessage("Hello There!!!")
//    }
}