package com.audegn.tamoghna.helperclasses;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.audegn.tamoghna.smartpages11.R;

import java.util.ArrayList;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder> {
    private ArrayList<Notifications> mNoti;
    public Context mycontext;

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView Header;
        public TextView time;
        public TextView noti;

        public ViewHolder(View v, int viewtype) {
            super(v);
            switch (viewtype){
                case 0:
                    time = (TextView) v.findViewById(R.id.recyclenotitime);
                    noti = (TextView) v.findViewById(R.id.recyclenotinoti);
                    break;
                case 1:
                    Header = (TextView) v.findViewById(R.id.recyclenotiheader);
                    break;
                default:
                    break;
            }
        }

    }


    public void add(int position, Notifications notifications) {
        mNoti.add(notifications);
        notifyItemInserted(position);
    }

    public void remove(Notifications notifications) {
        int position = mNoti.indexOf(notifications);
        mNoti.remove(notifications);
        notifyItemRemoved(position);
    }

    public void remove(int position) {
        mNoti.remove(position);
        notifyItemRemoved(position);
    }

    public NotificationsAdapter(ArrayList<Notifications> notificationses, Context context) {
        mNoti = notificationses;
        mycontext = context;
    }

    @Override
    public int getItemViewType(int position) {
        if(mNoti.get(position).isheader()) {
            return 1;
        }else{
            return 0;
        }
    }

    @Override
    public NotificationsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        View v;
        ViewHolder vh;
        switch (viewType) {
            case 0:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclernotificationsbody, parent, false);
                vh = new ViewHolder(v, viewType);
                break;
            case 1:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclernotificationstitle, parent, false);
                vh = new ViewHolder(v, viewType);
                break;
            default:
                return null;
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if(mNoti.get(position).isheader()){
           holder.Header.setText(mNoti.get(position).getHeader());
        }else{
            holder.noti.setText(mNoti.get(position).getNotification());
            holder.time.setText(mNoti.get(position).getTime());
        }
    }

    @Override
    public int getItemCount() {
        return mNoti.size();
    }

}
