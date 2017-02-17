package com.mastek.appengage.device;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
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
import com.mastek.appengage.MA;
import com.mastek.appengage.R;
import com.mastek.appengage.crash.CrashPieChartFragment;
import com.mastek.appengage.dashboard.DeviceTypeAdapter;
import com.mastek.appengage.device.appversions.AppVersionsFragment;
import com.mastek.appengage.device.manufacturer.ManufacturerFragment;
import com.mastek.appengage.device.model.ModelFragment;
import com.mastek.appengage.device.osversions.OSVersionsFragment;
import com.mastek.appengage.device.platform.PlatformFragment;
import com.mastek.appengage.device.resolution.ResolutionsFragment;
import com.mastek.appengage.device.type.TypeFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DeviceFragment extends Fragment implements DatePickerDialog.OnDateSetListener{

    TabLayout tabDevices;
    ViewPager viewPagerDevices;
    List<Fragment> fragmentList;
    List<String> tabs;
    TextView btnDateRange1;
    ImageView imgCalender1;
    AppCompatSpinner spnrDeviceType1;
    DeviceTypeAndDateRangeListener listener,modelListener,typeListener,
            platformListener,OsVersionListener,appVersionListener,resolutionListener;
    List<String> data;

    String sd;
    String ed;
    String type;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_device, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnDateRange1=(TextView)view.findViewById(R.id.btnDateRange1);
        imgCalender1=(ImageView)view.findViewById(R.id.imgCalender1);
        spnrDeviceType1=(AppCompatSpinner)view.findViewById(R.id.spnrDeviceType1);

        Calendar now = Calendar.getInstance();
        Calendar after=Calendar.getInstance();
        after.add(Calendar.MONTH,-1);

        SimpleDateFormat format = new SimpleDateFormat("MMMM d, yyyy");

        String formatedDate=""+format.format(after.getTime())+" - "+""+format.format(now.getTime());
        btnDateRange1.setText(formatedDate);

        final DatePickerDialog dpd = DatePickerDialog.newInstance(
                DeviceFragment.this,
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


        data=new ArrayList<>();
        data.add("All");
        data.add("SmartPhone");
        data.add("Tablet");

        spnrDeviceType1.setAdapter(new DeviceTypeAdapter(getActivity(),data));



        tabDevices=(TabLayout)view.findViewById(R.id.tabDevices);
        viewPagerDevices=(ViewPager)view.findViewById(R.id.viewPagerDevices);
        fragmentList=new ArrayList<>();

        ManufacturerFragment manufacturerFragment=new ManufacturerFragment();
        listener=(DeviceTypeAndDateRangeListener) manufacturerFragment;
        fragmentList.add(manufacturerFragment);

        ModelFragment modelFragment=new ModelFragment();
        modelListener=(DeviceTypeAndDateRangeListener)modelFragment;
        fragmentList.add(modelFragment);

        final TypeFragment typeFragment=new TypeFragment();
        typeListener=(DeviceTypeAndDateRangeListener)typeFragment;
        fragmentList.add(typeFragment);

        PlatformFragment platformFragment=new PlatformFragment();
        platformListener=(DeviceTypeAndDateRangeListener) platformFragment;
        fragmentList.add(platformFragment);

        OSVersionsFragment osVersionsFragment=new OSVersionsFragment();
        OsVersionListener=(DeviceTypeAndDateRangeListener)osVersionsFragment;
        fragmentList.add(osVersionsFragment);

        AppVersionsFragment appVersionsFragment=new AppVersionsFragment();
        appVersionListener=(DeviceTypeAndDateRangeListener)appVersionsFragment;
        fragmentList.add(appVersionsFragment);

        ResolutionsFragment resolutionsFragment=new ResolutionsFragment();
        resolutionListener=(DeviceTypeAndDateRangeListener)resolutionsFragment;
        fragmentList.add(resolutionsFragment);

        tabs=new ArrayList<>();
        tabs.add("Manufacturer");
        tabs.add("Model");
        tabs.add("Type");
        tabs.add("Platform");
        tabs.add("OS Versions");
        tabs.add("App Versions");
        tabs.add("Resolutions");

        viewPagerDevices.setAdapter(new DevicesPagerAdapter(getChildFragmentManager(),fragmentList,tabs));
        viewPagerDevices.setOffscreenPageLimit(tabs.size()-1);
        tabDevices.setupWithViewPager(viewPagerDevices);
        spnrDeviceType1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LinearLayout linearLayout=((LinearLayout) view);
                TextView textView=(TextView)linearLayout.getChildAt(0);
                textView.setTextColor(Color.parseColor("#FFFFFF"));
                if("All".equals(data.get(position))){
                    type="A";
                    listener.deviceTypeChange(sd,ed,type);
                    modelListener.deviceTypeChange(sd,ed,type);
                    typeListener.deviceTypeChange(sd,ed,type);
                    platformListener.deviceTypeChange(sd,ed,type);
                    OsVersionListener.deviceTypeChange(sd,ed,type);
                    appVersionListener.deviceTypeChange(sd,ed,type);
                    resolutionListener.deviceTypeChange(sd,ed,type);
                }else if("SmartPhone".equals(data.get(position))){
                    type="S";
                    listener.deviceTypeChange(sd,ed,type);
                    modelListener.deviceTypeChange(sd,ed,type);
                    typeListener.deviceTypeChange(sd,ed,type);
                    platformListener.deviceTypeChange(sd,ed,type);
                    OsVersionListener.deviceTypeChange(sd,ed,type);
                    appVersionListener.deviceTypeChange(sd,ed,type);
                    resolutionListener.deviceTypeChange(sd,ed,type);

                }else if("Tablet".equals(data.get(position))){
                    type="T";
                    listener.deviceTypeChange(sd,ed,type);
                    modelListener.deviceTypeChange(sd,ed,type);
                    typeListener.deviceTypeChange(sd,ed,type);
                    platformListener.deviceTypeChange(sd,ed,type);
                    OsVersionListener.deviceTypeChange(sd,ed,type);
                    appVersionListener.deviceTypeChange(sd,ed,type);
                    resolutionListener.deviceTypeChange(sd,ed,type);
                }
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
        listener.deviceTypeChange(sd,ed,type);
        modelListener.deviceTypeChange(sd,ed,type);
        typeListener.deviceTypeChange(sd,ed,type);
        platformListener.deviceTypeChange(sd,ed,type);
        OsVersionListener.deviceTypeChange(sd,ed,type);
        appVersionListener.deviceTypeChange(sd,ed,type);
        resolutionListener.deviceTypeChange(sd,ed,type);
    }


    public interface DeviceTypeAndDateRangeListener {

        public void deviceTypeChange(String sd,String ed,String type);
    }


    @Override
    public void onStart() {
        super.onStart();

        MA.startFragmentScreen(DeviceFragment.class.getName());

    }


    @Override
    public void onStop() {
        super.onStop();

        MA.endFragmentScreen(DeviceFragment.class.getName());

    }

}
