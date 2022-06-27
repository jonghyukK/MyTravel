package org.kjh.mytravel.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.PRIORITY_HIGH
import androidx.core.app.NotificationManagerCompat
import com.orhanobut.logger.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import org.kjh.mytravel.R

/**
 * MyTravel
 * Class: NotificationUtils
 * Created by jonghyukkang on 2022/06/20.
 *
 * Description:
 */


const val CHANNEL_ID = "MYTRAVEL_NOTIFICATION_CHANNEL"
const val NOTIFICATION_ID = 1

const val MY_TRAVEL_NOTIFICATION_CHANNEL_NAME = "게시물 업로드 알림"
const val MY_TRAVEL_NOTIFICATION_DESCRIPTION = "게시물 업로드 진행상황 및 완료 관련 알림"

object NotificationUtils {

    private lateinit var builder: NotificationCompat.Builder

    private fun getNotificationBuilder(context: Context): NotificationCompat.Builder {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = MY_TRAVEL_NOTIFICATION_CHANNEL_NAME
            val description = MY_TRAVEL_NOTIFICATION_DESCRIPTION
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                this.description = description
            }

            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(channel)
        }

        if (!::builder.isInitialized) {
            builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(context.getString(R.string.noti_upload_title))
                .setPriority(PRIORITY_HIGH)
        }

        return builder
    }

    fun makeUploadNotification(context: Context) {
        val builder = getNotificationBuilder(context)
            .setProgress(0, 0, true)
            .setContentText(context.getString(R.string.uploading))

        NotificationManagerCompat
            .from(context)
            .notify(NOTIFICATION_ID, builder.build())
    }

    fun updateUploadNotification(context: Context, msg: String) {
        val builder = getNotificationBuilder(context)
            .setProgress(0, 0, false)
            .setContentText(msg)

        NotificationManagerCompat
            .from(context)
            .notify(NOTIFICATION_ID, builder.build())
    }
}

