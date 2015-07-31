package com.audegn.tamoghna.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.audegn.tamoghna.helperclasses.Profile;
import com.audegn.tamoghna.smartpages11.MainActivity;
import com.audegn.tamoghna.smartpages11.R;

public class DetailsFragment extends Fragment {
    public static MainActivity activity;
    public static Profile profile;
    public static EditText name,address,mobno,email;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.details,container,false);
        initialize(view);
        return view;
    }
    public void initialize(View view){
        name = (EditText) view.findViewById(R.id.profilename);
        address = (EditText) view.findViewById(R.id.profileaddress);
        mobno = (EditText) view.findViewById(R.id.profilephoneno);
        email = (EditText) view.findViewById(R.id.profileemailaddress);
        if(profile != null){
            name.setText(profile.getName());
            address.setText(profile.getAddress());
            mobno.setText(profile.getPhoneno());
            email.setText(profile.getEmail());
        }
    }
    public void setdetails(){
        address.setEnabled(true);
        mobno.setEnabled(true);
        email.setEnabled(true);
    }
    public String[] getargs(){
        String addre = address.getText().toString();
        String mob = mobno.getText().toString();
        String emai = email.getText().toString();
        String[] lol = {addre,mob,emai};
        return lol;
    }
}
