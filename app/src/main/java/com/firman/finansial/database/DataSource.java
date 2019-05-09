package com.firman.finansial.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import com.firman.finansial.Utils;
import com.firman.finansial.model.Kategori;
import com.firman.finansial.model.Keuangan;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by Firman on 4/26/2019.
 */
public class DataSource {
    public static final int TIPE_PEMASUKAN = 0;
    public static final int TIPE_PENGELUARAN = 1;

    private static DataSource instance;
    private final DBHelper dbHelper;
    private SQLiteDatabase db;
    private volatile int openedDbCount = 0;

    private DataSource(Context context) {
        dbHelper = new DBHelper(context);
        if (getKategori(-1).size()==0) {
            insert(new Kategori(-1, "Makanan", TIPE_PENGELUARAN, true));
            insert(new Kategori(-1, "Tagihan", TIPE_PENGELUARAN, true));
            insert(new Kategori(-1, "Rumah", TIPE_PENGELUARAN, true));
            insert(new Kategori(-1, "Hewan Peliharaan", TIPE_PENGELUARAN, true));
            insert(new Kategori(-1, "Kesehatan", TIPE_PENGELUARAN, true));
            insert(new Kategori(-1, "Transportasi", TIPE_PENGELUARAN, true));
            insert(new Kategori(-1, "Hadiah", TIPE_PENGELUARAN, true));
            insert(new Kategori(-1, "Pakaian", TIPE_PENGELUARAN, true));
            insert(new Kategori(-1, "Belanja", TIPE_PENGELUARAN, true));
            insert(new Kategori(-1, "Pajak", TIPE_PENGELUARAN, true));
            insert(new Kategori(-1, "Lainnya", TIPE_PENGELUARAN, true));

            insert(new Kategori(-1, "Gaji", TIPE_PEMASUKAN, true));
            insert(new Kategori(-1, "Bonus", TIPE_PEMASUKAN, true));
            insert(new Kategori(-1, "Tabungan", TIPE_PEMASUKAN, true));
            insert(new Kategori(-1, "Deposito", TIPE_PEMASUKAN, true));
            insert(new Kategori(-1, "Penjualan", TIPE_PEMASUKAN, true));
            insert(new Kategori(-1, "Pengembalian Utang", TIPE_PEMASUKAN, true));
            insert(new Kategori(-1, "Investasi", TIPE_PEMASUKAN, true));
            insert(new Kategori(-1, "Hadiah", TIPE_PEMASUKAN, true));
            insert(new Kategori(-1, "Lainnya", TIPE_PEMASUKAN, true));
        }
    }

    public static DataSource getInstance(Context context) {
        synchronized (DataSource.class) {
            if (instance == null)
                instance = new DataSource(context);
        }
        return instance;
    }

    private synchronized void open() {
        openedDbCount++;
        if (openedDbCount == 1) {
            db = dbHelper.getWritableDatabase();
        }
    }

    private synchronized void close() {
        openedDbCount--;
        if (openedDbCount == 0) {
            db.close();
        }
    }

    public void insert(Keuangan keuangan) {
        ContentValues contentValues = new ContentValues();
        open();
        contentValues.put(TableHolder.KEUANGAN.COLUMN_ID,
                "EN" + (DatabaseUtils.queryNumEntries(db, TableHolder.KEUANGAN.NAME) + 1));
        contentValues.put(TableHolder.KEUANGAN.COLUMN_TIPEKEUANGAN, keuangan.getTipeKeuangan());
        contentValues.put(TableHolder.KEUANGAN.COLUMN_WAKTU,
                Utils.formatDate(keuangan.getWaktu(), "yyyy-MM-dd"));
        contentValues.put(TableHolder.KEUANGAN.COLUMN_JUMLAH, keuangan.getJumlah());
        contentValues.put(TableHolder.KEUANGAN.COLUMN_IDKATEGORI, keuangan.getKategori().getIdKategori());
        contentValues.put(TableHolder.KEUANGAN.COLUMN_DESKRIPSI, keuangan.getDeskripsi());
        db.insert(TableHolder.KEUANGAN.NAME, null, contentValues);
        close();
    }

    public void insert(Kategori kategori) {
        ContentValues contentValues = new ContentValues();
        open();
        contentValues.put(TableHolder.KATEGORI.COLUMN_IDKATEGORI,
                DatabaseUtils.queryNumEntries(db, TableHolder.KATEGORI.NAME) + 1);
        contentValues.put(TableHolder.KATEGORI.COLUMN_NMKATEGORI, kategori.getNmKategori());
        contentValues.put(TableHolder.KATEGORI.COLUMN_TYPEKATEGORI, kategori.getTypeKategori());
        contentValues.put(TableHolder.KATEGORI.COLUMN_STATUS, kategori.isStatus() ? 1 : 0);
        db.insert(TableHolder.KATEGORI.NAME, null, contentValues);
        close();
    }

    public void update(Keuangan keuangan) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TableHolder.KEUANGAN.COLUMN_TIPEKEUANGAN, keuangan.getTipeKeuangan());
        contentValues.put(TableHolder.KEUANGAN.COLUMN_WAKTU,
                Utils.formatDate(keuangan.getWaktu(), "yyyy-MM-dd"));
        contentValues.put(TableHolder.KEUANGAN.COLUMN_JUMLAH, keuangan.getJumlah());
        contentValues.put(TableHolder.KEUANGAN.COLUMN_IDKATEGORI, keuangan.getKategori().getIdKategori());
        contentValues.put(TableHolder.KEUANGAN.COLUMN_DESKRIPSI, keuangan.getDeskripsi());
        contentValues.put(TableHolder.KEUANGAN.COLUMN_DELETE, keuangan.isDelete()?1:0);
        open();
        db.update(TableHolder.KEUANGAN.NAME, contentValues,
                TableHolder.KEUANGAN.COLUMN_ID + "=?", new String[]{keuangan.getId()});
        close();
    }

    public void update(Kategori kategori) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TableHolder.KATEGORI.COLUMN_NMKATEGORI, kategori.getNmKategori());
        contentValues.put(TableHolder.KATEGORI.COLUMN_TYPEKATEGORI, kategori.getTypeKategori());
        contentValues.put(TableHolder.KATEGORI.COLUMN_STATUS, kategori.isStatus() ? 1 : 0);

        open();
        db.update(TableHolder.KATEGORI.NAME, contentValues,
                TableHolder.KATEGORI.COLUMN_IDKATEGORI + "=?",
                new String[]{String.valueOf(kategori.getIdKategori())});
        close();
    }

    public Keuangan getKeuangan(String id) {
        String SQL = "SELECT a.*, b." + TableHolder.KATEGORI.COLUMN_NMKATEGORI +
                " FROM " + TableHolder.KEUANGAN.NAME + " a " +
                " JOIN " + TableHolder.KATEGORI.NAME + " b" +
                " ON a.idKategori=b.idKategori WHERE a.id=?";
        open();
        Cursor cursor = db.rawQuery(SQL, new String[]{id});
        Keuangan keuangan = null;
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            keuangan = new Keuangan();
            keuangan.setId(cursor.getString(
                    cursor.getColumnIndex(TableHolder.KEUANGAN.COLUMN_ID)));
            try {
                keuangan.setWaktu(
                        Utils.parseDate(cursor.getString(
                                cursor.getColumnIndex(TableHolder.KEUANGAN.COLUMN_WAKTU)),
                                "yyyy-MM-dd"));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            keuangan.setTipeKeuangan(cursor.getInt(
                    cursor.getColumnIndex(TableHolder.KEUANGAN.COLUMN_TIPEKEUANGAN)
            ));

            keuangan.setJumlah(cursor.getLong(
                    cursor.getColumnIndex(TableHolder.KEUANGAN.COLUMN_JUMLAH))
            );
            keuangan.setDeskripsi(cursor.getString
                    (cursor.getColumnIndex(TableHolder.KEUANGAN.COLUMN_DESKRIPSI))
            );

            Kategori kategori = new Kategori();
            kategori.setIdKategori(cursor.getInt(
                    cursor.getColumnIndex(TableHolder.KATEGORI.COLUMN_IDKATEGORI))
            );
            kategori.setNmKategori(cursor.getString(
                    cursor.getColumnIndex(TableHolder.KATEGORI.COLUMN_NMKATEGORI))
            );
            keuangan.setKategori(kategori);

        }

        cursor.close();
        close();
        return keuangan;
    }

    public Kategori getKategori(String id) {
        String SQL = "SELECT * FROM " + TableHolder.KATEGORI.NAME + " WHERE idKategori=?";
        open();
        Cursor cursor = db.rawQuery(SQL, new String[]{id});
        Kategori kategori = null;

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            kategori = new Kategori();
            kategori.setIdKategori(cursor.getInt(
                    cursor.getColumnIndex(TableHolder.KATEGORI.COLUMN_IDKATEGORI))
            );
            kategori.setNmKategori(cursor.getString(
                    cursor.getColumnIndex(TableHolder.KATEGORI.COLUMN_NMKATEGORI))
            );
            kategori.setTypeKategori(cursor.getInt(
                    cursor.getColumnIndex(TableHolder.KATEGORI.COLUMN_TYPEKATEGORI)
            ));
            kategori.setStatus(cursor.getInt(
                    cursor.getColumnIndex(TableHolder.KATEGORI.COLUMN_NMKATEGORI)) == 1
            );
        }

        cursor.close();
        close();

        return kategori;
    }

    public List<Keuangan> getKeuangan(Date date, int tipeKeuangan) {
        String SQL = "SELECT a.*, b." + TableHolder.KATEGORI.COLUMN_NMKATEGORI +
                " FROM " + TableHolder.KEUANGAN.NAME + " a " +
                " JOIN " + TableHolder.KATEGORI.NAME + " b" +
                " ON a.idKategori=b.idKategori WHERE a.`delete` = 0 AND a.waktu=? ";

        String[] argsValue;
        if (tipeKeuangan > -1) {
            SQL += "AND a.tipeKeuangan=?";
            argsValue = new String[]{
                    Utils.formatDate(date, "yyyy-MM-dd"),
                    String.valueOf(tipeKeuangan)
            };
        } else {
            argsValue = new String[]{Utils.formatDate(date, "yyyy-MM-dd")};
        }

        open();
        db.beginTransaction();
        List<Keuangan> items = new ArrayList<>();

        try {
            Cursor cursor = db.rawQuery(SQL, argsValue);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                do {
                    Keuangan keuangan = new Keuangan();
                    keuangan.setId(cursor.getString(
                            cursor.getColumnIndex(TableHolder.KEUANGAN.COLUMN_ID))
                    );
                    keuangan.setTipeKeuangan(cursor.getInt(
                            cursor.getColumnIndex(TableHolder.KEUANGAN.COLUMN_TIPEKEUANGAN)
                    ));
                    keuangan.setWaktu(
                            Utils.parseDate(cursor.getString(
                                    cursor.getColumnIndex(TableHolder.KEUANGAN.COLUMN_WAKTU)),
                                    "yyyy-MM-dd")
                    );

                    keuangan.setJumlah(cursor.getLong(
                            cursor.getColumnIndex(TableHolder.KEUANGAN.COLUMN_JUMLAH))
                    );
                    keuangan.setDeskripsi(cursor.getString
                            (cursor.getColumnIndex(TableHolder.KEUANGAN.COLUMN_DESKRIPSI))
                    );

                    Kategori kategori = new Kategori();
                    kategori.setIdKategori(cursor.getInt(
                            cursor.getColumnIndex(TableHolder.KATEGORI.COLUMN_IDKATEGORI))
                    );
                    kategori.setNmKategori(cursor.getString(
                            cursor.getColumnIndex(TableHolder.KATEGORI.COLUMN_NMKATEGORI))
                    );
                    keuangan.setKategori(kategori);

                    items.add(keuangan);
                } while (cursor.moveToNext());
            }

            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        db.endTransaction();
        close();

        return items;
    }

    public List<Kategori> getKategori(int typeKategori) {
        String SQL = "SELECT * FROM " + TableHolder.KATEGORI.NAME;

        String[] argsValue = null;
        if (typeKategori > -1) {
            SQL += " WHERE `status` =? AND typeKategori=?";
            argsValue = new String[]{"1", String.valueOf(typeKategori)};
        }

        open();
        db.beginTransaction();
        List<Kategori> items = new ArrayList<>();

        try {
            Cursor cursor = db.rawQuery(SQL, argsValue);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                do {
                    Kategori kategori = new Kategori();
                    kategori.setIdKategori(cursor.getInt(
                            cursor.getColumnIndex(TableHolder.KATEGORI.COLUMN_IDKATEGORI))
                    );
                    kategori.setNmKategori(cursor.getString(
                            cursor.getColumnIndex(TableHolder.KATEGORI.COLUMN_NMKATEGORI))
                    );
                    kategori.setTypeKategori(cursor.getInt(
                            cursor.getColumnIndex(TableHolder.KATEGORI.COLUMN_TYPEKATEGORI)
                    ));
                    kategori.setStatus(cursor.getInt(
                            cursor.getColumnIndex(TableHolder.KATEGORI.COLUMN_NMKATEGORI)) == 1
                    );

                    items.add(kategori);
                } while (cursor.moveToNext());
            }

            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        db.endTransaction();
        close();

        return items;
    }

}
