package com.openclassrooms.realestatemanager.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

import com.openclassrooms.realestatemanager.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import es.dmoral.toasty.Toasty;

/**
 * Created by Philippe on 21/02/2018.
 */

public class Utils {

    /**
     * Conversion d'un prix d'un bien immobilier (Dollars vers Euros)
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @param dollars
     * @return
     */
    public static int convertDollarToEuro(int dollars){
        return (int) Math.round(dollars * 0.812);
    }

    public static double convertDollarToEuroDouble(double dollars){ return (double) Math.round(dollars * 0.812); }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Convert € to $ ----------------------------------------------
    //----------------------------------------------------------------------------------------------

    public static int convertEuroToDollar(int euros){ return (int) Math.round(euros / 0.812); }

    public static double convertEuroToDollarDouble(double euros){ return (double) Math.round(euros / 0.812); }

    /**
     * Conversion de la date d'aujourd'hui en un format plus approprié
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @return
     */
    public static String getTodayDate(){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(new Date());
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Convert date YYYY/mm/dd to dd/mm/YYYY -----------------------
    //----------------------------------------------------------------------------------------------

    public static String convertUsDateToFrenchDate(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(date);
    }

    /**
     * Vérification de la connexion réseau
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @param context
     * @return
     */
    public static Boolean isInternetAvailable(Context context){
        WifiManager wifi = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        return wifi.isWifiEnabled();
    }

    public static Boolean isInternetAvailableNew(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnectedOrConnecting()){
            Toasty.success(context, R.string.internet_available, Toasty.LENGTH_SHORT).show(); //internet available
            return true;
        }
        else{
            Toasty.error(context, R.string.internet_unavailable, Toasty.LENGTH_SHORT).show();
            return false;
        }
    }
}
