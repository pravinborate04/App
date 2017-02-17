package com.mastek.appengage.dashboard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Pravin103082 on 03-02-2017.
 */

public class DashboardResponseModel
{

    @SerializedName("tse")
    @Expose
    private Integer tse;
    @SerializedName("te")
    @Expose
    private Integer te;
    @SerializedName("tuu")
    @Expose
    private Integer tuu;
    @SerializedName("tnu")
    @Expose
    private Integer tnu;
    @SerializedName("tts")
    @Expose
    private Integer tts;
    @SerializedName("tce")
    @Expose
    private Integer tce;

    public Integer getTse() {
        return tse;
    }

    public void setTse(Integer tse) {
        this.tse = tse;
    }

    public Integer getTe() {
        return te;
    }

    public void setTe(Integer te) {
        this.te = te;
    }

    public Integer getTuu() {
        return tuu;
    }

    public void setTuu(Integer tuu) {
        this.tuu = tuu;
    }

    public Integer getTnu() {
        return tnu;
    }

    public void setTnu(Integer tnu) {
        this.tnu = tnu;
    }

    public Integer getTts() {
        return tts;
    }

    public void setTts(Integer tts) {
        this.tts = tts;
    }

    public Integer getTce() {
        return tce;
    }

    public void setTce(Integer tce) {
        this.tce = tce;
    }

}
