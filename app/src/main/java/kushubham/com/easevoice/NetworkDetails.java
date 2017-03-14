package kushubham.com.easevoice;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by admin on 2/19/2016.
 */
public class NetworkDetails extends AppCompatActivity
{
    FloatingActionButton fb;
    ListView lv;
    //TextView tv;
    String sim_operator_Name;

    ArrayList<String> phnheader=new ArrayList<>();
    ArrayList<String> phndetails=new ArrayList<>();
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.networkdetails);
        fb=(FloatingActionButton)findViewById(R.id.back2);
        lv=(ListView)findViewById(R.id.listView12);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        });
        //Get the instance of TelephonyManager
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        //Calling the methods of TelephonyManager the returns the information
        String IMEINumber = tm.getDeviceId();
        String subscriberID = tm.getDeviceId();
        String SIMSerialNumber = tm.getSimSerialNumber();
        String networkCountryISO = tm.getNetworkCountryIso();
        String SIMCountryISO = tm.getSimCountryIso();
        String softwareVersion = tm.getDeviceSoftwareVersion();
        String voiceMailNumber = tm.getVoiceMailNumber();
        sim_operator_Name= tm.getSimOperatorName();
        //Get the phone type
        String strphoneType = "";
        int phoneType = tm.getPhoneType();
        switch (phoneType) {
            case (TelephonyManager.PHONE_TYPE_CDMA):
                strphoneType = "CDMA";
                break;
            case (TelephonyManager.PHONE_TYPE_GSM):
                strphoneType = "GSM";
                break;
            case (TelephonyManager.PHONE_TYPE_NONE):
                strphoneType = "NONE";
                break;
        }
        //getting information if phone is in roaming
        boolean isRoaming = tm.isNetworkRoaming();
        phnheader.add("Sim Operator");
        phndetails.add(sim_operator_Name);
        phnheader.add("IMEI Number");
        phndetails.add(IMEINumber);
        phnheader.add(" SubscriberID");
        phndetails.add(subscriberID);
        phnheader.add("Sim Serial Number");
        phndetails.add(SIMSerialNumber);
        phnheader.add("Network Country ISO");
        phndetails.add(networkCountryISO);
        phnheader.add("SIM Country ISO");
        phndetails.add(SIMCountryISO);
        phnheader.add(" IMEI Software Version");
        phndetails.add(softwareVersion);
        phnheader.add("Voice Mail Number");
        phndetails.add(voiceMailNumber);
        phnheader.add("Phone Network Type");
        phndetails.add(strphoneType);
        phnheader.add("In Roaming?");
        phndetails.add(String.valueOf(isRoaming));
        lv.setAdapter(new NetworkCustomAdapter(NetworkDetails.this, phnheader,phndetails));
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
            startActivity(new Intent(NetworkDetails.this, AboutEaseVoice.class));
            return true;
        }
        if (id == R.id.action_developer) {
            startActivity(new Intent(NetworkDetails.this, AboutDeveloper.class));
            return true;
        }
        if (id == R.id.action_instructions) {
            startActivity(new Intent(NetworkDetails.this, Instructions.class));
            return true;
        }
        if (id == R.id.action_easechat) {
            startActivity(new Intent(NetworkDetails.this, ChooseOptionActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
