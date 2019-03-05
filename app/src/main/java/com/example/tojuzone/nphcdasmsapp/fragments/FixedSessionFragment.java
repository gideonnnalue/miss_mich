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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tojuzone.nphcdasmsapp.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FixedSessionFragment extends Fragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 2;

    private Dialog mDialog;
    private Dialog loadingDialog;

    private Button sendMessageBtn;

    private EditText a, b, c, d, e, f, g, h, i , j, k ,l ,m ,n , o, p, q;
    private TextView err;

    private String hfCode;

    private String msg;
    private String phoneNum;

    private String SENT = "SMS_SENT";
    private String DELIVERED = "SMS_DELIVERED";
    PendingIntent sentPI, deliveredPI;
    BroadcastReceiver smsSentReciever, smsDeliveredReciever;

    public FixedSessionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView =  inflater.inflate(R.layout.fragment_fixed_session, container, false);

        setupSharedPreferences();


        a = mView.findViewById(R.id.fixed_a);
        b = mView.findViewById(R.id.fixed_b);
        c = mView.findViewById(R.id.fixed_c);
        d = mView.findViewById(R.id.fixed_d);
        e = mView.findViewById(R.id.fixed_e);
        f = mView.findViewById(R.id.fixed_f);
        g = mView.findViewById(R.id.fixed_g);
        h = mView.findViewById(R.id.fixed_h);
        i = mView.findViewById(R.id.fixed_i);
        j = mView.findViewById(R.id.fixed_j);
        k = mView.findViewById(R.id.fixed_k);
        l = mView.findViewById(R.id.fixed_l);
        m = mView.findViewById(R.id.fixed_m);
        n = mView.findViewById(R.id.fixed_n);
        o = mView.findViewById(R.id.fixed_o);
        p = mView.findViewById(R.id.fixed_p);
        q = mView.findViewById(R.id.fixed_q);



        err = mView.findViewById(R.id.fixed_err);

        sentPI = PendingIntent.getBroadcast(getContext(), 0, new Intent(SENT), 0);
        deliveredPI = PendingIntent.getBroadcast(getContext(), 0, new Intent(DELIVERED), 0);

        mDialog = new Dialog(getActivity());
        loadingDialog = new Dialog(getActivity());
        loadingDialog.setCancelable(false);

        sendMessageBtn = mView.findViewById(R.id.fixedMsgBtn);
        sendMessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSms();
            }
        });

        return mView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(getContext()).unregisterOnSharedPreferenceChangeListener(this);
    }

    protected void sendSms() {
        String _a, _b, _c, _d, _e, _f, _g, _h, _i, _j, _k, _l, _m, _n, _o, _p, _q;
        _a = a.getText().toString();
        _b = b.getText().toString();
        _c = c.getText().toString();
        _d = d.getText().toString();
        _e = e.getText().toString();
        _f = f.getText().toString();
        _g = g.getText().toString();
        _h = h.getText().toString();
        _i = i.getText().toString();
        _j = j.getText().toString();
        _k = k.getText().toString();
        _l = l.getText().toString();
        _m = m.getText().toString();
        _n = n.getText().toString();
        _o = o.getText().toString();
        _p = p.getText().toString();
        _q = q.getText().toString();

        if (_a.isEmpty() || _b.isEmpty() || _c.isEmpty() || _d.isEmpty() || _e.isEmpty() || _f.isEmpty() || _g.isEmpty() || _h.isEmpty() || _i.isEmpty() || _j.isEmpty() || _k.isEmpty() || _l.isEmpty() || _m.isEmpty() || _n.isEmpty() || _o.isEmpty() || _p.isEmpty() || _q.isEmpty()) {
            err.setText("*Ensure every field is filled");
        } else {
            err.setText("");
            phoneNum = "+2348138378885";
            msg = hfCode + " FS A" + _a + " B" + _b + " C" + _c + " D" + _d + " E" + _e + " F" + _f + " G" + _g + " H" + _h + " I" + _i + " J" + _j + " K" + _k + " L" + _l + " M" + _m + " N" + _n + " O" + _o + " P" + _p + " Q" + _q;

            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.SEND_SMS)) {

                } else {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);

                }
            } else {

                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phoneNum, null, msg, sentPI, deliveredPI);
                showLoading();
                a.setText("");
                b.setText("");
                c.setText("");
                d.setText("");
                e.setText("");
                f.setText("");
                g.setText("");
                h.setText("");
                i.setText("");
                j.setText("");
                k.setText("");
                l.setText("");
                m.setText("");
                n.setText("");
                o.setText("");
                p.setText("");
                q.setText("");
            }

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

    private void setupSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        hfCode = sharedPreferences.getString("hfCode", "XW26");
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if ( grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNum, null, msg, sentPI, deliveredPI);
                    showLoading();

                    err.setText("");
                    a.setText("");
                    b.setText("");
                    c.setText("");
                    d.setText("");
                    e.setText("");
                    f.setText("");
                    g.setText("");
                    h.setText("");
                    i.setText("");
                    j.setText("");
                    k.setText("");
                    l.setText("");
                    m.setText("");
                    n.setText("");
                    o.setText("");
                    p.setText("");
                    q.setText("");
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
        if (key.equals("hfCode")) {
            hfCode = sharedPreferences.getString("hfCode", "XW26");
        }
    }
}
