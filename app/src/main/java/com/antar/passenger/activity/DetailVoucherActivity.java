package com.antar.passenger.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.antar.passenger.R;
import com.antar.passenger.constants.BaseApp;
import com.antar.passenger.constants.Constants;
import com.antar.passenger.json.BuyVoucherRequestJson;
import com.antar.passenger.json.BuyVoucherResponse;
import com.antar.passenger.json.PoinRequestJson;
import com.antar.passenger.json.ResponseJson;
import com.antar.passenger.models.User;
import com.antar.passenger.utils.LocaleHelper;
import com.antar.passenger.utils.PicassoTrustAll;
import com.antar.passenger.utils.Utility;
import com.antar.passenger.utils.api.ServiceGenerator;
import com.antar.passenger.utils.api.service.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailVoucherActivity extends AppCompatActivity {
    TextView tvDesc, tvVoucherName, tvVoucherPrice, tvVoucherExpired, tvMinimumOrder, tvVoucherType
            ,tvQuantityVoucher, tvNominalVoucher, tvTopUp, tvSaldo;
    ImageView ivVoucher;
    AppCompatButton btnBuy;

    private String saldoWallet;
    Dialog dialog;
    LinearLayout llWalet, llBankTransfer;
    RelativeLayout rlcancel, rlorder;

    String voucherName, voucherPrice, voucherDesc, voucherExpired, voucherPoster, voucherQuantity, voucherNominal
            ,voucherMinimumOrder, voucherType, idVoucher, voucherQuantityWithoutString;

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
        tvNominalVoucher = findViewById(R.id.tv_nominal);
        ivVoucher = findViewById(R.id.iv_voucher_poster);
        btnBuy = findViewById(R.id.btn_buy);


        dialog = new Dialog(this);

        voucherName = getIntent().getStringExtra("voucherName");
        voucherPrice = getIntent().getStringExtra("voucherPrice");
        voucherDesc = getIntent().getStringExtra("voucherDesc");
        voucherExpired = getString(R.string.voucher_expired) +  getIntent().getStringExtra("voucherExpired");
        voucherPoster = getIntent().getStringExtra("voucherPoster");
        voucherQuantity = getString(R.string.voucher_jumlah) +  getIntent().getStringExtra("voucherQuantity");
        voucherNominal = getIntent().getStringExtra("voucherNominal");
        voucherMinimumOrder = getString(R.string.voucher_minimum) +  getIntent().getStringExtra("voucherMinimumOrder");
        voucherType = getString(R.string.voucher_type) +  getIntent().getStringExtra("voucherType");
        idVoucher = getIntent().getStringExtra("idVoucher");
        voucherQuantityWithoutString = getIntent().getStringExtra("voucherQuantity");

        tvDesc.setText(voucherDesc);
        tvVoucherName.setText(voucherName);
        Utility.convertLocaleCurrencyTV(tvVoucherPrice, this, voucherPrice, getString(R.string.voucher_price));
        tvVoucherExpired.setText(voucherExpired);
        tvMinimumOrder.setText(voucherMinimumOrder);
        tvVoucherType.setText(voucherType);
        tvQuantityVoucher.setText(voucherQuantity);
        Utility.convertLocaleCurrencyTV(tvNominalVoucher, this, voucherNominal, getString(R.string.voucher_potongan) );

        if(!voucherPoster.isEmpty()){
            PicassoTrustAll.getInstance(this)
                    .load(Constants.IMAGESSLIDER + voucherPoster)
                    .into(ivVoucher);
        }

        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPaymentMethodDialog();
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase));
    }

    private void showPaymentMethodDialog(){
        dialog.setContentView(R.layout.dialog_payment_method);
        dialog.setTitle("Choose Payment");
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        User userLogin = BaseApp.getInstance(this).getLoginUser();
        saldoWallet = String.valueOf(userLogin.getWalletSaldo());
        int nominalSaldoWallet = (int) userLogin.getWalletSaldo();
        System.out.println("debugSaldoWallet: " + userLogin.getWalletSaldo());
        System.out.println("debugHargaVoucher: " + voucherNominal);
        int hargaVoucher = Integer.parseInt(voucherPrice);
        boolean check = nominalSaldoWallet < hargaVoucher;

        System.out.println("debugIf: " + check);

        llBankTransfer = dialog.findViewById(R.id.llcheckedtransfer);
        llWalet = dialog.findViewById(R.id.llcheckedwallet);
        rlcancel = dialog.findViewById(R.id.rlcancel);
        tvTopUp = dialog.findViewById(R.id.tv_topUp);
        rlcancel = dialog.findViewById(R.id.rlcancel);
//        rlorder = dialog.findViewById(R.id.order);
        tvSaldo = dialog.findViewById(R.id.saldo_wallet);

        Utility.convertLocaleCurrencyTV(tvSaldo, DetailVoucherActivity.this, saldoWallet);

        rlcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        llBankTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DetailVoucherActivity.this, "Untuk Alasan Keamanan, sementara ini pembelian voucher via bank ditutup", Toast.LENGTH_LONG).show();
            }
        });

        llWalet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nominalSaldoWallet<hargaVoucher){
                    Toast.makeText(DetailVoucherActivity.this, "Saldo Tidak Cukup", Toast.LENGTH_LONG).show();
                }else{
                    buyVoucher();
                }

            }
        });

        tvTopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetailVoucherActivity.this, TopupSaldoActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        dialog.show();

    }

    private void buyVoucher(){
        User loginUser = BaseApp.getInstance(DetailVoucherActivity.this).getLoginUser();
        UserService userService = ServiceGenerator.createService(
                UserService.class, loginUser.getNoTelepon(), loginUser.getPassword());
        BuyVoucherRequestJson request = new BuyVoucherRequestJson();
        request.setId(loginUser.getId());
        request.setBank("Wallet");
        request.setName(loginUser.getFullnama());
        request.setAmount(voucherPrice);
        request.setCard("Wallet");
        request.setEmail(loginUser.getEmail());
        request.setNotelepon(loginUser.getPhone());
        request.setIdVoucher(idVoucher);
        request.setType("buyvoucher");
        request.setQuantityVoucher(voucherQuantityWithoutString);

        userService.buyVoucher(request).enqueue(new Callback<BuyVoucherResponse>() {
            @Override
            public void onResponse(Call<BuyVoucherResponse> call, Response<BuyVoucherResponse> response) {
                Toast.makeText(DetailVoucherActivity.this, "Berhasil Membeli Voucher", Toast.LENGTH_LONG).show();
                Intent i = new Intent(DetailVoucherActivity.this, MainActivity.class);
                dialog.dismiss();
                startActivity(i);
                finish();
            }

            @Override
            public void onFailure(Call<BuyVoucherResponse> call, Throwable t) {

            }
        });

    }
}