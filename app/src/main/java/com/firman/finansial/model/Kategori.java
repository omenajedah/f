package com.firman.finansial.model;

/**
 * Created by Firman on 5/4/2019.
 */
public class Kategori {

    private int idKategori;
    private String nmKategori;
    private int typeKategori;
    private boolean status;


    public Kategori() {
    }

    public Kategori(int idKategori, String nmKategori, int typeKategori, boolean status) {
        this.idKategori = idKategori;
        this.nmKategori = nmKategori;
        this.typeKategori = typeKategori;
        this.status = status;
    }

    public int getIdKategori() {
        return idKategori;
    }

    public void setIdKategori(int idKategori) {
        this.idKategori = idKategori;
    }

    public String getNmKategori() {
        return nmKategori;
    }

    public void setNmKategori(String nmKategori) {
        this.nmKategori = nmKategori;
    }

    public int getTypeKategori() {
        return typeKategori;
    }

    public void setTypeKategori(int typeKategori) {
        this.typeKategori = typeKategori;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Kategori{" +
                "idKategori=" + idKategori +
                ", nmKategori='" + nmKategori + '\'' +
                ", typeKategori=" + typeKategori +
                ", status=" + status +
                '}';
    }
}
