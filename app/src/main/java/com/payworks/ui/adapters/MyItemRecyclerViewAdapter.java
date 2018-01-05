package com.payworks.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.payworks.R;
import com.payworks.generated.model.Usertransaction;
import com.payworks.ui.fragments.MyTransactionsFragment.OnListFragmentInteractionListener;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link } and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<Usertransaction> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyItemRecyclerViewAdapter(ArrayList<Usertransaction> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        String status;
        String mode;

        holder.mDateView.setText(mValues.get(position).getCreatedDate());



        if (mValues.get(position).getTransactionMode() !=null)

        {
            if (mValues.get(position).getTransactionMode().equals("1")) {
                mode = "Deposit";
            } else if (mValues.get(position).getTransactionMode().equals("2")) {
                mode = "Withdraw";
            } else if (mValues.get(position).getTransactionMode().equals("3")) {
                mode = "Money Added to wallet";
            } else if (mValues.get(position).getTransactionMode().equals("4")) {
                mode = "Deposit(Merchant Sale)";
            } else if (mValues.get(position).getTransactionMode().equals("5")) {
                mode = "Withdrawal(Merchant Sale)";
            } else if (mValues.get(position).getTransactionMode().equals("6")) {
                mode = "Withdrawal Request";
            } else if (mValues.get(position).getTransactionMode().equals("7")) {
                mode = "Withdrawal Request Cancel";
            } else {
                mode = "Failed";
            }
            holder.mTransactionIdView.setText(mode);
        }


        if (mValues.get(position).getTransactionStatus() !=null) {
            if (mValues.get(position).getTransactionStatus().equals("1")) {
                status = "Success";
            } else if (mValues.get(position).getTransactionStatus().equals("2")) {
                status = "Pending";
            } else if (mValues.get(position).getTransactionStatus().equals("3")) {
                status = "Pending";
            } else if (mValues.get(position).getTransactionStatus().equals("4")) {
                status = "Cancelled";
            } else {
                status = "Failed";
            }
            holder.mTransactionTypeView.setText(status);
        }
        holder.mTransactionCommentView.setText(mValues.get(position).getFullothername());
        holder.mAmount.setText(mValues.get(position).getTransactionAmount());


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mDateView;
        public final TextView mAmount;
        public final TextView mTransactionIdView;
        public final TextView mTransactionCommentView;
        public final TextView mTransactionTypeView;
        public Usertransaction mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;

            mDateView = (TextView) view.findViewById(R.id.date);
            mAmount = (TextView) view.findViewById(R.id.content);
            mTransactionCommentView = (TextView) view.findViewById(R.id.transaction_comment);
            mTransactionTypeView = (TextView) view.findViewById(R.id.transaction_type);
            mTransactionIdView = (TextView) view.findViewById(R.id.transaction_id);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTransactionIdView.getText() + "'";
        }
    }
}
