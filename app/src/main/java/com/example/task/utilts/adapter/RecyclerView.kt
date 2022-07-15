package com.example.task.utilts.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.task.R
import com.example.task.ResponseTamil
import com.example.task.activity.ViewActivity
import com.example.task.utilts.api.ApiClient
import com.example.task.utilts.exoPlayer.viewDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RecyclerView(private val context: Context,private val mList:ArrayList<viewDetails>):RecyclerView.Adapter<com.example.task.utilts.adapter.RecyclerView.ViewHolder>() {
    private lateinit var apiClient: ApiClient

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =LayoutInflater.from(context).inflate(R.layout.recycler_view,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        apiClient = ApiClient()
        var clickable:String=""
        val item = mList[position]
        holder.title.text = item.title
        holder.desc.text = item.descr
        Glide.with(context).load(item.image)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(40))).into(holder.image)
        if (item.isClickabe.equals("true")){
            holder.image.setOnClickListener{
                val image = item.image
                val title = item.title
                val descr = item.descr
                val intent = Intent(context, ViewActivity::class.java)
                intent.putExtra("IMAGE",image)
                intent.putExtra("TITLE",title)
                intent.putExtra("DESCR",descr)
                context.startActivity(intent)
            }
        }else if(item.isClickabe.equals("false")) {
            holder.image.setOnClickListener {
                Toast.makeText(context,"Its non-clickable content",Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun getItemCount(): Int {
        return mList.size
    }
    class ViewHolder(ItemView: View):RecyclerView.ViewHolder(ItemView) {
            val title:TextView =itemView.findViewById(R.id.tv_title)
            val desc:TextView=itemView.findViewById(R.id.tv_desc)
            val image:ImageView = itemView.findViewById(R.id.iv_image)

    }

    fun updateData(viewModels: ArrayList<viewDetails>) {
        mList.clear()
        mList.addAll(viewModels)
        notifyDataSetChanged()
    }


}


