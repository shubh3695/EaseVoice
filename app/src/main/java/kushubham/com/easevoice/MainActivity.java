package kushubham.com.easevoice;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import java.lang.reflect.Method;
import java.util.ArrayList;
public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle=getIntent().getExtras();
        ArrayList<String> textMatchList=bundle.getStringArrayList("allchoice");
        if (!textMatchList.isEmpty())
        {
            if (textMatchList.get(0).contains("search"))   //done
            {
                Intent searchAct=new Intent(MainActivity.this,SearchActivity.class);
                String searchQuery = textMatchList.get(0).replace("search", "");
                searchAct.putExtra("search", searchQuery);
                startActivity(searchAct);
            } else if ( textMatchList.get(0).contains("enable WiFi"))
            {
                Intent intent = new Intent(getApplicationContext(), WifiConnections.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
            } else if (textMatchList.get(0).contains("disable WiFi"))
            {
                WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);

                if (wifiManager.isWifiEnabled() == false)
                {
                    // If wifi disabled then enable it
                    Toast.makeText(getApplicationContext(), "Wifi Already Disabled",
                            Toast.LENGTH_LONG).show();
                    finish();
                }
                else {
                    wifiManager.setWifiEnabled(false);
                    Toast.makeText(this, "WiFi has been disabled", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
            else if (textMatchList.get(0).contains("open"))  //done
            {
                String match =textMatchList.get(0).replace("open", "");
                match =match.replace(" ", "");
                Intent app=new Intent(MainActivity.this,OpenApplication.class);
                app.putExtra("package", match);
                startActivity(app);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                // Log.i(LOG_TAG, match);
            }
            else if ( textMatchList.get(0).contains("email") ||textMatchList.get(0).contains("Gmail") )  //done
            {
                String add;
                Intent mail=new Intent(MainActivity.this,EmailActivity.class);
                if(textMatchList.get(0).contains("email"))
                {
                     add=textMatchList.get(0).replace("email","");
                }
                else{
                    add=textMatchList.get(0).replace("Gmail","");
                }
                mail.putExtra("emailtext",add);
                startActivity(mail);
                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
            }
            else if (textMatchList.get(0).contains("message")) //done
            {
                String messageBody = textMatchList.get(0).replace("message", "");
                Intent msg=new Intent(MainActivity.this,PhoneMessage.class);
                msg.putExtra("msg",messageBody);
                startActivity(msg);
                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
            }
            else if (textMatchList.get(0).contains("network details")||textMatchList.get(0).contains("networks details")) //done
            {
                Intent details=new Intent(MainActivity.this,NetworkDetails.class);
                startActivity(details);
                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
            }
            else if(textMatchList.get(0).contains("phone details"))
            {
                Intent det=new Intent(MainActivity.this,PhoneDetails.class);
                startActivity(det);
                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
            }
            else if(textMatchList.get(0).contains("chat application"))
            {
                startActivity(new Intent(MainActivity.this,ChooseOptionActivity.class));
            }
            else
            if (textMatchList.get(0).contains("Bluetooth"))  //done
            {
                Intent i=new Intent(MainActivity.this,BluetoothActivity.class);
                String str=textMatchList.get(0);
                str.replace("Bluetooth","").trim();
                i.putExtra("bluetoothstring",str);
                startActivity(i);
            }
            else
            if (textMatchList.get(0).contains("enable mobile data"))
            {
                Intent intent = new Intent();
                intent.setClassName("com.android.settings", "com.android.settings.Settings$DataUsageSummaryActivity");
                startActivityForResult(intent, 1);
            } else if (textMatchList.get(0).contains("mobile data status"))
            {
                boolean state = getMobileDataState();
                   if (state) {
                       Toast.makeText(MainActivity.this,"Data Already On",Toast.LENGTH_LONG).show();
                    } else
                       Toast.makeText(MainActivity.this,"Data Disconnected",Toast.LENGTH_LONG).show();
            }
            else if (textMatchList.get(0).contains("my location")) {
                Intent myloc=new Intent(MainActivity.this,GPSActivity.class);
                startActivity(myloc);
                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
            }
            else if (textMatchList.get(0).contains("call")||textMatchList.get(0).contains("Call")) {
                String callto = textMatchList.get(0).replace("call","") ;
                Intent call=new Intent(MainActivity.this,MakeCall.class);
                call.putExtra("callto",callto);
                startActivity(call);
                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
            }
            else
            {
                Intent searchAct=new Intent(MainActivity.this,SearchActivity.class);
                String searchQuery = textMatchList.get(0);
                searchAct.putExtra("search", searchQuery);
                startActivity(searchAct);
            }
        }
    }
    public boolean getMobileDataState() {
        try {
            TelephonyManager telephonyService = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            Method getMobileDataEnabledMethod = telephonyService.getClass().getDeclaredMethod("getDataEnabled");
            if (null != getMobileDataEnabledMethod) {
                boolean mobileDataEnabled = (Boolean) getMobileDataEnabledMethod.invoke(telephonyService);
                return mobileDataEnabled;
            }
        } catch (Exception ex) {
            // Log.e(TAG, "Error getting mobile data state", ex);
        }
        return false;
    }
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
