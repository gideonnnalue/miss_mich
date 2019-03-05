package com.example.tojuzone.nphcdasmsapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class HFConfigActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener{

    private Button saveHFCode;
    private EditText hfCodeValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hfconfig);

        saveHFCode = findViewById(R.id.hfCodeSaveBtn);
        hfCodeValue = findViewById(R.id.hf_config_edit);


        //        sharedPreferences.edit().putString("username", "rob").apply();

//        String username = sharedPreferences.getString("username", "");

//        Log.i("username", username);
//        Toast.makeText(this, "username: " + username, Toast.LENGTH_SHORT).show();


        ActionBar actionBar = this.getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        saveHFCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSharedPreference(hfCodeValue.getText().toString());
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }

    public void setSharedPreference(String value) {
//        SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.tojuzone.nphcdasmsapp", Context.MODE_PRIVATE);
//        sharedPreferences.edit().putString("hfCode", value).apply();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("hfCode", value);
        editor.apply();


        NavUtils.navigateUpFromSameTask(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

    }
}
