package com.xboard.xboardandroid.adapter

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.xboard.xboardandroid.R
import java.net.URL
import java.util.concurrent.Executors

class MessageAdapter(private val messageList : List<Pair<String,String>>, context: FragmentActivity):RecyclerView.Adapter<MessageAdapter.MessageViewHolder>(){
    private val myClipBoard = context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager

    class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return MessageViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.message_rv_item,parent,false))
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        if(messageList[position].first != ""){
            holder.itemView.findViewById<TextView>(R.id.text).text = messageList[position].first
        }
        else{
            val executorService = Executors.newSingleThreadExecutor()
            val handler = Handler(Looper.getMainLooper())
            executorService.execute{
                val bitmap= BitmapFactory.decodeStream(URL(messageList[position].second).openStream())
                handler.post {
                    holder.itemView.findViewById<TextView>(R.id.text).visibility = View.GONE
                    holder.itemView.findViewById<ImageView>(R.id.image).visibility = View.VISIBLE
                    holder.itemView.findViewById<ImageView>(R.id.image).setImageBitmap(bitmap)
                }
            }
        }
        holder.itemView.findViewById<TextView>(R.id.text).setOnClickListener {
            val myClip = ClipData.newPlainText("text",holder.itemView.findViewById<TextView>(R.id.text).text)
            myClipBoard.setPrimaryClip(myClip)
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }
}