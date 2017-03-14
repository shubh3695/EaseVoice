package kushubham.com.easevoice;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by admin on 2/19/2016.
 */
public class PhoneDetails extends AppCompatActivity
{
    ListView lv;
    FloatingActionButton fbb;
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
        setContentView(R.layout.phonedetails);
        fbb=(FloatingActionButton)findViewById(R.id.back3);
        fbb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        });
        String os= Build.MODEL;//
        String ver=android.os.Build.VERSION.SDK;      // API Level
        String device=android.os.Build.DEVICE    ;  //     // Device
        String s1=Build.BRAND ;        // Brand
        String product=Build.PRODUCT ;   // Product
        String s2=Build.DISPLAY; //display
        String s3=Build.getRadioVersion();
        String s4=Build.FINGERPRINT; //fingerprint
        String s5=Build.MANUFACTURER;
        String s6=Build.SERIAL;
        String s7=Build.USER;
        String s8= String.valueOf(Build.TIME);
        String s9=Build.TYPE; //type
        lv=(ListView)findViewById(R.id.listView11);
        phnheader.add("Model");
        phndetails.add(os);
        phnheader.add("Device");
        phndetails.add(device);
        phnheader.add("Brand");
        phndetails.add(s1);
        phnheader.add("Product");
        phndetails.add(product);
        phnheader.add("SDK Version");
        phndetails.add(ver);
        phnheader.add("Manufacturer");
        phndetails.add(s5);
        phnheader.add("Display");
        phndetails.add(s2);
        phnheader.add("Build Fingerprint");
        phndetails.add(s4);
        phnheader.add("Build Time");
        phndetails.add(s8);
        phnheader.add("Serial Number");
        phndetails.add(s6);
        phnheader.add("Build Type");
        phndetails.add(s9);
        lv.setAdapter(new PhoneCustomAdapter(PhoneDetails.this, phnheader,phndetails));
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
            startActivity(new Intent(PhoneDetails.this, AboutEaseVoice.class));
            return true;
        }
        if (id == R.id.action_developer) {
            startActivity(new Intent(PhoneDetails.this, AboutDeveloper.class));
            return true;
        }
        if (id == R.id.action_instructions) {
            startActivity(new Intent(PhoneDetails.this, Instructions.class));
            return true;
        }
        if (id == R.id.action_easechat) {
            startActivity(new Intent(PhoneDetails.this, ChooseOptionActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
