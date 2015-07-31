package com.audegn.tamoghna.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.audegn.tamoghna.smartpages11.R;

public class PasswordFragment extends Fragment {
    EditText password, confirm;
    Button enter;
    OnPasswordSet mpasswordset;
    public interface OnPasswordSet{
        public void passwordsetevent(String password);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            mpasswordset = (OnPasswordSet)activity;
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.password,container,false);
        initialize(root);
        return root;
    }
    public void initialize(View view){
        password = (EditText) view.findViewById(R.id.editText);
        confirm = (EditText) view.findViewById(R.id.editText3);
        enter = (Button) view.findViewById(R.id.buttoncontinuelogin);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(password.getText().toString().length()>5) {
                    if (password.getText().toString().contentEquals(confirm.getText().toString())) {
                        mpasswordset.passwordsetevent(password.getText().toString());

                    } else {
                        Toast.makeText(getActivity(), "Passwords Doesn't Match", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getActivity(), "Password Length too Short", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
