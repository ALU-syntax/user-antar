package com.insoftbumdesku.passenger.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.insoftbumdesku.passenger.R;
import com.insoftbumdesku.passenger.utils.SettingPreference;

public class WaActivity extends AppCompatActivity {

    EditText deskripsi;
    Button submit;
    TextView notif;
    ImageView backbtn, images;
    RelativeLayout rlnotif, rlprogress;
    String disableback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wa);
        disableback = "false";
        deskripsi = findViewById(R.id.deskripsi);
        backbtn = findViewById(R.id.back_btn);
        submit = findViewById(R.id.submit);
        rlnotif = findViewById(R.id.rlnotif);
        notif = findViewById(R.id.textnotif);
        rlprogress = findViewById(R.id.rlprogress);
        images = findViewById(R.id.imagebackground);


        submit.setOnClickListener(v -> {
            String isipesan = deskripsi.getText().toString();
            SettingPreference sp = new SettingPreference(v.getContext());
            String phoneNumber = sp.getSetting()[3];
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://api.whatsapp.com/send?phone=" + "+62" + phoneNumber + "&text=" +isipesan));
            startActivity(intent);

            Toast.makeText( WaActivity.this, "install whatsapp agar bisa menghubungi team admin", Toast.LENGTH_SHORT).show();


        });
    }

    private boolean appInstalledOrNot(String url) {
        PackageManager packageManager = getPackageManager();
        boolean app_installed;
        try {
            packageManager.getPackageInfo(url, packageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

    public void onBackPressed() {
        if (!disableback.equals("true")) {
            finish();
        }
    }

    public void progressshow() {
        rlprogress.setVisibility(View.VISIBLE);
        disableback = "true";
    }

    public void progresshide() {
        rlprogress.setVisibility(View.GONE);
        disableback = "false";
    }
}


