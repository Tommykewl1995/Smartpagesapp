package com.audegn.tamoghna.fragments;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.audegn.tamoghna.helperclasses.Transactions;
import com.audegn.tamoghna.helperclasses.TransactionsAdapter;
import com.audegn.tamoghna.helperclasses.datetime;
import com.audegn.tamoghna.smartpages11.R;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Date;

public class TransactionsFragment extends Fragment{

    public interface Ontransact{
        public JSONArray transactionevent();
    }

    public interface Onreloadt{
        public void reloadeventt();
    }

    RecyclerView recycle;
    RecyclerView.Adapter adaptor;
    RecyclerView.LayoutManager manager;
    ArrayList<Transactions> trans =  new ArrayList<>();
    Ontransact mtrans;
    public static JSONArray array;
    Onreloadt mreload;
    TextView totalorders,totalamounts;
    long totalamount =0;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            mtrans = (Ontransact)activity;
        }catch (ClassCastException e){
            e.printStackTrace();
        }
        try{
            mreload = (Onreloadt)activity;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setarray();
        if(array == null){
            View view = inflater.inflate(R.layout.networkdown,container,false);
            ((ImageView) view.findViewById(R.id.imageView)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mreload.reloadeventt();
                }
            });
            return view;
        }
        View view = inflater.inflate(R.layout.transactions,container,false);
        totalorders = (TextView) view.findViewById(R.id.totalorders);
        totalamounts = (TextView) view.findViewById(R.id.totalsales);
        totalorders.setText(String.valueOf(array.length()));
        totalamounts.setText(String.valueOf(totalamount));
        recycle = (RecyclerView) view.findViewById(R.id.recycler);
        manager = new LinearLayoutManager(getActivity());
        adaptor = new TransactionsAdapter(trans,getActivity());
        recycle.setLayoutManager(manager);
        recycle.setAdapter(adaptor);
        return view;
    }

    public void setarray(){
        array = mtrans.transactionevent();
        if(array != null){
            for(int i = 0; i<array.length(); i++){
                StringBuilder builder = new StringBuilder();
                builder.append(new datetime().getnotificationtime(new Date(Long.decode(array.optJSONObject(i).optString("created_on")))).get(0) + " ");
                builder.append(new datetime().getnotificationtime(new Date(Long.decode(array.optJSONObject(i).optString("created_on")))).get(1));
                String name = array.optJSONObject(i).optString("buyer_name");
                String date = builder.toString();
                String code = array.optJSONObject(i).optString("payment_id");
                int amount = Integer.decode(array.optJSONObject(i).optString("amount"));
                short pending = 1;
                Transactions transactions = new Transactions(name,code,amount,date,pending);
                trans.add(transactions);
                totalamount = totalamount + (long) amount;
            }
        }
    }
}
