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
import com.payworks.generated.model.Invoice;
import com.payworks.ui.activities.MyDonationsActivity;
import com.payworks.ui.activities.MyInvoicesActivity;

import java.util.ArrayList;

/**
 * Created by Abhinandan on 26/12/17.
 */

public class MyInvoicesAdapter extends ArrayAdapter<Invoice> {

    int groupid;
    ArrayList<Invoice> myInvoicesList;
    Context context;

    public MyInvoicesAdapter(MyInvoicesActivity myInvoicesActivity, int layout_my_invoices, int invoice_name, ArrayList<Invoice> myInvoicesList)
    {
        super(myInvoicesActivity,layout_my_invoices,invoice_name,myInvoicesList);
        Log.e("abhi", "MyProductAdapter: " );
        groupid=layout_my_invoices;
        this.context = myInvoicesActivity;
        this.myInvoicesList = myInvoicesList;

    }


    // Hold views of the ListView to improve its scrolling performance
    static class ViewHolder {
        public TextView invoiceName;
        public TextView invoicePrice;
        public TextView invoiceUpdateDate;
        public TextView invoiceNum;





    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        View rowView = convertView;
        Log.e("abhi", "getView:------------------------- " );

        if(rowView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView= inflater.inflate(groupid, parent, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.invoiceName= (TextView) rowView.findViewById(R.id.product_name);
            viewHolder.invoicePrice= (TextView) rowView.findViewById(R.id.product_price);
            viewHolder.invoiceUpdateDate= (TextView) rowView.findViewById(R.id.update_date);
            viewHolder.invoiceNum= (TextView) rowView.findViewById(R.id.invoice_num);


            rowView.setTag(viewHolder);

        }
        // Set text to each TextView of ListView item
        final Invoice invoice = getItem(position);
        final ViewHolder holder = (ViewHolder) rowView.getTag();

        if (invoice !=null) {


            holder.invoiceName.setText(invoice.getCustomerName());
            holder.invoiceNum.setText(invoice.getInvoicenumber());
            holder.invoicePrice.setText(invoice.getAmount());
            holder.invoiceUpdateDate.setText(invoice.getUpdatedDate());




        }



        return rowView;
    }

}
