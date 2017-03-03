package com.sodevan.hotshots.Adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sodevan.hotshots.Models.Item;
import com.sodevan.hotshots.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by ronaksakhuja on 03/03/17.
 */

public class RecyclerView_Home extends RecyclerView.Adapter<RecyclerView_Home.MyViewHolder>{

    private List<Item> itemslist;
    public RecyclerView_Home(List<Item> itemslist){
        Log.d("tag","constructor called");
        this.itemslist=itemslist;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);
        Log.d("tag","created");
        return new MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Item item=itemslist.get(position);
        holder.text.setText(item.getText());
        //Date date = new Date (Long.parseLong(item.getTimestamp()));
        //DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        Calendar calendar = Calendar.getInstance();
        TimeZone tz = TimeZone.getDefault();
        calendar.setTimeInMillis(Long.parseLong(item.getTimestamp()) );
        calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date currenTimeZone =  calendar.getTime();
        String s=sdf.format(currenTimeZone);
        holder.times.setText(s);
        Log.d("tag","rechd");

    }

    @Override
    public int getItemCount() {
        return itemslist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView text,times;
        public MyViewHolder(View itemView) {
            super(itemView);
            text= (TextView) itemView.findViewById(R.id.joke);
            times= (TextView) itemView.findViewById(R.id.time);

        }
    }
}
