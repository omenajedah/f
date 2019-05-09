package com.firman.finansial.model;

import java.util.Date;

/**
 * Created by Firman on 5/4/2019.
 */
public class KeuanganHarian {

    private final Date hari;
    private final long totalPemasukan;
    private final long totalPengeluaran;

    public KeuanganHarian(Date hari, long totalPemasukan, long totalPengeluaran) {
        this.hari = hari;
        this.totalPemasukan = totalPemasukan;
        this.totalPengeluaran = totalPengeluaran;
    }


    public Date getHari() {
        return hari;
    }

    public long getTotalPemasukan() {
        return totalPemasukan;
    }

    public long getTotalPengeluaran() {
        return totalPengeluaran;
    }
}
