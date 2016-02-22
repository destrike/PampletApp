package com.codesyaoriol.pampletapp.activity;

import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;

import com.codesyaoriol.pampletapp.core.BaseActivity;
import com.codesyaoriol.pampletapp.core.GEngine;
import com.codesyaoriol.pampletapp.R;
import com.codesyaoriol.pampletapp.fragment.PampletListFragment;

import java.io.File;
import java.io.IOException;
import java.util.Date;


public class MainActivity extends BaseActivity {

    public static MainActivity INSTANCE = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        File folders = new File(extStorageDirectory, "IFIN-PDF");
        File newt = new File(folders, "config.txt");
        if (!newt.exists()) {
        try {


            String Filepath = "config" + ".txt";
            File file = new File(folders, Filepath);

            try {
                file.createNewFile();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        } catch (Exception e) {
        }
    }


        INSTANCE = this;

         /* Initialize Frame Layout */
        setFrameLayout(R.id.framelayout_main);


        /* switch fragment */
        GEngine.switchFragment(INSTANCE, new PampletListFragment(), getFrameLayout());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
