package com.prodevsblog.phplogin;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    public EditText editText1V;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText1V = findViewById(R.id.editText1);
        //query_search1 = getIntent().getStringExtra(String.valueOf(editText1V));
    }
    public void TombolRekap(View view){
        startActivity(new Intent(MainActivity.this, RekapActivity.class));
    }

    public void TombolCariDetail(View view){

        Intent intent = new Intent(MainActivity.this, ExpendableListActivity.class);
        startActivity(intent);

    }

    public void TombolTampilSemuaData(View view){
        Intent intentObj = new Intent(MainActivity.this,DataListActivity.class);
        startActivity(intentObj);
    }
    public void Tombolqr(View view){
        startActivity(new Intent(MainActivity.this,QRCodeActivity.class));
    }

    public void Tombolpdf(View view){
        startActivity(new Intent(MainActivity.this,ViewPDF.class));
    }

    public void PencetCari(View view){
        String pencarian = editText1V.getText().toString();
        CekJSON(pencarian);
    }



    public void CekJSON(final String penomoran) {
        class GetDatJSON extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            String nomorinside = penomoran;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this,"Fetching...","Wait...",false,false);
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showDataDetails(s);
            }
            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(URLS.URL_GET_DATA,nomorinside);
                return s;
            }
        }
        GetDatJSON ge = new GetDatJSON();
        ge.execute();
    }

    public void showDataDetails(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(URLS.TAG_JSON_ARRAY);
            if (result.length() == 0){
                Toast.makeText(getApplicationContext(),"Data Randis Tidak Ditemukan", Toast.LENGTH_LONG).show();
            } else {
                JSONObject c = result.getJSONObject(0);
                String foto = c.getString(URLS.TAG_IMAGE);
               String noreg = c.getString(URLS.TAG_NOREG_BARU);
                String pemegang = c.getString(URLS.TAG_PEMEGANG);
                String jenis = c.getString(URLS.TAG_JENIS_RAN);
                Toast.makeText(getApplicationContext(),""+noreg+"", Toast.LENGTH_SHORT).show();
                Intent intentObj = new Intent(this, ShowDataListDetailActivity.class);
//               intentObj.putExtra("FOTO", foto);
                intentObj.putExtra("NOMOR", noreg);
//               intentObj.putExtra("PEMEGANG", pemegang);
//               intentObj.putExtra("JENIS", jenis);
                startActivity(intentObj);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
