package com.mastek.appengage.device.type;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Pravin103082 on 07-02-2017.
 */

public class TypeResponseModel
{
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("users")
    @Expose
    private String users;
    @SerializedName("time")
    @Expose
    private String time;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
