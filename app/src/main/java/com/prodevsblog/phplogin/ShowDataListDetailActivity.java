package com.prodevsblog.phplogin;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

public class ShowDataListDetailActivity extends AppCompatActivity {

    public String query_search;
    public TextView noregV,pemegangV, jenisV, reg_lamaV,no_mesinV,tipeV,merekV,kubikasiV,tahunV,kondisiV,bahan_bakarV, no_rangkaV, gabunganV;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_show_data_list_details);
        query_search = getIntent().getStringExtra("NOMOR");
        //Toast.makeText(getApplicationContext(),"Pencarian Data", Toast.LENGTH_SHORT).show();
        noregV = findViewById(R.id.id_asdld_reg_baru);
        pemegangV = findViewById(R.id.id_asdld_pemegang);
        //jenisV = findViewById(R.id.id_asdld_jenis);
        reg_lamaV = findViewById(R.id.id_asdld_reg_lama);
        no_mesinV = findViewById(R.id.id_asdld_no_mesin);
       // tipeV = findViewById(R.id.id_asdld_tipe);
       // merekV = findViewById(R.id.id_asdld_merek);
        kubikasiV = findViewById(R.id.id_asdld_kubikasi);
        tahunV = findViewById(R.id.id_asdld_tahun);
        kondisiV = findViewById(R.id.id_asdld_kondisi);
        bahan_bakarV = findViewById(R.id.id_asdld_bahan_bakar);
        no_rangkaV = findViewById(R.id.id_asdld_no_rangka);
        gabunganV = findViewById(R.id.id_asdld_gabungan);

        getJSONDetail();
    }

    public void getJSONDetail() {
        class GetDataRandis extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ShowDataListDetailActivity.this,"Mengambil Data","Tunggu...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showDataDetails(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rhObj = new RequestHandler();

                // URLS urlsObj = new URLS();
                //String h = urlsObj.TAG_JSON_ARRAY;

                String s = rhObj.sendGetRequestParam(URLS.URL_GET_DATA, query_search);

                return s;
            }
        }


        GetDataRandis ge = new GetDataRandis();
        ge.execute();

    }


    public void showDataDetails(String json){

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(URLS.TAG_JSON_ARRAY);
            if (result.length() == 0) {
                Toast.makeText(getApplicationContext(),"Data Randis Tidak Ditemukan", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }
            JSONObject c = result.getJSONObject(0);
            String noreg = c.getString(URLS.TAG_NOREG_BARU);
            String norangka = c.getString(URLS.TAG_NO_RANGKA);
            String pemegang = c.getString(URLS.TAG_PEMEGANG);
            String jenis = c.getString(URLS.TAG_JENIS_RAN);
            String foto  = c.getString(URLS.TAG_IMAGE);
            String noreg_lama =c.getString(URLS.TAG_NOREG_LAMA);
            String no_mesin  =c.getString(URLS.TAG_NO_MESIN);
            String tipe = c.getString(URLS.TAG_TIPE);
            String merek = c.getString(URLS.TAG_MEREK);
            String kubikasi = c.getString(URLS.TAG_KUBIKASI);
            String tahun = c.getString(URLS.TAG_TAHUN);
            String kondisi = c.getString(URLS.TAG_KONDISI);
            String bahan_bakar = c.getString(URLS.TAG_BAHAN_BAKAR);

            if(foto == null || foto == "" || foto.length()==0 || foto == "null"){
                Picasso.get().load(URLS.URL_GET_GAMBAR+"no_image.png").into((ImageView)findViewById(R.id.app_bar_image)) ;
            } else {
                Picasso.get().load(URLS.URL_GET_GAMBAR+foto).into((ImageView)findViewById(R.id.app_bar_image)) ;
            }
            noregV.setText(noreg);
           // jenisV.setText(jenis);
            pemegangV.setText(pemegang);
            no_rangkaV.setText(norangka);

            reg_lamaV.setText(noreg_lama);
            no_mesinV.setText(no_mesin);
           // tipeV.setText(tipe);
            //merekV.setText(merek);
            kubikasiV.setText(kubikasi);
            tahunV.setText(tahun);
            kondisiV.setText(kondisi);
            bahan_bakarV.setText(bahan_bakar);
            gabunganV.setText(jenis+" "+merek+" "+tipe);

        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
        finish();
    }


    void MulaiWaktu(){

        timer = new Timer();
        Log.i("Main", "Invoking logout timer");
        ShowDataListDetailActivity.LogOutTimerTask logoutTimeTask = new ShowDataListDetailActivity.LogOutTimerTask();
        timer.schedule(logoutTimeTask, URLS.TAG_TIMER); //auto logout in 30 second

    }

    void BerhentiWaktu(){

        if (timer != null) {
            timer.cancel();
            Log.i("Main", "cancel timer");
            timer = null;
        }
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        BerhentiWaktu();
        MulaiWaktu();
    }

    @Override
    protected void onPause() {
        super.onPause();

        BerhentiWaktu();


    }

    @Override
    protected void onResume() {
        super.onResume();
        MulaiWaktu();

    }

    private class LogOutTimerTask extends TimerTask {

        @Override
        public void run() {

            //redirect user to login screen
            Intent i = new Intent(ShowDataListDetailActivity.this, LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.putExtra("TIMEOUT", "Y");
            startActivity(i);
            finish();
        }
    }

    public void LihatGambar(View view){


        final ImagePopup imagePopup = new ImagePopup(this);
        imagePopup.setImageOnClickClose(true);
        ImageView imageView =  findViewById(R.id.app_bar_image);
        imagePopup.initiatePopup(imageView.getDrawable());
        imagePopup.viewPopup();

    }
}
