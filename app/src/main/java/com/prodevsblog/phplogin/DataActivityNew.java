package com.prodevsblog.phplogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

public class DataActivityNew extends AppCompatActivity {
    public String foto,noreg,pemegang,jenisran;
    public TextView noregV,pemegangV,jenisV,fotoV;
    public String foto1 ="no_gambar.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        noreg = extras.getString("NOREG");
        pemegang = extras.getString("PEMEGANG");
        jenisran = extras.getString("JENIS");
        noregV = findViewById(R.id.id_ad_noreg);
        pemegangV = findViewById(R.id.id_ad_pemegang);
        jenisV = findViewById(R.id.id_ad_jenis_ran_text);
        noregV.setText(noreg);
        jenisV.setText(jenisran);
        pemegangV.setText(pemegang);
        foto = extras.getString("FOTO");
        if(foto.length() < 2 || foto == "null" || foto =="(NULL)" || foto == null){
            Picasso.get().load(URLS.URL_GET_GAMBAR+foto1).into((ImageView)findViewById(R.id.id_ad_imageView_foto));
        } else
            {
            Picasso.get().load(URLS.URL_GET_GAMBAR+foto).into((ImageView)findViewById(R.id.id_ad_imageView_foto)) ;
        }
    }
}
