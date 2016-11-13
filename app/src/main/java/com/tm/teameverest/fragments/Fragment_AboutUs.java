package com.tm.teameverest.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tm.teameverest.R;

/**
 * Created by user on 12/11/16.
 */
public class Fragment_AboutUs extends Fragment {

    private ImageView img_logo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about_us, null);

        initializeUI(view);

        return view;
    }

    /**
     * current view
     */
    private void initializeUI(View view) {

        img_logo = (ImageView) view.findViewById(R.id.img_logo);

//        Glide.with(getActivity())
//                .load(R.mipmap.ic_launcher)
//                .centerCrop()
//                .placeholder(R.mipmap.ic_launcher)
//                .crossFade()
//                .into(img_logo);
    }
}
