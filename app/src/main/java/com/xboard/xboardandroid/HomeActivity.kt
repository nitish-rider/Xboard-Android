package com.xboard.xboardandroid

import android.content.ClipboardManager
import android.os.Bundle
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import com.xboard.xboardandroid.adapter.MessageAdapter
import com.xboard.xboardandroid.databinding.ActivityHomeBinding
import com.xboard.xboardandroid.utils.API.api
import com.xboard.xboardandroid.utils.API.myChannelId
import com.xboard.xboardandroid.viewmodel.MainViewModel
import kotlin.math.max
import kotlin.math.min

class HomeActivity : AppCompatActivity() {


    private lateinit var binding:ActivityHomeBinding
    private val mainViewModel = MainViewModel()
    private lateinit var mScaleGestureDetector: ScaleGestureDetector
    private var mScaleFactor = 1.0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        mScaleGestureDetector = ScaleGestureDetector(this, ScaleListener())

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
                val myClipBoard = this.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                val myClip = myClipBoard.primaryClip
                textChannel.sendMessage("#c ${myClip?.getItemAt(0)!!.text}")
                Toast.makeText(this,"Text Send to Desktop's Clipboard",Toast.LENGTH_SHORT).show()
            }
        }

        binding.sendToTxBt.setOnClickListener {
            val channel = api.getTextChannelById(myChannelId)
            channel.ifPresent{textChannel->
                val myClipBoard = this.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                val myClip = myClipBoard.primaryClip
                textChannel.sendMessage("#p ${myClip?.getItemAt(0)!!.text}")
                Toast.makeText(this,"Text Send to Desktop's Cursor",Toast.LENGTH_SHORT).show()
            }
        }

        binding.getSSBt.setOnClickListener {
            val channel = api.getTextChannelById(myChannelId)
            channel.ifPresent{textChannel->
                textChannel.sendMessage("s")
                Toast.makeText(this,"Requested Desktop's ScreenShot",Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onTouchEvent(motionEvent: MotionEvent): Boolean {
        mScaleGestureDetector.onTouchEvent(motionEvent)
        return true
    }

    private inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(scaleGestureDetector: ScaleGestureDetector): Boolean {
            mScaleFactor *= scaleGestureDetector.scaleFactor
            mScaleFactor = max(0.1f, min(mScaleFactor, 10.0f))
            binding.imageFS.scaleX = mScaleFactor
            binding.imageFS.scaleY = mScaleFactor
            return true
        }
    }
}