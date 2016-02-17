package com.codesyaoriol.pampletapp.Singleton;

/**
 * Created by User on 2/17/2016.
 */
public class DelSingleton {

    public static int del;

    public static void setValue(int del){
        DelSingleton.del = del;
    }

    public static int getSelectedEvent() {return del;}

}
