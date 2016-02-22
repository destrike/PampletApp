package com.codesyaoriol.pampletapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ImageView;


import com.codesyaoriol.pampletapp.R;
import com.codesyaoriol.pampletapp.Singleton.Singleton;
import com.codesyaoriol.pampletapp.fragment.PampletListFragment;

import net.sf.andpdf.pdfviewer.PdfViewerActivity;

import java.io.File;
import org.apache.commons.io.FilenameUtils;
import java.util.ArrayList;

public class Pdftorun extends Activity {

    private String mOpenFile;
    private ImageView mViewImage;
    Bitmap bitmap = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdftorun);



        File sdCardRoot = Environment.getExternalStorageDirectory();
        File yourDir = new File(sdCardRoot, "IFIN-PDF");

        String path = yourDir.getPath()+"/"+Singleton.getSelectedEvent();
//        String paths = yourDir.getPath()+"/"+Singleton.getSelectedEvent();
        Intent intent = new Intent(this, Second.class);
        intent.putExtra(PdfViewerActivity.EXTRA_PDFFILENAME, path);

        mViewImage = (ImageView)findViewById(R.id.imageView);



        Log.i("path2", String.valueOf(path));
        try
        {
            if(path.contains(".pdf")){
                startActivity(intent);
            finish();
            }
            else {

                File pathLink = new File(yourDir, Singleton.getSelectedEvent());
                if (pathLink.isFile()) {
                    MediaScannerConnection.scanFile(this, new String[]{
                            pathLink.toString()}, null, new MediaScannerConnection.OnScanCompletedListener() {
                        @Override
                        public void onScanCompleted(String path, Uri uri) {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setDataAndType(uri, "image/*");
                            startActivityForResult(intent, this.hashCode());
                            finish();
                        }
                    });





            }
        }}

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
            finish();
            Intent gohome = new Intent (this, MainActivity.class);
            startActivity(gohome);

        }
        return super.onKeyDown(keyCode, event);
    }

}
