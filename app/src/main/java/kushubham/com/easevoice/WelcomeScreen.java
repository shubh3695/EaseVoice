package kushubham.com.easevoice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by admin on 3/2/2016.
 */
public class WelcomeScreen extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.welcome);
        Thread timer =     new Thread() {
            public void run() {
                try {
                    sleep(2500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally
                {
                    Intent myInt = new Intent(WelcomeScreen.this, SpeechRecognize.class);
                    startActivity(myInt);
                    overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                    finish();
                }
            }
        };
        timer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
