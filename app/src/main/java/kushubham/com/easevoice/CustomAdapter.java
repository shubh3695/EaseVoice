package kushubham.com.easevoice;

/**
 * Created by admin on 2/26/2016.
 */
import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
public class CustomAdapter extends BaseAdapter{
    String [] result;
    Context context;
    int [] imageId;
    WifiManager mainWifi;
    int flag=0;
    private static LayoutInflater inflater=null;
    public CustomAdapter(WifiConnections wifiConnections, String[] name, int[] image) {
        // TODO Auto-generated constructor stub
        result=name;
        context=wifiConnections;
        imageId=image;

        if( result.length==0)
        {flag=1;
            result[0]="Wifi Not Available";
            imageId[0]=R.drawable.notavailable;
        }

        mainWifi= (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView tv;
        ImageView img;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.wifi_custom_view, null);
        holder.tv=(TextView) rowView.findViewById(R.id.textView1);
        holder.img=(ImageView) rowView.findViewById(R.id.imageView1);
        holder.tv.setText(result[position]);
        holder.img.setImageResource(imageId[position]);
        rowView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                if(flag==1) {
                    Toast.makeText(context, "No Available Wifi " , Toast.LENGTH_LONG).show();
                }
                else{
                    String key=WifiConnections.tv.getText().toString();
                    setwifi(result[position],key);

                }
            }
        });
        return rowView;
    }
    private void setwifi(String ssidname,String key) {

        if (mainWifi.isWifiEnabled() == false) {
            // If wifi disabled then enable it

            mainWifi.setWifiEnabled(true);
        }
        WifiConfiguration wifiConfig = new WifiConfiguration();
        wifiConfig.SSID = String.format("\"%s\"", ssidname);

        if(key.length()==0)
        {
            wifiConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE); // This is for a public network which dont have any authentication
        }
        else
        {
            wifiConfig.preSharedKey = String.format("\"%s\"", key);
        }
        int netId = mainWifi.addNetwork(wifiConfig);
        mainWifi.disconnect();
        mainWifi.enableNetwork(netId, true);
        mainWifi.reconnect();
        WifiInfo wifiInfo = mainWifi.getConnectionInfo();
    }
}