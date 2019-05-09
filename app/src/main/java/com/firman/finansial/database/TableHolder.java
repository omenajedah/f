package com.firman.finansial.database;

/**
 * Created by Firman on 5/5/2019.
 */
public class TableHolder {

    public interface KEUANGAN {
        String NAME = "tbl_keuangan";

        String COLUMN_ID = "id";
        String COLUMN_TIPEKEUANGAN = "tipeKeuangan";
        String COLUMN_WAKTU = "waktu";
        String COLUMN_JUMLAH = "jumlah";
        String COLUMN_IDKATEGORI = "idKategori";
        String COLUMN_DESKRIPSI = "deskripsi";
        String COLUMN_DELETE = "`delete`";

        String SQL_CREATE = "CREATE TABLE " + NAME + " (" +
                COLUMN_ID + " CHAR(10) NOT NULL PRIMARY KEY," +
                COLUMN_TIPEKEUANGAN +" TINYINT(1)," +
                COLUMN_WAKTU + " DATE," + COLUMN_JUMLAH + " INT," +
                COLUMN_IDKATEGORI + " INT," + COLUMN_DESKRIPSI + " TEXT," +
                COLUMN_DELETE + " TINYINT(1) DEFAULT 0);";
    }

    public interface KATEGORI {
        String NAME = "tbl_kategori";

        String COLUMN_IDKATEGORI = "idKategori";
        String COLUMN_NMKATEGORI = "nmKategori";
        String COLUMN_TYPEKATEGORI = "typeKategori";
        String COLUMN_STATUS = "`status`";

        String SQL_CREATE = "CREATE TABLE " + NAME + " (" +
                COLUMN_IDKATEGORI + " INT NOT NULL PRIMARY KEY," +
                COLUMN_NMKATEGORI + " VARCHAR(50)," +
                COLUMN_TYPEKATEGORI + " TINYINT(1)," +
                COLUMN_STATUS + " TINYINT(1));";
    }

}
