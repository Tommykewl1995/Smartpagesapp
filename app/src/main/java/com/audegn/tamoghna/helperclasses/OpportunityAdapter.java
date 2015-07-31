package com.audegn.tamoghna.helperclasses;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.audegn.tamoghna.fragments.fulloppofragment;
import com.audegn.tamoghna.smartpages11.MainActivity;
import com.audegn.tamoghna.smartpages11.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class OpportunityAdapter extends RecyclerView.Adapter<OpportunityAdapter.ViewHolder> {
    private ArrayList<Opportunity> mOppo;
    public FragmentManager manager;
    private MainActivity activity;
    public static String wich;

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView name;
        public TextView tag;
        public TextView oppo;
        public TextView createdat;
        public TextView expiresin;
        public TextView amount;
        public TextView card;

        public ViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.recycleopponame);
            tag = (TextView) v.findViewById(R.id.recycleoppotag);
            oppo = (TextView) v.findViewById(R.id.recycleoppooppo);
            createdat = (TextView) v.findViewById(R.id.recycleoppocreateat);
            expiresin = (TextView) v.findViewById(R.id.recycleoppoexpire);
            amount = (TextView) v.findViewById(R.id.recycleoppoamount);
            card = (TextView) v.findViewById(R.id.cards);
        }

    }


    public void add(int position, Opportunity opportunity) {
        mOppo.add(opportunity);
        notifyItemInserted(position);
    }

    public void remove(Opportunity opportunity) {
        int position = mOppo.indexOf(opportunity);
        mOppo.remove(opportunity);
        notifyItemRemoved(position);
    }

    public void remove(int position) {
        mOppo.remove(position);
        notifyItemRemoved(position);
    }

    public OpportunityAdapter(ArrayList<Opportunity> opportunities, FragmentManager manager1, MainActivity activity, String which) {
        mOppo = opportunities;
        manager =manager1;
        this.activity = activity;
        this.wich = which;
    }

    @Override
    public OpportunityAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
        View v;
        ViewHolder vh;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerlayout, parent, false);
        vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        switch(mOppo.get(position).getTag()){
            case "Social":
                holder.tag.setBackground(activity.getResources().getDrawable(R.drawable.singleroundedsocial));
                break;
            case "Personal":
                holder.tag.setBackground(activity.getResources().getDrawable(R.drawable.singleroundedpersonal));
                break;
            case "Commercial":
                holder.tag.setBackground(activity.getResources().getDrawable(R.drawable.singleroundedcommercial));
                break;
            case "Nostalgia":
                holder.tag.setBackground(activity.getResources().getDrawable(R.drawable.singleroundednostalgia));
                break;
            case "Religious":
                holder.tag.setBackground(activity.getResources().getDrawable(R.drawable.singleroundedreligious));
                break;
            case "Educational":
                holder.tag.setBackground(activity.getResources().getDrawable(R.drawable.singlerounded));
                break;
        }
        holder.name.setText(mOppo.get(position).getName());
        holder.tag.setText(mOppo.get(position).getTag());
        holder.oppo.setText(mOppo.get(position).getOpportunity());
        holder.createdat.setText(mOppo.get(position).getCreatedat());
        holder.expiresin.setText(mOppo.get(position).getExpiresat());
        holder.amount.setText(String.valueOf(mOppo.get(position).getPrice()));
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuItem bedMenuItem = activity.menu.findItem(R.id.homeicon);
                bedMenuItem.setIcon(R.drawable.backvectorwhite);
                Bundle newbundle = new Bundle();
                newbundle.putParcelable("opportunity", mOppo.get(position));
                newbundle.putString("WICH", wich);
                Fragment fraggrenide = new fulloppofragment();
                new fulloppofragment().activity = getactivity();
                fraggrenide.setArguments(newbundle);
                manager.beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).replace(R.id.frame_container, fraggrenide).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mOppo.size();
    }

    private MainActivity getactivity(){
        return activity;
    }
}
