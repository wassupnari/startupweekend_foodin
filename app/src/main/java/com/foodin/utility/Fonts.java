package com.foodin.utility;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by wassupnari on 10/18/14.
 */
public class Fonts {

    public static Typeface typefaceOpensansItalic = null;
    public static Typeface typefaceOpensansRegular = null;

    public static final String opensansItalic = "fonts/OpenSans-Italic.ttf";
    public static final String opensansRegular = "fonts/OpenSans-Regular.ttf";

    public static Typeface getOpenSansItalic(Context context) {
        try {
            if (typefaceOpensansItalic == null)
                typefaceOpensansItalic = Typeface.createFromAsset(context.getAssets(), opensansItalic);
        } catch (Exception ex) {
            typefaceOpensansItalic = Typeface.DEFAULT;
        }

        return typefaceOpensansItalic;
    }

    public static Typeface getOpenSansRegular(Context context) {
        try {
            if (typefaceOpensansRegular == null)
                typefaceOpensansRegular = Typeface.createFromAsset(context.getAssets(), opensansRegular);
        } catch (Exception ex) {
            typefaceOpensansRegular = Typeface.DEFAULT;
        }

        return typefaceOpensansRegular;
    }

}