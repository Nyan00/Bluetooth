package com.example.root.bluetooth;

import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class Main3Activity extends AppCompatActivity {
    public static final String EXTRAS_DEVICE_NAME="DEVICE_NAME";
    public static final String EXTRAS_DEVICE_ADDRESS="DEVICE_ADDRESS";
    private final static String TAG=Main3Activity.class.getSimpleName();
    private BluetoothLeService mBluetoothLeService;
    private String mDeviceAddress , mDeviceName;
    private boolean mConnected=false;
    private List< BluetoothGattService> bluetoothGattServices;
    private BluetoothGattCharacteristic mNotifyCharacteristic;
    TextView txtName;
    TextView txtAdd;
    TextView txt1;
    TextView txt2;
    TextView txt3;
    TextView txt4;

    // Code to manage Service lifecycle.
    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
            if (!mBluetoothLeService.initialize()) {
                Log.e(TAG, "Unable to initialize Bluetooth");
                finish();
            }
            // Automatically connects to the device upon successful start-up initialization.
            mBluetoothLeService.connect(mDeviceAddress);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBluetoothLeService = null;
        }
    };

    // Handles various events fired by the Service.
    // ACTION_GATT_CONNECTED: connected to a GATT server.
    // ACTION_GATT_DISCONNECTED: disconnected from a GATT server.
    // ACTION_GATT_SERVICES_DISCOVERED: discovered GATT services.
    // ACTION_DATA_AVAILABLE: received data from the device.  This can be a result of read
    //                        or notification operations.
    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
                mConnected = true;
                txt1.setText(R.string.connected);

                //updateConnectionState(R.string.connected);
                invalidateOptionsMenu();
            } else if (BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action)) {
                mConnected = false;
                txt1.setText(R.string.not_connect);
                //updateConnectionState(R.string.disconnected);
                invalidateOptionsMenu();
                //clearUI();
            } else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
                // Show all the supported services and characteristics on the user interface.
                //displayGattServices(mBluetoothLeService.getSupportedGattServices());

                bluetoothGattServices = mBluetoothLeService.getSupportedGattServices();
                //BluetoothGattCharacteristic characteristic = setService(bluetoothGattServices);
                //if (characteristic != null) {
                  //  final int charaProp = characteristic.getProperties();
                    //if ((charaProp | BluetoothGattCharacteristic.PROPERTY_READ) > 0) {
                        // If there is an active notification on a characteristic, clear
                        // it first so it doesn't update the data field on the user interface.
                      //  if (mNotifyCharacteristic != null) {
                        //    mBluetoothLeService.setCharacteristicNotification(
                          //          mNotifyCharacteristic, false);
                            //mNotifyCharacteristic = null;
                        //}
                        //mBluetoothLeService.readCharacteristic(characteristic);
                    //}
                    /*if ((charaProp | BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {
                        mNotifyCharacteristic = characteristic;
                        mBluetoothLeService.setCharacteristicNotification(
                                characteristic, true);
                    }*/
                //}
            } else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
                //displayData(intent.getStringExtra(BluetoothLeService.EXTRA_DATA));
                Toast.makeText(getApplicationContext(), intent.getStringExtra(BluetoothLeService.EXTRA_DATA), 0).show();
            }
        }
    };
    ImageButton btnPlay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        final Intent intent= getIntent();
        mDeviceName=intent.getStringExtra(EXTRAS_DEVICE_NAME);
        mDeviceAddress=intent.getStringExtra(EXTRAS_DEVICE_ADDRESS);




        addControls();
        addEvents();
        txtName.setText(mDeviceName);
        txtAdd.setText(mDeviceAddress);
        txt1.setText("Not Connect");

        Intent gattServiceIntent = new Intent(getApplicationContext(), BluetoothLeService.class);
        bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);

    }

    @Override
    protected void onResume(){
        super.onResume();
        registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
        if (mBluetoothLeService != null) {
            final boolean result = mBluetoothLeService.connect(mDeviceAddress);
            Log.d(TAG, "Connect request result=" + result);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mGattUpdateReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
        mBluetoothLeService = null;
    }

    private void clearUI() {
        //mGattServicesList.setAdapter((SimpleExpandableListAdapter) null);
        //mDataField.setText(R.string.no_data);
        txtName.setText(R.string.no_data);
        txtAdd.setText(R.string.no_data);
        txt1.setText(R.string.no_data);
    }

    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
        return intentFilter;
    }
    private void addEvents() {
        final Context context = this;
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Main4Activity.class);
                startActivity(intent);
                mBluetoothLeService.connect(mDeviceAddress);

            }
        });
    }

    private void addControls()
    {
        btnPlay= (ImageButton) findViewById(R.id.btnplay);

        txtName= (TextView) findViewById(R.id.txtName);
        txtAdd= (TextView) findViewById(R.id.txtAdd);
        txt1= (TextView) findViewById(R.id.txt1);
        txt2= (TextView) findViewById(R.id.txt2);
        txt3= (TextView) findViewById(R.id.txt3);
        txt4= (TextView) findViewById(R.id.txt4);
    }
}
