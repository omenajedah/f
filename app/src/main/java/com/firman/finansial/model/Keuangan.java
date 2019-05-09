package com.firman.finansial.model;

import java.util.Date;

/**
 * Created by Firman on 5/7/2019.
 */
public class Keuangan {

    private String id;
    private int tipeKeuangan;
    private Date waktu;
    private long jumlah;
    private Kategori kategori;
    private String deskripsi;
    private boolean delete;

    public Keuangan() {
    }

    public Keuangan(String id, int tipeKeuangan, Date waktu, long jumlah, Kategori kategori,
                    String deskripsi, boolean delete) {
        this.id = id;
        this.tipeKeuangan = tipeKeuangan;
        this.waktu = waktu;
        this.jumlah = jumlah;
        this.kategori = kategori;
        this.deskripsi = deskripsi;
        this.delete = delete;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTipeKeuangan() {
        return tipeKeuangan;
    }

    public void setTipeKeuangan(int tipeKeuangan) {
        this.tipeKeuangan = tipeKeuangan;
    }

    public Date getWaktu() {
        return waktu;
    }

    public void setWaktu(Date waktu) {
        this.waktu = waktu;
    }

    public long getJumlah() {
        return jumlah;
    }

    public void setJumlah(long jumlah) {
        this.jumlah = jumlah;
    }

    public Kategori getKategori() {
        return kategori;
    }

    public void setKategori(Kategori kategori) {
        this.kategori = kategori;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    @Override
    public String toString() {
        return "Keuangan{" +
                "id='" + id + '\'' +
                ", tipeKeuangan=" + tipeKeuangan +
                ", waktu=" + waktu +
                ", jumlah=" + jumlah +
                ", kategori=" + kategori +
                ", deskripsi='" + deskripsi + '\'' +
                ", delete=" + delete +
                '}';
    }
}
