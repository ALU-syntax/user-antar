package com.insoftbumdesku.passenger.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.insoftbumdesku.passenger.R;
import com.insoftbumdesku.passenger.constants.BaseApp;
import com.insoftbumdesku.passenger.item.WilayahItem;
import com.insoftbumdesku.passenger.json.DaftarWilayahRequestJson;
import com.insoftbumdesku.passenger.json.DaftarWilayahResponseJson;
import com.insoftbumdesku.passenger.mine.wilayahinterface;
import com.insoftbumdesku.passenger.models.User;
import com.insoftbumdesku.passenger.utils.SessionWilayah;
import com.insoftbumdesku.passenger.utils.api.ServiceGenerator;
import com.insoftbumdesku.passenger.utils.api.service.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PilihWilayahActivity extends AppCompatActivity implements wilayahinterface {
    private RecyclerView rvwilayah;
    private SessionWilayah sessionWilayah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_wilayah);
        sessionWilayah = new SessionWilayah(this);
        rvwilayah = findViewById(R.id.rvwilayah);
        fetchdata();
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rvwilayah.setLayoutManager(llm);

    }

    private void fetchdata() {
        User loginuser = BaseApp.getInstance(this).getLoginUser();
        UserService userservice = ServiceGenerator.createService(
                UserService.class, loginuser.getNoTelepon(), loginuser.getPassword()
        );
        DaftarWilayahRequestJson param = new DaftarWilayahRequestJson();
        param.setWilayah(1);
        userservice.daftarwilayah(param).enqueue(new Callback<DaftarWilayahResponseJson>() {
            @Override
            public void onResponse(Call<DaftarWilayahResponseJson> call, Response<DaftarWilayahResponseJson> response) {
                if(response.isSuccessful()) {
                    WilayahItem item = new WilayahItem(PilihWilayahActivity.this, response.body().getData(), PilihWilayahActivity.this);
                    item.notifyDataSetChanged();
                    rvwilayah.setAdapter(item);
                }
            }

            @Override
            public void onFailure(Call<DaftarWilayahResponseJson> call, Throwable t) {

            }
        });
    }

    @Override
    public void updatewilayah(int id, String namacabang) {
        sessionWilayah.createSession(String.valueOf(id), namacabang);
        Intent intent = new Intent(PilihWilayahActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}