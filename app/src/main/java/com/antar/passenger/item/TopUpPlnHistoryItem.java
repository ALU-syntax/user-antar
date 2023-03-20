package com.antar.passenger.item;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.antar.passenger.R;
import com.antar.passenger.models.TopUpPlnHistoryModel;
import com.antar.passenger.utils.Utility;

import java.util.ArrayList;
import java.util.List;

public class TopUpPlnHistoryItem extends RecyclerView.Adapter<TopUpPlnHistoryItem.ViewHolder> {

    List<TopUpPlnHistoryModel> topUpRequestResponseList = new ArrayList<>();

    private onRecyclerViewOnClicked itemClicked;

    public TopUpPlnHistoryItem(List<TopUpPlnHistoryModel> data, onRecyclerViewOnClicked onItemClicked) {
        this.topUpRequestResponseList.addAll(data);
        this.itemClicked = onItemClicked;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topup_request, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final TopUpPlnHistoryModel topUp = topUpRequestResponseList.get(position);
        holder.titleTag.setText(topUp.getDestinationNumber());
        holder.priceTag.setText(Utility.convertCurrency(String.valueOf(topUp.getPrice()), holder.itemView.getContext()));
        holder.itemView.setOnClickListener(v -> itemClicked.onClick(topUp));
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

    public interface onRecyclerViewOnClicked {
        void onClick(TopUpPlnHistoryModel model);
    }
}
