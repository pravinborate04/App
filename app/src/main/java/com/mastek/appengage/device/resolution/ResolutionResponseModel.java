package com.mastek.appengage.device.resolution;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Pravin103082 on 07-02-2017.
 */

public class ResolutionResponseModel
{
    @SerializedName("resolution")
    @Expose
    private String resolution;
    @SerializedName("users")
    @Expose
    private String users;
    @SerializedName("time")
    @Expose
    private String time;

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
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
