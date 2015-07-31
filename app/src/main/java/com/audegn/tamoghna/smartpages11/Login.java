package com.audegn.tamoghna.smartpages11;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.transition.Explode;
import android.view.Window;
import android.widget.Toast;

import com.audegn.tamoghna.fragments.LoginFragment;
import com.audegn.tamoghna.fragments.MobileNoFragment;
import com.audegn.tamoghna.fragments.PasswordFragment;
import com.audegn.tamoghna.fragments.UserNameFragment;
import com.audegn.tamoghna.helperclasses.JSONpost;
import com.matesnetwork.Cognalys.VerifyMobile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Login extends Activity implements MobileNoFragment.Oncontinue, LoginFragment.OnLogin,
        LoginFragment.OnSignup, PasswordFragment.OnPasswordSet, UserNameFragment.Onusername, LoginFragment.OnforgotPassword{
    FragmentManager manage;
    FragmentTransaction ft;
    String mobno, password, username, email;
    Toast a,b,c;
    JSONArray token;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {
            Explode slider = new Explode();
            getWindow().setEnterTransition(slider);
        }
        setContentView(R.layout.logincontainer);
        a = Toast.makeText(this,"User Successfully Logged in", Toast.LENGTH_SHORT);
        b = Toast.makeText(this,"Login Failed", Toast.LENGTH_LONG);
        c = Toast.makeText(this,"Enter All Fields Properly", Toast.LENGTH_LONG);
        manage = getFragmentManager();
        ft = manage.beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.replace(R.id.logincontain, new LoginFragment(), "LOGIN");
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void continueevent(String string) {
        this.mobno = string;
        String mobile = VerifyMobile.getCountryCode(getApplicationContext()) + string;
        Intent in = new Intent(Login.this, VerifyMobile.class);
        in.putExtra("app_id", getResources().getString(R.string.cognalysapp_id));
        in.putExtra("access_token", getResources().getString(R.string.cognalysaccess_token));
        in.putExtra("mobile", mobile);
        startActivityForResult(in, VerifyMobile.REQUEST_CODE);
    }

    @Override
    public void signupevent() {
        ft = manage.beginTransaction();
        ft.replace(R.id.logincontain, new UserNameFragment(), "SIGNUP");
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void forgotevent() {
        ft = manage.beginTransaction();
        ft.replace(R.id.logincontain, new MobileNoFragment(), "SIGNUP");
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void loginevent(String user, String password) {
        this.mobno = user;
        this.password = password;
        new logintask(Login.this).execute();
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setExitTransition(new Explode());
            Intent lola = new Intent(Login.this, MainActivity.class);
            startActivity(lola, ActivityOptions.makeSceneTransitionAnimation(Login.this).toBundle());
        }else{
            Intent lola = new Intent(Login.this, MainActivity.class);
            startActivity(lola);
        }
    }

    @Override
    public void userevent(String username, String email) {
        this.username = username;
        this.email = email;
        ft = manage.beginTransaction();
        ft.replace(R.id.logincontain, new MobileNoFragment(), "SIGNUP");
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void passwordsetevent(String password) {
        this.password = password;
        String[] haha = {this.mobno, this.password,this.username,this.email};
        new Signuptask(Login.this).execute(haha);

    }
    public class Signuptask extends AsyncTask<String,Void,Void>{

        private ProgressDialog dialog;
        private Activity activity;
        private Context context;
        public Signuptask(Activity activity) {
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
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(Login.this, "User Signed Up", Toast.LENGTH_SHORT);
                }
            });
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setExitTransition(new Explode());
                Intent lola = new Intent(Login.this, MainActivity.class);
                startActivity(lola, ActivityOptions.makeSceneTransitionAnimation(Login.this).toBundle());
            }else{
                Intent lola = new Intent(Login.this, MainActivity.class);
                startActivity(lola);
            }
        }
        @Override
        protected Void doInBackground(String... params) {
            if(params != null) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("username", params.clone()[2]);
                    jsonObject.put("password", params.clone()[1]);
                    jsonObject.put("contact_no", params.clone()[0]);
                    jsonObject.put("email", params.clone()[3]);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String testurl = "https://enigmatic-forest-7588.herokuapp.com/signup";
                String url = "https://192.168.0.108:3030/signup";
                JSONpost post = new JSONpost();
                token = post.postJSON(jsonObject, testurl, null);
                if (token != null) {
                    a.show();
                    getSharedPreferences("session", MODE_PRIVATE).edit().clear()
                            .putString("session_id", token.optJSONObject(0).optString("session_id"))
                            .putString("username", token.optJSONObject(0).optString("username")).commit();

                } else {
                    b.show();
                }
            } else {
                c.show();
            }
            return null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == VerifyMobile.REQUEST_CODE) {
            String message = data.getStringExtra("message");
            int result = data.getIntExtra("result", 0);
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT)
                    .show();
            switch (result){
                case 101:
                    Toast.makeText(getApplicationContext(),  "MISSING CREDENTIALS", Toast.LENGTH_SHORT).show();
                    break;
                case 102:
                    Toast.makeText(getApplicationContext(), "MISSING REQUIRED VALUES", Toast.LENGTH_SHORT).show();
                    break;
                case 103:
                    Toast.makeText(getApplicationContext(), "MISSING PROPER NUMBER", Toast.LENGTH_SHORT).show();
                    break;
                case 104:
                    Toast.makeText(getApplicationContext(), "VERIFICATION SUCCESS", Toast.LENGTH_SHORT).show();
                    break;
                case 105:
                    Toast.makeText(getApplicationContext(), "NUMBER IS NOT CORRECT", Toast.LENGTH_SHORT).show();
                    break;
                case 106:
                    Toast.makeText(getApplicationContext(), "MOBILE NUMBER VERIFICATION CANCELED", Toast.LENGTH_SHORT).show();
                    break;
                case 107:
                    Toast.makeText(getApplicationContext(), "NETWORK ERROR CANNOT BE VERIFIED", Toast.LENGTH_SHORT).show();
                    break;
                case 108:
                    Toast.makeText(getApplicationContext(), "MOBILE NUMBER VERIFICATION FAILED, NO INTERNET", Toast.LENGTH_SHORT).show();
                    break;
            }
            if(result == 104){
                ft = manage.beginTransaction();
                ft.replace(R.id.logincontain, new PasswordFragment(), "PASSWORD");
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack(null);
                ft.commit();
            }
        }
    }
    private class logintask extends AsyncTask<Void,Void,Void> {
        private ProgressDialog dialog;
        private Activity cont;

        public logintask(Activity context) {
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
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(Login.this, "User Logged in", Toast.LENGTH_SHORT);
                }
            });
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setExitTransition(new Explode());
                Intent lola = new Intent(Login.this, MainActivity.class);
                startActivity(lola, ActivityOptions.makeSceneTransitionAnimation(Login.this).toBundle());
            }else{
                Intent lola = new Intent(Login.this, MainActivity.class);
                startActivity(lola);
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            if (mobno != null
                    && password != null) {
                JSONObject obj = new JSONObject();
                try {
                    obj.put("mobileno", mobno);
                    obj.put("password", password);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                JSONpost postman = new JSONpost();
                String testis = "https://enigmatic-forest-7588.herokuapp.com/login";
                String url = "https://192.168.0.108:3030/login";
                token = postman.postJSON(obj, testis,null);
                if (token != null) {
                    a.show();
                    getSharedPreferences("session", MODE_PRIVATE).edit().clear()
                            .putString("session_id", token.optJSONObject(0).optString("session_id"))
                            .putString("username", token.optJSONObject(0).optString("username")).commit();
                } else {
                    b.show();
                }
            } else {
                c.show();
            }
            return null;
        }
    }

    @Override
    protected void onDestroy() {
        SharedPreferences prefs =  getSharedPreferences("session", MODE_PRIVATE);
        if(!prefs.contains("session_id")){
            Toast.makeText(this,"Please login first!",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Login.this, Login.class);
            startActivity(intent);
        }else{
            super.onDestroy();
        }
    }
}
