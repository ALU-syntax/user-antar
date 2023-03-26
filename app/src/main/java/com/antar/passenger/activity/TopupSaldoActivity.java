package com.antar.passenger.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.antar.passenger.utils.LocaleHelper;
import com.duitku.sdk.DuitkuCallback.DuitkuCallbackTransaction;
import com.duitku.sdk.DuitkuClient;
import com.duitku.sdk.DuitkuUtility.BaseKitDuitku;
import com.duitku.sdk.DuitkuUtility.DuitkuKit;
import com.duitku.sdk.Model.ItemDetails;
import com.antar.passenger.R;
import com.antar.passenger.constants.BaseApp;
import com.antar.passenger.json.FcmKeyResponseJson;
import com.antar.passenger.json.FcmRequestJson;
import com.antar.passenger.json.fcm.FCMMessage;
import com.antar.passenger.models.Notif;
import com.antar.passenger.models.User;
import com.antar.passenger.utils.SettingPreference;
import com.antar.passenger.utils.api.FCMHelper;
import com.antar.passenger.utils.api.ServiceGenerator;
import com.antar.passenger.utils.api.service.UserService;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TopupSaldoActivity extends DuitkuClient {
    private String keyss;
    EditText et_nominal;
    ImageView text1, text2, text3, text4;
    RelativeLayout rlnotif, rlprogress;
    TextView textnotif;
    String disableback;
    LinearLayout banktransfer;
    SettingPreference sp;
    ImageView backBtn;
    boolean debug;
    int isi_saldo = 0;
    private String paymentAmount;

    DuitkuKit duitku ;
    DuitkuCallbackTransaction callbackKit ;
    User loginUser;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topup);
        getkey();
        loginUser = BaseApp.getInstance(TopupSaldoActivity.this).getLoginUser();

        duitku = new DuitkuKit();
        callbackKit = new DuitkuCallbackTransaction();

        View bottom_sheet = findViewById(R.id.bottom_sheet);
        BottomSheetBehavior.from(bottom_sheet);
        sp = new SettingPreference(this);

        et_nominal = findViewById(R.id.saldo);
        text1 = findViewById(R.id.text1);
        text2 = findViewById(R.id.text2);
        text3 = findViewById(R.id.text3);
        text4 = findViewById(R.id.text4);
        rlnotif = findViewById(R.id.rlnotif);
        textnotif = findViewById(R.id.textnotif);
        rlprogress = findViewById(R.id.rlprogress);
        backBtn = findViewById(R.id.back_btn);
        banktransfer = findViewById(R.id.banktransfer);

//        et_nominal.addTextChangedListener(Utility.currencyTW(et_nominal, this));

        banktransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!et_nominal.getText().toString().isEmpty()) {
                    settingMerchant();
                    startPayment(TopupSaldoActivity.this); //optional
                } else {
                    notif("nominal tidak boleh kosong!");
                }
            }
        });


        text1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                et_nominal.setText("10.000");
                isi_saldo = 10000;
            }
        });

        text2.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                et_nominal.setText("20.000");
                isi_saldo = 20000;
            }
        });

        text3.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                et_nominal.setText("50.000");
                isi_saldo = 50000;
            }
        });

        text4.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                et_nominal.setText("100.000");
                isi_saldo = 100000;
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        disableback = "false";
    }

    private void  getkey() {
        User loginUser = BaseApp.getInstance(TopupSaldoActivity.this).getLoginUser();
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

    public void notif(String text) {
        rlnotif.setVisibility(View.VISIBLE);
        textnotif.setText(text);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                rlnotif.setVisibility(View.GONE);
            }
        }, 3000);
    }

    @Override
    public void onBackPressed() {
        if (!disableback.equals("true")) {
            finish();
        }
    }

    public String convertAngka(String value) {
        return (((((value + "")
                .replaceAll(sp.getSetting()[0], ""))
                .replaceAll(" ", ""))
                .replaceAll(",", ""))
                .replaceAll("[$.]", ""));
    }



    public void progressshow() {
        rlprogress.setVisibility(View.VISIBLE);
        disableback = "true";
    }

    public void progresshide() {
        rlprogress.setVisibility(View.GONE);
        disableback = "false";
    }

    private void sendNotif(final String regIDTujuan, final Notif notif) {

        final FCMMessage message = new FCMMessage();
        message.setTo(regIDTujuan);
        message.setData(notif);

        FCMHelper.sendMessage(keyss, message).enqueue(new okhttp3.Callback() {
            @Override
            public void onResponse(@NonNull okhttp3.Call call, @NonNull okhttp3.Response response) {
            }

            @Override
            public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void onSuccessTransaction(String status, String reference, String amount, String Code) {
        Toast.makeText(TopupSaldoActivity.this,"Transaction"+status,Toast.LENGTH_LONG).show();

        clearSdkTask(); //REQUIRED
        super.onSuccessTransaction(status, reference, amount, Code);
        Notif notif = new Notif();
        notif.title = "Topup Sukses";
        notif.message = "topup berhasil diproses";
        sendNotif(loginUser.getToken(), notif);
    }

    @Override
    public void onPendingTransaction(String status, String reference, String amount, String Code) {
        Toast.makeText(TopupSaldoActivity.this,"Transaction"+status,Toast.LENGTH_LONG).show();
        clearSdkTask(); //REQUIRED
        super.onPendingTransaction(status, reference, amount, Code);
        Notif notif = new Notif();
        notif.title = "Topup Pending";
        notif.message = "topup Pending";
        sendNotif(loginUser.getToken(), notif);
    }

    @Override
    public void onCancelTransaction(String status, String reference, String amount, String Code) {
        Toast.makeText(TopupSaldoActivity.this,"Transaction :"+status,Toast.LENGTH_LONG).show();

        clearSdkTask(); //REQUIRED
        super.onCancelTransaction(status, reference, amount, Code);
        Notif notif = new Notif();
        notif.title = "Topup Batal";
        notif.message = "topup dibatalkan";
        sendNotif(loginUser.getToken(), notif);
    }

    protected void onResume() {
        super.onResume();
        run(TopupSaldoActivity.this);

    }
    private void settingMerchant(){

        //set false if callback from duitku
        callbackKit.setCallbackFromMerchant(true);

        int nominal = isi_saldo;
        run(TopupSaldoActivity.this);
        //set base url merchant
        BaseKitDuitku.setBaseUrlApiDuitku("https://admin.bumdes-ku.com/duitku/");
        BaseKitDuitku.setUrlRequestTransaction("RequestTransaction.php");
        BaseKitDuitku.setUrlCheckTransaction("CheckTransaction.php");
        BaseKitDuitku.setUrlListPayment("ListPayment.php");

        duitku.setPaymentAmount(nominal);
        duitku.setProductDetails("Topup Saldo");
        duitku.setEmail(loginUser.getEmail());
        duitku.setPhoneNumber(loginUser.getNoTelepon());
        duitku.setAdditionalParam(loginUser.getId()); //optional
        duitku.setMerchantUserInfo(loginUser.getId()); //optional
        duitku.setCustomerVaName(loginUser.getFullnama());
        duitku.setExpiryPeriod("10");
        duitku.setCallbackUrl("https://admin.bumdes-ku.com/api/Pelanggan/callbackUrl");
        duitku.setReturnUrl("https://admin.bumdes-ku.com/api/Pelanggan/returnUrl");

        //customer detail
        duitku.setFirstName(loginUser.getFullnama());//optional //mandatory if indodana
        duitku.setLastName("");//optional //mandatory if indodana

        //address
        duitku.setAddress(loginUser.getAlamat());//optional //mandatory if indodana
        duitku.setCity("");//optional //mandatory if indodana
        duitku.setPostalCode(loginUser.getId());//optional //mandatory if indodana
        duitku.setCountryCode(loginUser.getCountrycode());//optional //mandatory if indodana


        //set item details
        ItemDetails itemDetails = new ItemDetails(nominal,1,"topup poin");
        ArrayList<ItemDetails> arrayList = new  ArrayList<ItemDetails> ();
        arrayList.add(itemDetails);
        duitku.setItemDetails(arrayList);
    }

}
