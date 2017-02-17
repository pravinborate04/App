package com.mastek.appengage.device.osversions;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.gson.Gson;
import com.mastek.appengage.App;
import com.mastek.appengage.Constants;
import com.mastek.appengage.R;
import com.mastek.appengage.RetrofitManager;
import com.mastek.appengage.Tools;
import com.mastek.appengage.WebServices;
import com.mastek.appengage.device.DeviceFragment;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OSVersionsFragment extends Fragment implements DeviceFragment.DeviceTypeAndDateRangeListener {

    WebServices webServices;
    PieChart pieChart, piechart1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_osversions, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pieChart = (PieChart) view.findViewById(R.id.piechartOsVersion);
        piechart1 = (PieChart) view.findViewById(R.id.piechartOsVersion1);
        pieChart.setRotationEnabled(false);
        piechart1.setRotationEnabled(false);
    }


    public void setPieChart(List<OsVersionResponseModel> pieChartModels) {
        ArrayList<Entry> yvalues = new ArrayList<Entry>();
        ArrayList<Entry> yvalues1 = new ArrayList<Entry>();
        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < pieChartModels.size(); i++) {
            if (Float.parseFloat(pieChartModels.get(i).getUsers()) != 0.0) {
                yvalues.add(new Entry(Float.parseFloat(pieChartModels.get(i).getUsers()), i));
            }
            if (Float.parseFloat(pieChartModels.get(i).getTime()) != 0.0) {
                yvalues1.add(new Entry(Float.parseFloat(pieChartModels.get(i).getTime()), i));
            }
            xVals.add(pieChartModels.get(i).getOsversion());
        }

        PieDataSet dataSet = new PieDataSet(yvalues, "");
        PieDataSet dataSet1 = new PieDataSet(yvalues1, "");


        dataSet.setColors(ColorTemplate.LIBERTY_COLORS);
        Log.e("Default Size", dataSet.getValueTextSize() + "");
        DisplayMetrics metrics = getActivity().getResources().getDisplayMetrics();
        final float val = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 4, metrics);

        Log.e("val", val + "");
        dataSet.setValueTextSize(val);
        dataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return "" + (int) value;
            }
        });
        dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        PieData data = new PieData(xVals, dataSet);
        pieChart.setData(data);
        pieChart.setDescription("");
        pieChart.setCenterText(generateCenterSpannableText());
        pieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        //   dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        //------------------------------------------------------------
        dataSet1.setColors(ColorTemplate.LIBERTY_COLORS);
        dataSet1.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        Log.e("Default Size", dataSet1.getValueTextSize() + "");
        DisplayMetrics metrics1 = getActivity().getResources().getDisplayMetrics();
        float val1 = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 4, metrics1);

        Log.e("val", val1 + "");
        dataSet1.setValueTextSize(val);
        dataSet1.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return getDurationString((int) value);
            }
        });
        PieData data1 = new PieData(xVals, dataSet1);

        piechart1.setData(data1);
        piechart1.setDescription("");
        piechart1.setCenterText("Time");
        piechart1.animateY(1400, Easing.EasingOption.EaseInOutQuad);


    }

    private SpannableString generateCenterSpannableText() {

        SpannableString s = new SpannableString("Users");
        return s;
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

    @Override
    public void deviceTypeChange(String sd, String ed, String type) {
        Log.e("OSVERSIONS", "sd " + sd + " ed " + ed + " type " + type);
        Tools.showProgress(getActivity());
        webServices = RetrofitManager.getInstance().call();
        webServices.getDeviceCounters(Constants.SEARCH_BY_OS_VERSION, sd, ed, type, App.getInstance().getLoginUser().getAkey()).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.body() != null) {
                    Log.e("resp OS", response.body());
                    Tools.hideProgress();

                    List<OsVersionResponseModel> OsVersionResponseModelList = new ArrayList<OsVersionResponseModel>();
                    try {
                        JSONArray jsonArray = new JSONArray(response.body());
                        Log.e("Size", jsonArray.length() + "");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            OsVersionResponseModel osVersionResponseModel;
                            osVersionResponseModel = new Gson().fromJson(jsonArray.getString(i), OsVersionResponseModel.class);
                            OsVersionResponseModelList.add(osVersionResponseModel);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.e("Act size", OsVersionResponseModelList.size() + "");
                    setPieChart(OsVersionResponseModelList);
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Tools.hideProgress();

            }
        });
    }
}
