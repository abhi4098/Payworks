package com.payworks.ui.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.payworks.R;
import com.payworks.generated.model.Subscription;
import com.payworks.generated.model.Ticket;
import com.payworks.ui.activities.MySubscriptionsActivity;
import com.payworks.ui.activities.MyTicketsActivity;

import java.util.ArrayList;

/**
 * Created by Abhinandan on 27/12/17.
 */

public class MySubscriptionAdapter extends ArrayAdapter<Subscription> {



    int groupid;
    ArrayList<Subscription> mySubscriptionList;
    Context context;

    public MySubscriptionAdapter(MySubscriptionsActivity mySubscriptionsActivity, int layout_my_subscriptions, int subs_name, ArrayList<Subscription> mySubscriptionList)
    {

        super(mySubscriptionsActivity,layout_my_subscriptions,subs_name,mySubscriptionList);
        groupid=layout_my_subscriptions;
        this.context = mySubscriptionsActivity;
        this.mySubscriptionList = mySubscriptionList;
    }


    // Hold views of the ListView to improve its scrolling performance
    static class ViewHolder {
        public TextView subsName;
        public TextView subsPrice;
        public TextView subsUpdateDate;
        public TextView subsPeriod;
        public TextView subsSold;
        public TextView subsTax;





    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        View rowView = convertView;
        Log.e("abhi", "getView:------------------------- " );

        if(rowView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView= inflater.inflate(groupid, parent, false);
            MySubscriptionAdapter.ViewHolder viewHolder = new MySubscriptionAdapter.ViewHolder();
            viewHolder.subsName= (TextView) rowView.findViewById(R.id.subs_name);
            viewHolder.subsPrice= (TextView) rowView.findViewById(R.id.subs_price);
            viewHolder.subsUpdateDate= (TextView) rowView.findViewById(R.id.update_date);
            viewHolder.subsPeriod= (TextView) rowView.findViewById(R.id.subs_Period);
            viewHolder.subsSold= (TextView) rowView.findViewById(R.id.subs_sold);
            viewHolder.subsTax= (TextView) rowView.findViewById(R.id.subs_tax);


            rowView.setTag(viewHolder);

        }
        // Set text to each TextView of ListView item
        final Subscription ticket = getItem(position);
        final MySubscriptionAdapter.ViewHolder holder = (MySubscriptionAdapter.ViewHolder) rowView.getTag();

        if (ticket !=null) {


            holder.subsName.setText(ticket.getSubscriptionname());
            holder.subsPrice.setText(ticket.getSubscriptionprice());
            holder.subsTax.setText(ticket.getSubscriptiontax());
            holder.subsSold.setText(ticket.getSold());
            holder.subsPeriod.setText(ticket.getSubscriptiontrialperiod());

            if (ticket.getUpdatedDate() != null) {
                String date = ticket.getUpdatedDate();
                String[] splited = date.split("\\s+");
                String subsDate = splited[0];
                holder.subsUpdateDate.setText(subsDate);
            }



        }



        return rowView;
    }

}
