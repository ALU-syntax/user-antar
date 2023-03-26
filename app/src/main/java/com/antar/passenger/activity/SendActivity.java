package com.antar.passenger.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
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

import com.antar.passenger.R;
import com.antar.passenger.constants.BaseApp;
import com.antar.passenger.constants.Constants;
import com.antar.passenger.gmap.directions.Directions;
import com.antar.passenger.gmap.directions.Route;
import com.antar.passenger.json.GetNearRideCarRequestJson;
import com.antar.passenger.json.GetNearRideCarResponseJson;
import com.antar.passenger.models.DriverModel;
import com.antar.passenger.models.FiturModel;
import com.antar.passenger.models.User;
import com.antar.passenger.utils.LocaleHelper;
import com.antar.passenger.utils.PicassoTrustAll;
import com.antar.passenger.utils.api.MapDirectionAPI;
import com.antar.passenger.utils.api.ServiceGenerator;
import com.antar.passenger.utils.api.service.BookService;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
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


public class SendActivity extends AppCompatActivity
        implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    public static final String FITUR_KEY = "FiturKey";
    private static final String TAG = "RideCarActivity";
    private static final int REQUEST_PERMISSION_LOCATION = 991;
    String ICONFITUR;
    @BindView(R.id.pickUpContainer)
    LinearLayout setPickUpContainer;
    @BindView(R.id.destinationContainer)
    LinearLayout setDestinationContainer;
    @BindView(R.id.destinationContainer2)
    LinearLayout setDestinationContainer2;
    @BindView(R.id.destinationContainer3)
    LinearLayout setDestinationContainer3;
    @BindView(R.id.destinationContainer4)
    LinearLayout setDestinationContainer4;
    @BindView(R.id.destinationContainer5)
    LinearLayout setDestinationContainer5;


    @BindView(R.id.pickUpButton)
    Button setPickUpButton;
    @BindView(R.id.destinationButton)
    Button setDestinationButton;

    @BindView(R.id.destinationButton2)
    Button setDestinationButton2;
    @BindView(R.id.destinationButton3)
    Button setDestinationButton3;
    @BindView(R.id.destinationButton4)
    Button setDestinationButton4;
    @BindView(R.id.destinationButton5)
    Button setDestinationButton5;

    @BindView(R.id.pickUpText)
    TextView pickUpText;
    @BindView(R.id.bottom_sheet)
    LinearLayout bottomsheet;

    @BindView(R.id.destinationText)
    TextView destinationText;

    @BindView(R.id.destinationText2)
    TextView destinationText2;

    @BindView(R.id.destinationText3)
    TextView destinationText3;

    @BindView(R.id.destinationText4)
    TextView destinationText4;

    @BindView(R.id.destinationText5)
    TextView destinationText5;

    @BindView(R.id.order)
    Button orderButton;
    @BindView(R.id.image)
    ImageView icon;
    @BindView(R.id.layanan)
    TextView layanan;
    @BindView(R.id.layanandes)
    TextView layanandesk;
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
    String fitur, getbiaya, biayaminimum, biayaakhir;
    int fiturId;
    long maksimum;
    private GoogleMap gMap;
    private GoogleApiClient googleApiClient;
    private Location lastKnownLocation;
    private LatLng pickUpLatLang;
    private LatLng destinationLatLang;
    private LatLng destinationLatLang2;
    private LatLng destinationLatLang3;
    private LatLng destinationLatLang4;
    private LatLng destinationLatLang5;

    private Polyline directionLine;
    private Marker pickUpMarker;
    private Marker destinationMarker;
    private Marker destinationMarker2;
    private Marker destinationMarker3;
    private Marker destinationMarker4;
    private Marker destinationMarker5;
    private ArrayList<DriverModel> driverAvailable;
    private List<Marker> driverMarkers;
    private Realm realm;
    private FiturModel designedFitur;
    private double jarak;
    private boolean isMapReady = false;
    private String timeDistance, icondrver;
    private okhttp3.Callback updateRouteCallback = new okhttp3.Callback() {
        @Override
        public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {
        }

        @Override
        public void onResponse(@NonNull okhttp3.Call call, okhttp3.Response response) throws IOException {
            if (response.isSuccessful()) {
                final String json = Objects.requireNonNull(response.body()).string();
                final long distance = MapDirectionAPI.getDistance(SendActivity.this, json);
                final String time = MapDirectionAPI.getTimeDistance(SendActivity.this, json);
                if (distance >= 0) {
                    SendActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            String format = String.format(Locale.US, "%.0f", (double) distance / 1000f);
                            long dist = Long.parseLong(format);
                            if (dist < maksimum) {
                                rlprogress.setVisibility(View.GONE);
                                updateLineDestination(json);
                                updateDistance(distance);
                                timeDistance = time;
                            } else {
                                orderButton.setEnabled(false);
                                orderButton.setBackground(getResources().getDrawable(R.drawable.rounded_corners_button));
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
        ButterKnife.bind(this);
        driverAvailable = new ArrayList<>();
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomsheet);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        Places.initialize(getApplicationContext(), getString(R.string.google_maps_key));

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), getString(R.string.google_maps_key));
        }

        setPickUpContainer.setVisibility(View.VISIBLE);
        setDestinationContainer.setVisibility(View.GONE);
        setDestinationContainer2.setVisibility(View.GONE);
        setDestinationContainer3.setVisibility(View.GONE);
        setDestinationContainer4.setVisibility(View.GONE);
        setDestinationContainer5.setVisibility(View.GONE);

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

        setDestinationButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDestination2();
            }
        });

        setDestinationButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDestination3();
            }
        });

        setDestinationButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDestination4();
            }
        });

        setDestinationButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDestination5();
            }
        });


        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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

        destinationText2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDestinationContainer.setVisibility(View.VISIBLE);
                setPickUpContainer.setVisibility(View.GONE);
                openAutocompleteActivity(3);
            }
        });

        destinationText3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDestinationContainer.setVisibility(View.VISIBLE);
                setPickUpContainer.setVisibility(View.GONE);
                openAutocompleteActivity(4);
            }
        });

        destinationText4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDestinationContainer.setVisibility(View.VISIBLE);
                setPickUpContainer.setVisibility(View.GONE);
                openAutocompleteActivity(5);
            }
        });


        destinationText5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDestinationContainer.setVisibility(View.VISIBLE);
                setPickUpContainer.setVisibility(View.GONE);
                openAutocompleteActivity(6);
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
        fiturId = intent.getIntExtra(FITUR_KEY, -1);
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
        icondrver = designedFitur.getIcon_driver();
        maksimum = Long.parseLong(designedFitur.getMaksimumdist());

        Log.e("biaya", getbiaya);

        updateFitur();

        PicassoTrustAll.getInstance(this)
                .load(Constants.IMAGESFITUR + ICONFITUR)
                .placeholder(R.drawable.logo)
                .resize(100, 100)
                .into(icon);

        layanan.setText(designedFitur.getFitur());
        layanandesk.setText(designedFitur.getKeterangan());
        orderButton.setBackground(getResources().getDrawable(R.drawable.rounded_corners_button));
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

        if (requestCode == 3) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                destinationText2.setText(place.getAddress());
                LatLng latLng = place.getLatLng();
                if (latLng != null) {
                    gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                            new LatLng(latLng.latitude, latLng.longitude), 15f)
                    );
                    onDestination2();
                }
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i(TAG, Objects.requireNonNull(status.getStatusMessage()));
            }
        }

        if (requestCode == 4) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                destinationText.setText(place.getAddress());
                LatLng latLng = place.getLatLng();
                if (latLng != null) {
                    gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                            new LatLng(latLng.latitude, latLng.longitude), 15f)
                    );
                    onDestination3();
                }
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i(TAG, Objects.requireNonNull(status.getStatusMessage()));
            }
        }

        if (requestCode == 5) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                destinationText.setText(place.getAddress());
                LatLng latLng = place.getLatLng();
                if (latLng != null) {
                    gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                            new LatLng(latLng.latitude, latLng.longitude), 15f)
                    );
                    onDestination4();
                }
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i(TAG, Objects.requireNonNull(status.getStatusMessage()));
            }
        }

        if (requestCode == 6) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                destinationText.setText(place.getAddress());
                LatLng latLng = place.getLatLng();
                if (latLng != null) {
                    gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                            new LatLng(latLng.latitude, latLng.longitude), 15f)
                    );
                    onDestination5();
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

            fetchNearDriver(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
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

                if (icondrver.equals("1")) {
                    driverMarkers.add(
                            gMap.addMarker(new MarkerOptions()
                                    .position(currentDriverPos)
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.drivermap))
                                    .anchor((float) 0.5, (float) 0.5)
                                    .rotation(Float.parseFloat(driver.getBearing()))
                                    .flat(true)
                            )
                    );
                } else if (icondrver.equals("2")) {
                    driverMarkers.add(
                            gMap.addMarker(new MarkerOptions()
                                    .position(currentDriverPos)
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.carmap))
                                    .anchor((float) 0.5, (float) 0.5)
                                    .rotation(Float.parseFloat(driver.getBearing()))
                                    .flat(true)
                            )
                    );
                } else if (icondrver.equals("3")) {
                    driverMarkers.add(
                            gMap.addMarker(new MarkerOptions()
                                    .position(currentDriverPos)
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.truck))
                                    .anchor((float) 0.5, (float) 0.5)
                                    .rotation(Float.parseFloat(driver.getBearing()))
                                    .flat(true)
                            )
                    );
                } else if (icondrver.equals("4")) {
                    driverMarkers.add(
                            gMap.addMarker(new MarkerOptions()
                                    .position(currentDriverPos)
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.delivery))
                                    .anchor((float) 0.5, (float) 0.5)
                                    .rotation(Float.parseFloat(driver.getBearing()))
                                    .flat(true)
                            )
                    );
                } else if (icondrver.equals("5")) {
                    driverMarkers.add(
                            gMap.addMarker(new MarkerOptions()
                                    .position(currentDriverPos)
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.hatchback))
                                    .anchor((float) 0.5, (float) 0.5)
                                    .rotation(Float.parseFloat(driver.getBearing()))
                                    .flat(true)
                            )
                    );
                } else if (icondrver.equals("6")) {
                    driverMarkers.add(
                            gMap.addMarker(new MarkerOptions()
                                    .position(currentDriverPos)
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.suv))
                                    .anchor((float) 0.5, (float) 0.5)
                                    .rotation(Float.parseFloat(driver.getBearing()))
                                    .flat(true)
                            )
                    );
                } else if (icondrver.equals("7")) {
                    driverMarkers.add(
                            gMap.addMarker(new MarkerOptions()
                                    .position(currentDriverPos)
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.van))
                                    .anchor((float) 0.5, (float) 0.5)
                                    .rotation(Float.parseFloat(driver.getBearing()))
                                    .flat(true)
                            )
                    );
                } else if (icondrver.equals("8")) {
                    driverMarkers.add(
                            gMap.addMarker(new MarkerOptions()
                                    .position(currentDriverPos)
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.bicycle))
                                    .anchor((float) 0.5, (float) 0.5)
                                    .rotation(Float.parseFloat(driver.getBearing()))
                                    .flat(true)
                            )
                    );
                } else if (icondrver.equals("9")) {
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


    private void onDestination2() {
        if (destinationMarker2 != null) destinationMarker2.remove();
        if (destinationMarker3 != null) destinationMarker3.remove();
        if (destinationMarker4 != null) destinationMarker4.remove();
        if (destinationMarker5 != null) destinationMarker5.remove();

        destinationText3.setText("");
        destinationText4.setText("");
        destinationText5.setText("");

        destinationLatLang3 = null;
        destinationLatLang4 = null;
        destinationLatLang5 = null;

        setDestinationContainer.setVisibility(View.GONE);
        setDestinationContainer2.setVisibility(View.GONE);
        setDestinationContainer3.setVisibility(View.VISIBLE);
        setDestinationContainer4.setVisibility(View.GONE);
        setDestinationContainer5.setVisibility(View.GONE);

        LatLng centerPos = gMap.getCameraPosition().target;
        destinationMarker2 = gMap.addMarker(new MarkerOptions()
                .position(centerPos)
                .title("Destination2")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.destination)));
        destinationLatLang2 = centerPos;

        requestAddress(centerPos, destinationText2);
        requestRoute2(centerPos);
        setDestinationContainer2.setVisibility(View.GONE);
        if (destinationText.getText().toString().isEmpty()) {
            setDestinationContainer.setVisibility(View.VISIBLE);
        } else {
            setDestinationContainer.setVisibility(View.GONE);
        }

    }

    private void onDestination3() {
        if (destinationMarker3 != null) destinationMarker3.remove();
        if (destinationMarker4 != null) destinationMarker4.remove();
        if (destinationMarker5 != null) destinationMarker5.remove();

        destinationText4.setText("");
        destinationText5.setText("");

        destinationLatLang4 = null;
        destinationLatLang5 = null;


        setDestinationContainer.setVisibility(View.GONE);
        setDestinationContainer2.setVisibility(View.GONE);
        setDestinationContainer3.setVisibility(View.GONE);
        setDestinationContainer4.setVisibility(View.VISIBLE);
        setDestinationContainer5.setVisibility(View.GONE);

        LatLng centerPos = gMap.getCameraPosition().target;
        destinationMarker3 = gMap.addMarker(new MarkerOptions()
                .position(centerPos)
                .title("Destination3")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.destination)));
        destinationLatLang3 = centerPos;

        requestAddress(centerPos, destinationText3);
        requestRoute3(centerPos);

        setDestinationContainer3.setVisibility(View.GONE);
        if (destinationText2.getText().toString().isEmpty()) {
            setDestinationContainer2.setVisibility(View.VISIBLE);
        } else {
            setDestinationContainer2.setVisibility(View.GONE);
        }

    }

    private void onDestination4() {
        if (destinationMarker4 != null) destinationMarker4.remove();
        if (destinationMarker5 != null) destinationMarker5.remove();

        destinationText5.setText("");
        destinationLatLang5 = null;

        setDestinationContainer.setVisibility(View.GONE);
        setDestinationContainer2.setVisibility(View.GONE);
        setDestinationContainer3.setVisibility(View.GONE);
        setDestinationContainer4.setVisibility(View.GONE);
        setDestinationContainer5.setVisibility(View.VISIBLE);

        LatLng centerPos = gMap.getCameraPosition().target;
        destinationMarker4 = gMap.addMarker(new MarkerOptions()
                .position(centerPos)
                .title("Destination4")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.destination)));
        destinationLatLang4 = centerPos;

        requestAddress(centerPos, destinationText4);
        requestRoute4(centerPos);

        setDestinationContainer4.setVisibility(View.GONE);
        if (destinationText3.getText().toString().isEmpty()) {
            setDestinationContainer3.setVisibility(View.VISIBLE);
        } else {
            setDestinationContainer3.setVisibility(View.GONE);
        }

    }

    private void onDestination5() {
        if (destinationMarker5 != null) destinationMarker5.remove();
        LatLng centerPos = gMap.getCameraPosition().target;
        destinationMarker5 = gMap.addMarker(new MarkerOptions()
                .position(centerPos)
                .title("Destination5")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.destination)));
        destinationLatLang5 = centerPos;

        requestAddress(centerPos, destinationText5);
        requestRoute5(centerPos);

        setDestinationContainer5.setVisibility(View.GONE);
        if (destinationText4.getText().toString().isEmpty()) {
            setDestinationContainer4.setVisibility(View.VISIBLE);
        } else {
            setDestinationContainer4.setVisibility(View.GONE);
        }

    }

    private void onDestination() {
        setDestinationContainer2.setVisibility(View.VISIBLE);
        if (destinationMarker != null) destinationMarker.remove();
        if (destinationMarker2 != null) destinationMarker2.remove();
        if (destinationMarker3 != null) destinationMarker3.remove();
        if (destinationMarker4 != null) destinationMarker4.remove();
        if (destinationMarker5 != null) destinationMarker5.remove();

        destinationText2.setText("");
        destinationText3.setText("");
        destinationText4.setText("");
        destinationText5.setText("");

        destinationLatLang2 = null;
        destinationLatLang3 = null;
        destinationLatLang4 = null;
        destinationLatLang5 = null;

        LatLng centerPos = gMap.getCameraPosition().target;
        destinationMarker = gMap.addMarker(new MarkerOptions()
                .position(centerPos)
                .title("Destination")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.destination)));
        destinationLatLang = centerPos;

        requestAddress(centerPos, destinationText);
        requestRoute();

        setDestinationContainer.setVisibility(View.GONE);
        setDestinationContainer2.setVisibility(View.VISIBLE);
        setDestinationContainer3.setVisibility(View.GONE);
        setDestinationContainer4.setVisibility(View.GONE);
        setDestinationContainer5.setVisibility(View.GONE);

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
        fetchNearDriver(pickUpLatLang.latitude, pickUpLatLang.longitude);
        requestRoute();
    }

    private void requestRoute() {
        if (pickUpLatLang != null && destinationLatLang != null) {
            rlprogress.setVisibility(View.VISIBLE);
            textprogress.setText(getString(R.string.waiting_pleaseWait));
            MapDirectionAPI.getDirection(pickUpLatLang, destinationLatLang).enqueue(updateRouteCallback);
        }
    }

    private void requestRoute2(LatLng centerpos) {
        if (destinationLatLang != null && destinationLatLang2 != null) {
            rlprogress.setVisibility(View.VISIBLE);
            textprogress.setText(getString(R.string.waiting_pleaseWait));
//            MapDirectionAPI.getDirection(destinationLatLang, destinationLatLang2).enqueue(updateRouteCallback);
            MapDirectionAPI.getDirectionVia(pickUpLatLang, destinationLatLang, centerpos).enqueue(updateRouteCallback);
        }
    }

    private void requestRoute3(LatLng centerpos) {
        if (destinationLatLang2 != null && destinationLatLang3 != null) {
            rlprogress.setVisibility(View.VISIBLE);
            textprogress.setText(getString(R.string.waiting_pleaseWait));
            MapDirectionAPI.getDirectionVia(pickUpLatLang, destinationLatLang, destinationLatLang2, centerpos).enqueue(updateRouteCallback);
        }
    }


    private void requestRoute4(LatLng centerpos) {
        if (destinationLatLang3 != null && destinationLatLang4 != null) {
            rlprogress.setVisibility(View.VISIBLE);
            textprogress.setText(getString(R.string.waiting_pleaseWait));
            MapDirectionAPI.getDirectionVia(pickUpLatLang, destinationLatLang, destinationLatLang2, destinationLatLang3, centerpos).enqueue(updateRouteCallback);
        }
    }



    private void requestRoute5(LatLng centerpos) {
        if (destinationLatLang4 != null && destinationLatLang5 != null) {
            rlprogress.setVisibility(View.VISIBLE);
            textprogress.setText(getString(R.string.waiting_pleaseWait));
            MapDirectionAPI.getDirectionVia(pickUpLatLang, destinationLatLang, destinationLatLang2, destinationLatLang3, destinationLatLang4, centerpos).enqueue(updateRouteCallback);
        }
    }


    private void updateLineDestination(String json) {
        Directions directions = new Directions(SendActivity.this);
        try {
            List<Route> routes = directions.parse(json);

            if (directionLine != null) directionLine.remove();
            if (routes.size() > 0) {
                directionLine = gMap.addPolyline((new PolylineOptions())
                        .addAll(routes.get(0).getOverviewPolyLine())
                        .color(ContextCompat.getColor(SendActivity.this, R.color.colorgradient))
                        .width(8));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateDistance(long distance) {
        orderButton.setEnabled(true);
        orderButton.setBackground(getResources().getDrawable(R.drawable.button_round_1));
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomsheet);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        setDestinationContainer.setVisibility(View.GONE);
        setPickUpContainer.setVisibility(View.GONE);
        orderButton.setVisibility(View.VISIBLE);

        this.jarak = ((float) (distance)) / 1000f;

        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (driverAvailable.isEmpty()) {
//                    notif("Sorry, there are no drivers around you.");
                    noDriver();
                } else {
                    onNextButtonClick();
                }
            }
        });
    }

    private void noDriver() {
        Double biaya = Double.parseDouble(getbiaya);
        Double biayaTotal = biaya * jarak;
        String formattedTotal = NumberFormat.getNumberInstance(Locale.US).format(biayaTotal);
        final String formattedTextPrice = String.format(Locale.US, "IDR %s.00", formattedTotal);
        AlertDialog dialog = new AlertDialog.Builder(SendActivity.this)
                .setTitle("Maaf Tidak ada Driver kami yang online")
                .setMessage("Silahkan Booking manual(whatsapp) melalu staf kami sehingga bisa di proses secepat mungkin.")
                .setPositiveButton("Book Manual", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String message = "M-ojek"
                                + "\n" +
                                "Asal : " + pickUpText.getText().toString()
                                + "\n " +
                                "Tujuan " + " :" +
                                destinationText.getText().toString() + " , Tujuan 2: " + destinationText2.getText().toString() + " , Tujuan 3 : " + destinationText3.getText().toString() + " , Tujuan 4: " + destinationText4.getText().toString() + " ,Tujuan 5: " + destinationText5.getText().toString()
                                + "\n " +
                                "TOTAL HARGA " + " :" +
                                formattedTextPrice;

                        Uri uri = Uri.parse("https://api.whatsapp.com/send?phone=+6285376761974&text=Saya%20ingin%20%20pesan%20" + message);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancel", null).create();
        dialog.show();
    }


    private void onNextButtonClick() {
        Intent intent = new Intent(this, SendDetailActivity.class);
        intent.putExtra("distance", jarak);//double
        intent.putExtra("price", getbiaya);//long
        intent.putExtra("pickup_latlng", pickUpLatLang);
        intent.putExtra("destination_latlng", destinationLatLang);
        intent.putExtra("destination_latlng2", destinationLatLang2);
        intent.putExtra("destination_latlng3", destinationLatLang3);
        intent.putExtra("destination_latlng4", destinationLatLang4);
        intent.putExtra("destination_latlng5", destinationLatLang5);
        intent.putExtra("pickup", pickUpText.getText().toString());
        intent.putExtra("destination", destinationText.getText().toString());
        intent.putExtra("destination2", destinationText2.getText().toString());
        intent.putExtra("destination3", destinationText3.getText().toString());
        intent.putExtra("destination4", destinationText4.getText().toString());
        intent.putExtra("destination5", destinationText5.getText().toString());
        intent.putExtra("driver", driverAvailable);
        intent.putExtra("biaya_minimum", biayaminimum);
        intent.putExtra("time_distance", timeDistance);
        intent.putExtra("driver", driverAvailable);
        intent.putExtra("icon", ICONFITUR);
        intent.putExtra("layanan", layanan.getText().toString());
        intent.putExtra("layanandesk", layanandesk.getText().toString());
        intent.putExtra(FITUR_KEY, fiturId);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    private void fetchNearDriver(double latitude, double longitude) {
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
                public void onFailure(@NonNull Call<GetNearRideCarResponseJson> call, @NonNull Throwable t) {

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


    @Override
    protected void onStart() {
        googleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


}