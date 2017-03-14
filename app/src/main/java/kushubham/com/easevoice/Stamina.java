package kushubham.com.easevoice;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.widget.Toast;
public class Stamina extends Activity
{
    /* This variables need to be global, so we can used them onResume and onPause method to
       stop the listener */
    TelephonyManager Tel;
    MyPhoneStateListener MyListener;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stamina);
        /* Update the listener, and start it */
        MyListener   = new MyPhoneStateListener();
        Tel       = ( TelephonyManager )getSystemService(Context.TELEPHONY_SERVICE);
        Tel.listen(MyListener ,PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
    }
    /* Called when the application is minimized */
    @Override
    protected void onPause()
    {
        super.onPause();
        Tel.listen(MyListener, PhoneStateListener.LISTEN_NONE);
    }
    /* Called when the application resumes */
    @Override
    protected void onResume()
    {
        super.onResume();
        Tel.listen(MyListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
    }
    /* —————————– */
    /* Start the PhoneState listener */
   /* —————————– */
    private class MyPhoneStateListener extends PhoneStateListener
    {
        /* Get the Signal strength from the provider, each tiome there is an update */
        @Override
        public void onSignalStrengthsChanged(SignalStrength signalStrength)
        {
            //(-113 + 2 * ASU).
            double x=signalStrength.getGsmSignalStrength();
            x=(-113)+(2*x);
            super.onSignalStrengthsChanged(signalStrength);
            // read the airplane mode setting
            if(x<=-107 || x==-113)
            {
            boolean isEnabled = Settings.System.getInt(
                    getContentResolver(),
                    Settings.System.AIRPLANE_MODE_ON, 0) == 1;
// toggle airplane mode
            Settings.System.putInt(
                    getContentResolver(),
                    Settings.System.AIRPLANE_MODE_ON, isEnabled ? 0 : 1);
// Post an intent to reload
            Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
            intent.putExtra("state", !isEnabled);
            sendBroadcast(intent);
            Toast.makeText(getApplicationContext(), "GSM Cinr = "
                 + x, Toast.LENGTH_LONG).show();
        }}
    };/* End of private Class */
}/* GetGsmSignalStrength */