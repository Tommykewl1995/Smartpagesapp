package com.audegn.tamoghna.fragments;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.transition.Explode;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.audegn.tamoghna.helperclasses.JSONpost;
import com.audegn.tamoghna.smartpages11.MainActivity;
import com.audegn.tamoghna.smartpages11.R;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;

public class SettingsFragment extends Fragment implements View.OnClickListener{

    public interface Onlogout{
        public void logoutevent();
    }

    ImageView logout;
    TextView sendfeed;
    ImageView displayph, popupnoti;
    Onlogout mlogout;
    boolean d=false,p=false;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            mlogout = (Onlogout)activity;
        }catch (ClassCastException e){
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings,container,false);
        initialize(view);
        return view;
    }
    public void initialize(View view){
        displayph = (ImageView) view.findViewById(R.id.displaycontactnumber);
        popupnoti = (ImageView) view.findViewById(R.id.popupnotifications);
        sendfeed = (TextView) view.findViewById(R.id.sendfeedback);
        logout = (ImageView) view.findViewById(R.id.logout);
        logout.setOnClickListener(this);
        displayph.setOnClickListener(this);
        popupnoti.setOnClickListener(this);
        sendfeed.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.displaycontactnumber:
                if(!d) {
                    displayph.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.checkboxvector));
                    d = true;
                }else {
                    displayph.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.checkboxemptyvector));
                    d=false;
                }
                break;
            case R.id.popupnotifications:
                if(!p) {
                    popupnoti.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.checkboxvector));
                    p = true;
                }else{
                    popupnoti.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.checkboxemptyvector));
                    p = false;
                }
                break;
            case R.id.sendfeedback:
                Fragment fragment = new FeedbackFragment();
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {
                    fragment.setSharedElementEnterTransition(TransitionInflater.from(getActivity()).inflateTransition(R.transition.oppotransition));
                    fragment.setEnterTransition(TransitionInflater.from(getActivity()).inflateTransition(R.transition.oppotransition));
                    fragment.setExitTransition(TransitionInflater.from(getActivity()).inflateTransition(R.transition.oppotransition));
                    fragment.setSharedElementReturnTransition(TransitionInflater.from(getActivity()).inflateTransition(R.transition.oppotransition));
                }
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,fragment)
                        .remove(new MainFragment()).commit();
                break;
            case R.id.logout:
                mlogout.logoutevent();
                break;
        }
    }
}
