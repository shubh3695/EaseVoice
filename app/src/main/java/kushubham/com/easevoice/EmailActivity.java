package kushubham.com.easevoice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
/**
 * Created by admin on 2/19/2016.
 */
public class EmailActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle=getIntent().getExtras();
        String msg=bundle.getString("emailtext");
        Intent emailIntent=new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_EMAIL,"Enter EmailAddress");
        emailIntent.putExtra(Intent.EXTRA_SUBJECT," ");
        emailIntent.setType("plain/text");
        emailIntent.putExtra(Intent.EXTRA_TEXT,msg);
        startActivity(emailIntent);
        if (emailIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(emailIntent);
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

}
