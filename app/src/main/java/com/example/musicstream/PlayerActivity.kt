package com.example.musicstream

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.musicstream.databinding.ActivityPlayerBinding

class PlayerActivity : AppCompatActivity() {

    lateinit var binding:ActivityPlayerBinding
    lateinit var exoplayer:ExoPlayer

    val playerListener = object:Player.Listener{
        override fun onIsPlayingChanged(isPlaying: Boolean) {
            super.onIsPlayingChanged(isPlaying)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

          MyExoplayer.getCurrentSong()?.apply {
              binding.songTitleTextView.text = title
              binding.songSubtitleTextView.text=subtitle
              Glide.with(binding.songCoverImageView).load(coverUrl)
                  .circleCrop()
                  .into(binding.songCoverImageView)
                  exoplayer =MyExoplayer.getInstance()!!
              binding.playerView.player =exoplayer
              binding.playerView.showController()
              exoplayer.addListener(playerListener)
          }
    }

    override fun onDestroy() {
        super.onDestroy()
        exoplayer?.removeListener(playerListener)
    }
}