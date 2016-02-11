package com.codesyaoriol.pampletapp.Singleton;

/**
 * Created by User on 2/10/2016.
 */
public class Singleton {

    public static String mOpenFile;

    public static void setContact(String mOpenFile){
        Singleton.mOpenFile = mOpenFile;
    }

    public static String getSelectedEvent() {return mOpenFile;}
}
