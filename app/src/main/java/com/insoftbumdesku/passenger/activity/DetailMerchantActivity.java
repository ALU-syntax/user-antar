package com.insoftbumdesku.passenger.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.insoftbumdesku.passenger.R;
import com.insoftbumdesku.passenger.constants.BaseApp;
import com.insoftbumdesku.passenger.constants.Constants;
import com.insoftbumdesku.passenger.item.CatItemItem;
import com.insoftbumdesku.passenger.item.ItemItem;
import com.insoftbumdesku.passenger.json.GetAllMerchantbyCatRequestJson;
import com.insoftbumdesku.passenger.json.MerchantByIdResponseJson;
import com.insoftbumdesku.passenger.json.MerchantbyIdRequestJson;
import com.insoftbumdesku.passenger.models.CatItemModel;
import com.insoftbumdesku.passenger.models.ItemModel;
import com.insoftbumdesku.passenger.models.PesananMerchant;
import com.insoftbumdesku.passenger.models.User;
import com.insoftbumdesku.passenger.utils.PicassoTrustAll;
import com.insoftbumdesku.passenger.utils.Utility;
import com.insoftbumdesku.passenger.utils.api.MapDirectionAPI;
import com.insoftbumdesku.passenger.utils.api.ServiceGenerator;
import com.insoftbumdesku.passenger.utils.api.service.UserService;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.adapters.FastItemAdapter;
import com.mikepenz.fastadapter.listeners.ClickEventHook;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailMerchantActivity extends AppCompatActivity implements ItemItem.OnCalculatePrice {
    ImageView fotomerchant, partner, promo, backbtn, partnershim, promoshim;
    TextView tutup, nama, kategori, hours, qtytext, costtext;
    FloatingActionButton maps;
    String id, resume, alamat, tutupki, lat, lon, idresto, alamatresto;
    double merlat, merlon, distance;
    int idfitur;
    LatLng lokasi;

    FrameLayout rlnotif;
    TextView textnotif;

    CardView pricecountainer;

    ShimmerFrameLayout shimmerchantnear, shimmerbadge, shimmerdetail, shimmerbadgepromo;
    RecyclerView rvcatmerchantnear, rvmerchantnear;
    CatItemItem catMerchantNearItem;
    LinearLayout llmerchantnear, shimlistnear, shimlistcatnear, lldetail;
    RelativeLayout nodatanear;
    int[] exiF;
    private BottomSheetDialog mBottomSheetDialog;
    private View bottom_sheet;
    private FastItemAdapter<ItemItem> itemAdapter;
    private List<ItemModel> itemRealmResults;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_merchant);
        realm = Realm.getDefaultInstance();

        bottom_sheet = findViewById(R.id.bottom_sheet);
        BottomSheetBehavior.from(bottom_sheet);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        lat = intent.getStringExtra("lat");
        lon = intent.getStringExtra("lon");

        fotomerchant = findViewById(R.id.image);
        partner = findViewById(R.id.partner);
        promo = findViewById(R.id.promo);
        backbtn = findViewById(R.id.back_btn);
        tutup = findViewById(R.id.tutup);
        textnotif = findViewById(R.id.textnotif);
        rlnotif = findViewById(R.id.rlnotif);
        nama = findViewById(R.id.nama);
        kategori = findViewById(R.id.kategoridistance);
        hours = findViewById(R.id.hours);
        pricecountainer = findViewById(R.id.price_container);
        qtytext = findViewById(R.id.qty_text);
        costtext = findViewById(R.id.cost_text);
        maps = findViewById(R.id.fab);

        shimmerbadge = findViewById(R.id.shimmerbadge);
        promoshim = findViewById(R.id.promoshim);
        partnershim = findViewById(R.id.partnershim);
        shimmerdetail = findViewById(R.id.shimmerdetail);
        lldetail = findViewById(R.id.lldetail);

        shimmerchantnear = findViewById(R.id.shimmerchantnear);
        rvcatmerchantnear = findViewById(R.id.catmerchantnear);
        rvmerchantnear = findViewById(R.id.merchantnear);
        llmerchantnear = findViewById(R.id.llmerchantnear);
        shimlistnear = findViewById(R.id.shimlistnear);
        shimlistcatnear = findViewById(R.id.shimlistcatnear);
        nodatanear = findViewById(R.id.rlnodata);
        shimmerbadgepromo = findViewById(R.id.shimmerbadgepromo);

        resume = "0";
        rvcatmerchantnear.setHasFixedSize(true);
        rvcatmerchantnear.setNestedScrollingEnabled(false);
        rvcatmerchantnear.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        FusedLocationProviderClient mFusedLocation = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocation.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    LatLng centerPos = new LatLng(location.getLatitude(), location.getLongitude());
                    requestAddress(centerPos);
                    lokasi = centerPos;
                    getdata(location);
                }
            }
        });


        itemAdapter = new FastItemAdapter<>();
        rvmerchantnear.setLayoutManager(new LinearLayoutManager(this));
        DeletePesanan();
        shimmershow();


    }

    private void requestAddress(LatLng latlang) {
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
                                    alamat = userdata.getString("formatted_address");
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

    public void notif(String text) {
        rlnotif.setVisibility(View.VISIBLE);
        textnotif.setText(text);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                rlnotif.setVisibility(View.GONE);
            }
        }, 3000);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void calculatePrice() {
        List<PesananMerchant> existingFood = realm.where(PesananMerchant.class).findAll();

        int quantity = 0;
        long cost = 0;
        for (int p = 0; p < existingFood.size(); p++) {
            quantity += Objects.requireNonNull(existingFood.get(p)).getQty();
            cost += Objects.requireNonNull(existingFood.get(p)).getTotalHarga();
        }

        if (quantity > 0) {
            pricecountainer.setVisibility(View.VISIBLE);
            pricecountainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (tutupki.equals("0")) {
                        Intent i = new Intent(DetailMerchantActivity.this, DetailOrderActivity.class);
                        i.putExtra("lat", lokasi.latitude);
                        i.putExtra("lon", lokasi.longitude);
                        i.putExtra("merlat", merlat);
                        i.putExtra("merlon", merlon);
                        i.putExtra("alamat", alamat);
                        i.putExtra("FiturKey", idfitur);
                        i.putExtra("distance", distance);
                        i.putExtra("alamatresto", alamatresto);
                        i.putExtra("idresto", idresto);
                        i.putExtra("namamerchant", nama.getText().toString());
                        i.putExtra("non_merchant", 0);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    } else {
                        notif("merchant are closing!");
                    }
                }
            });

        } else {
            pricecountainer.setVisibility(View.GONE);
        }

        qtytext.setText("" + quantity + " Item");
        Utility.currencyTXT(costtext, String.valueOf(cost), this);
    }

    private void getdata(Location location) {
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        UserService userService = ServiceGenerator.createService(
                UserService.class, loginUser.getNoTelepon(), loginUser.getPassword());
        MerchantbyIdRequestJson param = new MerchantbyIdRequestJson();
        param.setId(id);
        param.setLat(String.valueOf(location.getLatitude()));
        param.setLon(String.valueOf(location.getLongitude()));
        param.setPhone(loginUser.getNoTelepon());
        userService.merchantbyid(param).enqueue(new Callback<MerchantByIdResponseJson>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<MerchantByIdResponseJson> call, @NonNull final Response<MerchantByIdResponseJson> response) {
                if (response.isSuccessful()) {
                    if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("success")) {
                        shimmertutup();

                        if (!response.body().getFotomerchant().isEmpty()) {
                            PicassoTrustAll.getInstance(DetailMerchantActivity.this)
                                    .load(Constants.IMAGESMERCHANT + response.body().getFotomerchant())
                                    .resize(250, 250)
                                    .into(fotomerchant);
                        }

                        merlat = Double.parseDouble(response.body().getLatmerchant());
                        merlon = Double.parseDouble(response.body().getLongmerchant());
                        idfitur = Integer.parseInt(response.body().getIdfitur());
                        idresto = response.body().getIdmerchant();
                        alamatresto = response.body().getAlamatmerchant();

                        float km = Float.parseFloat(response.body().getDistance());
                        String format = String.format(Locale.US, "%.1f", km);

                        distance = Double.parseDouble(format);

                        String[] parsedJamBuka = response.body().getBukamerchant().split(":");
                        String[] parsedJamTutup = response.body().getTutupmerchant().split(":");

                        int jamBuka = Integer.parseInt(parsedJamBuka[0]), menitBuka = Integer.parseInt(parsedJamBuka[1]);
                        int jamTutup = Integer.parseInt(parsedJamTutup[0]), menitTutup = Integer.parseInt(parsedJamTutup[1]);

                        int totalMenitBuka = (jamBuka * 60) + menitBuka;
                        int totalMenitTutup = (jamTutup * 60) + menitTutup;

                        Calendar now = Calendar.getInstance();
                        int totalMenitNow = (now.get(Calendar.HOUR_OF_DAY) * 60) + now.get(Calendar.MINUTE);

                        if (totalMenitNow <= totalMenitTutup && totalMenitNow >= totalMenitBuka) {
                            tutup.setVisibility(View.GONE);
                            tutupki = "0";
                        } else {
                            tutup.setVisibility(View.VISIBLE);
                            tutupki = "1";
                        }

                        nama.setText(response.body().getNamamerchant());
                        maps.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i = new Intent(DetailMerchantActivity.this, MapsMerchantActivity.class);
                                i.putExtra("lat", lat);
                                i.putExtra("lon", lon);
                                i.putExtra("name", response.body().getNamamerchant());
                                startActivity(i);
                            }
                        });
                        hours.setText("Open Hours: " + Objects.requireNonNull(response.body()).getBukamerchant() + "-" + response.body().getTutupmerchant());
                        kategori.setText(response.body().getKategorimerchant() + " " + getString(R.string.text_with_bullet) + " " + distance + "Km");


                        if (response.body().getPromo().equals("1")) {
                            promo.setVisibility(View.VISIBLE);
                            promoshim.setVisibility(View.VISIBLE);
                            shimmerbadgepromo.startShimmerAnimation();
                        } else {
                            promo.setVisibility(View.GONE);
                            promoshim.setVisibility(View.GONE);
                            shimmerbadgepromo.setVisibility(View.GONE);
                        }

                        if (response.body().getPartner().equals("1")) {
                            partner.setVisibility(View.VISIBLE);
                            partnershim.setVisibility(View.VISIBLE);
                            shimmerbadge.startShimmerAnimation();
                        } else {
                            partner.setVisibility(View.GONE);
                            partnershim.setVisibility(View.GONE);
                            shimmerbadge.setVisibility(View.GONE);
                        }

                        itemRealmResults = response.body().getData();
                        resume = "1";
                        LoadItem();
                        Realm realm = BaseApp.getInstance(DetailMerchantActivity.this).getRealmInstance();
                        realm.beginTransaction();
                        realm.delete(ItemModel.class);
                        realm.copyToRealm(response.body().getData());
                        realm.commitTransaction();
                        itemAdapter.notifyDataSetChanged();
                        itemAdapter.withSelectable(true);
                        itemAdapter.withItemEvent(new ClickEventHook<ItemItem>() {
                            @Nullable
                            @Override
                            public View onBind(@NonNull RecyclerView.ViewHolder viewHolder) {
                                if (viewHolder instanceof ItemItem.ViewHolder) {
                                    return ((ItemItem.ViewHolder) viewHolder).itemView;
                                }
                                return null;
                            }

                            @Override
                            public void onClick(View v, int position, FastAdapter<ItemItem> fastAdapter, ItemItem item) {
                                sheetlist(position);
                            }
                        });
                        catMerchantNearItem = new CatItemItem(DetailMerchantActivity.this, response.body().getKategori(), R.layout.item_cat_merchant, new CatItemItem.OnItemClickListener() {
                            @Override
                            public void onItemClick(final CatItemModel item) {
                                //clicknear.clear();
                                shimlistnear.setVisibility(View.VISIBLE);
                                shimmerchantnear.setVisibility(View.VISIBLE);
                                shimlistcatnear.setVisibility(View.GONE);
                                rvmerchantnear.setVisibility(View.GONE);
                                nodatanear.setVisibility(View.GONE);
                                shimmerchantnear.startShimmerAnimation();
                                FusedLocationProviderClient mFusedLocation = LocationServices.getFusedLocationProviderClient(DetailMerchantActivity.this);
                                mFusedLocation.getLastLocation().addOnSuccessListener(DetailMerchantActivity.this, new OnSuccessListener<Location>() {
                                    @Override
                                    public void onSuccess(Location location) {
                                        if (location != null) {

                                            getmerchntbycatnear(location, item.getId_kategori_item());
                                        }
                                    }
                                });
                            }
                        });

                        rvcatmerchantnear.setAdapter(catMerchantNearItem);


                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<MerchantByIdResponseJson> call, @NonNull Throwable t) {

            }
        });
    }

    private void getmerchntbycatnear(final Location location, String cat) {
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        UserService userService = ServiceGenerator.createService(
                UserService.class, loginUser.getNoTelepon(), loginUser.getPassword());
        GetAllMerchantbyCatRequestJson param = new GetAllMerchantbyCatRequestJson();
        param.setId(id);
        param.setLat(String.valueOf(location.getLatitude()));
        param.setLon(String.valueOf(location.getLongitude()));
        param.setPhone(loginUser.getNoTelepon());
        param.setKategori(cat);
        userService.getitembycat(param).enqueue(new Callback<MerchantByIdResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<MerchantByIdResponseJson> call, @NonNull Response<MerchantByIdResponseJson> response) {
                if (response.isSuccessful()) {
                    if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("success")) {
                        itemAdapter.clear();
                        shimmerchantnear.setVisibility(View.GONE);
                        rvmerchantnear.setVisibility(View.VISIBLE);
                        shimmerchantnear.stopShimmerAnimation();
                        if (response.body().getData().isEmpty()) {
                            nodatanear.setVisibility(View.VISIBLE);
                        } else {
                            nodatanear.setVisibility(View.GONE);
                            itemRealmResults = response.body().getData();
                            LoadItem();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<MerchantByIdResponseJson> call, @NonNull Throwable t) {

            }
        });
    }

    private void DeletePesanan() {
        RealmResults<PesananMerchant> deleteFood = realm.where(PesananMerchant.class).findAll();
        realm.beginTransaction();
        deleteFood.deleteAllFromRealm();
        realm.commitTransaction();
    }

    private void LoadItem() {
        itemAdapter.clear();
        RealmResults<PesananMerchant> existingItemMenu = realm.where(PesananMerchant.class).findAll();


        exiF = new int[itemRealmResults.size()];

        for (int i = 0; i < itemRealmResults.size(); i++) {
            ItemItem itemMenuItem = new ItemItem(DetailMerchantActivity.this, this);

            itemMenuItem.quantity = 0;
            for (int j = 0; j < existingItemMenu.size(); j++) {
                if (Objects.requireNonNull(existingItemMenu.get(j)).getIdItem() == itemRealmResults.get(i).getId_item()) {
                    itemMenuItem.quantity = Objects.requireNonNull(existingItemMenu.get(j)).getQty();
                    itemMenuItem.catatan = Objects.requireNonNull(existingItemMenu.get(j)).getCatatan();
                    exiF[i] = Objects.requireNonNull(existingItemMenu.get(j)).getQty();
                }
            }

            itemMenuItem.id = itemRealmResults.get(i).getId_item();
            itemMenuItem.namaMenu = itemRealmResults.get(i).getNama_item();
            itemMenuItem.deskripsiMenu = itemRealmResults.get(i).getDeskripsi_item();
            itemMenuItem.foto = itemRealmResults.get(i).getFoto_item();
            itemMenuItem.harga = Long.parseLong(itemRealmResults.get(i).getHarga_item());
            itemMenuItem.promo = itemRealmResults.get(i).getStatus_promo();
            if (itemRealmResults.get(i).getHarga_promo().isEmpty()) {
                itemMenuItem.hargapromo = 0;
            } else {
                itemMenuItem.hargapromo = Long.parseLong(itemRealmResults.get(i).getHarga_promo());
            }
            itemAdapter.add(itemMenuItem);
        }

        itemAdapter.notifyDataSetChanged();
        rvmerchantnear.setAdapter(itemAdapter);
    }

    private void shimmershow() {
        shimmerdetail.startShimmerAnimation();
        rvmerchantnear.setVisibility(View.GONE);
        shimmerchantnear.startShimmerAnimation();
    }

    private void shimmertutup() {
        shimmerdetail.stopShimmerAnimation();
        shimmerdetail.setVisibility(View.GONE);
        lldetail.setVisibility(View.VISIBLE);
        rvcatmerchantnear.setVisibility(View.VISIBLE);
        rvmerchantnear.setVisibility(View.VISIBLE);
        shimmerchantnear.stopShimmerAnimation();
        shimmerchantnear.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DeletePesanan();
        realm.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        calculatePrice();
        if (resume.equals("1")) {
            LoadItem();
        }
    }

    @Override
    public void onBackPressed() {
        List<PesananMerchant> existingItem = realm.where(PesananMerchant.class).findAll();

        int quantity = 0;
        for (int p = 0; p < existingItem.size(); p++) {
            quantity += Objects.requireNonNull(existingItem.get(p)).getQty();
        }

        if (quantity > 0) {
            new AlertDialog.Builder(this, R.style.DialogStyle)
                    .setTitle("Delete current order(s)?")
                    .setMessage("Leaving this page will cause you lose all the order you've made. Continue?")
                    .setPositiveButton("YES!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DeletePesanan();
                            finish();
                        }
                    })
                    .setNegativeButton("NO", null)
                    .show();
        } else {
            finish();
        }
    }

    private void sheetlist(int position) {
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottom_sheet);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        ItemModel selectedItem = realm.where(ItemModel.class).equalTo("id_item", itemAdapter.getAdapterItem(position).id).findFirst();
        @SuppressLint("InflateParams") final View mDialog = getLayoutInflater().inflate(R.layout.sheet_detail_item, null);
        TextView text = mDialog.findViewById(R.id.title);
        ImageView imageView = mDialog.findViewById(R.id.imageview);
        WebView content = mDialog.findViewById(R.id.content);
        TextView kategori = mDialog.findViewById(R.id.kategori);
        TextView hargapromo = mDialog.findViewById(R.id.hargapromo);
        TextView harga = mDialog.findViewById(R.id.harga);

        PicassoTrustAll.getInstance(this)
                .load(Constants.IMAGESITEM + Objects.requireNonNull(selectedItem).getFoto_item())
                .resize(250, 250)
                .into(imageView);

        if (selectedItem.getStatus_promo().equals("1")) {
            hargapromo.setVisibility(View.VISIBLE);
            Utility.currencyTXT(harga, selectedItem.getHarga_promo(), this);
            Utility.currencyTXT(hargapromo, selectedItem.getHarga_item(), this);
            hargapromo.setPaintFlags(hargapromo.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            hargapromo.setVisibility(View.GONE);
            Utility.currencyTXT(harga, selectedItem.getHarga_item(), this);

        }

        kategori.setText(selectedItem.getKategori_item());


        String mimeType = "text/html";
        String encoding = "utf-8";
        String htmlText = selectedItem.getDeskripsi_item();

        String textcontent = "<html dir=" + "><head>"
                + "<style type=\"text/css\">@font-face {font-family: MyFont;src: url(\"file:///android_asset/fonts/NeoSans_Pro_Regular.ttf\")}body{font-family: MyFont;color: #000000;text-align:justify;line-height:1.2}"
                + "</style></head>"
                + "<body>"
                + htmlText
                + "</body></html>";
        content.loadDataWithBaseURL(null, textcontent, mimeType, encoding, null);

        text.setText(selectedItem.getNama_item());


        mBottomSheetDialog = new BottomSheetDialog(this);
        mBottomSheetDialog.setContentView(mDialog);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Objects.requireNonNull(mBottomSheetDialog.getWindow()).addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        mBottomSheetDialog.show();
        mBottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mBottomSheetDialog = null;
            }
        });
    }
}
