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
import com.payworks.generated.model.Sentrequest;
import com.payworks.generated.model.Userbankaccount;

import java.util.ArrayList;

import static android.graphics.Color.rgb;

/**
 * Created by Abhinandan on 26/12/17.
 */

public class BankAccountDetailsAdapter extends ArrayAdapter<Userbankaccount> {

    int groupid;
    ArrayList<Userbankaccount> bankDetailsList;
    FragmentActivity context;
    String priorityName,statusName;

    public BankAccountDetailsAdapter(FragmentActivity navigationalActivity, int layout_bank_details, int account_holder_name, ArrayList<Userbankaccount> bankDetailsList)
    {
        super(navigationalActivity,layout_bank_details,account_holder_name,bankDetailsList);
        Log.e("abhi", "MyProductAdapter: " );
        groupid=layout_bank_details;
        this.context = navigationalActivity;
        this.bankDetailsList = bankDetailsList;

    }


    // Hold views of the ListView to improve its scrolling performance
    static class ViewHolder {
        public TextView accountHolderName;
        public TextView accountNum;
        public TextView bankName;
        public TextView bankNum;
        public TextView accountType;








    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        View rowView = convertView;
        Log.e("abhi", "getView:------------------------- " );

        if(rowView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView= inflater.inflate(groupid, parent, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.accountHolderName= (TextView) rowView.findViewById(R.id.account_holder_name);
            viewHolder.accountNum= (TextView) rowView.findViewById(R.id.account_num);
            viewHolder.accountType= (TextView) rowView.findViewById(R.id.account_type);
            viewHolder.bankName= (TextView) rowView.findViewById(R.id.bank_name);
            viewHolder.bankNum= (TextView) rowView.findViewById(R.id.bank_num);




            rowView.setTag(viewHolder);

        }
        // Set text to each TextView of ListView item
        final Userbankaccount userbankaccount = getItem(position);
        final ViewHolder holder = (ViewHolder) rowView.getTag();

        if (userbankaccount !=null) {
            holder.accountHolderName.setText(userbankaccount.getAccountholder());
            holder.accountType.setText(userbankaccount.getAccounttype());
            holder.accountNum.setText(userbankaccount.getAccountnumber());
            holder.bankName.setText(userbankaccount.getBankname());
            holder.bankNum.setText(userbankaccount.getBankphone());





        }



        return rowView;
    }

}
