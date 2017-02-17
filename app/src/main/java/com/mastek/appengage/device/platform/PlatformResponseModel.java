package com.mastek.appengage.device.platform;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Pravin103082 on 07-02-2017.
 */

public class PlatformResponseModel
{
    @SerializedName("platform")
    @Expose
    private String platform;
    @SerializedName("users")
    @Expose
    private String users;
    @SerializedName("time")
    @Expose
    private String time;

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
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
