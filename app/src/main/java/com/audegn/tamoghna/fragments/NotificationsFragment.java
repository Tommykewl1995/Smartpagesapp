package com.audegn.tamoghna.fragments;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.audegn.tamoghna.helperclasses.Notifications;
import com.audegn.tamoghna.helperclasses.NotificationsAdapter;
import com.audegn.tamoghna.helperclasses.datetime;
import com.audegn.tamoghna.smartpages11.R;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Date;

public class NotificationsFragment extends Fragment {

    public interface Onnotify{
        public JSONArray notificationevent();
    }

    public interface Onreloadn{
        public void reloadeventn();
    }

    RecyclerView recycle;
    RecyclerView.Adapter adaptor;
    RecyclerView.LayoutManager manager;
    ArrayList<Notifications> noti =  new ArrayList<>();
    public static JSONArray array;
    Onnotify mnotify;
    Onreloadn mreload;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            mnotify = (Onnotify)activity;
        }catch (ClassCastException e){
            e.printStackTrace();
        }
        try{
            mreload = (Onreloadn)activity;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setarray();
        if(array == null){
            View view = inflater.inflate(R.layout.networkdown,container,false);
            ((ImageView) view.findViewById(R.id.imageView)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mreload.reloadeventn();
                }
            });
            return view;
        }
        View view = inflater.inflate(R.layout.notifications,container,false);
        recycle = (RecyclerView) view.findViewById(R.id.recycler);
        manager = new LinearLayoutManager(getActivity());
        adaptor = new NotificationsAdapter(noti,getActivity());
        recycle.setLayoutManager(manager);
        recycle.setAdapter(adaptor);
        return view;
    }

    public void setarray(){
        String s = "kkk";
        array = mnotify.notificationevent();
        if(array != null) {
            for (int i = 0; i < array.length(); i++) {
                if (!s.contentEquals(new datetime().getnotificationtime(new Date(Long.decode(array.optJSONObject(i).optString("created_on")))).get(0))) {
                    s = new datetime().getnotificationtime(new Date(Long.decode(array.optJSONObject(i).optString("created_on")))).get(0);
                    Notifications notifications = new Notifications(s);
                    noti.add(notifications);
                }
                String name = array.optJSONObject(i).optString("setter");
                SpannableString setter = new SpannableString(name);
                setter.setSpan(new ForegroundColorSpan(Color.BLUE), 0, name.length(), 0);
                String notifi = array.optJSONObject(i).optString("mainbody");
                notifi.replaceFirst(name, new StringBuilder().append(setter).toString());
                Notifications notii = new Notifications(new datetime().getnotificationtime(new Date(Long.decode(array.optJSONObject(i).optString("created_on")))).get(1), notifi);
                noti.add(notii);
            }
        }
    }
}
