package com.mastek.appengage.insights;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Pravin103082 on 08-02-2017.
 */

public class InsightSessionResposeModel
{
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("tse")
    @Expose
    private String tse;
    @SerializedName("tts")
    @Expose
    private String tts;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTse() {
        return tse;
    }

    public void setTse(String tse) {
        this.tse = tse;
    }

    public String getTts() {
        return tts;
    }

    public void setTts(String tts) {
        this.tts = tts;
    }


}
