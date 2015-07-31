package com.audegn.tamoghna.fragments;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.audegn.tamoghna.helperclasses.Opportunity;
import com.audegn.tamoghna.smartpages11.MainActivity;
import com.audegn.tamoghna.smartpages11.R;

import org.json.JSONArray;

public class fulloppofragment extends Fragment implements View.OnClickListener{

    public interface Onamount{
        public JSONArray amountevent(String id, String name);
    }

    public interface Onpause{
        public void pauseevent();
    }

    TextView name,tag,oppo,createdat,expiresin,amount;
    public static Opportunity opportunity =  null;
    Onamount mamount;
    Onpause mpause;
    public static MainActivity activity;
    public static String wich;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            mamount = (Onamount)activity;
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            mpause = (Onpause)activity;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        opportunity = (Opportunity) args.getParcelable("opportunity");
        wich = args.getString("WICH");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.opportunityfullscreen,container,false);
        initialize(v);
        settext();
        return v;
    }
    public void initialize(View v){
        name = (TextView) v.findViewById(R.id.recycleopponame);
        tag = (TextView) v.findViewById(R.id.recycleoppotag);
        oppo = (TextView) v.findViewById(R.id.recycleoppooppo);
        createdat = (TextView) v.findViewById(R.id.recycleoppocreateat);
        expiresin = (TextView) v.findViewById(R.id.recycleoppoexpire);
        amount = (TextView) v.findViewById(R.id.recycleoppoamount);
        amount.setOnClickListener(this);
    }
    public void settext(){
        name.setText(opportunity.getName());
        tag.setText(opportunity.getTag());
        oppo.setText(opportunity.getOpportunity());
        createdat.setText(opportunity.getCreatedat());
        expiresin.setText(opportunity.getExpiresat());
        amount.setText(String.valueOf(opportunity.getPrice()));
        settagback();
        if(wich == "MY"){
            new MainActivity().wich = true;
        }else if(wich == "MAIN"){
            new MainActivity().wich = false;
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.recycleoppoamount:
                mamount.amountevent(opportunity.getOpportunity_id(), opportunity.getName());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mpause.pauseevent();
    }
    public void settagback(){
        switch(opportunity.getTag()){
            case "Social":
                tag.setBackground(activity.getResources().getDrawable(R.drawable.singleroundedsocial));
                break;
            case "Personal":
                tag.setBackground(activity.getResources().getDrawable(R.drawable.singleroundedpersonal));
                break;
            case "Commercial":
                tag.setBackground(activity.getResources().getDrawable(R.drawable.singleroundedcommercial));
                break;
            case "Nostalgia":
                tag.setBackground(activity.getResources().getDrawable(R.drawable.singleroundednostalgia));
                break;
            case "Religious":
                tag.setBackground(activity.getResources().getDrawable(R.drawable.singleroundedreligious));
                break;
            case "Educational":
                tag.setBackground(activity.getResources().getDrawable(R.drawable.singlerounded));
                break;
        }
    }
}
