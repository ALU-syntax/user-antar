package com.antar.passenger.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.antar.passenger.R;
import com.antar.passenger.constants.Constants;
import com.antar.passenger.utils.LocaleHelper;
import com.antar.passenger.utils.PicassoTrustAll;
import com.antar.passenger.utils.Utility;

public class DetailVoucherActivity extends AppCompatActivity {
    TextView tvDesc, tvVoucherName, tvVoucherPrice, tvVoucherExpired, tvMinimumOrder, tvVoucherType
            ,tvQuantityVoucher;
    ImageView ivVoucher;
    AppCompatButton btnBuy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_voucher);
        tvDesc = findViewById(R.id.tv_description);
        tvVoucherName = findViewById(R.id.tv_voucher_name);
        tvVoucherPrice = findViewById(R.id.tv_voucher_price);
        tvVoucherExpired = findViewById(R.id.tv_expired);
        tvMinimumOrder = findViewById(R.id.tv_minimum_order);
        tvVoucherType = findViewById(R.id.tv_type);
        tvQuantityVoucher = findViewById(R.id.tv_isi_voucher);
        ivVoucher = findViewById(R.id.iv_voucher_poster);
        btnBuy = findViewById(R.id.btn_buy);

        String voucherName = getIntent().getStringExtra("voucherName");
        String voucherPrice = getIntent().getStringExtra("voucherPrice");
        String voucherDesc = getIntent().getStringExtra("voucherDesc");
        String voucherExpired = getIntent().getStringExtra("voucherExpired");
        String voucherPoster = getIntent().getStringExtra("voucherPoster");
        String voucherQuantity = getIntent().getStringExtra("voucherQuantity");
        String voucherNominal = getIntent().getStringExtra("voucherNominal");
        String voucherMinimumOrder = getIntent().getStringExtra("voucherMinimumOrder");
        String voucherType = getIntent().getStringExtra("voucherType");

        tvDesc.setText(voucherDesc);
        tvVoucherName.setText(voucherName);
        Utility.convertLocaleCurrencyTV(tvVoucherPrice, this, voucherPrice);
        tvVoucherExpired.setText(voucherExpired);
        tvMinimumOrder.setText(String.valueOf(voucherMinimumOrder));
        tvVoucherType.setText(voucherType);
        tvQuantityVoucher.setText(String.valueOf(voucherQuantity));


        if(!voucherPoster.isEmpty()){
            PicassoTrustAll.getInstance(this)
                    .load(Constants.IMAGESSLIDER + voucherPoster)
                    .into(ivVoucher);
        }

        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase));
    }
}