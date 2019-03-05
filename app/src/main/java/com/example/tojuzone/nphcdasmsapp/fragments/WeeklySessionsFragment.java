package com.example.tojuzone.nphcdasmsapp.fragments;


import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tojuzone.nphcdasmsapp.R;
import com.example.tojuzone.nphcdasmsapp.activities.DashboardActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class WeeklySessionsFragment extends Fragment implements SharedPreferences.OnSharedPreferenceChangeListener{

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;
    private Dialog mDialog;
    private Dialog loadingDialog;

    private Button sendMessageBtn;

    private EditText weeklyA;
    private EditText weeklyB;

    private TextView error;

    private String msg;
    private String phoneNum;

    private String hfCode;

    String testValue;
    String states;

    private String SENT = "SMS_SENT";
    private String DELIVERED = "SMS_DELIVERED";
    PendingIntent sentPI, deliveredPI;
    BroadcastReceiver smsSentReciever, smsDeliveredReciever;

    public WeeklySessionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_weekly_sessions, container, false);

        setupSharedPreferences();

        weeklyA = mView.findViewById(R.id.week_a_edit);
        weeklyB = mView.findViewById(R.id.week_b_edit);

        error = mView.findViewById(R.id.week_err_txt);

        sentPI = PendingIntent.getBroadcast(getContext(), 0, new Intent(SENT), 0);
        deliveredPI = PendingIntent.getBroadcast(getContext(), 0, new Intent(DELIVERED), 0);


        mDialog = new Dialog(getActivity());
        loadingDialog = new Dialog(getActivity());
        loadingDialog.setCancelable(false);

        sendMessageBtn = mView.findViewById(R.id.weeklyMsgBtn);
        sendMessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), weeklyA.getText().toString(), Toast.LENGTH_SHORT).show();
                if (weeklyA.getText().toString().isEmpty() || weeklyB.getText().toString().isEmpty()) {
                    error.setText("*Ensure every field is filled");

                } else {
                    sendSms();

                }
            }
        });

        return mView;

    }

    private void setupSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        hfCode = sharedPreferences.getString("hfCode", "XW26");

        Boolean shown = sharedPreferences.getBoolean("show", true);
        testValue = shown.toString();

        states = "Abuja";

        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        getActivity().unregisterReceiver(smsSentReciever);
        getActivity().unregisterReceiver(smsDeliveredReciever);
    }

    @Override
    public void onResume() {
        super.onResume();


        smsSentReciever = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        loadingDialog.dismiss();
                        Toast.makeText(getContext(), "SMS Sent", Toast.LENGTH_SHORT).show();
                        messageResponse(true);
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        loadingDialog.dismiss();
                        Toast.makeText(getContext(), "Error sending message...", Toast.LENGTH_SHORT).show();
                        messageResponse(false);
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        loadingDialog.dismiss();
                        Toast.makeText(getContext(), "No service", Toast.LENGTH_SHORT).show();
                        messageResponse(false);
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        loadingDialog.dismiss();
                        Toast.makeText(getContext(), "Null PDU!", Toast.LENGTH_SHORT).show();
                        messageResponse(false);
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        loadingDialog.dismiss();
                        Toast.makeText(getContext(), "Radio Off", Toast.LENGTH_SHORT).show();
                        messageResponse(false);
                        break;
                }
            }
        };



        smsDeliveredReciever = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(getContext(), "SMS delivered", Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(getContext(), "SMS not delivered", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };

        getActivity().registerReceiver(smsSentReciever, new IntentFilter(SENT));
        getActivity().registerReceiver(smsDeliveredReciever, new IntentFilter(DELIVERED));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(getContext()).unregisterOnSharedPreferenceChangeListener(this);


    }

    protected void sendSms() {
            error.setText("");
            phoneNum = "+2348138378885";
            msg = hfCode + " WK A" + weeklyA.getText().toString() + " B" + weeklyB.getText().toString();

            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.SEND_SMS)) {

                } else {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
                }
            } else {

                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phoneNum, null, msg, sentPI, deliveredPI);
                showLoading();
                weeklyA.setText("");
                weeklyB.setText("");

            }


    }

    private void showLoading() {
        loadingDialog.setContentView(R.layout.message_loading);
        loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loadingDialog.show();

    }


    private void messageResponse(boolean isSuccesful) {
        if (isSuccesful) {
            mDialog.setContentView(R.layout.message_success_pop_up);

            Button okBtn = mDialog.findViewById(R.id.msg_ok_btn);
            okBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.dismiss();
                }
            });

            mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mDialog.show();
        } else {
            mDialog.setContentView(R.layout.message_failed_pop_up);

            Button tryAgainBtn = mDialog.findViewById(R.id.msg_try_again_btn);
            tryAgainBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.dismiss();
                }
            });

            mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mDialog.show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if ( grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNum, null, msg, sentPI, deliveredPI);
                    showLoading();
                    error.setText("");
                    weeklyA.setText("");
                    weeklyB.setText("");
                } else {
                    messageResponse(false);
                    Toast.makeText(getContext(), "SMS failed, please try agan.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("show")) {
            Boolean shown = sharedPreferences.getBoolean("show", true);
            testValue = shown.toString();
        }

        if (key.equals("hfCode")) {
            hfCode = sharedPreferences.getString("hfCode", "XW26");
        }
    }
}
