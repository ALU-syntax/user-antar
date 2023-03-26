package com.antar.passenger.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.antar.passenger.R;
import com.antar.passenger.constants.Constants;
import com.antar.passenger.item.TopUpItem;
import com.antar.passenger.models.ayopulsa.PriceListDataModel;
import com.antar.passenger.models.ayopulsa.PriceListDetailModel;
import com.antar.passenger.utils.LocaleHelper;
import com.antar.passenger.utils.SettingPreference;

public class OperatorProviderListActivity extends AppCompatActivity implements TopUpItem.onTopUpItemClicked {

    RecyclerView recycle;
    TopUpItem adapterTopUp;
    TextView tvTitle;
    RelativeLayout rlProgress;
    EditText etSearchProduct;
    ImageView backButton;
    SettingPreference sp;
    PriceListDataModel priceListCategoryPulsa;
    String topUpType;

    public static final String TOPUP_TYPE = "topuptype";

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_list);
        etSearchProduct = findViewById(R.id.etSearchProduct);
        rlProgress= findViewById(R.id.rlprogress);
        backButton = findViewById(R.id.back_btn);
        recycle = findViewById(R.id.rcView);
        tvTitle = findViewById(R.id.tvTitle);
        sp = new SettingPreference(this);

        initRecyclerView();
        initIntent();

        backButton.setOnClickListener(v -> finish());

        etSearchProduct.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (adapterTopUp != null) {
                    adapterTopUp.getFilter().filter(s.toString());
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initRecyclerView() {
        recycle.setHasFixedSize(true);
        recycle.setNestedScrollingEnabled(false);
        recycle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    private void initIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            String operator = intent.getStringExtra(Constants.OPERATOR);
            topUpType = intent.getStringExtra(TOPUP_TYPE);
            tvTitle.setText(operator != null ?operator.toUpperCase(): "");
            priceListCategoryPulsa = (PriceListDataModel) intent.getSerializableExtra("data");
            if (priceListCategoryPulsa != null) {
                adapterTopUp = new TopUpItem(priceListCategoryPulsa.getDataX(), OperatorProviderListActivity.this);
                recycle.setAdapter(adapterTopUp);
            }
        }
    }

    @Override
    public void onClick(PriceListDetailModel model) {
        Intent i = new Intent();
        i.putExtra(Constants.OPERATOR, model);
        setResult(RESULT_OK, i);
        finish();
    }
}
