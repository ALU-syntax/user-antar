package com.insoftbumdesku.passenger.activity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.insoftbumdesku.passenger.R;
import com.insoftbumdesku.passenger.constants.BaseApp;
import com.insoftbumdesku.passenger.item.ItemItem;
import com.insoftbumdesku.passenger.item.MartItem;
import com.insoftbumdesku.passenger.json.GetNearRideCarRequestJson;
import com.insoftbumdesku.passenger.json.GetNearRideCarResponseJson;
import com.insoftbumdesku.passenger.mine.MartInterface;
import com.insoftbumdesku.passenger.models.DriverModel;
import com.insoftbumdesku.passenger.models.ItemPesanan;
import com.insoftbumdesku.passenger.models.PesananMerchant;
import com.insoftbumdesku.passenger.models.User;
import com.insoftbumdesku.passenger.utils.Log;
import com.insoftbumdesku.passenger.utils.api.MapDirectionAPI;
import com.insoftbumdesku.passenger.utils.api.ServiceGenerator;
import com.insoftbumdesku.passenger.utils.api.service.BookService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mikepenz.fastadapter.adapters.FastItemAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NonMerchantActivity extends AppCompatActivity implements MartInterface, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, OnMapReadyCallback, ItemItem.OnCalculatePrice {
    private TextView productAdd, productRemove, qty_text, cost_text;
    private TextView martGetLocationText, mart_martDestinationText;
    private List<MartItem> martItemList;
    private FastItemAdapter<MartItem> martAdapter;
    private RecyclerView productListRecycler;
    private CardView mart_martLocation, mart_martDestination;
    private CardView priceContainer;
    private int hitung_total_item;
    private int hitung_total_harga;
    private EditText mart_martName;
    private ImageView back_btn;

    int[] exiF;

    private LatLng martLatLng;
    private LatLng destinationLatLng;
    private Marker martMarker;
    private GoogleMap gMap;
    private Location lastKnownLocation;
    private GoogleApiClient googleApiClient;

    private List<DriverModel> driverAvailable;
    private List<Marker> driverMarkers;

    public static final int MART_LOCATION = 1;
    public static final int DESTINATION_LOCATION = 2;
    private static final int REQUEST_PERMISSION_LOCATION = 991;

    private double distance;
    private Realm realm;

    private FastItemAdapter<ItemItem> itemAdapter;

    private okhttp3.Callback updateRouteCallback = new okhttp3.Callback() {
        @Override
        public void onFailure(okhttp3.Call call, IOException e) {

        }

        @Override
        public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
            if (response.isSuccessful()) {
                final String json = response.body().string();
//                final long distance = MapDirectionAPI.getDistance(NonMerchantActivity.this, json);
                distance = MapDirectionAPI.getDistance(NonMerchantActivity.this, json);
                final String time = MapDirectionAPI.getTimeDistance(NonMerchantActivity.this, json);
                if (distance >= 0) {
                    NonMerchantActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            updateLineDestination(json);
//                            updateDistance(distance);
//                            timeDistance = Long.parseLong(time) / 60;

                        }
                    });
                }
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_merchant);

        productAdd = findViewById(R.id.mart_plusList);
        productRemove = findViewById(R.id.mart_minusList);
        productListRecycler = findViewById(R.id.mart_menuListRecycler);
        priceContainer = findViewById(R.id.price_container);
        mart_martLocation = findViewById(R.id.mart_martLocation);
        mart_martDestination = findViewById(R.id.mart_martDestination);
        mart_martDestinationText = findViewById(R.id.mart_martDestinationText);
        martGetLocationText = findViewById(R.id.mart_martLocationText);
        mart_martName = findViewById(R.id.mart_martName);
        back_btn = findViewById(R.id.back_btn);
        qty_text = findViewById(R.id.qty_text);
        cost_text = findViewById(R.id.cost_text);
        martItemList = new ArrayList<>();
        driverAvailable = new ArrayList<>();
        driverMarkers = new ArrayList<>();

        itemAdapter = new FastItemAdapter<>();

        initializeRecyclerView();
        hitungTotal();
        hitungHarga();

        productAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem();
            }
        });

        priceContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LoadItem();
                String alamat_user = mart_martDestinationText.getText().toString();
                String alamat_resto = martGetLocationText.getText().toString();
                String nama_resto = mart_martName.getText().toString();

                if(alamat_user.isEmpty()){
                    mart_martDestinationText.setError("Alamat Pembeli Tidak Boleh Kosong...");
                }else if(alamat_resto.isEmpty()){
                    martGetLocationText.setError("Alamat Toko Tidak Boleh Kosong..");
                }else if(nama_resto.isEmpty()){
                    mart_martName.setError("Nama Toko Tidak Boleh Kosong...");
                }else {
                    Intent i = new Intent(NonMerchantActivity.this, DetailOrderActivity.class);
                    i.putExtra("lat", destinationLatLng.latitude);
                    i.putExtra("lon", destinationLatLng.longitude);
                    i.putExtra("merlat", martLatLng.latitude);
                    i.putExtra("merlon", martLatLng.longitude);
                    i.putExtra("alamat", alamat_user );
                    i.putExtra("FiturKey", 6);
                    i.putExtra("distance", distance);
                    i.putExtra("alamatresto", alamat_resto);
                    i.putExtra("idresto", "9999");
                    i.putExtra("namamerchant",  nama_resto);
                    i.putExtra("non_merchant", 1);
                    i.putExtra("total_harga", hitung_total_harga);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);

                }


            }
        });

        mart_martLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getMartLocation();
            }
        });

        mart_martDestination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDestinationLocation();
            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void LoadItem() {
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.delete(ItemPesanan.class);

        int nomor = 0;

        for(int i=0; i<martItemList.size(); i++){
            ItemPesanan pesanan = new ItemPesanan();
            nomor++;
            pesanan.setId_item(nomor);
            pesanan.setNama_item(martItemList.get(i).getNamaProduk());
            pesanan.setHarga_item(martItemList.get(i).getItemPrice());
            pesanan.setQuantity(martItemList.get(i).getQuantity());
            pesanan.setTotal_harga(martItemList.get(i).getPriceTotal());
            pesanan.setCatatan(martItemList.get(i).getCatatan());
            realm.insertOrUpdate(pesanan);
            Log.d("data aku", pesanan.getId_item()+" - "+pesanan.getNama_item()+" - "+pesanan.getHarga_item()+" - "+pesanan.getTotal_harga());
        }


        realm.delete(PesananMerchant.class);


        int no = 0;

        for(int i=0; i<martItemList.size(); i++){
            PesananMerchant merchant = new PesananMerchant();
            no++;
            merchant.setIdItem(no);
            merchant.setNamaItem(martItemList.get(i).getNamaProduk());
            merchant.setQty(martItemList.get(i).getQuantity());
            merchant.setTotalHarga(martItemList.get(i).getPriceTotal());
            merchant.setCatatan(martItemList.get(i).getCatatan());
            realm.insertOrUpdate(merchant);

        }

        realm.commitTransaction();




    }

    private void getMartLocation() {
        Intent intent = new Intent(NonMerchantActivity.this, PicklocationActivity.class);
        intent.putExtra(PicklocationActivity.FORM_VIEW_INDICATOR, MART_LOCATION);
        startActivityForResult(intent, PicklocationActivity.LOCATION_PICKER_ID);
    }

    private void getDestinationLocation() {
        Intent intent = new Intent(NonMerchantActivity.this, PicklocationActivity.class);
        intent.putExtra(PicklocationActivity.FORM_VIEW_INDICATOR, DESTINATION_LOCATION);
        startActivityForResult(intent, PicklocationActivity.LOCATION_PICKER_ID);
    }

    private void addItem() {
        if (martItemList.size() + 1 <= 100) martItemList.add(new MartItem(this));
        martAdapter.setNewList(martItemList);
        martAdapter.notifyDataSetChanged();
        hitungTotal();
        hitungHarga();

    }

    private void removeItem() {
        if (martItemList.size() - 1 > 0) martItemList.remove(martItemList.size() - 1);
        martAdapter.setNewList(martItemList);
        martAdapter.notifyDataSetChanged();
        hitungTotal();
        hitungHarga();
    }


    private void initializeRecyclerView() {
        if (martItemList.isEmpty()) martItemList.add(new MartItem(this));

        martAdapter = new FastItemAdapter<>();

        productListRecycler.setLayoutManager(new LinearLayoutManager(this));
        productListRecycler.setAdapter(martAdapter);
        productListRecycler.setNestedScrollingEnabled(false);

        martAdapter.setNewList(martItemList);
        productAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem();

            }
        });

        productRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeItem();
            }
        });


    }


    @Override
    public void hitungTotal() {
        int total_item =0;
        for(int i=0; i<martItemList.size(); i++) {
            total_item = total_item + martItemList.get(i).getQuantity();
            hitung_total_item = total_item;
            qty_text.setText(String.valueOf(hitung_total_item + " Item"));
        }
    }


    @Override
    public void hitungHarga() {
        int total_harga = 0;
        for(int i=0; i<martItemList.size(); i++) {
            total_harga = total_harga + martItemList.get(i).getPriceTotal();
            hitung_total_harga = total_harga;
            cost_text.setText(String.valueOf("Rp. "+hitung_total_harga));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LocationPickerActivity.LOCATION_PICKER_ID) {
            if (resultCode == Activity.RESULT_OK) {
                int fillData = data.getIntExtra(PicklocationActivity.FORM_VIEW_INDICATOR, -1);
                String address = data.getStringExtra(PicklocationActivity.LOCATION_NAME);
                LatLng latLng = data.getParcelableExtra(PicklocationActivity.LOCATION_LATLNG);
                switch (fillData) {
                    case MART_LOCATION:
                        martGetLocationText.setText(address);
                        martLatLng = latLng;

//                        if (martMarker != null) martMarker.remove();
//                        martMarker = gMap.addMarker(new MarkerOptions()
//                                .position(martLatLng)
//                                .title("Pick Up")
//                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_loka)));
//                        fetchNearDriver(martLatLng.latitude, martLatLng.longitude);

                        break;
                    case DESTINATION_LOCATION:
                        mart_martDestinationText.setText(address);
                        destinationLatLng = latLng;
//
//                        if (destinationMarker != null) destinationMarker.remove();
//                        destinationMarker = gMap.addMarker(new MarkerOptions()
//                                .position(destinationLatLng)
//                                .title("Destination")
//                                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_loc_blue)));

                        break;
                }

                requestRoute();
            }
        }
    }

    private void requestRoute() {
        if (martLatLng != null && destinationLatLng != null) {
            MapDirectionAPI.getDirection(martLatLng, destinationLatLng).enqueue(updateRouteCallback);
        }
    }

    private void fetchNearDriver(double latitude, double longitude) {
        if (lastKnownLocation != null) {
            User loginUser = BaseApp.getInstance(this).getLoginUser();
            BookService service = ServiceGenerator.createService(BookService.class, loginUser.getEmail(), loginUser.getPassword());
            GetNearRideCarRequestJson param = new GetNearRideCarRequestJson();
            param.setLatitude(latitude);
            param.setLongitude(longitude);

            service.getNearRide(param).enqueue(new Callback<GetNearRideCarResponseJson>() {
                @Override
                public void onResponse(Call<GetNearRideCarResponseJson> call, Response<GetNearRideCarResponseJson> response) {
                    if (response.isSuccessful()) {
                        driverAvailable = response.body().getData();
                        createMarker();
                    }
                }

                @Override
                public void onFailure(retrofit2.Call<GetNearRideCarResponseJson> call, Throwable t) {

                }
            });
        }
    }

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
        }
    }

    private void fetchNearDriver() {
        User loginUser = BaseApp.getInstance(this).getLoginUser();

        BookService service = ServiceGenerator.createService(BookService.class, loginUser.getEmail(), loginUser.getPassword());
        GetNearRideCarRequestJson param = new GetNearRideCarRequestJson();
        param.setLatitude(lastKnownLocation.getLatitude());
        param.setLongitude(lastKnownLocation.getLongitude());

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
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_loka)))
                );
            }
        }
        else
            notOnline();
    }
    private void notOnline(){
        new AlertDialog.Builder(NonMerchantActivity.this)
                .setTitle("Driver Tidak Tersedia")
                .setMessage("Maaf, Tidak ada driver yang insoftbumdesku saat ini, Anda Bisa Menghubungi Kami Via Whatsapp")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String message = "BUMDES-KU";
                        Uri uri = Uri.parse("https://api.whatsapp.com/send?phone=+6285220020027&text=I%20Want%20to%20order%20"+message);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);

                    }
                })
                .setNegativeButton("Cancel", null).create().show();

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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        gMap.getUiSettings().setMyLocationButtonEnabled(true);
        updateLastLocation();
    }

    @Override
    public void calculatePrice() {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}    // end class code
