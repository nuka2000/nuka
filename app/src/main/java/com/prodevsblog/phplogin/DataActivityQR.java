package com.prodevsblog.phplogin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DataActivityQR extends AppCompatActivity {

    public String query_search;
    public TextView noregV,pemegangV, jenisV;
    private String JSON_STRING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        query_search = getIntent().getStringExtra("QUERY_SEARCHQR");
        setContentView(R.layout.activity_data);
        noregV = findViewById(R.id.id_ad_noreg);
        pemegangV = findViewById(R.id.id_ad_pemegang);
        jenisV = findViewById(R.id.id_ad_pemegang);
        getJSONDetail();
        Toast.makeText(getApplicationContext(),"Pencarian Data", Toast.LENGTH_SHORT).show();
    }

    public void getJSONDetail() {
        class GetDataRandis extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DataActivityQR.this,"Mengambil Data","Tunggu...",false,false);
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

                String s = rhObj.sendGetRequestParam(URLS.URL_GET_DATA,query_search);

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
                Toast.makeText(getApplicationContext(), "Data Randis Tidak Ditemukan", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);

            }
            JSONObject c = result.getJSONObject(0);
            String noreg = c.getString(URLS.TAG_NOREG_BARU);
            String pemegang = c.getString(URLS.TAG_PEMEGANG);
            String jenis = c.getString(URLS.TAG_JENIS_RAN);
            String foto  = c.getString(URLS.TAG_IMAGE);
            if(foto == null || foto == "" || foto.length()==0 || foto == "null"){
                Picasso.get().load(URLS.URL_GET_GAMBAR+"no_gambar.jpg").into((ImageView)findViewById(R.id.id_ad_imageView_foto)) ;
            } else {
                Picasso.get().load(URLS.URL_GET_GAMBAR+foto).into((ImageView)findViewById(R.id.id_ad_imageView_foto)) ;
            }


            noregV.setText(noreg);
            jenisV.setText(jenis);
            pemegangV.setText(pemegang);


        } catch (JSONException e) {
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



