package com.antar.passenger.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.antar.passenger.R;
import com.antar.passenger.constants.BaseApp;
import com.antar.passenger.json.FcmKeyResponseJson;
import com.antar.passenger.json.FcmRequestJson;
import com.antar.passenger.utils.api.ServiceGenerator;
import com.antar.passenger.utils.api.service.UserService;
import com.antar.passenger.utils.local_interface.OnBpjsItemClick;
import com.antar.passenger.item.TopUpBPJSHistoryItem;
import com.antar.passenger.json.MobilePulsaHealthBPJSBaseResponse;
import com.antar.passenger.json.MobilePulsaHealthBPJSResponseModel;
import com.antar.passenger.json.MobileTopUpPostPaidStatusJson;
import com.antar.passenger.json.fcm.FCMMessage;
import com.antar.passenger.models.Notif;
import com.antar.passenger.models.User;
import com.antar.passenger.utils.ProjectUtils;
import com.antar.passenger.utils.SettingPreference;
import com.antar.passenger.utils.Utility;
import com.antar.passenger.utils.api.FCMHelper;
import com.antar.passenger.utils.api.MobilePulsaApiHelper;
import com.antar.passenger.utils.api.service.ApiListener;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopUpBpjsActivity extends AppCompatActivity implements OnBpjsItemClick {
    ImageView ivBack;
    Spinner spinner;
    EditText etIdBpjs;
    Button submit;
    private String keyss;
    RelativeLayout rlProgress,rlnotif;
    String disableBack;
    TextView notif;
    SettingPreference sp;
    RecyclerView rcBpjsHistory;
    LinearLayout llHistory;
    List<MobilePulsaHealthBPJSResponseModel> mobileBPJSHistory = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_up_bpjs);
        getkey();
        ivBack = findViewById(R.id.ivBack);
        spinner = findViewById(R.id.spinnerListDates);
        etIdBpjs = findViewById(R.id.etIdBpjs);
        submit = findViewById(R.id.submit);
        rlnotif = findViewById(R.id.rlnotif);
        rlProgress = findViewById(R.id.rlprogress);
        notif = findViewById(R.id.textnotif);
        rcBpjsHistory = findViewById(R.id.rcBpjsTopUp);
        llHistory = findViewById(R.id.llListPastTopUp);
        
        sp = new SettingPreference(this);
        initSpinner();

        ivBack.setOnClickListener(v -> finish());
        submit.setOnClickListener(v -> {
            progressShow();
            checkBpjsNumber();
        });
    }

    private void  getkey() {
        User loginUser = BaseApp.getInstance(TopUpBpjsActivity.this).getLoginUser();
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

    private void checkBpjsNumber() {
        MobilePulsaApiHelper.checkHealthBPJS(etIdBpjs.getText().toString(),
                spinner.getSelectedItemPosition() + 1, sp).enqueue(new Callback<MobilePulsaHealthBPJSBaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<MobilePulsaHealthBPJSBaseResponse> cal, @NonNull Response<MobilePulsaHealthBPJSBaseResponse> response) {
                progressHide();
                if (response.isSuccessful()) {
                    if (response.body() != null &&
                            response.body().getData().getMessage().equalsIgnoreCase("inquiry success")) {
                        payBPJS(String.valueOf(response.body().getData().getTransactionId()));
                    } else {
                        progressHide();
                        notif(Objects.requireNonNull(response.body()).getData().getMessage());
                    }
                } else {
                    progressHide();
                    notif(Objects.requireNonNull(response.errorBody()).toString());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MobilePulsaHealthBPJSBaseResponse> call, @NonNull Throwable t) {
                progressHide();
            }
        });
    }

    private void payBPJS(String trId) {
        MobilePulsaApiHelper.payBPJSKes(trId,sp).enqueue(new Callback<MobilePulsaHealthBPJSBaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<MobilePulsaHealthBPJSBaseResponse> call,@NonNull Response<MobilePulsaHealthBPJSBaseResponse> response) {
                progressHide();
                if (response.isSuccessful()) {
                    if (response.body() != null &&
                            response.body().getData().getMessage().equalsIgnoreCase("PAYMENT SUCCESS")) {
                        MobilePulsaHealthBPJSResponseModel data = response.body().getData();
                        sp.updateBPJSList(data);
                        MobilePulsaApiHelper.trackUserTopUp(String.valueOf(data.getSellingPrice()), "BPJS", etIdBpjs.getText().toString(),
                                TopUpBpjsActivity.this, sp, new ApiListener() {
                                    @Override
                                    public void onSuccess() {
                                        Notif notif = new Notif();
                                        notif.title = "Topup";
                                        notif.message = "Permintaan pengisian telah berhasil, kami akan mengirimkan pemberitahuan setelah kami mengkonfirmasi";
                                        final User user = BaseApp.getInstance(TopUpBpjsActivity.this).getLoginUser();
                                        sendNotif(user.getToken(), notif);
                                        finish();
                                    }

                                    @Override
                                    public void onError() {
                                        notif(Objects.requireNonNull(response.body()).getData().getMessage());
                                    }
                                });
                    } else {
                        notif(Objects.requireNonNull(response.body()).getData().getMessage());
                    }
                } else {
                    notif(Objects.requireNonNull(response.errorBody()).toString());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MobilePulsaHealthBPJSBaseResponse> call, @NonNull Throwable t) {
                progressHide();
            }
        });
    }

    private void initSpinner() {
        ArrayAdapter<String> nominalListAdapter =
                new ArrayAdapter<>(this, R.layout.dialog_spinner_item, ProjectUtils.getNext12MonthList());
        nominalListAdapter.setDropDownViewResource(R.layout.dialog_spinner_dropdown_item);
        spinner.setAdapter(nominalListAdapter);

        initRcView();
    }

    private void initRcView() {
        if (sp.getSetting()[11] != null && !sp.getSetting()[11].isEmpty()) {
            llHistory.setVisibility(View.VISIBLE);
            Type listOfMyClassObject = new TypeToken<ArrayList<MobilePulsaHealthBPJSResponseModel>>() {}.getType();
            mobileBPJSHistory.addAll(new Gson().fromJson(sp.getSetting()[11], listOfMyClassObject));

            rcBpjsHistory.setHasFixedSize(true);
            rcBpjsHistory.setNestedScrollingEnabled(false);
            rcBpjsHistory.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

            TopUpBPJSHistoryItem item = new TopUpBPJSHistoryItem(mobileBPJSHistory, this);
            rcBpjsHistory.setAdapter(item);
        } else {
            llHistory.setVisibility(View.GONE);
        }
    }

    public void notif(String text) {
        rlnotif.setVisibility(View.VISIBLE);
        notif.setText(text);

        new Handler().postDelayed(() -> rlnotif.setVisibility(View.GONE), 3000);
    }

    private void progressShow() {
        rlProgress.setVisibility(View.VISIBLE);
        disableBack = "true";
    }

    private void progressHide() {
        rlProgress.setVisibility(View.GONE);
        disableBack = "false";
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
    public void onItemClick(MobilePulsaHealthBPJSResponseModel model) {
        progressShow();
        MobilePulsaApiHelper.checkPostPaidStatus(model.getReferenceId(),sp).enqueue(new Callback<MobileTopUpPostPaidStatusJson>() {
            @Override
            public void onResponse(@NonNull Call<MobileTopUpPostPaidStatusJson> call, @NonNull Response<MobileTopUpPostPaidStatusJson> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        progressHide();
                        MobileTopUpPostPaidStatusJson.MobileTOpUpPostPaidDetailJson data = response.body().getData();

                        final Dialog dialog = new Dialog(TopUpBpjsActivity.this);
                        dialog.setContentView(R.layout.dialog_payment_details);
                        TextView tvPel = dialog.findViewById(R.id.idPelanggan);
                        TextView tvTotal = dialog.findViewById(R.id.tvTotal);
                        TextView titlePel = dialog.findViewById(R.id.tvIdPel);

                        titlePel.setText("STATUS");
                        tvPel.setText(data.getMessage());

                        int total = data.getPrice() + 600;
                        tvTotal.setText(new StringBuilder().append(data.getPeriod()).append(" bulan\n").append(Utility.convertCurrency(String.valueOf(total), TopUpBpjsActivity.this)).toString());
                        dialog.show();
                    } else {
                        progressHide();
                        notif(Objects.requireNonNull(response.errorBody()).toString());
                    }
                } else {
                    progressHide();
                    notif("error, silahkan cek data akun anda!");
                }
            }

            @Override
            public void onFailure(@NonNull Call<MobileTopUpPostPaidStatusJson> call, @NonNull Throwable t) {
                progressHide();
                notif("error, silahkan cek data akun anda!");
            }
        });
    }
}
