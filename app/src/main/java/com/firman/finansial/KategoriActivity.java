package com.firman.finansial;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.firman.finansial.database.DataSource;
import com.firman.finansial.model.Kategori;
import com.google.android.material.switchmaterial.SwitchMaterial;

/**
 * Created by Firman on 5/2/2019.
 */
public class KategoriActivity extends AppCompatActivity {

    public static final String KEY_KATEGORI = "idKategori";
    private Spinner typeKategoriSP;
    private EditText namaKategoriET;
    private SwitchMaterial hapusSw;

    private DataSource dataSource;
    private Kategori kategori;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategori);
        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        typeKategoriSP = findViewById(R.id.type_kategori);
        namaKategoriET = findViewById(R.id.nmKategori);
        hapusSw = findViewById(R.id.status);

        dataSource = DataSource.getInstance(this);

        if (getIntent().hasExtra(KEY_KATEGORI)) {
            initUI();
        }
    }

    private void initUI() {
        kategori = dataSource.getKategori(String.valueOf(getIntent().getIntExtra(KEY_KATEGORI, 0)));
        typeKategoriSP.setSelection(kategori.getTypeKategori());
        namaKategoriET.setText(kategori.getNmKategori());
        hapusSw.setChecked(kategori.isStatus());
    }

    public void onSimpanClick(View view) {
        if (kategori == null) {
            kategori = new Kategori(-1, namaKategoriET.getText().toString(),
                    typeKategoriSP.getSelectedItemPosition(), hapusSw.isChecked());
            dataSource.insert(kategori);
            finish();
        } else {
            kategori.setTypeKategori(typeKategoriSP.getSelectedItemPosition());
            kategori.setNmKategori(namaKategoriET.getText().toString());
            kategori.setStatus(hapusSw.isChecked());
            dataSource.update(kategori);
            finish();
        }
        Toast.makeText(this, "Data Tersimpan", Toast.LENGTH_SHORT).show();

    }
}
