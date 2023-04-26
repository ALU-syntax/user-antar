package com.antar.passenger.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.antar.passenger.R;
import com.antar.passenger.constants.BaseApp;
import com.antar.passenger.item.ItemVoucher;
import com.antar.passenger.item.MyVoucherItem;
import com.antar.passenger.json.MyVoucherResponseJson;
import com.antar.passenger.json.UserVoucherRequestJson;
import com.antar.passenger.json.UserVoucherResponseJson;
import com.antar.passenger.json.VoucherResponseJson;
import com.antar.passenger.models.User;
import com.antar.passenger.models.VoucherModel;
import com.antar.passenger.utils.api.ServiceGenerator;
import com.antar.passenger.utils.api.service.UserService;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyVoucherActivity extends AppCompatActivity {

    ImageView ivBackButton;
    RecyclerView rvVoucher;
    MyVoucherItem myVoucherItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_voucher);

        ivBackButton = findViewById(R.id.iv_back_btn);
        rvVoucher = findViewById(R.id.rv_voucher);

        ivBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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

//                    VoucherModel voucherModel = new VoucherModel();
//                    for(MyVoucherResponseJson myVoucher : response.body().getMyVoucherResponseJson()){
//                        voucherModel.setId(myVoucher.getIdVoucherPromo());
//                        voucherModel.setVoucherName(myVoucher.getNamaVoucherPromo());
//                        voucherModel.setDescription(myVoucher.getDescription());
//                        voucherModel.setHargaVoucher(myVoucher.getHargaVoucher());
//                        voucherModel.setVoucherNominal(myVoucher.getNominalVoucherPromo());
//                        voucherModel.setVoucherType(myVoucher.getTypeVoucherPromo());
//                        voucherModel.setIsiVoucher(myVoucher.getQuantity());
//                        voucherModel.setMinimumTransaksi(myVoucher.getMinimumTransaksi());
//                        voucherModel.setExpired(myVoucher.getExpired());
//                        voucherModel.setFitur(myVoucher.getFitur());
//                        voucherModel.setImageVoucher(myVoucher.getImageVoucherPromo());
//                        voucherModel.setStatus(myVoucher.getStatus());
//                    }
                    myVoucherItem = new MyVoucherItem(response.body().getMyVoucherResponseJson(), R.layout.item_my_voucher, MyVoucherActivity.this);
                    rvVoucher.setHasFixedSize(true);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyVoucherActivity.this, LinearLayoutManager.VERTICAL, false);
                    rvVoucher.setLayoutManager(linearLayoutManager);
                    rvVoucher.setAdapter(myVoucherItem);
                }
            }

            @Override
            public void onFailure(Call<UserVoucherResponseJson> call, Throwable t) {

            }
        });
    }
}