package kushubham.com.easevoice;
/**
 * Created by admin on 6/16/2016.
 */
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
public class CustomCardAdapter extends RecyclerView.Adapter<CustomCardAdapter.MyViewHolder> {
    private ArrayList<DeveloperDataModel> dataSet;
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView personname;
        TextView email;
        TextView phone;
        ImageView imageViewIcon;
        public MyViewHolder(View itemView) {
            super(itemView);
            this.personname = (TextView) itemView.findViewById(R.id.textViewName);
            this.email = (TextView) itemView.findViewById(R.id.email);
            this.phone=(TextView) itemView.findViewById(R.id.phone);
            this.imageViewIcon = (ImageView) itemView.findViewById(R.id.imageperson);
        }
    }
    public CustomCardAdapter(ArrayList<DeveloperDataModel> data) {
        this.dataSet = data;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cards_layout, parent, false);
        view.setOnClickListener(AboutDeveloper.myOnClickListener);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition)
    {
        TextView personname = holder.personname;
        TextView email = holder.email;
        TextView phone=holder.phone;
        ImageView imageViewIcon = holder.imageViewIcon;
        personname.setText(dataSet.get(listPosition).getName());
        email.setText(dataSet.get(listPosition).getEmail());
        phone.setText(dataSet.get(listPosition).getPhone());
        imageViewIcon.setImageResource(dataSet.get(listPosition).getImage());
    }
    @Override
    public int getItemCount()
    {
        return dataSet.size();
    }
}
