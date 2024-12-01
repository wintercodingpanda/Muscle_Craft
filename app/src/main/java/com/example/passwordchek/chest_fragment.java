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


public class chest_fragment extends Fragment {

    public chest_fragment() {
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
        View view = inflater.inflate(R.layout.chest_fragment, container, false);
        listview = view.findViewById(R.id.listview);

        int[] imageId = {R.drawable.chest1, R.drawable.chest2, R.drawable.chest3, R.drawable.chest4, R.drawable.chest5, R.drawable.chest6, R.drawable.chest7, R.drawable.chest8};
        String[] name = {"DUMBBELL FLY", "LEVER INCLINE CHEST PRESS", "MACHINE FLY", "CHEST DIPS", "BENCH PRESS", "PUSH-UP","Lever Shoulder Press","Lat Pulldown"};
        String[] time = {"4(SET)|12(REPS)|90s(REST)", "2(SET)|10(REPS)|60s(REST)", "4(SET)|12(REPS)|90s(REST)", "3(SET)|6(REPS)|1m(REST)", "4(SET)|12(REPS)|2min(REST)", "2(SET)|10(REPS)|20s(REST)","2(SET)|10(REPS)|1min(REST)","3(SET)|8-10(REPS)|1min(REST)"};
        String[] description =
                {"Dumbbell fly exercise targets the sternal heads of your pectoralis major muscles, which are found in your chest, but also strengthens your triceps, deltoids, biceps, wrist flexors and brachialis muscles."
                        , "LEVER INCLINE CHEST PRESS exercise targets the sternal heads of your pectoralis major muscles, which are found in your chest, but also strengthens your triceps, deltoids, biceps, wrist flexors and brachialis muscles."
                        , "Machine fly exercise will help you build fully defined pecs. It is one of the best exercises you should do If you want a defined and more shred upper chest muscle."
                        , "Chest dips are a bodyweight exercise that target the chest, triceps, and shoulders. They are performed using parallel bars or dip bars, and involve lowering your body by bending your elbows and leaning forward slightly, and then pushing yourself back up by straightening your arms."
                        , "The bench press is a popular strength training exercise that primarily targets the chest muscles (pectoralis major and minor)."
                        , "A push-up is a calisthenic exercise that involves lifting and lowering your body using your arms. They are commonly used to build upper body strength, improve core stability, and enhance overall physical fitness."
                ,"Lever shoulder press is one of the effective exercises used to develop and strengthen the shoulder muscles. The lever machine provides a guided range of motion, allowing you to focus on proper form and reducing the risk of injury. The lever shoulder press can be performed with different grips and hand positions, providing variety to your shoulder training."
                ,"The lat pulldown is a pulling exercise that primarily targets the latissimus dorsi muscles (commonly known as “lats”) in your back. It involves pulling a cable bar or handle down towards your chest while seated on a machine specifically designed for this exercise."};


        ArrayList<chest> userArrayList = new ArrayList<>();

        for (int i = 0; i < imageId.length; i++) {

            chest chest = new chest(name[i], time[i], description[i], imageId[i]);
            userArrayList.add(chest);

        }

        ListAdapter listAdapter = new ListAdapter(getContext(), userArrayList);

        listview.setAdapter(listAdapter);
        listview.setClickable(true);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(getContext(), viewgym_exercises.class);
                i.putExtra("name", name[position]);
                i.putExtra("time", time[position]);
                i.putExtra("description", description[position]);
                i.putExtra("imageid", imageId[position]);
                startActivity(i);

            }
        });
        return view;
    }
}