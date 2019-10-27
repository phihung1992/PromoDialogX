package com.attlib.promodialogx;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.attlib.attpromodialogx.PromoDialogManager;

public class MainActivity extends AppCompatActivity {
    String dumpPackageName = "com.app2tap.logicriddles";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PromoDialogManager.getInstance().load(dumpPackageName);
        findViewById(R.id.btn_show_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PromoDialogManager.getInstance().newDialog()
                        .setCanceled(true, false)
                        .show(MainActivity.this);
            }
        });
    }
}
