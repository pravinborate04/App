package com.mastek.appengage.insights;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.gson.Gson;
import com.mastek.appengage.App;
import com.mastek.appengage.R;
import com.mastek.appengage.RetrofitManager;
import com.mastek.appengage.Tools;
import com.mastek.appengage.WebServices;
import com.mastek.appengage.cities.CustomMarkerView;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InsightUsersFragment extends Fragment implements InsightFragment.InsightDateChangeListener {


    private LineChart mChart,mChart1;
    WebServices webServices;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_insight_users, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mChart = (LineChart) view.findViewById(R.id.chartInsightsUser);
        mChart1=(LineChart)view.findViewById(R.id.chartInsightsUser1);

        mChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        mChart.setVisibleXRange(1,10);
        mChart.getAxisLeft().setDrawGridLines(false);
        mChart.getXAxis().setDrawGridLines(false);
        mChart.animateXY(1400,1400);

        mChart1.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        mChart1.setVisibleXRange(1,10);
        mChart1.getAxisLeft().setDrawGridLines(false);
        mChart1.getXAxis().setDrawGridLines(false);
        mChart1.animateXY(1400,1400);
        mChart1.getAxisLeft().removeAllLimitLines();
        mChart1.getAxisRight().removeAllLimitLines();

        CustomMarkerView mv = new CustomMarkerView(getActivity(), R.layout.layout_marker_view);

// set the marker to the chart
        mChart1.setMarkerView(mv);
        Calendar now = Calendar.getInstance();
        Calendar after = Calendar.getInstance();
        after.add(Calendar.MONTH, -1);

        SimpleDateFormat format = new SimpleDateFormat("MMMM d, yyyy");

        String formatedDate = "" + format.format(after.getTime()) + " - " + "" + format.format(now.getTime());
        //btnDateRangeLoc1.setText(formatedDate);

        String sd = after.getTimeInMillis() / 1000 + "";
        String ed = now.getTimeInMillis() / 1000 + "";
        dateChanged(sd, ed);
    }

    public void setLineChart(List<InsightUserResposeModel> lineChart) {

        mChart.invalidate();
        mChart1.invalidate();
        ArrayList<Entry> yvalues = new ArrayList<Entry>();
        ArrayList<Entry> yvalues1 = new ArrayList<Entry>();
        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < lineChart.size(); i++) {

            yvalues.add(new Entry(Float.parseFloat(lineChart.get(i).getTuu()), i));


            yvalues1.add(new Entry(Float.parseFloat(lineChart.get(i).getTts()), i));

            xVals.add(getDate(lineChart.get(i).getDate()));
        }

        LineDataSet dataSet = new LineDataSet(yvalues, "Users");
        dataSet.setLineWidth(5);
        dataSet.setValueTextSize(12);

        dataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return ""+(int)value;
            }
        });
        LineData lineData = new LineData(xVals, dataSet);
        mChart.setData(lineData);


        LineDataSet dataSet1=new LineDataSet(yvalues1,"Time Spend");
        dataSet1.setLineWidth(5);
        dataSet1.setValueTextSize(12);
        dataSet1.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return "";
            }
        });

        LineData lineData1 = new LineData(xVals, dataSet1);
        mChart1.setData(lineData1);
       mChart1.getAxisLeft().setValueFormatter(new YAxisValueFormatter() {
           @Override
           public String getFormattedValue(float value, YAxis yAxis) {
               return getHoursFromSec(value);
           }
       });

        mChart1.getAxisRight().setValueFormatter(new YAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, YAxis yAxis) {
                return getHoursFromSec(value);
            }
        });
    }

    @Override
    public void dateChanged(String sd, String ed) {
        webServices = RetrofitManager.getInstance().call();
        Tools.showProgress(getActivity());
        webServices.getUserInsights(sd, ed, App.getInstance().getLoginUser().getAkey()).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Tools.hideProgress();
                if (response.body() != null) {
                    Log.e("resp", response.body());
                    JSONArray jsonArray = null;
                    List<InsightUserResposeModel> userResposeModels = new ArrayList<InsightUserResposeModel>();

                    try {
                        jsonArray = new JSONArray(response.body());
                        Log.e("Size", jsonArray.length() + "");
                        if (jsonArray.length() > 0) {
                            for (int i = 0; i < jsonArray.length(); i++) {

                                InsightUserResposeModel insightUserResposeModel;
                                insightUserResposeModel = new Gson().fromJson(jsonArray.getString(i), InsightUserResposeModel.class);
                                userResposeModels.add(insightUserResposeModel);
                            }


                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.e("Act size", userResposeModels.size() + "");
                    setLineChart(userResposeModels);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Tools.hideProgress();
            }
        });
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


    public String getDate(String v){
       SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMdd");
        Date date1=null;
        try {
            date1=simpleDateFormat.parse(v);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat postFormatter=new SimpleDateFormat("MMM dd");
        String newdate=postFormatter.format(date1);

        return newdate;
    }


    public String getHoursFromSec(float s){
       // int seconds=Integer.parseInt(s);
        int seconds=(int)s;
        int hours = seconds / 3600;

        return hours+" Hrs";
    }
}
