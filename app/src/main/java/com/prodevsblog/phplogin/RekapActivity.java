package com.prodevsblog.phplogin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.prodevsblog.phplogin.URLS.URL_GET_DATA_REKAP;

public class RekapActivity extends AppCompatActivity {
    private String JSON_STRING;
    public String foto, noreg, pemegang, jenisran;
    public TextView tampil2000,tampil2010,tampil2019,tampilbaik,tampilrr,tampilrb,tampil88,tampil92,tampilhsd,tampiltotalran;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rekap2);
        tampil2000 = findViewById(R.id.sd_2000);
        tampil2010 = findViewById(R.id.sd_2010);
        tampil2019 = findViewById(R.id.sd_2019);
        tampilbaik = findViewById(R.id.sd_baik);
        tampilrr = findViewById(R.id.sd_rr);
        tampilrb = findViewById(R.id.sd_rb);
        tampil88 = findViewById(R.id.sd_ron88);
        tampil92 = findViewById(R.id.sd_ron92);
        tampilhsd = findViewById(R.id.sd_hsd);
        tampiltotalran = findViewById(R.id.sd_mobil);
        getJSON();}


        private void getJSON() {
        class GetJSON extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(com.prodevsblog.phplogin.RekapActivity.this, "Mengambil Data", "Mohon Tunggu...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showDataDetails(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                //String VariableQ = "5675-09";
                String s = rh.sendGetRequestParam(URLS.URL_GET_DATA_REKAP,"");
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    public void showDataDetails(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray x = jsonObject.getJSONArray(URLS.TAG_JSON_REKAP);
            if (x.length() == 0) {
                Toast.makeText(getApplicationContext(), "Data Kosong", Toast.LENGTH_LONG).show();
            } else {
                JSONObject c = x.getJSONObject(0);
                String th2000 = c.getString(URLS.TAG_2000);
                String th2019 = c.getString(URLS.TAG_2019);
                String th2010 = c.getString(URLS.TAG_2010);
                tampil2000.setText(Html.fromHtml("Tahun 2000 Kebawah : <b>"+ th2000 +"</b> Unit"));
                tampil2010.setText(Html.fromHtml("Tahun 2001-2010 : <b>"+ th2010 +"</b>  Unit"));
                tampil2019.setText(Html.fromHtml("Tahun 2011-2019 : <b>"+ th2019 +"</b>  Unit"));
                String baik = c.getString(URLS.TAG_baik);
                String rr = c.getString(URLS.TAG_rr);
                String rb = c.getString(URLS.TAG_rb);
                tampilbaik.setText(Html.fromHtml("Baik : <b>"+ baik +"</b> Unit"));
                tampilrr.setText(Html.fromHtml("Rusak Ringan : <b>"+ rr +"</b>  Unit"));
                tampilrb.setText(Html.fromHtml("Rusak Berat : <b>"+ rb +"</b>  Unit"));
                String ron88 = c.getString(URLS.TAG_88);
                String ron92 = c.getString(URLS.TAG_92);
                String hsd = c.getString(URLS.TAG_hsd);
                tampil88.setText(Html.fromHtml("RON 88 (Premium) : <b>"+ ron88 +"</b> Unit"));
                tampil92.setText(Html.fromHtml("RON 92 (Pertamax) : <b>"+ ron92 +"</b>  Unit"));
                tampilhsd.setText(Html.fromHtml("HSD (Solar) : <b>"+ hsd +"</b>  Unit"));
                String tot = c.getString(URLS.TAG_total);
                tampiltotalran.setText(Html.fromHtml("<b>"+ tot +"</b> Unit"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    } @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}

