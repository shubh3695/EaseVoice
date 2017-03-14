package kushubham.com.easevoice;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.CallLog;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2/18/2016.
 */
public class OpenApplication extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        String match = bundle.getString("package");
        final PackageManager pm = getPackageManager(); //get a list of installed appss
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        List lauchintent = new ArrayList(packages.size());
        List labelname = new ArrayList(packages.size());
        int i = 0;
        if(match.equalsIgnoreCase("playstore") )
        {
            match="googleplaystore";
        }
        if (match.compareTo("calllog") == 0 || match.compareTo("calllogs") == 0 || match.compareTo("phone")==0)
        {
            Intent in = new Intent();
            in.setAction(Intent.ACTION_VIEW);
            in.setType(CallLog.Calls.CONTENT_TYPE);
            startActivity(in);
            onPause();
        } else {
            for (ApplicationInfo packageInfo : packages) {
                lauchintent.add(i, pm.getLaunchIntentForPackage(packageInfo.packageName));
                labelname.add(i, pm.getApplicationLabel(packageInfo));

                i++;
            }
            int flag = 0;
            for (i = 0; i < packages.size(); i++) {
                String search = (labelname.get(i)).toString();
                search = search.replace(" ", "");
                if (search.equalsIgnoreCase(match)) {
                    Intent ii = (Intent) lauchintent.get(i);
                    ii.addCategory(Intent.CATEGORY_LAUNCHER);
                    startActivity(ii);
                    flag = 1;
//do we need to terminate the loop to prevent lag timer
                    break;
                }
            }
            if (flag == 0) {
                Toast.makeText(OpenApplication.this, "NO Such Application Found", Toast.LENGTH_LONG).show();
                onPause();
            }
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
    finish();
    }
}