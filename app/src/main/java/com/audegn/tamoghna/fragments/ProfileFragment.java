package com.audegn.tamoghna.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTabHost;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.audegn.tamoghna.helperclasses.Profile;
import com.audegn.tamoghna.smartpages11.MainActivity;
import com.audegn.tamoghna.smartpages11.R;

import org.json.JSONArray;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    public interface Onprofile{
        public JSONArray profileevent();
    }
    public interface Onreloadp{
        public void reloadeventp();
    }
    public interface Pic{
        public void picevent(String which);
    }
    public interface Profileupload{
        public void profileuploadevent();
    }

    FragmentTabHost mtabhost;
    public static JSONArray array;
    Onprofile mprofile;
    Onreloadp mreload;
    ImageButton cameracover,gallerycover;
    public static String url;
    public static RelativeLayout coverpic;
    Pic pic;
    Bitmap coverbitmap;
    Bitmap profilebitmap;
    public static CircleImageView profileimage;
    public static MainActivity activity;
    boolean editable = false;
    Profileupload mupload;
    public static String coveruri =null,profileuri = null;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            mprofile = (Onprofile)activity;
        }catch (ClassCastException e){
            e.printStackTrace();
        }
        try{
            mreload = (Onreloadp)activity;
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            pic = (Pic)activity;
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            mupload = (Profileupload)activity;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setupprofile();
        if(array == null){
            View view = inflater.inflate(R.layout.networkdown,container,false);
            ((ImageView) view.findViewById(R.id.imageView)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mreload.reloadeventp();
                }
            });
            return view;
        }
        View view = inflater.inflate(R.layout.profile,container,false);
        cameracover = (ImageButton) view.findViewById(R.id.attachcoverpiccamera);
        gallerycover = (ImageButton) view.findViewById(R.id.attachcoverpicgallery);
        cameracover.setEnabled(false);
        cameracover.setVisibility(View.INVISIBLE);
        gallerycover.setImageDrawable(activity.getResources().getDrawable(R.drawable.editvector));
        coverpic = (RelativeLayout) view.findViewById(R.id.coverpic);
        profileimage = (CircleImageView) view.findViewById(R.id.profile_image);
        new MyPostsFragment().activity = this.activity;
        new DetailsFragment().activity = this.activity;
        ArrayList<String> yoyo = new ArrayList<>();
        if(activity.getSharedPreferences("user", Context.MODE_PRIVATE).contains("cover_pic")
                && activity.getSharedPreferences("profile_pic", Context.MODE_PRIVATE).contains("profile_pic")){
            yoyo.add("COVER");
            yoyo.add("PROFILE");
            String[] both = {"COVER", "PROFILE"};
            new Loadimages(activity, activity.getResources(),yoyo).execute();
        }else if(activity.getSharedPreferences("user", Context.MODE_PRIVATE).contains("cover_pic")) {
            yoyo.add("COVER");
            new Loadimages(activity, activity.getResources(),yoyo).execute();
        }else if(activity.getSharedPreferences("profile_pic", Context.MODE_PRIVATE).contains("profile_pic")){
            yoyo.add("PROFILE");
            new Loadimages(activity, activity.getResources(),yoyo).execute();
        }
        profileimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editable){
                    LayoutInflater linf = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View views = linf.inflate(R.layout.dialogpopup, null);
                final PopupWindow window = new PopupWindow(views, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
                ImageButton cameraa, galleryy;
                cameraa = (ImageButton) window.getContentView().findViewById(R.id.imageButtoncamera);
                galleryy = (ImageButton) window.getContentView().findViewById(R.id.imageButtongallery);
                cameraa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        window.dismiss();
                        pic.picevent("PROFILECAMERA");
                    }
                });
                galleryy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        window.dismiss();
                        pic.picevent("PROFILEGALLERY");
                    }
                });
                window.showAsDropDown(profileimage);
            }
            }
        });
        cameracover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pic.picevent("COVERCAMERA");
            }
        });
        gallerycover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editable){
                    editable = true;
                    gallerycover.setImageDrawable(activity.getResources().getDrawable(R.drawable.attachmentvector));
                    cameracover.setEnabled(true);
                    cameracover.setVisibility(View.VISIBLE);
                    new DetailsFragment().setdetails();
                    mupload.profileuploadevent();
;                }else if(editable) {
                    pic.picevent("COVERGALLERY");
                }
            }
        });
        mtabhost = (FragmentTabHost) view.findViewById(R.id.tabhost);
        mtabhost.setup(getActivity(),getChildFragmentManager(),R.id.tabFrameLayout);
        mtabhost.addTab(mtabhost.newTabSpec("Details").setIndicator(getTabIndicator(mtabhost.getContext(),"Details")), DetailsFragment.class, null);
        mtabhost.addTab(mtabhost.newTabSpec("MyPosts").setIndicator(getTabIndicator(mtabhost.getContext(),"MyPosts")),MyPostsFragment.class,null);
        mtabhost.addTab(mtabhost.newTabSpec("Tags").setIndicator(getTabIndicator(mtabhost.getContext(),"Tags")),TagsFragment.class,null);
        return view;
    }

    private View getTabIndicator(Context context, String title) {
        View view = LayoutInflater.from(context).inflate(R.layout.tab_layout, null);
        TextView tv = (TextView) view.findViewById(R.id.tabtextview);
        tv.setText(title);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mtabhost = null;
    }
    public void setupprofile(){
        array = mprofile.profileevent();
        if(array != null){
            if(new MyPostsFragment().array == null) {
                new MyPostsFragment().array = array.optJSONObject(0).optJSONArray("userposts");
            }
            if(new DetailsFragment().profile == null) {
                Profile profile = new Profile();
                profile.setName(array.optJSONObject(0).optString("username"));
                profile.setAddress(array.optJSONObject(0).optString("address"));
                profile.setEmail(array.optJSONObject(0).optString("email"));
                profile.setWebsite(array.optJSONObject(0).optString("website"));
                profile.setPhoneno("+91-" + array.optJSONObject(0).optString("contact_no"));
                profile.setFax(array.optJSONObject(0).optString("fax"));
                new DetailsFragment().profile = profile;
            }

        }
    }
    public void setBitmapcover(String uri, Resources resources, int req){
        if(req == 1) {
            coveruri = uri;
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(uri, options);
            options.inSampleSize = calculateInSampleSize(options,coverpic.getWidth(),coverpic.getHeight());
            options.inJustDecodeBounds = false;
            coverbitmap = BitmapFactory.decodeFile(uri,options);
            coverpic.setBackground(new BitmapDrawable(resources, coverbitmap));

        }else if (req == 2) {
            profileuri = uri;
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(uri, options);
            options.inSampleSize = calculateInSampleSize(options,profileimage.getWidth(),profileimage.getHeight());
            options.inJustDecodeBounds = false;
            profilebitmap = BitmapFactory.decodeFile(uri, options);
            profileimage.setImageBitmap(profilebitmap);
        }
    }
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
    private class Loadimages extends AsyncTask<Void,Void,Void>{
        private ProgressDialog dialog;
        private Activity activity;
        private Resources resources;
        private ArrayList<String> condition = new ArrayList<>();
        public Loadimages(Activity activity, Resources resources, ArrayList<String> stringArrayList) {
            this.activity = activity;
            this.resources = resources;
            dialog = new ProgressDialog(activity);
            condition = stringArrayList;
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
        protected Void doInBackground(Void... params) {
            if(condition.contains("PROFILE")){
                final BitmapFactory.Options optionsp = new BitmapFactory.Options();
                optionsp.inJustDecodeBounds = true;
                BitmapFactory.decodeByteArray(Base64.decode(activity.getSharedPreferences("profile_pic", Context.MODE_PRIVATE).getString("profile_pic", "").getBytes(), Base64.DEFAULT),0,
                        Base64.decode(activity.getSharedPreferences("profile_pic", Context.MODE_PRIVATE).getString("profile_pic", "").getBytes(), Base64.DEFAULT).length,optionsp);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        optionsp.inSampleSize = calculateInSampleSize(optionsp,profileimage.getWidth(),profileimage.getHeight());
                    }
                });
                optionsp.inJustDecodeBounds = false;
                profilebitmap = BitmapFactory.decodeByteArray(Base64.decode(activity.getSharedPreferences("profile_pic", Context.MODE_PRIVATE).getString("profile_pic", "").getBytes(), Base64.DEFAULT),0,
                        Base64.decode(activity.getSharedPreferences("profile_pic", Context.MODE_PRIVATE).getString("profile_pic", "").getBytes(), Base64.DEFAULT).length,optionsp);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        profileimage.setImageBitmap(profilebitmap);
                    }
                });
            }
            if(condition.contains("COVER")) {
                final BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeByteArray(Base64.decode(activity.getSharedPreferences("user", Context.MODE_PRIVATE).getString("cover_pic", "").getBytes(), Base64.DEFAULT), 0,
                        Base64.decode(activity.getSharedPreferences("user", Context.MODE_PRIVATE).getString("cover_pic", "").getBytes(), Base64.DEFAULT).length, options);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        options.inSampleSize = calculateInSampleSize(options, coverpic.getWidth(), coverpic.getHeight());
                    }
                });
                options.inJustDecodeBounds = false;
                coverbitmap = BitmapFactory.decodeByteArray(Base64.decode(activity.getSharedPreferences("user", Context.MODE_PRIVATE).getString("cover_pic", "").getBytes(), Base64.DEFAULT), 0,
                        Base64.decode(activity.getSharedPreferences("user", Context.MODE_PRIVATE).getString("cover_pic", "").getBytes(), Base64.DEFAULT).length, options);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        coverpic.setBackground(new BitmapDrawable(resources, coverbitmap));
                    }
                });
            }
            return null;
        }
    }

}
