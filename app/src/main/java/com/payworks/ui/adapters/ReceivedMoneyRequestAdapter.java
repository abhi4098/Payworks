package com.payworks.ui.adapters;

/**
 * Created by Abhinandan on 21/9/17.
 */

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.payworks.R;
import com.payworks.generated.model.Donation;
import com.payworks.generated.model.Receivedrequest;
import com.payworks.ui.activities.MyDonationsActivity;
import com.payworks.ui.activities.RecievedMoneyRequestActivity;

import java.util.ArrayList;


public class ReceivedMoneyRequestAdapter extends ArrayAdapter<Receivedrequest> {

    int groupid;
    ArrayList<Receivedrequest> myrecReqList;
    Context context;
    String userPriority;


    public ReceivedMoneyRequestAdapter(RecievedMoneyRequestActivity recievedMoneyRequestActivity, int layout_received_money_request, int payee_name, ArrayList<Receivedrequest> myrecReqList) {
        super(recievedMoneyRequestActivity,layout_received_money_request,payee_name,myrecReqList);
        groupid=layout_received_money_request;
        this.context = recievedMoneyRequestActivity;
        this.myrecReqList = myrecReqList;

    }

    // Hold views of the ListView to improve its scrolling performance
    static class ViewHolder {
        public TextView payeeName;
        public TextView payableAmount;
        public TextView priority;
        public TextView dueDate;





    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        View rowView = convertView;
        Log.e("abhi", "getView:------------------------- " );

        if(rowView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView= inflater.inflate(groupid, parent, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.payeeName= (TextView) rowView.findViewById(R.id.payee_name);
            viewHolder.payableAmount= (TextView) rowView.findViewById(R.id.payable_amount);
            viewHolder.priority= (TextView) rowView.findViewById(R.id.user_priority);
            viewHolder.dueDate= (TextView) rowView.findViewById(R.id.user_due_date);


            rowView.setTag(viewHolder);

        }
        // Set text to each TextView of ListView item
        final Receivedrequest receivedrequest = getItem(position);
        final ViewHolder holder = (ViewHolder) rowView.getTag();

        if (receivedrequest !=null) {


            holder.payeeName.setText(receivedrequest.getFullname());
            holder.payableAmount.setText(receivedrequest.getAmount());

           if (receivedrequest.getPriority()!=null)

           {
               switch (receivedrequest.getPriority()) {
                   case "0":
                       userPriority = "Low";
                       break;
                   case "1":
                       userPriority = "Normal";
                       break;
                   default:
                       userPriority = "High";
                       break;
               }
               holder.priority.setText(userPriority);
           }


            if (receivedrequest.getDuedate() != null) {
                String date = receivedrequest.getDuedate();
                String[] splited = date.split("\\s+");
                String userDueDate = splited[0];
                holder.dueDate.setText(userDueDate);
            }



        }



        return rowView;
    }


}

