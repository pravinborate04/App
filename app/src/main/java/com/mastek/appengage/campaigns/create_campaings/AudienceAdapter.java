package com.mastek.appengage.campaigns.create_campaings;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.mastek.appengage.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pravin103082 on 16-02-2017.
 */

public class AudienceAdapter extends RecyclerView.Adapter<AudienceAdapter.AudienceViewHolder> {

    Context mContext;
    LayoutInflater layoutInflater;
    List<String> stringList;
   // OnItemCheckListener onItemCheckListener;

    public ArrayList<String> selectedStrings;
    public AudienceAdapter(Context context, List<String> list/*,OnItemCheckListener onItemCheckListener*/) {
        this.mContext = context;
        stringList = list;
     //   this.onItemCheckListener=onItemCheckListener;
        layoutInflater=LayoutInflater.from(mContext);
        selectedStrings=new ArrayList<>();
    }

    @Override
    public AudienceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=layoutInflater.inflate(R.layout.dialog_audience_single_row,parent,false);
        AudienceViewHolder viewHolder=new AudienceViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AudienceViewHolder holder, final int position) {
        holder.textView.setText(stringList.get(position));
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                   // onItemCheckListener.onItemCheck(stringList.get(position));
                    Log.e("check",stringList.get(position));
                    selectedStrings.add(stringList.get(position));
                }else {
                    selectedStrings.remove(stringList.get(position));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }

    class AudienceViewHolder extends RecyclerView.ViewHolder{
        CheckBox checkBox;
        TextView textView;

        public AudienceViewHolder(View itemView) {
            super(itemView);
            checkBox=(CheckBox)itemView.findViewById(R.id.checkBoxDialogAudienceSingleRow);
            textView=(TextView)itemView.findViewById(R.id.txtDialogAudienceSingleRow);
        }
    }

    interface OnItemCheckListener {
        void onItemCheck(String item);
        void onItemUncheck(String item);
    }

}
