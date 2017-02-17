package com.mastek.appengage.dashboard;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.google.gson.Gson;
import com.mastek.appengage.App;
import com.mastek.appengage.R;
import com.mastek.appengage.RetrofitManager;
import com.mastek.appengage.Tools;
import com.mastek.appengage.WebServices;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DashboardFragment extends Fragment implements AdapterView.OnItemSelectedListener,DatePickerDialog.OnDateSetListener {

    AppCompatSpinner spnrDeviceType;
    List<String> data;
    TextView btnDateRange,txtTotalSession,txtTotalEvents,txtTotalUsers,txtNewUsers,txtTotalTimeSpend,txtTotalCrashes;
    WebServices webServices;
    String sd;
    String ed;
    String type;
    ImageView imgCalender;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        webServices= RetrofitManager.getInstance().call();

        spnrDeviceType= (AppCompatSpinner) view.findViewById(R.id.spnrDeviceType);
        btnDateRange=(TextView)view.findViewById(R.id.btnDateRange);
        txtTotalSession=(TextView)view.findViewById(R.id.txtTotalSession);
        txtTotalEvents=(TextView)view.findViewById(R.id.txtTotalEvents);
        txtTotalUsers=(TextView)view.findViewById(R.id.txtTotalUsers);
        txtNewUsers=(TextView)view.findViewById(R.id.txtNewUsers);
        txtTotalTimeSpend=(TextView)view.findViewById(R.id.txtTotalTimeSpend) ;
        txtTotalCrashes=(TextView)view.findViewById(R.id.txtTotalCrashes);
        imgCalender=(ImageView)view.findViewById(R.id.imgCalender);


        data=new ArrayList<>();
        data.add("All");
        data.add("SmartPhone");
        data.add("Tablet");
      //  spnrDeviceType.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, data));


        Calendar now = Calendar.getInstance();
        Calendar after=Calendar.getInstance();
        after.add(Calendar.MONTH,-1);

        SimpleDateFormat format = new SimpleDateFormat("MMMM d, yyyy");

        String formatedDate=""+format.format(after.getTime())+" - "+""+format.format(now.getTime());
        btnDateRange.setText(formatedDate);

        final DatePickerDialog dpd = DatePickerDialog.newInstance(
                DashboardFragment.this,
                after.get(Calendar.YEAR),
                after.get(Calendar.MONTH),
                after.get(Calendar.DAY_OF_MONTH),
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );


        imgCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
            }
        });
        sd=after.getTimeInMillis()/1000+"";
        ed=now.getTimeInMillis()/1000+"";

        spnrDeviceType.setAdapter(new DeviceTypeAdapter(getActivity(),data));
        spnrDeviceType.setOnItemSelectedListener(this);

        Log.e("sd",sd);
        Log.e("sd",ed);
        Log.e("Akey", App.getInstance().getLoginUser().getAkey());


       /* webServices.getDashBoardCounters(sd,ed,"A",App.getInstance().getLoginUser().getAkey()).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    JSONArray jsonArray=new JSONArray(response.body());
                    DashboardResponseModel responseModel=new Gson().fromJson(jsonArray.getString(0),DashboardResponseModel.class);
                    Log.e("resda",responseModel.getTe()+"");
                    txtTotalSession.setText(responseModel.getTse()+"");
                    txtTotalEvents.setText(responseModel.getTe()+"");
                    txtTotalUsers.setText(responseModel.getTuu()+"");
                    txtNewUsers.setText(responseModel.getTnu()+"");
                    txtTotalTimeSpend.setText(getDurationString(responseModel.getTts()));
                    txtTotalCrashes.setText(responseModel.getTce()+"");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("Eroor",t.getLocalizedMessage());

            }
        });*/

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.e("check",data.get(position));

        if("All".equals(data.get(position))){
            type="A";
            getData(sd,ed,type);
        }else if("SmartPhone".equals(data.get(position))){
            type="S";
            getData(sd,ed,type);
        }else if("Tablet".equals(data.get(position))){
            type="T";
            getData(sd,ed,type);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {
        Calendar startCalender=Calendar.getInstance();
        startCalender.set(Calendar.YEAR,year);
        startCalender.set(Calendar.MONTH,monthOfYear);
        startCalender.set(Calendar.DAY_OF_MONTH,dayOfMonth);

        Calendar endCalender=Calendar.getInstance();
        endCalender.set(Calendar.YEAR,yearEnd);
        endCalender.set(Calendar.MONTH,monthOfYearEnd);
        endCalender.set(Calendar.DAY_OF_MONTH,dayOfMonthEnd);


        SimpleDateFormat format = new SimpleDateFormat("MMMM d, yyyy");


        String formatedDate=""+format.format(startCalender.getTime())+" - "+""+format.format(endCalender.getTime());


        String date=startCalender.get(Calendar.DAY_OF_MONTH)+"-"+(startCalender.get(Calendar.MONTH)+1)+"-"+startCalender.get(Calendar.YEAR)+"\n" +
                "to "+endCalender.get(Calendar.DAY_OF_MONTH)+"-"+(endCalender.get(Calendar.MONTH)+1)+"-"+endCalender.get(Calendar.YEAR);
        Log.e("date",formatedDate);
        btnDateRange.setText(formatedDate);

        sd=startCalender.getTimeInMillis()/1000+"";
        ed=endCalender.getTimeInMillis()/1000+"";

        getData(sd,ed,type);

    }

    private String getDurationString(int seconds) {

        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        seconds = seconds % 60;

        return twoDigitString(hours) + " : " + twoDigitString(minutes) + " : " + twoDigitString(seconds);
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



    private void getData(String sd,String ed,String type){
        Log.e("Requesst","sd "+sd+" ed"+ed+" Type"+type);
        Tools.showProgress(getContext());
        webServices.getDashBoardCounters(sd,ed,type,App.getInstance().getLoginUser().getAkey()).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
               Tools.hideProgress();
                if(response.body()==null){
                   return;
               }
                Log.e("Response",response.body());
                try {
                    JSONArray jsonArray=new JSONArray(response.body());
                    if(jsonArray.length()>0){
                        DashboardResponseModel responseModel=new Gson().fromJson(jsonArray.getString(0),DashboardResponseModel.class);
                        Log.e("resda",responseModel.getTe()+"");
                        txtTotalSession.setText(responseModel.getTse()+"");
                        txtTotalEvents.setText(responseModel.getTe()+"");
                        txtTotalUsers.setText(responseModel.getTuu()+"");
                        txtNewUsers.setText(responseModel.getTnu()+"");
                        txtTotalTimeSpend.setText(getDurationString(responseModel.getTts()));
                        txtTotalCrashes.setText(responseModel.getTce()+"");
                    }else {
                        txtTotalSession.setText("0");
                        txtTotalEvents.setText("0");
                        txtTotalUsers.setText("0");
                        txtNewUsers.setText("0");
                        txtTotalTimeSpend.setText(getDurationString(0));
                        txtTotalCrashes.setText("0");

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("Eroor",t.getLocalizedMessage());
                Tools.hideProgress();
            }
        });

    }








}
