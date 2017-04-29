package com.com.gang.aiyicomeon.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.administrator.ayicomeon.R;

public class Jiedan_xiangqing extends AppCompatActivity {
    private LinearLayout qeurenstar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jiedan_xiangqing);
        qeurenstar = (LinearLayout) findViewById(R.id.qeurenstar);
        qeurenstar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent();
                a.setClass(Jiedan_xiangqing.this, ClasstongjiAct.class);
                startActivity(a);
            }
        });
    }
}
