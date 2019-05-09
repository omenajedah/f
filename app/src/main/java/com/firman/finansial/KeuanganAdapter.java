package com.firman.finansial;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.firman.finansial.database.DataSource;
import com.firman.finansial.model.Keuangan;

import java.util.List;

import static com.firman.finansial.KeuanganActivity.KEY_KEUANGAN;

/**
 * Created by Firman on 5/4/2019.
 */
public class KeuanganAdapter extends RecyclerView.Adapter<KeuanganAdapter.KeuanganViewHolder> {

    private static final String TAG = KeuanganAdapter.class.getSimpleName();

    private final List<Keuangan> dataItem;
    private LayoutInflater inflater;

    public KeuanganAdapter(List<Keuangan> dataItem) {
        this.dataItem = dataItem;
    }

    @NonNull
    @Override
    public KeuanganAdapter.KeuanganViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (inflater == null)
            inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_keuangan, parent, false);

        return new KeuanganViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KeuanganAdapter.KeuanganViewHolder holder, int position) {
        Keuangan keuangan = dataItem.get(position);
        Log.d(TAG, "onBindViewHolder: "+keuangan.toString());

        if (keuangan.getTipeKeuangan() == DataSource.TIPE_PENGELUARAN) {
            holder.imageView.setColorFilter(
                    ContextCompat.getColor(inflater.getContext(), R.color.colorPengeluaran), PorterDuff.Mode.SRC_IN);
            holder.imageView.setRotationX(180f);
            holder.imageView.setRotationY(180f);
            holder.jumlah.setTextColor(ContextCompat.getColor(inflater.getContext(), R.color.colorPengeluaran));
        } else {
            holder.imageView.setRotationX(180f);
            holder.imageView.setColorFilter(
                    ContextCompat.getColor(inflater.getContext(), R.color.colorPemasukan), PorterDuff.Mode.SRC_IN);
            holder.jumlah.setTextColor(ContextCompat.getColor(inflater.getContext(), R.color.colorPemasukan));
        }
        holder.view.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), KeuanganActivity.class);
            intent.putExtra(KEY_KEUANGAN, keuangan.getId());
            v.getContext().startActivity(intent);
        });

        String deskripsi = keuangan.getDeskripsi().trim();
        holder.kategori.setText(keuangan.getKategori().getNmKategori());
        holder.jumlah.setText(Utils.formatIDR(keuangan.getJumlah()));
        holder.deskripsi.setText(TextUtils.isEmpty(deskripsi) ? "-" : deskripsi);
    }

    @Override
    public int getItemCount() {
        return dataItem.size();
    }

    class KeuanganViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView kategori;
        private TextView jumlah;
        private TextView deskripsi;
        private View view;

        public KeuanganViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            kategori = itemView.findViewById(R.id.kategori);
            jumlah = itemView.findViewById(R.id.jumlah);
            deskripsi = itemView.findViewById(R.id.deskripsi);
            view = itemView.findViewById(R.id.root_list);
        }
    }
}
