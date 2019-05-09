package com.firman.finansial;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firman.finansial.database.DataSource;
import com.firman.finansial.model.Keuangan;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView waktu;
    private Calendar calendar = Calendar.getInstance();
    private KeuanganAdapter adapter;
    private List<Keuangan> keuanganList = new ArrayList<>();

    private DataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.toolbar));
        waktu = findViewById(R.id.waktu);
        RecyclerView recyclerView = findViewById(R.id.listData);
        dataSource = DataSource.getInstance(this);
        adapter = new KeuanganAdapter(keuanganList);

        waktu.setText(Utils.formatDate(calendar.getTime(), "dd MMM yyyy"));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    public void onDateClick(View view) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                waktu.setText(Utils.formatDate(calendar.getTime(), "dd MMM yyyy"));
                getData();
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    public void onAddClick(View view) {
        Intent intent = new Intent(this, KeuanganActivity.class);
        startActivity(intent);
    }


    public void getData() {
        keuanganList.clear();
        keuanganList.addAll(dataSource.getKeuangan(calendar.getTime(), -1));
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_kategori) {
            startActivity(new Intent(this, DaftarKategoriActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
