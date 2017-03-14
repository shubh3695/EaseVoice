package kushubham.com.easevoice;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by admin on 2/22/2016.
 */
public class PopupActivity extends Activity
{
    private ArrayList<String> matches=new ArrayList<>(0);
    private ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popupxml);
        list=(ListView)findViewById(R.id.textwords);
        DisplayMetrics dm= new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width=dm.widthPixels;
        int height=dm.heightPixels;
        getWindow().setLayout((int)(width*0.8),(int)(height*0.8));
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null)
            matches=bundle.getStringArrayList("arraylist");
        if(matches!=null)
            list.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,matches));
        else
        {
            Toast.makeText(getApplicationContext(),"Nothing to Show",Toast.LENGTH_LONG).show();
        }

        // ListView Item Click Listener
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition = position;

                // ListView Clicked item value
                String itemval = (String) list.getItemAtPosition(position);

                Intent tts = new Intent(PopupActivity.this, TexttoSpeak.class);
                tts.putExtra("data", itemval);
                startActivity(tts);
                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);

            }

        });



    }
}