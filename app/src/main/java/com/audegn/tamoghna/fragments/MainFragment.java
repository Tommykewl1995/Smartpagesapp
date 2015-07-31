package com.audegn.tamoghna.fragments;

import android.app.Activity;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.audegn.tamoghna.helperclasses.Opportunity;
import com.audegn.tamoghna.helperclasses.OpportunityAdapter;
import com.audegn.tamoghna.helperclasses.datetime;
import com.audegn.tamoghna.smartpages11.MainActivity;
import com.audegn.tamoghna.smartpages11.R;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Date;

public class MainFragment extends Fragment implements Runnable{
    public static MainActivity activity;
    public interface OngetReq{
        public JSONArray reqevent();
    }

    public interface Onreload{
        public void reloadevent();
    }

    OngetReq mreq;
    RecyclerView recycle;
    RecyclerView.Adapter adaptor;
    RecyclerView.LayoutManager manager;
    ImageButton button;
    FrameLayout oppo;
    ArrayList<Opportunity> opi = new ArrayList<>();
    JSONArray array;
    Onreload mreload;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            mreq = (OngetReq)activity;
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            mreload = (Onreload)activity;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setarray();
        new PostFragment().activity = activity;
        if(array == null){
            View view = inflater.inflate(R.layout.networkdown,container,false);
            ((ImageView) view.findViewById(R.id.imageView)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mreload.reloadevent();
                }
            });
            return view;
        }
        final View view = inflater.inflate(R.layout.mainfragment,container,false);
        button = (ImageButton) view.findViewById(R.id.addopportunitybutton);
        oppo = (FrameLayout) view.findViewById(R.id.framedim);
        recycle = (RecyclerView) view.findViewById(R.id.recycler);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(MainFragment.this).start();
                new MainActivity().menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.cancelvector));
                new MainActivity().cross = true;
            }
        });
        manager = new LinearLayoutManager(getActivity());
        adaptor = new OpportunityAdapter(opi,getActivity().getSupportFragmentManager(), activity, "MAIN");
        recycle.setLayoutManager(manager);
        recycle.setAdapter(adaptor);
        return view;
    }

    @Override
    public void run() {
        Fragment fragment = new PostFragment();
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {
            fragment.setSharedElementEnterTransition(TransitionInflater.from(getActivity()).inflateTransition(R.transition.oppotransition));
            fragment.setEnterTransition(TransitionInflater.from(getActivity()).inflateTransition(R.transition.oppotransition));
            fragment.setExitTransition(TransitionInflater.from(getActivity()).inflateTransition(R.transition.oppotransition));
            fragment.setSharedElementReturnTransition(TransitionInflater.from(getActivity()).inflateTransition(R.transition.oppotransition));
        }
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,fragment)
                .addSharedElement(oppo,"framedim").remove(new MainFragment()).commit();
    }
    public void setarray(){
        array = mreq.reqevent();
        if(array != null) {
            for (int i = 0; i < array.length(); i++) {
                Opportunity oppos = new Opportunity();
                Date date = new Date(Long.decode(array.optJSONObject(i).optString("created_on")));
                Date data = new Date(Long.decode(array.optJSONObject(i).optString("expiry_date")));
                oppos.setName(array.optJSONObject(i).optString("title"));
                oppos.setOpportunity(array.optJSONObject(i).optString("mainbody"));
                oppos.setPrice(array.optJSONObject(i).optString("price"));
                oppos.setTag(array.optJSONObject(i).optString("type"));
                oppos.setCreatedat(new datetime().getlol(date));
                oppos.setExpiresat(new datetime().getlol(data));
                oppos.setMobileno(array.optJSONObject(i).optString("mobile_no"));
                oppos.setOpportunity_id(array.optJSONObject(i).optString("_id"));
                opi.add(oppos);
            }
        }
    }
}
