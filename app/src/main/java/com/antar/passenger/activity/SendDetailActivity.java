package com.antar.passenger.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.antar.passenger.R;
import com.antar.passenger.constants.BaseApp;
import com.antar.passenger.json.CheckStatusTransaksiRequest;
import com.antar.passenger.json.CheckStatusTransaksiResponse;
import com.antar.passenger.json.FcmKeyResponseJson;
import com.antar.passenger.json.FcmRequestJson;
import com.antar.passenger.json.PromoRequestJson;
import com.antar.passenger.json.PromoResponseJson;
import com.antar.passenger.json.SendRequestJson;
import com.antar.passenger.json.SendResponseJson;
import com.antar.passenger.json.fcm.DriverRequest;
import com.antar.passenger.json.fcm.DriverResponse;
import com.antar.passenger.json.fcm.FCMMessage;
import com.antar.passenger.models.DriverModel;
import com.antar.passenger.models.FiturModel;
import com.antar.passenger.models.TransaksiSendModel;
import com.antar.passenger.models.User;
import com.antar.passenger.utils.LocaleHelper;
import com.antar.passenger.utils.SessionWilayah;
import com.antar.passenger.utils.Utility;
import com.antar.passenger.utils.api.FCMHelper;
import com.antar.passenger.utils.api.ServiceGenerator;
import com.antar.passenger.utils.api.service.BookService;
import com.antar.passenger.utils.api.service.UserService;
import com.google.android.gms.maps.model.LatLng;
import com.ybs.countrypicker.CountryPicker;
import com.ybs.countrypicker.CountryPickerListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.antar.passenger.activity.SendActivity.FITUR_KEY;
import static com.antar.passenger.json.fcm.FCMType.ORDER;



public class SendDetailActivity extends AppCompatActivity {

    @BindView(R.id.dokumen)
    Button dokument;
    @BindView(R.id.fashion)
    Button fashion;
    @BindView(R.id.box)
    Button box;
    @BindView(R.id.other)
    Button other;
    @BindView(R.id.otherdetail)
    EditText othertext;
    @BindView(R.id.countrycode)
    TextView countrycode;

    @BindView(R.id.countrycodereceiver)
    TextView countrycodereceiver;

    @BindView(R.id.countrycodereceiver2)
    TextView countrycodereceiver2;

    @BindView(R.id.countrycodereceiver3)
    TextView countrycodereceiver3;

    @BindView(R.id.countrycodereceiver4)
    TextView countrycodereceiver4;

    @BindView(R.id.countrycodereceiver5)
    TextView countrycodereceiver5;

    @BindView(R.id.distance)
    TextView distanceText;
    @BindView(R.id.price)
    TextView priceText;
    @BindView(R.id.topUp)
    TextView topUp;
    @BindView(R.id.cost)
    TextView cost;
    @BindView(R.id.ketsaldo)
    TextView diskontext;
    @BindView(R.id.diskon)
    TextView diskon;
    @BindView(R.id.saldo)
    TextView saldotext;
    @BindView(R.id.checkedcash)
    ImageButton checkedcash;
    @BindView(R.id.checkedwallet)
    ImageButton checkedwallet;
    @BindView(R.id.cashPayment)
    TextView cashpayment;
    @BindView(R.id.walletpayment)
    TextView walletpayment;
    @BindView(R.id.llcheckedwallet)
    LinearLayout llcheckedwallet;
    @BindView(R.id.llcheckedcash)
    LinearLayout llcheckedcash;
    @BindView(R.id.fitur)
    TextView fiturtext;
    @BindView(R.id.sendername)
    EditText sendername;
    @BindView(R.id.recievername)
    EditText recievername;

    @BindView(R.id.recievername2)
    EditText recievername2;
    @BindView(R.id.recievername3)
    EditText recievername3;
    @BindView(R.id.recievername4)
    EditText recievername4;
    @BindView(R.id.recievername5)
    EditText recievername5;


    @BindView(R.id.phonenumber)
    EditText senderphone;
    @BindView(R.id.phonenumberreceiever)
    EditText recieverphone;

    @BindView(R.id.phonenumberreceiever2)
    EditText recieverphone2;

    @BindView(R.id.phonenumberreceiever3)
    EditText recieverphone3;

    @BindView(R.id.phonenumberreceiever4)
    EditText recieverphone4;

    @BindView(R.id.phonenumberreceiever5)
    EditText recieverphone5;
    @BindView(R.id.rlprogress)
    RelativeLayout rlprogress;
    @BindView(R.id.rlnotif)
    RelativeLayout rlnotif;
    @BindView(R.id.textnotif)
    TextView textnotif;
    @BindView(R.id.order)
    Button order;

    @BindView(R.id.promocode)
    EditText promokode;

    @BindView(R.id.btnpromo)
    Button btnpromo;

    @BindView(R.id.promonotif)
    TextView promonotif;

    @BindView(R.id.penerima_container2)
    LinearLayout penerima_container2;

    @BindView(R.id.penerima_container3)
    LinearLayout penerima_container3;

    @BindView(R.id.penerima_container4)
    LinearLayout penerima_container4;

    @BindView(R.id.penerima_container5)
    LinearLayout penerima_container5;

    @BindView(R.id.vouchercode)
    EditText voucherPromo;

    @BindView(R.id.btnvoucher)
    Button btnVoucher;

    @BindView(R.id.vouchernotif)
    TextView voucherNotif;

    String itemdetail, fitur;
    String country_iso_code = "en";
    Context context = SendDetailActivity.this;
    Realm realm;
    TransaksiSendModel transaksi;
    Thread thread;
    boolean threadRun = true;
    private double distance;
    private LatLng pickUpLatLang;
    private LatLng destinationLatLang;
    private LatLng destinationLatLang2;
    private LatLng destinationLatLang3;
    private LatLng destinationLatLang4;
    private LatLng destinationLatLang5;
    private String pickup, icon, layanan, layanandesk;
    private String destination, destination2, destination3, destination4, destination5;
    private String biayaakhir;
    private ArrayList<DriverModel> driverAvailable;
    private FiturModel fiturModel;
    private String saldoWallet;
    private String checkedpaywallet;
    private long harga, promocode;
    private DriverRequest request;
    private SessionWilayah sessionWilayah;
    private String keyss;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase));
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_detail);
        ButterKnife.bind(this);
        getkey();
        promocode = 0;
        sessionWilayah = new SessionWilayah(this);
        realm = Realm.getDefaultInstance();
        User userLogin = BaseApp.getInstance(this).getLoginUser();
        saldoWallet = String.valueOf(userLogin.getWalletSaldo());
        Intent intent = getIntent();
        distance = intent.getDoubleExtra("distance", 0);
        String price = intent.getStringExtra("price");
        pickUpLatLang = intent.getParcelableExtra("pickup_latlng");
        destinationLatLang = intent.getParcelableExtra("destination_latlng");
        destinationLatLang2 = intent.getParcelableExtra("destination_latlng2");
        destinationLatLang3 = intent.getParcelableExtra("destination_latlng3");
        destinationLatLang4 = intent.getParcelableExtra("destination_latlng4");
        destinationLatLang5 = intent.getParcelableExtra("destination_latlng5");
        pickup = intent.getStringExtra("pickup");
        icon = intent.getStringExtra("icon");
        layanan = intent.getStringExtra("layanan");
        layanandesk = intent.getStringExtra("layanandesk");
        destination = intent.getStringExtra("destination");
        destination2 = intent.getStringExtra("destination2");
        destination3 = intent.getStringExtra("destination3");
        destination4 = intent.getStringExtra("destination4");
        destination5 = intent.getStringExtra("destination5");
        String biayaminimum = intent.getStringExtra("biaya_minimum");
        String timeDistance = intent.getStringExtra("time_distance");
        driverAvailable = (ArrayList<DriverModel>) intent.getSerializableExtra("driver");
        int selectedFitur = intent.getIntExtra(FITUR_KEY, -1);

        if (selectedFitur != -1)
            fiturModel = realm.where(FiturModel.class).equalTo("idFitur", selectedFitur).findFirst();
        assert fiturModel != null;
        fitur = String.valueOf(fiturModel.getIdFitur());

        biayaakhir = String.valueOf(fiturModel.getBiayaAkhir());
        fiturtext.setText(timeDistance);
        float km = ((float) distance);
        String format = String.format(Locale.US, "%.1f", km);
        distanceText.setText(format);
//        Utility.currencyTXT(cost, String.valueOf(price), this);
//        Utility.currencyTXT(diskon, String.valueOf(promocode), SendDetailActivity.this);
        Utility.convertLocaleCurrencyTV(cost, this, String.valueOf(price));
        Utility.convertLocaleCurrencyTV(diskon, SendDetailActivity.this, String.valueOf(promocode));
        diskontext.setText("Discount " + fiturModel.getDiskon() + " with Wallet");

        checkedpaywallet = "0";
        Log.e("CHECKEDWALLET", checkedpaywallet);
        checkedcash.setSelected(true);
        checkedwallet.setSelected(false);
        cashpayment.setTextColor(getResources().getColor(R.color.colorgradient));
        walletpayment.setTextColor(getResources().getColor(R.color.gray));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            checkedcash.setBackgroundTintList(getResources().getColorStateList(R.color.colorgradient));
            checkedwallet.setBackgroundTintList(getResources().getColorStateList(R.color.gray));
        }

        String biaya = String.valueOf(biayaminimum);
        long biayaTotal = (long) (Double.parseDouble(Objects.requireNonNull(price)) * km);
        if (biayaTotal < Double.parseDouble(Objects.requireNonNull(biayaminimum))) {
            this.harga = Long.parseLong(biayaminimum);
            biayaTotal = Long.parseLong(biayaminimum);
//            Utility.currencyTXT(cost, biaya, this);
            Utility.convertLocaleCurrencyTV(cost, this, biaya);
        } else {
//            Utility.currencyTXT(cost, price, this);
            Utility.convertLocaleCurrencyTV(cost, this, price);
        }
        this.harga = biayaTotal;

        final long finalBiayaTotal = biayaTotal;
        String totalbiaya = String.valueOf(finalBiayaTotal);
//        Utility.currencyTXT(priceText, totalbiaya, this);
        Utility.convertLocaleCurrencyTV(priceText, this, totalbiaya);

        long saldokini = Long.parseLong(saldoWallet);
        if (saldokini < (biayaTotal - (harga * Double.parseDouble(biayaakhir)))) {
            llcheckedcash.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String totalbiaya = String.valueOf(finalBiayaTotal);
//                    Utility.currencyTXT(priceText, totalbiaya, context);
//                    Utility.currencyTXT(diskon, String.valueOf(promocode), SendDetailActivity.this);
                    Utility.convertLocaleCurrencyTV(priceText, context, totalbiaya);
                    Utility.convertLocaleCurrencyTV(diskon, SendDetailActivity.this, String.valueOf(promocode));
                    checkedcash.setSelected(true);
                    checkedwallet.setSelected(false);
                    checkedpaywallet = "0";
                    Log.e("CHECKEDWALLET", checkedpaywallet);
                    cashpayment.setTextColor(getResources().getColor(R.color.colorgradient));
                    walletpayment.setTextColor(getResources().getColor(R.color.gray));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        checkedcash.setBackgroundTintList(getResources().getColorStateList(R.color.colorgradient));
                        checkedwallet.setBackgroundTintList(getResources().getColorStateList(R.color.gray));
                    }
                }
            });
        } else {
            llcheckedcash.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String totalbiaya = String.valueOf(finalBiayaTotal);
//                    Utility.currencyTXT(priceText, totalbiaya, context);
//                    Utility.currencyTXT(diskon, String.valueOf(promocode), SendDetailActivity.this);
                    Utility.convertLocaleCurrencyTV(priceText, context, totalbiaya);
                    Utility.convertLocaleCurrencyTV(diskon, SendDetailActivity.this, String.valueOf(promocode));
                    checkedcash.setSelected(true);
                    checkedwallet.setSelected(false);
                    checkedpaywallet = "0";
                    Log.e("CHECKEDWALLET", checkedpaywallet);
                    cashpayment.setTextColor(getResources().getColor(R.color.colorgradient));
                    walletpayment.setTextColor(getResources().getColor(R.color.gray));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        checkedcash.setBackgroundTintList(getResources().getColorStateList(R.color.colorgradient));
                        checkedwallet.setBackgroundTintList(getResources().getColorStateList(R.color.gray));
                    }
                }
            });

            final long finalBiayaTotal1 = biayaTotal;
            llcheckedwallet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    long diskonwallet = (long) (Double.parseDouble(biayaakhir) * harga);
                    String totalwallet = String.valueOf(diskonwallet + promocode);
//                    Utility.currencyTXT(diskon, totalwallet, context);
                    Utility.convertLocaleCurrencyTV(diskon, context, totalwallet);
                    String totalbiaya = String.valueOf(finalBiayaTotal1 - (diskonwallet + promocode));
//                    Utility.currencyTXT(priceText, totalbiaya, context);
                    Utility.convertLocaleCurrencyTV(priceText, context, totalbiaya);
                    checkedcash.setSelected(false);
                    checkedwallet.setSelected(true);
                    checkedpaywallet = "1";
                    Log.e("CHECKEDWALLET", checkedpaywallet);
                    walletpayment.setTextColor(getResources().getColor(R.color.colorgradient));
                    cashpayment.setTextColor(getResources().getColor(R.color.gray));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        checkedwallet.setBackgroundTintList(getResources().getColorStateList(R.color.colorgradient));
                        checkedcash.setBackgroundTintList(getResources().getColorStateList(R.color.gray));
                    }
//                    notif("Untuk Alasan Keamanan Untuk sementara fitur saldo kami nonaktifkan terlebih dahulu.");

                }
            });
        }

        dokument.setSelected(true);
        fashion.setSelected(false);
        box.setSelected(false);
        other.setSelected(false);
        itemdetail = "document";
        othertext.setVisibility(View.GONE);

        dokument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dokument.setSelected(true);
                fashion.setSelected(false);
                box.setSelected(false);
                other.setSelected(false);
                itemdetail = "document";
                othertext.setVisibility(View.GONE);
                othertext.setText("");
            }
        });

        fashion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dokument.setSelected(false);
                fashion.setSelected(true);
                box.setSelected(false);
                other.setSelected(false);
                itemdetail = "fashion";
                othertext.setVisibility(View.GONE);
                othertext.setText("");
            }
        });

        box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dokument.setSelected(false);
                fashion.setSelected(false);
                box.setSelected(true);
                other.setSelected(false);
                itemdetail = "box";
                othertext.setVisibility(View.GONE);
                othertext.setText("");
            }
        });

        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dokument.setSelected(false);
                fashion.setSelected(false);
                box.setSelected(false);
                other.setSelected(true);
                othertext.setVisibility(View.VISIBLE);
            }
        });

        countrycode.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                final CountryPicker picker = CountryPicker.newInstance("Select Country");
                picker.setListener(new CountryPickerListener() {
                    @Override
                    public void onSelectCountry(String name, String code, String dialCode, int flagDrawableResID) {
                        countrycode.setText(dialCode);
                        picker.dismiss();
                        country_iso_code = code;
                    }
                });
                picker.setStyle(R.style.countrypicker_style, R.style.countrypicker_style);
                picker.show(getSupportFragmentManager(), "Select Country");
            }
        });

        countrycodereceiver.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                final CountryPicker picker = CountryPicker.newInstance("Select Country");
                picker.setListener(new CountryPickerListener() {
                    @Override
                    public void onSelectCountry(String name, String code, String dialCode, int flagDrawableResID) {
                        countrycodereceiver.setText(dialCode);
                        picker.dismiss();
                        country_iso_code = code;
                    }
                });
                picker.setStyle(R.style.countrypicker_style, R.style.countrypicker_style);
                picker.show(getSupportFragmentManager(), "Select Country");
            }
        });

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sendername.getText().toString().isEmpty()) {
                    notif("Sender name cant be empty!");
                } else if (senderphone.getText().toString().isEmpty()) {
                    notif("Sender phone cant be empty!");
                } else if (recievername.getText().toString().isEmpty()) {
                    notif("Receiver cant be empty!");
                } else if (recieverphone.getText().toString().isEmpty()) {
                    notif("Receiver phone cant be empty!");
                } else if(destinationLatLang2 != null) {
                    if (recievername2.getText().toString().isEmpty()) {
                        notif("Second Receiver name cant be empty");
                    } else if (recieverphone2.getText().toString().isEmpty()) {
                        notif("Second Receiver phone cant empty");
                    } else {
                        if (destinationLatLang3 != null) {
                            if (recievername3.getText().toString().isEmpty()) {
                                notif("Third Receiver name cant be empty");
                            } else if (recieverphone3.getText().toString().isEmpty()) {
                                notif("Third Receiver phone cant empty");
                            } else {
                                if (destinationLatLang4 != null) {
                                    if (recievername4.getText().toString().isEmpty()) {
                                        notif("Fourth Receiver name cant be empty");
                                    } else if (recieverphone4.getText().toString().isEmpty()) {
                                        notif("Fourth Receiver phone cant empty");
                                    } else {
                                        if (destinationLatLang5 != null) {
                                            if (recievername5.getText().toString().isEmpty()) {
                                                notif("Fifth Receiver name cant be empty");
                                            } else if (recieverphone5.getText().toString().isEmpty()) {
                                                notif("Fifht Receiver phone cant empty");
                                            } else {
                                                onOrderButton();
                                            }
                                        }

                                    }
                                } else {
                                    onOrderButton();
                                }
                            }
                        } else {
                            onOrderButton();
                        }
                    }
                }else{
                    onOrderButton();
                }
            }
        });

        btnpromo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    Objects.requireNonNull(imm).hideSoftInputFromWindow(Objects.requireNonNull(getCurrentFocus()).getWindowToken(), 0);
                } catch (Exception ignored) {

                }
                if (promokode.getText().toString().isEmpty()) {
                    notif("Promo code cant be empty!");
                } else {
                    promokodedata();
                }
            }
        });

        if(destinationLatLang2 != null) {
            penerima_container2.setVisibility(View.VISIBLE);
        }
        if(destinationLatLang3 != null) {
            penerima_container3.setVisibility(View.VISIBLE);
        }
        if(destinationLatLang4 != null) {
            penerima_container4.setVisibility(View.VISIBLE);
        }
        if(destinationLatLang5 != null) {
            penerima_container5.setVisibility(View.VISIBLE);
        }


    }

    private void  getkey() {
        User loginUser = BaseApp.getInstance(SendDetailActivity.this).getLoginUser();
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

    @SuppressLint("SetTextI18n")
    private void promokodedata() {
        btnpromo.setEnabled(false);
        btnpromo.setText("Wait...");
        final User user = BaseApp.getInstance(this).getLoginUser();
        PromoRequestJson request = new PromoRequestJson();
        request.setFitur(fitur);
        request.setCode(promokode.getText().toString());

        UserService service = ServiceGenerator.createService(UserService.class, user.getNoTelepon(), user.getPassword());
        service.promocode(request).enqueue(new Callback<PromoResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<PromoResponseJson> call, @NonNull Response<PromoResponseJson> response) {
                if (response.isSuccessful()) {
                    if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("success")) {
                        btnpromo.setEnabled(true);
                        btnpromo.setText("Use");
                        if (response.body().getType().equals("persen")) {
                            promocode = (Long.parseLong(response.body().getNominal()) * harga) / 100;
                        } else {
                            promocode = Long.parseLong(response.body().getNominal());
                        }
                        Log.e("", String.valueOf(promocode));
                        if (checkedpaywallet.equals("1")) {
                            long diskonwallet = (long) (Double.parseDouble(biayaakhir) * harga);
                            String diskontotal = String.valueOf(diskonwallet + promocode);
                            String totalbiaya = String.valueOf(harga - (diskonwallet + promocode));
//                            Utility.currencyTXT(priceText, totalbiaya, context);
//                            Utility.currencyTXT(diskon, diskontotal, SendDetailActivity.this);
                            Utility.convertLocaleCurrencyTV(priceText, context, totalbiaya);
                            Utility.convertLocaleCurrencyTV(diskon, SendDetailActivity.this, diskontotal);
                        } else {
                            String diskontotal = String.valueOf(promocode);
                            String totalbiaya = String.valueOf(harga - promocode);
//                            Utility.currencyTXT(priceText, totalbiaya, context);
//                            Utility.currencyTXT(diskon, diskontotal, SendDetailActivity.this);
                            Utility.convertLocaleCurrencyTV(priceText, context,totalbiaya);
                            Utility.convertLocaleCurrencyTV(diskon, SendDetailActivity.this, diskontotal);
                        }
                    } else {
                        btnpromo.setEnabled(true);
                        btnpromo.setText("Use");
                        notif("promo code not available!");
                        promocode = 0;
                        if (checkedpaywallet.equals("1")) {
                            long diskonwallet = (long) (Double.parseDouble(biayaakhir) * harga);
                            String diskontotal = String.valueOf(diskonwallet + promocode);
                            String totalbiaya = String.valueOf(harga - (diskonwallet + promocode));
//                            Utility.currencyTXT(priceText, totalbiaya, context);
//                            Utility.currencyTXT(diskon, diskontotal, SendDetailActivity.this);
                            Utility.convertLocaleCurrencyTV(priceText, context, totalbiaya);
                            Utility.convertLocaleCurrencyTV(diskon, SendDetailActivity.this, diskontotal);
                        } else {
                            String diskontotal = String.valueOf(promocode);
                            String totalbiaya = String.valueOf(harga - promocode);
//                            Utility.currencyTXT(priceText, totalbiaya, context);
//                            Utility.currencyTXT(diskon, diskontotal, SendDetailActivity.this);
                            Utility.convertLocaleCurrencyTV(priceText, context, totalbiaya);
                            Utility.convertLocaleCurrencyTV(diskon, SendDetailActivity.this, diskontotal);
                        }
                    }
                } else {
                    notif("error!");
                }
            }

            @Override
            public void onFailure(@NonNull Call<PromoResponseJson> call, @NonNull Throwable t) {
                t.printStackTrace();
                notif("error");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        User userLogin = BaseApp.getInstance(this).getLoginUser();
        saldoWallet = String.valueOf(userLogin.getWalletSaldo());

//        Utility.currencyTXT(saldotext, saldoWallet, this);
        Utility.convertLocaleCurrencyTV(saldotext , this, saldoWallet);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    private void noDriver() {
        double biayaTotal = harga;
        String formattedTotal = NumberFormat.getNumberInstance(Locale.US).format(biayaTotal);
        final String formattedTextPrice = String.format(Locale.US, "IDR %s.00", formattedTotal);
        AlertDialog dialog = new AlertDialog.Builder(SendDetailActivity.this)
                .setTitle("Maaf Tidak ada Driver kami yang online")
                .setMessage("Silahkan Booking manual(whatsapp) melalu staf kami sehingga bisa di proses secepat mungkin.")
                .setPositiveButton("Book Manual", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String message = "BUMDES-KU"
                                + "\n" +
                                "Asal : " + pickup
                                + "\n " +
                                "Tujuan " + " :" +
                                destination + " , " + destination2 + " , " + destination3 + " , " + destination4 + " , " + destination5
                                + "\n " +
                                "TOTAL HARGA " + " :" +
                                formattedTextPrice;

                        Uri uri = Uri.parse("https://api.whatsapp.com/send?phone=+6285220020027&text=Saya%20ingin%20%20pesan%20"+message);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancel", null).create();
        dialog.show();
    }

    private void onOrderButton() {
        HashMap<String,String> wilayah = sessionWilayah.getSessionData();
        if (checkedpaywallet.equals("1")) {
            if (driverAvailable.isEmpty()) {
//                notif("Sorry, there are no drivers around you.");
                noDriver();
            } else {
                rlprogress.setVisibility(View.VISIBLE);
                SendRequestJson param = new SendRequestJson();
                User userLogin = BaseApp.getInstance(this).getLoginUser();
                param.setIdPelanggan(userLogin.getId());
                param.setOrderFitur(String.valueOf(fiturModel.getIdFitur()));
                param.setStartLatitude(pickUpLatLang.latitude);
                param.setStartLongitude(pickUpLatLang.longitude);
                param.setEndLatitude(destinationLatLang.latitude);
                param.setEndLongitude(destinationLatLang.longitude);

                if(destinationLatLang2 != null) {
                    param.setEndLat2(destinationLatLang2.latitude);
                    param.setEndLong2(destinationLatLang2.longitude);
                }else{
                    param.setEndLat2(0);
                    param.setEndLong2(0);
                }

                if(destinationLatLang3 != null) {
                    param.setEndLat3(destinationLatLang3.latitude);
                    param.setEndLong3(destinationLatLang3.longitude);
                }else{
                    param.setEndLat3(0);
                    param.setEndLong3(0);
                }

                if(destinationLatLang4 != null) {
                    param.setEndLat4(destinationLatLang4.latitude);
                    param.setEndLong4(destinationLatLang4.longitude);
                }else{
                    param.setEndLat4(0);
                    param.setEndLong4(0);
                }

                if(destinationLatLang5 != null) {
                    param.setEndLat5(destinationLatLang5.latitude);
                    param.setEndLong5(destinationLatLang5.longitude);
                }else{
                    param.setEndLat5(0);
                    param.setEndLong5(0);
                }

                param.setJarak(distance);
                param.setHarga(this.harga);
                param.setEstimasi(fiturtext.getText().toString());
                param.setKreditpromo(String.valueOf((Double.parseDouble(biayaakhir) * this.harga) + promocode));
                param.setAlamatAsal(pickup);
                param.setAlamatTujuan(destination);

                param.setAlamatTujuan2(destination2);
                param.setAlamatTujuan3(destination3);
                param.setAlamatTujuan4(destination4);
                param.setAlamatTujuan5(destination5);

                param.setPakaiWallet(1);
                param.setNamaPengirim(sendername.getText().toString());
                param.setTeleponPengirim(countrycode.getText().toString() + senderphone.getText().toString());
                param.setNamaPenerima(recievername.getText().toString());

                param.setNamaPenerima2(recievername2.getText().toString());
                param.setNamaPenerima3(recievername3.getText().toString());
                param.setNamaPenerima4(recievername4.getText().toString());
                param.setNamaPenerima5(recievername5.getText().toString());

                param.setTeleponPenerima(countrycodereceiver.getText().toString() + recieverphone.getText().toString());

                param.setTeleponPenerima2(countrycodereceiver2.getText().toString() + recieverphone2.getText().toString());
                param.setTeleponPenerima3(countrycodereceiver3.getText().toString() + recieverphone3.getText().toString());
                param.setTeleponPenerima4(countrycodereceiver4.getText().toString() + recieverphone4.getText().toString());
                param.setTeleponPenerima5(countrycodereceiver5.getText().toString() + recieverphone5.getText().toString());

                param.setCabang(Integer.parseInt(wilayah.get(sessionWilayah.IDWILAYAH)));

                if (!othertext.getText().toString().isEmpty()) {
                    param.setNamaBarang(othertext.getText().toString());
                } else {
                    param.setNamaBarang(itemdetail);
                }
                sendRequestTransaksi(param, driverAvailable);
            }
        } else {
            if (driverAvailable.isEmpty()) {
                noDriver();
//                notif("Sorry, there are no drivers around you.");
            } else {
                rlprogress.setVisibility(View.VISIBLE);
                SendRequestJson param = new SendRequestJson();
                User userLogin = BaseApp.getInstance(this).getLoginUser();
                param.setIdPelanggan(userLogin.getId());
                param.setOrderFitur(String.valueOf(fiturModel.getIdFitur()));
                param.setStartLatitude(pickUpLatLang.latitude);
                param.setStartLongitude(pickUpLatLang.longitude);
                param.setEndLatitude(destinationLatLang.latitude);
                param.setEndLongitude(destinationLatLang.longitude);

                if(destinationLatLang2 != null) {
                    param.setEndLat2(destinationLatLang2.latitude);
                    param.setEndLong2(destinationLatLang2.longitude);
                }else{
                    param.setEndLat2(0);
                    param.setEndLong2(0);
                }

                if(destinationLatLang3 != null) {
                    param.setEndLat3(destinationLatLang3.latitude);
                    param.setEndLong3(destinationLatLang3.longitude);
                }else{
                    param.setEndLat3(0);
                    param.setEndLong3(0);
                }

                if(destinationLatLang4 != null) {
                    param.setEndLat4(destinationLatLang4.latitude);
                    param.setEndLong4(destinationLatLang4.longitude);
                }else{
                    param.setEndLat4(0);
                    param.setEndLong4(0);
                }

                if(destinationLatLang5 != null) {
                    param.setEndLat5(destinationLatLang5.latitude);
                    param.setEndLong5(destinationLatLang5.longitude);
                }else{
                    param.setEndLat5(0);
                    param.setEndLong5(0);
                }

                param.setJarak(distance);
                param.setHarga(this.harga);
                param.setEstimasi(fiturtext.getText().toString());
                param.setKreditpromo(String.valueOf(promocode));
                param.setAlamatAsal(pickup);

                param.setAlamatTujuan(destination);
                param.setAlamatTujuan2(destination2);
                param.setAlamatTujuan3(destination3);
                param.setAlamatTujuan4(destination4);
                param.setAlamatTujuan5(destination5);


                param.setPakaiWallet(0);
                param.setNamaPengirim(sendername.getText().toString());
                param.setTeleponPengirim(countrycode.getText().toString() + senderphone.getText().toString());
                param.setNamaPenerima(recievername.getText().toString());

                param.setNamaPenerima2(recievername2.getText().toString());
                param.setNamaPenerima3(recievername3.getText().toString());
                param.setNamaPenerima4(recievername4.getText().toString());
                param.setNamaPenerima5(recievername5.getText().toString());


                param.setTeleponPenerima(countrycodereceiver.getText().toString() + recieverphone.getText().toString());

                param.setTeleponPenerima2(countrycodereceiver2.getText().toString() + recieverphone2.getText().toString());
                param.setTeleponPenerima3(countrycodereceiver3.getText().toString() + recieverphone3.getText().toString());
                param.setTeleponPenerima4(countrycodereceiver4.getText().toString() + recieverphone4.getText().toString());
                param.setTeleponPenerima5(countrycodereceiver5.getText().toString() + recieverphone5.getText().toString());
                param.setCabang(Integer.parseInt(wilayah.get(sessionWilayah.IDWILAYAH)));

                if (!othertext.getText().toString().isEmpty()) {
                    param.setNamaBarang(othertext.getText().toString());
                } else {
                    param.setNamaBarang(itemdetail);
                }

                sendRequestTransaksi(param, driverAvailable);
            }
        }
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

    private void sendRequestTransaksi(SendRequestJson param, final List<DriverModel> driverList) {
        rlprogress.setVisibility(View.VISIBLE);
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        final BookService service = ServiceGenerator.createService(BookService.class, loginUser.getEmail(), loginUser.getPassword());

        service.requestTransaksisend(param).enqueue(new Callback<SendResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<SendResponseJson> call, @NonNull Response<SendResponseJson> response) {
                if (response.isSuccessful()) {
                    buildDriverRequest(Objects.requireNonNull(response.body()));
                    thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i < driverList.size(); i++) {
                                fcmBroadcast(i, driverList);
                            }

                            try {
                                Thread.sleep(30000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            if (threadRun) {
                                CheckStatusTransaksiRequest param = new CheckStatusTransaksiRequest();
                                param.setIdTransaksi(transaksi.getId());
                                service.checkStatusTransaksi(param).enqueue(new Callback<CheckStatusTransaksiResponse>() {
                                    @Override
                                    public void onResponse(@NonNull Call<CheckStatusTransaksiResponse> call, @NonNull Response<CheckStatusTransaksiResponse> response) {
                                        if (response.isSuccessful()) {
                                            CheckStatusTransaksiResponse checkStatus = response.body();
                                            if (!Objects.requireNonNull(checkStatus).isStatus()) {
                                                notif("Driver not found!");
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        notif("Driver not found!");
                                                    }
                                                });

                                                rlprogress.setVisibility(View.GONE);
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(@NonNull Call<CheckStatusTransaksiResponse> call, @NonNull Throwable t) {
                                        notif("Driver not found!");
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                notif("Driver not found!");
                                                rlprogress.setVisibility(View.GONE);
                                            }
                                        });

                                        rlprogress.setVisibility(View.GONE);

                                    }
                                });
                            }

                        }
                    });
                    thread.start();


                }
            }

            @Override
            public void onFailure(@NonNull Call<SendResponseJson> call, @NonNull Throwable t) {
                t.printStackTrace();
                notif("Your account has a problem, please contact passenger service!");
                rlprogress.setVisibility(View.GONE);
            }
        });
    }

    private void buildDriverRequest(SendResponseJson response) {
        transaksi = response.getData().get(0);
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        if (request == null) {
            request = new DriverRequest();
            request.setIdTransaksi(transaksi.getId());
            request.setIdPelanggan(transaksi.getIdPelanggan());
            request.setRegIdPelanggan(loginUser.getToken());
            request.setOrderFitur(fiturModel.getHome());
            request.setStartLatitude(transaksi.getStartLatitude());
            request.setStartLongitude(transaksi.getStartLongitude());
            request.setEndLatitude(transaksi.getEndLatitude());
            request.setEndLongitude(transaksi.getEndLongitude());
            request.setEndLat2(transaksi.getEndLat2());
            request.setEndLong2(transaksi.getEndLong2());
            request.setEndLat3(transaksi.getEndLat3());
            request.setEndLong3(transaksi.getEndLong3());
            request.setEndLat4(transaksi.getEndLat4());
            request.setEndLong4(transaksi.getEndLong4());
            request.setEndLat5(transaksi.getEndLat5());
            request.setEndLong5(transaksi.getEndLong5());

            request.setJarak(transaksi.getJarak());
            request.setHarga(transaksi.getHarga());
            request.setWaktuOrder(transaksi.getWaktuOrder());
            request.setAlamatAsal(transaksi.getAlamatAsal());
            request.setAlamatTujuan(transaksi.getAlamatTujuan());
            request.setAlamatTujuan2(transaksi.getAlamatTujuan2());
            request.setAlamatTujuan3(transaksi.getAlamatTujuan3());
            request.setAlamatTujuan4(transaksi.getAlamatTujuan4());
            request.setAlamatTujuan5(transaksi.getAlamatTujuan5());
            request.setKodePromo(transaksi.getKodePromo());
            request.setKreditPromo(transaksi.getKreditPromo());
            request.setPakaiWallet(String.valueOf(transaksi.isPakaiWallet()));
            request.setEstimasi(transaksi.getEstimasi());
            request.setLayanan(layanan);
            request.setLayanandesc(layanandesk);
            request.setIcon(icon);
            request.setBiaya(cost.getText().toString());
            request.setDistance(distanceText.getText().toString());


            String namaLengkap = String.format("%s", loginUser.getFullnama());
            request.setNamaPelanggan(namaLengkap);
            request.setTelepon(loginUser.getNoTelepon());
            request.setType(ORDER);
        }
    }

    private void fcmBroadcast(int index, List<DriverModel> driverList) {
        DriverModel driverToSend = driverList.get(index);
        request.setTime_accept(new Date().getTime() + "");
        final FCMMessage message = new FCMMessage();
        message.setTo(driverToSend.getRegId());
        message.setData(request);

        Log.e("REQUEST TO DRIVER", message.getData().toString());

        FCMHelper.sendMessage(keyss, message).enqueue(new okhttp3.Callback() {
            @Override
            public void onResponse(@NonNull okhttp3.Call call, @NonNull okhttp3.Response response) {
                Log.e("REQUEST TO DRIVER", message.getData().toString());
            }

            @Override
            public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {
                e.printStackTrace();
            }
        });
    }

    @SuppressWarnings("unused")
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(final DriverResponse response) {
        if (response.getResponse().equalsIgnoreCase(DriverResponse.ACCEPT) || response.getResponse().equals("3") || response.getResponse().equals("4")) {
            runOnUiThread(new Runnable() {
                public void run() {
                    threadRun = false;
                    for (DriverModel cDriver : driverAvailable) {
                        if (cDriver.getId().equals(response.getId())) {


                            Intent intent = new Intent(SendDetailActivity.this, ProgressActivity.class);
                            intent.putExtra("id_driver", cDriver.getId());
                            intent.putExtra("id_transaksi", request.getIdTransaksi());
                            intent.putExtra("response", "2");
                            startActivity(intent);
                            DriverResponse response = new DriverResponse();
                            response.setId("");
                            response.setIdTransaksi("");
                            response.setResponse("");
                            EventBus.getDefault().postSticky(response);
                            finish();
                        }
                    }
                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

}
