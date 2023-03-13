package com.insoftbumdesku.passenger.item;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.insoftbumdesku.passenger.R;
import com.insoftbumdesku.passenger.constants.Constants;
import com.insoftbumdesku.passenger.models.BankModel;
import com.insoftbumdesku.passenger.utils.PicassoTrustAll;

import java.util.List;
import java.util.Objects;

import static android.content.Context.CLIPBOARD_SERVICE;

public class BankItem extends RecyclerView.Adapter<BankItem.ItemRowHolder> {

    private List<BankModel> dataList;
    private Context mContext;
    private int rowLayout;

    public BankItem(Context context, List<BankModel> dataList, int rowLayout) {
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

    @Override
    public void onBindViewHolder(@NonNull final ItemRowHolder holder, final int position) {
        final BankModel singleItem = dataList.get(position);
        PicassoTrustAll.getInstance(mContext)
                .load(Constants.IMAGESBANK + singleItem.getImage_bank())
                .resize(250, 250)
                .into(holder.images);

        holder.namabank.setText(singleItem.getNama_bank());
        holder.namapemilik.setText(singleItem.getNama_pemilik());
        holder.rekening.setText(singleItem.getRekening_bank());


        if (position % 2 == 1) {
            holder.background.setBackgroundColor(mContext.getResources().getColor(R.color.backgroundgray));
            //  holder.imageView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        } else {
            holder.background.setBackgroundColor(mContext.getResources().getColor(R.color.white));
            //  holder.imageView.setBackgroundColor(Color.parseColor("#FFFAF8FD"));
        }

        holder.rekening.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ObsoleteSdkInt")
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Disalin!", Toast.LENGTH_SHORT).show();
                if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
                    android.text.ClipboardManager clipboard = (android.text.ClipboardManager) mContext.getSystemService(CLIPBOARD_SERVICE);
                    Objects.requireNonNull(clipboard).setText(singleItem.getRekening_bank());
                } else {
                    android.content.ClipboardManager clipboard = (android.content.ClipboardManager) mContext.getSystemService(CLIPBOARD_SERVICE);
                    android.content.ClipData clip = android.content.ClipData.newPlainText("Disalin!", singleItem.getRekening_bank());
                    Objects.requireNonNull(clipboard).setPrimaryClip(clip);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    static class ItemRowHolder extends RecyclerView.ViewHolder {
        TextView namabank, namapemilik;
        ImageView images;
        Button rekening;
        RelativeLayout background;

        ItemRowHolder(View itemView) {
            super(itemView);
            background = itemView.findViewById(R.id.rootLayout);
            namabank = itemView.findViewById(R.id.namabank);
            namapemilik = itemView.findViewById(R.id.namapemilik);
            rekening = itemView.findViewById(R.id.norekening);
            images = itemView.findViewById(R.id.images);
        }
    }
}
