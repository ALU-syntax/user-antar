package com.antar.passenger.activity;

import android.Manifest;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.antar.passenger.R;
import com.antar.passenger.constants.BaseApp;
import com.antar.passenger.constants.Constants;
import com.antar.passenger.gmap.directions.Directions;
import com.antar.passenger.gmap.directions.Route;
import com.antar.passenger.item.ItemPesananItem;
import com.antar.passenger.json.DetailRequestJson;
import com.antar.passenger.json.DetailTransResponseJson;
import com.antar.passenger.json.FcmKeyResponseJson;
import com.antar.passenger.json.FcmRequestJson;
import com.antar.passenger.json.LokasiDriverRequest;
import com.antar.passenger.json.LokasiDriverResponse;
import com.antar.passenger.json.fcm.CancelBookRequestJson;
import com.antar.passenger.json.fcm.CancelBookResponseJson;
import com.antar.passenger.json.fcm.DriverResponse;
import com.antar.passenger.json.fcm.FCMMessage;
import com.antar.passenger.models.DriverModel;
import com.antar.passenger.models.FiturModel;
import com.antar.passenger.models.LokasiDriverModel;
import com.antar.passenger.models.TransaksiModel;
import com.antar.passenger.models.User;
import com.antar.passenger.utils.LocaleHelper;
import com.antar.passenger.utils.NetworkManager;
import com.antar.passenger.utils.PicassoTrustAll;
import com.antar.passenger.utils.Utility;
import com.antar.passenger.utils.api.FCMHelper;
import com.antar.passenger.utils.api.MapDirectionAPI;
import com.antar.passenger.utils.api.ServiceGenerator;
import com.antar.passenger.utils.api.service.BookService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.antar.passenger.utils.api.service.UserService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.antar.passenger.json.fcm.FCMType.ORDER;

import static com.antar.passenger.utils.MapsUtils.getBearing;
import static com.antar.passenger.utils.api.service.MessagingService.BROADCAST_ORDER;


public class ProgressActivity extends AppCompatActivity
        implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final int REQUEST_PERMISSION_LOCATION = 991;
    private static final int REQUEST_PERMISSION_CALL = 992;
    Bundle orderBundle;
    String idtrans, iddriver, response;
    String regdriver, fitur, imagedriver, tokenmerchant;
    String latdriver = "";
    String londriver = "";
    String complete, icondriver, gethome;
    ImageView img_cek_item1, img_cek_item2, img_cek_item3, img_cek_item4, img_cek_item5;
    Timer timer = new Timer();
    TextView textprogress, status, time, costtext;
    ImageView phone, chat;
    TextView produk, sendername, receivername, receivername2, receivername3, receivername4, receivername5;
    Button senderphone, receiverphone, receiverphone2, receiverphone3, receiverphone4, receiverphone5;
    LinearLayout ll2, ll3, ll4, ll5;
    TextView textnotif, priceText;
    TextView diskon, cost, distanceText, fiturtext, destinationText,destinationText2, destinationText3, destinationText4, destinationText5 , pickUpText;
    ImageView foto, image, backbtn;
    TextView layanandesk, layanan, totaltext;
    LinearLayout llpayment, bottomsheet, setDestinationContainer, setPickUpContainer, llchat, lldestination, lldestination2, lldestination3, lldestination4, lldestination5, lldistance, lldetailsend, llmerchantdetail;
    RelativeLayout rlnotif, rlprogress;
    Button orderButton;
    RecyclerView rvmerchantnear;
    ItemPesananItem itemPesananItem;
    Realm realm;
    private GoogleMap gMap;
    private GoogleApiClient googleApiClient;
    private LatLng pickUpLatLng;
    private LatLng destinationLatLng;
    private LatLng destinationLatLng2;
    private LatLng destinationLatLng3;
    private LatLng destinationLatLng4;
    private LatLng destinationLatLng5;
    private Polyline directionLine;
    private Marker pickUpMarker;
    private Marker destinationMarker;
    private Marker driverMarker;
    private boolean isCancelable = true;
    private int markerCount;
    private Handler handler;
    private String keyss;
    private Runnable updateDriverRunnable = new Runnable() {
        @Override
        public void run() {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        LokasiDriverRequest param = new LokasiDriverRequest();
                        final BookService service = ServiceGenerator.createService(BookService.class, "admin", "12345");
                        param.setId(iddriver);
                        service.liatLokasiDriver(param).enqueue(new Callback<LokasiDriverResponse>() {
                            @Override
                            public void onResponse(@NonNull Call<LokasiDriverResponse> call, @NonNull Response<LokasiDriverResponse> response) {
                                if (response.isSuccessful()) {
                                    final LokasiDriverModel latlang = Objects.requireNonNull(response.body()).getData().get(0);
                                    final LatLng location = new LatLng(latlang.getLatitude(), latlang.getLongitude());
                                    updateDriverMarker(location);
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<LokasiDriverResponse> call, @NonNull Throwable t) {


                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    timer.scheduleAtFixedRate(new TimerTask() {
                        @Override
                        public void run() {
                            if (NetworkManager.isConnectToInternet(ProgressActivity.this)) {
                                try {
                                    LokasiDriverRequest param = new LokasiDriverRequest();
                                    final BookService service = ServiceGenerator.createService(BookService.class, "admin", "12345");
                                    param.setId(iddriver);
                                    service.liatLokasiDriver(param).enqueue(new Callback<LokasiDriverResponse>() {
                                        @Override
                                        public void onResponse(@NonNull Call<LokasiDriverResponse> call, @NonNull Response<LokasiDriverResponse> response) {
                                            if (response.isSuccessful()) {
                                                final LokasiDriverModel latlang = Objects.requireNonNull(response.body()).getData().get(0);
                                                final LatLng location = new LatLng(latlang.getLatitude(), latlang.getLongitude());
                                                updateDriverMarker(location);
                                            }
                                        }

                                        @Override
                                        public void onFailure(@NonNull Call<LokasiDriverResponse> call, @NonNull Throwable t) {


                                        }
                                    });
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }, 0, 4000);
                }
            }).start();
        }
    };
    private okhttp3.Callback updateRouteCallback = new okhttp3.Callback() {
        @Override
        public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {

        }

        @Override
        public void onResponse(@NonNull okhttp3.Call call, okhttp3.Response response) throws IOException {
            if (response.isSuccessful()) {
                final String json = Objects.requireNonNull(response.body()).string();
                final long distance = MapDirectionAPI.getDistance(ProgressActivity.this, json);
                final String time = MapDirectionAPI.getTimeDistance(ProgressActivity.this, json);
                if (distance >= 0) {
                    ProgressActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            updateLineDestination(json);
                            float km = ((float) (distance)) / 1000f;
                            String format = String.format(Locale.US, "%.1f", km);
                            distanceText.setText(format);
                            fiturtext.setText(time);
                        }
                    });
                }
            }
        }
    };
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            orderBundle = intent.getExtras();
            orderHandler(Objects.requireNonNull(orderBundle).getInt("code"));
        }
    };

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopDriverLocationUpdate();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopDriverLocationUpdate();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        stopDriverLocationUpdate();
        timer.cancel();
        super.onBackPressed();
        if (complete.equals("1")) {
            Intent intent = new Intent(ProgressActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } else {
            finish();
        }
    }

    private void stopDriverLocationUpdate() {
        handler.removeCallbacks(updateDriverRunnable);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride);
        getkey();
        realm = Realm.getDefaultInstance();
        handler = new Handler();
        fitur = "0";
        icondriver = "0";
        gethome = "0";
        Intent intent = getIntent();
        iddriver = intent.getStringExtra("id_driver");
        idtrans = intent.getStringExtra("id_transaksi");
        response = intent.getStringExtra("response");
        if (intent.getStringExtra("complete") == null) {
            complete = "false";
        } else {
            complete = intent.getStringExtra("complete");
        }
        receiverphone = findViewById(R.id.receiverphone);
        receiverphone2 = findViewById(R.id.receiverphone2);
        receiverphone3 = findViewById(R.id.receiverphone3);
        receiverphone4 = findViewById(R.id.receiverphone4);
        receiverphone5 = findViewById(R.id.receiverphone5);

        senderphone = findViewById(R.id.senderphone);

        receivername = findViewById(R.id.receivername);
        receivername2 = findViewById(R.id.receivername2);
        receivername3 = findViewById(R.id.receivername3);
        receivername4 = findViewById(R.id.receivername4);
        receivername5 = findViewById(R.id.receivername5);

        ll2 = findViewById(R.id.ll2);
        ll3 = findViewById(R.id.ll3);
        ll4 = findViewById(R.id.ll4);
        ll5 = findViewById(R.id.ll5);

        sendername = findViewById(R.id.sendername);
        produk = findViewById(R.id.produk);
        lldetailsend = findViewById(R.id.senddetail);
        lldistance = findViewById(R.id.lldistance);
        lldestination = findViewById(R.id.lldestination);
        lldestination2 = findViewById(R.id.lldestination2);
        lldestination3 = findViewById(R.id.lldestination3);
        lldestination4 = findViewById(R.id.lldestination4);
        lldestination5 = findViewById(R.id.lldestination5);
        img_cek_item1 = findViewById(R.id.img_cek_item1);
        img_cek_item2 = findViewById(R.id.img_cek_item2);
        img_cek_item3 = findViewById(R.id.img_cek_item3);
        img_cek_item4 = findViewById(R.id.img_cek_item4);
        img_cek_item5 = findViewById(R.id.img_cek_item5);
        status = findViewById(R.id.status);
        chat = findViewById(R.id.chat);
        phone = findViewById(R.id.phonenumber);
        setPickUpContainer = findViewById(R.id.pickUpContainer);
        setDestinationContainer = findViewById(R.id.destinationContainer);
        bottomsheet = findViewById(R.id.bottom_sheet);
        llpayment = findViewById(R.id.llpayment);
        layanan = findViewById(R.id.layanan);
        layanandesk = findViewById(R.id.layanandes);
        backbtn = findViewById(R.id.back_btn);
        llchat = findViewById(R.id.llchat);
        image = findViewById(R.id.image);
        foto = findViewById(R.id.background);
        pickUpText = findViewById(R.id.pickUpText);
        destinationText = findViewById(R.id.destinationText);
        destinationText2 = findViewById(R.id.destinationText2);
        destinationText3 = findViewById(R.id.destinationText3);
        destinationText4 = findViewById(R.id.destinationText4);
        destinationText5 = findViewById(R.id.destinationText5);
        fiturtext = findViewById(R.id.fitur);
        distanceText = findViewById(R.id.distance);
        cost = findViewById(R.id.cost);
        diskon = findViewById(R.id.diskon);
        priceText = findViewById(R.id.price);
        orderButton = findViewById(R.id.order);
        rlnotif = findViewById(R.id.rlnotif);
        textnotif = findViewById(R.id.textnotif);
        rlprogress = findViewById(R.id.rlprogress);
        textprogress = findViewById(R.id.textprogress);
        llmerchantdetail = findViewById(R.id.merchantdetail);
        time = findViewById(R.id.time);
        costtext = findViewById(R.id.cost_text);
        rvmerchantnear = findViewById(R.id.merchantnear);
        totaltext = findViewById(R.id.totaltext);

        status.setVisibility(View.VISIBLE);
        image.setVisibility(View.GONE);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomsheet);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        setPickUpContainer.setVisibility(View.GONE);
        setDestinationContainer.setVisibility(View.GONE);
        llpayment.setVisibility(View.GONE);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        rvmerchantnear.setHasFixedSize(true);
        rvmerchantnear.setNestedScrollingEnabled(false);
        rvmerchantnear.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        orderButton.setText(getString(R.string.text_cancel));
        orderButton.setBackground(getResources().getDrawable(R.drawable.rounded_corners_button_red));

        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isCancelable) {
                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ProgressActivity.this, R.style.DialogStyle);
                    alertDialogBuilder.setTitle("Cancel order");
                    alertDialogBuilder.setMessage("Do you want to cancel this order?");
                    alertDialogBuilder.setPositiveButton("yes",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    cancelOrder();
                                }
                            });

                    alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                } else {
                    notif("You cannot cancel the order, the trip has already begun!");
                }
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView);
        Objects.requireNonNull(mapFragment).getMapAsync(this);


        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        rlprogress.setVisibility(View.VISIBLE);
        textprogress.setText(getString(R.string.waiting_pleaseWait));

    }

    private void getkey() {
        User loginUser = BaseApp.getInstance(ProgressActivity.this).getLoginUser();
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

    private void getData(final String idtrans, final String iddriver) {
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        BookService service = ServiceGenerator.createService(BookService.class, loginUser.getEmail(), loginUser.getPassword());
        DetailRequestJson param = new DetailRequestJson();
        param.setId(idtrans);
        param.setIdDriver(iddriver);
        service.detailtrans(param).enqueue(new Callback<DetailTransResponseJson>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<DetailTransResponseJson> call, @NonNull Response<DetailTransResponseJson> responsedata) {
                if (responsedata.isSuccessful()) {

                    final TransaksiModel transaksi = Objects.requireNonNull(responsedata.body()).getData().get(0);
                    FiturModel designedFitur = realm.where(FiturModel.class).equalTo("idFitur", Integer.valueOf(transaksi.getOrderFitur())).findFirst();
                    icondriver = Objects.requireNonNull(designedFitur).getIcon_driver();
                    gethome = designedFitur.getHome();
                    DriverModel driver = responsedata.body().getDriver().get(0);
                    parsedata(transaksi, driver, designedFitur);
                    regdriver = driver.getRegId();
                    tokenmerchant = transaksi.getToken_merchant();
                    imagedriver = Constants.IMAGESDRIVER + driver.getFoto();

                    if (transaksi.isPakaiWallet()) {
                        totaltext.setText("Total (Wallet)");
                    } else {
                        totaltext.setText("Total (Cash)");
                    }
                    if (pickUpMarker != null) pickUpMarker.remove();
                    pickUpMarker = gMap.addMarker(new MarkerOptions()
                            .position(pickUpLatLng)
                            .title("Pick Up")
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.pickup)));

                    if(designedFitur.getHome().equals("2")){


                        destinationMarker = gMap.addMarker(new MarkerOptions()
                                .position(destinationLatLng)
                                .title("Destination")
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.destination)));

                        if(destinationLatLng2 != null) {
                            destinationMarker = gMap.addMarker(new MarkerOptions()
                                    .position(destinationLatLng2)
                                    .title("Destination2")
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.destination)));
                        }

                        if(destinationLatLng3 != null) {
                            destinationMarker = gMap.addMarker(new MarkerOptions()
                                    .position(destinationLatLng3)
                                    .title("Destination3")
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.destination)));
                        }

                        if(destinationLatLng4 != null) {
                            destinationMarker = gMap.addMarker(new MarkerOptions()
                                    .position(destinationLatLng4)
                                    .title("Destination2")
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.destination)));
                        }

                        if(destinationLatLng5 != null) {
                            destinationMarker = gMap.addMarker(new MarkerOptions()
                                    .position(destinationLatLng5)
                                    .title("Destination2")
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.destination)));
                        }

                    }
                    else
                    {
                        if (destinationMarker != null) destinationMarker.remove();
                        destinationMarker = gMap.addMarker(new MarkerOptions()
                                .position(destinationLatLng)
                                .title("Destination")
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.destination)));
                    }



                    updateLastLocation();

                    fitur = transaksi.getOrderFitur();

                    if (!designedFitur.getHome().equals("3")) {
                        if (designedFitur.getHome().equals("4")) {
                            requestRoute();
                            llmerchantdetail.setVisibility(View.VISIBLE);
                            Utility.currencyTXT(fiturtext, String.valueOf(transaksi.getHarga()), ProgressActivity.this);
                            lldistance.setVisibility(View.GONE);
                            time.setText("Delivery Fee");
                            costtext.setText("Order Cost");

                            itemPesananItem = new ItemPesananItem(responsedata.body().getItem(), R.layout.item_pesanan);
                            rvmerchantnear.setAdapter(itemPesananItem);

                        } else if (designedFitur.getHome().equals("2")) {

                            if(destinationLatLng2 != null) {
                                if(destinationLatLng2.latitude != 0 && destinationLatLng2.longitude != 0) {
                                    if(destinationLatLng3 != null) {
                                        if (destinationLatLng3.latitude != 0 && destinationLatLng3.longitude != 0) {
                                            if(destinationLatLng4 != null) {
                                                if (destinationLatLng4.latitude != 0 && destinationLatLng4.longitude != 0) {
                                                    if(destinationLatLng5 != null) {
                                                        if (destinationLatLng5.latitude != 0 && destinationLatLng5.longitude != 0) {
                                                            requestRoute5(destinationLatLng5);
                                                        } else {
                                                            requestRoute4(destinationLatLng4);
                                                        }
                                                    }
                                                } else {
                                                    requestRoute3(destinationLatLng3);
                                                }
                                            }
                                        } else {
                                            requestRoute2(destinationLatLng2);
                                        }
                                    }
                                }else{
                                    requestRoute();
                                }
                            }

                            lldetailsend.setVisibility(View.VISIBLE);
                            produk.setText(transaksi.getNamaBarang());
                            sendername.setText(transaksi.namaPengirim);
                            receivername.setText(transaksi.namaPenerima);

                            if(!transaksi.namaPenerima2.isEmpty()){
                                ll2.setVisibility(View.VISIBLE);
                                receivername2.setText(transaksi.namaPenerima2);
                                receiverphone2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ProgressActivity.this, R.style.DialogStyle);
                                        alertDialogBuilder.setTitle("Call Driver");
                                        alertDialogBuilder.setMessage("You want to call " + transaksi.getNamaPenerima2() + "(" + transaksi.teleponPenerima2 + ")?");
                                        alertDialogBuilder.setPositiveButton("yes",
                                                new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface arg0, int arg1) {
                                                        if (ActivityCompat.checkSelfPermission(ProgressActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                                            ActivityCompat.requestPermissions(ProgressActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PERMISSION_CALL);
                                                            return;
                                                        }

                                                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                                                        callIntent.setData(Uri.parse("tel:" + transaksi.teleponPenerima));
                                                        startActivity(callIntent);
                                                    }
                                                });

                                        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });

                                        AlertDialog alertDialog = alertDialogBuilder.create();
                                        alertDialog.show();


                                    }
                                });

                            }

                            if(!transaksi.namaPenerima3.isEmpty()){
                                ll3.setVisibility(View.VISIBLE);
                                receivername3.setText(transaksi.namaPenerima3);
                                receiverphone3.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ProgressActivity.this, R.style.DialogStyle);
                                        alertDialogBuilder.setTitle("Call Driver");
                                        alertDialogBuilder.setMessage("You want to call " + transaksi.getNamaPenerima3() + "(" + transaksi.teleponPenerima3 + ")?");
                                        alertDialogBuilder.setPositiveButton("yes",
                                                new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface arg0, int arg1) {
                                                        if (ActivityCompat.checkSelfPermission(ProgressActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                                            ActivityCompat.requestPermissions(ProgressActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PERMISSION_CALL);
                                                            return;
                                                        }

                                                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                                                        callIntent.setData(Uri.parse("tel:" + transaksi.teleponPenerima));
                                                        startActivity(callIntent);
                                                    }
                                                });

                                        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });

                                        AlertDialog alertDialog = alertDialogBuilder.create();
                                        alertDialog.show();


                                    }
                                });

                            }

                            if(!transaksi.namaPenerima4.isEmpty()){
                                ll4.setVisibility(View.VISIBLE);
                                receivername4.setText(transaksi.namaPenerima4);
                                receiverphone4.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ProgressActivity.this, R.style.DialogStyle);
                                        alertDialogBuilder.setTitle("Call Driver");
                                        alertDialogBuilder.setMessage("You want to call " + transaksi.getNamaPenerima4() + "(" + transaksi.teleponPenerima4 + ")?");
                                        alertDialogBuilder.setPositiveButton("yes",
                                                new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface arg0, int arg1) {
                                                        if (ActivityCompat.checkSelfPermission(ProgressActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                                            ActivityCompat.requestPermissions(ProgressActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PERMISSION_CALL);
                                                            return;
                                                        }

                                                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                                                        callIntent.setData(Uri.parse("tel:" + transaksi.teleponPenerima));
                                                        startActivity(callIntent);
                                                    }
                                                });

                                        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });

                                        AlertDialog alertDialog = alertDialogBuilder.create();
                                        alertDialog.show();


                                    }
                                });

                            }

                            if(!transaksi.namaPenerima5.isEmpty()){
                                ll5.setVisibility(View.VISIBLE);
                                receivername5.setText(transaksi.namaPenerima5);
                                receiverphone5.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ProgressActivity.this, R.style.DialogStyle);
                                        alertDialogBuilder.setTitle("Call Driver");
                                        alertDialogBuilder.setMessage("You want to call " + transaksi.getNamaPenerima5() + "(" + transaksi.teleponPenerima5 + ")?");
                                        alertDialogBuilder.setPositiveButton("yes",
                                                new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface arg0, int arg1) {
                                                        if (ActivityCompat.checkSelfPermission(ProgressActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                                            ActivityCompat.requestPermissions(ProgressActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PERMISSION_CALL);
                                                            return;
                                                        }

                                                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                                                        callIntent.setData(Uri.parse("tel:" + transaksi.teleponPenerima));
                                                        startActivity(callIntent);
                                                    }
                                                });

                                        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });

                                        AlertDialog alertDialog = alertDialogBuilder.create();
                                        alertDialog.show();


                                    }
                                });

                            }

                            senderphone.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ProgressActivity.this, R.style.DialogStyle);
                                    alertDialogBuilder.setTitle("Call Driver");
                                    alertDialogBuilder.setMessage("You want to call " + transaksi.getNamaPengirim() + "(" + transaksi.teleponPengirim + ")?");
                                    alertDialogBuilder.setPositiveButton("yes",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface arg0, int arg1) {
                                                    if (ActivityCompat.checkSelfPermission(ProgressActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                                        ActivityCompat.requestPermissions(ProgressActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PERMISSION_CALL);
                                                        return;
                                                    }

                                                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                                                    callIntent.setData(Uri.parse("tel:" + transaksi.teleponPengirim));
                                                    startActivity(callIntent);
                                                }
                                            });

                                    alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });

                                    AlertDialog alertDialog = alertDialogBuilder.create();
                                    alertDialog.show();


                                }
                            });

                            receiverphone.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ProgressActivity.this, R.style.DialogStyle);
                                    alertDialogBuilder.setTitle("Call Driver");
                                    alertDialogBuilder.setMessage("You want to call " + transaksi.getNamaPenerima() + "(" + transaksi.teleponPenerima + ")?");
                                    alertDialogBuilder.setPositiveButton("yes",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface arg0, int arg1) {
                                                    if (ActivityCompat.checkSelfPermission(ProgressActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                                        ActivityCompat.requestPermissions(ProgressActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PERMISSION_CALL);
                                                        return;
                                                    }

                                                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                                                    callIntent.setData(Uri.parse("tel:" + transaksi.teleponPenerima));
                                                    startActivity(callIntent);
                                                }
                                            });

                                    alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });

                                    AlertDialog alertDialog = alertDialogBuilder.create();
                                    alertDialog.show();


                                }
                            });

                        } else {
                            requestRoute();
                        }
                    }

                    else {
                        lldestination.setVisibility(View.GONE);
                        lldistance.setVisibility(View.GONE);
                        fiturtext.setText(transaksi.getEstimasi());
                    }
                    if (transaksi.status == 4 && transaksi.getRate().isEmpty()) {
                        Intent intent = new Intent(ProgressActivity.this, RateActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("id_driver", iddriver);
                        intent.putExtra("id_transaksi", idtrans);
                        intent.putExtra("response", response);
                        startActivity(intent);
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull retrofit2.Call<DetailTransResponseJson> call, @NonNull Throwable t) {

            }
        });


    }

    @SuppressLint("SetTextI18n")
    private void parsedata(TransaksiModel request, final DriverModel driver, FiturModel fiturmodel) {
        final User loginUser = BaseApp.getInstance(ProgressActivity.this).getLoginUser();
        rlprogress.setVisibility(View.GONE);
        pickUpLatLng = new LatLng(request.getStartLatitude(), request.getStartLongitude());
        destinationLatLng = new LatLng(request.getEndLatitude(), request.getEndLongitude());
        destinationLatLng2 = new LatLng(request.getEndLat2(), request.getEndLong2());
        destinationLatLng3 = new LatLng(request.getEndLat3(), request.getEndLong3());
        destinationLatLng4 = new LatLng(request.getEndLat4(), request.getEndLong4());
        destinationLatLng5 = new LatLng(request.getEndLat5(), request.getEndLong5());

        PicassoTrustAll.getInstance(this)
                .load(Constants.IMAGESDRIVER + driver.getFoto())
                .placeholder(R.drawable.image_placeholder)
                .into(foto);

        layanandesk.setText(driver.getNomor_kendaraan() + " " + getString(R.string.text_with_bullet) + " " + driver.getTipe());

        if (!response.equals("2")) {
            if (!response.equals("3")) {
                if (response.equals("4")) {
                    isCancelable = false;
                    llchat.setVisibility(View.GONE);
                    orderButton.setVisibility(View.GONE);
                    status.setText(getString(R.string.notification_finish));
                } else if (response.equals("5")) {
                    isCancelable = false;
                    llchat.setVisibility(View.GONE);
                    orderButton.setVisibility(View.GONE);
                    status.setText(getString(R.string.notification_cancel));
                } else if(response.equals("11")){
                    isCancelable = false;
                    llchat.setVisibility(View.GONE);
                    orderButton.setVisibility(View.GONE);
                    status.setText("Paket Pertama Telah Diterima");
                }else if(response.equals("12")){
                    isCancelable = false;
                    llchat.setVisibility(View.GONE);
                    orderButton.setVisibility(View.GONE);
                    status.setText("Paket Kedua Telah Diterima");
                }else if(response.equals("13")){
                    isCancelable = false;
                    llchat.setVisibility(View.GONE);
                    orderButton.setVisibility(View.GONE);
                    status.setText("Paket Ketiga Telah Diterima");
                }else if(response.equals("14")){
                    isCancelable = false;
                    llchat.setVisibility(View.GONE);
                    orderButton.setVisibility(View.GONE);
                    status.setText("Paket Keempat Telah Diterima");
                }else if(response.equals("15")){
                    isCancelable = false;
                    llchat.setVisibility(View.GONE);
                    orderButton.setVisibility(View.GONE);
                    status.setText("Paket Kelima Telah Diterima");
                }
            } else {
                llchat.setVisibility(View.VISIBLE);
                isCancelable = false;
                orderButton.setVisibility(View.GONE);
                if (fiturmodel.getHome().equals("4")) {
                    status.setText("driver delivers the order");
                } else {
                    status.setText(getString(R.string.notification_start));
                }
            }
        }

        else {
            llchat.setVisibility(View.VISIBLE);
            if (fiturmodel.getHome().equals("4")) {
                status.setText("the driver is buying an order");
            } else {
                status.setText(getString(R.string.notification_accept));
            }
        }
        layanan.setText(driver.getNamaDriver());
        if (fiturmodel.getHome().equals("4")) {
            pickUpText.setText(request.getNama_merchant());
            Utility.currencyTXT(cost, String.valueOf(request.getTotal_biaya()), this);
        } else {
            pickUpText.setText(request.getAlamatAsal());
            Utility.currencyTXT(cost, String.valueOf(request.getHarga()), this);
        }

        destinationText.setText(request.getAlamatTujuan());
        if(!request.getAlamatTujuan2().isEmpty()) {
            destinationText2.setText(request.getAlamatTujuan2());
            lldestination2.setVisibility(View.VISIBLE);
        }
        if(!request.getAlamatTujuan3().isEmpty()) {
            destinationText3.setText(request.getAlamatTujuan3());
            lldestination3.setVisibility(View.VISIBLE);
        }
        if(!request.getAlamatTujuan4().isEmpty()) {
            destinationText4.setText(request.getAlamatTujuan4());
            lldestination4.setVisibility(View.VISIBLE);
        }
        if(!request.getAlamatTujuan5().isEmpty()) {
            destinationText5.setText(request.getAlamatTujuan5());
            lldestination5.setVisibility(View.VISIBLE);
        }


        Utility.currencyTXT(diskon, request.getKreditPromo(), this);
        Utility.currencyTXT(priceText, String.valueOf(request.getBiaya_akhir()), this);

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ProgressActivity.this, R.style.DialogStyle);
                alertDialogBuilder.setTitle("Call Driver");
                alertDialogBuilder.setMessage("You want to call driver (" + driver.getNoTelepon() + ")?");
                alertDialogBuilder.setPositiveButton("yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                if (ActivityCompat.checkSelfPermission(ProgressActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                    ActivityCompat.requestPermissions(ProgressActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PERMISSION_CALL);
                                    return;
                                }

                                Intent callIntent = new Intent(Intent.ACTION_CALL);
                                callIntent.setData(Uri.parse("tel:" + driver.getNoTelepon()));
                                startActivity(callIntent);
                            }
                        });

                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();


            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProgressActivity.this, ChatActivity.class);
                intent.putExtra("senderid", loginUser.getId());
                intent.putExtra("receiverid", driver.getId());
                intent.putExtra("tokendriver", loginUser.getToken());
                intent.putExtra("tokenku", driver.getRegId());
                intent.putExtra("name", driver.getNamaDriver());
                intent.putExtra("pic", Constants.IMAGESDRIVER + driver.getFoto());
                startActivity(intent);
            }
        });
    }

    private void removeNotif() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Objects.requireNonNull(notificationManager).cancel(0);
    }

    private void cancelOrder() {
        rlprogress.setVisibility(View.VISIBLE);
        User loginUser = BaseApp.getInstance(ProgressActivity.this).getLoginUser();
        CancelBookRequestJson requestcancel = new CancelBookRequestJson();
        requestcancel.id = loginUser.getId();
        requestcancel.id_transaksi = idtrans;

        BookService service = ServiceGenerator.createService(BookService.class, loginUser.getEmail(), loginUser.getPassword());
        service.cancelOrder(requestcancel).enqueue(new Callback<CancelBookResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<CancelBookResponseJson> call, @NonNull Response<CancelBookResponseJson> response) {
                if (response.isSuccessful()) {
                    if (Objects.requireNonNull(response.body()).mesage.equals("canceled")) {
                        rlprogress.setVisibility(View.GONE);
                        fcmcancel();
                        fcmcancelmerchant();
                        notif("Order Canceled!");
                        finish();
                    } else {
                        notif("Failed!");
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<CancelBookResponseJson> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });


    }

    private void fcmcancel() {
        DriverResponse response = new DriverResponse();
        response.type = ORDER;
        response.setIdTransaksi(idtrans);
        response.setResponse(DriverResponse.REJECT);

        FCMMessage message = new FCMMessage();
        message.setTo(regdriver);
        message.setData(response);


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

    private void fcmcancelmerchant() {
        DriverResponse response = new DriverResponse();
        response.type = ORDER;
        response.setIdTransaksi(idtrans);
        response.setResponse(String.valueOf(Constants.CANCEL));

        FCMMessage message = new FCMMessage();
        message.setTo(tokenmerchant);
        message.setData(response);


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
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        gMap.getUiSettings().setMyLocationButtonEnabled(true);
        try {
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.style_json));

            if (!success) {
                Log.e("", "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e("", "Can't find style. Error: ", e);
        }
        getData(idtrans, iddriver);
    }

    private void startDriverLocationUpdate() {
        handler = new Handler();
        handler.postDelayed(updateDriverRunnable, 4000);
    }

    private void updateDriverMarker(LatLng latLng) {
        latdriver = String.valueOf(latLng.latitude);
        londriver = String.valueOf(latLng.longitude);
        Location mCurrentLocation = new Location(LocationManager.NETWORK_PROVIDER);
        mCurrentLocation.setLatitude(Double.parseDouble(latdriver));
        mCurrentLocation.setLongitude(Double.parseDouble(londriver));

        if (markerCount == 1) {
            animateMarker(mCurrentLocation, driverMarker);
        } else if (markerCount == 0) {
            if (driverMarker != null) driverMarker.remove();
            if (icondriver.equals("1")) {
                driverMarker = gMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title("Driver")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.drivermap)));
            } else if (icondriver.equals("2")) {
                driverMarker = gMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title("Driver")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.carmap)));
            } else if (icondriver.equals("3")) {
                driverMarker = gMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title("Driver")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.truck)));
            } else if (icondriver.equals("4")) {
                driverMarker = gMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title("Driver")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.delivery)));
            } else if (icondriver.equals("5")) {
                driverMarker = gMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title("Driver")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.hatchback)));
            } else if (icondriver.equals("6")) {
                driverMarker = gMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title("Driver")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.suv)));
            } else if (icondriver.equals("7")) {
                driverMarker = gMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title("Driver")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.van)));
            } else if (icondriver.equals("8")) {
                driverMarker = gMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title("Driver")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.bicycle)));
            } else if (icondriver.equals("9")) {
                driverMarker = gMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title("Driver")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.bajaj)));
            }
            markerCount = 1;
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
            }
        }

    }

    public void animateMarker(final Location destination, final Marker marker) {
        if (marker != null) {
            final LatLng startPosition = marker.getPosition();
            final LatLng endPosition = new LatLng(destination.getLatitude(), destination.getLongitude());
            if (!startPosition.equals(endPosition)) {
                Log.e("lat", destination.getLatitude() + " " + destination.getLongitude());
                Log.e("destination", destination.getBearing() + "");

                final LatLngInterpolator latLngInterpolator = new LatLngInterpolator.LinearFixed();
                ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
                valueAnimator.setDuration(500); // duration 1 second
                valueAnimator.setInterpolator(new LinearInterpolator());
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        try {
                            float v = animation.getAnimatedFraction();
                            LatLng newPosition = latLngInterpolator.interpolate(v, startPosition, endPosition);
                            marker.setPosition(newPosition);
                            marker.setAnchor(0.5f, 0.5f);

                            marker.setRotation(getBearing(startPosition, newPosition));
                        } catch (Exception ex) {
                            // I don't care atm..
                        }
                    }
                });
                valueAnimator.start();
            }

        }
    }

    private void updateLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSION_LOCATION);
            return;
        }
        LocationServices.FusedLocationApi.getLastLocation(
                googleApiClient);
        gMap.setMyLocationEnabled(true);

        if (pickUpLatLng != null) {
            gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    pickUpLatLng, 15f)
            );

            gMap.animateCamera(CameraUpdateFactory.zoomTo(15f));
        }
    }

    private void requestRoute() {
        if (pickUpLatLng != null && destinationLatLng != null) {
            MapDirectionAPI.getDirection(pickUpLatLng, destinationLatLng).enqueue(updateRouteCallback);
        }
    }

    private void requestRoute2(LatLng destinationLatLng2) {
        if (pickUpLatLng != null && destinationLatLng != null && destinationLatLng2 != null) {
            MapDirectionAPI.getDirectionVia(pickUpLatLng, destinationLatLng, destinationLatLng2).enqueue(updateRouteCallback);
        }
    }

    private void requestRoute3(LatLng destinationLatLng3) {
        if (pickUpLatLng != null && destinationLatLng != null && destinationLatLng2 != null && destinationLatLng3 != null) {
            MapDirectionAPI.getDirectionVia(pickUpLatLng, destinationLatLng, destinationLatLng2, destinationLatLng3).enqueue(updateRouteCallback);
        }
    }

    private void requestRoute4(LatLng destinationLatLng4) {
        if (pickUpLatLng != null && destinationLatLng != null && destinationLatLng2 != null && destinationLatLng3 != null && destinationLatLng4 != null) {
            MapDirectionAPI.getDirectionVia(pickUpLatLng, destinationLatLng, destinationLatLng2, destinationLatLng3, destinationLatLng4).enqueue(updateRouteCallback);
        }
    }

    private void requestRoute5(LatLng destinationLatLng5) {
        if (pickUpLatLng != null && destinationLatLng != null && destinationLatLng2 != null && destinationLatLng3 != null && destinationLatLng4 != null && destinationLatLng5 != null) {
            MapDirectionAPI.getDirectionVia(pickUpLatLng, destinationLatLng, destinationLatLng2, destinationLatLng3, destinationLatLng4, destinationLatLng5).enqueue(updateRouteCallback);
        }
    }


    private void updateLineDestination(String json) {
        Directions directions = new Directions(ProgressActivity.this);
        try {
            List<Route> routes = directions.parse(json);

            if (directionLine != null) directionLine.remove();
            if (routes.size() > 0) {
                directionLine = gMap.addPolyline((new PolylineOptions())
                        .addAll(routes.get(0).getOverviewPolyLine())
                        .color(ContextCompat.getColor(ProgressActivity.this, R.color.colorgradient))
                        .width(8));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @SuppressLint("SetTextI18n")
    private void orderHandler(int code) {
        switch (code) {
            case Constants.REJECT:
                isCancelable = false;
                orderButton.setVisibility(View.GONE);
                break;
            case Constants.CANCEL:
                isCancelable = false;
                orderButton.setVisibility(View.GONE);
                llchat.setVisibility(View.GONE);
                status.setText(getString(R.string.notification_cancel));
                break;
            case Constants.ACCEPT:
                llchat.setVisibility(View.VISIBLE);
                if (gethome.equals("4")) {
                    status.setText("the driver is buying an order");
                } else {
                    status.setText(getString(R.string.notification_accept));
                }
                break;
            case Constants.START:
                llchat.setVisibility(View.VISIBLE);
                isCancelable = false;
                orderButton.setVisibility(View.GONE);
                if (gethome.equals("4")) {
                    status.setText("driver delivers the order");
                } else {
                    status.setText(getString(R.string.notification_start));
                }
                break;
            case Constants.FINISH:
                isCancelable = false;
                llchat.setVisibility(View.GONE);
                orderButton.setVisibility(View.GONE);
                status.setText(getString(R.string.notification_finish));
                getData(idtrans, iddriver);

                break;

            case Constants.TERIMA1:
                isCancelable = false;
                llchat.setVisibility(View.GONE);
                orderButton.setVisibility(View.GONE);
                status.setText("Paket Pertama Telah Diterima");
                getData(idtrans, iddriver);
                img_cek_item1.setVisibility(View.VISIBLE);
                break;

            case Constants.TERIMA2:
                isCancelable = false;
                llchat.setVisibility(View.GONE);
                orderButton.setVisibility(View.GONE);
                status.setText("Paket Kedua Telah Diterima");
                getData(idtrans, iddriver);
                img_cek_item2.setVisibility(View.VISIBLE);
                break;

            case Constants.TERIMA3:
                isCancelable = false;
                llchat.setVisibility(View.GONE);
                orderButton.setVisibility(View.GONE);
                status.setText("Paket Ketiga Telah Diterima");
                getData(idtrans, iddriver);
                img_cek_item3.setVisibility(View.VISIBLE);
                break;

            case Constants.TERIMA4:
                isCancelable = false;
                llchat.setVisibility(View.GONE);
                orderButton.setVisibility(View.GONE);
                status.setText("Paket Keempat Telah Diterima");
                getData(idtrans, iddriver);
                img_cek_item4.setVisibility(View.VISIBLE);
                break;

            case Constants.TERIMA5:
                isCancelable = false;
                llchat.setVisibility(View.GONE);
                orderButton.setVisibility(View.GONE);
                status.setText("Paket Kelima Telah Diterima");
                getData(idtrans, iddriver);
                img_cek_item5.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (response.equals("2") || response.equals("3") && !fitur.equals("0")) {
            startDriverLocationUpdate();
        }
        registerReceiver(broadcastReceiver, new IntentFilter(BROADCAST_ORDER));
        removeNotif();
    }

    @Override
    protected void onStart() {
        googleApiClient.connect();
        registerReceiver(broadcastReceiver, new IntentFilter(BROADCAST_ORDER));
        EventBus.getDefault().register(this);
        super.onStart();
    }

    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        EventBus.getDefault().unregister(this);
        super.onStop();

        unregisterReceiver(broadcastReceiver);
        if (response.equals("5") || response.equals("4")) {
            stopDriverLocationUpdate();
        }
    }

    @SuppressWarnings("unused")
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(final DriverResponse response) {
        Log.e("IN PROGRESS", response.getResponse() + " " + response.getId() + " " + response.getIdTransaksi());
        if (!complete.equals("true")) {
            orderHandler(Integer.parseInt(response.getResponse()));
            DriverResponse responses = new DriverResponse();
            responses.setId("");
            responses.setIdTransaksi("");
            responses.setResponse("");
            EventBus.getDefault().postSticky(responses);
        }

    }


    private interface LatLngInterpolator {
        LatLng interpolate(float fraction, LatLng a, LatLng b);

        class LinearFixed implements LatLngInterpolator {
            @Override
            public LatLng interpolate(float fraction, LatLng a, LatLng b) {
                double lat = (b.latitude - a.latitude) * fraction + a.latitude;
                double lngDelta = b.longitude - a.longitude;
                // Take the shortest path across the 180th meridian.
                if (Math.abs(lngDelta) > 180) {
                    lngDelta -= Math.signum(lngDelta) * 360;
                }
                double lng = lngDelta * fraction + a.longitude;
                return new LatLng(lat, lng);
            }
        }
    }
}
