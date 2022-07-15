package com.example.task.hindi

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.task.R
import com.example.task.ResponseTamil
import com.example.task.databinding.ActivityHindiBinding
import com.example.task.utilts.Service
import com.example.task.utilts.api.ApiClient
import com.example.task.utilts.adapter.RecyclerView
import com.example.task.utilts.exoPlayer.viewDetails
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HindiActivity : AppCompatActivity() {
    private lateinit var apiClient: ApiClient
    private lateinit var binding: ActivityHindiBinding
    private lateinit var adapter: RecyclerView
    private var mList = ArrayList<viewDetails>()
    private var recyclerView: androidx.recyclerview.widget.RecyclerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        startService(Intent(this, Service::class.java))

        binding = ActivityHindiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        apiClient = ApiClient()
        val post = intent.getStringExtra("HINDI")
        binding.tvPost.text = post
        recyclerView = binding.recyclerView
        recyclerView?.layoutManager = GridLayoutManager(this, 2)
        getDetails()
        adapter = RecyclerView(this, mList)
        recyclerView?.adapter = adapter
    }

    fun getDetails() {

        apiClient.getClient().hindiDetails()?.enqueue(object : Callback<ResponseTamil?> {
            override fun onResponse(
                call: Call<ResponseTamil?>,
                response: Response<ResponseTamil?>
            ) {
                //1 Create a Coroutine scope using a job to be able to cancel when needed
                val mainActivityJob = Job()

                //2 Handle exceptions if any
                val errorHandler = CoroutineExceptionHandler { _, exception ->
                    AlertDialog.Builder(this@HindiActivity).setTitle("Error")
                        .setMessage(exception.message)
                        .setPositiveButton("Ok") { _, _ -> }
                        .setIcon(R.drawable.ic_launcher_foreground).show()
                }
                lifecycleScope.launch(Dispatchers.Default){

                    val resp = response.body()
                    val userData = resp!!.userData
                    if (response.isSuccessful) {
                        for (i in resp.userData!!.indices){
                            val title = resp.userData[i]?.title.toString()
                            val desc = resp.userData[i]?.discription.toString()
                            val image = resp.userData[i]?.postUrl.toString()
                            val clickable = resp.userData[i]?.isClickable.toString()
                            mList.add(
                                viewDetails(
                                    title,
                                    desc,
                                    image,
                                    clickable
                                )
                            )
                            Log.d("TAG", "onResponse:"+title)
                            Log.d("TAG", "onResponse:"+desc)


                        }


                    }
                }

                adapter.updateData(mList)
                adapter.notifyDataSetChanged()







            }

            override fun onFailure(call: Call<ResponseTamil?>, t: Throwable) {
                call.cancel()
                Log.d("TAG", t.message.toString())

            }

        })


    }
    override fun onDestroy() {
        super.onDestroy()
//        val notificationManager =
//            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        notificationManager.cancelAll()
    }

}