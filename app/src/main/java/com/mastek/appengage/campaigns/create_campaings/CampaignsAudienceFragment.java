package com.mastek.appengage.campaigns.create_campaings;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.mastek.appengage.App;
import com.mastek.appengage.HomeActivity;
import com.mastek.appengage.R;
import com.mastek.appengage.RetrofitManager;
import com.mastek.appengage.Tools;
import com.mastek.appengage.WebServices;
import com.mastek.appengage.campaigns.CampaignModelTypeResponseModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CampaignsAudienceFragment extends Fragment {

    LinearLayout linNewAudience;
    RadioGroup AudienceRadioGrp;
    RadioButton radioBtnEveryone, radioBtnNewAudience;
    Stack<String> stringStack;
    WebServices webServices;
    List<String> manufacturer, model, platform, os, appversion, devicetype;
    Button btnNextCampainAudience;
    CreateCampaignReqModel reqModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_campaigns_audience, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        if(getArguments()!=null){
          reqModel=(CreateCampaignReqModel)getArguments().getSerializable("req");
        }

        btnNextCampainAudience = (Button) view.findViewById(R.id.btnNextCampainAudience);
        webServices = RetrofitManager.getInstance().call();
        stringStack = new Stack<>();
        linNewAudience = (LinearLayout) view.findViewById(R.id.linNewAudience);
        AudienceRadioGrp = (RadioGroup) view.findViewById(R.id.AudienceRadioGrp);
        radioBtnEveryone = (RadioButton) view.findViewById(R.id.radioBtnEveryone);
        radioBtnNewAudience = (RadioButton) view.findViewById(R.id.radioBtnNewAudience);

        final AppCompatSpinner spnrAudienceType1 = (AppCompatSpinner) view.findViewById(R.id.spnrAudienceType1);
        final AppCompatSpinner spnrAudienceType2 = (AppCompatSpinner) view.findViewById(R.id.spnrAudienceType2);
        final AppCompatSpinner spnrAudienceType3 = (AppCompatSpinner) view.findViewById(R.id.spnrAudienceType3);
        final AppCompatSpinner spnrAudienceType4 = (AppCompatSpinner) view.findViewById(R.id.spnrAudienceType4);
        final AppCompatSpinner spnrAudienceType5 = (AppCompatSpinner) view.findViewById(R.id.spnrAudienceType5);
        final AppCompatSpinner spnrAudienceType6 = (AppCompatSpinner) view.findViewById(R.id.spnrAudienceType6);


        final TextView addType1 = (TextView) view.findViewById(R.id.addType1);
        final TextView addType2 = (TextView) view.findViewById(R.id.addType2);
        final TextView addType3 = (TextView) view.findViewById(R.id.addType3);
        final TextView addType4 = (TextView) view.findViewById(R.id.addType4);
        final TextView addType5 = (TextView) view.findViewById(R.id.addType5);
        final TextView addType6 = (TextView) view.findViewById(R.id.addType6);


        final TextView deleteType2 = (TextView) view.findViewById(R.id.deleteType2);
        final TextView deleteType3 = (TextView) view.findViewById(R.id.deleteType3);
        final TextView deleteType4 = (TextView) view.findViewById(R.id.deleteType4);
        final TextView deleteType5 = (TextView) view.findViewById(R.id.deleteType5);
        final TextView deleteType6 = (TextView) view.findViewById(R.id.deleteType6);

        final LinearLayout linAudienceSelectiveAudience2 = (LinearLayout) view.findViewById(R.id.linAudienceSelectiveAudience2);
        final LinearLayout linAudienceSelectiveAudience3 = (LinearLayout) view.findViewById(R.id.linAudienceSelectiveAudience3);
        final LinearLayout linAudienceSelectiveAudience4 = (LinearLayout) view.findViewById(R.id.linAudienceSelectiveAudience4);
        final LinearLayout linAudienceSelectiveAudience5 = (LinearLayout) view.findViewById(R.id.linAudienceSelectiveAudience5);
        final LinearLayout linAudienceSelectiveAudience6 = (LinearLayout) view.findViewById(R.id.linAudienceSelectiveAudience6);

        final TextView spnrType5 = (TextView) view.findViewById(R.id.spnrType5);
        final TextView spnrType1 = (TextView) view.findViewById(R.id.spnrType1);
        final TextView spnrType2 = (TextView) view.findViewById(R.id.spnrType2);
        final TextView spnrType3 = (TextView) view.findViewById(R.id.spnrType3);
        final TextView spnrType4 = (TextView) view.findViewById(R.id.spnrType4);
        final TextView spnrType6 = (TextView) view.findViewById(R.id.spnrType6);

        final List<String> dataAudienceType = new ArrayList<>();
        dataAudienceType.add("Manufacturer");
        dataAudienceType.add("Model");
        dataAudienceType.add("Application version");
        dataAudienceType.add("Platform");
        dataAudienceType.add("Device type");
        dataAudienceType.add("OS");

        spnrAudienceType1.setAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item,
                dataAudienceType));
        spnrAudienceType2.setAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item,
                dataAudienceType));
        spnrAudienceType3.setAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item,
                dataAudienceType));
        spnrAudienceType4.setAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item,
                dataAudienceType));
        spnrAudienceType5.setAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item,
                dataAudienceType));
        spnrAudienceType6.setAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item,
                dataAudienceType));


        addType1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stringStack.push(spnrAudienceType1.getSelectedItem().toString());
                dataAudienceType.remove(spnrAudienceType1.getSelectedItem().toString());
                spnrAudienceType1.setEnabled(false);
                linAudienceSelectiveAudience2.setVisibility(View.VISIBLE);
            }
        });
        deleteType2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //   Log.e("check",spnrAudienceType1.getSelectedItem().toString());
                spnrAudienceType1.setEnabled(true);
                String pop = stringStack.pop();
                Log.e("pop", pop);
                dataAudienceType.add(pop);
                linAudienceSelectiveAudience2.setVisibility(View.GONE);
            }
        });
        addType2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spnrAudienceType2.setEnabled(false);

                stringStack.push(spnrAudienceType2.getSelectedItem().toString());
                dataAudienceType.remove(spnrAudienceType2.getSelectedItem().toString());
                linAudienceSelectiveAudience3.setVisibility(View.VISIBLE);
            }
        });
        deleteType3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spnrAudienceType2.setEnabled(true);

                String pop = stringStack.pop();
                Log.e("pop", pop);
                dataAudienceType.add(pop);
                linAudienceSelectiveAudience3.setVisibility(View.GONE);
            }
        });
        addType3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spnrAudienceType3.setEnabled(false);
                stringStack.push(spnrAudienceType3.getSelectedItem().toString());
                dataAudienceType.remove(spnrAudienceType3.getSelectedItem().toString());
                linAudienceSelectiveAudience4.setVisibility(View.VISIBLE);
            }
        });
        deleteType4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spnrAudienceType3.setEnabled(true);

                String pop = stringStack.pop();
                Log.e("pop", pop);
                dataAudienceType.add(pop);
                linAudienceSelectiveAudience4.setVisibility(View.GONE);
            }
        });
        addType4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spnrAudienceType4.setEnabled(false);
                stringStack.push(spnrAudienceType4.getSelectedItem().toString());
                dataAudienceType.remove(spnrAudienceType4.getSelectedItem().toString());
                linAudienceSelectiveAudience5.setVisibility(View.VISIBLE);
            }
        });
        deleteType5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spnrAudienceType4.setEnabled(true);

                String pop = stringStack.pop();
                Log.e("pop", pop);
                dataAudienceType.add(pop);
                linAudienceSelectiveAudience5.setVisibility(View.GONE);
            }
        });
        addType5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spnrAudienceType5.setEnabled(false);
                stringStack.push(spnrAudienceType5.getSelectedItem().toString());
                dataAudienceType.remove(spnrAudienceType5.getSelectedItem().toString());
                linAudienceSelectiveAudience6.setVisibility(View.VISIBLE);
            }
        });
        deleteType6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spnrAudienceType5.setEnabled(true);

                String pop = stringStack.pop();
                Log.e("pop", pop);
                dataAudienceType.add(pop);
                linAudienceSelectiveAudience6.setVisibility(View.GONE);
            }
        });

        AudienceRadioGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioBtnEveryone) {
                    if (linNewAudience.getVisibility() == View.VISIBLE)
                        linNewAudience.setVisibility(View.GONE);
                } else {
                    if (linNewAudience.getVisibility() == View.GONE) {
                        linNewAudience.setVisibility(View.VISIBLE);
                    }
                }
            }
        });


        spnrAudienceType1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("device", "" + dataAudienceType.get(position));
                spnrAudienceType1.setPrompt(dataAudienceType.get(position));

                if ("Manufacturer".equals(dataAudienceType.get(position))) {
                    populateSpinnerType("mnu", spnrType1);
                } else if ("Model".equals(dataAudienceType.get(position))) {
                    populateSpinnerType("model", spnrType1);
                } else if ("Application version".equals(dataAudienceType.get(position))) {
                    populateSpinnerType("appversion", spnrType1);
                } else if ("Platform".equals(dataAudienceType.get(position))) {
                    populateSpinnerType("platform", spnrType1);
                } else if ("Device type".equals(dataAudienceType.get(position))) {
                    populateSpinnerType("dt", spnrType1);
                } else if ("OS".equals(dataAudienceType.get(position))) {
                    populateSpinnerType("os", spnrType1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spnrAudienceType2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("device", "" + dataAudienceType.get(position));
                spnrAudienceType2.setPrompt(dataAudienceType.get(position));

                if ("Manufacturer".equals(dataAudienceType.get(position))) {
                    populateSpinnerType("mnu", spnrType2);
                } else if ("Model".equals(dataAudienceType.get(position))) {
                    populateSpinnerType("model", spnrType2);
                } else if ("Application version".equals(dataAudienceType.get(position))) {
                    populateSpinnerType("appversion", spnrType2);
                } else if ("Platform".equals(dataAudienceType.get(position))) {
                    populateSpinnerType("platform", spnrType2);
                } else if ("Device type".equals(dataAudienceType.get(position))) {
                    populateSpinnerType("dt", spnrType2);
                } else if ("OS".equals(dataAudienceType.get(position))) {
                    populateSpinnerType("os", spnrType2);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spnrAudienceType3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("device", "" + dataAudienceType.get(position));
                spnrAudienceType3.setPrompt(dataAudienceType.get(position));

                if ("Manufacturer".equals(dataAudienceType.get(position))) {
                    populateSpinnerType("mnu", spnrType3);
                } else if ("Model".equals(dataAudienceType.get(position))) {
                    populateSpinnerType("model", spnrType3);
                } else if ("Application version".equals(dataAudienceType.get(position))) {
                    populateSpinnerType("appversion", spnrType3);
                } else if ("Platform".equals(dataAudienceType.get(position))) {
                    populateSpinnerType("platform", spnrType3);
                } else if ("Device type".equals(dataAudienceType.get(position))) {
                    populateSpinnerType("dt", spnrType3);
                } else if ("OS".equals(dataAudienceType.get(position))) {
                    populateSpinnerType("os", spnrType3);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnrAudienceType4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("device", "" + dataAudienceType.get(position));
                spnrAudienceType4.setPrompt(dataAudienceType.get(position));

                if ("Manufacturer".equals(dataAudienceType.get(position))) {
                    populateSpinnerType("mnu", spnrType4);
                } else if ("Model".equals(dataAudienceType.get(position))) {
                    populateSpinnerType("model", spnrType4);
                } else if ("Application version".equals(dataAudienceType.get(position))) {
                    populateSpinnerType("appversion", spnrType4);
                } else if ("Platform".equals(dataAudienceType.get(position))) {
                    populateSpinnerType("platform", spnrType4);
                } else if ("Device type".equals(dataAudienceType.get(position))) {
                    populateSpinnerType("dt", spnrType4);
                } else if ("OS".equals(dataAudienceType.get(position))) {
                    populateSpinnerType("os", spnrType4);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spnrAudienceType5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("device", "" + dataAudienceType.get(position));
                spnrAudienceType5.setPrompt(dataAudienceType.get(position));

                if ("Manufacturer".equals(dataAudienceType.get(position))) {
                    populateSpinnerType("mnu", spnrType5);
                } else if ("Model".equals(dataAudienceType.get(position))) {
                    populateSpinnerType("model", spnrType5);
                } else if ("Application version".equals(dataAudienceType.get(position))) {
                    populateSpinnerType("appversion", spnrType5);
                } else if ("Platform".equals(dataAudienceType.get(position))) {
                    populateSpinnerType("platform", spnrType5);
                } else if ("Device type".equals(dataAudienceType.get(position))) {
                    populateSpinnerType("dt", spnrType5);
                } else if ("OS".equals(dataAudienceType.get(position))) {
                    populateSpinnerType("os", spnrType5);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnrAudienceType6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("device", "" + dataAudienceType.get(position));
                spnrAudienceType6.setPrompt(dataAudienceType.get(position));

                if ("Manufacturer".equals(dataAudienceType.get(position))) {
                    populateSpinnerType("mnu", spnrType6);
                } else if ("Model".equals(dataAudienceType.get(position))) {
                    populateSpinnerType("model", spnrType6);
                } else if ("Application version".equals(dataAudienceType.get(position))) {
                    populateSpinnerType("appversion", spnrType6);
                } else if ("Platform".equals(dataAudienceType.get(position))) {
                    populateSpinnerType("platform", spnrType6);
                } else if ("Device type".equals(dataAudienceType.get(position))) {
                    populateSpinnerType("dt", spnrType6);
                } else if ("OS".equals(dataAudienceType.get(position))) {
                    populateSpinnerType("os", spnrType6);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnNextCampainAudience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (linNewAudience.getVisibility() == View.VISIBLE) {

                    if (spnrAudienceType1.getPrompt() != null) {
                        Log.e(spnrAudienceType1.getPrompt().toString(), spnrType1.getText().toString());
                        getArrayListForPerticularAudience(spnrAudienceType1.getPrompt().toString(), spnrType1.getText().toString());
                    }

                    if (spnrAudienceType2.getPrompt() != null) {
                        Log.e(spnrAudienceType2.getPrompt().toString(), spnrType2.getText().toString());
                        getArrayListForPerticularAudience(spnrAudienceType2.getPrompt().toString(), spnrType2.getText().toString());

                    }

                    if (spnrAudienceType3.getPrompt() != null) {
                        Log.e(spnrAudienceType3.getPrompt().toString(), spnrType3.getText().toString());
                        getArrayListForPerticularAudience(spnrAudienceType3.getPrompt().toString(), spnrType3.getText().toString());

                    }


                    if (spnrAudienceType4.getPrompt() != null) {
                        Log.e(spnrAudienceType4.getPrompt().toString(), spnrType4.getText().toString());
                        getArrayListForPerticularAudience(spnrAudienceType4.getPrompt().toString(), spnrType4.getText().toString());

                    }


                    if (spnrAudienceType5.getPrompt() != null) {
                        Log.e(spnrAudienceType5.getPrompt().toString(), spnrType5.getText().toString());
                        getArrayListForPerticularAudience(spnrAudienceType5.getPrompt().toString(), spnrType5.getText().toString());


                    }

                    if (spnrAudienceType6.getPrompt() != null) {
                        Log.e(spnrAudienceType6.getPrompt().toString(), spnrType6.getText().toString());
                        getArrayListForPerticularAudience(spnrAudienceType6.getPrompt().toString(), spnrType6.getText().toString());
                    }

                }

                CreateCampaignReqModel.QueryBean queryBean=new CreateCampaignReqModel.QueryBean();

                if(manufacturer!=null)
                queryBean.setLm(manufacturer);

                if(model!=null)
                queryBean.setLmod(model);

                if(appversion!=null)
                queryBean.setLav(appversion);

                if(devicetype!=null)
                queryBean.setLdt(devicetype);

                if(os!=null)
                queryBean.setLosv(os);

                if(platform!=null)
                queryBean.setLpf(platform);
                CampainSummaryFragment campainSummaryFragment=new CampainSummaryFragment();
                reqModel.setQuery(queryBean);
                Bundle bundle=new Bundle();
                bundle.putSerializable("req",reqModel);
                campainSummaryFragment.setArguments(bundle);
                ((HomeActivity)getActivity()).loadFragment(R.id.frmContainer,campainSummaryFragment,
                        CampainSummaryFragment.class.getName());

            }
        });


    }


    public void populateSpinnerType(String str, final TextView textView) {

        if (str.equals("model")) {
            Tools.showProgress(getActivity());
            webServices.getAudienceForModel(App.getInstance().getLoginUser().getAkey()).enqueue(new Callback<List<CampaignModelTypeResponseModel>>() {
                @Override
                public void onResponse(Call<List<CampaignModelTypeResponseModel>> call, Response<List<CampaignModelTypeResponseModel>> response) {
                    Tools.hideProgress();
                    if (response.body() != null && response.body().size() > 0) {
                        List<String> modelData = new ArrayList<String>();

                        for (CampaignModelTypeResponseModel model : response.body()) {
                            modelData.add(model.getAn());
                        }

                        final Dialog dialog = new Dialog(getActivity());
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.dialog_audience);
                        RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.relDialog);
                        Button btnSelect = (Button) dialog.findViewById(R.id.btnSelect);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        final AudienceAdapter audienceAdapter = new AudienceAdapter(getActivity(), modelData);
                        recyclerView.setAdapter(audienceAdapter);
                        dialog.show();
                        btnSelect.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                textView.setText(audienceAdapter.selectedStrings.toString());
                                Log.e("select", audienceAdapter.selectedStrings.toString());
                                dialog.dismiss();
                            }
                        });

                       /* appCompatSpinner.setAdapter(new ArrayAdapter<String>(getActivity(),
                                android.R.layout.simple_spinner_item,
                                modelData));*/

                    }
                }

                @Override
                public void onFailure(Call<List<CampaignModelTypeResponseModel>> call, Throwable t) {
                    Tools.hideProgress();

                }
            });

        } else {
            Tools.showProgress(getActivity());
            webServices.getAudienceManufacturer(str, App.getInstance().getLoginUser().getAkey()).enqueue(new Callback<List<String>>() {
                @Override
                public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                    Tools.hideProgress();
                    if (response.body() != null && response.body().size() > 0) {


                        final Dialog dialog = new Dialog(getActivity());

                        dialog.setContentView(R.layout.dialog_audience);
                        Button btnSelect = (Button) dialog.findViewById(R.id.btnSelect);
                        RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.relDialog);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        final AudienceAdapter audienceAdapter = new AudienceAdapter(getActivity(), response.body());
                        recyclerView.setAdapter(audienceAdapter);
                        dialog.show();

                        btnSelect.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                textView.setText(audienceAdapter.selectedStrings.toString());
                                Log.e("select", audienceAdapter.selectedStrings.toString());
                                dialog.dismiss();
                            }
                        });
                       /* appCompatSpinner.setAdapter(new ArrayAdapter<String>(getActivity(),
                                android.R.layout.simple_spinner_item,
                                response.body()));*/
                    }
                }

                @Override
                public void onFailure(Call<List<String>> call, Throwable t) {
                    Tools.hideProgress();
                }
            });
        }
    }


    public List<String> getListObject(String s1) {
        String replace = s1.replace("[", "");
        String replace1 = replace.replace("]", "");
        List<String> myList = new ArrayList<String>(Arrays.asList(replace1.split(",")));

        return myList;
    }


    public void getArrayListForPerticularAudience(String type, String arrayString) {
        if (type.equals("Manufacturer")) {
            manufacturer = getListObject(arrayString);
        } else if (type.equals("Model")) {
            model = getListObject(arrayString);
        } else if (type.equals("Application version")) {
            appversion = getListObject(arrayString);
        } else if (type.equals("Platform")) {
            platform = getListObject(arrayString);
        } else if (type.equals("Device type")) {
            devicetype = getListObject(arrayString);
        } else if (type.equals("OS")) {
            os = getListObject(arrayString);
        }

    }
}
