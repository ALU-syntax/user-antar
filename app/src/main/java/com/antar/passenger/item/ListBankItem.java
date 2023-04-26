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
import com.antar.passenger.models.BankModel;
import com.antar.passenger.models.MerchantModel;
import com.antar.passenger.utils.PicassoTrustAll;

import java.util.List;

/**
 * Created by Ardian Iqbal Yusmartito on 26/04/23
 * Github : https://github.com/ALU-syntax
 * Twitter : https://twitter.com/mengkerebe
 * Instagram : https://www.instagram.com/ardian_iqbal_
 * LinkedIn : https://www.linkedin.com/in/ardianiqbal
 */
public class ListBankItem extends RecyclerView.Adapter<ListBankItem.ViewHolder> {

    private List<BankModel> dataList;
    private Context mContext;
    private int rowLayout;

    public ListBankItem() {
    }

    public ListBankItem(List<BankModel> dataList, Context mContext, int rowLayout) {
        this.dataList = dataList;
        this.mContext = mContext;
        this.rowLayout = rowLayout;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final BankModel singleItem = dataList.get(position);
        holder.accountNumber.setText(singleItem.getRekening_bank());
        holder.bankName.setText(singleItem.getNama_bank());
        holder.accountOwner.setText(singleItem.getNama_pemilik());

        System.out.println("debugImage: " + singleItem.getImage_bank());

        if (!singleItem.getImage_bank().isEmpty()) {
//            PicassoTrustAll.getInstance(mContext)
//                    .load(Constants.IMAGESMERCHANT + singleItem.getImage_bank())
//                    .resize(250, 250)
//                    .into(holder.bankLogo);
            System.out.println("debugImage: " + singleItem.getImage_bank());

            PicassoTrustAll.getInstance(mContext)
                    .load(Constants.IMAGESBANK + singleItem.getImage_bank())
                    .into(holder.bankLogo);
        }
    }


    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView bankName, accountOwner, accountNumber;
        ImageView bankLogo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            bankName = itemView.findViewById(R.id.tv_bank_name);
            accountOwner = itemView.findViewById(R.id.tv_account_owner_name);
            accountNumber = itemView.findViewById(R.id.tv_account_number);
            bankLogo = itemView.findViewById(R.id.iv_logo_bank);

        }
    }
}
