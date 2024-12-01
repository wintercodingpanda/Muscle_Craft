package com.example.passwordchek;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import de.hdodenhof.circleimageview.CircleImageView;

public class tipAdapter extends FirebaseRecyclerAdapter<diet_tips_Model, tipAdapter.myViewHolder> {

    public tipAdapter(@NonNull FirebaseRecyclerOptions<diet_tips_Model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull diet_tips_Model model) {
        holder.Title.setText(model.getTitle());
        Glide.with(holder.img.getContext())
                .load(model.getImageUrl())
                .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(R.drawable.bottomicon)
                .into(holder.img);
        holder.btnReadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.img.getContext())
                        .setContentHolder(new ViewHolder(R.layout.read_more_popup))
                        .setExpanded(true, 2000)
                        .create();

                View view1 = dialogPlus.getHolderView();
                CircleImageView img11 = view1.findViewById(R.id.img11);
                TextView booktext1 = view1.findViewById(R.id.titletext1);
                TextView desctext1 = view1.findViewById(R.id.desctext1);
                Button btnBackIt = view1.findViewById(R.id.btnBackIt);
                btnBackIt.setOnClickListener(view2 -> dialogPlus.dismiss());
                booktext1.setText(model.getTitle());
                desctext1.setText(model.getDescription());
                Glide.with(holder.img.getContext())
                        .load(model.getImageUrl())
                        .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                        .circleCrop()
                        .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark_normal)
                        .into(img11);
                dialogPlus.show();
            }
        });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tips_item, parent, false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder {
        CircleImageView img;
        TextView Title, Description;
        Button btnReadMore;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img1);
            Title = itemView.findViewById(R.id.tipTitle);
            btnReadMore = itemView.findViewById(R.id.btnReadMore);
        }
    }
}
