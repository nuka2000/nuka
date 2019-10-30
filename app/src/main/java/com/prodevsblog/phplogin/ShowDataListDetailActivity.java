package com.prodevsblog.phplogin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ShowDataListDetailActivity extends AppCompatActivity {

    public String query_search;
    public TextView noregV,pemegangV, jenisV, reg_lamaV,no_mesinV,tipeV,merekV,kubikasiV,tahunV,kondisiV,bahan_bakarV, no_rangkaV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data_list_detail);
        query_search = getIntent().getStringExtra("NOMOR");
        //Toast.makeText(getApplicationContext(),"Pencarian Data", Toast.LENGTH_SHORT).show();
        noregV = findViewById(R.id.id_asdld_reg_baru);
        pemegangV = findViewById(R.id.id_asdld_pemegang);
        jenisV = findViewById(R.id.id_asdld_jenis);
        reg_lamaV = findViewById(R.id.id_asdld_reg_lama);
        no_mesinV = findViewById(R.id.id_asdld_no_mesin);
        tipeV = findViewById(R.id.id_asdld_tipe);
        merekV = findViewById(R.id.id_asdld_merek);
        kubikasiV = findViewById(R.id.id_asdld_kubikasi);
        tahunV = findViewById(R.id.id_asdld_tahun);
        kondisiV = findViewById(R.id.id_asdld_kondisi);
        bahan_bakarV = findViewById(R.id.id_asdld_bahan_bakar);
        no_rangkaV = findViewById(R.id.id_asdld_no_rangka);

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
            if(foto == null || foto == "" || foto.length()==0 || foto == "null"){
                Picasso.get().load(URLS.URL_GET_GAMBAR+"no_gambar.jpg").into((ImageView)findViewById(R.id.app_bar_image)) ;
            } else {
                Picasso.get().load(URLS.URL_GET_GAMBAR+foto).into((ImageView)findViewById(R.id.app_bar_image)) ;
            }
            noregV.setText(noreg);
            jenisV.setText(jenis);
            pemegangV.setText(pemegang);
            no_rangkaV.setText(norangka);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
