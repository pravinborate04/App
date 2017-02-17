package com.mastek.appengage;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.mastek.appengage.dashboard.DeviceTypeAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CohortsFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    TableLayout aTable;
    WebServices webServices;
    List<String> rows;
    List<String> colms;
    LinearLayout linRow, linVertical;
    AppCompatSpinner spnrCohortsType;
    List<String> dataCohorts;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cohorts, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        spnrCohortsType = (AppCompatSpinner) view.findViewById(R.id.spnrCohortsType);
        dataCohorts = new ArrayList<>();
        dataCohorts.add("Daily");
        dataCohorts.add("Weekly");
        dataCohorts.add("Monthly");

        spnrCohortsType.setAdapter(new DeviceTypeAdapter(getActivity(), dataCohorts));
        spnrCohortsType.setOnItemSelectedListener(this);

        linRow = (LinearLayout) view.findViewById(R.id.linRow);
        linVertical = (LinearLayout) view.findViewById(R.id.linVertical);


        rows = new ArrayList<>();
        colms = new ArrayList<>();

        //  appendRows(aTable,amortValues);

    }


    public TextView addTextView(String text, boolean isDate, boolean isheader) {
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

        View view = layoutInflater.inflate(R.layout.single_cohorts_textview, null, false);
        TextView textView = (TextView) view.findViewById(R.id.textView);

        /*TextView textView=new TextView(getActivity());
        textView.setGravity(Gravity.CENTER);

        textView.setText(text);
*/
        if (isDate) {
            Drawable background = textView.getBackground();
            ((GradientDrawable) background).setColor(Color.parseColor("#00ECEFF1"));
            textView.setWidth(340);
            if (!TextUtils.isEmpty(text))
                textView.setText(getDate(text));
        } else {
            Drawable background = textView.getBackground();
            ((GradientDrawable) background).setColor(Color.parseColor("#00ECEFF1"));
            textView.setWidth(150);
            textView.setText(text);

        }
        if (isheader) {
            Drawable background = textView.getBackground();
            ((GradientDrawable) background).setColor(Color.parseColor("#00ECEFF1"));
            textView.setText(text);
            textView.setTypeface(null, Typeface.BOLD);
        }

        if (!isheader && !isDate) {
            checkPercetageUsers(textView, text);
        }
        return textView;
    }


    public String getDate(String v) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date1 = null;
        try {
            date1 = simpleDateFormat.parse(v);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat postFormatter = new SimpleDateFormat("MMM dd, yyyy");
        String newdate = postFormatter.format(date1);

        return newdate;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        LinearLayout linearLayout = ((LinearLayout) view);
        TextView textView = (TextView) linearLayout.getChildAt(0);
        textView.setTextColor(Color.parseColor("#FFFFFF"));
        Log.e("ss", dataCohorts.get(position));
        if (dataCohorts.get(position).equals("Daily")) {
            linVertical.removeAllViews();
            getData("d");
        } else if (dataCohorts.get(position).equals("Weekly")) {
            linVertical.removeAllViews();
            getData("w");
        } else if (dataCohorts.get(position).equals("Monthly")) {
            linVertical.removeAllViews();
            getData("m");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public void getData(String type) {

        if (type.equals("d") || type.equals("w")) {

            LinearLayout headerLinearLayout = new LinearLayout(getActivity());
            headerLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
            headerLinearLayout.addView(addTextView("", true, true));
            for (int i = 0; i < 30; i++) {
                headerLinearLayout.addView(addTextView(i + "", false, true));
            }
            linVertical.addView(headerLinearLayout);
        } else {
            LinearLayout headerLinearLayout = new LinearLayout(getActivity());
            headerLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
            headerLinearLayout.addView(addTextView("", true, true));
            for (int i = 0; i < 13; i++) {
                headerLinearLayout.addView(addTextView(i + "", false, true));
            }
            linVertical.addView(headerLinearLayout);
        }

        webServices = RetrofitManager.getInstance().call();
        Tools.showProgress(getActivity());
        webServices.fetchCohorts(App.getInstance().getLoginUser().getAkey(), type).enqueue(new Callback<List<CohortsResponseModel>>() {
            @Override
            public void onResponse(Call<List<CohortsResponseModel>> call, Response<List<CohortsResponseModel>> response) {
                Tools.hideProgress();
                if (response.body() != null) {
                    Log.e("Resp", response.body().size() + "");

                    for (int i = 0; i < response.body().size(); i++) {
                        rows.add(response.body().get(i).getDate());
                        LinearLayout linearLayout = new LinearLayout(getActivity());
                        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                        linearLayout.addView(addTextView(response.body().get(i).getDate(), true, false));
                        for (int j = 0; j < response.body().get(i).getValues().size(); j++) {
                            //   colms.add(response.body().get(i).getUsers().get(i)+"");
                            linearLayout.addView(addTextView(response.body().get(i).getValues().get(j) + "", false, false));
                        }
                        linVertical.addView(linearLayout);

                    }
                }
            }

            @Override
            public void onFailure(Call<List<CohortsResponseModel>> call, Throwable t) {
                Tools.hideProgress();
                Log.e("check", t.getLocalizedMessage());
            }
        });
    }


    public void checkPercetageUsers(TextView textView, String text) {
        int i = 0;
        try {
            i = Integer.parseInt(text);
        } catch (NumberFormatException e) {
            Double d = Double.parseDouble(text);
            i = d.intValue();
            Log.e("i ", i + "");
        }
        ;

      /*  Drawable oldBackgroud=textView.getBackground();
        Drawable background = textView.getBackground();
        if (background instanceof ShapeDrawable) {
            Log.e("ShapeDrawable", "ShapeDrawable");
            //((ShapeDrawable)background).getPaint().setColor(getResources().getColor(R.color.colorToSet));
        } else if (background instanceof GradientDrawable) {
          //  Log.e("GradientDrawable", "GradientDrawable");
            if (i == 0) {
                ((GradientDrawable) background).setColor(getResources().getColor(R.color.colorPrimary));
            } else if (i == 100) {
                ((GradientDrawable) background).setColor(Color.parseColor("#FF607D8B"));
            }
        } else if (background instanceof ColorDrawable) {
            Log.e("GradientDrawable", "GradientDrawable");

            //  ((ColorDrawable)background).setColor(getResources().getColor(R.color.colorToSet));
        }*/
        if (i >= 90 && i <= 100) {
            textView.setTextColor(Color.parseColor("#FFFFFF"));
            Drawable background = textView.getBackground();
            ((GradientDrawable) background).setColor(getResources().getColor(R.color.colorPrimaryDark));
            //textView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        } else if (i >= 80 && i <= 90) {
            textView.setTextColor(Color.parseColor("#FFFFFF"));
            Drawable background = textView.getBackground();
            ((GradientDrawable) background).setColor(Color.parseColor("#FF37474F"));
            // textView.setBackgroundColor(Color.parseColor("#FF37474F"));
        } else if (i >= 70 && i <= 80) {
            textView.setTextColor(Color.parseColor("#FFFFFF"));
            Drawable background = textView.getBackground();
            ((GradientDrawable) background).setColor(Color.parseColor("#FF455A64"));
            // textView.setBackgroundColor(Color.parseColor("#FF455A64"));
        } else if (i >= 60 && i <= 70) {
            textView.setTextColor(Color.parseColor("#FFFFFF"));
            Drawable background = textView.getBackground();
            ((GradientDrawable) background).setColor(Color.parseColor("#FF546E7A"));
            //  textView.setBackgroundColor(Color.parseColor("#FF546E7A"));
        } else if (i >= 50 && i <= 60) {
            Drawable background = textView.getBackground();
            ((GradientDrawable) background).setColor(Color.parseColor("#FF607D8B"));
            textView.setTextColor(Color.parseColor("#FFFFFF"));
            //   textView.setBackgroundColor(Color.parseColor("#FF607D8B"));
        } else if (i >= 40 && i <= 50) {
            textView.setTextColor(Color.parseColor("#FFFFFF"));
            Drawable background = textView.getBackground();
            ((GradientDrawable) background).setColor(Color.parseColor("#FF78909C"));
            // textView.setBackgroundColor(Color.parseColor("#FF78909C"));
        } else if (i >= 30 && i <= 40) {
            Drawable background = textView.getBackground();
            ((GradientDrawable) background).setColor(Color.parseColor("#FF90A4AE"));
            // textView.setBackgroundColor(Color.parseColor("#FF90A4AE"));
        } else if (i >= 20 && i <= 30) {
            Drawable background = textView.getBackground();
            ((GradientDrawable) background).setColor(Color.parseColor("#FFB0BEC5"));
            // textView.setBackgroundColor(Color.parseColor("#FFB0BEC5"));
        } else if (i >= 10 && i <= 20) {
            Drawable background = textView.getBackground();
            ((GradientDrawable) background).setColor(Color.parseColor("#FFCFD8DC"));
            // textView.setBackgroundColor(Color.parseColor("#FFCFD8DC"));
        } else if (i >= 0 && i <= 10) {
            Drawable background = textView.getBackground();
            ((GradientDrawable) background).setColor(Color.parseColor("#00ECEFF1"));
            // textView.setBackgroundColor(Color.parseColor("#00ECEFF1"));
        }
    }
}
