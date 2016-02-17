package com.codesyaoriol.pampletapp.Singleton;

/**
 * Created by User on 2/17/2016.
 */
public class DLSingleton {

    public static int dl;

    public static void setValue(int dl){
        DLSingleton.dl = dl;
    }

    public static int getSelectedEvent() {return dl;}
}
