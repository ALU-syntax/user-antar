package com.antar.passenger.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.antar.passenger.R;
import com.antar.passenger.constants.BaseApp;
import com.antar.passenger.item.MyVoucherItem;
import com.antar.passenger.item.SelectVoucherItem;
import com.antar.passenger.json.UserVoucherRequestJson;
import com.antar.passenger.json.UserVoucherResponseJson;
import com.antar.passenger.models.User;
import com.antar.passenger.utils.api.ServiceGenerator;
import com.antar.passenger.utils.api.service.UserService;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectVoucherActivity extends AppCompatActivity {

    ImageView ivBackBtn;
    RecyclerView rvVoucher;
    SelectVoucherItem selectVoucherItem;
    int fiturId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_voucher);

        ivBackBtn = findViewById(R.id.iv_back_btn);
        rvVoucher = findViewById(R.id.rv_voucher);

        ivBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        fiturId = intent.getIntExtra("fiturId", -1);


        User loginUser = BaseApp.getInstance(this).getLoginUser();
        UserService service = ServiceGenerator.createService(UserService.class, loginUser.getEmail(), loginUser.getPassword());
        UserVoucherRequestJson param = new UserVoucherRequestJson();
        param.setId(loginUser.getId());

        service.userVoucher(param).enqueue(new Callback<UserVoucherResponseJson>() {
            @Override
            public void onResponse(Call<UserVoucherResponseJson> call, Response<UserVoucherResponseJson> response) {
                if(Objects.requireNonNull(response.body()).getMyVoucherResponseJson().isEmpty()){
//                  rlnodata.setVisibility(View.VISIBLE);
                }else{
                    selectVoucherItem = new SelectVoucherItem(response.body().getMyVoucherResponseJson(), SelectVoucherActivity.this, R.layout.item_select_voucher, SelectVoucherActivity.this, fiturId);
                    rvVoucher.setHasFixedSize(true);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SelectVoucherActivity.this, LinearLayoutManager.VERTICAL, false);
                    rvVoucher.setLayoutManager(linearLayoutManager);
                    rvVoucher.setAdapter(selectVoucherItem);
                }
            }

            @Override
            public void onFailure(Call<UserVoucherResponseJson> call, Throwable t) {

            }
        });
    }
}