package kushubham.com.easevoice;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.Window;
import android.view.WindowManager;

import java.util.Locale;

/**
 * Created by admin on 2/24/2016.
 */
public class TexttoSpeak extends Activity {
    TextToSpeech t1;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
       getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.speaklayout);

        Bundle bundle = getIntent().getExtras();
        final String data = bundle.getString("data");
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
    public void onPause(){
        if(t1 !=null){
            t1.stop();
            t1.shutdown();
        }
        super.onPause();
    }
    public void onDestroy(){
        if(t1 !=null){
            t1.stop();
            t1.shutdown();
        }
        super.onDestroy();
    }
}
