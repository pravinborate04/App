package com.mastek.appengage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Pravin103082 on 10-02-2017.
 */

public class CohortsResponseModel
{
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("users")
    @Expose
    private List<String> users = null;
    @SerializedName("values")
    @Expose
    private List<String> values = null;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }
}
