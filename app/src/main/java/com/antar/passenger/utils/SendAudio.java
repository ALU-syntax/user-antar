package com.antar.passenger.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.antar.passenger.R;
import com.antar.passenger.activity.ChatActivity;
import com.antar.passenger.constants.BaseApp;
import com.antar.passenger.constants.Constants;
import com.antar.passenger.json.FcmKeyResponseJson;
import com.antar.passenger.json.FcmRequestJson;
import com.antar.passenger.json.fcm.FCMMessage;
import com.antar.passenger.models.Chat;
import com.antar.passenger.models.User;
import com.antar.passenger.utils.api.FCMHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.antar.passenger.utils.api.ServiceGenerator;
import com.antar.passenger.utils.api.service.UserService;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by aradi on 12/5/2018.
 */


public class SendAudio {


    private static String mFileName = null;
    private DatabaseReference rootref;
    private String senderid;
    private String Receiverid;
    private String tokendriver;
    private String tokenku;
    private String Receiver_name;
    private String Receiver_pic;
    private Context context;
    private MediaRecorder mRecorder = null;

    private DatabaseReference Adduser_to_inbox;

    private EditText message_field;
    private Handler handler;
    private Runnable runnable;
    private CountDownTimer timer;
    private String keyss;

    public SendAudio(Context context, EditText message_field,
                     DatabaseReference rootref, DatabaseReference adduser_to_inbox
            , String senderid, String receiverid, String tokenku, String tokendriver, String receiver_name, String receiver_pic) {

        this.context = context;
        this.message_field = message_field;
        this.rootref = rootref;
        this.Adduser_to_inbox = adduser_to_inbox;
        this.senderid = senderid;
        this.Receiverid = receiverid;
        this.tokendriver = tokendriver;
        this.tokenku = tokenku;
        this.Receiver_name = receiver_name;
        this.Receiver_pic = receiver_pic;
        mFileName = Objects.requireNonNull(context.getExternalCacheDir()).getAbsolutePath();
        mFileName += "audiorecordtest.mp3";

    }

    private void startRecording() {

        if (mRecorder != null) {
            mRecorder.stop();
            mRecorder.reset();
            mRecorder.release();
            mRecorder = null;
        }

        mRecorder = new MediaRecorder();

        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);

        if (mRecorder != null)
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);

        if (mRecorder != null)
            mRecorder.setOutputFile(mFileName);

        if (mRecorder != null)
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            if (mRecorder != null)
                mRecorder.prepare();
        } catch (IOException e) {
            Log.e("resp", "prepare() failed");
        }
        if (mRecorder != null)
            mRecorder.start();


    }

    public void stopRecording() {
        stop_timer_without_recoder();
        if (mRecorder != null) {
            mRecorder.stop();
            mRecorder.reset();
            mRecorder.release();
            mRecorder = null;
            Runbeep("stop");
            UploadAudio();
        }
    }

    @SuppressLint("SetTextI18n")
    public void Runbeep(final String action) {
        handler = new Handler();
        if (action.equals("start")) {
            message_field.setText("00:00");
            runnable = new Runnable() {
                @Override
                public void run() {
                    start_timer();
                }
            };

            handler.postDelayed(runnable, 700);
        }

        final MediaPlayer beep = MediaPlayer.create(context, R.raw.notification);
        beep.setVolume(100, 100);
        beep.start();
        beep.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                beep.release();
                if (action.equals("start"))
                    startRecording();
            }
        });
    }

    private void UploadAudio() {
        final User loginUser = BaseApp.getInstance(context).getLoginUser();
        Date c = Calendar.getInstance().getTime();
        final String formattedDate = Constants.df.format(c);

        StorageReference reference = FirebaseStorage.getInstance().getReference();
        DatabaseReference dref = rootref.child("chat").child(senderid + "-" + Receiverid).push();
        final String key = dref.getKey();
        ChatActivity.uploadingAudioId = key;
        final String current_user_ref = "chat" + "/" + senderid + "-" + Receiverid;
        final String chat_user_ref = "chat" + "/" + Receiverid + "-" + senderid;

        HashMap my_dummi_pic_map = new HashMap<>();
        my_dummi_pic_map.put("receiver_id", Receiverid);
        my_dummi_pic_map.put("sender_id", senderid);
        my_dummi_pic_map.put("chat_id", key);
        my_dummi_pic_map.put("text", "");
        my_dummi_pic_map.put("type", "audio");
        my_dummi_pic_map.put("pic_url", "none");
        my_dummi_pic_map.put("status", "0");
        my_dummi_pic_map.put("tokendriver", tokendriver);
        my_dummi_pic_map.put("tokenuser", tokenku);
        my_dummi_pic_map.put("isdriver", "0");
        my_dummi_pic_map.put("time", "");
        my_dummi_pic_map.put("sender_name", loginUser.getFullnama());
        my_dummi_pic_map.put("timestamp", formattedDate);

        HashMap dummy_push = new HashMap<>();
        dummy_push.put(current_user_ref + "/" + key, my_dummi_pic_map);
        rootref.updateChildren(dummy_push);


        Uri uri = Uri.fromFile(new File(mFileName));


        reference.child("Audio").child(key + ".mp3").putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        ChatActivity.uploadingAudioId = "none";

                        HashMap message_user_map = new HashMap<>();
                        message_user_map.put("receiver_id", Receiverid);
                        message_user_map.put("sender_id", senderid);
                        message_user_map.put("chat_id", key);
                        message_user_map.put("text", "");
                        message_user_map.put("type", "audio");
                        message_user_map.put("pic_url", uri.toString());
                        message_user_map.put("status", "0");
                        message_user_map.put("tokendriver", tokendriver);
                        message_user_map.put("tokenuser", tokenku);
                        message_user_map.put("time", "");
                        message_user_map.put("sender_name", loginUser.getFullnama());
                        message_user_map.put("timestamp", formattedDate);

                        HashMap user_map = new HashMap<>();

                        user_map.put(current_user_ref + "/" + key, message_user_map);
                        user_map.put(chat_user_ref + "/" + key, message_user_map);

                        rootref.updateChildren(user_map, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                String inbox_sender_ref = "Inbox" + "/" + senderid + "/" + Receiverid;
                                String inbox_receiver_ref = "Inbox" + "/" + Receiverid + "/" + senderid;

                                HashMap<String, java.io.Serializable> sendermap = new HashMap<>();
                                sendermap.put("rid", senderid);
                                sendermap.put("name", loginUser.getFullnama());
                                sendermap.put("pic", Constants.IMAGESUSER + loginUser.getFotopelanggan());
                                sendermap.put("tokendriver", tokenku);
                                sendermap.put("tokenuser", tokendriver);
                                sendermap.put("msg", "Send an audio..");
                                sendermap.put("status", "0");
                                sendermap.put("timestamp", -1 * System.currentTimeMillis());
                                sendermap.put("date", formattedDate);

                                HashMap<String, java.io.Serializable> receivermap = new HashMap<>();
                                receivermap.put("rid", Receiverid);
                                receivermap.put("name", Receiver_name);
                                receivermap.put("pic", Receiver_pic);
                                receivermap.put("tokendriver", tokendriver);
                                receivermap.put("tokenuser", tokenku);
                                receivermap.put("msg", "Send an audio..");
                                receivermap.put("status", "1");
                                receivermap.put("timestamp", -1 * System.currentTimeMillis());
                                receivermap.put("date", formattedDate);

                                HashMap both_user_map = new HashMap<>();
                                both_user_map.put(inbox_sender_ref, receivermap);
                                both_user_map.put(inbox_receiver_ref, sendermap);

                                Adduser_to_inbox.updateChildren(both_user_map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        Chat chat = new Chat();
                                        chat.senderid = senderid;
                                        chat.receiverid = Receiverid;
                                        chat.name = loginUser.getFullnama();
                                        chat.pic = Constants.IMAGESUSER + loginUser.getFotopelanggan();
                                        chat.tokendriver = tokendriver;
                                        chat.tokenuser = tokenku;
                                        chat.isdriver = "0";
                                        chat.message = "Send Audio...\uD83C\uDFA4";
                                        sendMessageToDriver(tokenku, chat);

                                    }
                                });
                            }
                        });
                    }
                });

            }
        });

    }

    private void start_timer() {

        timer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long time = 30000 - millisUntilFinished;

                int min = (int) (time / 1000) / 60;
                int sec = (int) (time / 1000) % 60;
                String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", min, sec);
                message_field.setText(timeLeftFormatted);
            }

            @Override
            public void onFinish() {
                stopRecording();
                message_field.setText(null);
            }
        };

        timer.start();
    }

    public void stop_timer() {

        if (mRecorder != null) {
            mRecorder.stop();
            mRecorder.reset();
            mRecorder.release();
            mRecorder = null;
        }

        if (handler != null && runnable != null)
            handler.removeCallbacks(runnable);

        message_field.setText(null);

        if (timer != null) {
            timer.cancel();
            message_field.setText(null);
        }

    }

    private void stop_timer_without_recoder() {

        if (handler != null && runnable != null)
            handler.removeCallbacks(runnable);

        message_field.setText(null);

        if (timer != null) {
            timer.cancel();
            message_field.setText(null);
        }

    }

    private void sendMessageToDriver(final String regIDTujuan, final Chat chat) {
        getkey();
        final FCMMessage message = new FCMMessage();
        message.setTo(regIDTujuan);
        message.setData(chat);

        FCMHelper.sendMessage(keyss, message).enqueue(new okhttp3.Callback() {
            @Override
            public void onResponse(@NonNull okhttp3.Call call, @NonNull okhttp3.Response response) {
                Log.e("REQUEST TO DRIVER", message.getData().toString());
            }

            @Override
            public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void getkey() {
        User loginUser = BaseApp.getInstance(context).getLoginUser();
        UserService userService = ServiceGenerator.createService(UserService.class,
                loginUser.getEmail(), loginUser.getPassword());
        FcmRequestJson param = new FcmRequestJson();
        param.setFcm(1);
        userService.fcmgetkey(param).enqueue(new Callback<FcmKeyResponseJson>() {
            @Override
            public void onResponse(Call<FcmKeyResponseJson> call, Response<FcmKeyResponseJson> response) {
                if(response.isSuccessful()){
                    String res = response.body().getResultcode();
                    if(res.equalsIgnoreCase("00")){
                        keyss = response.body().getKeydata();

                    }
                }
            }

            @Override
            public void onFailure(Call<FcmKeyResponseJson> call, Throwable t) {

            }
        });
    }

}

