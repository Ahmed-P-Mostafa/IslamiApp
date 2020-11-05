package com.example.quranproject.services

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.util.Log
import android.view.View
import com.example.quranproject.api.model.RadiosItem
import kotlinx.android.synthetic.main.fragment_radio.*

class MediaPlayerService():Service() {
    private val TAG = "MediaPlayerService"

    var mediaPlayer:MediaPlayer?= null
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        mediaPlayer
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val url = intent?.extras?.getString("url")

        releaseMediaPlayer()

        Log.d(TAG, "playRadio: $url")
        mediaPlayer = MediaPlayer()
        mediaPlayer?.setDataSource(url)
        mediaPlayer?.prepareAsync()
        Log.d(TAG, "onStartCommand: is null after ? ${(mediaPlayer == null)}")


        mediaPlayer?.setOnPreparedListener {
            listener?.onPrepared()

            mediaPlayer?.start()


        }
        mediaPlayer?.setOnCompletionListener {
            releaseMediaPlayer()
        }


        return START_NOT_STICKY
    }

        interface PreparedListener{
            fun onPrepared()
        }

    var listener : PreparedListener? = null
    fun onPreparedListener(listener:PreparedListener){
        this.listener = listener
    }


    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        stopSelf()
    }
    fun releaseMediaPlayer() {
        if (mediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mediaPlayer?.stop()
            mediaPlayer?.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mediaPlayer = null
            //audioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }

}