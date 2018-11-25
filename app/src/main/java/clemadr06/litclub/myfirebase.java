package clemadr06.litclub;

import android.annotation.TargetApi;

import android.app.Notification;

import android.app.NotificationChannel;

import android.app.NotificationManager;

import android.app.PendingIntent;

import android.content.Context;

import android.content.Intent;

import android.content.SharedPreferences;

import android.graphics.Bitmap;

import android.graphics.BitmapFactory;

import android.graphics.Color;

import android.media.RingtoneManager;

import android.net.Uri;

import android.os.AsyncTask;

import android.os.Build;

import android.os.Handler;

import android.os.Looper;

import android.provider.Settings;

import android.support.v4.app.NotificationCompat;

import android.support.v4.content.ContextCompat;

import android.util.Log;



import com.google.firebase.iid.FirebaseInstanceId;

import com.google.firebase.messaging.FirebaseMessagingService;

import com.google.firebase.messaging.RemoteMessage;

import com.loopj.android.http.AsyncHttpClient;

import com.loopj.android.http.AsyncHttpResponseHandler;



import java.io.IOException;

import java.io.InputStream;

import java.net.HttpURLConnection;

import java.net.MalformedURLException;

import java.net.URL;

import java.util.Map;


import clemadr06.LitClub.R;
import cz.msebera.android.httpclient.Header;



public class myfirebase extends FirebaseMessagingService {



    private static final String TAG = "MyFirebaseMsgService";



    private Context mContext=this;

    private NotificationManager mNotificationManager;

    private NotificationCompat.Builder mBuilder;

    public static final String NOTIFICATION_CHANNEL_ID = "10001";



    Bitmap myBitmap;

    String title,body,img_url;

    Map<String,String> message;

    /**

     * Called when message is received.

     *

     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.

     */

    // [START receive_message]

    @Override

    public void onMessageReceived(RemoteMessage remoteMessage) {



        // TODO(developer): Handle FCM messages here.

        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ

        Log.d(TAG, "From: " + remoteMessage.getFrom());



        Log.d(TAG, "test ID: " + FirebaseInstanceId.getInstance().getId());

        Log.d(TAG, "test token: " + FirebaseInstanceId.getInstance().getToken());



        // Check if message contains a notification payload.



        if (remoteMessage.getData() != null) {

            Log.e(TAG, "Message Notification test Body: " + remoteMessage.getData());

        }



        try {

            Map<String, String> message = remoteMessage.getData();

            title = message.get("title");

            body=message.get("body");

            img_url=message.get("image");

        }catch (Exception e){

            e.printStackTrace();

        }



        try{

            if(img_url!=null) {

                Handler mainHandler = new Handler(Looper.getMainLooper());

                Runnable myRunnable = new Runnable() {

                    @Override

                    public void run() {

                        AsyncHttpClient client = new AsyncHttpClient();

                        client.get(img_url, null, new AsyncHttpResponseHandler() {

                            @Override

                            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {



                            }



                            @Override

                            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                                myBitmap = BitmapFactory.decodeByteArray(responseBody, 0, responseBody.length);

                            }



                            @Override

                            public void onFinish() {

                                createNotification();

                            }

                        });

                    }

                };

                mainHandler.post(myRunnable);

            }else{

                createNotification();

            }

        }catch (Exception e){

            e.printStackTrace();

            createNotification();

        }





    }



    /**

     * Called if InstanceID token is updated. This may occur if the security of

     * the previous token had been compromised. Note that this is called when the InstanceID token

     * is initially generated so this is where you would retrieve the token.

     */

    @Override

    public void onNewToken(String token) {

        Log.d(TAG, "test Refreshed ID: " + FirebaseInstanceId.getInstance().getId());

        Log.d(TAG, " test Refreshed token: " + token);



        // If you want to send messages to this application instance or

        // manage this apps subscriptions on the server side, send the

        // Instance ID token to your app server.

        sendRegistrationToServer(token);

    }



    private void sendRegistrationToServer(String token1) {

        SharedPreferences pref = getSharedPreferences("firebase",Context.MODE_PRIVATE) ;

        SharedPreferences.Editor ed = pref.edit();

        ed.putBoolean("refresh",true);

        ed.putString("token",token1);

        ed.apply();



    }





    public void createNotification()

    {

        //Creates an explicit intent for an Activity in your app

        Intent resultIntent = new Intent(this , MainActivity.class);

        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);



        PendingIntent resultPendingIntent = PendingIntent.getActivity(this,

                0 /* Request code */, resultIntent,

                PendingIntent.FLAG_UPDATE_CURRENT);



        mBuilder = new NotificationCompat.Builder(mContext);

        mBuilder.setSmallIcon(R.mipmap.ic_launcher);

        mBuilder.setContentTitle(title)

                .setContentText(body);

        if(myBitmap!=null) {

            mBuilder.

                    setStyle(new NotificationCompat.BigPictureStyle().bigPicture(myBitmap).setSummaryText(body));

        }

        mBuilder.setAutoCancel(false)

                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)

                .setContentIntent(resultPendingIntent);



        mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);



        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)

        {

            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, getResources().getString(R.string.default_notification_channel_id), importance);

            notificationChannel.enableLights(true);

            notificationChannel.setLightColor(Color.RED);

            notificationChannel.enableVibration(true);

            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

            assert mNotificationManager != null;

            mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);

            mNotificationManager.createNotificationChannel(notificationChannel);

        }

        assert mNotificationManager != null;

        mNotificationManager.notify(0 /* Request Code */, mBuilder.build());

    }

}