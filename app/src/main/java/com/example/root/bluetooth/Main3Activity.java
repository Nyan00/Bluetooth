package com.example.root.bluetooth;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Main3Activity extends AppCompatActivity {
    ImageButton btnPlay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        addControls();
        addEvents();
    }

    private void addEvents() {
        final Context context = this;
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Main4Activity.class);
                startActivity(intent);
            }
        });
    }

    private void addControls()
    {
        btnPlay= (ImageButton) findViewById(R.id.btnplay);
    }
}
