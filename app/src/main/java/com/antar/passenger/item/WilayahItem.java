package com.antar.passenger.item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.antar.passenger.R;
import com.antar.passenger.mine.wilayahinterface;
import com.antar.passenger.models.Cabang;

import java.util.List;


public class WilayahItem extends RecyclerView.Adapter<WilayahItem.ViewHolder>  {

    private Context context;
    private List<Cabang> cabang;
    private wilayahinterface winter;

    public WilayahItem(Context context, List<Cabang> cabang, wilayahinterface winter) {
        this.context = context;
        this.cabang = cabang;
        this.winter = winter;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_wilayah, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.namacabang.setText(cabang.get(position).getNama_cabang());
        holder.rootlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                winter.updatewilayah(cabang.get(position).getId(), cabang.get(position).getNama_cabang());
            }
        });

    }

    @Override
    public int getItemCount() {
        return cabang.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView namacabang;
        private LinearLayout rootlayout;

        public ViewHolder(View itemView) {
            super(itemView);
            namacabang = itemView.findViewById(R.id.itemwilayah);
            rootlayout = itemView.findViewById(R.id.rootLayout);

        }
    }

}

