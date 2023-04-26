package com.antar.passenger.item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.antar.passenger.R;
import com.antar.passenger.constants.Constants;
import com.antar.passenger.json.MyVoucherResponseJson;
import com.antar.passenger.models.UserVoucherModel;
import com.antar.passenger.models.VoucherModel;
import com.antar.passenger.utils.PicassoTrustAll;
import com.antar.passenger.utils.Utility;

import java.util.List;

/**
 * Created by Ardian Iqbal Yusmartito on 16/04/23
 * Github : https://github.com/ALU-syntax
 * Twitter : https://twitter.com/mengkerebe
 * Instagram : https://www.instagram.com/ardian_iqbal_
 * LinkedIn : https://www.linkedin.com/in/ardianiqbal
 */
public class MyVoucherItem extends RecyclerView.Adapter<MyVoucherItem.VoucherView> {

    private List<MyVoucherResponseJson> listVoucherModels;
    private int viewHolder;
    private Context mContext;
    private String quantity;


    public MyVoucherItem() {
    }

    public MyVoucherItem(List<MyVoucherResponseJson> listVoucherModels, int viewHolder, Context mContext) {
        this.listVoucherModels = listVoucherModels;
        this.viewHolder = viewHolder;
        this.mContext = mContext;
    }

    public MyVoucherItem(List<MyVoucherResponseJson> listVoucherModels, int viewHolder, Context mContext, String quantity) {
        this.listVoucherModels = listVoucherModels;
        this.viewHolder = viewHolder;
        this.mContext = mContext;
        this.quantity = quantity;
    }

    @NonNull
    @Override
    public VoucherView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(viewHolder, parent, false);
        return new VoucherView(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VoucherView holder, int position) {
        MyVoucherResponseJson voucherModel = listVoucherModels.get(position);
        String txtVoucherExpired = "Expired: " + voucherModel.getExpired();
        String txtVoucherQuantity = "Jumlah: " + voucherModel.getQuantity();

        holder.tvVoucherExpired.setText(txtVoucherExpired);
        holder.tvVoucherQuantity.setText(txtVoucherQuantity);
        holder.tvNamaVoucher.setText(voucherModel.getNamaVoucherPromo());
        Utility.convertLocaleCurrencyTV(holder.tvNominalVoucher, mContext, String.valueOf(voucherModel.getNominalVoucherPromo()), "Nilai Voucher:");
        holder.tvMinimumTransaction.setText(String.valueOf(voucherModel.getMinimumTransaksi()));
        Utility.convertLocaleCurrencyTV(holder.tvMinimumTransaction, mContext, String.valueOf(voucherModel.getMinimumTransaksi()), "Minimum Transaksi:");

        if(!voucherModel.getImageVoucherPromo().isEmpty()){
            PicassoTrustAll.getInstance(mContext)
                    .load(Constants.IMAGESSLIDER + voucherModel.getImageVoucherPromo())
                    .into(holder.voucherImage);
        }

    }

    @Override
    public int getItemCount() {
        return (null != listVoucherModels ? listVoucherModels.size() : 0);
    }

    public class VoucherView extends RecyclerView.ViewHolder{
        ImageView voucherImage;
        TextView tvVoucherExpired, tvVoucherQuantity, tvNamaVoucher, tvNominalVoucher, tvMinimumTransaction;

        public VoucherView(@NonNull View itemView) {
            super(itemView);
            voucherImage = itemView.findViewById(R.id.my_image_voucher);
            tvVoucherExpired = itemView.findViewById(R.id.my_voucher_expired);
            tvVoucherQuantity = itemView.findViewById(R.id.tv_my_voucher_quantity);
            tvNamaVoucher = itemView.findViewById(R.id.my_nama_voucher);
            tvNominalVoucher = itemView.findViewById(R.id.tv_my_nominal_voucher);
            tvMinimumTransaction = itemView.findViewById(R.id.tv_my_minimum_transaksi);

        }
    }
}
