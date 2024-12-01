package com.example.passwordchek;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class arms_fregment extends Fragment {

    public arms_fregment() {
        // Required empty public constructor
    }

    ListView armslistview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.arms_fregment, container, false);
        armslistview = view.findViewById(R.id.armslistview);

        int[] armsimageId = {R.drawable.arms1, R.drawable.arms2, R.drawable.arms3, R.drawable.arms4, R.drawable.arms5, R.drawable.arms6, R.drawable.arms7};
            String[] armsname = {"ROPE PUSHDOWN", "DUMBBELL ROW", "DUMBBELL CURL", "SINGLE ARM CURL", "LEVER PREACHER CURL", "ASISTED TRICEPS DIPS", "PUSH-UP"};
        String[] armstime = {"3(SET)|12(REPS)|60s(REST)", "3(SET)|20(REPS)", "3(SET)|10(REPS)|30s(REST)", "4(SET)|15(REPS)|10s(REST)", "3(SET)|12(REPS)", "3(SET)|10(REPS)|30s(REST)", "3(SET)|20(REPS)|60s(REST)"};
        String[] armsdescription =
                {"Dumbbell fly exercise targets the sternal heads of your pectoralis major muscles, which are found in your chest, but also strengthens your triceps, deltoids, biceps, wrist flexors and brachialis muscles."
                        , "LEVER INCLINE CHEST PRESS exercise targets the sternal heads of your pectoralis major muscles, which are found in your chest, but also strengthens your triceps, deltoids, biceps, wrist flexors and brachialis muscles."
                        , "Dumbbell curl is a basic strength training exercise to build muscle and strength in the arms. Specifically, the dumbbell curl works all the muscles in the front of the arm."
                        , "The high cable single arm bicep curl is a variation of the bicep curl exercise that uses a cable machine to provide resistance. It is a unilateral exercise, meaning it targets one arm at a time, and primarily works the biceps muscle group in the upper arm."
                        , "The bench press is a popular strength training exercise that primarily targets the chest muscles (pectoralis major and minor)."
                        , "Triceps Dips are a bodyweight exercise that primarily works your triceps, chest and shoulders. The chest and biceps are also used to help you lift yourself up."
                        ,"A push-up is a calisthenic exercise that involves lifting and lowering your body using your arms. They are commonly used to build upper body strength, improve core stability, and enhance overall physical fitness."};

        ArrayList<arms> userarmsArrayList=new ArrayList<>();
        for (int i = 0; i < armsimageId.length; i++) {

            arms arms = new arms(armsname[i], armstime[i], armsdescription[i], armsimageId[i]);
            userarmsArrayList.add(arms);
        }

        armsListAdapter armsListAdapter = new armsListAdapter(getContext(), userarmsArrayList );

        armslistview.setAdapter(armsListAdapter);
        armslistview.setClickable(true);
        armslistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getContext(), viewgym_exercises.class);
                i.putExtra("name", armsname[position]);
                i.putExtra("time", armstime[position]);
                i.putExtra("description", armsdescription[position]);
                i.putExtra("imageid", armsimageId[position]);
                startActivity(i);

            }
        });

        return view;
    }
}