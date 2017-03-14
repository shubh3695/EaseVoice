package kushubham.com.easevoice;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
/**
 * Created by admin on 2/18/2016.
 */
@SuppressWarnings("ALL")
public class SpeechRecognize extends AppCompatActivity implements RecognitionListener
{
    private ArrayList<String> matches;
    private ListView mlvTextMatches;
    private ImageButton button;
    private ProgressBar progressBar;
    private SpeechRecognizer speech = null;
    private Intent recognizerIntent;
    private String LOG_TAG = "VoiceRecognitionActivity";
    // flag for Internet connection status
    Boolean isInternetPresent = false;
    // Connection detector class
    ConnectionDetector cd;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cd = new ConnectionDetector(getApplicationContext());
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        button = (ImageButton) findViewById(R.id.btSpeak);
        progressBar.setVisibility(View.INVISIBLE);
        speech = SpeechRecognizer.createSpeechRecognizer(this);
        speech.setRecognitionListener(this);
        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "en");
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                this.getPackageName());
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);

button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        // get Internet status
        isInternetPresent = cd.isConnectingToInternet();

        // check for Internet status
            if (!isInternetPresent) {
                // Internet connection is not present
            // Ask user to connect to Internet
            showAlertDialog(SpeechRecognize.this, "No Internet Connection",
                    "You don't have internet connection.", false);
        }
        else {
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setIndeterminate(true);
        speech.startListening(recognizerIntent); }
    }
});
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
            startActivity(new Intent(SpeechRecognize.this, AboutEaseVoice.class));
            return true;
        }
        if (id == R.id.action_developer) {
            startActivity(new Intent(SpeechRecognize.this, AboutDeveloper.class));
            return true;
        }
        if (id == R.id.action_instructions) {
            startActivity(new Intent(SpeechRecognize.this, Instructions.class));
            return true;
        }
        if (id == R.id.action_easechat) {
            startActivity(new Intent(SpeechRecognize.this, ChooseOptionActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void Click(View view) //floatingactionbutton
    {
        Intent i=new Intent(getApplicationContext(),PopupActivity.class);
       i.putStringArrayListExtra("arraylist",matches);
        startActivity(i);
    }
    @Override
    public void onReadyForSpeech(Bundle params) {
        Log.i(LOG_TAG, "onReadyForSpeech");
    }
    @Override
    public void onBeginningOfSpeech() {
        Log.i(LOG_TAG, "onBeginningOfSpeech");
        progressBar.setIndeterminate(false);
        progressBar.setMax(10);
    }
    @Override
    public void onRmsChanged(float rmsdB) {
        Log.i(LOG_TAG, "onRmsChanged: " + rmsdB);
        progressBar.setProgress((int) rmsdB);
    }
    @Override
    public void onBufferReceived(byte[] buffer) {
        Log.i(LOG_TAG, "onBufferReceived: " + buffer);
    }
    @Override
    public void onEndOfSpeech() {
        Log.i(LOG_TAG, "onEndOfSpeech");
        speech.stopListening();
        progressBar.setIndeterminate(false);
        progressBar.setVisibility(View.INVISIBLE);
    }
    @Override
    public void onError(int errorCode) {
        Toast.makeText(this,"Check your Connectivity...",Toast.LENGTH_LONG).show();
    }
    @Override
    public void onResults(Bundle results) {
        Log.i(LOG_TAG, "onResults");
               matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);  //plan to return arraylist when the size f A.L is g>0
            Intent send=new Intent(SpeechRecognize.this,MainActivity.class);
            send.putStringArrayListExtra("allchoice", matches);
            startActivity(send);
    }
    @Override
    public void onPartialResults(Bundle partialResults) {
        Log.i(LOG_TAG, "onPartialResults");
    }
    @Override
    public void onEvent(int arg0, Bundle arg1)
    {
        Log.i(LOG_TAG, "onEvent");
    }
    public void showAlertDialog(Context context, String title, String message, Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(message);

        // Setting alert dialog icon
        alertDialog.setIcon((status) ? R.drawable.success : R.drawable.fail);
        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        // Showing Alert Message
        alertDialog.show();
    }
   @Override
    protected void onPause() {
       super.onPause();
  }
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }
}