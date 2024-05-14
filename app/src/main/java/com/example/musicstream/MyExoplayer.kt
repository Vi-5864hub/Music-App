package com.example.musicstream

import android.content.Context
import android.media.browse.MediaBrowser.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.example.musicstream.model.SongModel
import com.google.firebase.firestore.FirebaseFirestore

object MyExoplayer {
    private  var exoplayer:ExoPlayer? = null
    private var currentSong:SongModel? =null

    fun getInstance():ExoPlayer?{
        return exoplayer
    }

    fun getCurrentSong():SongModel?{
        return  currentSong
    }

    fun startPlaying(context: Context,song: SongModel){
        if (exoplayer==null)
         exoplayer =ExoPlayer.Builder(context).build()

        if (currentSong!= song){
            updateCount()
            //its new song to start play
            currentSong = song

            currentSong?.url?.apply {
                val mediaItem = androidx.media3.common.MediaItem.fromUri(this)
                exoplayer?.setMediaItem(mediaItem)
                exoplayer?.prepare()
                exoplayer?.play()
            }
        }

    }
    fun updateCount(){
      currentSong?.id?.let {id->
          FirebaseFirestore.getInstance().collection("songs")
              .document(id).get().addOnSuccessListener {
                var latestCount = it.getLong("count")
                  if (latestCount== null){
                      latestCount = 1L
              }else {
                  latestCount = latestCount+1
                  }
                  FirebaseFirestore.getInstance().collection("songs")
                      .document(id)
                      .update(mapOf("count" to latestCount))
              }
      }
    }
}