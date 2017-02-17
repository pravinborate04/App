package com.mastek.appengage.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Pravin103082 on 02-02-2017.
 */

public class LoginResponseModel {

    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("akey")
    @Expose
    private String akey;
    @SerializedName("login")
    @Expose
    private String login;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAkey() {
        return akey;
    }

    public void setAkey(String akey) {
        this.akey = akey;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
