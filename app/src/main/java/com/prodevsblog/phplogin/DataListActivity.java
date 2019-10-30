package com.prodevsblog.phplogin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class DataListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private ListView listView;
    private String JSON_STRING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_list);
        listView =  findViewById(R.id.id_adl_list_view);
        listView.setOnItemClickListener(this);
        getJSON();
    }

    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DataListActivity.this,"Mengambil Data","Mohon Tunggu...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                LihatData();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String VariableQ = "ambil_semua";
                String s = rh.sendGetRequestParam(URLS.URL_GET_DATA,VariableQ);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    private void LihatData(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(URLS.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String noreg = jo.getString(URLS.TAG_NOREG_BARU);
                String pemegang = jo.getString(URLS.TAG_PEMEGANG);
                HashMap<String,String> anggota = new HashMap<>();
                anggota.put(URLS.TAG_NOREG_BARU,noreg);
                anggota.put(URLS.TAG_PEMEGANG,pemegang);
                list.add(anggota);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                DataListActivity.this, list, R.layout.activity_data_list_detail,
                new String[]{
                        URLS.TAG_NOREG_BARU,
                        URLS.TAG_PEMEGANG
                },
                new int[]{
                        R.id.id_adld_noreg,
                        R.id.id_adld_pemegang
                });

        listView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, DataListActivity.class);
        HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);
        String nomor = map.get(URLS.TAG_NOREG_BARU).toString();
        Intent intentObj = new Intent(DataListActivity.this,DataActivity.class);
        intentObj.putExtra("VARIABLE_EDITTEXT", nomor);
        startActivity(intentObj);

    }

}
