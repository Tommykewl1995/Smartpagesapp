package com.audegn.tamoghna.helperclasses;

import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class datetime {
    Calendar calendar = Calendar.getInstance();
    Date mindate = calendar.getTime();
    public String getlol(Date date){
        if(date.before(mindate)){
            long diff = mindate.getTime()-date.getTime();
            if(diff<60000){
                return "just now";
            }
            diff = diff/60000;
            if(diff<60){
                return String.valueOf(diff) + " minutes ago";
            }
            diff = diff/60;
            if(diff<24){
                return String.valueOf(diff) + " hours ago";
            }
            diff = diff/24;
            if(diff<30){
                return String.valueOf(diff) + " days ago";
            }
            diff = diff/30;
            if(diff<12){
                return String.valueOf(diff) + " months ago";
            }
            diff = diff/12;
            return String.valueOf(diff) + " years ago";
        }
        if(date.after(mindate)){
            long diff = date.getTime()-mindate.getTime();
            if(diff<60000){
                return "just now";
            }
            diff = diff/60000;
            if(diff<60){
                return "in " + String.valueOf(diff) + " minutes";
            }
            diff = diff/60;
            if(diff<24){
                return "in " + String.valueOf(diff) + " hours";
            }
            diff = diff/24;
            if(diff<30){
                return "in " + String.valueOf(diff) + " days";
            }
            diff = diff/30;
            if(diff<12){
                return "in " + String.valueOf(diff) + " months";
            }
            diff = diff/12;
            return "in " + String.valueOf(diff) + " years";
        }
        return null;
    }
    public ArrayList<String> getnotificationtime(Date date){
        Calendar nowa = Calendar.getInstance();
        nowa.setTimeInMillis(nowa.getTimeInMillis() + (long)TimeZone.getDefault().getRawOffset());
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(date.getTime() + (long) TimeZone.getDefault().getRawOffset());
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH);
        int day = now.get(Calendar.DAY_OF_MONTH);
        int hour = now.get(Calendar.HOUR_OF_DAY);
        int minute = now.get(Calendar.MINUTE);
        ArrayList<String> lola = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        if(day == nowa.get(Calendar.DAY_OF_MONTH)){
            builder.append("Today- ");
        }
        if(day == (nowa.get(Calendar.DAY_OF_MONTH) - 1)){
            builder.append("Yesterday- ");
        }
        builder.append(getday(now.get(Calendar.DAY_OF_WEEK)) + ", " + getmonth(month) + " " + day + ", " + year);
        Log.e("builder", builder.toString());
        lola.add(builder.toString());
        if(hour<12){
            StringBuilder buildtime = new StringBuilder();
            buildtime.append(hour + ":" + minute + " AM");
            lola.add(buildtime.toString());
        }
        if(hour>12){
            StringBuilder buildtime = new StringBuilder();
            buildtime.append((hour-12) + ":" + minute + " PM");
            lola.add(buildtime.toString());
        }
        return lola;
    }
    public String getday(int d){
        switch(d){
            case 1:
                return "Sun";
            case 2:
                return "Mon";
            case 3:
                return "Tue";
            case 4:
                return "Wed";
            case 5:
                return "Thu";
            case 6:
                return "Fri";
            case 7:
                return "Sat";
            default:
                return null;
        }
    }
    public String getmonth(int m){
        switch (m){
            case 0:
                return "Jan";
            case 1:
                return "Feb";
            case 2:
                return "Mar";
            case 3:
                return "Apr";
            case 4:
                return "May";
            case 5:
                return "Jun";
            case 6:
                return "Jul";
            case 7:
                return "Aug";
            case 8:
                return "Sep";
            case 9:
                return "Oct";
            case 10:
                return "Nov";
            case 11:
                return "Dec";
            default:
                return null;
        }
    }

}