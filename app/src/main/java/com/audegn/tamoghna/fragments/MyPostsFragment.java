package com.audegn.tamoghna.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.audegn.tamoghna.helperclasses.Opportunity;
import com.audegn.tamoghna.helperclasses.OpportunityAdapter;
import com.audegn.tamoghna.helperclasses.datetime;
import com.audegn.tamoghna.smartpages11.MainActivity;
import com.audegn.tamoghna.smartpages11.R;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Date;

public class MyPostsFragment extends Fragment {
    public static MainActivity activity;
    RecyclerView recycle;
    RecyclerView.Adapter adaptor;
    RecyclerView.LayoutManager manager;
    FrameLayout oppo;
    ArrayList<Opportunity> opi = new ArrayList<>();
    public static JSONArray array = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setarray();
        View view = inflater.inflate(R.layout.myposts, container,false);
        recycle = (RecyclerView) view.findViewById(R.id.recycler);
        manager = new LinearLayoutManager(getActivity());
        adaptor = new OpportunityAdapter(opi,getActivity().getSupportFragmentManager(), activity, "MY");
        recycle.setLayoutManager(manager);
        recycle.setAdapter(adaptor);
        return view;
    }
    public void setarray(){
        if(array != null) {
            for (int i = 0; i < array.length(); i++) {
                Opportunity oppos = new Opportunity();
                Date date = new Date(Long.decode(array.optJSONObject(i).optString("created_on")));
                oppos.setName(array.optJSONObject(i).optString("title"));
                oppos.setOpportunity(array.optJSONObject(i).optString("mainbody"));
                oppos.setPrice(array.optJSONObject(i).optString("price"));
                oppos.setTag(array.optJSONObject(i).optString("type"));
                oppos.setCreatedat(new datetime().getlol(date));
                oppos.setExpiresat(array.optJSONObject(i).optString("expiry_date"));
                oppos.setMobileno(array.optJSONObject(i).optString("mobile_no"));
                oppos.setOpportunity_id(array.optJSONObject(i).optString("_id"));
                opi.add(oppos);
            }
        }
    }
}
