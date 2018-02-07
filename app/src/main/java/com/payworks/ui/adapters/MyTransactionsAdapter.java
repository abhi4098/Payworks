package com.payworks.ui.adapters;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.payworks.R;
import com.payworks.generated.model.Usertransaction;
import com.payworks.ui.fragments.MyTransactionsFragment;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.graphics.Color.rgb;

/**
 * Created by Abhinandan on 7/2/18.
 */

public class MyTransactionsAdapter extends ArrayAdapter<Usertransaction> {
    int groupid;
    Context context;
    ArrayList<Usertransaction> myTransactionList;
    String status;
    String mode;

    public MyTransactionsAdapter(FragmentActivity context, int vg, int id, ArrayList<Usertransaction> myTransactionList) {
        super(context,vg, id, myTransactionList);
        this.context=context;
        groupid=vg;
        this.myTransactionList=myTransactionList;
    }

    static class ViewHolder {
        public TextView mDateView;
        public TextView mTransactionName;
        public TextView mAmount;
        public TextView mTransactionIdView;
        public TextView mTransactionCommentView;
        public TextView mTransactionTypeView;
        public TextView mTransactionDollarSign;



        public Usertransaction mItem;




    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        View rowView = convertView;

        if(rowView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView= inflater.inflate(groupid, parent, false);
            ViewHolder viewHolder = new ViewHolder();

           viewHolder.mDateView = (TextView) rowView.findViewById(R.id.date);
            viewHolder.mAmount = (TextView) rowView.findViewById(R.id.content);
            viewHolder.mTransactionCommentView = (TextView) rowView.findViewById(R.id.transaction_comment);
            viewHolder.mTransactionTypeView = (TextView) rowView.findViewById(R.id.transaction_status);
            viewHolder.mTransactionIdView = (TextView) rowView.findViewById(R.id.transaction_id);
            viewHolder.mTransactionName = (TextView) rowView.findViewById(R.id.transactions_name);
            viewHolder.mTransactionDollarSign = (TextView) rowView.findViewById(R.id.dollar_sign);


            rowView.setTag(viewHolder);

        }
        // Set text to each TextView of ListView item
        final Usertransaction usertransaction = getItem(position);
        final ViewHolder holder = (ViewHolder) rowView.getTag();

        if (usertransaction !=null) {
            holder.mDateView.setText(usertransaction.getCreatedDate());
            holder.mTransactionIdView.setText(usertransaction.getTransactionId());
            holder.mAmount.setText(usertransaction.getTransactionAmount());


            if (usertransaction.getTransactionMode() !=null)

            {
                if (usertransaction.getTransactionMode().equals("1") ||usertransaction.getTransactionMode().equals("3") ||
                        usertransaction.getTransactionMode().equals("4")||usertransaction.getTransactionMode().equals("5")
                        ||usertransaction.getTransactionMode().equals("7") ) {
                    //mode = "Deposit";
                    holder.mAmount.setTextColor(rgb(50,205,50));
                    holder.mTransactionDollarSign.setTextColor(rgb(50,205,50));

                } else if (usertransaction.getTransactionMode().equals("2") ||usertransaction.getTransactionMode().equals("6")) {
                   // mode = "Withdraw";
                    holder.mAmount.setTextColor(rgb(255,0,0));
                    holder.mTransactionDollarSign.setTextColor(rgb(255,0,0));
                } else {
                    //mode = "Failed";
                    holder.mAmount.setTextColor(rgb(169,169,169));
                    holder.mTransactionDollarSign.setTextColor(rgb(169,169,169));
                }

            }


            if (usertransaction.getTransactionStatus() !=null) {
                if (usertransaction.getTransactionStatus().equals("1")) {
                    status = "Success";
                } else if (usertransaction.getTransactionStatus().equals("2")) {
                    status = "Pending";
                } else if (usertransaction.getTransactionStatus().equals("3")) {
                    status = "Pending";
                } else if (usertransaction.getTransactionStatus().equals("4")) {
                    status = "Cancelled";
                } else {
                    status = "Failed";
                }
                holder.mTransactionTypeView.setText(status);
            }


            holder.mTransactionName.setText(usertransaction.getFullname());
            holder.mTransactionCommentView.setText(usertransaction.getTransactionComment());

        }



        return rowView;
    }


}
