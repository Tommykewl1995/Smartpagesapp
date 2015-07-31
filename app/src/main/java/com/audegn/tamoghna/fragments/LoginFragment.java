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
import android.widget.TextView;
import android.widget.Toast;

import com.audegn.tamoghna.smartpages11.R;

public class LoginFragment extends Fragment implements View.OnClickListener{
    TextView tvname,forgot,enter,donthaveaccnt;
    EditText mobno,password;
    OnSignup msignup;
    OnLogin mlogin;
    OnforgotPassword mforgot;
    public interface OnSignup{
        public void signupevent();
    }
    public interface  OnLogin{
        public void loginevent(String user, String password);
    }
    public interface OnforgotPassword{
        public void forgotevent();
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            msignup = (OnSignup)activity;
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            mlogin = (OnLogin)activity;
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            mforgot = (OnforgotPassword)activity;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.login, container, false);
        initialize(root);
        return root;
    }
    private void initialize(View view){
        forgot = (TextView) view.findViewById(R.id.loginforgot);
        enter = (Button) view.findViewById(R.id.tvloginenter);
        donthaveaccnt = (TextView) view.findViewById(R.id.tvdonthaveanaccount);
        mobno = (EditText) view.findViewById(R.id.editText);
        password = (EditText) view.findViewById(R.id.editText3);
        forgot.setOnClickListener(this);
        enter.setOnClickListener(this);
        donthaveaccnt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loginforgot:
                mforgot.forgotevent();
                break;
            case R.id.tvloginenter:
                Toast.makeText(getActivity(), "User Logged In", Toast.LENGTH_SHORT).show();
                mlogin.loginevent(mobno.getText().toString(),password.getText().toString());
                break;
            case R.id.tvdonthaveanaccount:
                msignup.signupevent();
                break;
        }
    }
}
