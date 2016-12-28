package com.profuturo.android.capturaimagenes.camscanner;

import android.content.Context;
import android.content.pm.PackageManager;

public class Validacion {

    public static boolean motorImagesIsInstalled(Context c){
        PackageManager pm = c.getPackageManager();
        boolean app_installed;
        try {
            pm.getPackageInfo("mx.com.profuturo.motor", PackageManager.GET_ACTIVITIES);
            app_installed = true;
        }
        catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

}