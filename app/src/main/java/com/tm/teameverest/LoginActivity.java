package com.tm.teameverest;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.tm.teameverest.utils.AppPreference;
import com.tm.teameverest.utils.NetworkUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private EditText email_address;
    private EditText password;
    private LinearLayout lin_signup;
    private TextView txt_signup;
    private TextView txt_title;
    private ImageView img_logo;
    private TextView forgot_password;

    private Button btn_signin;

    private FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener mAuthListener;


    private CoordinatorLayout coordinatorLayout;

    protected void setStatusBarTranslucent(boolean makeTranslucent) {
        if (makeTranslucent) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            setStatusBarTranslucent(true);
        }

        if (AppPreference.getInstance(getApplicationContext()).getBoolean(
                AppPreference.BooleanKeys.LOGGED_IN)) {
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
        } else {

        }
        setContentView(R.layout.activity_login);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);

        initializeUI();

    }

    private void initializeUI() {

        email_address = (EditText) findViewById(R.id.edt_email_address);
        password = (EditText) findViewById(R.id.edt_password);

        lin_signup = (LinearLayout) findViewById(R.id.lin_signup);
        txt_signup = (TextView) findViewById(R.id.txt_signup);

        txt_title = (TextView) findViewById(R.id.txt_title);

        forgot_password = (TextView) findViewById(R.id.txt_forgotPassword);

        img_logo = (ImageView) findViewById(R.id.img_logo);

        btn_signin = (Button) findViewById(R.id.btn_signin);
        lin_signup.setOnClickListener(onClickListener);
        txt_signup.setOnClickListener(onClickListener);

        forgot_password.setOnClickListener(onClickListener);
        btn_signin.setOnClickListener(onClickListener);

        //mAuth = FirebaseAuth.getInstance();

    }


    /**
     * showing forgot dialog
     */
    private void showForgotDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("Forgot Password");
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_view_for_forgot_password, null, false);
        final EditText edt_forgot_password = (EditText) view.findViewById(R.id.edt_forgot_password);
        builder.setView(view);

        builder.setPositiveButton("Ok", null);
        builder.setNegativeButton("Cancel", null);

        final AlertDialog dialog = builder.create();


        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button btnOk = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (isValidEmailAddress(edt_forgot_password.getText().toString().trim())) {
                            if (networkCheck()) {

                                AppPreference.getInstance(getApplicationContext()).putBoolean(
                                        AppPreference.BooleanKeys.LOGGED_IN, true);

                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(intent);

                                // TODO service
                            } else {
                                snackbarAlertMsg("No Internet Connection.");
                            }
                            dialog.dismiss();
                        } else {
                            hideSoftKeyboard();
                            Toast.makeText(LoginActivity.this, "Please enter valid email address.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        dialog.setCancelable(false);
        dialog.show();
    }


    /**
     * validating email address
     *
     * @param email
     * @return valid true
     */
    private boolean isValidEmailAddress(String email) {
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher matcher = pattern
                .matcher(email);
        if (matcher.find()) {
            return true;
        }
        return false;
    }

    /**
     * Hides the soft keyboard
     */
    private void hideSoftKeyboard() {

        if (this.getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) this
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(this
                    .getCurrentFocus().getWindowToken(), 0);
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
                .make(coordinatorLayout, msg, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    //-----------------Onclick listener------------------------------------//
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.lin_signup:
                case R.id.txt_signup:
                    Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                    startActivity(intent);
                    break;
                case R.id.txt_forgotPassword:
                    showForgotDialog();
                    break;
                case R.id.btn_signin:

                    if (email_address.getText().toString().length() == 0 && password.getText().toString().length() == 0) {
                        snackbarAlertMsg("Please enter email address and password.");
                    } else if (email_address.getText().toString().length() == 0) {
                        snackbarAlertMsg("Please enter email address.");
                    } else if (password.getText().toString().length() == 0) {
                        snackbarAlertMsg("Please enter password.");
                    } else {

                        Intent intent_home = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent_home);

//                        mAuth.signInWithEmailAndPassword(email_address.getText().toString(), password.getText().toString())
//                                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<AuthResult> task) {
//                                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
//
//                                        if (!task.isSuccessful()) {
//                                            Log.w(TAG, "signInWithEmail:failed", task.getException());
//                                            snackbarAlertMsg("Authentication failed.");
//                                        } else {
//                                            Intent intent_home = new Intent(LoginActivity.this, HomeActivity.class);
//                                            startActivity(intent_home);
//                                        }
//                                    }
//                                });
                    }
                    break;
            }
        }
    };


//    @Override
//    public void onStart() {
//        super.onStart();
//        mAuth.addAuthStateListener(mAuthListener);
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        if (mAuthListener != null) {
//            mAuth.removeAuthStateListener(mAuthListener);
//        }
//    }

}
