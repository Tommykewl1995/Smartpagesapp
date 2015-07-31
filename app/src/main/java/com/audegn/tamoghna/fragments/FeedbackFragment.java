package com.audegn.tamoghna.fragments;

import android.app.Activity;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.audegn.tamoghna.smartpages11.R;

public class FeedbackFragment extends Fragment {

    public interface Onfeed{
        public void feedevent(String message);
    }

    EditText feedback;
    ImageButton send;
    Onfeed mfeed;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            mfeed = (Onfeed)activity;
        }catch (ClassCastException e){
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.feedback,container,false);
        feedback = (EditText) view.findViewById(R.id.feedbackedittext);
        ((EditText) view.findViewById(R.id.feedbackedittext)).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    ((EditText) view.findViewById(R.id.feedbackedittext)).setBackground(getResources().getDrawable(R.drawable.infotext));
                } else {
                    if (((EditText) view.findViewById(R.id.feedbackedittext)).getText().toString().contentEquals("")) {
                        ((EditText) view.findViewById(R.id.feedbackedittext)).setBackground(getResources().getDrawable(R.drawable.infotextgrey));
                    } else {
                        ((EditText) view.findViewById(R.id.feedbackedittext)).setBackground(getResources().getDrawable(R.drawable.infotextblack));
                    }
                }
            }
        });
        send = (ImageButton) view.findViewById(R.id.feedbacksendbutton);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send.setBackground(getActivity().getResources().getDrawable(R.drawable.infotext));
                mfeed.feedevent(feedback.getText().toString());
                Fragment fragment = new MainFragment();
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {
                    fragment.setSharedElementEnterTransition(TransitionInflater.from(getActivity()).inflateTransition(R.transition.oppotransition));
                    fragment.setEnterTransition(TransitionInflater.from(getActivity()).inflateTransition(R.transition.oppotransition));
                    fragment.setExitTransition(TransitionInflater.from(getActivity()).inflateTransition(R.transition.oppotransition));
                    fragment.setSharedElementReturnTransition(TransitionInflater.from(getActivity()).inflateTransition(R.transition.oppotransition));
                }
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,fragment)
                        .remove(new MainFragment()).commit();
            }
        });
        return view;
    }
}
