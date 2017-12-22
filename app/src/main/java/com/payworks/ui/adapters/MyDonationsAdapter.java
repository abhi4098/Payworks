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
import com.payworks.generated.model.Product;
import com.payworks.ui.activities.MyDonationsActivity;
import com.payworks.ui.activities.MyProductActivity;

import java.util.ArrayList;


public class MyDonationsAdapter extends ArrayAdapter<Donation> {

    int groupid;
    ArrayList<Donation> myDonationList;
    Context context;


    public MyDonationsAdapter(MyDonationsActivity myDonationsActivity, int layout_my_donation_list, int date, ArrayList<Donation> myDonationList) {
        super(myDonationsActivity,layout_my_donation_list,date,myDonationList);
        Log.e("abhi", "MyProductAdapter: " );
        groupid=layout_my_donation_list;
        this.context = myDonationsActivity;
        this.myDonationList = myDonationList;

    }

    // Hold views of the ListView to improve its scrolling performance
    static class ViewHolder {
        public TextView donationName;
        public TextView donationPrice;
        public TextView donationUpdateDate;





    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        View rowView = convertView;
        Log.e("abhi", "getView:------------------------- " );

        if(rowView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView= inflater.inflate(groupid, parent, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.donationName= (TextView) rowView.findViewById(R.id.donation_name);
            viewHolder.donationPrice= (TextView) rowView.findViewById(R.id.donation_price);
            viewHolder.donationUpdateDate= (TextView) rowView.findViewById(R.id.donatio_update_date);


            rowView.setTag(viewHolder);

        }
        // Set text to each TextView of ListView item
        final Donation donation = getItem(position);
        final ViewHolder holder = (ViewHolder) rowView.getTag();

        if (donation !=null) {


            holder.donationName.setText(donation.getDonationname());
            holder.donationPrice.setText(donation.getDonationprice());
            holder.donationUpdateDate.setText(donation.getUpdatedDate());


        }



        return rowView;
    }


}

