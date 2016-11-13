package com.tm.teameverest;

import android.app.Application;
import android.content.Context;

import com.tm.teameverest.utils.FontsOverride;

/**
 * Created by user on 13/11/16.
 */
public class TeamEverestApplication extends Application {

    private static final String TAG = "TeamEverestApplication";

    private static TeamEverestApplication instance;

    private Context context;


    @Override
    public void onCreate() {
        super.onCreate();

        instance = new TeamEverestApplication();

        instance.context = this.getApplicationContext();

        // to change the font for whole application
        FontsOverride.setDefaultFont(getApplicationContext(), "SERIF",
                "Montserrat-Regular.ttf");
    }

}
