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

public class leg_fragment extends Fragment {


    public leg_fragment() {
        // Required empty public constructor
    }

    ListView listview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.leg_fragment, container, false);

        listview = view.findViewById(R.id.listview);

        int[] legimageId = {R.drawable.legs1, R.drawable.legs2, R.drawable.legs3, R.drawable.legs4, R.drawable.legs5, R.drawable.legs6, R.drawable.legs7,R.drawable.legs8,R.drawable.legs9,R.drawable.legs10};
        String[] legname = {"DUMBBELL SQUAT", "LEG EXTENSION", "CALF RAISE", "SQUAT", "LEG CURL", "LEG PRESS", "DEADLIFT","Lever Seated Calf Raise","Hack Squats Machine","Dumbbell Cossack Squat"};
        String[] legtime = {"5(SET)|4(REPS)|1min(REST)", "3(SET)|20(REPS)|10s(REST)", "5(SET)|12(REPS)|10s(REST)", "3(SET)|13(REPS)|2min(REST)" , "3(SET)|12(REPS)|10s(REST)", "3(SET)|12(REPS)|1min(REST)", "3(SET)|12(REPS)|1min(REST)","3(SET)|10-12(REPS)|1min(REST)","3(SET)|12(REPS)|1min(REST)","3(SET)|12(REPS)|1min(REST)"};
        String[] legdescription =
                {"Dumbbell fly exercise targets the sternal heads of your pectoralis major muscles, which are found in your chest, but also strengthens your triceps, deltoids, biceps, wrist flexors and brachialis muscles."
                , "LEVER INCLINE CHEST PRESS exercise targets the sternal heads of your pectoralis major muscles, which are found in your chest, but also strengthens your triceps, deltoids, biceps, wrist flexors and brachialis muscles."
                , "Since the target muscle group of this exercise designed to isolate is the calf muscles, it can maximize muscle development by focusing more on the muscles in this area."
                , "The barbell squat is a strength training exercise that targets the muscles of the lower body, particularly the quadriceps, hamstrings, and glutes."
                , "The bench press is a popular strength training exercise that primarily targets the chest muscles (pectoralis major and minor)."
                , "The leg press, is a type of strength training exercise. It’s an effective move for strengthening your quadriceps, which are in the front of your upper legs."
                , "The deadlift is a compound exercise that involves lifting a weight from the ground to a standing position. It is often considered one of the best exercises for building overall strength and power, as it works multiple muscle groups throughout the body."
                ,"Place forefeet on machines foot plates with heels extending off. Position lower thighs under machine pads.\n" +
                        "\n" +
                        "Push from the balls of your feet, raising your heels as high as you can. Pause, then slowly return to the starting position."
                ,"The focus of the hack squat machine is the quadriceps muscles. It is also a machine exercise designed to train and strengthen the entire lower body, including the hips, hamstrings, quads, and core muscles. Since its application is more secure, beginners may prefer it.\n" +
                        "\n" +
                        "The hack squat machine is useful because the weight load is distributed at an angle to your center of mass. This reduces stress on the spine and allows more weight to be lifted overall. You can include it in your training programs to increase functional strength and improve the stabilizing muscles of the legs."
                ,"The Dumbbell Cossack Squat is a strength-training exercise that focuses on improving lower-body mobility, flexibility, and strength. The movement requires significant hip mobility, which can help improve flexibility in the hips and groin area. It’s particularly effective for developing unilateral strength since it focuses on one leg at a time."};

        ArrayList<lag> legArrayList = new ArrayList<>();

        for (int i = 0; i < legimageId.length; i++) {
            lag lag = new lag(legname[i], legtime[i], legdescription[i], legimageId[i]);
            legArrayList.add(lag);
        }

        legListAdapter legListAdapter = new legListAdapter(getContext(), legArrayList);

        listview.setAdapter(legListAdapter);
        listview.setClickable(true);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getContext(), viewgym_exercises.class);
                i.putExtra("name", legname[position]);
                i.putExtra("time", legtime[position]);
                i.putExtra("description", legdescription[position]);
                i.putExtra("imageid", legimageId[position]);
                startActivity(i);
            }
        });

        return view;
    }
}