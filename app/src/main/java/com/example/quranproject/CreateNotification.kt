package com.example.quranproject

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.quranproject.api.model.RadiosItem
import com.example.quranproject.services.NotificationActionService

class CreateNotification {

    companion object{
        val CHANNEL_ID = "CHANEL1"
        val ACTION_PLAY = "ACTION_PLAY"
        val ACTION_NEXT = "ACTION_NEXT"
        val ACTION_PREVIOUS = "ACTION_PREVIOUS"

        val BROADCASE_REQUEST_CODE = 1003


        //var notification:Notification? = null

        fun createNotification(context: Context , radiosItem: RadiosItem?,playButton:Int,pos:Int,size:Int){
            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){

                val notificationManagerCompat=NotificationManagerCompat.from(context)

                var mediaSessionCompat=MediaSessionCompat(context,"tag")

                val icon :Bitmap= BitmapFactory.decodeResource(context.resources,R.drawable.radio)

                // initializing the next and previous icons variables
                var drw_Previous :Int? = null
                var drw_Next :Int? = null


                /**
                 * @param drw_Previous
                 * pending Intent for previous Button
                 * if pos == 0 then the radio item is the first one so remove previous drawable icon
                 * else set intent with the previous Action and set drawable icon to previous_icon
                 * */
                val pendingIntentPrevious :PendingIntent?

                if (pos==0){
                    pendingIntentPrevious = null
                    drw_Previous = 0
                }else{
                    val intentPrevious = Intent(context,NotificationActionService::class.java).setAction(ACTION_PREVIOUS)
                    pendingIntentPrevious = PendingIntent.getBroadcast(context, BROADCASE_REQUEST_CODE,intentPrevious,PendingIntent.FLAG_UPDATE_CURRENT)
                    drw_Previous = R.drawable.ic_previous_
                }
                /**
                 * pending intent to play button */

                val intentPlay = Intent(context,NotificationActionService::class.java).setAction(ACTION_PLAY)
                var pendingIntentPlay = PendingIntent.getBroadcast(context, BROADCASE_REQUEST_CODE,intentPlay,PendingIntent.FLAG_UPDATE_CURRENT)

                /**
                 * @param drw_Next
                 * pending Intent for next Button
                 * if pos == size then the radio item is the last one so remove drawable icon
                 * else set intent with the next Action and set drawable icon to next_icon
                 * */
                val pendingIntentNext :PendingIntent?

                if (pos==size){
                    pendingIntentNext = null
                    drw_Next = 0
                }else{
                    val intentNext = Intent(context,NotificationActionService::class.java).setAction(
                        ACTION_NEXT)
                    pendingIntentNext = PendingIntent.getBroadcast(context, BROADCASE_REQUEST_CODE,intentNext,PendingIntent.FLAG_UPDATE_CURRENT)
                     drw_Next  = R.drawable.ic_next_
                }




                val notification = NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_notification_radio)
                    .setContentText(radiosItem?.name)
                    .setContentTitle("Quran Radio")
                    .setLargeIcon(icon)
                    .setOnlyAlertOnce(true)
                    .setShowWhen(false)
                    .addAction(drw_Previous,"previous",pendingIntentPrevious)
                    .addAction(playButton,"play",pendingIntentPlay)
                    .addAction(drw_Next,"next",pendingIntentNext)
                    .setStyle(androidx.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(0, 1, 2)
                        .setMediaSession(mediaSessionCompat.sessionToken))
                    .setPriority(NotificationCompat.PRIORITY_MAX)
                    .setOngoing(true)
                    .setAutoCancel(true)

                    .build()

                notificationManagerCompat.notify(1, notification)
            }
        }

    }
}