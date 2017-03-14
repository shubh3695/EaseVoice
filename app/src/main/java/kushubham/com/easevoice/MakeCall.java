package kushubham.com.easevoice;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by admin on 2/24/2016.
 */
public class MakeCall extends AppCompatActivity {
    List contactname =new ArrayList();
    List contactnum=new ArrayList();
    List match=new ArrayList();
    ListView lv;
FloatingActionButton fbb;
    String callto;
    String call;
    int c=0;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.call_someone);
        lv=(ListView)findViewById(R.id.listView);

        fbb=(FloatingActionButton)findViewById(R.id.backbutton7);
        fbb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        });
        Bundle bundle=getIntent().getExtras();
        callto=bundle.getString("callto");
        call=callto;
        if(!containsDigit(callto))
        {
            giveContactList();
            searchList();
            // ListView Item Click Listener
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    String str=match.get(position) .toString()     ;
                    str= str.replace(" ","");
                    call=str.substring(str.length()-10);
                    Intent in=new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + call));
                    try{
                        startActivity(in);
                    }
                    catch (android.content.ActivityNotFoundException ex){
                        Toast.makeText(getApplicationContext(), "Phone Application is not found", Toast.LENGTH_SHORT).show();
                    }
                }

            });
        }
        else
        {
            Intent in=new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + call));
            try{
                startActivity(in);
            }
            catch (android.content.ActivityNotFoundException ex){
                Toast.makeText(getApplicationContext(), "Phone Application is not founded", Toast.LENGTH_SHORT).show();
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
        if (id == R.id.action_easevoice) {
            startActivity(new Intent(MakeCall.this, AboutEaseVoice.class));
            return true;
        }
        if (id == R.id.action_developer) {
            startActivity(new Intent(MakeCall.this, AboutDeveloper.class));
            return true;
        }
        if (id == R.id.action_instructions) {
            startActivity(new Intent(MakeCall.this, Instructions.class));
            return true;
        }
        if (id == R.id.action_easechat) {
            startActivity(new Intent(MakeCall.this, ChooseOptionActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public final boolean containsDigit(String s) {
        boolean containsDigit = false;
        s=s.trim();
        if (s != null && !s.isEmpty()) {
            for (char c : s.toCharArray()) {
                if (containsDigit = Character.isDigit(c)) {
                    break;
                }
            }
        }

        return containsDigit;
    }
    protected void onPause() {
        super.onPause();
        finish();
    }
    private void giveContactList()throws NullPointerException
    {
        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        int i=0;
        if (phones.getCount() > 0)
        {
            phones.moveToFirst();
            c=phones.getCount();
            while (i < c)
            {
                contactname.add(i, phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));
                contactnum.add(i, phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                i++;
                phones.moveToNext();
            }
        }
        phones.close();
    }
    private  void searchList()
    {
        String callnam=callto;
        callnam=callnam.toLowerCase().replace(" ","");
        int r=0;
        for(int i = 0; i < c; i++)
        {
            String m= contactname.get(i).toString();
            m=m.toLowerCase().replace(" ","");
            if (m.contains(callnam))
            {
                        match.add(r, contactname.get(i) + " " + contactnum.get(i));
                r++;
            }
        }
        if(match.isEmpty())
        {
            Toast.makeText(getApplicationContext(), "No Such Contact Found", Toast.LENGTH_LONG).show();
            finish();
        }
        else
        {

        }
        lv.setAdapter(new CustomAdapterMapsInstruct(MakeCall.this,match));
    }
}