package com.mastek.appengage;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Pravin103082 on 03-02-2017.
 */

public class Tools
{
   public static ProgressDialog progressDialog;


    public static boolean isNetConnected(Context context) {
        final ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        final NetworkInfo netInfo = mConnectivityManager.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }


    public static void showProgress(Context context){

        if(((Activity) context).isFinishing())
        {
            return;
        }
        if(progressDialog==null){
            progressDialog=new ProgressDialog(context);
            progressDialog.setMessage("Please Wait");
            progressDialog.setTitle("AppEngage");
        }

        if(!((Activity) context).isFinishing())
        {
            //show dialog
            if(((Activity)context)!=null)
            progressDialog.show();
        }

    }
    public static void hideProgress(){

        if(progressDialog!=null){
            if(progressDialog.isShowing()){
                progressDialog.dismiss();
                progressDialog = null;
            }
        }
    }



}
