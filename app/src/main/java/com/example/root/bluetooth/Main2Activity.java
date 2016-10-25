package com.example.root.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class Main2Activity extends AppCompatActivity {

    Button btnScan;
    ImageButton btnNext;
    ImageButton btnBack;
    private BluetoothAdapter BA;
    private Set<BluetoothDevice> pairedDevices;
    private ArrayAdapter<String> BTArrayAdapter;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        addControls();
        addEvents();
    }

    private void addControls() {
        btnScan= (Button) findViewById(R.id.button3);
        btnNext= (ImageButton) findViewById(R.id.btnNext);
        btnBack= (ImageButton) findViewById(R.id.btnBack);
        lv = (ListView) findViewById(R.id.lvPair);
        BA=BluetoothAdapter.getDefaultAdapter();
        BTArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        lv.setAdapter(BTArrayAdapter);
    }

    private void list(View v) {
        // get paired devices
        pairedDevices = BA.getBondedDevices();

        // put it's one to the adapter
        for(BluetoothDevice device : pairedDevices)
        BTArrayAdapter.add(device.getName()+ "\n" + device.getAddress());

        Toast.makeText(getApplicationContext(),"Show Paired Devices",
                Toast.LENGTH_SHORT).show();

    }
    final BroadcastReceiver bReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // add the name and the MAC address of the object to the arrayAdapter
                BTArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                BTArrayAdapter.notifyDataSetChanged();
            }
        }
    };
    public void find(View view) {
        if (BA.isDiscovering()) {
            // the button is pressed when it discovers, so cancel the discovery
            BA.cancelDiscovery();
        }
        else {
            BTArrayAdapter.clear();
            BA.startDiscovery();

            registerReceiver(bReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));
        }
    }
    private void off(View v) {
        BA.disable();
        Toast.makeText(getApplicationContext(),"Bluetooth turned off",
                Toast.LENGTH_LONG).show();
    }

    private void on(View v) {
        if (!BA.isEnabled()) {
            Intent turnOnIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnOnIntent, 0);

            Toast.makeText(getApplicationContext(),"Bluetooth turned on" ,
                    Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getApplicationContext(),"Bluetooth is already on",
                    Toast.LENGTH_LONG).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if(requestCode == 0){
            if(BA.isEnabled()) {
                Toast.makeText(getApplicationContext(),"Enabled",Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(),"Disabled",Toast.LENGTH_LONG).show();
            }
        }
    }
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        unregisterReceiver(bReceiver);
    }

    private void addEvents() {
        if (BA==null)
        {
            btnScan.setEnabled(false);
            Toast.makeText(getApplicationContext(),"Not support",Toast.LENGTH_LONG).show();
        }
        else
        {
            btnScan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    on(v);
                    find(v);
                    list(v);
                }
            });
            //btnScan.setOnClickListener(new View.OnClickListener() {
              //  @Override
                //public void onClick(View v) {
                  //  off(v);
                //}
            //});
            final Context context = this;
            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, Main3Activity.class);
                    startActivity(intent);
                }
            });
            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, MainActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
}
