package com.antar.passenger.activity;

import android.Manifest;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentTransaction;

import com.antar.passenger.R;
import com.antar.passenger.constants.BaseApp;
import com.antar.passenger.constants.Constants;
import com.antar.passenger.fragment.EnableLlocationFragment;
import com.antar.passenger.models.User;
import com.antar.passenger.utils.PhoneProviderHelper;
import com.antar.passenger.utils.SharedPrefrence;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.Locale;
import java.util.Objects;

import pl.droidsonroids.gif.GifDrawable;


public class SplashActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        try {
            GifDrawable gifFromAssets = new GifDrawable(getAssets(), "splash_image.gif");
        } catch (IOException e) {
            e.printStackTrace();
        }
        removeNotif();
        final User user = BaseApp.getInstance(this).getLoginUser();
        sharedPreferences = getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE);
        PhoneProviderHelper.addOperator(sharedPreferences);

        if (!sharedPreferences.contains("My_Lang")){
            Locale locale = new Locale("in");
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

            //save data to shared preference
            SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
            editor.putString("My_Lang", "in");
            editor.apply();
        }

        new Handler().postDelayed(() -> {

            if (user != null) {

                if (ActivityCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    enable_location();
                } else {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

            } else {
                if (ActivityCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    enable_location();
                } else {
                    Intent intent = new Intent(SplashActivity.this, IntroActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        }, 4800);


    }

    public void GPSStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean GpsStatus = Objects.requireNonNull(locationManager).isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!GpsStatus) {
            Toast.makeText(this, "On Location in High Accuracy", Toast.LENGTH_SHORT).show();
            startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 2);

        } else {


            GetCurrentlocation();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            GPSStatus();
        }
    }


    private void GetCurrentlocation() {
        FusedLocationProviderClient mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            enable_location();
            return;
        }

        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {

                            // if we successfully get the location of the passenger then we will save the locatio into
                            //locally and go to the Main view
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(String.valueOf(Constants.LATITUDE), "" + location.getLatitude());
                            editor.putString(String.valueOf(Constants.LONGITUDE), "" + location.getLongitude());
                            editor.apply();
                            Constants.LATITUDE = location.getLatitude();
                            Constants.LONGITUDE = location.getLongitude();
                            startActivity(new Intent(SplashActivity.this, MainActivity.class));
                            overridePendingTransition(R.anim.from_right, R.anim.to_left);
                            finish();
                        } else {

                            if (sharedPreferences.getString(String.valueOf(Constants.LATITUDE), "").equals("") || sharedPreferences.getString(String.valueOf(Constants.LONGITUDE), "").equals("")) {
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(String.valueOf(Constants.LATITUDE), "33.738045");
                                editor.putString(String.valueOf(Constants.LONGITUDE), "73.084488");
                                editor.apply();
                                Constants.LATITUDE = Double.valueOf("33.738045");
                                Constants.LONGITUDE = Double.valueOf("73.084488");
                            }
                            startActivity(new Intent(SplashActivity.this, MainActivity.class));
                            overridePendingTransition(R.anim.from_right, R.anim.to_left);
                            finish();
                        }
                    }
                });
    }


    private void enable_location() {
        EnableLlocationFragment enable_llocationFragment = new EnableLlocationFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.from_right, R.anim.to_left, R.anim.from_left, R.anim.to_right);
        getSupportFragmentManager().popBackStackImmediate();
        transaction.replace(R.id.splash, enable_llocationFragment).addToBackStack(null).commit();

    }

    private void removeNotif() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Objects.requireNonNull(notificationManager).cancel(0);
    }


}
