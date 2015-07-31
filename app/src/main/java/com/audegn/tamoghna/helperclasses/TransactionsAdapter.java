package com.audegn.tamoghna.helperclasses;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.audegn.tamoghna.smartpages11.R;

import java.util.ArrayList;

public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.ViewHolder> {
    private ArrayList<Transactions> mTrans;
    public Context mycontext;

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView Name;
        public TextView Code;
        public TextView Amount;
        public TextView Date;
        public TextView Pending;

        public ViewHolder(View v) {
            super(v);
            Name = (TextView) v.findViewById(R.id.recyclertransactionname);
            Code = (TextView) v.findViewById(R.id.recyclertransactioncode);
            Amount = (TextView) v.findViewById(R.id.recyclertransactionamount);
            Date = (TextView) v.findViewById(R.id.recyclertransactiondate);
            Pending = (TextView) v.findViewById(R.id.recyclertransactionpending);
        }

    }


    public void add(int position, Transactions transactions) {
        mTrans.add(transactions);
        notifyItemInserted(position);
    }

    public void remove(Transactions transactions) {
        int position = mTrans.indexOf(transactions);
        mTrans.remove(transactions);
        notifyItemRemoved(position);
    }

    public void remove(int position) {
        mTrans.remove(position);
        notifyItemRemoved(position);
    }

    public void updateitem(int position,short pending, int amount) {
        mTrans.get(position).setPending(pending).setAmount(amount);
        notifyItemChanged(position);
    }

    public void updateitem(int position,short pending) {
        mTrans.get(position).setPending(pending);
        notifyItemChanged(position);
    }

    public void updateitem(int position, int amount) {
        mTrans.get(position).setAmount(amount);
        notifyItemChanged(position);
    }

    public TransactionsAdapter(ArrayList<Transactions> transactionses, Context context) {
        mTrans = transactionses;
        mycontext = context;
    }

    @Override
    public TransactionsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.transactionsitem, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.Name.setText(mTrans.get(position).getName());
        holder.Code.setText(mTrans.get(position).getCode());
        holder.Amount.setText("\u20B9" + " " + String.valueOf(mTrans.get(position).getAmount()));
        holder.Date.setText(mTrans.get(position).getDate());
        switch (mTrans.get(position).getPending()){
            case 0:
                holder.Pending.setText("Done");
                holder.Pending.setBackground(mycontext.getResources().getDrawable(R.drawable.pendinggreen));
                break;
            case 1:
                holder.Pending.setText("Pending");
                holder.Pending.setBackground(mycontext.getResources().getDrawable(R.drawable.pendingorange));
                break;
            case 2:
                holder.Pending.setText("Pending");
                holder.Pending.setBackground(mycontext.getResources().getDrawable(R.drawable.pendingred));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mTrans.size();
    }


}
