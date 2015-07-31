package com.audegn.tamoghna.smartpages11;

import android.app.Activity;
import android.app.ActivityOptions;
import android.database.Cursor;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.transition.Slide;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.audegn.tamoghna.fragments.DetailsFragment;
import com.audegn.tamoghna.fragments.DevelopersFragment;
import com.audegn.tamoghna.fragments.FeedbackFragment;
import com.audegn.tamoghna.fragments.HelpFragment;
import com.audegn.tamoghna.fragments.MainFragment;
import com.audegn.tamoghna.fragments.NotificationsFragment;
import com.audegn.tamoghna.fragments.PostFragment;
import com.audegn.tamoghna.fragments.ProfileFragment;
import com.audegn.tamoghna.fragments.ReportFragment;
import com.audegn.tamoghna.fragments.SettingsFragment;
import com.audegn.tamoghna.fragments.TermsFragment;
import com.audegn.tamoghna.fragments.TransactionsFragment;
import com.audegn.tamoghna.fragments.fulloppofragment;
import com.audegn.tamoghna.helperclasses.JSONParser;
import com.audegn.tamoghna.helperclasses.JSONpost;
import com.audegn.tamoghna.helperclasses.NavDrawerItem;
import com.audegn.tamoghna.helperclasses.NavDrawerListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MainActivity extends AppCompatActivity implements PostFragment.Onoppopost,
        MainFragment.OngetReq, fulloppofragment.Onamount, TransactionsFragment.Ontransact,
        NotificationsFragment.Onnotify,ReportFragment.Onfeedback,FeedbackFragment.Onfeed,ProfileFragment.Onprofile,
        PostFragment.Ongall, PostFragment.Onpic, MainFragment.Onreload,NotificationsFragment.Onreloadn,
        TransactionsFragment.Onreloadt, ProfileFragment.Onreloadp, ProfileFragment.Pic, ProfileFragment.Profileupload,
        fulloppofragment.Onpause, SettingsFragment.Onlogout{
    public static Toast toast;
    public static Toast tost;
    public static String token = "";
    public static Menu menu;
    TextView titletext;
    Fragment fragment;
    ActionBar action;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    public static boolean tick = false;
    private String imgDecodableString;
    private CharSequence mDrawerTitle;

    private CharSequence mTitle;

    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;
    String session;
    Toolbar tool;
    public static JSONArray object, notifications,transactionses,profiles;
    public static JSONArray returnd;
    public static File dyanamicfile;
    public static boolean cross = false;
    public static boolean wich = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        super.onCreate(savedInstanceState);
        mTitle = mDrawerTitle = getTitle();
        SharedPreferences prefs =  getSharedPreferences("session", MODE_PRIVATE);
        if(!prefs.contains("session_id")){
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
        }else{
            session = prefs.getString("session_id", "");
        }
        Context context = getApplicationContext();
        CharSequence text = "Opportunity posted Successfully";
        int duration = Toast.LENGTH_SHORT;
        CharSequence texta = "Couldn't post Opportunity. Try Again";
        toast = Toast.makeText(context, text, duration);
        tost = Toast.makeText(context, texta, duration);
        token = getSharedPreferences("session", MODE_PRIVATE).getString("session_id", "");
        setContentView(R.layout.activity_main);
        object = new JSONArray();
        notifications = new JSONArray();
        transactionses = new JSONArray();
        profiles = new JSONArray();
        if(session != null) {
            new Get(MainActivity.this).execute("OPPORTUNITIES");
        }
        initialize();
        tool = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        setSupportActionBar(tool);
        action = getSupportActionBar();
        action.setDefaultDisplayHomeAsUpEnabled(false);
        action.setDisplayHomeAsUpEnabled(false);
        action.setDisplayShowCustomEnabled(true);
        action.setIcon(R.drawable.logovectorwhite);
        action.setTitle("HOME");
        action.setDisplayShowTitleEnabled(false);
        action.setCustomView(R.layout.toolbar);
        action.setDisplayShowHomeEnabled(false);
        action.setHomeButtonEnabled(false);
        action.setDisplayUseLogoEnabled(false);
        titletext = (TextView) action.getCustomView().findViewById(R.id.titletext);
        mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,tool,R.string.app_name,R.string.app_name){
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());
    }
    public void initialize(){
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
        navDrawerItems = new ArrayList<>();
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);
        additems();
    }
    public void additems(){
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1), true, "22"));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[6], navMenuIcons.getResourceId(6, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[7], navMenuIcons.getResourceId(7, -1)));
        navMenuIcons.recycle();
        adapter = new NavDrawerListAdapter(getApplicationContext(),
                navDrawerItems);
        mDrawerList.setAdapter(adapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.homeicon:
                if(tick){
                    tick = false;
                    String[] objection = new DetailsFragment().getargs();
                    HashMap<String,String> newhash = new HashMap<>();
                    if(new ProfileFragment().coveruri != null) {
                        newhash.put("cover_pic", new ProfileFragment().coveruri);
                    }
                    if(new ProfileFragment().profileuri != null) {
                        newhash.put("profile_pic", new ProfileFragment().profileuri);
                    }
                    new PostProgress(MainActivity.this,newhash,"PROFILE").execute(objection);
                    item.setIcon(getResources().getDrawable(R.drawable.logovectorwhite));
                }else if(wich){
                    item.setIcon(getResources().getDrawable(R.drawable.logovectorwhite));
                    titletext.setText("PROFILE");
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new ProfileFragment()).commit();
                }else {
                    if (cross) {
                        cross = false;
                        item.setIcon(getResources().getDrawable(R.drawable.logovectorwhite));
                    }
                    if (object != null) {
                        titletext.setText("HOME");
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new MainFragment()).commit();
                    } else {
                        new Get(MainActivity.this).execute("OPPORTUNITIES");
                        titletext.setText("HOME");
                    }
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        return super.onPrepareOptionsMenu(menu);
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
    private class Get extends AsyncTask<String,Void,Void>{
        private ProgressDialog dialog;
        private Activity activity;
        private Context context;
        public Get(Activity activity) {
            this.activity = activity;
            context = activity;
            dialog = new ProgressDialog(context);
        }

        protected void onPreExecute() {
            this.dialog.setMessage("Progress start");
            this.dialog.show();
        }

        @Override
        protected void onPostExecute(Void s) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
        @Override
        protected Void doInBackground(String... params) {
            List<String> lista = Arrays.asList(params);
            if(lista.contains("OPPORTUNITIES")) {
                String test = "https://enigmatic-forest-7588.herokuapp.com/";
//                String ur = "https://192.168.0.108:3030/";
                HashMap<String, String> session_id = new HashMap<>();
                session_id.put("session_id", session);
                object = new JSONParser().getJSONFromUrl(test, session_id,MainActivity.this);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        action.setTitle("HOME");
                        fragment = new MainFragment();
                        new MainFragment().activity = MainActivity.this;
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, fragment).commit();
                    }
                });
            }
            if(lista.contains("TRANSACTIONS")){
                String ur = "https://enigmatic-forest-7588.herokuapp.com/transaction";
                String test = "https://192.168.0.108:3030/transaction";
                HashMap<String, String> session_id = new HashMap<>();
                session_id.put("session_id", session);
                transactionses = new JSONParser().getJSONFromUrl(ur, session_id, MainActivity.this);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        fragment = new TransactionsFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, fragment).commit();
                    }
                });
            }
            if(lista.contains("NOTIFICATIONS")){
                String ur = "https://enigmatic-forest-7588.herokuapp.com/notification";
                String test = "https://192.168.0.108:3030/notification";
                HashMap<String, String> session_id = new HashMap<>();
                session_id.put("session_id", session);
                notifications = new JSONParser().getJSONFromUrl(ur, session_id, MainActivity.this);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        fragment = new NotificationsFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, fragment).commit();
                    }
                });
            }
            if(lista.contains("PROFILE")){
                String ur = "https://enigmatic-forest-7588.herokuapp.com/profile";
                String test = "https://192.168.0.108:3030/profile";
                HashMap<String, String> session_id = new HashMap<>();
                session_id.put("session_id", session);
                profiles = new JSONParser().getJSONFromUrl(ur, session_id, MainActivity.this);
                SharedPreferences prefs =  getSharedPreferences("user", MODE_PRIVATE);
                if(!prefs.contains("profile_pic") && profiles.optJSONObject(0).has("profilepic_url") && profiles.optJSONObject(0).has("coverpic_url")) {
                    prefs.edit().putString("profile_pic", Base64.encodeToString(new JSONParser().getimagebitmapfromurl(profiles.optJSONObject(0).optString("profilepic_url"), null), Base64.DEFAULT))
                            .putString("cover_pic", Base64.encodeToString(new JSONParser().getimagebitmapfromurl(profiles.optJSONObject(0).optString("coverpic_url"), null), Base64.DEFAULT)).commit();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        fragment = new ProfileFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, fragment).commit();
                    }
                });
            }
            return null;
        }
    }
    private class SlideMenuClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            displayView(position);
        }
    }

    private void displayView(int position) {
        Fragment fragmen = null;
        String text = null;
        switch (position) {
            case 0:
                if(profiles == null || profiles.isNull(0)){
                    new Get(MainActivity.this).execute("PROFILE");
                    if(new ProfileFragment().activity == null){
                        new ProfileFragment().activity = MainActivity.this;
                    }
                    titletext.setText("PROFILE");
                }else {
                    fragmen = new ProfileFragment();
                    titletext.setText("PROFILE");
                }
                break;
            case 1:
                if(transactionses == null || transactionses.isNull(0)){
                    new Get(MainActivity.this).execute("TRANSACTIONS");
                    text = "TRANSACTIONS";
                }else {
                    fragmen = new TransactionsFragment();
                    text = "TRANSACTIONS";
                }
                break;
            case 2:
                if(notifications == null || notifications.isNull(0)){
                    new Get(MainActivity.this).execute("NOTIFICATIONS");
                    titletext.setText("NOTIFICATIONS");
                }else {
                    fragmen = new NotificationsFragment();
                    titletext.setText("NOTIFICATIONS");
                }
                break;
            case 3:
                fragmen = new SettingsFragment();
                titletext.setText("SETTINGS");
                break;
            case 4:
                fragmen = new TermsFragment();
                titletext.setText("TERMS");
                break;
            case 5:
                fragmen = new DevelopersFragment();
                titletext.setText("DEVELOPERS");
                break;
            case 6:
                fragmen = new ReportFragment();
                titletext.setText("REPORT A PROBLEM");
                break;
            case 7:
                fragmen = new HelpFragment();
                titletext.setText("HELP");
                break;
            default:
                break;
        }

        if (fragmen != null) {
            action.setTitle(text);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragmen).commit();
        }
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(navMenuTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
    }

    private class PostProgress extends AsyncTask<String, Void, String> {
        private ProgressDialog dialog;
        private Activity activity;
        private Context context;
        ArrayList<String> imagess;
        HashMap<String, String> profilee;
        String types = null;
        public PostProgress(Activity activity, ArrayList<String> images, String type) {
            this.activity = activity;
            context = activity;
            dialog = new ProgressDialog(context);
            this.imagess = images;
            this.types = type;
        }
        public PostProgress(Activity activity, HashMap<String,String> used, String type) {
            this.activity = activity;
            context = activity;
            dialog = new ProgressDialog(context);
            this.profilee = used;
            this.types = type;
        }

        protected void onPreExecute() {
            this.dialog.setMessage("Progress start");
            this.dialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            if(types == "OPPO") {
                new Get(MainActivity.this).execute("OPPORTUNITIES");
            }else if(types == "PROFILE"){
                new Get(MainActivity.this).execute("PROFILE");
            }
        }

        @Override
        protected String doInBackground(String... params) {
            if(types.contentEquals("OPPO")) {
                JSONObject objectile = new JSONObject();
                try {
                    objectile.put("title", params.clone()[0]);
                    objectile.put("mainbody", params.clone()[1]);
                    objectile.put("type", params.clone()[2]);
                    objectile.put("expiry_date", params.clone()[3]);
                    if (params.clone()[4] != null) {
                        objectile.put("price", params.clone()[4]);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JSONpost npost = new JSONpost();
                String tem = "https://192.168.0.108:3030/post";
                String test = "https://enigmatic-forest-7588.herokuapp.com/post";
                HashMap<String, String> sewaiya = new HashMap<>();
                sewaiya.put("session_id", token);
                if (imagess != null) {
                    JSONArray picarray = new JSONArray();
                    for (int i = 0; i < imagess.size(); i++) {
                        String s = postimage(imagess.get(i), sewaiya, npost, "enigmatic-forest-7588.herokuapp.com/imageupload");
                        picarray.put(s);
                    }
                    try {
                        objectile.put("url", picarray);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                JSONArray stringa = npost.postJSON(objectile, test, sewaiya);
                if (params.clone()[4] != null) {
                    npost.clear();
                    HashMap<String, String> hashes = new HashMap<>();
                    hashes.put("X-Api-Key", getResources().getString(R.string.intramojoappid));
                    hashes.put("X-Auth-Token", getResources().getString(R.string.intramojoapptoken));
                    JSONObject object = new JSONObject();
                    try {
                        object.remove("mainbody");
                        object.put("title", stringa.optJSONObject(0).optString("post_id"));
                        object.put("description", params.clone()[1]);
                        object.put("currency", "INR");
                        if (params.clone()[4] != null) {
                            object.put("base_price", params.clone()[4]);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String s = npost.postJSON(object, "https://www.instamojo.com/api/1.1/links/", hashes).toString();
                }

                if (stringa != null) {
                    if (stringa.toString().contentEquals("")) {
                        tost.show();
                    } else {
                        toast.show();
                    }
                }
            }else if(types.contentEquals("PROFILE")){
                JSONObject objectile = new JSONObject();
                try {
                    objectile.put("address", params.clone()[0]);
                    objectile.put("contact_no", params.clone()[1]);
                    objectile.put("email", params.clone()[2]);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JSONpost npost = new JSONpost();
                String tem = "https://192.168.0.108:3030/profile/edit";
                String test = "https://enigmatic-forest-7588.herokuapp.com/profile/edit";
                ArrayList<String> urls = new ArrayList<>();
                urls.add("enigmatic-forest-7588.herokuapp.com/coverpicupload");
                urls.add("enigmatic-forest-7588.herokuapp.com/profilepicupload");
                HashMap<String, String> sewaiya = new HashMap<>();
                sewaiya.put("session_id", token);
                if (profilee != null || !profilee.isEmpty()) {
                    JSONObject picarray = new JSONObject();
                    try {
                        if (profilee.containsKey("cover_pic") && profilee.get("cover_pic") != null) {
                            picarray.put("cover_pic", postimage(profilee.get("cover_pic"), sewaiya, npost, urls.get(0)));
                            objectile.put("coverpic_url", picarray.optString("cover_pic"));
                            getSharedPreferences("user", MODE_PRIVATE).edit().putString("cover_pic", get64(profilee.get("cover_pic"))).commit();
                        }
                        if (profilee.containsKey("profile_pic") && profilee.get("profile_pic") != null) {
                            picarray.put("profile_pic", postimage(profilee.get("profile_pic"), sewaiya, npost, urls.get(1)));
                            objectile.put("profilepic_url", picarray.optString("profile_pic"));
                            getSharedPreferences("profile_pic", MODE_PRIVATE).edit().putString("profile_pic", get64(profilee.get("profile_pic"))).commit();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                JSONArray stringa = npost.postJSON(objectile, test, sewaiya);
            }
            return null;
        }
        public String get64(String path){
            FileInputStream fileInputStream = null;
            File file = new File(path);
            byte[] bFile = new byte[(int) file.length()];
            try {
                fileInputStream = new FileInputStream(file);
                fileInputStream.read(bFile);
                fileInputStream.close();
                for (int i = 0; i < bFile.length; i++) {
                    System.out.print((char) bFile[i]);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return Base64.encodeToString(bFile, Base64.DEFAULT);
        }
    }

    @Override
    public void postevent(String subject, String oppo, String tag, long time,String price, ArrayList<String> images) {
        String [] array = {subject,oppo,tag,String.valueOf(time),price};
        new PostProgress(MainActivity.this, images,"OPPO").execute(array);
    }

    @Override
    public void reloadevent() {
        new Get(MainActivity.this).execute("OPPORTUNITIES");
    }

    @Override
    public void reloadeventn() {
        new Get(MainActivity.this).execute("NOTIFICATIONS");
    }

    @Override
    public void reloadeventt() {
        new Get(MainActivity.this).execute("TRANSACTIONS");
    }

    @Override
    public void reloadeventp() {
        new Get(MainActivity.this).execute("PROFILE");
        if(new ProfileFragment().activity == null) {
            new ProfileFragment().activity = MainActivity.this;
        }
    }

    @Override
    public void profileuploadevent() {
       menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.donevector));
        tick = true;
    }

    @Override
    public void feedevent(String message) {
        sendMail("Smartpage Feedback", message);
    }

    @Override
    public void feedbackevent(String message) {
        sendMail("Smartpage Error Report", message);
    }

    @Override
    public JSONArray notificationevent() {
        return notifications;
    }

    @Override
    public JSONArray profileevent() {
        return profiles;
    }

    @Override
    public JSONArray transactionevent() {
        return transactionses;
    }

    @Override
    public JSONArray reqevent() {
        return object;
    }

    @Override
    public void logoutevent() {
        new Logout(MainActivity.this).execute();
    }

    @Override
    public void pauseevent() {
        menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.logovectorwhite));
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new MainFragment()).commit();
        action.setTitle("HOME");
    }

    private class BuyPost extends AsyncTask<String,Void,JSONArray>{

        private ProgressDialog dialog;
        private Activity activity;
        private Context context;
        public BuyPost(Activity activity) {
            this.activity = activity;
            context = activity;
            dialog = new ProgressDialog(context);
        }

        protected void onPreExecute() {
            this.dialog.setMessage("Progress start");
            this.dialog.show();
        }

        @Override
        protected void onPostExecute(JSONArray s) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }

        @Override
        protected JSONArray doInBackground(String... params) {
            JSONObject ovj = new JSONObject();
            HashMap<String,String> hass = new HashMap<>();
            hass.put("session_id", token);
            try {
                ovj.put("status", "red");
                ovj.put("post_id", params.clone()[0]);
            }catch (JSONException e){
                e.printStackTrace();
            }
            JSONpost postit = new JSONpost();
            returnd = postit.postJSON(ovj, "https://enigmatic-forest-7588.herokuapp.com/notification", hass);
            String name = returnd.optJSONObject(0).optString("username");
            String hue = params.clone()[2];
            String email = returnd.optJSONObject(0).optString("email") + "@gmail.com";
            hue = hue.replaceAll(" ", "-").toLowerCase();
            name = name.replaceAll(" ", "+");
            final StringBuilder buld = new StringBuilder();
            buld.append(params.clone()[1]);
            buld.append("Audegn/").append(hue + "/?data_name=").append(name + "&data_email=")
                    .append(email + "&data_phone=").append(returnd.optJSONObject(0).optString("contact_no") + "&intent=buy");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {
                        getWindow().setEnterTransition(new Explode());
                        getWindow().setExitTransition(new Slide());
                        Intent intel = new Intent(Intent.ACTION_VIEW, Uri.parse(buld.toString()));
                        startActivityForResult(intel, 1, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this)                            .toBundle());
                    }else{
                        Intent intel = new Intent(Intent.ACTION_VIEW, Uri.parse(buld.toString()));
                        startActivityForResult(intel, 1);
                    }
                }
            });
            return returnd;
        }
    }

    @Override
    public JSONArray amountevent(String id, String name) {
        String[] strings = {id, getResources().getString(R.string.instamojouri),name};
        new BuyPost(MainActivity.this).execute(strings);
        return returnd;
    }

    @Override
    public String galleryevent() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, 3);
        return imgDecodableString;
    }

    @Override
    public void picevent(String which) {
        if(which.contentEquals("COVERCAMERA")){
            dispatchTakePictureIntent(1);
        }else if(which.contentEquals("COVERGALLERY")){
            Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, 1);
        }else if(which.contentEquals("PROFILECAMERA")){
            dispatchTakePictureIntent(2);
        }else{
            Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, 2);
        }
    }

    @Override
    public String picevent() {
        dispatchTakePictureIntent(3);
        return imgDecodableString;
    }

    private Session createSessionObject() {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        return Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("audegn@gmail.com", "SHEISABITCH");
            }
        });
    }

    private Message createMessage(String email, String subject, String messageBody, Session session) throws MessagingException, UnsupportedEncodingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("audegn@gmail.com", "Smartpages"));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(email, email));
        message.setSubject(subject);
        message.setText(messageBody);
        return message;
    }

    private class SendMailTask extends AsyncTask<Message, Void, Void> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(MainActivity.this, "Please wait", "Sending mail", true, false);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this,"Report send Successfully", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        protected Void doInBackground(Message... messages) {
            try {
                Transport.send(messages[0]);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private void sendMail(String subject, String messageBody) {
        Session session = createSessionObject();
        String email = "sonesh@audegn.com";
        try {
            Message message = createMessage(email, subject, messageBody, session);
            new SendMailTask().execute(message);
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        imgDecodableString = "file:" + image.getAbsolutePath();
        dyanamicfile = image;
        return image;
    }
    private void dispatchTakePictureIntent(int i) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Toast.makeText(MainActivity.this,"Cannot take picture Please Try Again", Toast.LENGTH_SHORT).show();
            }
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, i);
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode<4 && resultCode == RESULT_OK
                    && null != data) {

                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                if(requestCode == 3) {
                    new PostFragment().addpic(imgDecodableString);
                }
                if(requestCode == 1){
                    new ProfileFragment().setBitmapcover(imgDecodableString, getResources(), requestCode);
                }
                if(requestCode == 2){
                    new ProfileFragment().setBitmapcover(imgDecodableString, getResources(), requestCode);
                }
                cursor.close();
            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
                if(dyanamicfile != null) {
                    dyanamicfile.delete();
                }
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
            if(dyanamicfile != null) {
                dyanamicfile.delete();
            }
        }
    }
    public String postimage(String filepath, HashMap<String,String> sewaiya, JSONpost npost, String url) {
        if (filepath != null){
            int index = filepath.lastIndexOf("/");
            String names = filepath.substring(index + 1);
            int indext = names.lastIndexOf(".");
            String extension = names.substring(indext + 1);
            extension = "image/" + extension;
            String pic = "https://" + url + "?file_name=" + names + "&file_type=" + extension;
            String pictest = "https://" + url + "?file_name=" + names + "&file_type=" + extension;
            JSONParser parser = new JSONParser();
            JSONArray picarray;
            picarray = parser.getJSONFromUrl(pictest, sewaiya, MainActivity.this);
            HashMap<String, String> pichead = new HashMap<>();
            ArrayList<String> picture = new ArrayList<>();
            picture.add(filepath);
            String finalalize = picarray.optJSONObject(0).optString("signed_request");
            finalalize.replaceAll("\\/", "/");
            JSONArray isreceive = npost.postJSONimage(picture, finalalize, pichead, extension);
            return picarray.optJSONObject(0).optString("url");
        }else {
            return null;
        }
    }

    private class Logout extends AsyncTask<Void,Void,Void>{

        private ProgressDialog dialog;
        private Context cont;

        public Logout(Context context) {
            cont = context;
            dialog = new ProgressDialog(context);
        }

        protected void onPreExecute() {
            this.dialog.setMessage("Progress start");
            this.dialog.show();
        }

        @Override
        protected void onPostExecute(Void s) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setEnterTransition(new Explode());
                getWindow().setExitTransition(new Slide());
                Intent intel = new Intent(MainActivity.this,Login.class);
                startActivity(intel, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
            }else{
                Intent intel = new Intent(MainActivity.this,Login.class);
                startActivity(intel);
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            JSONpost post = new JSONpost();
            String url = "https://enigmatic-forest-7588.herokuapp.com/logout";
            HashMap<String ,String> key = new HashMap<>();
            key.put("session_id",session);
            post.postJSON(null,url,key);
            trimCache(cont);
            return null;
        }
    }
    public static void trimCache(Context context) {
        try {
            File dir = context.getCacheDir();
            if (dir != null && dir.isDirectory()) {
                deleteDir(dir);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }
}