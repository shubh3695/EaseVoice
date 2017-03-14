package kushubham.com.easevoice;

import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.text.DecimalFormat;

/**
 * Created by admin on 2/19/2016.
 */
public class GPSActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener
{
    private TextView latit,llong,speed;
    private Button mapopen;
    private double speedy,lat,lng;
    private FloatingActionButton speaking,backb;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private final String LOG_TAG = "BLY_LKO";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gpshome);
        latit=(TextView)findViewById(R.id.lat);
        llong=(TextView)findViewById(R.id.llong);
        speed=(TextView)findViewById(R.id.speed);
        speaking=(FloatingActionButton)findViewById(R.id.speaking);
        backb=(FloatingActionButton)findViewById(R.id.backbutton);
        LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean enabled = service
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
                  // check if enabled and if not send user to the Gps settings
                  // Better solution would be to display a dialog and suggesting to
                  // go to the settings
        if (!enabled) {
            Toast.makeText(getApplicationContext(),"Enable Location",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
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
            startActivity(new Intent(GPSActivity.this, AboutEaseVoice.class));
            return true;
        }
        if (id == R.id.action_developer) {
            startActivity(new Intent(GPSActivity.this, AboutDeveloper.class));
            return true;
        }
        if (id == R.id.action_instructions) {
            startActivity(new Intent(GPSActivity.this, Instructions.class));
            return true;
        }
        if (id == R.id.action_easechat) {
            startActivity(new Intent(GPSActivity.this,ChooseOptionActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void BackClick(View view)
    {
       onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
    @Override
    public void onConnected(Bundle bundle) throws SecurityException {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(2000); // Update location every  2 second
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }
    @Override
    public void onConnectionSuspended(int i) {
        Log.i(LOG_TAG, "GoogleApiClient connection has been suspend");
    }
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(LOG_TAG, "GoogleApiClient connection has failed");
    }
    @Override
    protected void onStart() {
        super.onStart();
        // Connect the client.
        mGoogleApiClient.connect();
    }
    @Override
    protected void onStop() {
        // Disconnecting the client invalidates it.
        mGoogleApiClient.disconnect();
        super.onStop();
    }
    @Override
    protected void onPause() {
        // Disconnecting the client invalidates it.
        mGoogleApiClient.disconnect();
        super.onPause();
    }
    @Override
    public void onLocationChanged(Location location) {
             lat= location.getLatitude();
             lng= location.getLongitude();
        DecimalFormat df=new DecimalFormat("#.###");
        speedy=(location.getSpeed());
        latit.setText("Lat: "+lat);
        llong.setText("Long:"+lng);
        speed.setText("Speed: " + df.format(speedy)+"km/hr");
        speaking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GPSActivity.this, MapsActivity.class);
                intent.putExtra("latitude", lat);
                intent.putExtra("longitude", lng);
                intent.putExtra("speed", speedy);
                startActivity(intent);
            }
        });
    }
}
