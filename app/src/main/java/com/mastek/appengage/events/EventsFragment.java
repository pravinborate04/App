package com.mastek.appengage.events;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.mastek.appengage.R;
import com.mastek.appengage.device.DevicesPagerAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class EventsFragment extends Fragment implements DatePickerDialog.OnDateSetListener{

    TextView btnDateRange1;
    ImageView imgCalender1;
    String sd;
    String ed;
    ViewPager viewPagerEvents;
    TabLayout tabEvents;
    List<Fragment> fragmentList;
    List<String> tabs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_events, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnDateRange1=(TextView)view.findViewById(R.id.btnDateRangeEvents1);
        imgCalender1=(ImageView)view.findViewById(R.id.imgCalenderEvents1);

        viewPagerEvents=(ViewPager)view.findViewById(R.id.viewPagerEvents);
        tabEvents=(TabLayout)view.findViewById(R.id.tabEvents);


        fragmentList=new ArrayList<>();

        EventSummaryFragment eventSummaryFragment=new EventSummaryFragment();
        fragmentList.add(eventSummaryFragment);

        EventCompareFragment eventCompareFragment=new EventCompareFragment();
        fragmentList.add(eventCompareFragment);

        Calendar now = Calendar.getInstance();
        Calendar after=Calendar.getInstance();
        after.add(Calendar.MONTH,-1);

        tabs=new ArrayList<>();
        tabs.add("Summary");
        tabs.add("Compare");


        viewPagerEvents.setAdapter(new DevicesPagerAdapter(getChildFragmentManager(),fragmentList,tabs));
        viewPagerEvents.setOffscreenPageLimit(tabs.size()-1);
        tabEvents.setupWithViewPager(viewPagerEvents);
        SimpleDateFormat format = new SimpleDateFormat("MMMM d, yyyy");

        String formatedDate=""+format.format(after.getTime())+" - "+""+format.format(now.getTime());
        btnDateRange1.setText(formatedDate);

        final DatePickerDialog dpd = DatePickerDialog.newInstance(
                EventsFragment.this,
                after.get(Calendar.YEAR),
                after.get(Calendar.MONTH),
                after.get(Calendar.DAY_OF_MONTH),
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );


        imgCalender1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
            }
        });

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {

    }
}
