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

public class abs_fragment extends Fragment {

    public abs_fragment() {
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.abs_fragment, container, false);

        listview = view.findViewById(R.id.listview);

        int[] absimageId = {R.drawable.abs1, R.drawable.abs2, R.drawable.abs3, R.drawable.abs4, R.drawable.abs5, R.drawable.abs6, R.drawable.abs7, R.drawable.abs8, R.drawable.abs9, R.drawable.abs10, R.drawable.abs11};
        String[] absname = {"Crunch Machine", "Captains Chair Leg Raise", "Decline Sit up", "Stability Ball Knee Tuck", "Hanging Side Knee Raises", "Weighted Crunch", "Standing Cable Crunch","Barbell Rollout","Ab-Wheel Rollout","Bench Side Bend","Ab Coaster Machine"};
        String[] abstime = {"5(SET)|4(REPS)|1min(REST)", "3(SET)|20(REPS)|10s(REST)", "5(SET)|12(REPS)|10s(REST)", "3(SET)|13(REPS)|2min(REST)", "3(SET)|10(REPS)|25s(REST)", "3(SET)|12(REPS)|1min(REST)", "3(SET)|10(REPS)|25sec(REST)", "3(SET)|15(REPS)|45-60s(REST)", "3(SET)|12(REPS)|10s(REST)", "3(SET)|12(REPS)|10s(REST)", "3(SET)|10(REPS)|1min(REST)"};
        String[] absdescription =
                {"1.Select a light resistance and sit down on the ab machine placing your feet under the pads provided and grabbing the top handles. ...\n" +
                        "2.At the same time, begin to lift the legs up as you crunch your upper torso. ...\n" +
                        "3.After a second pause, slowly return to the starting position as you breathe in."
                        , "This exercise that works the hip flexors improves your core muscles, especially the lower abdominal muscles. In addition, the back, chest and trapezius muscles act as stabilizers during the exercise.\n" +
                        "\n"+
                        "According to a study by the American Exercise Council, this exercise is one of the most effective exercises for strengthening the obliques and the second most effective for strengthening the abdominal muscles."
                        , "Decline sit-ups target the rectus abdominus and oblique muscles to strengthen the core muscles. It is an exercise that you can apply to shape the upper abdominal muscles and six pack muscles."
                        , "1- Assume a plank, with your hands on the floor below the shoulders and your feet on an exercise ball, supported by your toes. Keep your elbows straight, but not locked, and the shoulders relaxed. Hold your back straight and ensure your pelvis is not tipping downward.\n" +
                        "\n" +
                        "2- Keeping your body straight and your hands fixed in position, slowly push foward with your feet, bending at the hip. Push as far as you can, keeping control of the movement with your core.\n" +
                        "\n" +
                        "3- Hold the position for a few seconds, then return to the start position with a slow, controlled movement."
                        , "Start hanging from a bar with your hands shoulder-width apart. Raise both knees up and to the side of your body. Repeat with the other side."
                        , "The Weighted Crunch movement is an advanced exercise applied with the help of a weight to shape, tighten and strengthen the abdominal muscles. In this exercise you do with weight, you can have more interaction on the sixpack muscles."
                        , "1.Set the cable machine to a high position and attach a rope or handle to the cable.\n\n" +
                        "2.Stand with your back to the machine with your feet shoulder-width apart.\n\n" +
                        "3.Grab the rope or handle with both hands, placing your hands near your ears or temples.\n\n" +
                        "4.Take a step or two away from the machine to create tension in the cable.\n\n" +
                        "5.Position yourself so that your elbows are pointing out to the sides and your upper body is upright.\n\n" +
                        "6.Initiate the movement by bending at the waist and bringing your elbows and head downwards, towards your knees.\n\n" +
                        "7.As you crunch down, focus on contracting your abdominal muscles and exhaling.\n\n" +
                        "8.Hold the crunch position briefly, squeezing your abs.\n\n" +
                        "9.Slowly return to the starting position by extending your torso back up, allowing your arms to fully extend.\n\n" +
                        "10.Repeat for the desired number of repetitions."
                        ,"1. Kneel on the floor with your feet backing backwards slightly raised off the floor\n" +
                        "\n" +
                        "2. Grip the bar with your hands slightly wider than shoulder width apart\n" +
                        "\n" +
                        "3. Start with the barbell directly underneath your shoulders and gently roll the bar away from you as far as possible and then return to the starting position"
                ,"The ab wheel rollout exercise is an effective exercise as it works all the muscles of the upper body, especially the hip flexors and core muscles. Compared to traditional ab exercises like crunches or sit-ups, the ab roller exercises more challenging and effective.\n" +
                        "\n" +
                        "The Ab Wheel rollout exercise is a compound move. This means it activates multiple muscle groups at once. The ab wheel rollout can give you a better abdominal workout than traditional flexion exercises, allowing you to build stronger and more defined abs."
                ,"1- Lie sideways on the Roman chair; adjust it so that your upper body can pivot comfortably at your hips toward the floor.\n" +
                        "\n" +
                        "2- Lean slowly sideways toward the floor as far as is comfortable, taking care not to lean forward or backward. Breathe in on your descent.\n" +
                        "\n" +
                        "3- Pause at the edge of the movement, then gently raise your body to the start position. Repeat as required and switch sides.\n\n" +
                        "Bench Side Bend – Benefits:-\n" +
                        "This exercise, aimed at oblique muscles, is highly effective for firming and developing abdominal fat."
                ,"Benefits of Ab Coaster Machine:- \n" +
                        "Improved abdominal strength and definition, Reduced strain on the neck and lower back, Increased range of motion, Ability to target all areas of the abdominal muscles"};

        ArrayList<abs> absArrayList = new ArrayList<>();
        for (int i = 0; i < absimageId.length; i++) {
            abs abs = new abs(absname[i], abstime[i], absdescription[i], absimageId[i]);
            absArrayList.add(abs);
        }

        absListAdapter absListAdapter = new absListAdapter(getActivity(), absArrayList);
        listview.setAdapter(absListAdapter);
        listview.setClickable(true);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getContext(), viewgym_exercises.class);
                i.putExtra("name", absname[position]);
                i.putExtra("time", abstime[position]);
                i.putExtra("description", absdescription[position]);
                i.putExtra("imageid", absimageId[position]);
                startActivity(i);
            }
        });


        return view;
    }
}