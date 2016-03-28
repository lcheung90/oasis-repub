package csci567.project.oasis;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 6661 on 3/25/2016.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {


    String[] code, loc, day;

    public RecyclerAdapter(String[] code, String[] loc, String[] day){
        this.code = code;
        this.loc = loc;
        this.day = day;
    }





    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout,parent,false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.xx_Code.setText(code[position]);
        holder.xx_Loc.setText(loc[position]);
        holder.xx_Day.setText(day[position]);
    }

    @Override
    public int getItemCount() {
        return code.length;
    }






    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView xx_Code, xx_Loc, xx_Day;

        public RecyclerViewHolder(View view){
            super(view);
            xx_Code = (TextView)view.findViewById(R.id.xx_code);
            xx_Loc = (TextView)view.findViewById(R.id.xx_loc);
            xx_Day = (TextView)view.findViewById(R.id.xx_day);
        }
    }









}
