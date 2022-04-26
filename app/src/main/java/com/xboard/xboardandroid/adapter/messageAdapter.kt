package com.xboard.xboardandroid.adapter

import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.xboard.xboardandroid.R
import java.net.URL
import java.util.concurrent.Executors

class MessageAdapter(private val messageList : List<Pair<String,String>>):RecyclerView.Adapter<MessageAdapter.MessageViewHolder>(){
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
                    holder.itemView.findViewById<ImageView>(R.id.image).setImageBitmap(bitmap)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }
}