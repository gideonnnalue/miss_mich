package com.example.tojuzone.nphcdasmsapp.fragments;


import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.tojuzone.nphcdasmsapp.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class WeeklySessionsFragment extends Fragment {

    private Dialog mDialog;
    private Button sendMessageBtn;

    public WeeklySessionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_weekly_sessions, container, false);


        mDialog = new Dialog(getActivity());
        sendMessageBtn = mView.findViewById(R.id.weeklyMsgBtn);
        sendMessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageResponse(v, true);
            }
        });

        return mView;

    }

    private void messageResponse(View v, boolean isSuccesful) {
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

}
