package com.example.passwordchek;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import pl.droidsonroids.gif.GifImageView;

public class home_workout_adapter extends FirebaseRecyclerAdapter<home_workout_model, home_workout_adapter.homeViewHolder> {

    public home_workout_adapter(@NonNull FirebaseRecyclerOptions<home_workout_model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull homeViewHolder holder, int position, @NonNull home_workout_model model) {
        holder.exe_Name.setText(model.getTitle());
        holder.exe_Duration.setText(model.getDuration());
        Glide.with(holder.exe_Img.getContext())
                .load(model.getImageUrl())
                .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.exe_Img);


        holder.cardClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.exe_Img.getContext())
                        .setContentHolder(new ViewHolder(R.layout.detail_home_exer_layout))
                        .setExpanded(true, ViewGroup.LayoutParams.MATCH_PARENT)
                        .create();


                View view1 = dialogPlus.getHolderView();

                ImageView homeGifImg = view1.findViewById(R.id.homeGifImg);
                TextView homeExeName = view1.findViewById(R.id.homeExeName);
                TextView homeExeDuration = view1.findViewById(R.id.homeExeDuration);
                TextView homeExeDescription = view1.findViewById(R.id.homeExeDescription);


                TextView textback = view1.findViewById(R.id.textback);
                textback.setOnClickListener(v -> dialogPlus.dismiss());

                homeExeName.setText(model.getTitle());
                homeExeDuration.setText(model.getDuration());
                homeExeDescription.setText(model.getDescription());

                Glide.with(holder.exe_Img.getContext())
                        .load(model.getImageUrl())
                        .placeholder(R.drawable.abs2)
                        .into(homeGifImg);

//                Glide.with(holder.exe_Img.getContext()
//                        .load(model.getImageUrl())
//                        .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
//                        .circleCrop()
//                        .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark_normal)
//                        .into(homeGifImg);
                dialogPlus.show();

            }

        });
    }


    @NonNull
    @Override
    public homeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_workout_single_row_layout, parent, false);
        return new homeViewHolder(view);
    }

    class homeViewHolder extends RecyclerView.ViewHolder {
        GifImageView exe_Img;
        TextView exe_Name, exe_Duration;
        RelativeLayout cardClick;

        public homeViewHolder(@NonNull View itemView) {
            super(itemView);
            exe_Img = itemView.findViewById(R.id.exe_Img);
            exe_Name = itemView.findViewById(R.id.exe_Name);
            exe_Duration = itemView.findViewById(R.id.exe_Duration);
            cardClick = itemView.findViewById(R.id.cardClick);
        }
    }

}
