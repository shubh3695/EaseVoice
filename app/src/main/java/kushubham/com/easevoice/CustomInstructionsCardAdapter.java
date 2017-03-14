package kushubham.com.easevoice;
/**
 * Created by admin on 6/16/2016.
 */
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
public class CustomInstructionsCardAdapter extends RecyclerView.Adapter<CustomInstructionsCardAdapter.MyViewHolder> {
    private ArrayList<String> dataSet;
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView detail;
        public MyViewHolder(View itemView) {
            super(itemView);
            this.detail = (TextView) itemView.findViewById(R.id.detail);
        }
    }
    public CustomInstructionsCardAdapter(ArrayList<String> data) {
        this.dataSet = data;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cards_instructions, parent, false);
        view.setOnClickListener(Instructions.myOnClickListener);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition)
    {
        TextView personname = holder.detail;
        personname.setText(dataSet.get(listPosition));
    }
    @Override
    public int getItemCount()
    {
        return dataSet.size();
    }
}