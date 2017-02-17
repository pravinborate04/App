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

public class CampaignCreativeFragment extends Fragment {

    Button btnNextCampainCreative;
    CreateCampaignReqModel createCampaignReqModel;
    EditText edtMessageTitle,edtMessageBody;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_campaign_creative, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edtMessageTitle=(EditText)view.findViewById(R.id.edtMessageTitle) ;
        edtMessageBody=(EditText)view.findViewById(R.id.edtMessageBody);

        if(getArguments()!=null){
            createCampaignReqModel=(CreateCampaignReqModel)getArguments().getSerializable("req");
        }

        btnNextCampainCreative=(Button)view.findViewById(R.id.btnNextCampainCreative);

        btnNextCampainCreative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    CampaignsAudienceFragment campaignsAudienceFragment=new CampaignsAudienceFragment();
                    Bundle bundle=new Bundle();
                    createCampaignReqModel.setPn_title(edtMessageTitle.getText().toString().trim());
                    createCampaignReqModel.setPn_msg(edtMessageBody.getText().toString().trim());

                    bundle.putSerializable("req",createCampaignReqModel);
                    campaignsAudienceFragment.setArguments(bundle);

                    ((HomeActivity)getActivity()).loadFragment(R.id.frmContainer,
                            campaignsAudienceFragment,CampaignsAudienceFragment.class.getName());
                }

            }
        });
    }



    public boolean validate(){
        if(TextUtils.isEmpty(edtMessageBody.getText().toString().trim())){
            edtMessageBody.setError("this field is Mandatory");
            edtMessageBody.requestFocus();
            return false;
        }else if(TextUtils.isEmpty(edtMessageTitle.getText().toString().trim())){
            edtMessageTitle.setError("This field is Mandatory");
            edtMessageTitle.requestFocus();
            return false;
        }else {
            return true;
        }
    }
}
