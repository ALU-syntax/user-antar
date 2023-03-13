package com.insoftbumdesku.passenger.item;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.insoftbumdesku.passenger.R;
import com.insoftbumdesku.passenger.mine.MartInterface;
import com.mikepenz.fastadapter.items.AbstractItem;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Androgo on 12/4/2018.
 */

public class MartItem extends AbstractItem<MartItem, MartItem.ViewHolder> {

    private MartInterface listener;


    private String namaProduk = "";
    private int quantity = 1;
    private int itemPrice;
    private int priceTotal=0;
    private String catatan="";

    public MartItem(MartInterface listener){
        this.listener = listener;
    }

    public String getNamaProduk() {
        return namaProduk;
    }

    public void setNamaProduk(String namaProduk) {
        this.namaProduk = namaProduk;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(int itemPrice) {
        this.itemPrice = itemPrice;
    }

    public int getPriceTotal() {
        return priceTotal;
    }

    public void setPriceTotal(int priceTotal) {
        this.priceTotal = priceTotal;
    }

    public String getCatatan() {
        return catatan;
    }

    public void setCatatan(String catatan) {
        this.catatan = catatan;
    }

    @Override
    public int getType() {
        return R.id.item_menu;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_menu;
    }

    @Override
    public void bindView(final ViewHolder holder, List payloads) {
        super.bindView(holder, payloads);

        final ViewHolder holderFinal = holder;

        holder.productName.setText(namaProduk);
        holder.productQuantity.setText(String.valueOf(quantity));
        holder.priceTotal.setText(String.valueOf(priceTotal));

        holder.productName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                namaProduk = holderFinal.productName.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        holder.itemPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String quantity = holderFinal.productQuantity.getText().toString();
                String harga = holderFinal.itemPrice.getText().toString();
                if(!harga.isEmpty()) {
                    int quantity_int = Integer.parseInt(quantity);
                    itemPrice = Integer.parseInt(harga);
                    hitungHarga(quantity_int, itemPrice, holderFinal);
                }else{
                    holder.itemPrice.setError("Harga Estimasi Tidak Boleh Kosong");
                }
                listener.hitungHarga();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        holder.itemCatatan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                catatan = holderFinal.itemCatatan.getText().toString();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        holder.addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProduct();
                holder.productQuantity.setText(String.valueOf(quantity));
                String itemPrice_str = holder.itemPrice.getText().toString();
                if(!itemPrice_str.isEmpty()){
                    itemPrice = Integer.parseInt(itemPrice_str);
                    priceTotal = itemPrice * quantity;
                    holder.priceTotal.setText(String.valueOf(priceTotal));
                }else{
                    holder.itemPrice.setError("Harga Estimasi Tidak Boleh Kosong...!");
                }
                listener.hitungTotal();
                listener.hitungHarga();

            }
        });

        holder.removeProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeProduct();
                holder.productQuantity.setText(String.valueOf(quantity));
                String itemPrice_str = holder.itemPrice.getText().toString();
                if(!itemPrice_str.isEmpty()){
                    itemPrice = Integer.parseInt(itemPrice_str);
                    priceTotal = itemPrice * quantity;
                    holder.priceTotal.setText(String.valueOf(priceTotal));
                }else{
                    holder.itemPrice.setError("Harga Estimasi Tidak Boleh Kosong...!");
                }
                listener.hitungTotal();
                listener.hitungHarga();
            }
        });
    }

    private void addProduct() {
        if (quantity + 1 <= 100) quantity++;

    }

    private void removeProduct() {
        if (quantity - 1 > 0) quantity--;
    }

    private void hitungHarga(int jumlah, int harga, ViewHolder holder) {
        priceTotal = jumlah * harga;
        String total_harga_str = String.valueOf(priceTotal);
        holder.priceTotal.setText(total_harga_str);
    }

    @Override
    public void unbindView(ViewHolder holder) {
        super.unbindView(holder);
        holder.productName.setText(null);
        holder.productQuantity.setText(null);
        holder.itemPrice.setText(null);
        holder.priceTotal.setText(null);
        holder.itemCatatan.setText(null);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_name)
        EditText productName;

        @BindView(R.id.item_rightButton)
        ImageView addProduct;

        @BindView(R.id.item_leftButton)
        ImageView removeProduct;

        @BindView(R.id.item_quantity)
        TextView productQuantity;

        @BindView(R.id.item_price)
        EditText itemPrice;

        @BindView(R.id.price_total)
        TextView priceTotal;

        @BindView(R.id.item_catatan)
        EditText itemCatatan;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
