package com.antar.passenger.activity;


import android.app.ProgressDialog;
import android.content.Context;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.antar.passenger.R;
import com.antar.passenger.adapter.CityAdapter;
import com.antar.passenger.adapter.ExpedisiAdapter;
import com.antar.passenger.adapter.ProvinceAdapter;
import com.antar.passenger.api.ApiService;
import com.antar.passenger.api.ApiUrl;
import com.antar.passenger.models.city.ItemCity;
import com.antar.passenger.models.cost.ItemCost;
import com.antar.passenger.models.expedisi.ItemExpedisi;
import com.antar.passenger.models.province.ItemProvince;
import com.antar.passenger.models.province.Result;
import com.antar.passenger.utils.LocaleHelper;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OngkirActivity extends AppCompatActivity {

    private EditText etFromProvince, etToProvince;
    private EditText etFromCity, etToCity;
    private EditText etWeight, etCourier;

    private AlertDialog.Builder alert;
    private AlertDialog ad;
    private EditText searchList;
    private ListView mListView;

    private ProvinceAdapter adapter_province;
    private List<Result> ListProvince = new ArrayList<Result>();

    private CityAdapter adapter_city;
    private List<com.antar.passenger.models.city.Result> ListCity = new ArrayList<>();

    private ExpedisiAdapter adapter_expedisi;
    private List<ItemExpedisi> listItemExpedisi = new ArrayList<ItemExpedisi>();

    private ProgressDialog progressDialog;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ongkir);

        etFromProvince = (EditText) findViewById(R.id.etFromProvince);
        etFromCity = (EditText) findViewById(R.id.etFromCity);
        etToProvince = (EditText) findViewById(R.id.etToProvince);
        etToCity = (EditText) findViewById(R.id.etToCity);
        etWeight = (EditText) findViewById(R.id.etWeight);
        etCourier = (EditText) findViewById(R.id.etCourier);

        etFromProvince.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUpProvince(etFromProvince, etFromCity);

            }
        });

        etFromCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if (etFromProvince.getTag().equals("")) {
                        etFromProvince.setError("Mohon pilih provinsi");
                    } else {
                        popUpCity(etFromCity, etFromProvince);
                    }

                } catch (NullPointerException e) {
                    etFromProvince.setError("Mohon pilih provinsi");
                }

            }
        });

        etToProvince.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUpProvince(etToProvince, etToCity);

            }
        });

        etToCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if (etToProvince.getTag().equals("")) {
                        etToProvince.setError("Mohon pilih tujuan provinsi");
                    } else {
                        popUpCity(etToCity, etToProvince);
                    }

                } catch (NullPointerException e) {
                    etToProvince.setError("Mohon pilih tujuan provinsi");
                }

            }
        });

        etCourier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUpExpedisi(etCourier);
            }
        });

        Button btnProcess = (Button) findViewById(R.id.btnProcess);
        btnProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Origin = etFromCity.getText().toString();
                String destination = etToCity.getText().toString();
                String Weight = etWeight.getText().toString();
                String expedisi = etCourier.getText().toString();

                if (Origin.equals("")){
                    etFromCity.setError("Masukan posisi pengirim");
                } else if (destination.equals("")){
                    etToCity.setError("Masukan tujuan kota ");
                } else if (Weight.equals("")){
                    etWeight.setError("Masukan berat paket");
                } else if (expedisi.equals("")){
                    etCourier.setError("Mohon pilih expedisinya");
                } else {

                    progressDialog = new ProgressDialog(OngkirActivity.this);
                    progressDialog.setMessage("Mohon tunggu..");
                    progressDialog.show();

                    getCoast(
                            etFromCity.getTag().toString(),
                            etToCity.getTag().toString(),
                            etWeight.getText().toString(),
                            etCourier.getText().toString()
                    );
                }

            }
        });
    }

    public void popUpProvince(final EditText etProvince, final EditText etCity ) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View alertLayout = inflater.inflate(R.layout.custom_dialog_search, null);

        alert = new AlertDialog.Builder(OngkirActivity.this);
        alert.setTitle("Daftar Provinsi");
        alert.setMessage("Pilih Provinsi");
        alert.setView(alertLayout);
        alert.setCancelable(true);

        ad = alert.show();

        searchList = (EditText) alertLayout.findViewById(R.id.searchItem);
        searchList.addTextChangedListener(new MyTextWatcherProvince(searchList));
        searchList.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

        mListView = (ListView) alertLayout.findViewById(R.id.listItem);

        ListProvince.clear();
        adapter_province = new ProvinceAdapter(OngkirActivity.this, ListProvince);
        mListView.setClickable(true);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Object o = mListView.getItemAtPosition(i);
                Result cn = (Result) o;

                etProvince.setError(null);
                etProvince.setText(cn.getProvince());
                etProvince.setTag(cn.getProvinceId());

                etCity.setText("");
                etCity.setTag("");

                ad.dismiss();
            }
        });

        progressDialog = new ProgressDialog(OngkirActivity.this);
            progressDialog.setMessage("Mohon tunggu..");
        progressDialog.show();

        getProvince();

    }

    public void popUpCity(final EditText etCity, final EditText etProvince) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View alertLayout = inflater.inflate(R.layout.custom_dialog_search, null);

        alert = new AlertDialog.Builder(OngkirActivity.this);
            alert.setTitle("Daftar Kota");
        alert.setMessage("Pilih Kota");
        alert.setView(alertLayout);
        alert.setCancelable(true);

        ad = alert.show();

        searchList = (EditText) alertLayout.findViewById(R.id.searchItem);
        searchList.addTextChangedListener(new MyTextWatcherCity(searchList));
        searchList.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

        mListView = (ListView) alertLayout.findViewById(R.id.listItem);

        ListCity.clear();
        adapter_city = new CityAdapter(OngkirActivity.this, ListCity);
        mListView.setClickable(true);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Object o = mListView.getItemAtPosition(i);
                com.antar.passenger.models.city.Result cn = (com.antar.passenger.models.city.Result) o;

                etCity.setError(null);
                etCity.setText(cn.getCityName());
                etCity.setTag(cn.getCityId());

                ad.dismiss();
            }
        });

        progressDialog = new ProgressDialog(OngkirActivity.this);
        progressDialog.setMessage("Mohon tunggu..");
        progressDialog.show();

        getCity(etProvince.getTag().toString());

    }

    public void popUpExpedisi(final EditText etExpedisi) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View alertLayout = inflater.inflate(R.layout.custom_dialog_search, null);

        alert = new AlertDialog.Builder(OngkirActivity.this);
        alert.setTitle("Daftar Expedisi");
            alert.setMessage("Pilih Expedisi");
        alert.setView(alertLayout);
        alert.setCancelable(true);

        ad = alert.show();

        searchList = (EditText) alertLayout.findViewById(R.id.searchItem);
        searchList.addTextChangedListener(new MyTextWatcherCity(searchList));
        searchList.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

        mListView = (ListView) alertLayout.findViewById(R.id.listItem);

        listItemExpedisi.clear();
        adapter_expedisi = new ExpedisiAdapter(OngkirActivity.this, listItemExpedisi);
        mListView.setClickable(true);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Object o = mListView.getItemAtPosition(i);
                ItemExpedisi cn = (ItemExpedisi) o;

                etExpedisi.setError(null);
                etExpedisi.setText(cn.getName());
                etExpedisi.setTag(cn.getId());

                ad.dismiss();
            }
        });

        getExpedisi();

    }

    private class MyTextWatcherProvince implements TextWatcher {

        private View view;

        private MyTextWatcherProvince(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence s, int i, int before, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.searchItem:
                    adapter_province.filter(editable.toString());
                    break;
            }
        }
    }

    private class MyTextWatcherCity implements TextWatcher {

        private View view;

        private MyTextWatcherCity(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence s, int i, int before, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.searchItem:
                    adapter_city.filter(editable.toString());
                    break;
            }
        }
    }

    public void getProvince() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiUrl.URL_ROOT_HTTPS)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService service = retrofit.create(ApiService.class);
        Call<ItemProvince> call = service.getProvince();

        call.enqueue(new Callback<ItemProvince>() {
            @Override
            public void onResponse(Call<ItemProvince> call, Response<ItemProvince> response) {

                progressDialog.dismiss();
                Log.v("wow", "json : " + new Gson().toJson(response));

                if (response.isSuccessful()) {

                    int count_data = response.body().getRajaongkir().getResults().size();
                    for (int a = 0; a <= count_data - 1; a++) {
                        Result itemProvince = new Result(
                                response.body().getRajaongkir().getResults().get(a).getProvinceId(),
                                response.body().getRajaongkir().getResults().get(a).getProvince()
                        );

                        ListProvince.add(itemProvince);
                        mListView.setAdapter(adapter_province);
                    }

                    adapter_province.setList(ListProvince);
                    adapter_province.filter("");

                } else {
                    String error = "Error Retrive Data from Server !!!";
                    Toast.makeText(OngkirActivity.this, error, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ItemProvince> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(OngkirActivity.this, "Message : Error " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void getCity(String id_province) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiUrl.URL_ROOT_HTTPS)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService service = retrofit.create(ApiService.class);
        Call<ItemCity> call = service.getCity(id_province);

        call.enqueue(new Callback<ItemCity>() {
            @Override
            public void onResponse(Call<ItemCity> call, Response<ItemCity> response) {

                progressDialog.dismiss();
                Log.v("wow", "json : " + new Gson().toJson(response));

                if (response.isSuccessful()) {

                    int count_data = response.body().getRajaongkir().getResults().size();
                    for (int a = 0; a <= count_data - 1; a++) {
                        com.antar.passenger.models.city.Result itemProvince = new com.antar.passenger.models.city.Result(
                                response.body().getRajaongkir().getResults().get(a).getCityId(),
                                response.body().getRajaongkir().getResults().get(a).getProvinceId(),
                                response.body().getRajaongkir().getResults().get(a).getProvince(),
                                response.body().getRajaongkir().getResults().get(a).getType(),
                                response.body().getRajaongkir().getResults().get(a).getCityName(),
                                response.body().getRajaongkir().getResults().get(a).getPostalCode()
                        );

                        ListCity.add(itemProvince);
                        mListView.setAdapter(adapter_city);
                    }

                    adapter_city.setList(ListCity);
                    adapter_city.filter("");

                } else {
                    String error = "Error Retrive Data from Server !!!";
                    Toast.makeText(OngkirActivity.this, error, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ItemCity> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(OngkirActivity.this, "Message : Error " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getExpedisi() {

        ItemExpedisi itemItemExpedisi = new ItemExpedisi();

        itemItemExpedisi = new ItemExpedisi("1", "pos");
        listItemExpedisi.add(itemItemExpedisi);
        itemItemExpedisi = new ItemExpedisi("1", "tiki");
        listItemExpedisi.add(itemItemExpedisi);
        itemItemExpedisi = new ItemExpedisi("1", "jne");
        listItemExpedisi.add(itemItemExpedisi);

        mListView.setAdapter(adapter_expedisi);

        adapter_expedisi.setList(listItemExpedisi);
        adapter_expedisi.filter("");

    }

    public void getCoast(String origin,
                         String destination,
                         String weight,
                         String courier) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiUrl.URL_ROOT_HTTPS)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService service = retrofit.create(ApiService.class);
        Call<ItemCost> call = service.getCost(
                "2c1cf2e4614041e9e929dbee9b2ccfd1",
                origin,
                destination,
                weight,
                courier
        );

        call.enqueue(new Callback<ItemCost>() {
            @Override
            public void onResponse(Call<ItemCost> call, Response<ItemCost> response) {

                Log.v("wow", "json : " + new Gson().toJson(response));
                progressDialog.dismiss();

                if (response.isSuccessful()) {

                    int statusCode = response.body().getRajaongkir().getStatus().getCode();

                    if (statusCode == 200){
                        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View alertLayout = inflater.inflate(R.layout.custom_dialog_result, null);
                        alert = new AlertDialog.Builder(OngkirActivity.this);
                        alert.setTitle("Keterangan");
                        alert.setMessage("Hasil perhitungan");
                        alert.setView(alertLayout);
                        alert.setCancelable(true);

                        ad = alert.show();

                        TextView tv_origin = (TextView) alertLayout.findViewById(R.id.tv_origin);
                        TextView tv_destination = (TextView) alertLayout.findViewById(R.id.tv_destination);
                        TextView tv_expedisi = (TextView) alertLayout.findViewById(R.id.tv_expedisi);
                        TextView tv_coast = (TextView) alertLayout.findViewById(R.id.tv_coast);
                        TextView tv_time = (TextView) alertLayout.findViewById(R.id.tv_time);

                        tv_origin.setText(response.body().getRajaongkir().getOriginDetails().getCityName()+" (Postal Code : "+
                                response.body().getRajaongkir().getOriginDetails().getPostalCode()+")");

                        tv_destination.setText(response.body().getRajaongkir().getDestinationDetails().getCityName()+" (Postal Code : "+
                                response.body().getRajaongkir().getDestinationDetails().getPostalCode()+")");

                        tv_expedisi.setText(response.body().getRajaongkir().getResults().get(0).getCosts().get(0).getDescription()+" ("+
                                response.body().getRajaongkir().getResults().get(0).getName()+") ");

                        tv_coast.setText("Rp. "+response.body().getRajaongkir().getResults().get(0).getCosts().get(0).getCost().get(0).getValue().toString());

                        tv_time.setText(response.body().getRajaongkir().getResults().get(0).getCosts().get(0).getCost().get(0).getEtd()+" (Days)");

                        etFromProvince.setText("");
                        etFromCity.setText("");
                        etToProvince.setText("");
                        etToCity.setText("");
                        etWeight.setText("");
                        etCourier.setText("");
                    } else {

                        String message = response.body().getRajaongkir().getStatus().getDescription();
                        Toast.makeText(OngkirActivity.this, message, Toast.LENGTH_SHORT).show();
                    }

                } else {
                    String error = "Error Retrive Data from Server !!!";
                    Toast.makeText(OngkirActivity.this, error, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ItemCost> call, Throwable t) {

                progressDialog.dismiss();
                Toast.makeText(OngkirActivity.this, "Message : Error " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
