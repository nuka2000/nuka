package com.prodevsblog.phplogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

public class ViewPDF extends AppCompatActivity {

    public PDFView pdfViewVar;

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
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
