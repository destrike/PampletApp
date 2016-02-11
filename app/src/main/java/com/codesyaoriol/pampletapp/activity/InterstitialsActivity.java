package com.codesyaoriol.pampletapp.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.codesyaoriol.pampletapp.core.BaseActivity;
import com.codesyaoriol.pampletapp.core.GEngine;
import com.codesyaoriol.pampletapp.fragment.InterstitialsFragment;
import com.codesyaoriol.pampletapp.R;


public class InterstitialsActivity extends BaseActivity {

    public static InterstitialsActivity INSTANCE = null;
    public static int mFrame = R.id.frame_layout_interstitials;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interstitials);

        INSTANCE = this;
        setFrameLayout(mFrame);


        GEngine.switchFragment(INSTANCE, new InterstitialsFragment(), getFrameLayout());


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_interstitials, menu);
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
