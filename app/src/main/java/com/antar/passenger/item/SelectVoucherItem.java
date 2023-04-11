package com.antar.passenger.item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.antar.passenger.R;
import com.antar.passenger.models.UserVoucherModel;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Ardian Iqbal Yusmartito on 11/04/23
 * Github : https://github.com/ALU-syntax
 * Twitter : https://twitter.com/mengkerebe
 * Instagram : https://www.instagram.com/ardian_iqbal_
 * LinkedIn : https://www.linkedin.com/in/ardianiqbal
 */
public class SelectVoucherItem extends RecyclerView.Adapter<SelectVoucherItem.ViewSelectVoucher> {

    private List<UserVoucherModel> userVoucherList;
    private Context mContext;
    private int rowLayout;


    @NonNull
    @Override
    public ViewSelectVoucher onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ViewSelectVoucher(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewSelectVoucher holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewSelectVoucher extends RecyclerView.ViewHolder{
        TextView tvVoucherName, tvVoucherQuantity, tvExpired, tvVoucherNominal;
        ImageView ivVoucherPoster;
        Button btnSelectVoucher;

        public ViewSelectVoucher(@NonNull View itemView) {
            super(itemView);
            tvVoucherName = itemView.findViewById(R.id.nama_voucher);
            tvVoucherQuantity = itemView.findViewById(R.id.tv_voucher_quantity);
            tvExpired = itemView.findViewById(R.id.voucher_expired);
            tvVoucherNominal = itemView.findViewById(R.id.tv_nominal_voucher);
            ivVoucherPoster = itemView.findViewById(R.id.image_voucher);
            btnSelectVoucher = itemView.findViewById(R.id.btn_use);
        }
    }
}
