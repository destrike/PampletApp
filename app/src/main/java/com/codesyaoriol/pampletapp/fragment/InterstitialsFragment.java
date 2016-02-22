package com.codesyaoriol.pampletapp.fragment;


import android.content.Context;
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class InterstitialsFragment extends Fragment {

    private ImageView mImageView;
    private String mImageUrl;
    private ProgressBar spinner;
//    private static final String newt = "config.txt";
    private String mReadText;
    private String mIsLatest;
    private String mIsScheduled;
    private String mIsExtension;




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

                                Calendar c = Calendar.getInstance();


                                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                                String newDate = df.format(c.getTime());
                                // formattedDate have current date/time

                                mImageUrl = jsonObject.getJSONObject("Data").getJSONObject("pdf").getString("path");
                                mIsLatest = jsonObject.getJSONObject("Data").getJSONObject("pdf").getString("isLatest");
                                mIsScheduled = jsonObject.getJSONObject("Data").getJSONObject("pdf").getString("scheduleDate");

                                if (mImageUrl.contains(".pdf")){
                                    mIsExtension = ".pdf";
                                }else if(mImageUrl.contains(".jpg")) {
                                    mIsExtension = ".jpg";
                                }else {
                                    mIsExtension = ".png";
                                }

                                if (mIsLatest.equals("1")){

                                    if(mIsScheduled.equals(newDate)){


                                        new requestDownloadFile().execute("");


                                    }else {

                                        Toast.makeText(getActivity(), "No New Download", Toast.LENGTH_SHORT).show();

                                        getActivity().finish();
                                        Intent intent = new Intent(getActivity(), MainActivity.class);
                                        startActivity(intent);


                                    }

                                }else {

                                    Toast.makeText(getActivity(), "No New Download", Toast.LENGTH_SHORT).show();

                                    getActivity().finish();
                                    Intent intent = new Intent(getActivity(), MainActivity.class);
                                    startActivity(intent);


                                }

                                Log.i("imageurl", mImageUrl);
                                Log.i("islatested", mIsLatest);
                                Log.i("isschedules", mIsScheduled);
                                Log.i("iscurrent", newDate);



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
        int set = 0;



        @Override
        protected String doInBackground(String... params) {
//            File sdCardRoot = Environment.getExternalStorageDirectory();
//            File yourDir = new File(sdCardRoot, "IFIN-PDF");
////            PDFFile = "ISFL-" + Date() + ".pdf";
//            File x = new File(yourDir, "ISFL-" + new Date().getDate());


            try {

//                if(yourDir.exists()) {
//                    if(yourDir.delete()){
//                        getActivity().finish();
//                        Intent intent = new Intent(getActivity(), MainActivity.class);
//                        startActivity(intent);
//                    }
//
//                 }
//                else {


                    String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
                    File folder = new File(extStorageDirectory, "IFIN-PDF");
                    folder.mkdir();
                    Filepath = "ISFL-" + new Date().getDate()+mIsExtension;
                    File file = new File(folder, Filepath);

                    try {
                        file.createNewFile();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }

                    Downloader.DownloadFile
                            ("http://" + mImageUrl, file);//Paste your url here


//                }

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

                if (set==1) {

                    spinner.setVisibility(View.GONE);

                    Toast.makeText(getActivity(), "Download Complete", Toast.LENGTH_SHORT).show();

                    getActivity().finish();
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }else {

                    getActivity().finish();
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);


                }





        }

        @Override
        protected void onPreExecute() {

            Filepath = "ISFL-" + new Date().getDate();

            PDFFile = Filepath;

            Log.i("path2", String.valueOf(PDFFile));

            String textFromFileString =  readFromFile();



            if(mReadText!=null) {



                if (mReadText.contains(PDFFile)) {
                    spinner.setVisibility(View.GONE);


                    Toast.makeText(getActivity(), "No New Download", Toast.LENGTH_SHORT).show();

                } else {
                    spinner.setVisibility(View.VISIBLE);

                    set = 1;


                    writeToFile(PDFFile);
                    Log.i("mreadtext", mReadText);

                    Toast.makeText(getActivity(), "Download in progress", Toast.LENGTH_LONG).show();

                }

            } else {

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


            }




        }

    }
//Write List
    private void writeToFile(String data) {


        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        File folder = new File(extStorageDirectory, "IFIN-PDF");
        File file = new File(folder, "config.txt");



        String newLine = "\r\n";
        try {
            FileOutputStream fos = new FileOutputStream(file);

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fos);
            outputStreamWriter.append(data + newLine);
            outputStreamWriter.write(data);
            outputStreamWriter.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i("logit", String.valueOf(file));


        Log.i("writedata", data);

    }

    // Read List

    private String readFromFile() {
        String extStorageDirectory = Environment.getExternalStorageDirectory().getAbsolutePath().toString();
        File folder = new File(extStorageDirectory, "IFIN-PDF");
        File file = new File(folder, "config.txt");

        Log.i("filepath", file.toString());


        String ret = "";

        try {
            FileInputStream inputStream = new FileInputStream(file);
//            InputStream inputStream = getActivity().openFileInput(file.toString());

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString).append("\n");
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }

            Log.i("readFiles", ret);
            mReadText = ret;
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }




}
