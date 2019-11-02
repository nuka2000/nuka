package com.prodevsblog.phplogin;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SplashScreenActivity extends AppCompatActivity {

    private static final int SPLASH_TIME = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        setContentView(R.layout.activity_splash_screen_new);
        CekJSON();

    }

    public void CekJSON() {
        class GetDatJSON extends AsyncTask<Void,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                showDataDetails(s);

                finish();
            }
            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.CheckConnection(URLS.CEK_DB);
                try {
                    Thread.sleep(SPLASH_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return s;
            }
        }
        GetDatJSON ge = new GetDatJSON();
        ge.execute();
    }

    public void showDataDetails(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(URLS.KONEKSI);
            JSONObject c = result.getJSONObject(0);
            String access = c.getString("access");

            if (access.equals("0")){
                Toast.makeText(getApplicationContext(),"Cant Connect to Database", Toast.LENGTH_LONG).show();
                finish();
            }else {
                Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"No Internet Connection", Toast.LENGTH_LONG).show();
            ExitActivity.exitApplication(SplashScreenActivity.this);
            
        }
    }

}