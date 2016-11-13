package com.tm.teameverest.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.tm.teameverest.R;

/**
 * Created by user on 12/11/16.
 */
public class Fragment_ContactUs extends Fragment {

    private ImageView img_btn_whatsapp;
    private ImageView img_btn_facebook;
    private ImageView img_btn_twitter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_us, null);

        initializeUI(view);

        return view;
    }

    /**
     * current view
     */
    private void initializeUI(View view) {

        img_btn_whatsapp = (ImageView) view.findViewById(R.id.img_btn_whatsapp);
        img_btn_facebook = (ImageView) view.findViewById(R.id.img_btn_facebook);
        img_btn_twitter = (ImageView) view.findViewById(R.id.img_btn_twitter);

        img_btn_twitter.setOnClickListener(onClickListener);
        img_btn_facebook.setOnClickListener(onClickListener);
        img_btn_whatsapp.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.img_btn_facebook:
                    String packageNameFacebook = "com.facebook.katana";
                    boolean isFacebookInstalled = isAppInstalled(packageNameFacebook);

                    if (isFacebookInstalled) {
                        Intent intent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse("fb://page/146263172091383"));
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse("https://www.facebook.com/TeamEverestIndia"));
                        startActivity(intent);
                    }
                    break;
                case R.id.img_btn_twitter:
                    String packageNameTwitter = "com.twitter.android";
                    boolean isTwitterInstalled = isAppInstalled(packageNameTwitter);

                    if (isTwitterInstalled) {
                        Intent intent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse("twitter://user?user_id=39025954"));
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse("https://twitter.com/teameverest/"));
                        startActivity(intent);
                    }
                    break;
                case R.id.img_btn_whatsapp:
                    String packageNameWhatsapp = "com.whatsapp";
                    boolean isWhatsappInstalled = isAppInstalled(packageNameWhatsapp);
                    if (isWhatsappInstalled) {

                        Uri uri = Uri.parse("smsto:" + "8939912365");
                        Intent i = new Intent(Intent.ACTION_SENDTO, uri);
                        i.setPackage("com.whatsapp");
                        startActivity(Intent.createChooser(i, ""));
                    } else {
                        Toast.makeText(getActivity(), "WhatsApp not Installed", Toast.LENGTH_SHORT).show();

                        AlertDialog.Builder alert_builder = new AlertDialog.Builder(
                                getActivity());
                        alert_builder.setTitle("Colour Touch");
                        alert_builder.setMessage("Do you want download from playstore.");
                        alert_builder.setCancelable(true);
                        alert_builder.setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Uri uri = Uri
                                                .parse("market://details?id=com.whatsapp");
                                        Intent goToMarket = new Intent(
                                                Intent.ACTION_VIEW, uri);
                                        startActivity(goToMarket);
                                    }
                                });
                        alert_builder.setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                        AlertDialog alertDialog = alert_builder.create();
                        alertDialog.show();
                    }

                    break;
            }

        }
    };

    /**
     * check installed or not
     *
     * @param packageName
     * @return
     */
    protected boolean isAppInstalled(String packageName) {
        Intent mIntent = getActivity().getPackageManager()
                .getLaunchIntentForPackage(packageName);
        if (mIntent != null) {
            return true;
        } else {
            return false;
        }
    }
}
