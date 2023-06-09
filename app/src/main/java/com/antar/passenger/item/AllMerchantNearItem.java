package com.antar.passenger.item;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.antar.passenger.R;
import com.antar.passenger.activity.DetailMerchantActivity;
import com.antar.passenger.constants.Constants;
import com.antar.passenger.models.MerchantNearModel;
import com.antar.passenger.utils.PicassoTrustAll;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class AllMerchantNearItem extends RecyclerView.Adapter<AllMerchantNearItem.ItemRowHolder> {

    private List<MerchantNearModel> dataList;
    private Context mContext;
    private int rowLayout;

    public AllMerchantNearItem(Context context, List<MerchantNearModel> dataList, int rowLayout) {
        this.dataList = dataList;
        this.mContext = context;
        this.rowLayout = rowLayout;
    }

    @NonNull
    @Override
    public ItemRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ItemRowHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ItemRowHolder holder, final int position) {
        final MerchantNearModel singleItem = dataList.get(position);
        holder.name.setText(singleItem.getNama_merchant());
        if (!singleItem.getFoto_merchant().isEmpty()) {
            PicassoTrustAll.getInstance(mContext)
                    .load(Constants.IMAGESMERCHANT + singleItem.getFoto_merchant())
                    .resize(250, 250)
                    .into(holder.images);
        }

        holder.kategori.setText(singleItem.getCategory_merchant());

        if (singleItem.getStatus_promo().equals("1")) {
            holder.promobadge.setVisibility(View.VISIBLE);
            holder.shimmer.startShimmerAnimation();
        } else {
            holder.promobadge.setVisibility(View.GONE);
            holder.shimmer.stopShimmerAnimation();
        }

        String[] parsedJamBuka = singleItem.getJam_buka().split(":");
        String[] parsedJamTutup = singleItem.getJam_tutup().split(":");

        int jamBuka = Integer.parseInt(parsedJamBuka[0]), menitBuka = Integer.parseInt(parsedJamBuka[1]);
        int jamTutup = Integer.parseInt(parsedJamTutup[0]), menitTutup = Integer.parseInt(parsedJamTutup[1]);

        int totalMenitBuka = (jamBuka * 60) + menitBuka;
        int totalMenitTutup = (jamTutup * 60) + menitTutup;

        Calendar now = Calendar.getInstance();
        int totalMenitNow = (now.get(Calendar.HOUR_OF_DAY) * 60) + now.get(Calendar.MINUTE);
        holder.address.setText(singleItem.getAlamat_merchant());
        float km = Float.parseFloat(singleItem.getDistance());
        String format = String.format(Locale.US, "%.1f", km);

        if (totalMenitNow <= totalMenitTutup && totalMenitNow >= totalMenitBuka) {
            holder.distance.setText(format + "km" + " " + mContext.getString(R.string.text_with_bullet) + " " + "Open");
        } else {
            holder.distance.setText(format + "km" + " " + mContext.getString(R.string.text_with_bullet) + " " + "Closed");
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, DetailMerchantActivity.class);
                i.putExtra("lat", singleItem.getLatitude_merchant());
                i.putExtra("lon", singleItem.getLongitude_merchant());
                i.putExtra("id", singleItem.getId_merchant());
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mContext.startActivity(i);
            }
        });


    }


    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    static class ItemRowHolder extends RecyclerView.ViewHolder {
        TextView name, address, distance, kategori;
        ImageView images;
        ShimmerFrameLayout shimmer;
        FrameLayout promobadge;

        ItemRowHolder(View itemView) {
            super(itemView);
            images = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.nama);
            kategori = itemView.findViewById(R.id.kategori);
            shimmer = itemView.findViewById(R.id.shimreview);
            promobadge = itemView.findViewById(R.id.promobadge);
            address = itemView.findViewById(R.id.alamat);
            distance = itemView.findViewById(R.id.distance);
        }
    }
}
