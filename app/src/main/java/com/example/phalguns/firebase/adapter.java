package com.example.phalguns.firebase;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.util.ArrayList;

public class adapter extends BaseAdapter {


    public ArrayList<String> LocationList;
    public ArrayList<String> nameList;
    public ArrayList<String> fromList;
    public ArrayList<String> ToList;


    public Activity context;
    public LayoutInflater inflater;



    public adapter(Activity context, ArrayList<String> LocationList, ArrayList<String> nameList, ArrayList<String> fromList, ArrayList<String> ToList) {
        super();
        this.context = context;
        this.LocationList = LocationList;
        this.nameList = nameList;
        this.fromList = fromList;
        this.ToList = ToList;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return LocationList.size();
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

    public static class ViewHolder
    {


        TextView txtloc;
        TextView name;
        TextView from;
        TextView to;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        ViewHolder holder;
        if(convertView==null)
        {
            holder = new ViewHolder();
            convertView=inflater.inflate(R.layout.list, null);

            holder.txtloc = (TextView) convertView.findViewById(R.id.txtloc);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.from = (TextView) convertView.findViewById(R.id.from);
            holder.to = (TextView) convertView.findViewById(R.id.to);
            holder.txtloc.setText(LocationList.get(position));
            holder.name.setText(nameList.get(position));
            holder.from.setText(fromList.get(position));
            holder.to.setText(ToList.get(position));


            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
            holder.txtloc.setText( LocationList.get(position));
            holder.name.setText(nameList.get(position));
            holder.from.setText(fromList.get(position));
            holder.to.setText(ToList.get(position));

        }




        return convertView;
    }


}



