package com.example.tojuzone.nphcdasmsapp.fragments;


import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tojuzone.nphcdasmsapp.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FixedSessionFragment extends Fragment {

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 2;

    private Dialog mDialog;
    private Button sendMessageBtn;

    private EditText a, b, c, d, e, f, g, h, i , j, k ,l ,m ,n , o, p, q;

    private String msg;
    private String phoneNum;

    public FixedSessionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView =  inflater.inflate(R.layout.fragment_fixed_session, container, false);

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

        mDialog = new Dialog(getActivity());
        sendMessageBtn = mView.findViewById(R.id.fixedMsgBtn);
        sendMessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSms();
            }
        });

        return mView;
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
        phoneNum = "+2348138378885";
        msg = "Z2F FS A" + _a + " B" + _b + " C" + _c + " D" + _d + " E" + _e + " F" + _f + " G" + _g + " H" + _h + " I" + _i + " J" + _j + " K" + _k + " L" + _l + " M" + _m + " N" + _n + " O" + _o + " P" + _p + " Q" + _q;

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.SEND_SMS)) {

            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);

                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phoneNum, null, msg, null, null);
                messageResponse(true);
            }
        } else {

            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNum, null, msg, null, null);
            messageResponse(true);
        }


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
                    smsManager.sendTextMessage(phoneNum, null, msg, null, null);
                    messageResponse(true);
                    Toast.makeText(getContext(), "SMS sent", Toast.LENGTH_SHORT).show();
                } else {
                    messageResponse(false);
                    Toast.makeText(getContext(), "SMS failed, please try agan.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
