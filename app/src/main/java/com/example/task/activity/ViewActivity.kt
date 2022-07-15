package com.example.task.activity

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.task.databinding.ActivityViewBinding
import com.example.task.utilts.Service
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.util.Util


const val STATE_RESUME_WINDOW = "resumeWindow"
const val STATE_RESUME_POSITION = "resumePosition"
const val STATE_PLAYER_FULLSCREEN = "playerFullscreen"
const val STATE_PLAYER_PLAYING = "playerOnPlay"
const val USER_AGENT = "ExoPlayer-Drm"
const val DRM_LICENSE_URL = "https://proxy.uat.widevine.com/proxy?provider=widevine_test"


class ViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityViewBinding
    private lateinit var thumbnail:ImageView
    private lateinit var dataSourceFactory: DataSource.Factory
    private lateinit var playerView: PlayerView
    private var exoPlayer: ExoPlayer? = null
    private var playbackPosition = 0L
    private var playWhenReady = true
    private var videoUrl = ""
    private var mute = true
    private lateinit var mutebtn:ImageButton
    private lateinit var unMutebtn:ImageButton
    val drmSchemeUuid = C.WIDEVINE_UUID


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val image = intent.getStringExtra("IMAGE")
        val title = intent.getStringExtra("TITLE")
        val descr = intent.getStringExtra("DESCR")
        videoUrl = image!!

        binding.tvTitle.text = title
        binding.tvDescr.text = descr
        thumbnail = binding.ivThumbnail
        mutebtn = binding.mute
        unMutebtn = binding.unmute

        Glide.with(this).load(image).into(thumbnail)

//
        binding.ivBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    private fun buildMediaSource(): MediaSource {
        // Create a data source factory.

        val dataSourceFactory: DataSource.Factory = DefaultHttpDataSource.Factory()

        // Create a progressive media source pointing to a stream uri.
        val mediaSource: MediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(MediaItem.fromUri(videoUrl))

        return mediaSource
    }

    private fun initPlayer() {

        exoPlayer = ExoPlayer.Builder(this).build()

        val currentvolume: Float = exoPlayer!!.getVolume()
        
        // Bind the player to the view.
        binding.ivImagebg.player = exoPlayer
        binding.ivImagebg.setOnClickListener {
            binding.pause.visibility = View.VISIBLE
            binding.play.visibility = View.INVISIBLE
            if (exoPlayer!!.isPlaying){
                Handler().postDelayed({
                    binding.pause.visibility = View.INVISIBLE
                },1000)
                exoPlayer!!.pause()
            }else{
                binding.pause.visibility = View.INVISIBLE
                binding.play.visibility = View.VISIBLE
                Handler().postDelayed({
                    binding.play.visibility = View.INVISIBLE
                },1000)

                exoPlayer!!.play()
            }
        }
        //setting exoplayer when it is ready.
        exoPlayer!!.playWhenReady = true

        exoPlayer!!.addListener(object : Player.Listener { // player listener

            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                when (playbackState) { // check player play back state
                    Player.STATE_READY -> {
                        binding.ivThumbnail.visibility = View.GONE
                        binding.mute.visibility = View.VISIBLE

                    }
                    Player.STATE_ENDED -> {
                        //your logic
                    }
                    Player.STATE_BUFFERING ->{
                        //your logic
                    }
                    Player.STATE_IDLE -> {
                        //your logic
                    }
                    else -> {
                        playerView.hideController()
                    }
                }
            }
        })

        mutebtn.setOnClickListener {
            exoPlayer!!.volume = 0f
        binding.mute.visibility = View.INVISIBLE
            binding.unmute.visibility = View.VISIBLE
        }

        unMutebtn.setOnClickListener {
            exoPlayer!!.volume = currentvolume
            binding.mute.visibility = View.VISIBLE
            binding.unmute.visibility = View.INVISIBLE
        }

        exoPlayer!!.setMediaSource(buildMediaSource())

        exoPlayer!!.prepare()

    }


    private fun releasePlayer() {
        if (exoPlayer == null) {
            return
        }
        //release player when done
        exoPlayer!!.release()
        exoPlayer = null
    }


    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT < 24 || exoPlayer == null) {
            initPlayer()
        }
    }
    override fun onResume() {
        super.onResume()
        if (Util.SDK_INT < 24 || exoPlayer == null) {
            initPlayer()
        }
    }


    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT >= 24) {
            releasePlayer()
        }
    }
    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT >= 24) {
            releasePlayer()
        }

    }

    override fun onDestroy() {
        super.onDestroy()

    }
}