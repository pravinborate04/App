package com.mastek.appengage.campaigns;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.mastek.appengage.App;
import com.mastek.appengage.HomeActivity;
import com.mastek.appengage.R;
import com.mastek.appengage.RetrofitManager;
import com.mastek.appengage.WebServices;
import com.mastek.appengage.campaigns.create_campaings.CampaignNameFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CampaignsFragment extends Fragment {

    Button btnCreateCampaigns;
    public static int i = 0;
    WebServices webServices;
    boolean b=false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_campaigns, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnCreateCampaigns = (Button) view.findViewById(R.id.btnCreateCampaigns);
        webServices= RetrofitManager.getInstance().call();

        btnCreateCampaigns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivity)getActivity()).loadFragment(R.id.frmContainer,new CampaignNameFragment(),CampaignNameFragment.class.getName());
            }
        });


        if(b)
        btnCreateCampaigns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog dialog = new Dialog(getActivity());

                dialog.setContentView(R.layout.layout_create_campaign_dialog);
                final Button btnNext = (Button) dialog.findViewById(R.id.btnNextCampainName);
                final LinearLayout linCampaignName = (LinearLayout) dialog.findViewById(R.id.linCampaignName);
                final EditText edtCampaignName = (EditText) dialog.findViewById(R.id.edtCampaignName);
                final LinearLayout linCampaignCreative = (LinearLayout) dialog.findViewById(R.id.linCampaignCreative);
                final EditText edtCampaingMessageTitle = (EditText) dialog.findViewById(R.id.edtCampaingMessageTitle);
                final EditText edtCampaingMessageBody = (EditText) dialog.findViewById(R.id.edtCampaingMessageBody);
                final LinearLayout linAudience = (LinearLayout) dialog.findViewById(R.id.linAudience);
                final RadioGroup radioGroup=(RadioGroup)dialog.findViewById(R.id.radioGrpAudience);
                final LinearLayout linAudienceSelectiveAudience1=(LinearLayout)dialog.findViewById(R.id.linAudienceSelectiveAudience1);
                final AppCompatSpinner spnrAudienceType1=(AppCompatSpinner)dialog.findViewById(R.id.spnrAudienceType1);
                final AppCompatSpinner spnrAudienceType2=(AppCompatSpinner)dialog.findViewById(R.id.spnrAudienceType2);
                final AppCompatSpinner spnrAudienceType3=(AppCompatSpinner)dialog.findViewById(R.id.spnrAudienceType3);
                final AppCompatSpinner spnrAudienceType4=(AppCompatSpinner)dialog.findViewById(R.id.spnrAudienceType4);
                final AppCompatSpinner spnrAudienceType5=(AppCompatSpinner)dialog.findViewById(R.id.spnrAudienceType5);
                final AppCompatSpinner spnrAudienceType6=(AppCompatSpinner)dialog.findViewById(R.id.spnrAudienceType6);

                final TextView addType1=(TextView)dialog.findViewById(R.id.addType1);
                final TextView addType2=(TextView)dialog.findViewById(R.id.addType2);
                final TextView addType3=(TextView)dialog.findViewById(R.id.addType3);
                final TextView addType4=(TextView)dialog.findViewById(R.id.addType4);
                final TextView addType5=(TextView)dialog.findViewById(R.id.addType5);
                final TextView addType6=(TextView)dialog.findViewById(R.id.addType6);


                final TextView deleteType2=(TextView)dialog.findViewById(R.id.deleteType2);
                final TextView deleteType3=(TextView)dialog.findViewById(R.id.deleteType3);
                final TextView deleteType4=(TextView)dialog.findViewById(R.id.deleteType4);
                final TextView deleteType5=(TextView)dialog.findViewById(R.id.deleteType5);
                final TextView deleteType6=(TextView)dialog.findViewById(R.id.deleteType6);

                final LinearLayout linAudienceSelectiveAudience2=(LinearLayout)dialog.findViewById(R.id.linAudienceSelectiveAudience2);
                final LinearLayout linAudienceSelectiveAudience3=(LinearLayout)dialog.findViewById(R.id.linAudienceSelectiveAudience3);
                final LinearLayout linAudienceSelectiveAudience4=(LinearLayout)dialog.findViewById(R.id.linAudienceSelectiveAudience4);
                final LinearLayout linAudienceSelectiveAudience5=(LinearLayout)dialog.findViewById(R.id.linAudienceSelectiveAudience5);
                final LinearLayout linAudienceSelectiveAudience6=(LinearLayout)dialog.findViewById(R.id.linAudienceSelectiveAudience6);

                final AppCompatSpinner spnrType5=(AppCompatSpinner)dialog.findViewById(R.id.spnrType5);
                final AppCompatSpinner spnrType1=(AppCompatSpinner)dialog.findViewById(R.id.spnrType1);
                final AppCompatSpinner spnrType2=(AppCompatSpinner)dialog.findViewById(R.id.spnrType2);
                final AppCompatSpinner spnrType3=(AppCompatSpinner)dialog.findViewById(R.id.spnrType3);
                final AppCompatSpinner spnrType4=(AppCompatSpinner)dialog.findViewById(R.id.spnrType4);
                final AppCompatSpinner spnrType6=(AppCompatSpinner)dialog.findViewById(R.id.spnrType6);

                final List<String> dataAudienceType=new ArrayList<>();
                dataAudienceType.add("Manufacturer");
                dataAudienceType.add("Model");
                dataAudienceType.add("Application version");
                dataAudienceType.add("Platform");
                dataAudienceType.add("Device type");
                dataAudienceType.add("OS");

                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch(checkedId)
                        {
                            case R.id.radioBtnEveryone:
                                Log.e( "onCheckedChanged: ","radioBtnEveryone" );
                                if(linAudienceSelectiveAudience1.getVisibility()==View.VISIBLE)
                                    linAudienceSelectiveAudience1.setVisibility(View.GONE);
                                break;
                            case R.id.radioBtnNewAudience:
                                Log.e( "onCheckedChanged: ","radioBtnNewAudience" );
                                if(linAudienceSelectiveAudience1.getVisibility()==View.GONE)
                                linAudienceSelectiveAudience1.setVisibility(View.VISIBLE);
                                spnrAudienceType1.setAdapter(new ArrayAdapter<String>(getActivity(),
                                        android.R.layout.simple_spinner_item,
                                        dataAudienceType));

                                break;
                        }
                    }
                });

                btnNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                         if (i ==0  && TextUtils.isEmpty(edtCampaingMessageTitle.getText())
                                && TextUtils.isEmpty(edtCampaingMessageBody.getText())) {
                            i++;
                            linCampaignName.setVisibility(View.GONE);
                            linCampaignCreative.setVisibility(View.VISIBLE);
                            linAudience.setVisibility(View.GONE);
                            btnNext.setVisibility(View.GONE);

                        } else if (i == 1) {
                            i++;
                            linCampaignName.setVisibility(View.GONE);
                            linCampaignCreative.setVisibility(View.GONE);
                            linAudience.setVisibility(View.VISIBLE);
                        }
                    }
                });
                dialog.show();

                TextWatcher textWatcher = new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                        if(linCampaignName.getVisibility()==View.VISIBLE){
                            if (TextUtils.isEmpty(edtCampaignName.getText())) {

                                edtCampaignName.setError("*");
                            } else {
                                btnNext.setVisibility(View.VISIBLE);
                            }
                        }else if(linCampaignCreative.getVisibility()==View.VISIBLE){
                            if(!TextUtils.isEmpty(edtCampaingMessageTitle.getText())
                                    && !TextUtils.isEmpty(edtCampaingMessageBody.getText())){
                                btnNext.setVisibility(View.VISIBLE);
                            }else {
                                btnNext.setVisibility(View.GONE);
                            }

                        }

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                };

                edtCampaignName.addTextChangedListener(textWatcher);
                edtCampaingMessageTitle.addTextChangedListener(textWatcher);
                edtCampaingMessageBody.addTextChangedListener(textWatcher);

                spnrAudienceType1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Log.e("device",""+dataAudienceType.get(position));

                        if ("Manufacturer".equals(dataAudienceType.get(position))){
                            populateSpinnerType("mnu",spnrType1);
                        }else if ("Model".equals(dataAudienceType.get(position))){
                            populateSpinnerType("model",spnrType1);
                        }else if ("Application version".equals(dataAudienceType.get(position))){
                            populateSpinnerType("appversion",spnrType1);
                        }else if ("Platform".equals(dataAudienceType.get(position))){
                            populateSpinnerType("platform",spnrType1);
                        }else if ("Device type".equals(dataAudienceType.get(position))){
                            populateSpinnerType("dt",spnrType1);
                        }else if ("OS".equals(dataAudienceType.get(position))){
                            populateSpinnerType("os",spnrType1);
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                spnrAudienceType2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Log.e("device",""+dataAudienceType.get(position));

                        if ("Manufacturer".equals(dataAudienceType.get(position))){
                            populateSpinnerType("mnu",spnrType2);
                        }else if ("Model".equals(dataAudienceType.get(position))){
                            populateSpinnerType("model",spnrType2);
                        }else if ("Application version".equals(dataAudienceType.get(position))){
                            populateSpinnerType("appversion",spnrType2);
                        }else if ("Platform".equals(dataAudienceType.get(position))){
                            populateSpinnerType("platform",spnrType2);
                        }else if ("Device type".equals(dataAudienceType.get(position))){
                            populateSpinnerType("dt",spnrType2);
                        }else if ("OS".equals(dataAudienceType.get(position))){
                            populateSpinnerType("os",spnrType2);
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                String spnr1,spnr2,spnr3,spnr4,spnr5,spnr6;
                addType1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("pr",spnrAudienceType1.getSelectedItem().toString());
                        dataAudienceType.remove(spnrAudienceType1.getSelectedItem().toString());
                        spnrAudienceType2.setAdapter(new ArrayAdapter<String>(getActivity(),
                                android.R.layout.simple_spinner_item,
                                dataAudienceType));
                        linAudienceSelectiveAudience2.setVisibility(View.VISIBLE);
                    }
                });
                deleteType2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("check",spnrAudienceType1.getSelectedItem().toString());
                        linAudienceSelectiveAudience2.setVisibility(View.GONE);
                    }
                });
                addType2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        linAudienceSelectiveAudience3.setVisibility(View.VISIBLE);
                    }
                });
                deleteType3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        linAudienceSelectiveAudience3.setVisibility(View.GONE);
                    }
                });
                addType3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        linAudienceSelectiveAudience4.setVisibility(View.VISIBLE);
                    }
                });
                deleteType4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        linAudienceSelectiveAudience4.setVisibility(View.GONE);
                    }
                });
                addType4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        linAudienceSelectiveAudience5.setVisibility(View.VISIBLE);
                    }
                });
                deleteType5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        linAudienceSelectiveAudience5.setVisibility(View.GONE);
                    }
                });



                addType5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        linAudienceSelectiveAudience6.setVisibility(View.VISIBLE);
                    }
                });
                deleteType6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        linAudienceSelectiveAudience6.setVisibility(View.GONE);
                    }
                });
            }
        });


    }


    public void populateSpinnerType(String str, final AppCompatSpinner appCompatSpinner){

        if(str.equals("model")){
            webServices.getAudienceForModel(App.getInstance().getLoginUser().getAkey()).enqueue(new Callback<List<CampaignModelTypeResponseModel>>() {
                @Override
                public void onResponse(Call<List<CampaignModelTypeResponseModel>> call, Response<List<CampaignModelTypeResponseModel>> response) {
                    if(response.body()!=null && response.body().size()>0){
                        List<String> modelData=new ArrayList<String>();

                        for(CampaignModelTypeResponseModel model:response.body()){
                            modelData.add(model.getAn());
                        }

                        appCompatSpinner.setAdapter(new ArrayAdapter<String>(getActivity(),
                                android.R.layout.simple_spinner_item,
                                modelData));

                    }
                }

                @Override
                public void onFailure(Call<List<CampaignModelTypeResponseModel>> call, Throwable t) {

                }
            });

        }else {
            webServices.getAudienceManufacturer(str,App.getInstance().getLoginUser().getAkey()).enqueue(new Callback<List<String>>() {
                @Override
                public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                    if(response.body()!=null && response.body().size()>0){
                        appCompatSpinner.setAdapter(new ArrayAdapter<String>(getActivity(),
                                android.R.layout.simple_spinner_item,
                                response.body()));
                    }
                }

                @Override
                public void onFailure(Call<List<String>> call, Throwable t) {

                }
            });
        }



    }



    public void getDataForSpinner(String type){
        webServices.getAudienceManufacturer(type,App.getInstance().getLoginUser().getAkey()).enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                Log.e("res",response.body().toString());
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Log.e("ERROR ",t.getLocalizedMessage());

            }
        });

    }
}
