package com.tm.teameverest.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.tm.teameverest.DonateUsActivity;
import com.tm.teameverest.DonationPage;
import com.tm.teameverest.EventsActivity;
import com.tm.teameverest.R;

/**
 * Created by user on 12/11/16.
 */
public class Fragment_Home extends Fragment {

    private RelativeLayout rel_events;
    private RelativeLayout rel_donate_us;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null);

        initializeUI(view);

        return view;
    }

    private void initializeUI(View view) {

        rel_events = (RelativeLayout) view.findViewById(R.id.rel_events);
        rel_donate_us = (RelativeLayout) view.findViewById(R.id.rel_donate_us);

        rel_events.setOnClickListener(onClickListener);
        rel_donate_us.setOnClickListener(onClickListener);

    }

    //------------------------------Onclick Listener--------------------------------//
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.rel_events:
                    Intent intent = new Intent(getActivity(), EventsActivity.class);
                    startActivity(intent);
                    break;
                case R.id.rel_donate_us:
                    Intent intent_donate = new Intent(getActivity(), DonationPage.class);
                    startActivity(intent_donate);
                    break;

            }
        }
    };
}
