package com.example.passwordchek;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class armsListAdapter extends ArrayAdapter<arms> {
    public armsListAdapter(Context context, ArrayList<arms> userarmsArrayList) {
        super(context, R.layout.arms_list_item, userarmsArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        arms arms = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.arms_list_item, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.arms_pic);
        TextView exename = convertView.findViewById(R.id.armsexename);
        TextView exetime = convertView.findViewById(R.id.armsexetime);

        imageView.setImageResource(arms.armsimageId);
        exename.setText(arms.armsname);
        exetime.setText(arms.armstime);

        return convertView;
    }

}
