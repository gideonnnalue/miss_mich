package com.example.tojuzone.nphcdasmsapp.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.tojuzone.nphcdasmsapp.HFConfigActivity;
import com.example.tojuzone.nphcdasmsapp.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements SharedPreferences.OnSharedPreferenceChangeListener{

    private Button changeHFCode;
    private TextView hfValue;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_home, container, false);

        changeHFCode = mView.findViewById(R.id.hf_change_btn);
        hfValue = mView.findViewById(R.id.hf_code_text_value);

        getSharedPreference();

        changeHFCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent hfConfigActivity = new Intent(getContext(), HFConfigActivity.class);
                startActivity(hfConfigActivity);
            }
        });


        return mView;
    }

    public void getSharedPreference() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        hfValue.setText(sharedPreferences.getString("hfCode", "XW26"));
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("hfCode")) {
            hfValue.setText(sharedPreferences.getString("hfCode", "XW26"));

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(getContext()).unregisterOnSharedPreferenceChangeListener(this);

    }
}
