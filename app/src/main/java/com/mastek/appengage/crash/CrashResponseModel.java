package com.mastek.appengage.crash;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

/**
 * Created by Pravin103082 on 13-02-2017.
 */

public class CrashResponseModel {


    @SerializedName("TotalCrashes")
    @Expose
    private TotalCrashes totalCrashes;
    @SerializedName("mnu")
    @Expose
    private Mnu mnu;
    @SerializedName("osv")
    @Expose
    private Osv osv;
    @SerializedName("pf")
    @Expose
    private Pf pf;

    public TotalCrashes getTotalCrashes() {
        return totalCrashes;
    }

    public void setTotalCrashes(TotalCrashes totalCrashes) {
        this.totalCrashes = totalCrashes;
    }

    public Mnu getMnu() {
        return mnu;
    }

    public void setMnu(Mnu mnu) {
        this.mnu = mnu;
    }

    public Osv getOsv() {
        return osv;
    }

    public void setOsv(Osv osv) {
        this.osv = osv;
    }

    public Pf getPf() {
        return pf;
    }

    public void setPf(Pf pf) {
        this.pf = pf;
    }
}
