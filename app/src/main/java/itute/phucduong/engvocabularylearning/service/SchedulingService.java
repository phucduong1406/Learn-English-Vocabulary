package itute.phucduong.engvocabularylearning.service;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

import itute.phucduong.engvocabularylearning.Bookmark;
import itute.phucduong.engvocabularylearning.BookmarkAdapter;
import itute.phucduong.engvocabularylearning.Constant;
import itute.phucduong.engvocabularylearning.Dictionary;
import itute.phucduong.engvocabularylearning.MainActivity;
import itute.phucduong.engvocabularylearning.R;

import static android.content.ContentValues.TAG;

public class SchedulingService extends IntentService {
    private static final int TIME_VIBRATE = 1000;

    ArrayList<Bookmark> mSource = new ArrayList<>();
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();

    Random rd = new Random();
    //int x = rd.nextInt((max - min + 1) + min);
    int index = 0;

    public SchedulingService() {
        super(SchedulingService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(final Intent intent) {


        mSource = new ArrayList<Bookmark>();


        mData.child("Dictionary").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Dictionary dictionary = dataSnapshot.getValue(Dictionary.class);
                if (dictionary.favorite_word) {
                    mSource.add(new Bookmark(dictionary.word, dictionary.mean));

                    Intent notificationIntent = new Intent(SchedulingService.this, MainActivity.class);
                    notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    int requestID = (int) System.currentTimeMillis();
                    PendingIntent contentIntent = PendingIntent.getActivity(SchedulingService.this, requestID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    NotificationCompat.Builder builder =
                            new NotificationCompat.Builder(SchedulingService.this)
                                    .setSmallIcon(R.mipmap.ic_launcher)
                                    .setContentTitle(mSource.get(index).getName())
                                    .setContentText(mSource.get(index).getMean())
                                    .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                                    .setDefaults(Notification.DEFAULT_SOUND)
                                    .setAutoCancel(true)
                                    .setPriority(6)
                                    .setVibrate(new long[]{TIME_VIBRATE, TIME_VIBRATE})
                                    .setContentIntent(contentIntent);
                    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(index, builder.build());

                    index++;

                }

            }


            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


        Toast.makeText(this, mSource.toString() + "A", Toast.LENGTH_LONG).show();

       /* int index = intent.getIntExtra(Constant.KEY_TYPE, 0);

        Intent notificationIntent = new Intent(SchedulingService.this, MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        int requestID = (int) System.currentTimeMillis();
        PendingIntent contentIntent = PendingIntent.getActivity(SchedulingService.this, requestID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(SchedulingService.this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(mSource.get(index).getName())
                        .setContentText(mSource.get(index).getMean())
                        .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                        .setDefaults(Notification.DEFAULT_SOUND)
                        .setAutoCancel(true)
                        .setPriority(6)
                        .setVibrate(new long[]{TIME_VIBRATE, TIME_VIBRATE})
                        .setContentIntent(contentIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(index, builder.build());


        Toast.makeText(SchedulingService.this, mSource.get(index).getName() + " - " + index, Toast.LENGTH_SHORT).show();*/



//        mData.child("Dictionary").addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                Dictionary dictionary = dataSnapshot.getValue(Dictionary.class);
//                if (dictionary.favorite_word) {
//                    mSource.add(new Bookmark(dictionary.word, dictionary.mean));
//
//                    int index = intent.getIntExtra(Constant.KEY_TYPE, 0);
//
//                    Intent notificationIntent = new Intent(SchedulingService.this, MainActivity.class);
//                    notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                    int requestID = (int) System.currentTimeMillis();
//                    PendingIntent contentIntent = PendingIntent.getActivity(SchedulingService.this, requestID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//                    NotificationCompat.Builder builder =
//                            new NotificationCompat.Builder(SchedulingService.this)
//                                    .setSmallIcon(R.mipmap.ic_launcher)
//                                    .setContentTitle(mSource.get(index).getName())
//                                    .setContentText(mSource.get(index).getMean())
//                                    .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
//                                    .setDefaults(Notification.DEFAULT_SOUND)
//                                    .setAutoCancel(true)
//                                    .setPriority(6)
//                                    .setVibrate(new long[]{TIME_VIBRATE, TIME_VIBRATE})
//                                    .setContentIntent(contentIntent);
//                    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//                    notificationManager.notify(index, builder.build());
//
//
//                    Toast.makeText(SchedulingService.this, mSource.get(index).getName() + " - " + index, Toast.LENGTH_SHORT).show();
//                }
//
//            }
//
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException());
//            }
//        });






/*        mSource.add((new Bookmark("A", "Mean of A")));
        mSource.add((new Bookmark("B", "Mean of B")));
        mSource.add((new Bookmark("C", "Mean of C")));
        mSource.add((new Bookmark("D", "Mean of D")));

        int index = intent.getIntExtra(Constant.KEY_TYPE, 0);


        Intent notificationIntent = new Intent(SchedulingService.this, MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        int requestID = (int) System.currentTimeMillis();
        PendingIntent contentIntent = PendingIntent.getActivity(SchedulingService.this, requestID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(SchedulingService.this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(mSource.get(index).getName())
                        .setContentText(mSource.get(index).getMean())
                        .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                        .setDefaults(Notification.DEFAULT_SOUND)
                        .setAutoCancel(true)
                        .setPriority(6)
                        .setVibrate(new long[]{TIME_VIBRATE, TIME_VIBRATE})
                        .setContentIntent(contentIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(index, builder.build());


      *//*  Toast.makeText(SchedulingService.this, mSource.get(index).getName() + " - " + index, Toast.LENGTH_SHORT).show();*/




    }
}



