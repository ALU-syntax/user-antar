package com.antar.passenger.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
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

import com.antar.passenger.R;
import com.antar.passenger.constants.BaseApp;
import com.antar.passenger.constants.Constants;
import com.antar.passenger.gmap.directions.Directions;
import com.antar.passenger.gmap.directions.Route;
import com.antar.passenger.json.CheckStatusTransaksiRequest;
import com.antar.passenger.json.CheckStatusTransaksiResponse;
import com.antar.passenger.json.FcmKeyResponseJson;
import com.antar.passenger.json.FcmRequestJson;
import com.antar.passenger.json.GetNearRideCarRequestJson;
import com.antar.passenger.json.GetNearRideCarResponseJson;
import com.antar.passenger.json.PromoRequestJson;
import com.antar.passenger.json.PromoResponseJson;
import com.antar.passenger.json.RideCarRequestJson;
import com.antar.passenger.json.RideCarResponseJson;
import com.antar.passenger.json.fcm.DriverRequest;
import com.antar.passenger.json.fcm.DriverResponse;
import com.antar.passenger.json.fcm.FCMMessage;
import com.antar.passenger.models.DriverModel;
import com.antar.passenger.models.FiturModel;
import com.antar.passenger.models.TransaksiModel;
import com.antar.passenger.models.User;
import com.antar.passenger.utils.LocaleHelper;
import com.antar.passenger.utils.PicassoTrustAll;
import com.antar.passenger.utils.SessionWilayah;
import com.antar.passenger.utils.Utility;
import com.antar.passenger.utils.api.FCMHelper;
import com.antar.passenger.utils.api.MapDirectionAPI;
import com.antar.passenger.utils.api.ServiceGenerator;
import com.antar.passenger.utils.api.service.BookService;
import com.antar.passenger.utils.api.service.UserService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
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
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.antar.passenger.json.fcm.FCMType.ORDER;


public class RideCarActivity extends AppCompatActivity
        implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    public static final String FITUR_KEY = "FiturKey";
    private static final String TAG = "RideCarActivity";
    private static final int REQUEST_PERMISSION_LOCATION = 991;
    String ICONFITUR;
    TransaksiModel transaksi;
    Thread thread;
    boolean threadRun = true;
    Context context = RideCarActivity.this;
    @BindView(R.id.pickUpContainer)
    LinearLayout setPickUpContainer;
    @BindView(R.id.destinationContainer)
    LinearLayout setDestinationContainer;
    @BindView(R.id.pickUpButton)
    Button setPickUpButton;
    @BindView(R.id.destinationButton)
    Button setDestinationButton;
    @BindView(R.id.pickUpText)
    TextView pickUpText;
    @BindView(R.id.bottom_sheet)
    LinearLayout bottomsheet;
    @BindView(R.id.destinationText)
    TextView destinationText;
    @BindView(R.id.detail)
    LinearLayout detail;
    @BindView(R.id.distance)
    TextView distanceText;
    @BindView(R.id.price)
    TextView priceText;
    @BindView(R.id.topUp)
    TextView topUp;
    @BindView(R.id.order)
    Button orderButton;
    @BindView(R.id.image)
    ImageView icon;
    @BindView(R.id.layanan)
    TextView layanan;
    @BindView(R.id.layanandes)
    TextView layanandesk;
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
    @BindView(R.id.back_btn)
    ImageView backbtn;
    @BindView(R.id.rlprogress)
    RelativeLayout rlprogress;
    @BindView(R.id.rlnotif)
    RelativeLayout rlnotif;
    @BindView(R.id.textnotif)
    TextView textnotif;
    @BindView(R.id.textprogress)
    TextView textprogress;
    @BindView(R.id.fitur)
    TextView fiturtext;
    @BindView(R.id.promocode)
    EditText promokode;
    @BindView(R.id.btnpromo)
    Button btnpromo;
    @BindView(R.id.promonotif)
    TextView promonotif;
    String fitur, getbiaya, biayaminimum, biayaakhir, icondriver;
    private DriverRequest request;
    private GoogleMap gMap;
    private GoogleApiClient googleApiClient;
    private Location lastKnownLocation;
    private LatLng pickUpLatLang;
    private LatLng destinationLatLang;
    private Polyline directionLine;
    private Marker pickUpMarker;
    private Marker destinationMarker;
    private List<DriverModel> driverAvailable;
    private List<Marker> driverMarkers;
    private Realm realm;
    private FiturModel designedFitur;
    private double jarak;
    private long harga, promocode, maksimum;
    private String saldoWallet;
    private String checkedpaywallet;
    private boolean isMapReady = false;
    private SessionWilayah sessionWilayah;
    private String keyss;
    private okhttp3.Callback updateRouteCallback = new okhttp3.Callback() {
        @Override
        public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {
            notif("error connection, please select destination again!");
            setDestinationContainer.setVisibility(View.VISIBLE);
            rlprogress.setVisibility(View.GONE);
        }

        @Override
        public void onResponse(@NonNull okhttp3.Call call, okhttp3.Response response) throws IOException {
            if (response.isSuccessful()) {
                final String json = Objects.requireNonNull(response.body()).string();
                final long distance = MapDirectionAPI.getDistance(RideCarActivity.this, json);
                final String time = MapDirectionAPI.getTimeDistance(RideCarActivity.this, json);
                if (distance >= 0) {
                    RideCarActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String format = String.format(Locale.US, "%.0f", (double) distance / 1000f);
                            long dist = Long.parseLong(format);
//                            Toast.makeText(RideCarActivity.this, "maks "+maksimum, Toast.LENGTH_SHORT).show();
                            if (dist < maksimum) {
                                rlprogress.setVisibility(View.GONE);
                                promocode = 0;
                                promokode.setText("");
                                updateLineDestination(json);
                                updateDistance(distance);
                                fiturtext.setText(time);
                                String diskontotal = String.valueOf(promocode);
                                Utility.currencyTXT(diskon, diskontotal, RideCarActivity.this);
                            } else {
                                detail.setVisibility(View.GONE);
                                setDestinationContainer.setVisibility(View.VISIBLE);
                                rlprogress.setVisibility(View.GONE);
                                notif("destination too far away!");
                            }

                        }
                    });
                }
            }
        }
    };

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase));
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride);
        getkey();
        ButterKnife.bind(this);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomsheet);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        sessionWilayah = new SessionWilayah(this);
        promocode = 0;

        Places.initialize(getApplicationContext(), getString(R.string.google_maps_key));

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), getString(R.string.google_maps_key));
        }

        setPickUpContainer.setVisibility(View.VISIBLE);
        setDestinationContainer.setVisibility(View.GONE);
        detail.setVisibility(View.GONE);

        User userLogin = BaseApp.getInstance(this).getLoginUser();
        saldoWallet = String.valueOf(userLogin.getWalletSaldo());

        setPickUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPickUp();
            }
        });

        setDestinationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDestination();
            }
        });


        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        topUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), TopupSaldoActivity.class));
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


        pickUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPickUpContainer.setVisibility(View.VISIBLE);
                setDestinationContainer.setVisibility(View.GONE);
                openAutocompleteActivity(1);
            }
        });

        destinationText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDestinationContainer.setVisibility(View.VISIBLE);
                setPickUpContainer.setVisibility(View.GONE);
                openAutocompleteActivity(2);
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView);
        Objects.requireNonNull(mapFragment).getMapAsync(this);

        driverAvailable = new ArrayList<>();
        driverMarkers = new ArrayList<>();

        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        realm = Realm.getDefaultInstance();

        Intent intent = getIntent();
        int fiturId = intent.getIntExtra(FITUR_KEY, -1);
        ICONFITUR = intent.getStringExtra("icon");
        Log.e("FITUR_ID", fiturId + "");
        if (fiturId != -1)
            designedFitur = realm.where(FiturModel.class).equalTo("idFitur", fiturId).findFirst();

        RealmResults<FiturModel> fiturs = realm.where(FiturModel.class).findAll();

        for (FiturModel fitur : fiturs) {
            Log.e("ID_FITUR", fitur.getIdFitur() + " " + fitur.getFitur() + " " + fitur.getBiayaAkhir() + " " + ICONFITUR);
        }
        fitur = String.valueOf(designedFitur.getIdFitur());
        getbiaya = String.valueOf(designedFitur.getBiaya());
        biayaminimum = String.valueOf(designedFitur.getBiaya_minimum());
        biayaakhir = String.valueOf(designedFitur.getBiayaAkhir());
        icondriver = designedFitur.getIcon_driver();
        maksimum = Long.parseLong(designedFitur.getMaksimumdist());

        updateFitur();

        diskontext.setText("Discount " + designedFitur.getDiskon() + " with Wallet");
        PicassoTrustAll.getInstance(this)
                .load(Constants.IMAGESFITUR + ICONFITUR)
                .placeholder(R.drawable.logo)
                .resize(100, 100)
                .into(icon);

        layanan.setText(designedFitur.getFitur());
        layanandesk.setText(designedFitur.getKeterangan());

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
                            Utility.currencyTXT(priceText, totalbiaya, context);
                            Utility.currencyTXT(diskon, diskontotal, RideCarActivity.this);
                        } else {
                            String diskontotal = String.valueOf(promocode);
                            String totalbiaya = String.valueOf(harga - promocode);
                            Utility.currencyTXT(priceText, totalbiaya, context);
                            Utility.currencyTXT(diskon, diskontotal, RideCarActivity.this);
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
                            Utility.currencyTXT(priceText, totalbiaya, context);
                            Utility.currencyTXT(diskon, diskontotal, RideCarActivity.this);
                        } else {
                            String diskontotal = String.valueOf(promocode);
                            String totalbiaya = String.valueOf(harga - promocode);
                            Utility.currencyTXT(priceText, totalbiaya, context);
                            Utility.currencyTXT(diskon, diskontotal, RideCarActivity.this);
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

    private void  getkey() {
        User loginUser = BaseApp.getInstance(RideCarActivity.this).getLoginUser();
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

    private void openAutocompleteActivity(int request_code) {
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.ADDRESS, Place.Field.LAT_LNG);
        Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.FULLSCREEN, fields)
                .build(this);
        startActivityForResult(intent, request_code);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                pickUpText.setText(place.getAddress());
                LatLng latLng = place.getLatLng();
                if (latLng != null) {
                    gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                            new LatLng(latLng.latitude, latLng.longitude), 15f)
                    );
                    onPickUp();
                }
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i(TAG, Objects.requireNonNull(status.getStatusMessage()));
            }
        }
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                destinationText.setText(place.getAddress());
                LatLng latLng = place.getLatLng();
                if (latLng != null) {
                    gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                            new LatLng(latLng.latitude, latLng.longitude), 15f)
                    );
                    onDestination();
                }
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i(TAG, Objects.requireNonNull(status.getStatusMessage()));
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_PERMISSION_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                updateLastLocation(true);
            }
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        updateLastLocation(true);
    }

    @Override
    public void onConnectionSuspended(int i) {
        updateLastLocation(true);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        updateLastLocation(true);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        gMap.getUiSettings().setMyLocationButtonEnabled(true);
        gMap.getUiSettings().setMapToolbarEnabled(true);
        try {
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.style_json));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }

        isMapReady = true;

        updateLastLocation(true);
    }

    private void updateLastLocation(boolean move) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSION_LOCATION);
            return;
        }
        lastKnownLocation = LocationServices.FusedLocationApi.getLastLocation(
                googleApiClient);
        gMap.setMyLocationEnabled(true);

        if (lastKnownLocation != null) {
            if (move) {
                gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude()), 15f)
                );

                gMap.animateCamera(CameraUpdateFactory.zoomTo(15f));
            }

            fetchNearDriver(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude(), fitur);
        }
    }

    private void updateFitur() {
        if (driverAvailable != null) {
            driverAvailable.clear();
        }
        if (driverMarkers != null) {
            for (Marker m : driverMarkers) {
                m.remove();
            }
            driverMarkers.clear();
        }
        if (isMapReady) updateLastLocation(false);
    }

    private void createMarker() {
        if (!driverAvailable.isEmpty()) {
            for (Marker m : driverMarkers) {
                m.remove();
            }

            driverMarkers.clear();
            for (DriverModel driver : driverAvailable) {
                LatLng currentDriverPos = new LatLng(driver.getLatitude(), driver.getLongitude());

                if (icondriver.equals("1")) {
                    driverMarkers.add(
                            gMap.addMarker(new MarkerOptions()
                                    .position(currentDriverPos)
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.drivermap))
                                    .anchor((float) 0.5, (float) 0.5)
                                    .rotation(Float.parseFloat(driver.getBearing()))
                                    .flat(true)
                            )
                    );
                } else if (icondriver.equals("2")) {
                    driverMarkers.add(
                            gMap.addMarker(new MarkerOptions()
                                    .position(currentDriverPos)
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.carmap))
                                    .anchor((float) 0.5, (float) 0.5)
                                    .rotation(Float.parseFloat(driver.getBearing()))
                                    .flat(true)
                            )
                    );
                } else if (icondriver.equals("3")) {
                    driverMarkers.add(
                            gMap.addMarker(new MarkerOptions()
                                    .position(currentDriverPos)
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.truck))
                                    .anchor((float) 0.5, (float) 0.5)
                                    .rotation(Float.parseFloat(driver.getBearing()))
                                    .flat(true)
                            )
                    );
                } else if (icondriver.equals("4")) {
                    driverMarkers.add(
                            gMap.addMarker(new MarkerOptions()
                                    .position(currentDriverPos)
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.delivery))
                                    .anchor((float) 0.5, (float) 0.5)
                                    .rotation(Float.parseFloat(driver.getBearing()))
                                    .flat(true)
                            )
                    );
                } else if (icondriver.equals("5")) {
                    driverMarkers.add(
                            gMap.addMarker(new MarkerOptions()
                                    .position(currentDriverPos)
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.hatchback))
                                    .anchor((float) 0.5, (float) 0.5)
                                    .rotation(Float.parseFloat(driver.getBearing()))
                                    .flat(true)
                            )
                    );
                } else if (icondriver.equals("6")) {
                    driverMarkers.add(
                            gMap.addMarker(new MarkerOptions()
                                    .position(currentDriverPos)
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.suv))
                                    .anchor((float) 0.5, (float) 0.5)
                                    .rotation(Float.parseFloat(driver.getBearing()))
                                    .flat(true)
                            )
                    );
                } else if (icondriver.equals("7")) {
                    driverMarkers.add(
                            gMap.addMarker(new MarkerOptions()
                                    .position(currentDriverPos)
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.van))
                                    .anchor((float) 0.5, (float) 0.5)
                                    .rotation(Float.parseFloat(driver.getBearing()))
                                    .flat(true)
                            )
                    );
                } else if (icondriver.equals("8")) {
                    driverMarkers.add(
                            gMap.addMarker(new MarkerOptions()
                                    .position(currentDriverPos)
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.bicycle))
                                    .anchor((float) 0.5, (float) 0.5)
                                    .rotation(Float.parseFloat(driver.getBearing()))
                                    .flat(true)
                            )
                    );
                } else if (icondriver.equals("9")) {
                    driverMarkers.add(
                            gMap.addMarker(new MarkerOptions()
                                    .position(currentDriverPos)
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.bajaj))
                                    .anchor((float) 0.5, (float) 0.5)
                                    .rotation(Float.parseFloat(driver.getBearing()))
                                    .flat(true)
                            )
                    );
                }
            }
        }
    }


    private void onDestination() {

        if (destinationMarker != null) destinationMarker.remove();
        LatLng centerPos = gMap.getCameraPosition().target;
        destinationMarker = gMap.addMarker(new MarkerOptions()
                .position(centerPos)
                .title("Destination")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.destination)));
        destinationLatLang = centerPos;

        requestAddress(centerPos, destinationText);
        requestRoute();

        setDestinationContainer.setVisibility(View.GONE);
        if (pickUpText.getText().toString().isEmpty()) {
            setPickUpContainer.setVisibility(View.VISIBLE);
        } else {
            setPickUpContainer.setVisibility(View.GONE);
        }
    }

    private void onPickUp() {
        setDestinationContainer.setVisibility(View.VISIBLE);
        setPickUpContainer.setVisibility(View.GONE);
        if (pickUpMarker != null) pickUpMarker.remove();
        LatLng centerPos = gMap.getCameraPosition().target;
        pickUpMarker = gMap.addMarker(new MarkerOptions()
                .position(centerPos)
                .title("Pick Up")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pickup)));
        pickUpLatLang = centerPos;
        textprogress.setVisibility(View.VISIBLE);

        requestAddress(centerPos, pickUpText);
        fetchNearDriver(pickUpLatLang.latitude, pickUpLatLang.longitude, fitur);
        requestRoute();
    }

    private void requestRoute() {
        if (pickUpLatLang != null && destinationLatLang != null) {
            rlprogress.setVisibility(View.VISIBLE);
            textprogress.setText(getString(R.string.waiting_pleaseWait));
            MapDirectionAPI.getDirection(pickUpLatLang, destinationLatLang).enqueue(updateRouteCallback);
        }
    }


    private void updateLineDestination(String json) {
        Directions directions = new Directions(RideCarActivity.this);
        try {
            List<Route> routes = directions.parse(json);

            if (directionLine != null) directionLine.remove();
            if (routes.size() > 0) {
                directionLine = gMap.addPolyline((new PolylineOptions())
                        .addAll(routes.get(0).getOverviewPolyLine())
                        .color(ContextCompat.getColor(RideCarActivity.this, R.color.colorgradient))
                        .width(8));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateDistance(long distance) {
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomsheet);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        detail.setVisibility(View.VISIBLE);
        setDestinationContainer.setVisibility(View.GONE);
        setPickUpContainer.setVisibility(View.GONE);
        orderButton.setVisibility(View.VISIBLE);

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
        float km = ((float) (distance)) / 1000f;

        this.jarak = km;

        String format = String.format(Locale.US, "%.1f", km);
        distanceText.setText(format);
        String biaya = String.valueOf(biayaminimum);
        long biayaTotal = (long) (Double.parseDouble(getbiaya) * km);
        if (biayaTotal < Double.parseDouble(biayaminimum)) {
            this.harga = Long.parseLong(biayaminimum);
            biayaTotal = Long.parseLong(biayaminimum);
            Utility.currencyTXT(cost, biaya, this);
        } else {
            Utility.currencyTXT(cost, getbiaya, this);
        }
        this.harga = biayaTotal;

        final long finalBiayaTotal = biayaTotal;
        String totalbiaya = String.valueOf(finalBiayaTotal);
        Utility.currencyTXT(priceText, totalbiaya, this);

        long saldokini = Long.parseLong(saldoWallet);
        if (saldokini < (biayaTotal - (harga * Double.parseDouble(biayaakhir)))) {
            llcheckedcash.setOnClickListener(view -> {
                String totalbiaya12 = String.valueOf(finalBiayaTotal - promocode);
                Utility.currencyTXT(priceText, totalbiaya12, context);
                String diskontotal = String.valueOf(promocode);
                Utility.currencyTXT(diskon, diskontotal, RideCarActivity.this);
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
            });
        } else {
            llcheckedcash.setOnClickListener(view -> {
                String diskontotal = String.valueOf(promocode);
                String totalbiaya1 = String.valueOf(finalBiayaTotal - promocode);
                Utility.currencyTXT(priceText, totalbiaya1, context);
                Utility.currencyTXT(diskon, diskontotal, RideCarActivity.this);

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
            });

            final long finalBiayaTotal1 = biayaTotal;
            llcheckedwallet.setOnClickListener(view -> {
                long diskonwallet = (long) (Double.parseDouble(biayaakhir) * harga);
                String totalwallet = String.valueOf(diskonwallet + promocode);
                Utility.currencyTXT(diskon, totalwallet, context);
                String totalbiaya13 = String.valueOf(finalBiayaTotal1 - (diskonwallet + promocode));
                Utility.currencyTXT(priceText, totalbiaya13, context);
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
//                notif("Untuk Alasan Keamanan Untuk sementara fitur saldo kami nonaktifkan terlebih dahulu.");
            });
        }

        orderButton.setOnClickListener(v -> onOrderButton());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    private void fetchNearDriver(double latitude, double longitude, String fitur) {
        if (driverAvailable != null) {
            driverAvailable.clear();
        }
        if (driverMarkers != null) {
            for (Marker m : driverMarkers) {
                m.remove();
            }
            driverMarkers.clear();
        }
        if (lastKnownLocation != null) {
            User loginUser = BaseApp.getInstance(this).getLoginUser();

            BookService service = ServiceGenerator.createService(BookService.class, loginUser.getEmail(), loginUser.getPassword());
            GetNearRideCarRequestJson param = new GetNearRideCarRequestJson();
            param.setLatitude(latitude);
            param.setLongitude(longitude);
            param.setFitur(fitur);

            service.getNearRide(param).enqueue(new Callback<GetNearRideCarResponseJson>() {
                @Override
                public void onResponse(@NonNull Call<GetNearRideCarResponseJson> call, @NonNull Response<GetNearRideCarResponseJson> response) {
                    if (response.isSuccessful()) {
                        driverAvailable = Objects.requireNonNull(response.body()).getData();
                        createMarker();
                    }
                }

                @Override
                public void onFailure(@NonNull retrofit2.Call<GetNearRideCarResponseJson> call, @NonNull Throwable t) {

                }
            });
        }
    }


    private void requestAddress(LatLng latlang, final TextView textView) {
        if (latlang != null) {
            MapDirectionAPI.getAddress(latlang).enqueue(new okhttp3.Callback() {
                @Override
                public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {

                }

                @Override
                public void onResponse(@NonNull okhttp3.Call call, @NonNull final okhttp3.Response response) throws IOException {
                    if (response.isSuccessful()) {
                        final String json = Objects.requireNonNull(response.body()).string();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    JSONObject Jobject = new JSONObject(json);
                                    JSONArray Jarray = Jobject.getJSONArray("results");
                                    JSONObject userdata = Jarray.getJSONObject(0);
                                    String address = userdata.getString("formatted_address");
                                    textView.setText(address);
                                    Log.e("TESTER", userdata.getString("formatted_address"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }
            });
        }
    }


    private void onOrderButton() {
        HashMap<String,String> wilayah = sessionWilayah.getSessionData();
        if (driverAvailable.isEmpty()) {
//            notif("Sorry, there are no drivers around you.");
            noDriver();
        } else {


            RideCarRequestJson param = new RideCarRequestJson();
            User userLogin = BaseApp.getInstance(this).getLoginUser();
            param.setIdPelanggan(userLogin.getId());
            param.setOrderFitur(fitur);
            param.setStartLatitude(pickUpLatLang.latitude);
            param.setStartLongitude(pickUpLatLang.longitude);
            param.setEndLatitude(destinationLatLang.latitude);
            param.setEndLongitude(destinationLatLang.longitude);
            param.setJarak(this.jarak);
            param.setHarga(this.harga);
            param.setEstimasi(fiturtext.getText().toString());
            param.setAlamatAsal(pickUpText.getText().toString());
            param.setAlamatTujuan(destinationText.getText().toString());
            param.setWilayah(Integer.parseInt(wilayah.get(sessionWilayah.IDWILAYAH)));
            if (checkedpaywallet.equals("1")) {
                param.setKreditpromo(String.valueOf((Double.parseDouble(biayaakhir) * this.harga) + promocode));
                param.setPakaiWallet(1);
                sendRequestTransaksi(param, driverAvailable);
            } else if (checkedpaywallet.equals("0")) {
                param.setKreditpromo(String.valueOf(promocode));
                param.setPakaiWallet(0);
                sendRequestTransaksi(param, driverAvailable);
            } else {
                param.setKreditpromo(String.valueOf((Double.parseDouble(biayaakhir) * this.harga) + promocode));
                param.setPakaiWallet(2);
                sendRequestTransaksi(param, driverAvailable);
            }
        }
    }

    private void sendRequestTransaksi(RideCarRequestJson params, final List<DriverModel> driverList) {
        rlprogress.setVisibility(View.VISIBLE);
        textprogress.setText(getString(R.string.waiting_desc));
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        final BookService service = ServiceGenerator.createService(BookService.class, loginUser.getEmail(), loginUser.getPassword());

        service.requestTransaksi(params).enqueue(new Callback<RideCarResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<RideCarResponseJson> call, @NonNull Response<RideCarResponseJson> response) {
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
//                                            Toast.makeText(RideCarActivity.this, ""+ checkStatus.getMessage(), Toast.LENGTH_LONG).show();
                                            if (!Objects.requireNonNull(checkStatus).isStatus()) {
                                                notif("Driver not found!");
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        notif("Driver not found!");
                                                    }
                                                });

                                                new Handler().postDelayed(new Runnable() {
                                                    public void run() {
                                                        finish();
                                                    }
                                                }, 3000);
                                            } else {
//                                                requestXenditPayment(params);
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
                                            }
                                        });

                                        new Handler().postDelayed(new Runnable() {
                                            public void run() {
                                                finish();
                                            }
                                        }, 3000);

                                    }
                                });
                            }

                        }
                    });
                    thread.start();


                }
            }

            @Override
            public void onFailure(@NonNull Call<RideCarResponseJson> call, @NonNull Throwable t) {
                t.printStackTrace();
                notif("Your account has a problem, please contact passenger service!");
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        finish();
                    }
                }, 3000);
            }
        });
    }

    private void buildDriverRequest(RideCarResponseJson response) {
        transaksi = response.getData().get(0);
        Log.e("wallet", String.valueOf(transaksi.isPakaiWallet()));
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        if (request == null) {
            request = new DriverRequest();
            request.setIdTransaksi(transaksi.getId());
            request.setIdPelanggan(transaksi.getIdPelanggan());
            request.setRegIdPelanggan(loginUser.getToken());
            request.setOrderFitur(designedFitur.getHome());
            request.setStartLatitude(transaksi.getStartLatitude());
            request.setStartLongitude(transaksi.getStartLongitude());
            request.setEndLatitude(transaksi.getEndLatitude());
            request.setEndLongitude(transaksi.getEndLongitude());
            request.setJarak(transaksi.getJarak());
            request.setHarga(transaksi.getHarga());
            request.setWaktuOrder(transaksi.getWaktuOrder());
            request.setAlamatAsal(transaksi.getAlamatAsal());
            request.setAlamatTujuan(transaksi.getAlamatTujuan());
            request.setKodePromo(transaksi.getKodePromo());
            request.setKreditPromo(transaksi.getKreditPromo());
            request.setPakaiWallet(String.valueOf(transaksi.isPakaiWallet()));
            request.setEstimasi(transaksi.getEstimasi());
            request.setLayanan(layanan.getText().toString());
            request.setLayanandesc(layanandesk.getText().toString());
            request.setIcon(ICONFITUR);
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
        Log.e("DRIVER RESPONSE (W)", response.getResponse() + " " + response.getId() + " " + response.getIdTransaksi());
        if (response.getResponse().equalsIgnoreCase(DriverResponse.ACCEPT) || response.getResponse().equals("3") || response.getResponse().equals("4")) {
            runOnUiThread(new Runnable() {
                public void run() {
                    threadRun = false;
                    for (DriverModel cDriver : driverAvailable) {
                        if (cDriver.getId().equals(response.getId())) {
                            Intent intent = new Intent(RideCarActivity.this, ProgressActivity.class);
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
        googleApiClient.connect();
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        User userLogin = BaseApp.getInstance(this).getLoginUser();
        saldoWallet = String.valueOf(userLogin.getWalletSaldo());
        Utility.currencyTXT(saldotext, saldoWallet, this);
    }


    private void noDriver() {
        double biayaTotal = harga;
        String formattedTotal = NumberFormat.getNumberInstance(Locale.US).format(biayaTotal);
        final String formattedTextPrice = String.format(Locale.US, "IDR %s.00", formattedTotal);
        AlertDialog dialog = new AlertDialog.Builder(RideCarActivity.this)
                .setTitle("Maaf Tidak ada Driver kami yang online")
                .setMessage("Silahkan Booking manual(whatsapp) melalu staf kami sehingga bisa di proses secepat mungkin.")
                .setPositiveButton("Book Manual", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String message = "BUMDES-KU"
                                + "\n" +
                                "Asal : " + pickUpText.getText().toString()
                                + "\n " +
                                "Tujuan " + " :" +
                                destinationText.getText().toString()
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


}
