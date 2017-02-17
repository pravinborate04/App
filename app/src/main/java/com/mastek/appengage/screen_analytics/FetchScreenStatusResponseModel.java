package com.mastek.appengage.screen_analytics;

import android.widget.TextView;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Pravin103082 on 13-02-2017.
 */

public class FetchScreenStatusResponseModel implements Serializable
{
    @SerializedName("alt")
    @Expose
    private String alt;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("path")
    @Expose
    private String path;
    @SerializedName("ts")
    @Expose
    private Integer ts;
    @SerializedName("noc")
    @Expose
    private Integer noc;
    @SerializedName("tts")
    @Expose
    private Integer tts;
    @SerializedName("nuu")
    @Expose
    private Integer nuu;

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getTs() {
        return ts;
    }

    public void setTs(Integer ts) {
        this.ts = ts;
    }

    public Integer getNoc() {
        return noc;
    }

    public void setNoc(Integer noc) {
        this.noc = noc;
    }

    public Integer getTts() {
        return tts;
    }

    public void setTts(Integer tts) {
        this.tts = tts;
    }

    public Integer getNuu() {
        return nuu;
    }

    public void setNuu(Integer nuu) {
        this.nuu = nuu;
    }
}
