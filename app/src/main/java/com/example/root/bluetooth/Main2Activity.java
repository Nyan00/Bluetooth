package com.example.root.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Handler;
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
import java.util.HashMap;
import java.util.Set;

public class Main2Activity extends AppCompatActivity {

    Button btnScan;
    ImageButton btnNext;
    ImageButton btnBack;

    ListView lvAvailableDevices;

    private boolean mScanning;
    private Handler mHandler;
    private BluetoothAdapter mBluetoothAdapter;

    // Stops scanning after 10 seconds.
    private static final long SCAN_PERIOD = 10000;
    private static final int REQUEST_ENABLE_BT = 1;

    private ArrayAdapter<String> lvAdapterAvailableDevices;
    private ArrayList<BluetoothDevice> arrayListAvailableDevices;
    private HashMap<String, BluetoothDevice> hashMapAvailableDevices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        addControls();
        addEvents();

        mHandler = new Handler();

        // Use this check to determine whether BLE is supported on the device. Then
        // you can selectively disable BLE-related features.
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, R.string.ble_not_supported, Toast.LENGTH_SHORT).show();
            finish();
        }

        // Initializes Bluetooth adapter.
        final BluetoothManager bluetoothManager =
                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();

        if (mBluetoothAdapter == null) {
            Toast.makeText(this, R.string.ble_not_supported, Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void addControls() {
        btnScan= (Button) findViewById(R.id.button3);
        btnNext= (ImageButton) findViewById(R.id.btnNext);
        btnBack= (ImageButton) findViewById(R.id.btnBack);

        lvAdapterAvailableDevices = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, 0);
        lvAvailableDevices = (ListView) findViewById(R.id.lvAvai);
        lvAvailableDevices.setAdapter(lvAdapterAvailableDevices);
        hashMapAvailableDevices = new HashMap<String, BluetoothDevice>();
        arrayListAvailableDevices = new ArrayList<BluetoothDevice>();
    }



    private void addEvents() {
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lvAdapterAvailableDevices.clear();
                hashMapAvailableDevices.clear();
                arrayListAvailableDevices.clear();
                scanLeDevice(true);
            }
        });

        final Intent intent = new Intent(this, Main3Activity.class);

        lvAvailableDevices.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final BluetoothDevice device = arrayListAvailableDevices.get(i);
                if (device == null) return;

                intent.putExtra(Main3Activity.EXTRAS_DEVICE_NAME, device.getName());
                intent.putExtra(Main3Activity.EXTRAS_DEVICE_ADDRESS, device.getAddress());
                if (mScanning) {
                    mBluetoothAdapter.stopLeScan(mLeScanCallback);
                    mScanning = false;
                }
                startActivity(intent);
            }
        });
        final Context context = this;

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Main3Activity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Ensures Bluetooth is available on the device and it is enabled. If not,
        // displays a dialog requesting user permission to enable Bluetooth.
        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // User chose not to enable Bluetooth.
        if (requestCode == REQUEST_ENABLE_BT && resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(this, R.string.turn_bt_on, Toast.LENGTH_SHORT).show();
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void scanLeDevice(final boolean enable) {
        if (enable) {
            // Stops scanning after a pre-defined scan period.
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mScanning = false;
                    mBluetoothAdapter.stopLeScan(mLeScanCallback);
                }
            }, SCAN_PERIOD);

            mScanning = true;
            mBluetoothAdapter.startLeScan(mLeScanCallback);
        } else {
            mScanning = false;
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
        }
    }

    // Device scan callback.
    private BluetoothAdapter.LeScanCallback mLeScanCallback =
            new BluetoothAdapter.LeScanCallback() {
                @Override
                public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //mLeDeviceListAdapter.addDevice(device);
                            //mLeDeviceListAdapter.notifyDataSetChanged();
                            if (!hashMapAvailableDevices.containsKey(device.getAddress())) {
                                lvAdapterAvailableDevices.add(device.getName() + "|" + device.getAddress());
                                hashMapAvailableDevices.put(device.getAddress(), device);
                                arrayListAvailableDevices.add(device);
                            }

                        }
                    });
                }
            };
}
