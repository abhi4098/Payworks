package com.payworks.ui.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.payworks.R;
import com.payworks.generated.model.Donation;
import com.payworks.generated.model.Ticket;
import com.payworks.ui.activities.MyDonationsActivity;
import com.payworks.ui.activities.MyTicketsActivity;

import java.util.ArrayList;

/**
 * Created by Abhinandan on 26/12/17.
 */

public class MyTicketAdapter extends ArrayAdapter<Ticket> {


    int groupid;
    ArrayList<Ticket> myTicketList;
    Context context;

    public MyTicketAdapter(MyTicketsActivity myTicketsActivity, int layout_my_tickets, int ticket_name, ArrayList<Ticket> myTicketList) {

        super(myTicketsActivity,layout_my_tickets,ticket_name,myTicketList);
        Log.e("abhi", "MyProductAdapter: " );
        groupid=layout_my_tickets;
        this.context = myTicketsActivity;
        this.myTicketList = myTicketList;
    }



    // Hold views of the ListView to improve its scrolling performance
    static class ViewHolder {
        public TextView ticketName;
        public TextView ticketPrice;
        public TextView ticketUpdateDate;
        public TextView ticketAvail;
        public TextView ticketSold;
        public TextView ticketTax;





    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        View rowView = convertView;
        Log.e("abhi", "getView:------------------------- " );

        if(rowView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView= inflater.inflate(groupid, parent, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.ticketName= (TextView) rowView.findViewById(R.id.ticket_name);
            viewHolder.ticketPrice= (TextView) rowView.findViewById(R.id.ticket_price);
            viewHolder.ticketUpdateDate= (TextView) rowView.findViewById(R.id.update_date);
            viewHolder.ticketAvail= (TextView) rowView.findViewById(R.id.ticket_avail);
            viewHolder.ticketSold= (TextView) rowView.findViewById(R.id.ticket_sold);
            viewHolder.ticketTax= (TextView) rowView.findViewById(R.id.ticket_tax);


            rowView.setTag(viewHolder);

        }
        // Set text to each TextView of ListView item
        final Ticket ticket = getItem(position);
        final ViewHolder holder = (ViewHolder) rowView.getTag();

        if (ticket !=null) {


            holder.ticketName.setText(ticket.getTicketname());
            holder.ticketPrice.setText(ticket.getTicketprice());
            holder.ticketTax.setText(ticket.getTickettax());
            holder.ticketSold.setText(ticket.getSold());
            holder.ticketAvail.setText(ticket.getTicketavailable());
            holder.ticketUpdateDate.setText(ticket.getUpdatedDate());




        }



        return rowView;
    }

}
