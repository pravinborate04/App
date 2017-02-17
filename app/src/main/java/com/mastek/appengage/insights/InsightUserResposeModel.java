package com.mastek.appengage.insights;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Pravin103082 on 08-02-2017.
 */

public class InsightUserResposeModel
{
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("tuu")
    @Expose
    private String tuu;
    @SerializedName("tts")
    @Expose
    private String tts;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTuu() {
        return tuu;
    }

    public void setTuu(String tuu) {
        this.tuu = tuu;
    }

    public String getTts() {
        return tts;
    }

    public void setTts(String tts) {
        this.tts = tts;
    }

}
