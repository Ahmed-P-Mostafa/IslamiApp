package com.example.quranproject.ui


import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.*
import android.content.pm.PackageManager
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.quranproject.CreateNotification
import com.example.quranproject.R
import com.example.quranproject.adapters.RadioAdapter
import com.example.quranproject.api.ApiManager
import com.example.quranproject.api.model.RadiosItem
import com.example.quranproject.api.model.RadiosResponse
import com.example.quranproject.services.OnClearFromRecentService
import kotlinx.android.synthetic.main.bottom_sheet.*
import kotlinx.android.synthetic.main.fragment_radio.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RadioFragment : Fragment() {
    val TAG = "RadioFragment"
    val INTERNET_PERMISSION = 1

    val dummyArrayList = ArrayList<RadiosItem?>()

    var adapter = RadioAdapter(dummyArrayList)
    var isPlaying = false
    var mediaPlayer: MediaPlayer? = null

    //val service = MediaPlayerService()

    var notificationManager: NotificationManager? = null
    lateinit var audioManager: AudioManager
    var channelsList = ArrayList<RadiosItem?>()
    var channel = RadiosItem("", "")
    var chanelPosition = 0

    val mOnAudioFocusChangeListener = AudioManager.OnAudioFocusChangeListener {
        fun onAudioFocusChange(focusChange: Int) {
            if (focusChange == AudioManager.AUDIOFOCUS_GAIN_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK) {
                pauseRadio()
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                mediaPlayer?.start()
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                releaseMediaPlayer()
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_radio, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progress_bar.visibility = View.VISIBLE
        Log.d(TAG, "onViewCreated: ")
        audioManager =
            getActivity()?.getSystemService(Context.AUDIO_SERVICE) as AudioManager

        radioRecycler.adapter = adapter

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            creatChannel()

            context?.registerReceiver(broadcastReceiver, IntentFilter("radio"))
            context?.startService(Intent(context,OnClearFromRecentService::class.java))

        }
        checkInternetPermission()

        adapter.onChannelClickListener(object : RadioAdapter.onRadioClickListener {
            override fun onItemClicked(pos: Int, item: RadiosItem) {
                progress_bar.visibility = View.VISIBLE
                channel = item
                chanelPosition = pos

                playRadio(channelsList.get(chanelPosition)?.radioUrl)
                Log.d(TAG, "onItemClicked: chanel position : $chanelPosition")
                Log.d(TAG, "onItemClicked: ${channelsList.get(chanelPosition)?.radioUrl}")
                Log.d(TAG, "onItemClicked: ${channelsList.get(chanelPosition)?.name}")


            }
        })

        next_ic.setOnClickListener { nextChannel() }
        previous_ic.setOnClickListener { previousChannel() }
        play_ic.setOnClickListener {

            playButton(channelsList.get(chanelPosition)?.radioUrl)


        }
    }

    private fun creatChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var notificationChannel = NotificationChannel(
                CreateNotification.CHANNEL_ID,
                "name",
                NotificationManager.IMPORTANCE_LOW
            )

            notificationManager = context?.getSystemService(NotificationManager::class.java)

            if (notificationManager != null) {
                notificationManager?.createNotificationChannel(notificationChannel)
            }
        }
    }

    fun playButton(url: String?) {
        if (!isPlaying) {

            playRadio(url)
        } else {

            pauseRadio()
        }

    }

    fun nextChannel() {
        if (chanelPosition < channelsList.size - 1) {
            progress_bar.visibility = View.VISIBLE

            chanelPosition++
            createNotification(R.drawable.ic_pause)
            playRadio(channelsList.get(chanelPosition)?.radioUrl)
            Log.d(TAG, "nextChannel: ${channelsList.get(chanelPosition)?.radioUrl}")
            Log.d(TAG, "nextChannel: ${channelsList.get(chanelPosition)?.name}")
            Log.d(TAG, "nextChannel: chanel position : $chanelPosition")

            Log.d(TAG, "nextChannel: session ID ${mediaPlayer?.audioSessionId}")
            Log.d(TAG, "nextChannel: is playing ? ${mediaPlayer?.isPlaying}")
            Log.d(TAG, "nextChannel: is null ? ${(mediaPlayer == null)}")
        }
    }

    fun previousChannel() {

        if (chanelPosition > 0) {
            createNotification(R.drawable.ic_pause)
            progress_bar.visibility = View.VISIBLE
            chanelPosition--
            playRadio(channelsList.get(chanelPosition)?.radioUrl)
            Log.d(TAG, "previousChannel: ${channelsList.get(chanelPosition)?.radioUrl}")
            Log.d(TAG, "previousChannel: ${channelsList.get(chanelPosition)?.name}")
            Log.d(TAG, "previousChannel: chanel position : $chanelPosition")
        }
    }

    fun playRadio(url: String?) {
        changeChanelName()
        isPlaying = true
        play_ic.setImageDrawable(resources.getDrawable(R.drawable.ic_pause))
        progress_bar.visibility = View.VISIBLE
        createNotification(R.drawable.ic_pause)
        Log.d(TAG, "playRadio: executed")
        Log.d(TAG, "playRadio: session ID ${mediaPlayer?.audioSessionId}")
        Log.d(TAG, "playRadio: is playing ? ${mediaPlayer?.isPlaying}")
        Log.d(TAG, "playRadio: is null before ? ${(mediaPlayer == null)}")

        /*val serviceIntent=Intent(context,MediaPlayerService::class.java)
        serviceIntent.putExtra("url",url)
        context?.startService(serviceIntent)
        */


        /*val result = audioManager.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT)
        if (result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
        //TODO manage audio manager to handle audio ducos call backs
        }*/

        releaseMediaPlayer()

        Log.d(TAG, "playRadio: $url")
        mediaPlayer = MediaPlayer()
        mediaPlayer?.setDataSource(url)
        mediaPlayer?.prepareAsync()
        Log.d(TAG, "playRadio: is null after ? ${(mediaPlayer == null)}")

        mediaPlayer?.setOnPreparedListener {

            progress_bar.visibility = View.GONE
            mediaPlayer?.start()


        }
        mediaPlayer?.setOnCompletionListener {
            releaseMediaPlayer()
        }

        /*service.onPreparedListener(object : MediaPlayerService.PreparedListener {
            override fun onPrepared() {
                progress_bar.visibility = View.GONE
                changeChanelName()
            }

        })*/

    }

    fun createNotification(drawable:Int) {
        CreateNotification.createNotification(
            this.requireContext(),
            channelsList.get(chanelPosition),
            drawable,
            chanelPosition,
            channelsList.size - 1
        )
    }

    fun pauseRadio() {

        createNotification(R.drawable.ic_play)
        isPlaying = false
        mediaPlayer?.pause()
        //releaseMediaPlayer()
        progress_bar.visibility = View.GONE
        play_ic.setImageDrawable(resources.getDrawable(R.drawable.ic_play))
        Log.d(TAG, "pauseRadio: session ID ${mediaPlayer?.audioSessionId}")
        Log.d(TAG, "pauseRadio: is playing ? ${mediaPlayer?.isPlaying}")
        Log.d(TAG, "pauseRadio: is null ? ${(mediaPlayer == null)}")

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
            audioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }


    fun getRadioChannels() {
        Log.d(TAG, "getRadioChannels: ")
        ApiManager.getAPIs().getRadioChannels().enqueue(object : Callback<RadiosResponse> {
            override fun onResponse(
                call: Call<RadiosResponse>,
                response: Response<RadiosResponse>
            ) {
                if (response.isSuccessful) {
                    progress_bar.visibility = View.GONE
                    channelsList = response.body()?.radios!!
                    adapter?.changeData(channelsList)

                } else {
                    Log.d(TAG, "onResponse: ${response.code()} ${response.body()?.radios}")
                }
            }

            override fun onFailure(call: Call<RadiosResponse>, t: Throwable) {

                Log.d(TAG, "onFailure: ${t.localizedMessage} ")
                Toast.makeText(context, "failed to get Radios", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun isInternetPermissionGranted(): Boolean {
        return (ContextCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.INTERNET
        ) == PackageManager.PERMISSION_GRANTED)
    }

    fun checkInternetPermission() {
        if (isInternetPermissionGranted()) {
            getRadioChannels()
        } else {
            if (shouldShowRequestPermissionRationale(android.Manifest.permission.INTERNET)) {
                val dialog: AlertDialog
                dialog = AlertDialog.Builder(context).setMessage("Internet Permission needed")
                    .setTitle("Permission Nedded").setNegativeButton(
                        "Cancel",
                        DialogInterface.OnClickListener { dialog, which ->
                            dialog.dismiss()
                        }).setPositiveButton(
                        "Ok",
                        DialogInterface.OnClickListener { dialog, which ->
                            requestInternetPErmission()
                        }).create()
                dialog.show()


            } else {
                requestInternetPErmission()
            }
        }
    }

    fun requestInternetPErmission() {
        requestPermissions(arrayOf(android.Manifest.permission.INTERNET), INTERNET_PERMISSION)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == INTERNET_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getRadioChannels()
        } else {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_LONG).show()
            Log.d(TAG, "onRequestPermissionsResult: ${grantResults.get(0)}")
        }

    }

    fun changeChanelName() {
        channelName.setText(channelsList.get(chanelPosition)?.name)
    }

    override fun onStop() {
        super.onStop()
        releaseMediaPlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            notificationManager?.cancelAll()
        }
        context?.unregisterReceiver(broadcastReceiver)
    }



    var broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {

            var action = intent?.extras?.getString("actionName")

            when (action) {
                CreateNotification.ACTION_PLAY -> {
                    if (isPlaying){
                        pauseRadio()
                    }else{
                        playRadio(channelsList.get(chanelPosition)?.radioUrl)
                    }
                }
                CreateNotification.ACTION_NEXT -> {
                    nextChannel()
                }
                CreateNotification.ACTION_PREVIOUS->{
                    previousChannel()
                }
            }
        }
    }


}