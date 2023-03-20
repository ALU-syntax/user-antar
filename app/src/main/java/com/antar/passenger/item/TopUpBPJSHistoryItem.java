package com.antar.passenger.item;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.antar.passenger.R;
import com.antar.passenger.json.MobilePulsaHealthBPJSResponseModel;
import com.antar.passenger.utils.Utility;
import com.antar.passenger.utils.local_interface.OnBpjsItemClick;

import java.util.ArrayList;
import java.util.List;

public class TopUpBPJSHistoryItem extends RecyclerView.Adapter<TopUpBPJSHistoryItem.ViewHolder>{

    List<MobilePulsaHealthBPJSResponseModel> topUpRequestResponseList = new ArrayList<>();

    private OnBpjsItemClick itemClicked;

    public TopUpBPJSHistoryItem(List<MobilePulsaHealthBPJSResponseModel> data, OnBpjsItemClick onItemClicked) {
        this.topUpRequestResponseList.addAll(data);
        this.itemClicked = onItemClicked;
    }

    @NonNull
    @Override
    public TopUpBPJSHistoryItem.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TopUpBPJSHistoryItem.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topup_request, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull TopUpBPJSHistoryItem.ViewHolder holder, int position) {
        final MobilePulsaHealthBPJSResponseModel topUp = topUpRequestResponseList.get(position);
        holder.titleTag.setText(topUp.getTransactionName());
        holder.priceTag.setText(Utility.convertCurrency(String.valueOf(topUp.getPrice() + 600), holder.itemView.getContext()));
        holder.itemView.setOnClickListener(v -> itemClicked.onItemClick(topUp));
    }

    @Override
    public int getItemCount() {
        return topUpRequestResponseList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView priceTag, titleTag;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            priceTag = itemView.findViewById(R.id.priceTopUp);
            titleTag = itemView.findViewById(R.id.idPel);
        }
    }
}
