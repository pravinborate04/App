package com.mastek.appengage.screen_analytics;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mastek.appengage.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SingleScreenFragment extends Fragment {

    FetchScreenStatusResponseModel responseModel;
    String path;
    String name;
    ImageView imgSingleScreen;
    TextView txtSingleScreenName;
    TextView txtNumberOfUniqueUsers,txtNumberOfCrashes,
            txtTotalTimeSpendS,txtAvgTimeSpndByUser,
            txtTotalSessions,txtAvgTimeSpentPerSession;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_single_screen, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtSingleScreenName=(TextView)view.findViewById(R.id.txtSingleScreenName);
        imgSingleScreen=(ImageView)view.findViewById(R.id.imgSingleScreen);

        txtNumberOfUniqueUsers=(TextView)view.findViewById(R.id.txtNumberOfUniqueUsers);
        txtNumberOfCrashes=(TextView)view.findViewById(R.id.txtNumberOfCrashes);
        txtTotalTimeSpendS=(TextView)view.findViewById(R.id.txtTotalTimeSpendS);
        txtAvgTimeSpndByUser=(TextView)view.findViewById(R.id.txtAvgTimeSpndByUser);
        txtTotalSessions=(TextView)view.findViewById(R.id.txtTotalSessions);
        txtAvgTimeSpentPerSession=(TextView)view.findViewById(R.id.txtAvgTimeSpentPerSession);



        if(getArguments()!=null){
            path=getArguments().getString("path");
            name=getArguments().getString("name");
            responseModel= (FetchScreenStatusResponseModel) getArguments().getSerializable("singleScreen");
        }


        if(responseModel!=null){
            txtNumberOfUniqueUsers.setText(responseModel.getNuu()+"");
            txtNumberOfCrashes.setText(responseModel.getNoc()+"");
            txtTotalTimeSpendS.setText(getDurationString(responseModel.getTts())+"");
            int avgTimeSpendByUser=0;
            if(responseModel.getNuu()!=0){
                avgTimeSpendByUser=responseModel.getTts()/responseModel.getNuu();
            }else {
                avgTimeSpendByUser=0;
            }
            txtAvgTimeSpndByUser.setText(getDurationString(avgTimeSpendByUser)+"");
            txtTotalSessions.setText(responseModel.getTs()+"");

            int avgTimeSpendBySession;
            if(responseModel.getTs()!=0){
                avgTimeSpendBySession=responseModel.getTts()/responseModel.getTs();
            }else {
                avgTimeSpendBySession=0;
            }
            txtAvgTimeSpentPerSession.setText(getDurationString(avgTimeSpendBySession)+"");
        }

        txtSingleScreenName.setText(name);

        if(path!=null){
            new LoadBackgroundImageUrl(imgSingleScreen,true).execute("http://52.206.121.100/"+path);
        }else {
            Log.e("path","null");

        }

    }
    private String getDurationString(int seconds) {

        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        seconds = seconds % 60;

        return twoDigitString(hours) + ":" + twoDigitString(minutes) + ":" + twoDigitString(seconds);
    }

    private String twoDigitString(int number) {

        if (number == 0) {
            return "00";
        }

        if (number / 10 == 0) {
            return "0" + number;
        }

        return String.valueOf(number);
    }

    public class LoadBackgroundImageUrl extends AsyncTask<String, Void, Bitmap> {


        View view;
        boolean isImageViewResource;

        public LoadBackgroundImageUrl(View view, boolean isImageViewResource) {
            this.view = view;
            this.isImageViewResource = isImageViewResource;
        }

        @Override
        protected Bitmap doInBackground(String... params) {

            InputStream in;
            try {
                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                in = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(in);
                return myBitmap;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);

            if (isImageViewResource) {
                ((ImageView) view).setImageBitmap(result);
            } else {
                BitmapDrawable bitmapDrawable = new BitmapDrawable(result);
                this.view.setBackground(bitmapDrawable);
            }

        }
    }
}
