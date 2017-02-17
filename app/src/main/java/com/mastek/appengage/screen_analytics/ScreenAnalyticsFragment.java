package com.mastek.appengage.screen_analytics;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.google.gson.Gson;
import com.mastek.appengage.App;
import com.mastek.appengage.MA;
import com.mastek.appengage.R;
import com.mastek.appengage.RetrofitManager;
import com.mastek.appengage.Tools;
import com.mastek.appengage.WebServices;
import com.mastek.appengage.dashboard.DeviceTypeAdapter;
import com.mastek.appengage.device.DeviceFragment;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScreenAnalyticsFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    TextView btnDateRange1;
    ImageView imgCalender1;
    AppCompatSpinner spnrDeviceType1,spnrDevicePlatformSA1;

    String sd;
    String ed;
    String type;
    String platform;
    List<String> dataType,dataPlatform;
    WebServices webServices;
    ViewPager viewPagerScreens;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_screen_analytics, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dataType=new ArrayList<>();
        dataType.add("SmartPhone");
        dataType.add("Tablet");

        dataPlatform=new ArrayList<>();
        dataPlatform.add("Android");
        dataPlatform.add("iOS");

        viewPagerScreens=(ViewPager)view.findViewById(R.id.viewPagerScreens);
        viewPagerScreens.setPageTransformer(true, new ZoomOutPageTransformer());

        btnDateRange1=(TextView)view.findViewById(R.id.btnDateRangeSA1);
        imgCalender1=(ImageView)view.findViewById(R.id.imgCalenderSA1);
        spnrDeviceType1=(AppCompatSpinner)view.findViewById(R.id.spnrDeviceTypeSA1);
        spnrDevicePlatformSA1=(AppCompatSpinner)view.findViewById(R.id.spnrDevicePlatformSA1);


        Calendar now = Calendar.getInstance();
        Calendar after=Calendar.getInstance();
        after.add(Calendar.MONTH,-1);

        SimpleDateFormat format = new SimpleDateFormat("MMMM d, yyyy");

        String formatedDate=""+format.format(after.getTime())+" - "+""+format.format(now.getTime());
        btnDateRange1.setText(formatedDate);

        final DatePickerDialog dpd = DatePickerDialog.newInstance(
                ScreenAnalyticsFragment.this,
                after.get(Calendar.YEAR),
                after.get(Calendar.MONTH),
                after.get(Calendar.DAY_OF_MONTH),
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );

        dpd.setMaxDate(Calendar.getInstance());

        imgCalender1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
            }
        });
        sd=after.getTimeInMillis()/1000+"";
        ed=now.getTimeInMillis()/1000+"";


        spnrDeviceType1.setAdapter(new DeviceTypeAdapter(getActivity(),dataType));

        spnrDeviceType1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LinearLayout linearLayout=((LinearLayout) view);
                TextView textView=(TextView)linearLayout.getChildAt(0);
                textView.setTextColor(Color.parseColor("#FFFFFF"));
                Log.e("type",dataType.get(position));
                type=dataType.get(position);
                if(dataType.get(position).equals("SmartPhone")){
                    type="Mobile";
                }else {
                    type="Tablet";
                }
                getData(sd,ed,platform,type);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnrDevicePlatformSA1.setAdapter(new DeviceTypeAdapter(getActivity(),dataPlatform));
        spnrDevicePlatformSA1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LinearLayout linearLayout=((LinearLayout) view);
                TextView textView=(TextView)linearLayout.getChildAt(0);
                textView.setTextColor(Color.parseColor("#FFFFFF"));
                Log.e("dataPlatform ",dataPlatform.get(position));
                platform=dataPlatform.get(position);
                getData(sd,ed,platform,type);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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
        btnDateRange1.setText(formatedDate);
        sd=startCalender.getTimeInMillis()/1000+"";
        ed=endCalender.getTimeInMillis()/1000+"";

        getData(sd,ed,platform,type);

    }


    public void getData(String sd, String ed, String platform, String Type){

        if(sd==null || ed==null || platform==null || Type==null ){
            return;
        }
        Tools.showProgress(getActivity());
        Log.e("Req","Sd "+sd+" ed "+ed+" platform "+platform+" Type "+Type);
       webServices= RetrofitManager.getInstance().call();
        webServices.fetchScreenStats(sd,ed, App.getInstance().getLoginUser().getAkey(),platform,Type).enqueue(new Callback<List<FetchScreenStatusResponseModel>>() {
            @Override
            public void onResponse(Call<List<FetchScreenStatusResponseModel>> call, Response<List<FetchScreenStatusResponseModel>> response) {
                Tools.hideProgress();
                if(response.body()!=null){
                    Log.e("resp",new Gson().toJson(response.body()));
                    List<Fragment> fragments=new ArrayList<Fragment>();
                    for(int i=0;i<response.body().size();i++){
                        SingleScreenFragment singleScreenFragment=new SingleScreenFragment();
                        Bundle bundle=new Bundle();
                        bundle.putSerializable("singleScreen", (Serializable) response.body().get(i));
                        bundle.putString("path",response.body().get(i).getPath());
                        bundle.putString("name",response.body().get(i).getName());
                        singleScreenFragment.setArguments(bundle);
                        fragments.add(singleScreenFragment);
                    }
                    viewPagerScreens.setOffscreenPageLimit(fragments.size()-1);
                    viewPagerScreens.setAdapter(new ScreensPagerAdapter(getChildFragmentManager(),fragments));
                }
            }

            @Override
            public void onFailure(Call<List<FetchScreenStatusResponseModel>> call, Throwable t) {
                Log.e("eroor",t.getLocalizedMessage());
                Tools.hideProgress();
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();

        MA.startFragmentScreen(ScreenAnalyticsFragment.class.getName());

    }


    @Override
    public void onStop() {
        super.onStop();

        MA.endFragmentScreen(ScreenAnalyticsFragment.class.getName());

    }
}
