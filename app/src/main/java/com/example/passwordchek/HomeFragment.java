package com.example.passwordchek;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }

    BottomNavigationView bnView;
    FrameLayout maincontainer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        bnView = view.findViewById(R.id.bnView);
        maincontainer = view.findViewById(R.id.maincontainer);

        loadFragment(new chest_fragment(), 0);
        bnView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.chest) {
                    loadFragment(new chest_fragment(), 1);
                } else if (itemId == R.id.legs) {
                    loadFragment(new leg_fragment(), 1);
                } else if (itemId == R.id.arms) {
                    loadFragment(new arms_fregment(), 1);
                } else {
                    loadFragment(new abs_fragment(), 1);
                }
                return true;
            }
        });
        return view;
    }

    private void loadFragment(Fragment fragment, int flag) {
        FragmentManager fm = requireActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (flag == 0) {
            ft.add(R.id.maincontainer, fragment);
        } else {
            ft.replace(R.id.maincontainer, fragment);
        }
        ft.commit();

    }

}