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
    ImageButton btnkick;
    ImageButton btnfloor;
    ImageButton btnTom;
    ImageButton btnTom2;
    ImageButton btnSnare;
    ImageButton btnRide;
    ImageButton btnCrash;
    ImageButton btnhh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        addControls();
        addEvents();
    }


    private void addEvents() {
        final Context context = this;
        btnkick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickBassDrum(v);
            }
        });
        btnfloor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               clickFloorDrum(v);
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
        btnRide.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {clickRideDrum(v);
                                       }
                                   });
        btnCrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCrashDrum(v);
            }
        });
        btnhh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickHhDrum(v);
            }
        });
    }

    private void clickFloorDrum(View v) {
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

    private void clickRideDrum(View v) {
        SoundManager.playSound(5);
    }

    private void clickCrashDrum(View v) { SoundManager.playSound(2);}

    private void clickHhDrum(View v) {
        SoundManager.playSound(4);
    }

    public void clickBassDrum(View view) {
        SoundManager.playSound(1);
    }



    private void addControls() {
        btnkick= (ImageButton) findViewById(R.id.btnkick);
        btnfloor= (ImageButton) findViewById(R.id.btnfloor);
        btnTom= (ImageButton) findViewById(R.id.btntom);
        btnTom2= (ImageButton) findViewById(R.id.btntom2);
        btnSnare= (ImageButton) findViewById(R.id.btnsnare);
        btnCrash= (ImageButton) findViewById(R.id.btncrash);
        btnRide= (ImageButton) findViewById(R.id.btnride);
        btnhh= (ImageButton) findViewById(R.id.btnhh);

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

}
