package com.payworks.ui.adapters;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.payworks.R;
import com.payworks.generated.model.Invoice;
import com.payworks.generated.model.Sentrequest;


import java.util.ArrayList;

import static android.graphics.Color.rgb;

/**
 * Created by Abhinandan on 26/12/17.
 */

public class SentMoneyRequestAdapter extends ArrayAdapter<Sentrequest> {

    int groupid;
    ArrayList<Sentrequest> sentMoneyList;
    FragmentActivity context;
    String priorityName,statusName;

    public SentMoneyRequestAdapter(FragmentActivity navigationalActivity, int layout_sent_money, int client_name, ArrayList<Sentrequest> sentMoneyList)
    {
        super(navigationalActivity,layout_sent_money,client_name,sentMoneyList);
        Log.e("abhi", "MyProductAdapter: " );
        groupid=layout_sent_money;
        this.context = navigationalActivity;
        this.sentMoneyList = sentMoneyList;

    }


    // Hold views of the ListView to improve its scrolling performance
    static class ViewHolder {
        public TextView clientName;
        public TextView requestedAmount;
        public TextView tranStatus;
        public TextView dueDate;
        /*public TextView firstName;
        public TextView lastName*/;
        public TextView priority;
        public TextView notes;







    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        View rowView = convertView;
        Log.e("abhi", "getView:------------------------- " );

        if(rowView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView= inflater.inflate(groupid, parent, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.clientName= (TextView) rowView.findViewById(R.id.client_name);
            viewHolder.requestedAmount= (TextView) rowView.findViewById(R.id.requested_amount);
            viewHolder.dueDate= (TextView) rowView.findViewById(R.id.update_date);
            viewHolder.priority= (TextView) rowView.findViewById(R.id.priority_num);
            viewHolder.tranStatus= (TextView) rowView.findViewById(R.id.tran_status);
            viewHolder.notes= (TextView) rowView.findViewById(R.id.notes);



            rowView.setTag(viewHolder);

        }
        // Set text to each TextView of ListView item
        final Sentrequest sentrequest = getItem(position);
        final ViewHolder holder = (ViewHolder) rowView.getTag();

        if (sentrequest !=null) {


            holder.clientName.setText(sentrequest.getFirstName().concat(" ").concat(sentrequest.getLastName()));
            holder.requestedAmount.setText(sentrequest.getAmount());
            holder.notes.setText(sentrequest.getNotes());

            if (sentrequest.getStatus().equals("1"))
            {
                statusName = "Completed";
                holder.tranStatus.setText(statusName);
                holder.tranStatus.setTextColor(rgb(50,205,50));
            }
            else
            {
                statusName = "Pending";
                holder.tranStatus.setText(statusName);
                holder.tranStatus.setTextColor(rgb(30,169,225));
            }



            holder.dueDate.setText(sentrequest.getDuedate());

            if (sentrequest.getPriority().equals("2"))
            {
                priorityName = "High";
            }
            else if (sentrequest.getPriority().equals("1"))
            {
                priorityName = "Normal";
            }
            else
            {
                priorityName = "Low";
            }

            holder.priority.setText(priorityName);




        }



        return rowView;
    }

}
