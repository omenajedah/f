package com.firman.finansial;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.firman.finansial.database.DataSource;
import com.firman.finansial.model.Kategori;

import java.util.ArrayList;
import java.util.List;

import static com.firman.finansial.KategoriActivity.KEY_KATEGORI;

/**
 * Created by Firman on 5/2/2019.
 */
public class DaftarKategoriActivity extends AppCompatActivity {

    private static final String TAG = DaftarKategoriActivity.class.getSimpleName();

    private DataSource dataSource;
    private List<Kategori> kategoris = new ArrayList<>();
    private KategoriAdapter adapter;

    private ListView listView;
    private Spinner typeKategoriSpinner;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftarkategori);
        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listView = findViewById(R.id.listView);
        typeKategoriSpinner = findViewById(R.id.type_kategori);

        dataSource = DataSource.getInstance(this);
        adapter = new KategoriAdapter(this, kategoris);

        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    private void initView() {
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(DaftarKategoriActivity.this, KategoriActivity.class);
                intent.putExtra(KEY_KATEGORI, kategoris.get(position).getIdKategori());
                startActivity(intent);
            }
        });
        typeKategoriSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getData(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getData() {
        getData(typeKategoriSpinner.getSelectedItemPosition());
    }

    private void getData(int typeKategori) {
        kategoris.clear();
        kategoris.addAll(dataSource.getKategori(typeKategori));
        adapter.notifyDataSetChanged();
    }

    public void onAddClick(View view) {
        startActivity(new Intent(DaftarKategoriActivity.this, KategoriActivity.class));
    }

}
