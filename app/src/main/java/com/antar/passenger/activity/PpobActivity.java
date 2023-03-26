package com.antar.passenger.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.antar.passenger.R;
import com.antar.passenger.utils.LocaleHelper;


public class PpobActivity extends AppCompatActivity {

    private Context context;
    Button pulsa, token, bpjs;
    TextView notif;
    ImageView backbtn, images;
    RelativeLayout rlnotif, rlprogress;
    String disableback;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ppob);
        disableback = "false";
        backbtn = findViewById(R.id.back_btn);
        pulsa = findViewById(R.id.pulsa);
        token = findViewById(R.id.token);
        rlnotif = findViewById(R.id.rlnotif);
        notif = findViewById(R.id.textnotif);
        rlprogress = findViewById(R.id.rlprogress);
        images = findViewById(R.id.imagebackground);


        pulsa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PpobActivity.this, TopUpPulsaActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

            }
        });

        token.setOnClickListener(v -> {
            Intent i = new Intent(PpobActivity.this, TopUpPlnActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);

        });


        backbtn.setOnClickListener(v -> finish());
    }
}
