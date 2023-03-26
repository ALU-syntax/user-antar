package com.antar.passenger.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.antar.passenger.R;
import com.antar.passenger.adapter.PlaceAutocompleteAdapter;
import com.antar.passenger.constants.BaseApp;
import com.antar.passenger.json.GetNearRideCarRequestJson;
import com.antar.passenger.json.GetNearRideCarResponseJson;
import com.antar.passenger.models.AutoCompleteAdapter;
import com.antar.passenger.models.DriverModel;
import com.antar.passenger.models.User;
import com.antar.passenger.utils.LocaleHelper;
import com.antar.passenger.utils.api.ServiceGenerator;
import com.antar.passenger.utils.api.service.BookService;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FetchPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.antar.passenger.constants.Constants.bounds;


/**
 * Created by Androgo on 12/4/2018.
 */

public class LocationPickerActivity extends AppCompatActivity
        implements OnMapReadyCallback {

    public static final int LOCATION_PICKER_ID = 78;
    public static final String FORM_VIEW_INDICATOR = "FormToFill";
    public static final String LOCATION_NAME = "LocationName";
    public static final String LOCATION_LATLNG = "LocationLatLng";
    private static final int REQUEST_PERMISSION_LOCATION = 991;
    @BindView(R.id.locationPicker_autoCompleteText)
    AutoCompleteTextView autoCompleteTextView;
    @BindView(R.id.locationPicker_currentAddress)
    TextView currentAddress;
    @BindView(R.id.locationPicker_destinationButton)
    Button selectLocation;


    private GoogleMap gMap;
    // private GoogleApiClient googleApiClient;

    private Location lastKnownLocation;

    private List<DriverModel> driverAvailable;
    private List<Marker> driverMarkers;

    //new
    @BindView(R.id.loading)
    ProgressBar loading;
    @BindView(R.id.relative_load)
    RelativeLayout relative_load;

    private PlaceAutocompleteAdapter mAdapter;
    //New SDK
    private FusedLocationProviderClient fusedLocationClient;
    AutoCompleteAdapter mAdapterPickup;
    PlacesClient placesClient;

//    private static final LatLngbounds bounds = new LatLngbounds(
//            new LatLng(-34.041458, 150.790100), new LatLng(-33.682247, 151.383362));

    private int formToFill;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase));
    }

    private void fetchNearDriver() {
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        BookService service = ServiceGenerator.createService(BookService.class, loginUser.getEmail(), loginUser.getPassword());
        GetNearRideCarRequestJson param = new GetNearRideCarRequestJson();
        param.setLatitude(lastKnownLocation.getLatitude());
        param.setLongitude(lastKnownLocation.getLongitude());
        param.setFitur("6");

        service.getNearRide(param).enqueue(new Callback<GetNearRideCarResponseJson>() {
            @Override
            public void onResponse(Call<GetNearRideCarResponseJson> call, Response<GetNearRideCarResponseJson> response) {
                if (response.isSuccessful()) {
                    driverAvailable = response.body().getData();
                    createMarker();
                }
            }

            @Override
            public void onFailure(Call<GetNearRideCarResponseJson> call, Throwable t) {

            }
        });
    }

    private void createMarker() {
        if (!driverAvailable.isEmpty()) {
            for (Marker m : driverMarkers) {
                m.remove();
            }
            driverMarkers.clear();

            for (DriverModel driver : driverAvailable) {
                LatLng currentDriverPos = new LatLng(driver.getLatitude(), driver.getLongitude());
                driverMarkers.add(
                        gMap.addMarker(new MarkerOptions()
                                .position(currentDriverPos)
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.bicycle)))
                );

            }
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        //    googleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //  googleApiClient.disconnect();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_picker);
        ButterKnife.bind(this);

        driverAvailable = new ArrayList<>();
        driverMarkers = new ArrayList<>();

        //     setupGoogleApiClient();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.locationPicker_maps);
        mapFragment.getMapAsync(this);

        //   setupAutocompleteTextView();

        Intent intent = getIntent();
        formToFill = intent.getIntExtra(FORM_VIEW_INDICATOR, -1);

        selectLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectLocation();
            }
        });

        String apiKey = getString(R.string.google_maps_key);
        if(apiKey.isEmpty()){
            Toast.makeText(getApplicationContext(), "Error contacting API ",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), apiKey);
        }

        placesClient = Places.createClient(this);
        initAutoCompleteTextViewPickup();
        setupMap();

    }

    private void initAutoCompleteTextViewPickup() {
        autoCompleteTextView.setThreshold(1);
        mAdapterPickup = new AutoCompleteAdapter(this, placesClient, bounds);
        autoCompleteTextView.setAdapter(mAdapterPickup);
    }

    private void setupMap(){
        autoCompleteTextView.setAdapter(mAdapterPickup);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    final AutocompletePrediction item = mAdapterPickup.getItem(position);
                    String placeID = null;
                    if (item != null) {
                        placeID = item.getPlaceId();
                    }
                    List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS
                            , Place.Field.LAT_LNG);

                    FetchPlaceRequest request = null;
                    if (placeID != null) {
                        request = FetchPlaceRequest.builder(placeID, placeFields)
                                .build();
                    }

                    if (request != null) {
                        placesClient.fetchPlace(request).addOnSuccessListener(new OnSuccessListener<FetchPlaceResponse>() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onSuccess(FetchPlaceResponse result) {

                                String text_location = (result.getPlace().getName() + "\n" + result.getPlace().getAddress());
                                currentAddress.setText(Html.fromHtml("<b>" + text_location + "</b>"));

                                LatLng latLng = result.getPlace().getLatLng();
                                if (latLng != null) {
                                    gMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                e.printStackTrace();
                                currentAddress.setText(e.getMessage());
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

//        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
//                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
//
//        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
//            @Override
//            public void onPlaceSelected(Place place) {
//                // TODO: Get info about the selected place.
//                Log.i("TAG", "Place: " + place.getName());
//                CameraPosition cameraPosition = new CameraPosition.Builder().
//                        target(place.getLatLng()).
//                        tilt(45).
//                        zoom(15).
//                        bearing(0).
//                        build();
//                gMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
//            }
//
//            @Override
//            public void onError(Status status) {
//                // TODO: Handle the error.
//                Log.i("TAG", "An error occurred: " + status);
//            }
//        });


    private void selectLocation() {
        LatLng selectedLocation = gMap.getCameraPosition().target;
        String selectedAddress = currentAddress.getText().toString();

        Intent intent = new Intent();
        intent.putExtra(FORM_VIEW_INDICATOR, formToFill);
        intent.putExtra(LOCATION_NAME, selectedAddress);
        intent.putExtra(LOCATION_LATLNG, selectedLocation);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
/*
    private void setupGoogleApiClient() {
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                 //   .addApi(Places.GEO_DATA_API)
                    .build();
        }
    }
*/
   /* private void setupAutocompleteTextView() {
        mAdapter = new PlaceAutocompleteAdapter(this, googleApiClient, bounds, null);
        autoCompleteTextView.setAdapter(mAdapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                InputMethodManager inputManager =
                        (InputMethodManager) LocationPickerActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(autoCompleteTextView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                AutocompletePrediction item = mAdapter.getItem(position);
                getLocationFromPlaceId(item.getPlaceId(), new ResultCallback<PlaceBuffer>() {
                    @Override
                    public void onResult(@NonNull PlaceBuffer places) {
                        if (places.getStatus().isSuccess()) {
                            gMap.moveCamera(CameraUpdateFactory.newLatLng(places.get(0).getLatLng()));
                        }
                    }
                });

            }
        });
    }

    private void getLocationFromPlaceId(String placeId, ResultCallback<PlaceBuffer> callback) {
        Places.GeoDataApi.getPlaceById(googleApiClient, placeId).setResultCallback(callback);
    }
*/

    private void updateLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSION_LOCATION);
            return;
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(location.getLatitude(), location.getLongitude()), 15f)
                            );
                            gMap.animateCamera(CameraUpdateFactory.zoomTo(15f));
                            User loginUser = BaseApp.getInstance(LocationPickerActivity.this).getLoginUser();
                            BookService service = ServiceGenerator.createService(BookService.class, loginUser.getEmail(), loginUser.getPassword());
                            GetNearRideCarRequestJson param = new GetNearRideCarRequestJson();
                            param.setLatitude(location.getLatitude());
                            param.setLongitude(location.getLongitude());

                            service.getNearRide(param).enqueue(new Callback<GetNearRideCarResponseJson>() {
                                @Override
                                public void onResponse(Call<GetNearRideCarResponseJson> call, Response<GetNearRideCarResponseJson> response) {
                                    if (response.isSuccessful()) {
                                        driverAvailable = response.body().getData();
                                        createMarker();
                                    }
                                }
                                @Override
                                public void onFailure(Call<GetNearRideCarResponseJson> call, Throwable t) {

                                }
                            });
                        }
                    }

                });
        gMap.setMyLocationEnabled(true);
    }
/*
    private void updateLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSION_LOCATION);
            return;
        }

        lastKnownLocation = LocationServices.FusedLocationApi.getLastLocation(
                googleApiClient);
        gMap.setMyLocationEnabled(true);

        if (lastKnownLocation != null) {
            gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude()), 15f)
            );

            gMap.animateCamera(CameraUpdateFactory.zoomTo(15f));
            fetchNearDriver();
            show();
        }
    }
    */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        gMap.getUiSettings().setMyLocationButtonEnabled(true);
        updateLastLocation();
        setupMapOnCameraChange();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_PERMISSION_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                updateLastLocation();
            } else {
                // TODO: 10/15/2018 Tell user to use GPS
            }
        }
    }

    private void setupMapOnCameraChange() {
        gMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                LatLng center = gMap.getCameraPosition().target;
                fillAddress(currentAddress, center);
                show();
            }
        });
    }

    private void fillAddress(final TextView textView, final LatLng latLng) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Geocoder geocoder = new Geocoder(LocationPickerActivity.this, Locale.getDefault());
                    final List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                    LocationPickerActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (!addresses.isEmpty()) {
                                if (addresses.size() > 0) {
                                    String address = addresses.get(0).getAddressLine(0);
                                    textView.setText(address);
                                    hidden();
                                }
                            } else {
                                textView.setText(R.string.text_addressNotAvailable);
                                show();
                            }
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
/*
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        updateLastLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    */

    private LatLng getLocationFromAddress(String strAddress) {

        Geocoder coder = new Geocoder(this);
        List<Address> address;
        LatLng p1;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude());
            return p1;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    private void show (){
        loading.setVisibility(View.VISIBLE);
        relative_load.setVisibility(View.GONE);
        selectLocation.setVisibility(View.GONE);
    }

    private void hidden (){
        loading.setVisibility(View.GONE);
        relative_load.setVisibility(View.VISIBLE);
        selectLocation.setVisibility(View.VISIBLE);
    }

}
