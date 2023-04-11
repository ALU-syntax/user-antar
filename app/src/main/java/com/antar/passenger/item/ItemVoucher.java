package com.antar.passenger.item;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.antar.passenger.R;
import com.antar.passenger.activity.DetailVoucherActivity;
import com.antar.passenger.constants.Constants;
import com.antar.passenger.models.VoucherModel;
import com.antar.passenger.utils.PicassoTrustAll;
import com.antar.passenger.utils.Utility;

import java.util.List;

/**
 * Created by Ardian Iqbal Yusmartito on 09/04/23
 * Github : https://github.com/ALU-syntax
 * Twitter : https://twitter.com/mengkerebe
 * Instagram : https://www.instagram.com/ardian_iqbal_
 * LinkedIn : https://www.linkedin.com/in/ardianiqbal
 */
public class ItemVoucher extends RecyclerView.Adapter<ItemVoucher.ViewHolder> {

    private List<VoucherModel> listVoucherModel;
    private int viewHolder;
    private Context mContext;


    public ItemVoucher(List<VoucherModel> listVoucherModel, int viewHolder, Context mContext) {
        this.listVoucherModel = listVoucherModel;
        this.viewHolder = viewHolder;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(viewHolder, parent,  false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        VoucherModel voucherModel = listVoucherModel.get(position);
        holder.tvExpired.setText(voucherModel.getExpired());
        Utility.convertLocaleCurrencyTV(holder.tvPrice, mContext, String.valueOf(voucherModel.getHargaVoucher()));
        holder.tvName.setText(voucherModel.getVoucherName());

        if(!voucherModel.getImageVoucher().isEmpty()){
            PicassoTrustAll.getInstance(mContext)
                    .load(Constants.IMAGESSLIDER + voucherModel.getImageVoucher())
                    .into(holder.ivVoucherPoster);
        }

        holder.rlDetailVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, DetailVoucherActivity.class);
                i.putExtra("voucherName", voucherModel.getVoucherName());
                i.putExtra("voucherPrice", String.valueOf(voucherModel.getHargaVoucher()));
                i.putExtra("voucherDesc", voucherModel.getDescription());
                i.putExtra("voucherExpired", voucherModel.getExpired());
                i.putExtra("voucherPoster", voucherModel.getImageVoucher());
                i.putExtra("voucherQuantity", String.valueOf(voucherModel.getIsiVoucher()));
                i.putExtra("voucherNominal", String.valueOf(voucherModel.getVoucherNominal()));
                i.putExtra("voucherMinimumOrder", String.valueOf(voucherModel.getMinimumTransaksi()));
                i.putExtra("voucherType", voucherModel.getVoucherType());
                mContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != listVoucherModel ? listVoucherModel.size() : 0);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvPrice, tvExpired, tvName;
        ImageView ivVoucherPoster;
        RelativeLayout rlDetailVoucher;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvExpired = itemView.findViewById(R.id.tv_expired);
            tvName = itemView.findViewById(R.id.tv_voucher_name);
            ivVoucherPoster = itemView.findViewById(R.id.iv_voucher_poster);
            rlDetailVoucher = itemView.findViewById(R.id.rl_detail_voucher);

        }
    }
}
