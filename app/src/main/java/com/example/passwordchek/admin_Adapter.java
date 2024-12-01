package com.example.passwordchek;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

public class admin_Adapter extends FirebaseRecyclerAdapter<user_data_holder, admin_Adapter.myviewholder> {
    public admin_Adapter(@NonNull FirebaseRecyclerOptions<user_data_holder> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, @SuppressLint("RecyclerView") int position, @NonNull user_data_holder model) {
        holder.fname.setText(model.getFname() + " " + model.getLname());
        holder.email.setText(model.getEmail());
        holder.mobileno.setText(model.getNumber());

        holder.view_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.fname.getContext())
                        .setContentHolder(new ViewHolder(R.layout.detailview_user_detail))
                        .setExpanded(true, 2000)
                        .create();



                View view1 = dialogPlus.getHolderView();

                TextView name = view1.findViewById(R.id.name);
                TextView email = view1.findViewById(R.id.email);
                TextView dob = view1.findViewById(R.id.dob);
                TextView gender = view1.findViewById(R.id.gender);
                TextView mobile = view1.findViewById(R.id.mobile);
                TextView password = view1.findViewById(R.id.password);
                TextView btnback = view1.findViewById(R.id.backtext);

                btnback.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogPlus.dismiss();
                    }

                });

                ImageView delete = view1.findViewById(R.id.delete);
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(holder.fname.getContext());
                        builder.setTitle("Are You Sure?");
                        builder.setMessage("Deleted Data can't be undo");
                        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                FirebaseDatabase.getInstance().getReference().child("User_Detail")
                                        .child(getRef(position).getKey()).removeValue();

                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                Toast.makeText(holder.fname.getContext(), "Deleted Successful", Toast.LENGTH_SHORT).show();
                                dialogPlus.dismiss();

                            }
                        });
                        builder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(holder.fname.getContext(), "Cancled", Toast.LENGTH_SHORT).show();
                            }
                        });
                        builder.show();

                        //dialogPlus.dismiss();
                    }
                });



                name.setText(model.getFname() + " " + model.getLname());
                email.setText(model.getEmail());
                dob.setText(model.getDate());
                gender.setText(model.getGender());
                mobile.setText(model.getNumber());
                password.setText(model.getPassword());


                dialogPlus.show();


            }
        });

//For Update/Edit
        holder.btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.email.getContext())
                        .setContentHolder(new ViewHolder(R.layout.update_popup))
                        .setExpanded(true, 1800)
                        .create();
                View view = dialogPlus.getHolderView();
                EditText txtfname = view.findViewById(R.id.txtfname);
                EditText txtlname = view.findViewById(R.id.txtlname);
                EditText txtgender = view.findViewById(R.id.txtgender);
                EditText txtmobile = view.findViewById(R.id.txtmobile);
                Button btnUpdate = view.findViewById(R.id.btnUpdate);

                txtfname.setText(model.getFname());
                txtlname.setText(model.getLname());
                txtgender.setText(model.getGender());
                txtmobile.setText(model.getNumber());

                dialogPlus.show();
                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("fname", txtfname.getText().toString());
                        map.put("lname", txtlname.getText().toString());
                        map.put("gender", txtgender.getText().toString());
                        map.put("number", txtmobile.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("User_Detail").child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.email.getContext(), "Updated Successful", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(holder.email.getContext(), "Error", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });
            }
        });


//For Delete
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.fname.getContext());
                builder.setTitle("Are You Sure?");
                builder.setMessage("Deleted Data can't be undo");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("User_Detail")
                                .child(getRef(position).getKey()).removeValue();

                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    }
                });
                builder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(holder.fname.getContext(), "Cancled", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();

            }
        });
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_user_single_row_layout, parent, false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder {

        RelativeLayout view_user;
        Button btnDelete, btnedit;
        TextView fname, email, mobileno;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            view_user = itemView.findViewById(R.id.view_user);
            fname = itemView.findViewById(R.id.fname);
            email = itemView.findViewById(R.id.email);
            mobileno = itemView.findViewById(R.id.mobileno);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnedit = itemView.findViewById(R.id.btnedit);

        }
    }

}
