package com.mastek.appengage;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.mastek.appengage.exchandler.ExceptionHandler;
import com.mastek.appengage.login.LoginResponseModel;

/**
 * Created by Pravin103082 on 02-02-2017.
 */

public class App extends Application {

    public static App instance;
    private LoginResponseModel mLoginUser;

    public static final App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
       /* if(getLoginUser()!=null){
            startActivity(new Intent(this,HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                    Intent.FLAG_ACTIVITY_NEW_TASK));
        }*/

        //Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        registerActivityLifecycleCallbacks(new ActivityTracker());
        MA.init(this,"http://52.87.24.173/api/","58a2e82a15913d7d69fef3ed");

    }

    public void setLoginUser(LoginResponseModel loginUser) {
        mLoginUser = loginUser;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.edit().putString("loginUser", new Gson().toJson(loginUser)).apply();

    }


    public LoginResponseModel getLoginUser() {
        if (mLoginUser == null) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            String loginUser = preferences.getString("loginUser", null);
            if (loginUser != null) {
                mLoginUser = new Gson().fromJson(loginUser, LoginResponseModel.class);
            }
        }
        return mLoginUser;
    }

    public void clearLoginUser(){
        SharedPreferences preferences=PreferenceManager.getDefaultSharedPreferences(this);
        preferences.edit().clear().apply();
        mLoginUser=null;
    }
}
