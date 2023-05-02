package com.antar.passenger.item;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.antar.passenger.R;
import com.antar.passenger.activity.SelectVoucherActivity;
import com.antar.passenger.constants.BaseApp;
import com.antar.passenger.constants.Constants;
import com.antar.passenger.json.GetFiturResponseJson;
import com.antar.passenger.json.MyVoucherResponseJson;
import com.antar.passenger.models.FiturModel;
import com.antar.passenger.models.User;
import com.antar.passenger.models.UserVoucherModel;
import com.antar.passenger.utils.PicassoTrustAll;
import com.antar.passenger.utils.Utility;
import com.antar.passenger.utils.api.ServiceGenerator;
import com.antar.passenger.utils.api.service.UserService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.zip.Inflater;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ardian Iqbal Yusmartito on 11/04/23
 * Github : https://github.com/ALU-syntax
 * Twitter : https://twitter.com/mengkerebe
 * Instagram : https://www.instagram.com/ardian_iqbal_
 * LinkedIn : https://www.linkedin.com/in/ardianiqbal
 */
public class SelectVoucherItem extends RecyclerView.Adapter<SelectVoucherItem.ViewSelectVoucher> {

    private List<MyVoucherResponseJson> userVoucherList;
    private Context mContext;
    private int rowLayout;
    private Activity mActivity;
    private int fiturId;


    public SelectVoucherItem() {
    }

    public SelectVoucherItem(List<MyVoucherResponseJson> userVoucherList, Context mContext, int rowLayout) {
        this.userVoucherList = userVoucherList;
        this.mContext = mContext;
        this.rowLayout = rowLayout;
    }

    public SelectVoucherItem(List<MyVoucherResponseJson> userVoucherList, Context mContext, int rowLayout, Activity activity) {
        this.userVoucherList = userVoucherList;
        this.mContext = mContext;
        this.rowLayout = rowLayout;
        this.mActivity = activity;
    }

    public SelectVoucherItem(List<MyVoucherResponseJson> userVoucherList, Context mContext, int rowLayout, Activity mActivity, int fiturId) {
        List<MyVoucherResponseJson> voucher = new ArrayList<>();
        this.mContext = mContext;
        this.rowLayout = rowLayout;
        this.mActivity = mActivity;
        this.fiturId = fiturId;
        for(MyVoucherResponseJson response: userVoucherList){
            if(Integer.parseInt(response.getFitur()) == fiturId){
                voucher.add(response);
            }
        }
        for(MyVoucherResponseJson response: userVoucherList){
            if(!(Integer.parseInt(response.getFitur()) == fiturId)){
                voucher.add(response);
            }
        }
        this.userVoucherList = voucher;
    }

    @NonNull
    @Override
    public ViewSelectVoucher onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ViewSelectVoucher(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewSelectVoucher holder, int position) {
        MyVoucherResponseJson voucherModel = userVoucherList.get(position);
        holder.btnSelectVoucher.setEnabled(false);
        holder.btnSelectVoucher.setBackgroundTintList(mActivity.getResources().getColorStateList(R.color.white));
        holder.btnSelectVoucher.setBackground(mActivity.getResources().getDrawable(R.drawable.button_round_1));
        holder.btnSelectVoucher.setTextColor(mActivity.getResources().getColor(R.color.black));
        holder.tvFiturType.setBackground(mActivity.getResources().getDrawable(R.drawable.button_round_1));
        holder.tvFiturType.setBackgroundTintList(mActivity.getResources().getColorStateList(R.color.white));
        holder.tvVoucherQuantity.setBackgroundTintList(mActivity.getResources().getColorStateList(R.color.white));
        holder.tvVoucherQuantity.setBackground(mActivity.getResources().getDrawable(R.drawable.button_round_1));

        System.out.println("debugFiturId: " + fiturId);
        System.out.println("debugVoucher: " + Integer.parseInt(voucherModel.getFitur()));
        System.out.println("debugVoucherString: " + voucherModel.getFitur());

        if(fiturId == Integer.parseInt(voucherModel.getFitur())){

            holder.btnSelectVoucher.setEnabled(true);
            holder.btnSelectVoucher.setBackgroundTintList(mActivity.getResources().getColorStateList(R.color.colorPrimary));
            holder.btnSelectVoucher.setBackground(mActivity.getResources().getDrawable(R.drawable.button_round_1));
            holder.btnSelectVoucher.setTextColor(mActivity.getResources().getColor(R.color.white));
            holder.tvFiturType.setBackground(mActivity.getResources().getDrawable(R.drawable.button_round_1));
            holder.tvFiturType.setBackgroundTintList(mActivity.getResources().getColorStateList(R.color.blue_200));
            holder.tvVoucherQuantity.setBackgroundTintList(mActivity.getResources().getColorStateList(R.color.orange_300));
            holder.tvVoucherQuantity.setBackground(mActivity.getResources().getDrawable(R.drawable.button_round_1));
            holder.cardView.setForeground(null);
        }

        switch (voucherModel.getFitur()){
            case "1":
                holder.tvFiturType.setText("ANTRide");
                break;
            case "2":
                holder.tvFiturType.setText("ANTCar");
                break;
            case "3":
                holder.tvFiturType.setText("ANTFood");
                break;
            case "4":
                holder.tvFiturType.setText("ANTSend");
                break;
            case "5":
                holder.tvFiturType.setText("ANTShop");
                break;
            default:
                Toast.makeText(mContext, "You don't have couccher", Toast.LENGTH_LONG).show();
        }

        holder.tvVoucherName.setText(voucherModel.getNamaVoucherPromo());
        holder.tvVoucherQuantity.setText("Isi Voucher: " + String.valueOf(voucherModel.getQuantity()));
        holder.tvExpired.setText(voucherModel.getExpired());
//        holder.tvVoucherNominal.setText("Nominal: " + String.valueOf(voucherModel.getNominalVoucherPromo()));
        Utility.convertLocaleCurrencyTV(holder.tvVoucherNominal, mContext, String.valueOf(voucherModel.getNominalVoucherPromo()), "Nominal: ");

        if(!voucherModel.getImageVoucherPromo().isEmpty()){
            PicassoTrustAll.getInstance(mContext)
                    .load(Constants.IMAGESSLIDER + voucherModel.getImageVoucherPromo())
                    .into(holder.ivVoucherPoster);
        }

        holder.btnSelectVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("voucherNominal", voucherModel.getNominalVoucherPromo());
                intent.putExtra("voucherName", voucherModel.getNamaVoucherPromo());
                mActivity.setResult(Activity.RESULT_OK, intent);
                mActivity.finish();

            }
        });

    }

    @Override
    public int getItemCount() {return (null != userVoucherList ? userVoucherList.size() : 0);}

    public class ViewSelectVoucher extends RecyclerView.ViewHolder{
        TextView tvVoucherName, tvVoucherQuantity, tvExpired, tvVoucherNominal, tvFiturType;
        ImageView ivVoucherPoster;
        Button btnSelectVoucher;
        CardView cardView;

        public ViewSelectVoucher(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
            tvVoucherName = itemView.findViewById(R.id.nama_voucher);
            tvVoucherQuantity = itemView.findViewById(R.id.tv_voucher_quantity);
            tvExpired = itemView.findViewById(R.id.voucher_expired);
            tvVoucherNominal = itemView.findViewById(R.id.tv_nominal_voucher);
            ivVoucherPoster = itemView.findViewById(R.id.image_voucher);
            btnSelectVoucher = itemView.findViewById(R.id.btn_use);
            tvFiturType = itemView.findViewById(R.id.voucher_fitur);
        }
    }

}
