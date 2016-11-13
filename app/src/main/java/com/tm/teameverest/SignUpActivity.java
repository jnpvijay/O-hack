package com.tm.teameverest;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.tm.teameverest.utils.AppPreference;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "SignUpActivity";
    private EditText edt_name;
    private EditText edt_email_address;
    private EditText edt_mobile_num;
    private EditText edt_dob;
    private EditText edt_city;
    private EditText edt_add_bio;
    private EditText edt_any_comments;
    private EditText edt_password;

    private RadioGroup radio_group_sex;

    private RadioButton radio_btn_male;
    private RadioButton radio_btn_female;

    private Button btn_submit;

    private Calendar myCalendar;

    private CoordinatorLayout coordinatorLayout;

    // Firebase instance variables
    private DatabaseReference mFirebaseDatabaseReference;

    private FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_up);


        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        getSupportActionBar().setTitle("Sign Up");

        initializeUI();


    }

    /**
     * current UI
     */
    private void initializeUI() {
        edt_name = (EditText) findViewById(R.id.edt_name);
        edt_email_address = (EditText) findViewById(R.id.edt_email_address);
        edt_mobile_num = (EditText) findViewById(R.id.edt_mobile_num);
        edt_dob = (EditText) findViewById(R.id.edt_dob);
        edt_city = (EditText) findViewById(R.id.edt_city);
        edt_add_bio = (EditText) findViewById(R.id.edt_add_bio);
        edt_any_comments = (EditText) findViewById(R.id.edt_any_comments);
        edt_password = (EditText) findViewById(R.id.edt_password);

        radio_btn_male = (RadioButton) findViewById(R.id.radio_btn_male);
        radio_btn_female = (RadioButton) findViewById(R.id.radio_btn_female);

        radio_btn_male.setChecked(true);

        radio_group_sex = (RadioGroup) findViewById(R.id.radio_group_sex);
        radio_group_sex.setOnCheckedChangeListener(onCheckedChangeListener);

        edt_dob.setOnClickListener(onClickListener);

        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(onClickListener);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            if (i == R.id.radio_btn_male) {
                radio_btn_male.setChecked(true);
            } else {
                radio_btn_female.setChecked(true);
            }
        }
    };


    //--------------------------Onclick Listener-------------------------------------------//
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_submit:
                    navigatingNextView();
                    break;
                case R.id.edt_dob:
                    showDatePickerDialog();
                    break;
            }
        }
    };

    /**
     * Next Validation going here
     */
    private void navigatingNextView() {
        if (edt_name.getText().toString().trim().length() == 0
                || edt_email_address.getText().toString().trim().length() == 0
                || edt_mobile_num.getText().toString().trim().length() == 0
                || edt_city.getText().toString().trim().length() == 0
                || edt_dob.getText().toString().trim().length() == 0) {
            snackBarMsg("Please fill all mandatory fields.");
        } else if (isValidEmailAddress(edt_email_address.getText().toString())) {
            snackBarMsg("Please enter valid email address.");
        } else if (isValidMobileNum(edt_mobile_num.getText().toString())) {
            snackBarMsg("Please enter valid mobile number.");
        } else {
            // TODO post to server
            callingUpdateService();

            AppPreference.getInstance(getApplicationContext()).putBoolean(
                    AppPreference.BooleanKeys.LOGGED_IN, true);

            Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
            startActivity(intent);

        }
    }

    private void callingUpdateService() {

        mAuth.createUserWithEmailAndPassword(edt_email_address.getText().toString(), edt_password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this, "Error in authentication",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // TODO dismiss progress
                    }
                });
        // [END create_user_with_email]
    }


    /**
     * validation for mobile number
     *
     * @param mobile
     * @return valid true
     */
    private boolean isValidMobileNum(String mobile) {
        if (mobile.trim().length() == 10) {
            return true;
        }
        return false;
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
     * creating date picker logic.
     */
    private void showDatePickerDialog() {

        myCalendar = Calendar.getInstance();
        int pYear = myCalendar.get(Calendar.YEAR);
        int pMonth = myCalendar.get(Calendar.MONTH);
        int pDay = myCalendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(this, pDateSetListener, pYear, pMonth, pDay);

        Calendar minCalendar = Calendar.getInstance();
        minCalendar.add(Calendar.YEAR, -100); //etc

        Calendar maxCaledar = Calendar.getInstance();
        maxCaledar.add(Calendar.YEAR, -10); //etc

        // mindate
        dialog.getDatePicker().setMinDate(minCalendar.getTimeInMillis());

        // maxdate
        dialog.getDatePicker().setMaxDate(maxCaledar.getTimeInMillis());

        dialog.show();
    }

    //------------------------Date Picker Listener-------------------------------//
    DatePickerDialog.OnDateSetListener pDateSetListener = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year,
                              int monthOfYear, int dayOfMonth) {

            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            String myFormat = "dd/MM/yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

            edt_dob.setText(sdf.format(myCalendar.getTime()));
        }
    };


    /**
     * Error Alert Snackbar view
     */
    public void snackBarMsg(String msg) {
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, msg, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    public void updateDataDB() {
        // [START auth_state_listener]
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());

                    convertingJsonToUpdate(user.getUid().toString());

                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };
        // [END auth_state_listener]
    }

    /**
     * @param uid
     */
    private void convertingJsonToUpdate(String uid) {

    }
}
