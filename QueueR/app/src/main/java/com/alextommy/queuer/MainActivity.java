package com.alextommy.queuer;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;


import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.alextommy.queuer.R.id;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.view.Menu;
import android.content.Intent;
import com.google.zxing.Result;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    private ZXingScannerView mScannerView;
    private View popupView;
    private PopupWindow popupWindow;
    private boolean on = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.title);
    }

    public void showList(View view) {
        Intent intent = new Intent(this, EntryList.class);
        intent.putExtra("add", "false");
        startActivity(intent);

    }

    public void addEntry(View view) {
        mScannerView.stopCamera();
        String name   = ((EditText)popupView.findViewById(R.id.nameEntry)).getText().toString();
        String size   = ((EditText)popupView.findViewById(R.id.sEntry)).getText().toString();
        String time   = ((EditText)popupView.findViewById(R.id.timeEntry)).getText().toString();
        Intent intent = new Intent(this, EntryList.class);
        intent.putExtra("add", "true");
        intent.putExtra("name", name);
        intent.putExtra("size", size);
        intent.putExtra("time", time);
        popupWindow.dismiss();
        startActivity(intent);
    }

    public void showPopup(View view) {
        LayoutInflater layoutInflater = (LayoutInflater) getBaseContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        popupView = layoutInflater.inflate(R.layout.popup,
                (ViewGroup) findViewById(R.id.popup));
        popupWindow = new PopupWindow(popupView, 600, 800, true);
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
        // Log.v("pls", "work")
    }

    public void showPopup() {
        LayoutInflater layoutInflater = (LayoutInflater) getBaseContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        popupView = layoutInflater.inflate(R.layout.popup,
                (ViewGroup) findViewById(R.id.popup));
        popupWindow = new PopupWindow(popupView, 600, 800, true);
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
        // Log.v("pls", "work")
    }

    public void scanQR(View view){
        mScannerView = new ZXingScannerView(this); // Programmatically initialize the scanner view
        setContentView(mScannerView);
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        on = true;
        mScannerView.startCamera(); // Start camera
//        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                showPopup();
//            }
//        }, 3000);
    }

    @Override
    public void onPause() {
        super.onPause();
        if(on != false) {
            mScannerView.stopCamera(); // Stop camera on pause
            setContentView(R.layout.title);
        }
        on = false;
    }

    @Override
    public void handleResult(Result rawResult) {
        showPopup();
    }



}