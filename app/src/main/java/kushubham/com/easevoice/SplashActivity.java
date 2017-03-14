package kushubham.com.easevoice;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * Created by admin on 2/24/2016.
 */
public class SplashActivity extends Activity implements AnimationListener
{
    Animation animRotate;
    ImageView iv;
    //MediaPlayer ourSong;
    private Boolean firstTime = null;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splashscreen);
        iv=(ImageView)findViewById(R.id.imageView3);
       // ourSong.start();
        animRotate = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.rotate);
        // set animation listener
        animRotate.setAnimationListener(this);
        iv.startAnimation(animRotate);
                }
    private boolean isFirstTime() {
        if (firstTime == null)
        {
            SharedPreferences mPreferences = this.getSharedPreferences("first_time", Context.MODE_PRIVATE);
            firstTime = mPreferences.getBoolean("firstTime", true);
            if (firstTime) {
                SharedPreferences.Editor editor = mPreferences.edit();
                editor.putBoolean("firstTime", false);
                editor.commit();
            }
        }
        return firstTime;
    }
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
    @Override
    public void onAnimationStart(Animation animation)
    {
    }
    @Override
    public void onAnimationEnd(Animation animation)
    {
     //   ourSong.release();
        if(isFirstTime())
        {
            Intent myInt=new Intent(SplashActivity.this,WelcomeScreen.class);
            startActivity(myInt);
            overridePendingTransition(R.anim.slide_in_up,R.anim.slide_out_up);
        }
        else
        {
            Intent myInt = new Intent(SplashActivity.this, SpeechRecognize.class);
            startActivity(myInt);
            overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
        }

    }
    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}