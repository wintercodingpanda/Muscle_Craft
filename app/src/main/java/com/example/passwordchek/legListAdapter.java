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

public class legListAdapter extends ArrayAdapter<lag> {

    public legListAdapter(Context context, ArrayList<lag> legArrayList) {
        super(context, R.layout.exercise_list_item, legArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        lag lag = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.exercise_list_item, parent, false);
        }
        ImageView imageView = convertView.findViewById(R.id.exe_pic);
        TextView exename = convertView.findViewById(R.id.exename);
        TextView exetime = convertView.findViewById(R.id.exetime);

        imageView.setImageResource(lag.legimageId);
        exename.setText(lag.legname);
        exetime.setText(lag.legtime);

        return convertView;
    }
}
