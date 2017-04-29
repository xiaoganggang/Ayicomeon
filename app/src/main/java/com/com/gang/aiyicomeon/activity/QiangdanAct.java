package com.com.gang.aiyicomeon.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.administrator.ayicomeon.R;

public class QiangdanAct extends AppCompatActivity {
    private ImageView qiang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qiangdan);
        qiang=(ImageView)findViewById(R.id.qiang);
        qiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
