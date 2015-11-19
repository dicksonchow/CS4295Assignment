package com.assignment.cs4295.cs4295assignment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class EventListAdapter extends BaseAdapter {
    private  ArrayList<Event> data;
    private LayoutInflater layoutInflater;
    private Context context;

    static class ViewHolder{
        TextView title;
        TextView desc;
        TextView date;
        Button location;
        Button video;
    }
    public EventListAdapter(Context context, ArrayList<Event> data){
        this.data=data;
        this.context=context;
        this.layoutInflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null){
            convertView=layoutInflater.inflate(R.layout.event,null);
            holder=new ViewHolder();
            holder.title=(TextView)convertView.findViewById(R.id.tv_title);
            holder.desc=(TextView)convertView.findViewById(R.id.tv_desc);
            holder.date=(TextView)convertView.findViewById(R.id.tv_date);

            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }
        holder.title.setText(data.get(position).getTitle());
        holder.desc.setText(data.get(position).getDesc());
        holder.date.setText(data.get(position).getDate());
        return convertView;
    }
}