package com.tm.teameverest;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.firebase.auth.FirebaseAuth;
import com.tm.teameverest.fragments.Fragment_AboutUs;
import com.tm.teameverest.fragments.Fragment_Blogs;
import com.tm.teameverest.fragments.Fragment_ContactUs;
import com.tm.teameverest.fragments.Fragment_Home;
import com.tm.teameverest.fragments.Fragment_Settings;
import com.tm.teameverest.utils.AppPreference;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        // Handle the home action
        Fragment_Home fragment_home = new Fragment_Home();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, fragment_home).commit();

        MenuItem menuItem = navigationView.getMenu().getItem(0);
        menuItem.setChecked(true);

        getSupportActionBar().setTitle("Home");

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the home action
            Fragment_Home fragment_home = new Fragment_Home();
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, fragment_home).commit();

            callMenuHighlight(item);

            getSupportActionBar().setTitle("Home");
        } else if (id == R.id.nav_aboutus) {
            Fragment_AboutUs fragment_aboutUs = new Fragment_AboutUs();
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, fragment_aboutUs).commit();

            getSupportActionBar().setTitle("About Us");
        } else if (id == R.id.nav_blog) {
            Fragment_Blogs fragment_blogs = new Fragment_Blogs();
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, fragment_blogs).commit();

            getSupportActionBar().setTitle("Blogs");

        } else if (id == R.id.nav_contactus) {

            Fragment_ContactUs fragment_contactUs = new Fragment_ContactUs();
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, fragment_contactUs).commit();

            getSupportActionBar().setTitle("Contact Us");
        } else if (id == R.id.nav_settings) {
            Fragment_Settings fragment_settings = new Fragment_Settings();
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, fragment_settings).commit();

            getSupportActionBar().setTitle("Settings");
        } else if (id == R.id.nav_signout) {
            AppPreference.getInstance(getApplicationContext()).putBoolean(
                    AppPreference.BooleanKeys.LOGGED_IN, false);
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            //TODO update shared preference
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * @param menuItem Highlight the selected item has been done by NavigationView
     */
    private void callMenuHighlight(MenuItem menuItem) {
        menuItem.setChecked(true);
    }


}
