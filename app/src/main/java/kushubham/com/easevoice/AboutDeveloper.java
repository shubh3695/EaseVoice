package kushubham.com.easevoice;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by admin on 2/29/2016.
 */
public class AboutDeveloper extends AppCompatActivity
{
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<DeveloperDataModel> data;
    static View.OnClickListener myOnClickListener;
    FloatingActionButton floatingActionButton;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aboutdev_cards);
        myOnClickListener = new MyOnClickListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        data = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            data.add(new DeveloperDataModel(
                    DeveloperData.getNames(i),
                    DeveloperData.getEmail(i),
                    DeveloperData.getPhone(i),
                    DeveloperData.getImage(i)
            ));
        }
        adapter = new CustomCardAdapter(data);
        recyclerView.setAdapter(adapter);
       floatingActionButton=(FloatingActionButton)findViewById(R.id.backbutton23);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                onStop();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    private  class MyOnClickListener implements View.OnClickListener {
        private final Context context;
        private MyOnClickListener(Context context) {
            this.context = context;
        }
        @Override
        public void onClick(View v) {
            //removeItem(v);
            //  printView(v);
        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_easevoice) {
            startActivity(new Intent(AboutDeveloper.this, AboutEaseVoice.class));
            return true;
        }
        if (id == R.id.action_instructions) {
            startActivity(new Intent(AboutDeveloper.this, Instructions.class));
            return true;
        }
        if (id == R.id.action_easechat) {
            startActivity(new Intent(AboutDeveloper.this,ChooseOptionActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
    @Override
    protected void onStop()
    {
        super.onStop();
    }
}