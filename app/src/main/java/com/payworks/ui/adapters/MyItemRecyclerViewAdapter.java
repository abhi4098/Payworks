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
        holder.mDateView.setText(mValues.get(position).getCreatedDate());
        holder.mTransactionIdView.setText(mValues.get(position).getTransactionId());
        holder.mTransactionTypeView.setText(mValues.get(position).getTransactionMode());
        holder.mTransactionCommentView.setText(mValues.get(position).getTransactionComment());

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
        public final TextView mTransactionIdView;
        public final TextView mTransactionCommentView;
        public final TextView mTransactionTypeView;
        public Usertransaction mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;

            mDateView = (TextView) view.findViewById(R.id.date);
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
