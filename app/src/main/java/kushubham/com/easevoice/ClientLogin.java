package kushubham.com.easevoice;

/**
 * Created by admin on 3/4/2016.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
public class ClientLogin extends AppCompatActivity {
    static final int SocketServerPORT = 8080;

    EditText editTextUserName, editTextAddress;
    ImageButton buttonConnect;
    TextView textPort;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_login);


        editTextUserName = (EditText) findViewById(R.id.username);
        editTextAddress = (EditText) findViewById(R.id.address);
        textPort = (TextView) findViewById(R.id.port);

        textPort.setText("port: " + SocketServerPORT);
        buttonConnect = (ImageButton) findViewById(R.id.connect);


        buttonConnect.setOnClickListener(buttonConnectOnClickListener);
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
            startActivity(new Intent(ClientLogin.this, AboutEaseVoice.class));
            return true;
        }
        if (id == R.id.action_developer) {
            startActivity(new Intent(ClientLogin.this, AboutDeveloper.class));
            return true;
        }
        if (id == R.id.action_instructions) {
            startActivity(new Intent(ClientLogin.this, Instructions.class));
            return true;
        }
        if (id == R.id.action_easechat) {
            // startActivity(new Intent(ServerSide.this, ChooseOptionActivity.class));
            //   return true;
        }


        return super.onOptionsItemSelected(item);
    }



    OnClickListener buttonConnectOnClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            String textUserName = editTextUserName.getText().toString();
            if (textUserName.equals("")) {
                Toast.makeText(ClientLogin.this, "Enter User Name",
                        Toast.LENGTH_LONG).show();
                return;
            }
            String textAddress = editTextAddress.getText().toString();
            if (textAddress.equals("")) {
                Toast.makeText(ClientLogin.this, "Enter Address",
                        Toast.LENGTH_LONG).show();
                return;
            }


            Intent intent = new Intent(getApplicationContext(), ClientSide.class);
            intent.putExtra("clientusername",editTextUserName.getText().toString());
            intent.putExtra("address",textAddress);
            intent.putExtra("port",SocketServerPORT);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);

        }
    };

}