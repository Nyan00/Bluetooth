package com.example.root.bluetooth;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.root.bluetooth.com.example.root.model.soundManager;

public class Main4Activity extends AppCompatActivity {
    private soundManager SoundManager;
    ImageButton btnKick;
    ImageButton btnKick2;
    ImageButton btnTom;
    ImageButton btnTom2;
    ImageButton btnSnare;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        addControls();
        addEvents();
    }

    private void addEvents() {
        final Context context = this;
        btnKick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickBassDrum(v);
            }
        });

        btnKick2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickBass2rum(v);
            }
        });
        btnTom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickTomDrum(v);
            }
        });
        btnTom2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickTom2Drum(v);
            }
        });
        btnSnare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickSnareDrum(v);
            }
        });
    }

    private void clickBass2rum(View v) {
        SoundManager.playSound(3);
    }

    private void clickSnareDrum(View v) {
        SoundManager.playSound(6);
    }

    private void clickTom2Drum(View v) {
        SoundManager.playSound(8);
    }

    private void clickTomDrum(View v) {
        SoundManager.playSound(7);
    }

    private void addControls() {
        btnKick= (ImageButton) findViewById(R.id.btnKick1);
        btnKick2= (ImageButton) findViewById(R.id.btnKick2);
        btnTom= (ImageButton) findViewById(R.id.btntom1);
        btnTom2= (ImageButton) findViewById(R.id.btntom2);
        btnSnare= (ImageButton) findViewById(R.id.btnSnare);

        SoundManager=new soundManager();
        SoundManager.initSounds(getBaseContext());
        SoundManager.addSound(1,R.raw.bassdrum);
        SoundManager.addSound(2,R.raw.crashcymbal);
        SoundManager.addSound(3,R.raw.floortom);
        SoundManager.addSound(4,R.raw.hihatopen);
        SoundManager.addSound(5,R.raw.ridecymbalbow);
        SoundManager.addSound(6,R.raw.snaredrumunmuffled);
        SoundManager.addSound(7,R.raw.tom8inch);
        SoundManager.addSound(8,R.raw.tom12inch);
    }
    public void clickBassDrum(View view) {
        SoundManager.playSound(1);
    }

}
