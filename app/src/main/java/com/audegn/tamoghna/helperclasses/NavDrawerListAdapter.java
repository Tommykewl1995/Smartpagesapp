package com.audegn.tamoghna.helperclasses;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.audegn.tamoghna.smartpages11.R;

import java.util.ArrayList;

public class NavDrawerListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<NavDrawerItem> list = new ArrayList<>();

    public NavDrawerListAdapter(Context context, ArrayList<NavDrawerItem> list){
        this.context = context;
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = ((LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE))
                    .inflate(R.layout.drawer_list_item, null);
        }
        ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.title);
        TextView txtCount = (TextView) convertView.findViewById(R.id.counter);
        imgIcon.setImageResource(list.get(position).getIcon());
        txtTitle.setText(list.get(position).getTitle());

        if(list.get(position).getCounterVisibility()){
            txtCount.setText(list.get(position).getCount());
        }else{
            txtCount.setVisibility(View.GONE);
        }
        return convertView;
    }
}
