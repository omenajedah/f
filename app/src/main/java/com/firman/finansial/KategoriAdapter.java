package com.firman.finansial;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.firman.finansial.model.Kategori;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Firman on 5/5/2019.
 */
public class KategoriAdapter extends ArrayAdapter<Kategori> {
    private final LayoutInflater layoutInflater;

    public KategoriAdapter(@NonNull Context context, List<Kategori> kategoriList) {
        super(context, android.R.layout.simple_list_item_1, kategoriList);
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null)
            convertView = layoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false);

        ((TextView)convertView).setText(getItem(position).getNmKategori());
        return convertView;
    }


}
