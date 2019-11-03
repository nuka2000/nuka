package com.prodevsblog.phplogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.github.barteksc.pdfviewer.PDFView;

import java.util.Timer;
import java.util.TimerTask;

public class ViewPDF extends AppCompatActivity {

    public PDFView pdfViewVar;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pdf);

        pdfViewVar = findViewById(R.id.pdfView);
        pdfViewVar.fromAsset("tes.pdf")
                .enableSwipe(true)
                .load();
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
        ViewPDF.LogOutTimerTask logoutTimeTask = new ViewPDF.LogOutTimerTask();
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
            Intent i = new Intent(ViewPDF.this, LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.putExtra("TIMEOUT", "Y");
            startActivity(i);
            finish();
        }
    }
}
