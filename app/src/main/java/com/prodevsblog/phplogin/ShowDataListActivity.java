package com.prodevsblog.phplogin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class ShowDataListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    public String childP;
    private ListView listView;
    private String JSON_STRING;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_show_data_list);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        childP = extras.getString("CHILD_POSITION");
        //Toast.makeText(getApplicationContext(),childP, Toast.LENGTH_LONG).show();
        getJSON(childP);
        listView =  findViewById(R.id.id_asdl_List_View);
        listView.setOnItemClickListener(this);
    }

    private void getJSON(final String ChildPo){

        class GetJSON extends AsyncTask<Void, Void, String> {
            String query_pencarian = ChildPo;
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ShowDataListActivity.this,"Mengambil Data","Mohon Tunggu...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                LihatData(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(URLS.URL_GET_DATA_BY_KATEGORI,query_pencarian);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    private void LihatData(String json){

        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(URLS.TAG_JSON_ARRAY);

            if (result.length() == 0){
                Toast.makeText(getApplicationContext(),"Data Kosong", Toast.LENGTH_LONG).show();
                finish();
            }else{
                for(int i = 0; i<result.length(); i++){
                    JSONObject jo = result.getJSONObject(i);
                    String noreg = jo.getString(URLS.TAG_NOREG_BARU);
                    String pemegang = jo.getString(URLS.TAG_PEMEGANG);
                    String kondisi = jo.getString(URLS.TAG_KONDISI);
                    String bahan_bakar = jo.getString(URLS.TAG_BAHAN_BAKAR);
                    String jenis = jo.getString(URLS.TAG_JENIS_RAN);
                    HashMap<String, String> randis = new HashMap<>();
                    randis.put(URLS.TAG_NOREG_BARU,noreg);
                    randis.put(URLS.TAG_PEMEGANG,pemegang);
                    randis.put(URLS.TAG_KONDISI,kondisi);
                    randis.put(URLS.TAG_BAHAN_BAKAR,bahan_bakar);
                    randis.put(URLS.TAG_JENIS_RAN,jenis);
                    list.add(randis);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                ShowDataListActivity.this, list, R.layout.activitiy_show_data_list_item,
                new String[]{
                        URLS.TAG_NOREG_BARU,
                        URLS.TAG_PEMEGANG,
                        URLS.TAG_KONDISI,
                        URLS.TAG_BAHAN_BAKAR,
                        URLS.TAG_JENIS_RAN
                },
                new int[]{
                        R.id.id_asdli_no_reg,
                        R.id.id_asdli_pemegang,
                        R.id.id_asdli_kondisi,
                        R.id.id_asdli_bahan_bakar,
                        R.id.id_asdli_jenis
                });

        listView.setAdapter(adapter);
    }


    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        HashMap<String, String> map =(HashMap)parent.getItemAtPosition(position);
        String nomor = map.get(URLS.TAG_NOREG_BARU).toString();
        Intent intentObj = new Intent(ShowDataListActivity.this,ShowDataListDetailActivity.class);
        intentObj.putExtra("NOMOR", nomor);
        startActivity(intentObj);
    }

    void MulaiWaktu(){

        timer = new Timer();
        Log.i("Main", "Invoking logout timer");
        ShowDataListActivity.LogOutTimerTask logoutTimeTask = new ShowDataListActivity.LogOutTimerTask();
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
            Intent i = new Intent(ShowDataListActivity.this, LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.putExtra("TIMEOUT", "Y");
            startActivity(i);
            finish();
        }
    }
}
