package com.codesyaoriol.pampletapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;


import com.codesyaoriol.pampletapp.R;
import com.codesyaoriol.pampletapp.Singleton.Singleton;
import com.codesyaoriol.pampletapp.fragment.PampletListFragment;

import net.sf.andpdf.pdfviewer.PdfViewerActivity;

import java.io.File;
import java.util.ArrayList;

public class Pdftorun extends Activity {

    private String mOpenFile;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdftorun);



        File sdCardRoot = Environment.getExternalStorageDirectory();
        File yourDir = new File(sdCardRoot, "IFIN-PDF");
        String path = yourDir.getPath()+"/"+Singleton.getSelectedEvent();
        Intent intent = new Intent(this, Second.class);
        intent.putExtra(PdfViewerActivity.EXTRA_PDFFILENAME, path);



        Log.i("path2", String.valueOf(path));
        try
        {
            startActivity(intent);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }



    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            Intent gohome = new Intent (this, MainActivity.class);
            startActivity(gohome);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

}