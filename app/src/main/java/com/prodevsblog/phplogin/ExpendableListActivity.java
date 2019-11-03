package com.prodevsblog.phplogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ExpendableListActivity extends AppCompatActivity {

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;

    public Intent intentObj;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expendable_list);

        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        expandableListDetail = ExpandableListDataPump.getData();
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        expandableListAdapter = new CustomExpandableListAdapter(this, expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        expandableListTitle.get(groupPosition) + " List Expanded.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        expandableListTitle.get(groupPosition) + " List Collapsed.",
                        Toast.LENGTH_SHORT).show();

            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(
                        getApplicationContext(),
                        expandableListTitle.get(groupPosition)
                                + " -> "
                                + expandableListDetail.get(
                                expandableListTitle.get(groupPosition)).get(childPosition), Toast.LENGTH_SHORT).show();

                String childP = expandableListDetail.get(expandableListTitle.get(groupPosition)).get(childPosition);
                //Toast.makeText(getApplicationContext(),childP, Toast.LENGTH_LONG).show();

                if (childP == "RON 88"){
                    childP = "88";
                }else if(childP == "RON 92"){
                    childP = "92";
                } else if(childP == "HSD"){
                    childP = "HSD";
                }

                if (childP == "Sepeda Motor"){
                    childP = "R2";
                }

                intentObj = new Intent(ExpendableListActivity.this, ShowDataListActivity.class);
                intentObj.putExtra("CHILD_POSITION", childP);
                startActivity(intentObj);
                return true;
            }
        });
    }


    void MulaiWaktu(){

        timer = new Timer();
        Log.i("Main", "Invoking logout timer");
        ExpendableListActivity.LogOutTimerTask logoutTimeTask = new ExpendableListActivity.LogOutTimerTask();
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
            Intent i = new Intent(ExpendableListActivity.this, LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.putExtra("TIMEOUT", "Y");
            startActivity(i);
            finish();
        }
    }
}
