package com.mastek.appengage.events;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.borax12.materialdaterangepicker.date.DatePickerDialog;
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
import com.mastek.appengage.MA;
import com.mastek.appengage.R;
import com.mastek.appengage.RetrofitManager;
import com.mastek.appengage.Tools;
import com.mastek.appengage.WebServices;
import com.mastek.appengage.cities.CustomMarkerView;
import com.mastek.appengage.device.DeviceFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventSummaryFragment extends Fragment implements DatePickerDialog.OnDateSetListener{
    LinearLayout linEventSummary;
    WebServices webServices;
    TextView btnDateRange1;
    ImageView imgCalender1;
    String sd;
    String ed;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event_summary, container, false);
    }
    @Override
    public void onStart() {
        super.onStart();

        MA.startFragmentScreen(EventSummaryFragment.class.getName());

    }


    @Override
    public void onStop() {
        super.onStop();

        MA.endFragmentScreen(EventSummaryFragment.class.getName());

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        linEventSummary=(LinearLayout) view.findViewById(R.id.linEventSummary);
        btnDateRange1=(TextView)view.findViewById(R.id.btnDateRangeEvents1);
        imgCalender1=(ImageView)view.findViewById(R.id.imgCalenderEvents1);

        Calendar now = Calendar.getInstance();
        Calendar after=Calendar.getInstance();
        after.add(Calendar.MONTH,-1);
        SimpleDateFormat format = new SimpleDateFormat("MMMM d, yyyy");

        String formatedDate=""+format.format(after.getTime())+" - "+""+format.format(now.getTime());
        btnDateRange1.setText(formatedDate);

        final DatePickerDialog dpd = DatePickerDialog.newInstance(
                EventSummaryFragment.this,
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
        getData(sd,ed);

    }


    public void getData(String sd,String ed){
        webServices= RetrofitManager.getInstance().call();

        Tools.showProgress(getContext());
        webServices.getEventNames(sd,ed, App.getInstance().getLoginUser().getAkey()).enqueue(new Callback<List<EventSummaryResponseModel>>() {
            @Override
            public void onResponse(Call<List<EventSummaryResponseModel>> call, Response<List<EventSummaryResponseModel>> response) {
                Tools.hideProgress();
                if(response.body()!=null && response.body().size()>0){
                    Log.e("response",new Gson().toJson(response.body()));
                    setupUi(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<EventSummaryResponseModel>> call, Throwable t) {
                Tools.hideProgress();
            }
        });
    }


    public void setupUi(List<EventSummaryResponseModel> eventSummaryResponseModels) {
        for(int i=0; i<eventSummaryResponseModels.size() ;i++){
            View summaryView=getActivity().getLayoutInflater().inflate(R.layout.layout_event_summary_single_row,null);
            final TextView txtSingleRowEventName= (TextView) summaryView.findViewById(R.id.txtSingleRowEventName);
            TextView singleRowEventCount=(TextView)summaryView.findViewById(R.id.singleRowEventCount);
            txtSingleRowEventName.setText(eventSummaryResponseModels.get(i).getKey());
            singleRowEventCount.setText(eventSummaryResponseModels.get(i).getTotalEventCount()+"");
            linEventSummary.addView(summaryView);
            txtSingleRowEventName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("Text",txtSingleRowEventName.getText().toString());

                    final LineChart mChart,mChart1;
                    Dialog dialog = new Dialog(getActivity());
                    dialog.setContentView(R.layout.event_summary_event_line_chart);
                    mChart = (LineChart) dialog.findViewById(R.id.chartEventUser);
                    mChart1=(LineChart)dialog.findViewById(R.id.chartEventSession);
                    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                    Window window = dialog.getWindow();
                    lp.copyFrom(window.getAttributes());
//This makes the dialog take up the full width
                    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                    lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    window.setAttributes(lp);

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

                    Tools.showProgress(getContext());
                    webServices.getEventsummary(sd,ed,
                            App.getInstance().getLoginUser().getAkey(),txtSingleRowEventName.getText().toString())
                            .enqueue(new Callback<List<GetEventSummaryResponseModel>>() {
                        @Override
                        public void onResponse(Call<List<GetEventSummaryResponseModel>> call, Response<List<GetEventSummaryResponseModel>> response) {
                           Tools.hideProgress();
                            if(response.body()!=null) {
                                Log.e("Response dia", response.body().size() + "");
                                setLineChart(mChart, mChart1, response.body());
                            }
                        }

                        @Override
                        public void onFailure(Call<List<GetEventSummaryResponseModel>> call, Throwable t) {
                            Tools.hideProgress();
                        }
                    });
                    dialog.show();

                }
            });
        }
    }



    public void setLineChart(LineChart mChart,LineChart mChart1,List<GetEventSummaryResponseModel> lineChart) {

        mChart.invalidate();
        mChart1.invalidate();
        ArrayList<Entry> yvalues = new ArrayList<Entry>();
        ArrayList<Entry> yvalues1 = new ArrayList<Entry>();
        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < lineChart.size(); i++) {

            yvalues.add(new Entry(Float.parseFloat(lineChart.get(i).getEventCount()+""), i));


            yvalues1.add(new Entry(Float.parseFloat(lineChart.get(i).getUserCount()+""), i));

            xVals.add(getDate(lineChart.get(i).getEventDate()));
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
        getData(sd,ed);
    }
}
