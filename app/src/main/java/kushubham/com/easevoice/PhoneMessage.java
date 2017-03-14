package kushubham.com.easevoice;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
/**
 * Created by admin on 2/19/2016.
 */
public class PhoneMessage extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String num="",body="";
        Bundle bundle=getIntent().getExtras();
        String message=bundle.getString("msg");
        if(message.charAt(1)>=48&& message.charAt(1)<=57)
        {
            String arr[]=message.split(" ",5);
            num=arr[1]+arr[2]+arr[3];
            body=arr[4];
        }
        else {
            String arr[] = message.split(" ", 3);
            num=arr[1];
            body=arr[2];
        }try {
            Intent sendIntent = new Intent(Intent.ACTION_VIEW);
            sendIntent.putExtra("sms_body", body);
            sendIntent.putExtra("address", num);
            sendIntent.setType("vnd.android-dir/mms-sms");
            if (sendIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(sendIntent);
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),
                    "SMS faild, please try again later!",
                    Toast.LENGTH_LONG).show();
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}