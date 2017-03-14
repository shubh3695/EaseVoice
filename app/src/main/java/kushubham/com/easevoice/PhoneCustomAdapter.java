package kushubham.com.easevoice;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;


/**
 * Created by admin on 2/26/2016.
 */

public class PhoneCustomAdapter extends BaseAdapter {

    Context context;
    List pheader;
    List pdetail;

    private static LayoutInflater inflater=null;
    public PhoneCustomAdapter(PhoneDetails phoneDetails, List header, List detail) {
        // TODO Auto-generated constructor stub
        pheader=header;
        context=phoneDetails;
        pdetail=detail;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return pheader.size();
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
        TextView tv1;
        TextView tv2;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.phonedetailcustom, null);
        holder.tv1=(TextView) rowView.findViewById(R.id.textView1);
        holder.tv2=(TextView) rowView.findViewById(R.id.textView2);
        holder.tv1.setText(pheader.get(position).toString());
        holder.tv2.setText((pdetail.get(position)+" ").toString());

        return rowView;
    }
}