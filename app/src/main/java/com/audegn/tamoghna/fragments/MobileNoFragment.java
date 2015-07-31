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

public class MobileNoFragment extends Fragment {

    public interface Oncontinue{
        public void continueevent(String string);
    }

    EditText mobno;
    Button cont;
    Oncontinue mcontinue;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mcontinue = (Oncontinue) activity;
        }catch (ClassCastException e){
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.signupmobno,container,false);
        mobno = (EditText) view.findViewById(R.id.editText3);
        cont = (Button) view.findViewById(R.id.buttoncontinuelogin);
        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String regx = "[0-9]+";
                if(mobno.getText().toString().matches(regx) && mobno.getText().toString().length()==10) {
                    mcontinue.continueevent(mobno.getText().toString());
                }else{
                    Toast.makeText(getActivity(), "enter a valid 10 digits mobile number", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
}
