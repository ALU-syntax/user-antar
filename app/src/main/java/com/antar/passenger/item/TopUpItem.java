package com.antar.passenger.item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.antar.passenger.R;
import com.antar.passenger.models.ayopulsa.PriceListDetailModel;
import com.antar.passenger.utils.Utility;

import java.util.ArrayList;
import java.util.List;

public class TopUpItem extends RecyclerView.Adapter<TopUpItem.ItemRowHolder> implements Filterable  {
    private List<PriceListDetailModel> detailList;
    private List<PriceListDetailModel> filteredDetailList;
    private onTopUpItemClicked onClickListener;

    public TopUpItem(List<PriceListDetailModel> data, onTopUpItemClicked listener) {
        this.detailList = data;
        this.filteredDetailList = data;
        this.onClickListener = listener;
    }

    @NonNull
    @Override
    public TopUpItem.ItemRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemRowHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_top_up, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TopUpItem.ItemRowHolder holder, int position) {
        final PriceListDetailModel model = filteredDetailList.get(position);
        String[] s = model.getText().split("-");
        holder.tvTitle.setText(s[0]);
        holder.tvTitle.setTextColor(getColor(holder.itemView.getContext(),
                model.isStatus()));
        Utility.currencyTXT(holder.tvPrice, String.valueOf(model.getPrice() + 600), holder.itemView.getContext());
        holder.itemView.setOnClickListener(v -> {
            if (model.isStatus()) {
                onClickListener.onClick(model);
            } else {
                Toast.makeText(holder.itemView.getContext(), "This product is not active", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private int getColor(Context context, boolean isActive) {
        return ContextCompat.getColor(context, isActive ? R.color.black:R.color.quantum_grey500);
    }

    @Override
    public int getItemCount() {
        return filteredDetailList != null && !filteredDetailList.isEmpty() ? filteredDetailList.size() : 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filteredDetailList = detailList;
                } else {
                    List<PriceListDetailModel> filteredList = new ArrayList<>();
                    for (PriceListDetailModel row : filteredDetailList) {

                        if (row.getText().toLowerCase().contains(charString.toLowerCase())
                                || row.getText().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    filteredDetailList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredDetailList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredDetailList = (ArrayList<PriceListDetailModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    static class ItemRowHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvPrice;
        public ItemRowHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitleTopUp);
            tvPrice = itemView.findViewById(R.id.tvPriceTopUp);
        }
    }

    public static interface onTopUpItemClicked {
        void onClick(PriceListDetailModel model);
    }
}
