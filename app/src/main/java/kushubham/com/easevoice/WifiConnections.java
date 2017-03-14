package kushubham.com.easevoice;
/**
 * Created by admin on 2/18/2016.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import static android.content.Context.*;

public class WifiConnections extends AppCompatActivity {
    ListView lv;
    static EditText tv;
    WifiManager mainWifi;
    WifiReceiver receiverWifi;
    int wifis[];
    FloatingActionButton floatingActionButton;
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_wifi_connections);
        tv=(EditText)findViewById(R.id.editText1);
        lv=(ListView)findViewById(R.id.listView);
        // Initiate wifi service manager
        mainWifi = (WifiManager)getSystemService(WIFI_SERVICE);
        // Check for wifi is disabled
        if (mainWifi.isWifiEnabled())
        {
            // If wifi disabled then enable it
            Toast.makeText(getApplicationContext(), "Wifi Enabled",
                    Toast.LENGTH_LONG).show();
            mainWifi.setWifiEnabled(true);
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Wifi Already Enabled",
                    Toast.LENGTH_LONG).show();
        }
        // wifi scaned value broadcast receiver
        receiverWifi = new WifiReceiver();
        // Register broadcast receiver
        // Broacast receiver will automatically call when number of wifi connections changed
        registerReceiver(receiverWifi, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        mainWifi.startScan();


        floatingActionButton=(FloatingActionButton)findViewById(R.id.backbutton1);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                onStop();
            }
        });


        // ListView Item Click Listener
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

            }

        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_easevoice) {
            startActivity(new Intent(WifiConnections.this, AboutEaseVoice.class));
            return true;
        }
        if (id == R.id.action_developer) {
            startActivity(new Intent(WifiConnections.this, AboutDeveloper.class));
            return true;
        }
        if (id == R.id.action_instructions) {
            startActivity(new Intent(WifiConnections.this, Instructions.class));
            return true;
        }
        if (id == R.id.action_easechat) {
            startActivity(new Intent(WifiConnections.this, ChooseOptionActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    protected void onPause() {
        unregisterReceiver(receiverWifi);
        super.onPause();
        finish();
    }
    protected void onResume() {
        registerReceiver(receiverWifi, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        super.onResume();
    }
    // Broadcast receiver class called its receive method
    // when number of wifi connections changed
    class WifiReceiver extends BroadcastReceiver {
        // This method call when number of wifi connections changed
        public void onReceive(Context c, Intent intent) {
            List<ScanResult> wifiScanList = mainWifi.getScanResults();
            wifis = new int[wifiScanList.size()];
            int lev[] = new int[wifiScanList.size()];
            String name[] = new String[wifiScanList.size()];
            for(int i = 0; i < wifiScanList.size(); i++){
                lev[i] = ((wifiScanList.get(i)).level);
                name[i]=((wifiScanList.get(i)).SSID.toString());
            }
            for(int i=0; i < wifiScanList.size(); i++) {
                for (int j = 1; j < (wifiScanList.size() - i); j++) {
                    int temp;
                    String temps = "";
                    if (lev[j - 1]< lev[j]) {
                        //swap the elements!
                        temp = lev[j - 1];
                        temps = name[j - 1];

                        lev[j - 1] = lev[j];
                        name[j - 1] = name[j];
                        lev[j] = temp;
                        name[j] = temps;
                    }
                }
            }
            for(int i = 0; i < wifiScanList.size(); i++){
                int strength;
                lev[i]=Math.abs(lev[i]);
                if(lev[i]<=40)
                {
                    strength=R.drawable.excellent;
                }
                else if(lev[i]<=63)
                {
                    strength=R.drawable.good;
                }
                else if(lev[i]<=80)
                {
                    strength=R.drawable.fair;
                }
                else
                {
                    strength=R.drawable.poor;
                }
                wifis[i] = strength;
            }
            if(wifiScanList.size()>0)
            lv.setAdapter(new CustomAdapter(WifiConnections.this, name,wifis));
        }
    }
}