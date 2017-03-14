package kushubham.com.easevoice;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class Instructions extends AppCompatActivity
{
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<String> data;
    static View.OnClickListener myOnClickListener;
    TextToSpeech t1;
    FloatingActionButton floatingActionButton;
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instructions);
        String[] planets = new String[]
                { "Search followed by any query to search over internet",
                "Enable Wifi for enabling wifi,select wifi connection" ,
                "Disable Wifi for disabling wifi" ,
                "Open followed by the name of application you want to open" ,
                "Email followed by the subject of email" ,
                "Message followed by receiver name/number to send text" ,
                "Network Details to see details of your network" ,
                "Phone Details to see details of your phone" ,
                "Bluetooth to enable disable or see available/paired devices" ,
                "Enable Mobile Data to enable mobile data" ,
                "Mobile data status check mobile data is enabled or not" ,
                "My Location for your location on map and instantaneous speed" ,
                "Call followed by contact name/number to make a phone call" ,
                "Return the commands in voice of regular said input",
                "EaseChat for chatting, connect to a WiFi network without internet."
                };
        myOnClickListener = new MyOnClickListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view_instructions);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        data = new ArrayList<>();
        for (int i = 0; i < planets.length; i++) {
            data.add(planets[i]);
        }
        adapter = new CustomInstructionsCardAdapter(data);
        recyclerView.setAdapter(adapter);
        floatingActionButton=(FloatingActionButton)findViewById(R.id.backbutton12);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
onBackPressed();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        });
    }
    private  class MyOnClickListener implements View.OnClickListener {
        private final Context context;
        private MyOnClickListener(Context context) {
            this.context = context;
        }
        @Override
        public void onClick(View v) {
           dothisView(v);
            //  printView(v);
        }
        private void dothisView(View v)
        {
            int select=recyclerView.getChildPosition(v);
            if(select==0)
            {
                Intent searchAct=new Intent(Instructions.this,SearchActivity.class);
                searchAct.putExtra("search", "Who Is Prime Minister of India");
                startActivity(searchAct);
                ttl("Searching Who Is Prime Minister of India");
            }
            else
                if(select==1)
                {
                    Intent intent = new Intent(getApplicationContext(), WifiConnections.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                    ttl("performing enable wifi");

                }
            else if(select==2)
                {
                    WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
                    if (wifiManager.isWifiEnabled() == false)
                    {
                        // If wifi disabled then enable it
                        Toast.makeText(getApplicationContext(), "Wifi Already Disabled",
                                Toast.LENGTH_LONG).show();
                    }
                    else {
                        wifiManager.setWifiEnabled(false);
                        Toast.makeText(getApplicationContext(), "WiFi has been disabled", Toast.LENGTH_SHORT).show();
                    }
                    ttl("performing disable wifi");
                }
            else
                if(select==3)
                {
                    ttl("performing open play store");
                    Intent app=new Intent(Instructions.this,OpenApplication.class);
                    app.putExtra("package", "playstore");
                    startActivity(app);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
                else
                if(select==4)
                {
                    Intent mail=new Intent(Instructions.this,EmailActivity.class);
                    mail.putExtra("emailtext","How are you?");
                    startActivity(mail);
                    overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                    ttl("performing email how are you");
                }
                else
                if(select==5)
                { Intent msg=new Intent(Instructions.this,PhoneMessage.class);
                    msg.putExtra("msg","How are you?");
                    startActivity(msg);
                    overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                    ttl("performing message how are you");
                }
                else
                if(select==6)
                {
                    Intent details=new Intent(Instructions.this,NetworkDetails.class);
                    startActivity(details);
                    overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                    ttl("performing show network details");
                }
                else
                if(select==7)
                {
                    Intent det=new Intent(Instructions.this,PhoneDetails.class);
                    startActivity(det);
                    overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                    ttl("performing show phone details");
                }
                else
                if(select==8)
                {
                    Intent i=new Intent(Instructions.this,BluetoothActivity.class);
                    i.putExtra("bluetoothstring","");
                    startActivity(i);
                    ttl("performing open bluetooth");
                }
                else
                if(select==9)
                {
                    Intent intent = new Intent();
                    intent.setClassName("com.android.settings", "com.android.settings.Settings$DataUsageSummaryActivity");
                    startActivityForResult(intent, 1);
                    ttl("performing enable mobile data");
                }
                else
                if(select==10)
                {
                    boolean state = getMobileDataState();
                    if (state) {
                        Toast.makeText(Instructions.this,"Data Already On",Toast.LENGTH_LONG).show();
                    } else
                        Toast.makeText(Instructions.this,"Data Disconnected",Toast.LENGTH_LONG).show();
                    ttl("performing mobile data status");
                }
                else
                if(select==11)
                {
                    Intent myloc=new Intent(Instructions.this,GPSActivity.class);
                    startActivity(myloc);
                    overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                    ttl("performing my location");
                }
                else
                if(select==12)
                {
                    ttl("performing call One Two Three");
                    Intent call=new Intent(Instructions.this,MakeCall.class);
                    call.putExtra("callto","123");
                  //  ttl("performing call 12345");
                    startActivity(call);
                    overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                    //ttl("performing call 12345");
                }
                else
                if(select==13)
                {
                    ttl("you are using the finest Ease Voice");
                }
                else
                if(select==14)
                {
                    startActivity(new Intent(Instructions.this,ChooseOptionActivity.class));
                    ttl("Opening Offline Chat application");
                }
        }
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
        if (id == R.id.action_easevoice)
        {
            startActivity(new Intent(Instructions.this, AboutEaseVoice.class));
            return true;
        }
        if (id == R.id.action_developer) {
            startActivity(new Intent(Instructions.this, AboutDeveloper.class));
            return true;
        }
        if (id == R.id.action_instructions) {
            //startActivity(new Intent(SpeechRecognize.this, AboutEaseVoice.class));
            //return true;
        }
        if (id == R.id.action_easechat) {
            startActivity(new Intent(Instructions.this, ChooseOptionActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void ttl(String str)
    {
       final String data= str;
        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.US);
                    t1.setSpeechRate(0.8f);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                    {
                        t1.speak(data, TextToSpeech.QUEUE_FLUSH, null, null);
                    } else
                    {
                        t1.speak(data, TextToSpeech.QUEUE_FLUSH, null);
                    }
                }
            }
        });
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
    protected void onPause() {
        super.onPause();
        finish();
    }
}