package com.example.firebaselistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class AdapterClass extends ArrayAdapter<ModelClass> {

    List<ModelClass> list_item;
    Context context;

    public AdapterClass(Context context,List<ModelClass> list_item) {
        super(context, R.layout.custom_item,list_item);
        this.context=context;
        this.list_item = list_item;
    }


    @Override
    public View getView(int position,View convertView,ViewGroup parent) {

        convertView= LayoutInflater.from(context).inflate(R.layout.custom_item,parent,false);

        TextView name,phone,blood;

        name=convertView.findViewById(R.id.donnerName);
        phone=convertView.findViewById(R.id.donnerPhone);
        blood=convertView.findViewById(R.id.donnerBlood_simple);

        ModelClass modelClass=list_item.get(position);
        name.setText(modelClass.getName());
        phone.setText(modelClass.getPhoneNumber());
        blood.setText(modelClass.getBloodGroup());

        return convertView;
    }
}
