package com.mastek.appengage.crash;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.gson.Gson;
import com.mastek.appengage.App;
import com.mastek.appengage.MA;
import com.mastek.appengage.R;
import com.mastek.appengage.RetrofitManager;
import com.mastek.appengage.Tools;
import com.mastek.appengage.WebServices;
import com.mastek.appengage.cities.CitiesFragment;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrashPieChartFragment extends Fragment implements DatePickerDialog.OnDateSetListener {


    ImageView imgCalenderCrash1;
    TextView btnDateRangeCrash1;
    String sd;
    String ed;
    WebServices webServices;
    PieChart piechartTotalCrash,piechartCrashByMnu,piechartCrashByPf,piechartCrashByOsv;
    LinearLayout linCrash;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_crash, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        linCrash=(LinearLayout)view.findViewById(R.id.linCrash);
        piechartTotalCrash = (PieChart) view.findViewById(R.id.piechartTotalCrash);
        piechartCrashByMnu=(PieChart)view.findViewById(R.id.piechartCrashByMnu);
        piechartCrashByPf=(PieChart)view.findViewById(R.id.piechartCrashByPf);
        piechartCrashByOsv=(PieChart)view.findViewById(R.id.piechartCrashByOsv);

        piechartTotalCrash.setRotationEnabled(false);
        piechartTotalCrash.getLegend().setWordWrapEnabled(true);

        piechartCrashByMnu.setRotationEnabled(false);
        piechartCrashByMnu.getLegend().setWordWrapEnabled(true);

        piechartCrashByPf.setRotationEnabled(false);
        piechartCrashByPf.getLegend().setWordWrapEnabled(true);

        piechartCrashByOsv.setRotationEnabled(false);
        piechartCrashByOsv.getLegend().setWordWrapEnabled(true);

        imgCalenderCrash1=(ImageView)view.findViewById(R.id.imgCalenderCrash1);
        btnDateRangeCrash1=(TextView)view.findViewById(R.id.btnDateRangeCrash1);
        Calendar now = Calendar.getInstance();
        Calendar after=Calendar.getInstance();
        after.add(Calendar.MONTH,-1);

        SimpleDateFormat format = new SimpleDateFormat("MMMM d, yyyy");

        String formatedDate=""+format.format(after.getTime())+" - "+""+format.format(now.getTime());
        btnDateRangeCrash1.setText(formatedDate);

        final DatePickerDialog dpd = DatePickerDialog.newInstance(
                CrashPieChartFragment.this,
                after.get(Calendar.YEAR),
                after.get(Calendar.MONTH),
                after.get(Calendar.DAY_OF_MONTH),
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setMaxDate(Calendar.getInstance());

        imgCalenderCrash1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
            }
        });
        sd=after.getTimeInMillis()/1000+"";
        ed=now.getTimeInMillis()/1000+"";
        getData(sd,ed);





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
        btnDateRangeCrash1.setText(formatedDate);

        sd=startCalender.getTimeInMillis()/1000+"";
        ed=endCalender.getTimeInMillis()/1000+"";
        getData(sd,ed);
    }

    public void setTotalCrashPieChart(TotalCrashes totalCrashes){
        ArrayList<Entry> yvalues = new ArrayList<Entry>();
        ArrayList<String> xVals = new ArrayList<String>();
        yvalues.add(new Entry(totalCrashes.getTotalCrashes(), 0));
        xVals.add("");
        PieDataSet dataSet = new PieDataSet(yvalues, "");

        dataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return "";
            }
        });

        dataSet.setColors(ColorTemplate.LIBERTY_COLORS);
        PieData data = new PieData(xVals,dataSet);

        piechartTotalCrash.setCenterText(totalCrashes.getTotalCrashes()+"\n" +
                "Total Crashes");
        piechartTotalCrash.setDescription("");
        piechartTotalCrash.setCenterTextSize(15);
        piechartTotalCrash.setData(data);
        piechartTotalCrash.invalidate();

    }



    public void setTotalCrashByMnu(Mnu byMnu){
        ArrayList<Entry> yvalues = new ArrayList<Entry>();
        ArrayList<String> xVals = new ArrayList<String>();

        for(int i=0;i<byMnu.getMnu().size(); i++){
            Map<String,String> map=byMnu.getMnu().get(i);
            Set<String> keys=map.keySet();
            for(String s:keys){
                Log.e("Key "+keys," Value "+map.get(s));
                yvalues.add(new Entry(Float.parseFloat(map.get(s)),i));
                xVals.add(s);
            }
        }

        PieDataSet dataSet = new PieDataSet(yvalues, "");
        dataSet.setColors(ColorTemplate.LIBERTY_COLORS);
        PieData data = new PieData(xVals,dataSet);
        dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        piechartCrashByMnu.setDescription("");
        piechartCrashByMnu.setCenterText("Manufacturer");
        piechartCrashByMnu.setCenterTextSize(15);
        piechartCrashByMnu.setData(data);
        piechartCrashByMnu.invalidate();

    }


    public void setTotalCrashByPf(Pf totalCrashByPf){
        ArrayList<Entry> yvalues = new ArrayList<Entry>();
        ArrayList<String> xVals = new ArrayList<String>();
        for(int i=0;i<totalCrashByPf.getPf().size(); i++){
            Map<String,String> map=totalCrashByPf.getPf().get(i);
            Set<String> keys=map.keySet();
            for(String s:keys){
                Log.e("Key "+keys," Value "+map.get(s));
                yvalues.add(new Entry(Float.parseFloat(map.get(s)),i));
                xVals.add(s);
            }
        }

        PieDataSet dataSet = new PieDataSet(yvalues, "");
        dataSet.setColors(ColorTemplate.LIBERTY_COLORS);
        PieData data = new PieData(xVals,dataSet);
        dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        piechartCrashByPf.setDescription("");
        piechartCrashByPf.setCenterText("Platform");
        piechartCrashByPf.setCenterTextSize(15);
        piechartCrashByPf.setData(data);
        piechartCrashByPf.invalidate();
    }

    public void setTotalCrashByOsv(Osv byOsv){
        ArrayList<Entry> yvalues = new ArrayList<Entry>();
        ArrayList<String> xVals = new ArrayList<String>();
        for(int i=0;i<byOsv.getOsv().size(); i++){
            Map<String,String> map=byOsv.getOsv().get(i);
            Set<String> keys=map.keySet();
            for(String s:keys){
                Log.e("Key "+keys," Value "+map.get(s));
                yvalues.add(new Entry(Float.parseFloat(map.get(s)),i));
                xVals.add(s);
            }
        }

        PieDataSet dataSet = new PieDataSet(yvalues, "");
        dataSet.setColors(ColorTemplate.LIBERTY_COLORS);
        PieData data = new PieData(xVals,dataSet);
        dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        piechartCrashByOsv.setDescription("");
        piechartCrashByOsv.setCenterText("OS Version");
        piechartCrashByOsv.setCenterTextSize(15);
        piechartCrashByOsv.setData(data);
        piechartCrashByOsv.invalidate();
    }


    public void getData(String sd,String ed){
        Tools.showProgress(getActivity());
        webServices= RetrofitManager.getInstance().call();
        webServices.getCrashCounters(sd,ed, App.getInstance().getLoginUser().getAkey()).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Tools.hideProgress();
                if(response.body()!=null){
                    Log.e("Resp Crash",response.body());

                    try {
                        JSONArray jsonArray=new JSONArray(response.body());
                        if(jsonArray.length()>0){
                            if(linCrash.getVisibility()==View.GONE){
                                linCrash.setVisibility(View.VISIBLE);
                            }
                            CrashResponseModel crashResponseModel=new CrashResponseModel();
                            crashResponseModel.setTotalCrashes(new Gson().fromJson(jsonArray.getString(0),TotalCrashes.class));
                            crashResponseModel.setMnu(new Gson().fromJson(jsonArray.getString(1),Mnu.class));
                            crashResponseModel.setOsv(new Gson().fromJson(jsonArray.getString(2),Osv.class));
                            crashResponseModel.setPf(new Gson().fromJson(jsonArray.getString(3),Pf.class));
                            setTotalCrashPieChart(crashResponseModel.getTotalCrashes());
                            setTotalCrashByMnu(crashResponseModel.getMnu());
                            setTotalCrashByPf(crashResponseModel.getPf());
                            setTotalCrashByOsv(crashResponseModel.getOsv());
                        }else {
                            if(linCrash.getVisibility()==View.VISIBLE){
                                linCrash.setVisibility(View.GONE);
                                new AlertDialog.Builder(getActivity())
                                        .setTitle("Crashes")
                                        .setMessage("No Crash Data Found")
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        })
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .show();
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("Error Crash",t.getLocalizedMessage());
                Tools.hideProgress();
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();

        MA.startFragmentScreen(CrashPieChartFragment.class.getName());

    }


    @Override
    public void onStop() {
        super.onStop();

        MA.endFragmentScreen(CrashPieChartFragment.class.getName());

    }

}
