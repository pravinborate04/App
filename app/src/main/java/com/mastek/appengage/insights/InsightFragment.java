package com.mastek.appengage.insights;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.mastek.appengage.MA;
import com.mastek.appengage.R;
import com.mastek.appengage.device.DevicesPagerAdapter;
import com.mastek.appengage.events.EventSummaryFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class InsightFragment extends Fragment  implements DatePickerDialog.OnDateSetListener{

    ImageView imgCalenderInsight1;
    TextView btnDateRangeInsight1;
    ViewPager viewPagerInsights;
    TabLayout tabInsights;
    List<Fragment> fragmentList;
    List<String> tabs;
    InsightDateChangeListener dateChangeUserListener,dateChangeSessionListener;

    String sd;
    String ed;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_insight, container, false);
    }
    @Override
    public void onStart() {
        super.onStart();
        MA.startFragmentScreen(InsightFragment.class.getName());
    }


    @Override
    public void onStop() {
        super.onStop();
        MA.endFragmentScreen(InsightFragment.class.getName());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        fragmentList=new ArrayList<>();

        tabs=new ArrayList<>();
        tabs.add("Users");
        tabs.add("Session");

        InsightUsersFragment insightUsersFragment=new InsightUsersFragment();
        dateChangeUserListener=(InsightDateChangeListener)insightUsersFragment;
        fragmentList.add(insightUsersFragment);

        InsightSessionFragment insightSessionFragment=new InsightSessionFragment();
        dateChangeSessionListener=(InsightDateChangeListener)insightSessionFragment;
        fragmentList.add(insightSessionFragment);

        imgCalenderInsight1=(ImageView)view.findViewById(R.id.imgCalenderInsight1);
        btnDateRangeInsight1=(TextView)view.findViewById(R.id.btnDateRangeInsight1);
        viewPagerInsights=(ViewPager)view.findViewById(R.id.viewPagerInsights) ;
        tabInsights=(TabLayout)view.findViewById(R.id.tabInsights);


        Calendar now = Calendar.getInstance();
        Calendar after=Calendar.getInstance();
        after.add(Calendar.MONTH,-1);

        SimpleDateFormat format = new SimpleDateFormat("MMMM d, yyyy");

        String formatedDate=""+format.format(after.getTime())+" - "+""+format.format(now.getTime());
        btnDateRangeInsight1.setText(formatedDate);

        final DatePickerDialog dpd = DatePickerDialog.newInstance(
                InsightFragment.this,
                after.get(Calendar.YEAR),
                after.get(Calendar.MONTH),
                after.get(Calendar.DAY_OF_MONTH),
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setMaxDate(Calendar.getInstance());


        imgCalenderInsight1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
            }
        });
        sd=after.getTimeInMillis()/1000+"";
        ed=now.getTimeInMillis()/1000+"";


        viewPagerInsights.setAdapter(new DevicesPagerAdapter(getChildFragmentManager(),fragmentList,tabs));
        viewPagerInsights.setOffscreenPageLimit(tabs.size()-1);
        tabInsights.setupWithViewPager(viewPagerInsights);
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
        btnDateRangeInsight1.setText(formatedDate);

        sd=startCalender.getTimeInMillis()/1000+"";
        ed=endCalender.getTimeInMillis()/1000+"";

        dateChangeUserListener.dateChanged(sd,ed);
        dateChangeSessionListener.dateChanged(sd,ed);
    }

    public interface InsightDateChangeListener
    {
        public void dateChanged(String sd,String ed);
    }

}
