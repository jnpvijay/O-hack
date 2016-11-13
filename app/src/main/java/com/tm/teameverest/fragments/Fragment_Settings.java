package com.tm.teameverest.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appinvite.AppInviteInvitation;
import com.tm.teameverest.R;
import com.tm.teameverest.utils.AppPreference;

/**
 * Created by user on 12/11/16.
 */
public class Fragment_Settings extends Fragment implements CompoundButton.OnCheckedChangeListener {

    private static final int REQUEST_INVITE = 1991;
    private SwitchCompat switch_notifications;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, null);

        initializeUI(view);

        return view;
    }

    private void initializeUI(View view) {

        switch_notifications = (SwitchCompat) view.findViewById(R.id.switch_notifications);

        RelativeLayout rel_allow_notifications = (RelativeLayout) view.findViewById(R.id.rel_allow_notifications);
        RelativeLayout rel_send_invite = (RelativeLayout) view.findViewById(R.id.rel_send_invite);

        TextView txt_allow_notification_label = (TextView) view.findViewById(R.id.txt_allow_notification_label);
        TextView txt_invite_label = (TextView) view.findViewById(R.id.txt_invite_label);

        rel_allow_notifications.setOnClickListener(onClickListener);
        rel_send_invite.setOnClickListener(onClickListener);

        txt_allow_notification_label.setOnClickListener(onClickListener);
        txt_invite_label.setOnClickListener(onClickListener);

        switch_notifications.setOnCheckedChangeListener(this);

    }


    private void onInviteClicked() {
        Intent intent = new AppInviteInvitation.IntentBuilder("Enjoyable Moments!")
                .setMessage("Hi Hackers")
                .setDeepLink(Uri.parse("https://play.google.com/store/apps/details?id=com.tm.teameverest"))
                // .setCustomImage(Uri.parse(getString(R.string.invitation_custom_image)))
                .setCallToActionText("Awesowe app!")
                .build();
        startActivityForResult(intent, REQUEST_INVITE);
    }

    //------------------------------------Onclick Listener--------------------------//

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.rel_allow_notifications:
                case R.id.txt_allow_notification_label:
                    if (!switch_notifications.isChecked()) {
                        AppPreference.getInstance(getActivity()).putBoolean(AppPreference.BooleanKeys.ALLOW_NOTIFICATIONS, true);
                        switch_notifications.setChecked(true);
                    } else {
                        AppPreference.getInstance(getActivity()).putBoolean(AppPreference.BooleanKeys.ALLOW_NOTIFICATIONS, false);
                        switch_notifications.setChecked(false);
                    }
                    break;
                case R.id.rel_send_invite:
                case R.id.txt_invite_label:
                    onInviteClicked();
                    break;
            }
        }
    };

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b) {
            AppPreference.getInstance(getActivity()).putBoolean(AppPreference.BooleanKeys.ALLOW_NOTIFICATIONS, true);
        } else {
            AppPreference.getInstance(getActivity()).putBoolean(AppPreference.BooleanKeys.ALLOW_NOTIFICATIONS, false);
        }
    }

    //--------------------------------Onchecked Change Listener-----------------------//
}
