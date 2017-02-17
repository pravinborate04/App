package com.mastek.appengage.campaigns.create_campaings;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mastek.appengage.App;
import com.mastek.appengage.R;
import com.mastek.appengage.RetrofitManager;
import com.mastek.appengage.WebServices;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CampainSummaryFragment extends Fragment {
    CreateCampaignReqModel reqModel;
    TextView txtSummaryName;
    Button btnConfirm;
    WebServices webServices;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_campain_summary, container, false);
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtSummaryName=(TextView)view.findViewById(R.id.txtSummaryName);
        if(getActivity()!=null){
            reqModel=(CreateCampaignReqModel)getArguments().getSerializable("req");
            Log.e("name",reqModel.getName());
            Log.e("title",reqModel.getPn_title());
            Log.e("body",reqModel.getPn_msg());

            if(reqModel.getQuery().getLm()!=null)
            Log.e("Mnu",reqModel.getQuery().getLm().toString());

            if(reqModel.getQuery().getLpf()!=null)
                Log.e("platform",reqModel.getQuery().getLpf().toString());

            if(reqModel.getQuery().getLmod()!=null)
                Log.e("model",reqModel.getQuery().getLmod().toString());

            if(reqModel.getQuery().getLosv()!=null)
                Log.e("OS",reqModel.getQuery().getLosv().toString());

            if(reqModel.getQuery().getLdt()!=null)
                Log.e("Device Type",reqModel.getQuery().getLdt().toString());

            if(reqModel.getQuery().getLav()!=null)
                Log.e("App Version",reqModel.getQuery().getLav().toString());

        }

        txtSummaryName.setText(reqModel.getName());

        btnConfirm=(Button)view.findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reqModel.setSchedule_type("immediate");
                reqModel.setRecursive(false);
                webServices= RetrofitManager.getInstance().call();
                webServices.createCampaigns(App.getInstance().getLoginUser().getAkey(),reqModel).enqueue(new Callback<CreateCampaignsResModel>() {
                    @Override
                    public void onResponse(Call<CreateCampaignsResModel> call, Response<CreateCampaignsResModel> response) {
                        if(response.body()!=null){
                            Log.e("sucecss",response.body().getMsg());
                        }else {
                         //   Log.e("sucecss",response.body().getMsg());
                            Log.e("sucecss",new Gson().toJson(response));

                            Log.e("sucecss","null");

                        }
                    }

                    @Override
                    public void onFailure(Call<CreateCampaignsResModel> call, Throwable t) {
                        Log.e("sucecss",t.getLocalizedMessage());

                    }
                });
            }
        });


    }
}
