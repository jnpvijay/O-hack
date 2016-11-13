package com.tm.teameverest.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.util.Log;

import java.util.Locale;

/**
 * Created by user on 12/11/16.
 */
public class AppPreference extends Preference {

    private static final String TAG = "AppPreference";
    private SharedPreferences sharedPreferences;

    /**
     * Singleton instance for AppPreference
     */
    public static AppPreference instance;

    public AppPreference(Context context) {
        super(context);
        sharedPreferences = context.getSharedPreferences("Preferences",
                Context.MODE_PRIVATE);
    }

    public static AppPreference getInstance(Context context) {
        if (instance == null)
            return new AppPreference(context);
        return instance;
    }

    /**
     * @param key   key constant from AppPreference
     * @param value String value to be stored associated with the key
     *              <p>
     *              Stores the String value in preference with the key
     *              </p>
     */
    public void putString(StringKeys key, String value) {
        sharedPreferences.edit().putString(key.toString(), value).commit();
        Log.i(TAG, "Put String - key:" + key + " value:" + value);
    }

    /**
     * @param key key constant from AppPreference
     * @return String value associated with key or null
     */
    public String getString(StringKeys key) {
        return sharedPreferences.getString(key.toString(), null);
    }

    /**
     * @param key   key constant from AppPreference
     * @param value boolean value to be stored associated with the key
     *              <p>
     *              Stores the boolean value in preference with the key
     *              </p>
     */
    public void putBoolean(BooleanKeys key, boolean value) {
        sharedPreferences.edit().putBoolean(key.toString(), value).commit();
        Log.i(TAG, "Put String - key:" + key + " value:" + value);
    }


    /**
     * @param key key constant from AppPreference
     * @return boolean value associated with key or false
     */
    public boolean getBoolean(BooleanKeys key) {
        return sharedPreferences.getBoolean(key.toString(), false);
    }

    /**
     * This enum values are used to manage Boolean values in preference
     */
    public static enum BooleanKeys {
        /**
         * registeration status for GCM
         */
        REGISTRATION_COMPLETE,
        /**
         * True for user already logged in
         */
        LOGGED_IN,
        /*
            True for user turn off notifications
        * */
        ALLOW_NOTIFICATIONS;

        public String toString() {
            return this.name().toLowerCase(Locale.ENGLISH);
        }

        ;
    }

    ;


    /**
     * This enum values are used to manage String values in preference
     */
    public static enum StringKeys {
        /**
         * app_reg_id_for playservice
         */
        APP_REG_ID;

        public String toString() {
            return this.name().toLowerCase(Locale.ENGLISH);
        }

        ;
    }

    ;
}
