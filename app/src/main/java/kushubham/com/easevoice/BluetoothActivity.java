package kushubham.com.easevoice;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by admin on 3/3/2016.
 */
public class BluetoothActivity extends Activity
{
    private Button bluetoothgallery;
    private ArrayAdapter<String> btArrayAdapter;
    private ListView listDevicesFound;
    List<String> scanlist;
    private Set<BluetoothDevice> pairedDevices;
    private final BluetoothAdapter mBluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bluetoothxml);
        DisplayMetrics dm= new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width=dm.widthPixels;
        int height=dm.heightPixels;
        getWindow().setLayout((int)(width*0.85),(int)(height*0.6));
        bluetoothgallery=(Button)findViewById(R.id.gallerybt);
        listDevicesFound=(ListView)findViewById(R.id.devicesfound);
        btArrayAdapter = new ArrayAdapter<String>(BluetoothActivity.this, android.R.layout.simple_expandable_list_item_1);
        scanlist=new ArrayList<>();
        registerReceiver(ActionFoundReceiver,
                new IntentFilter(BluetoothDevice.ACTION_FOUND));
        Bundle bundle=getIntent().getExtras();
        String command=bundle.getString("bluetoothstring");
        if(command.contains("enable") ||command.contains("turn on") || command.isEmpty())
        {
            if (!mBluetoothAdapter.isEnabled()) {
                Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(turnOn, 1);
                Toast.makeText(BluetoothActivity.this, "Allow Permission to enable"
                        , Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(BluetoothActivity.this, "Already on",
                        Toast.LENGTH_LONG).show();
            }
            finish();
        }
        else if(command.contains("disable") ||command.contains("turn off"))
        {
            if(!mBluetoothAdapter.isEnabled())
                Toast.makeText(BluetoothActivity.this,"Already Off ",Toast.LENGTH_LONG).show();
            else
            {
                // TODO Auto-generated method stub
                mBluetoothAdapter.disable();
                Toast.makeText(BluetoothActivity.this, "Turned off",
                        Toast.LENGTH_LONG).show();
            }
            finish();
        }
        else if(command.contains("paired devices") ||command.contains("pair devices") ||command.contains("weird devices")||
                command.contains("Dell devices") || command.contains("world devices"))
        {
            if(!mBluetoothAdapter.isEnabled())
            {
                Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(turnOn, 1);
                Toast.makeText(BluetoothActivity.this, "Allow Permission to enable"
                        , Toast.LENGTH_LONG).show();
            }
            // TODO Auto-generated method stub
            pairedDevices = mBluetoothAdapter.getBondedDevices();
            @SuppressWarnings("rawtypes")
            ArrayList list = new ArrayList();
            for(BluetoothDevice bt : pairedDevices)
                list.add(bt.getName());
            Toast.makeText(BluetoothActivity.this,"Showing Paired Devices",
                    Toast.LENGTH_SHORT).show();
            listDevicesFound.setAdapter(new BluetoothCustomAdapter(BluetoothActivity.this, list));
        }
        else if(command.contains("scan devices") ||command.contains("scan available devices") ||
                command.contains("scanning devices") || command.contains("send"))
        {
            if(!mBluetoothAdapter.isEnabled()) {
                Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(turnOn, 1);
                Toast.makeText(BluetoothActivity.this, "Allow Permission to enable"
                        , Toast.LENGTH_LONG).show();
            }
            scanlist.clear();
            Intent discoverableIntent = new
                    Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(discoverableIntent);
            mBluetoothAdapter.startDiscovery();
        }
        else
        {
            finish();
        }
        bluetoothgallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
// View  the gallery
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
// Start the activity
                startActivity(intent);
            }
        });
    }
    private final BroadcastReceiver ActionFoundReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            String action = intent.getAction();
            if(BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                scanlist.add(device.getName() + "\n" + device.getAddress());
                btArrayAdapter.notifyDataSetChanged();
                listDevicesFound.setAdapter(new BluetoothCustomAdapter(BluetoothActivity.this, scanlist));
            }
        }};
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        unregisterReceiver(ActionFoundReceiver);
    }
}
