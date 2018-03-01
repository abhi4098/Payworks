package com.payworks.ui.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.payworks.R;
import com.payworks.generated.model.Bill;
import com.payworks.generated.model.Userbankaccount;

import java.util.ArrayList;

/**
 * Created by Abhinandan on 26/12/17.
 */

public class MyBillsShowAdapter extends ArrayAdapter<Bill> {

    int groupid;
    ArrayList<Bill> billdetailsList;
    FragmentActivity context;
    String priorityName,statusName;

    public MyBillsShowAdapter(FragmentActivity navigationalActivity, int layout_bank_details, int account_holder_name, ArrayList<Bill> billdetailsList)
    {
        super(navigationalActivity,layout_bank_details,account_holder_name,billdetailsList);
        Log.e("abhi", "MyProductAdapter: " );
        groupid=layout_bank_details;
        this.context = navigationalActivity;
        this.billdetailsList = billdetailsList;

    }


    // Hold views of the ListView to improve its scrolling performance
    static class ViewHolder {
        public TextView consumerName;
        public TextView consumerType;
        public TextView consumerAmount;
        public TextView consumerId;









    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        View rowView = convertView;
        Log.e("abhi", "getView:------------------------- " );

        if(rowView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView= inflater.inflate(groupid, parent, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.consumerName= (TextView) rowView.findViewById(R.id.consumer_name);
            viewHolder.consumerId= (TextView) rowView.findViewById(R.id.consumer_id);
            viewHolder.consumerAmount= (TextView) rowView.findViewById(R.id.content);
            viewHolder.consumerType= (TextView) rowView.findViewById(R.id.bill_type);





            rowView.setTag(viewHolder);

        }
        // Set text to each TextView of ListView item
        final Bill bill = getItem(position);
        final ViewHolder holder = (ViewHolder) rowView.getTag();

        if (bill !=null) {
            holder.consumerName.setText(bill.getConsumername());
            holder.consumerId.setText(bill.getConsumerid());
            holder.consumerType.setText(bill.getBilltemplatename());
            holder.consumerAmount.setText(bill.getBillamount());






        }



        return rowView;
    }

}
