package com.tm.teameverest.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;

import java.lang.reflect.Field;

/**
 * Created by user on 13/11/16.
 */
public class FontsOverride {

    public static void setDefaultFont(Context context,
                                      String staticTypefaceFieldName, String fontAssetName) {
        final Typeface regular = Typeface.createFromAsset(context.getAssets(),
                fontAssetName);
        replaceFont(staticTypefaceFieldName, regular);
    }

    protected static void replaceFont(String staticTypefaceFieldName,
                                      final Typeface newTypeface) {
        try {
            final Field staticField = Typeface.class
                    .getDeclaredField(staticTypefaceFieldName);
            staticField.setAccessible(true);
            staticField.set(null, newTypeface);
            Log.d("TypeFace", "replace font success:" + staticTypefaceFieldName);
        } catch (NoSuchFieldException e) {
            Log.e("TypeFace", "replace font error:" + staticTypefaceFieldName);
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            Log.e("TypeFace", "replace font error:" + staticTypefaceFieldName);
            e.printStackTrace();
        }
    }

}
