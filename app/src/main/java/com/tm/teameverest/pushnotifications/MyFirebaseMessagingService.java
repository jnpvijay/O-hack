package com.tm.teameverest.pushnotifications;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.tm.teameverest.LoginActivity;
import com.tm.teameverest.R;

import java.util.Map;

/**
 * Created by user on 12/11/16.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFMService";

    private static final String TITLE_TAG = "title";
    private static final String SUB_TITLE_TAG = "subtitle";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // Handle data payload of FCM messages.
        Log.d(TAG, "FCM Message Id: " + remoteMessage.getMessageId());
        Log.d(TAG, "FCM Notification Message: " + remoteMessage.getNotification());
        Log.d(TAG, "FCM Data Message: " + remoteMessage.getData());

        sendNotification(remoteMessage.getData());
    }


    /**
     * Create and show a simple notification containing the received GCM message
     *
     * @param json GCM message received.
     */
    private void sendNotification(Map json) {

        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        //  PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent, 0);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                //.setSmallIcon(getNotificationIcon())
                .setContentTitle(json.get(TITLE_TAG).toString())
                .setContentText(json.get(SUB_TITLE_TAG).toString())
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setAutoCancel(true)
                .setSound(defaultSoundUri);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        // default -0 , multi notification purpose multi notification
        //Integer.parseInt(menu_model.getOffer_id())
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());


    }

}
