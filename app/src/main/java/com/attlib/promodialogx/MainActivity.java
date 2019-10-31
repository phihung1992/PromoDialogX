package com.attlib.promodialogx;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.attlib.attpromodialogx.PromoDialog;
import com.attlib.attpromodialogx.PromoDialogManager;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PromoDialogManager.getInstance().load("test", "test", "test");
        findViewById(R.id.btn_show_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean showResult = PromoDialogManager.getInstance().newDialog()
                        .setCanceled(true, false)
                        .setListener(new PromoDialog.OnCallBack() {
                            @Override
                            public void onOk() {

                            }

                            @Override
                            public void onCanceled() {
                                finish();
                            }
                        })
                        .show(MainActivity.this);
                if (!showResult) {
                    finish();
                }
            }
        });
    }
}
