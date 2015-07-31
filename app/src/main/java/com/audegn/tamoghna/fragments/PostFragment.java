package com.audegn.tamoghna.fragments;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.audegn.tamoghna.helperclasses.datetime;
import com.audegn.tamoghna.smartpages11.MainActivity;
import com.audegn.tamoghna.smartpages11.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateChangedListener;

import java.util.ArrayList;
import java.util.Calendar;

public class PostFragment extends Fragment implements Runnable, View.OnClickListener{

    public interface Onoppopost{
       public void postevent(String subject,String oppo,String tag,long time,String price, ArrayList<String> images);
    }
    public interface Onpic{
        public String picevent();
    }
    public interface Ongall{
        public String galleryevent();
    }
    public static LinearLayout imagecontainer;
    Resources system;
    Spinner spin;
    EditText postoppurtunity,postsubject,postprice;
    public ImageButton postdate, posttime, attachphotoscamera, attachphotosgallery;
    ArrayAdapter<String> spinneradapt;
    ImageButton button;
    FrameLayout oppo;
    public static MainActivity activity;
    View view;
    Onoppopost mpost;
    CalendarDay mindate;
    Calendar calendar;
    public static CalendarDay dcay;
    Onpic mpic;
    Ongall mgall;
    public static ArrayList<String> images;
    public static ArrayList<String> trim;
    public static long expitytotal;
    boolean timepicked = false;
    boolean datepicked = false;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            mpost = (Onoppopost)activity;
        }catch (ClassCastException e){
            e.printStackTrace();
        }
        try{
            mpic = (Onpic)activity;
        }catch (ClassCastException e){
            e.printStackTrace();
        }
        try{
            mgall = (Ongall)activity;
        }catch (ClassCastException e){
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e("postfrg", "aa gya");
        view = inflater.inflate(R.layout.postanoppurtunity,container,false);
        imagecontainer = (LinearLayout) view.findViewById(R.id.llattachphotos);
        images = new ArrayList<>();
        trim = new ArrayList<>();
        calendar = Calendar.getInstance();
        mindate = new CalendarDay(calendar);
        initialize(view);
        postoppurtunity.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    postoppurtunity.setBackground(getResources().getDrawable(R.drawable.infotext));
                } else {
                    if (postoppurtunity.getText().toString().contentEquals("")) {
                        postoppurtunity.setBackground(getResources().getDrawable(R.drawable.infotextgrey));
                    } else {
                        postoppurtunity.setBackground(getResources().getDrawable(R.drawable.infotextblack));
                    }
                }
            }
        });
        postsubject.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    postsubject.setBackground(getResources().getDrawable(R.drawable.infotext));
                } else {
                    if (postsubject.getText().toString().contentEquals("")) {
                        postsubject.setBackground(getResources().getDrawable(R.drawable.infotextgrey));
                    } else {
                        postsubject.setBackground(getResources().getDrawable(R.drawable.infotextblack));
                    }
                }
            }
        });
        postprice.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    postprice.setBackground(getResources().getDrawable(R.drawable.infotext));
                } else {
                    if (postprice.getText().toString().contentEquals("")) {
                        postprice.setBackground(getResources().getDrawable(R.drawable.infotextgrey));
                    } else {
                        postprice.setBackground(getResources().getDrawable(R.drawable.infotextblack));
                    }
                }
            }
        });
        ArrayList<String> the = new ArrayList<>();
        the.add("Social");
        the.add("Personal");
        the.add("Commercial");
        the.add("Nostalgia");
        the.add("Religious");
        the.add("Educational");
        spinneradapt = new ArrayAdapter<>(getActivity(),R.layout.spinnerlayout, R.id.spinnerlayouttv, the);
        spin.setAdapter(spinneradapt);
        /*View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }*/
        return view;
    }

    public void initialize(View view){
        postsubject = (EditText) view.findViewById(R.id.postsubject);
        spin = (Spinner) view.findViewById(R.id.postspinner);
        postoppurtunity = (EditText) view.findViewById(R.id.postanoppurtunity);
        postprice = (EditText) view.findViewById(R.id.postprice);
        postdate = (ImageButton) view.findViewById(R.id.postdate);
        posttime = (ImageButton) view.findViewById(R.id.posttime);
        attachphotoscamera = (ImageButton) view.findViewById(R.id.attachcamera);
        attachphotosgallery = (ImageButton) view.findViewById(R.id.attachgallery);
        postdate.setOnClickListener(this);
        posttime.setOnClickListener(this);
        attachphotoscamera.setOnClickListener(this);
        attachphotosgallery.setOnClickListener(this);
        button = (ImageButton) view.findViewById(R.id.addopportunitybutton);
        oppo = (FrameLayout) view.findViewById(R.id.framedim);
        oppo.getForeground().setAlpha(0);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.postdate:
                oppo.getForeground().setAlpha(200);
                setupcalender(view);
                break;
            case R.id.posttime:
                oppo.getForeground().setAlpha(200);
                setuptimepicker(view);
                break;
            case R.id.addopportunitybutton:
                if(datepicked && timepicked && postsubject.getText().toString().length() != 0 && postoppurtunity.getText().toString().length() !=0) {
                    mpost.postevent(postsubject.getText().toString(),postoppurtunity.getText().toString(),spin.getSelectedItem().toString(),expitytotal,postprice.getText().toString(), images);
                    new Thread(PostFragment.this).start();
                    new MainActivity().menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.logovectorwhite));
                    new MainActivity().cross = false;
                }else{
                    Toast.makeText(getActivity(),"Ooops!",Toast.LENGTH_SHORT).show();
                    if(postsubject.getText().toString().length() == 0){
                        postsubject.setBackground(activity.getResources().getDrawable(R.drawable.infotextred));
                    }if(postoppurtunity.getText().toString().length() == 0){
                        postoppurtunity.setBackground(activity.getResources().getDrawable(R.drawable.infotextred));
                    }if(!datepicked) {
                        postdate.setBackgroundResource(R.drawable.redcircle);
                        Log.e("dateback", "changed");
                    }if(!timepicked){
                        posttime.setBackgroundResource(R.drawable.redcircle);
                        Log.e("timeback", "changed");
                    }
                }
                break;
            case R.id.attachcamera:
                String cam = mpic.picevent();
                break;
            case R.id.attachgallery:
                String gall = mgall.galleryevent();
                break;
        }
    }


    @Override
    public void run() {
        Fragment fragment = new MainFragment();
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {
            fragment.setSharedElementEnterTransition(TransitionInflater.from(getActivity()).inflateTransition(R.transition.oppotransition));
            fragment.setEnterTransition(TransitionInflater.from(getActivity()).inflateTransition(R.transition.oppotransition));
            fragment.setExitTransition(TransitionInflater.from(getActivity()).inflateTransition(R.transition.oppotransition));
            fragment.setSharedElementReturnTransition(TransitionInflater.from(getActivity()).inflateTransition(R.transition.oppotransition));
        }
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, fragment)
                .addSharedElement(oppo, "framedim").remove(new PostFragment()).commit();
    }

    public void setupcalender(final View view){
        LayoutInflater linf = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View views = linf.inflate(R.layout.calenderpopup,null);
        final PopupWindow popupWindow = new PopupWindow(views, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,true);
        MaterialCalendarView viewd;
        popupWindow.setOutsideTouchable(true);
        final TextView year,dates,ok,cancel;
        year = (TextView) popupWindow.getContentView().findViewById(R.id.popupyear);
        dates = (TextView) popupWindow.getContentView().findViewById(R.id.popupdate);
        ok = (TextView) popupWindow.getContentView().findViewById(R.id.popupok);
        cancel = (TextView) popupWindow.getContentView().findViewById(R.id.popupcancel);
        viewd = (com.prolificinteractive.materialcalendarview.MaterialCalendarView) popupWindow.getContentView().findViewById(R.id.calendarView);
        viewd.setMinimumDate(calendar);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 50);
        year.setText(String.valueOf(mindate.getYear()));
        dates.setText(new datetime().getday(calendar.get(Calendar.DAY_OF_WEEK)) + ", " + new datetime().getmonth(mindate.getMonth()) + " " + String.valueOf(mindate.getDay()));
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dcay != null) {
                    expitytotal = dcay.getDate().getTime();
                    popupWindow.dismiss();
                    setuptimepicker(view);
                    if(postdate.getBackground() == activity.getResources().getDrawable(R.drawable.redcircle)){
                        postdate.setBackground(activity.getResources().getDrawable(R.drawable.ripples));
                    }
                    datepicked = true;
                    return;
                } else {
                    Toast.makeText(getActivity(), "Select a date", Toast.LENGTH_SHORT).show();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dcay = null;
                popupWindow.dismiss();
                oppo.getForeground().setAlpha(0);
            }
        });
        viewd.setOnDateChangedListener(new OnDateChangedListener() {
            @Override
            public void onDateChanged(MaterialCalendarView materialCalendarView, CalendarDay calendarDay) {
                dcay = calendarDay;
                year.setText(String.valueOf(calendarDay.getYear()));
                dates.setText(new datetime().getday(calendarDay.getCalendar().get(Calendar.DAY_OF_WEEK)) + ", " + new datetime().getmonth(calendarDay.getMonth()) + " " + String.valueOf(calendarDay.getDay()));
            }
        });
    }

    public void setuptimepicker(View vs){
        LayoutInflater linf = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View views = linf.inflate(R.layout.timepicker,null);
        final PopupWindow popupWindow = new PopupWindow(views, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,true);
        final TimePicker tm;
        popupWindow.setOutsideTouchable(true);
        TextView setit, canceltm;
        tm = (android.widget.TimePicker) popupWindow.getContentView().findViewById(R.id.timePicker);
        setit = (TextView) popupWindow.getContentView().findViewById(R.id.bsettime);
        canceltm = (TextView) popupWindow.getContentView().findViewById(R.id.cancel);
        popupWindow.showAtLocation(vs, Gravity.CENTER, 0, 0);
        setit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expitytotal = expitytotal + (tm.getCurrentHour()*3600000) + (tm.getCurrentMinute()*60000);
                popupWindow.dismiss();
                oppo.getForeground().setAlpha(0);
                if(posttime.getBackground() == activity.getResources().getDrawable(R.drawable.redcircle)){
                    posttime.setBackground(activity.getResources().getDrawable(R.drawable.ripples));
                }
                timepicked = true;
            }
        });
        canceltm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                oppo.getForeground().setAlpha(0);
            }
        });
    }

    public void addpic(String path){
        final String patha = path;
        images.add(path);
        int index = path.lastIndexOf("/");
        final String names = path.substring(index+1);
        trim.add(names);
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowview = inflater.inflate(R.layout.imageuploaditem,null);
        TextView tv = (TextView) rowview.findViewById(R.id.textViewimageupload);
        ImageButton cancelbutton = (ImageButton) rowview.findViewById(R.id.imgpcancel);
        tv.setText(names);
        cancelbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                images.remove(patha);
                trim.remove(names);
                imagecontainer.removeView(rowview);
            }
        });
        imagecontainer.addView(rowview);
        Log.e("add", "add");
    }
}
