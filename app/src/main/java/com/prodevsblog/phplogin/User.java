package com.prodevsblog.phplogin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;

public class User {
    private String username, email;
    private int id;
    public User(int id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }
    public String getUsername() {
        return username;
    }
    public String getEmail() {
        return email;
    }

    public int getId() {
        return id;
    }

}
