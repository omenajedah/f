package com.firman.finansial;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.firman.finansial.database.DataSource;
import com.firman.finansial.model.Kategori;
import com.firman.finansial.model.Keuangan;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Firman on 5/2/2019.
 */
public class KeuanganActivity extends AppCompatActivity {

    public static final String KEY_KEUANGAN = "KEY_KEUANGAN";
    private static final String TAG = KeuanganActivity.class.getSimpleName();

    private Keuangan keuangan;

    private TextView waktuTV;
    private Calendar calendar = Calendar.getInstance();
    private List<Kategori> kategoriList = new ArrayList<>();
    private ArrayAdapter<Kategori> kategoriAdapter;

    private EditText jumlahET;
    private EditText deskripsiET;
    private Spinner tipeKeuanganSP;
    private Spinner kategoriSP;

    private DataSource dataSource;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keuangan);
        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        jumlahET = findViewById(R.id.jumlah);
        deskripsiET = findViewById(R.id.deskripsi);
        waktuTV = findViewById(R.id.waktu);
        tipeKeuanganSP = findViewById(R.id.type);
        kategoriSP = findViewById(R.id.kategori);

        jumlahET.setSelection(jumlahET.getText().toString().length());
        dataSource = DataSource.getInstance(this);
        kategoriAdapter = new ArrayAdapter<Kategori>(this, android.R.layout.simple_list_item_1, kategoriList) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                if (convertView == null)
                    convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
                ((TextView)convertView.findViewById(android.R.id.text1)).setText(getItem(position).getNmKategori());
                return convertView;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                if (convertView == null)
                    convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
                ((TextView)convertView.findViewById(android.R.id.text1)).setText(getItem(position).getNmKategori());
                return convertView;
            }
        };

        jumlahET.addTextChangedListener(currencyChangeListener);

        kategoriSP.setAdapter(kategoriAdapter);

        tipeKeuanganSP.setOnItemSelectedListener(itemSelectedListener);

        kategoriList.addAll(dataSource.getKategori(tipeKeuanganSP.getSelectedItemPosition()));
        kategoriAdapter.notifyDataSetChanged();

        waktuTV.setText(Utils.formatDate(calendar.getTime(), "dd MMM yyyy"));

        if (getIntent().hasExtra(KEY_KEUANGAN)) {
            initUI();
        }
    }

    private final TextWatcher currencyChangeListener = new TextWatcher() {
        String current = "";
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(!s.toString().equals(current)){
                jumlahET.removeTextChangedListener(this);

                String cleanString = s.toString().replaceAll("[Rp,.]", "");
                if (cleanString.isEmpty())
                    cleanString = "1000";

                long parsed = Long.parseLong(cleanString);
                if (parsed<0)
                    parsed = 1;
                String formatted = Utils.formatIDR(parsed);

                current = formatted;
                jumlahET.setText(formatted);
                jumlahET.setSelection(formatted.length());

                jumlahET.addTextChangedListener(this);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    public void onDateClick(View view) {

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                waktuTV.setText(Utils.formatDate(calendar.getTime(), "dd MMM yyyy"));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void initUI() {
        keuangan = dataSource.getKeuangan(getIntent().getStringExtra(KEY_KEUANGAN));
        Log.d(TAG, "initUI: "+keuangan.toString());
        findViewById(R.id.delete).setVisibility(View.VISIBLE);

        calendar.setTime(keuangan.getWaktu());
        waktuTV.setText(Utils.formatDate(calendar.getTime(), "dd MMM yyyy"));
        jumlahET.setText(String.valueOf(keuangan.getJumlah()));
        deskripsiET.setText(keuangan.getDeskripsi());
        tipeKeuanganSP.setSelection(keuangan.getTipeKeuangan());
        kategoriSP.setSelection(keuangan.getKategori().getIdKategori()-1);

    }

    public void onDeleteClick(View view) {
        keuangan.setDelete(true);
        dataSource.update(keuangan);
        finish();
        Toast.makeText(this, "Data terhapus", Toast.LENGTH_SHORT).show();
    }

    public void onSimpanClick(View view) {
        if (keuangan == null) {
            String cleanString = jumlahET.getText().toString().replaceAll("[Rp,.]", "");
            if (cleanString.isEmpty())
                cleanString = "1000";
            long jumlah = Long.parseLong(cleanString);

            keuangan = new Keuangan(null, tipeKeuanganSP.getSelectedItemPosition(),
                    calendar.getTime(), jumlah,
                    kategoriList.get(kategoriSP.getSelectedItemPosition()),
                    deskripsiET.getText().toString(), false);
            dataSource.insert(keuangan);
            finish();
        } else {
            String cleanString = jumlahET.getText().toString().replaceAll("[Rp,.]", "");
            if (cleanString.isEmpty())
                cleanString = "1000";
            long jumlah = Long.parseLong(cleanString);

            keuangan.setTipeKeuangan(tipeKeuanganSP.getSelectedItemPosition());
            keuangan.setWaktu(calendar.getTime());
            keuangan.setJumlah(jumlah);
            keuangan.setKategori(kategoriList.get(kategoriSP.getSelectedItemPosition()));
            keuangan.setDeskripsi(deskripsiET.getText().toString());
            dataSource.update(keuangan);
            finish();
        }

        Toast.makeText(this, "Data Tersimpan", Toast.LENGTH_SHORT).show();

    }


    private final AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            kategoriList.clear();
            kategoriList.addAll(dataSource.getKategori(position));
            kategoriAdapter.notifyDataSetChanged();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
}
