package com.mkchtv.cleantemplate.playground.topic.deeplink

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.mkchtv.cleantemplate.R
import com.mkchtv.cleantemplate.common.CardListItem
import kotlin.random.Random

@Composable
fun DeepLinkTopic() = Column(
    modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
        .verticalScroll(rememberScrollState()),
) {
    ExplicitDeepLink()
}

@Composable
private fun ColumnScope.ExplicitDeepLink() {
    val context = LocalContext.current

    CardListItem(headlineText = "Deep link to element details from notification") {
        notifyAboutDetails(context, Random.nextInt(20))
    }
}

private fun createChannel(): NotificationChannelCompat =
    NotificationChannelCompat
        .Builder(
            "com.mkchtv.cleantemplate.channel.default",
            NotificationManagerCompat.IMPORTANCE_DEFAULT
        )
        .setName("Default channel")
        .build()

private fun notifyAboutDetails(context: Context, detailsId: Int) {
    val channel = createChannel()
    val notificationManager = NotificationManagerCompat.from(context)
    notificationManager.createNotificationChannel(channel)

    val intent = Intent(
        Intent.ACTION_VIEW,
        Uri.parse("app://com.mkchtv.cleantemplate/details/$detailsId")
    )
    val pendingIntent = PendingIntent.getActivity(
        context,
        0,
        intent,
        PendingIntent.FLAG_IMMUTABLE
    )

    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        Toast.makeText(context, "Permission required", Toast.LENGTH_SHORT).show()
        return
    }
    notificationManager.notify(
        detailsId,
        NotificationCompat.Builder(context, channel.id)
            .setContentTitle("Clean template")
            .setContentText("Navigate to details")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
    )
}