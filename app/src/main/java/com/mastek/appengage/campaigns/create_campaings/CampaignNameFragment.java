package com.mastek.appengage.campaigns.create_campaings;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.mastek.appengage.HomeActivity;
import com.mastek.appengage.R;
import com.mastek.appengage.campaigns.CampaignModelTypeResponseModel;

public class CampaignNameFragment extends Fragment {


    Button btnNextCampainName;
    CreateCampaignReqModel responseModel;
    EditText edtCampainName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_campaign_name, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnNextCampainName = (Button) view.findViewById(R.id.btnNextCampainName);
        edtCampainName = (EditText) view.findViewById(R.id.edtCampainName);


        btnNextCampainName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validate()) {
                    responseModel = new CreateCampaignReqModel();
                    responseModel.setName(edtCampainName.getText().toString());
                    CampaignCreativeFragment campaignCreativeFragment=new CampaignCreativeFragment();
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("req",responseModel);
                    campaignCreativeFragment.setArguments(bundle);
                    ((HomeActivity) getActivity()).loadFragment(R.id.frmContainer,
                            campaignCreativeFragment,
                            CampaignCreativeFragment.class.getName());
                }
            }
        });
    }


    public boolean validate() {
        if (TextUtils.isEmpty(edtCampainName.getText().toString().trim())) {
            edtCampainName.setError("This Field is Mandatory!");
            edtCampainName.requestFocus();
            return false;
        } else {
            return true;
        }

    }
}
