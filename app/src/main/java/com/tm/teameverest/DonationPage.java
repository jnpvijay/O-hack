package com.tm.teameverest;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.tm.teameverest.utils.NetworkUtils;

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class DonationPage extends AppCompatActivity {

    private CoordinatorLayout coordinatorLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_page);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);

        Button button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(onClickListener);

    }

    public void onDonate() {

        EditText nameEdit = (EditText) findViewById(R.id.editText);
        String name = nameEdit.getText().toString();
        EditText ageEdit = (EditText) findViewById(R.id.editText2);
        String age = ageEdit.getText().toString();
        EditText emailEdit = (EditText) findViewById(R.id.editText3);
        String email = emailEdit.getText().toString();
        EditText phoneEdit = (EditText) findViewById(R.id.editText4);
        String phone = phoneEdit.getText().toString();
        EditText areaEdit = (EditText) findViewById(R.id.editText5);
        String area = areaEdit.getText().toString();
        EditText cityEdit = (EditText) findViewById(R.id.editText6);
        String city = cityEdit.getText().toString();
        Spinner genderEdit = (Spinner) findViewById(R.id.spinner);
        String gender = genderEdit.getSelectedItem().toString();
        Spinner contributionEdit = (Spinner) findViewById(R.id.spinner2);
        String contribution = contributionEdit.getSelectedItem().toString();

        Log.v("shashi", name + "|" + age + "|" + email + "|" + phone + "|" + area + "|" + city + "|" + gender + "|" + contribution);

        final JSONObject obj = new JSONObject();
        try {
            obj.put("name", name);
            obj.put("gender", gender);
            obj.put("age", age);
            obj.put("email", email);
            obj.put("phone_number", phone);
            obj.put("area", area);
            obj.put("town", city);
            obj.put("contribute_for", contribution);

            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        URL url = new URL("http://192.168.126.198:8080/v1/donation");
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setReadTimeout(10000);
                        conn.setConnectTimeout(15000);
                        conn.setRequestMethod("POST");
                        conn.setRequestProperty("Content-Type", "application/json");
                        conn.setDoInput(true);
                        conn.setDoOutput(true);
                        OutputStream os = conn.getOutputStream();
                        BufferedWriter writer = new BufferedWriter(
                                new OutputStreamWriter(os, "UTF-8"));
                        writer.write(obj.toString());
                        writer.flush();
                        writer.close();
                        os.close();
                        conn.connect();

                        AlertDialog.Builder builder = new AlertDialog.Builder(DonationPage.this);
                        builder.setTitle("Team Everest");
                        builder.setMessage("Message Posted Successfully");
                        builder.setPositiveButton("Ok", null);

                        final AlertDialog dialog = builder.create();


                        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                            @Override
                            public void onShow(DialogInterface dialogInterface) {
                                final Button btnOk = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                                btnOk.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();
                                        finish();
                                    }
                                });
                            }
                        });

                        dialog.setCancelable(false);
                        dialog.show();

                    } catch (Exception e) {
                    }
                }
            });
            t.start();
        } catch (Exception e) {
            e.printStackTrace();

            snackbarAlertMsg("Server error found.");
        }


    }

    /**
     * This method checks for internet connection and returns if true else false
     * and toast message for user
     *
     * @return
     */

    public boolean networkCheck() {
        if (!NetworkUtils.isConnected(this)) {
            // NetworkUtils.alertMsg(getActivity());
            return false;
        }
        return true;
    }

    /**
     * snack bar alert msg
     */
    private void snackbarAlertMsg(String msg) {
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, "" + msg, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.button3:
                    if (networkCheck()) {
                        onDonate();
                    } else {
                        snackbarAlertMsg("No Network.");
                    }
            }
        }
    };
}
