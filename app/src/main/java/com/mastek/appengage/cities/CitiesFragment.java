package com.mastek.appengage.cities;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.google.gson.Gson;
import com.mastek.appengage.App;
import com.mastek.appengage.MA;
import com.mastek.appengage.R;
import com.mastek.appengage.RetrofitManager;
import com.mastek.appengage.Tools;
import com.mastek.appengage.WebServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.osmdroid.api.IMapController;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.OverlayItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CitiesFragment extends Fragment implements DatePickerDialog.OnDateSetListener{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cities, container, false);
    }
    private MapView myOpenMapView;
    private IMapController myMapController;
    ArrayList<OverlayItem> anotherOverlayItemArray;
    WebServices webServices;

    ImageView imgCalenderLoc1;
    TextView btnDateRangeLoc1;
    String sd;
    String ed;

    @Override
    public void onStart() {
        super.onStart();

        MA.startFragmentScreen(CitiesFragment.class.getName());

    }


    @Override
    public void onStop() {
        super.onStop();

        MA.endFragmentScreen(CitiesFragment.class.getName());

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imgCalenderLoc1=(ImageView)view.findViewById(R.id.imgCalenderLoc1);
        btnDateRangeLoc1=(TextView)view.findViewById(R.id.btnDateRangeLoc1);

        Calendar now = Calendar.getInstance();
        Calendar after=Calendar.getInstance();
        after.add(Calendar.MONTH,-1);

        SimpleDateFormat format = new SimpleDateFormat("MMMM d, yyyy");

        String formatedDate=""+format.format(after.getTime())+" - "+""+format.format(now.getTime());
        btnDateRangeLoc1.setText(formatedDate);

        final DatePickerDialog dpd = DatePickerDialog.newInstance(
                CitiesFragment.this,
                after.get(Calendar.YEAR),
                after.get(Calendar.MONTH),
                after.get(Calendar.DAY_OF_MONTH),
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );

        dpd.setMaxDate(Calendar.getInstance());

        imgCalenderLoc1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
            }
        });
        sd=after.getTimeInMillis()/1000+"";
        ed=now.getTimeInMillis()/1000+"";

        myOpenMapView = (MapView)view.findViewById(R.id.map);
        myOpenMapView.setBuiltInZoomControls(true);
        myMapController = myOpenMapView.getController();
        myMapController.setZoom(3);

            getData(sd,ed);

    }

    public void getData(String sd,String ed)
    {
        webServices= RetrofitManager.getInstance().call();
        Tools.showProgress(getContext());
        webServices.getLocationCounters(sd,ed, App.getInstance().getLoginUser().getAkey()).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Tools.hideProgress();
                if(response.body()!=null){
                    Log.e("REspLOc",response.body());
                    List<CitiesResponseModel> responseModelList=new ArrayList<CitiesResponseModel>();
                    JSONArray jsonArray= null;
                    try {
                        jsonArray = new JSONArray(response.body());
                        Log.e("Size",jsonArray.length()+"" );

                        for(int i=0;i<jsonArray.length();i++){
                            CitiesResponseModel citiesResponseModel;
                            citiesResponseModel=new Gson().fromJson(jsonArray.getString(i),CitiesResponseModel.class);
                            responseModelList.add(citiesResponseModel);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Log.e("SIZE CITIES",responseModelList.size()+"");
                    showMap(responseModelList);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Tools.hideProgress();
            }
        });

    }



    public void showMap(List<CitiesResponseModel> models){
        anotherOverlayItemArray = new ArrayList<OverlayItem>();

        for(int i=0;i<models.size(); i++){
            GeoPoint geoPoint=new GeoPoint(Double.parseDouble(models.get(i).getLat()),Double.parseDouble(models.get(i).getLong()));

            Marker startMarker = new Marker(myOpenMapView);
            startMarker.setTitle(models.get(i).getCity());
            startMarker.setSubDescription( models.get(i).getUsers()+"\n" +
                    "City : "+models.get(i).getCity()+"\n" +
                    "Time : "+models.get(i).getTime() );
            startMarker.setPosition(geoPoint);
           // IGeoPoint iGeoPoint=new GeoPoint(Double.parseDouble(models.get(i).getLat()),Double.parseDouble(models.get(i).getLong()));
            anotherOverlayItemArray.add(new OverlayItem(
                    models.get(i).getCity(), "Users : "+models.get(i).getUsers()+"\n" +
                    "Time : "+getDurationString(Integer.parseInt(models.get(i).getTime())), geoPoint));
        }
        Drawable newMarker = this.getResources().getDrawable(R.drawable.ic_action_map);
        /*ItemizedIconOverlay<OverlayItem> anotherItemizedIconOverlay
                = new ItemizedIconOverlay<OverlayItem>(
                getActivity(), anotherOverlayItemArray, new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
            @Override
            public boolean onItemSingleTapUp(int index, OverlayItem item) {
               return onSingleTapUpHelper(index,item);
                //return true;
            }

            @Override
            public boolean onItemLongPress(int index, OverlayItem item) {
                return true;
            }
        });*/

        ItemizedIconOverlay<OverlayItem> anotherItemizedIconOverlay
                = new ItemizedIconOverlay<OverlayItem>(anotherOverlayItemArray, newMarker, new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
            @Override
            public boolean onItemSingleTapUp(int index, OverlayItem item) {
                return onSingleTapUpHelper(index,item);
            }

            @Override
            public boolean onItemLongPress(int index, OverlayItem item) {
                return true;
            }
        },getActivity());

        myOpenMapView.getOverlays().add(anotherItemizedIconOverlay);

    }


    public boolean onSingleTapUpHelper(int i, OverlayItem item) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle(item.getTitle());
        dialog.setMessage(item.getSnippet());
        dialog.show();
        return true;
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
        btnDateRangeLoc1.setText(formatedDate);

        sd=startCalender.getTimeInMillis()/1000+"";
        ed=endCalender.getTimeInMillis()/1000+"";
        getData(sd,ed);
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
}
