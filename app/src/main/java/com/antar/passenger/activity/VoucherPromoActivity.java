package com.antar.passenger.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.antar.passenger.R;
import com.antar.passenger.constants.BaseApp;
import com.antar.passenger.item.ItemVoucher;
import com.antar.passenger.item.PromoItem;
import com.antar.passenger.json.GetFiturResponseJson;
import com.antar.passenger.json.PromoRequestJson;
import com.antar.passenger.json.PromoResponseJson;
import com.antar.passenger.json.VoucherResponseJson;
import com.antar.passenger.models.User;
import com.antar.passenger.models.VoucherModel;
import com.antar.passenger.utils.LocaleHelper;
import com.antar.passenger.utils.api.ServiceGenerator;
import com.antar.passenger.utils.api.service.UserService;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VoucherPromoActivity extends AppCompatActivity {
    public List<VoucherModel> listVoucherModel;
    VoucherModel voucherModel;
    RecyclerView rvListVoucher;
    ItemVoucher itemVoucher;
    ImageView ivBackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher_promo);

        rvListVoucher = findViewById(R.id.rv_voucher);
        ivBackBtn = findViewById(R.id.iv_back_btn);

        ivBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        rvListVoucher.setHasFixedSize(true);
        GridLayoutManager gridLayout = new GridLayoutManager(this, 2);
        rvListVoucher.setLayoutManager(gridLayout);
        getData();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase));
    }

    private void getData() {
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        UserService service = ServiceGenerator.createService(UserService.class, loginUser.getEmail(), loginUser.getPassword());
        VoucherResponseJson param = new VoucherResponseJson();

        service.getVoucherPromo().enqueue(new Callback<VoucherResponseJson>() {
            @Override
            public void onResponse(Call<VoucherResponseJson> call, Response<VoucherResponseJson> response) {
                if(Objects.requireNonNull(response.body()).getVoucherModelList().isEmpty()){
//                    rlnodata.setVisibility(View.VISIBLE);
                }else{
                    itemVoucher = new ItemVoucher(response.body().getVoucherModelList(), R.layout.item_voucher, VoucherPromoActivity.this);
                    rvListVoucher.setAdapter(itemVoucher);
                }
            }

            @Override
            public void onFailure(Call<VoucherResponseJson> call, Throwable t) {

            }
        });
    }
}