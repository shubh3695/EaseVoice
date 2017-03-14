package kushubham.com.easevoice;

/**
 * Created by admin on 2/26/2016.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class CustomAdapterMapsInstruct extends BaseAdapter{
    Context context;
    List result;
    private static LayoutInflater inflater=null;
    public CustomAdapterMapsInstruct(MakeCall instructions, List name) {
        // TODO Auto-generated constructor stub
        result=name;
            context = instructions;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.size();
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


    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.instuct_call_custom_view, null);
        holder.tv=(TextView) rowView.findViewById(R.id.textView5);

        holder.tv.setText(result.get(position).toString());

        rowView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String str=result.get(position) .toString()     ;
                str= str.replace(" ","");
               String call=str.substring(str.length()-10);
                Intent in=new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + call));
                try{
                    context.startActivity(in);
                }
                catch (android.content.ActivityNotFoundException ex){
                    Toast.makeText(context.getApplicationContext(), "Phone Application is not found", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return rowView;
    }

}