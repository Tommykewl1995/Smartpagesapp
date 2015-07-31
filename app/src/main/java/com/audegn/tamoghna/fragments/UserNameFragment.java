package com.audegn.tamoghna.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.audegn.tamoghna.smartpages11.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserNameFragment extends Fragment {

    EditText username,email;
    Button continent;
    Onusername muser;

    public interface Onusername{
        public void userevent(String username, String email);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            muser = (Onusername)activity;
        }catch (ClassCastException e){
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.username,container,false);
        username = (EditText) view.findViewById(R.id.editText);
        email = (EditText) view.findViewById(R.id.editText3);
        continent = (Button) view.findViewById(R.id.tvloginenter);
        continent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String regix = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
                Pattern pat = Pattern.compile(regix);
                Matcher matcher;
                matcher = pat.matcher(email.getText().toString());
                if(matcher.matches()){
                    muser.userevent(username.getText().toString(),email.getText().toString());
                }else{
                    Toast.makeText(getActivity(),"Email not correct", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
}
