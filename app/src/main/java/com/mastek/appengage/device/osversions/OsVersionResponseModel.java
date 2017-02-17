package com.mastek.appengage.device.osversions;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Pravin103082 on 07-02-2017.
 */

public class OsVersionResponseModel
{
    @SerializedName("osversion")
    @Expose
    private String osversion;
    @SerializedName("users")
    @Expose
    private String users;
    @SerializedName("time")
    @Expose
    private String time;

    public String getOsversion() {
        return osversion;
    }

    public void setOsversion(String osversion) {
        this.osversion = osversion;
    }

    public String getUsers() {
        return users;
    }

    public void setUsers(String users) {
        this.users = users;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
