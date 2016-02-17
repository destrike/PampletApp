package com.codesyaoriol.pampletapp.fragment;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.codesyaoriol.pampletapp.R;
import com.codesyaoriol.pampletapp.activity.MainActivity;
import com.codesyaoriol.pampletapp.core.GRequest;
import com.codesyaoriol.pampletapp.core.GResponseErrorListener;
import com.codesyaoriol.pampletapp.core.GResponseListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class InterstitialsFragment extends Fragment {

    private ImageView mImageView;
    private String mImageUrl;
    private ProgressBar spinner;




    public InterstitialsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        new requestDownloadFile().execute("");

        requestApiGetUserInfo();

        // Find the progress bar





    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_interstitials, container, false);




        ImageView imgClose = (ImageView) view.findViewById(R.id.close);
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getActivity().finish();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);

            }
        });

        mImageView = (ImageView) view.findViewById(R.id.ad);

        spinner = (ProgressBar) getActivity().findViewById(R.id.progressBar1);
//        spinner.setVisibility(view.GONE);



        return view;
    }

    /* load image for interstitial */
    public void loadImage() {
        Picasso.with(getActivity())
                .load(mImageUrl)
                .fit()
                .into(mImageView, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                    }
                });

    }

    /* api request for image */
    public void requestApiGetUserInfo() {

        HashMap<String, String> params = new HashMap<>();
//        params.put("id", "15");

        GRequest request = new GRequest(GRequest.kApiMethodGetUserInfo, params,
                new GResponseListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        super.onResponse(jsonObject);

                        try {
                            if (jsonObject.getInt("Status") == 200) {

                                mImageUrl = jsonObject.getJSONObject("Data").getJSONObject("pdf").getString("path");
//                                String mPdfUrl = mImageUrl.

                                Log.i("imageurl", mImageUrl);

                                new requestDownloadFile().execute("");
//                                Log.i("profile", jsonObject.getJSONObject("Data").getString("first_name") + " " + jsonObject.getJSONObject("Data").getString("last_name"));

//                                loadImage();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new GResponseErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                super.onErrorResponse(volleyError);
            }
        });

        request.execute();
    }

    public static class Downloader {

        public static void DownloadFile(String fileURL, File directory) {
            try {
                FileOutputStream file = new FileOutputStream(directory);
                URL url = new URL(fileURL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                connection .setRequestMethod("GET");
                connection.setDoOutput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = input.read(buffer)) > 0) {
                    file.write(buffer, 0, len);
                }
                file.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    /* download from url */
    private class requestDownloadFile extends AsyncTask<String, Void, String> {

        String Filepath;
        String PDFFile;



        @Override
        protected String doInBackground(String... params) {
            File sdCardRoot = Environment.getExternalStorageDirectory();
            File yourDir = new File(sdCardRoot, "IFIN-PDF");
//            PDFFile = "ISFL-" + Date() + ".pdf";
            File x = new File(yourDir, "ISFL-" + new Date().getDate());


            try {

                if(x.exists()) {
                    Toast.makeText(getActivity(), "No New Download", Toast.LENGTH_SHORT).show();
                 }
                else {
                    String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
                    File folder = new File(extStorageDirectory, "IFIN-PDF");
                    folder.mkdir();
                    Filepath = "ISFL-" + new Date().getDate();
                    File file = new File(folder, Filepath);

                    try {
                        file.createNewFile();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }

                    Downloader.DownloadFile
                            ("http://" + mImageUrl, file);//Paste your url here
                }

            } catch (Exception e) {
            }

            return Filepath;
        }
//
//        private boolean resourcesDontAlreadyExist() {
//            // Here you would query your app's internal state to see if this download had been performed before
//            // Perhaps once checked save this in a shared preference for speed of access next time
//            File sdCardRoot = Environment.getExternalStorageDirectory();
//            File yourDir = new File(sdCardRoot, "IFIN-PDF");
//
//            return true; // returning true so we show the splash every time
//        }

        @Override
        protected void onPostExecute(String result) {

            File sdCardRoot = Environment.getExternalStorageDirectory();
            File yourDir = new File(sdCardRoot, "IFIN-PDF");
            File x = new File(yourDir, "ISFL-" + new Date().getDate());

            if(x.exists()) {
                spinner.setVisibility(View.GONE);

                Toast.makeText(getActivity(), "No New Download", Toast.LENGTH_SHORT).show();

            }else {
                spinner.setVisibility(View.GONE);

                Toast.makeText(getActivity(), "Download Complete", Toast.LENGTH_SHORT).show();

            }
            getActivity().finish();
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        }

        @Override
        protected void onPreExecute() {

            File sdCardRoot = Environment.getExternalStorageDirectory();
            File yourDir = new File(sdCardRoot, "IFIN-PDF");
            File x = new File(yourDir, "ISFL-" + new Date().getDate());


            if(x.exists()) {
                spinner.setVisibility(View.GONE);

                Toast.makeText(getActivity(), "No New Download", Toast.LENGTH_SHORT).show();

            }else {
                spinner.setVisibility(View.VISIBLE);

                Toast.makeText(getActivity(), "Download in progress", Toast.LENGTH_LONG).show();

            }





        }

    }




}
