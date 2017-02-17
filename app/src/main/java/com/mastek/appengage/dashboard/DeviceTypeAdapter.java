package com.mastek.appengage.dashboard;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mastek.appengage.R;

import java.util.List;

/**
 * Created by Pravin103082 on 03-02-2017.
 */

public class DeviceTypeAdapter extends ArrayAdapter<String> {
    Context mContext;
    LayoutInflater layoutInflater;
    List<String> data;

    public DeviceTypeAdapter(Context context,List<String> strings) {
        super(context, R.layout.device_type_single_row);
        mContext=context;
        layoutInflater=LayoutInflater.from(mContext);
        data=strings;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        DeviceTypeViewHolder viewHolder=null;
        if(convertView==null){
            viewHolder=new DeviceTypeViewHolder();
            convertView=layoutInflater.inflate(R.layout.device_type_single_row,parent,false);
            viewHolder.textView= (TextView) convertView.findViewById(R.id.txtSingleRowDeviceType);
            convertView.setTag(viewHolder);
        }else {
            viewHolder=(DeviceTypeViewHolder)convertView.getTag();
        }
        viewHolder.textView.setText(data.get(position));
        return convertView;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }

    class DeviceTypeViewHolder{

        TextView textView;

    }
}
