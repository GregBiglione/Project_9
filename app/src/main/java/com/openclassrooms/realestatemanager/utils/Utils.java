package com.openclassrooms.realestatemanager.utils;

import android.content.Context;
import android.net.wifi.WifiManager;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Convert € to $ ----------------------------------------------
    //----------------------------------------------------------------------------------------------

    public static int convertEuroToDollar(int euros){ return (int) Math.round(euros * 1.186); }

    /**
     * Conversion de la date d'aujourd'hui en un format plus approprié
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @return
     */
    public static String getTodayDate(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        return dateFormat.format(new Date());
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------- Convert date YYYY/mm/dd to dd/mm/YYYY -----------------------
    //----------------------------------------------------------------------------------------------

    public static String convertUsDateToFrenchDate(){
        String todayDate = getTodayDate();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY/mm/dd");
        Date testDate = null;
        try {
            testDate = simpleDateFormat.parse(todayDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat converter = new SimpleDateFormat("dd/mm/YYYY");
        String frenchDate = converter.format(testDate);
        return frenchDate;
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
}
