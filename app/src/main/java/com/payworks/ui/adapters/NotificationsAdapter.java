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
import com.payworks.generated.model.Activity;
import com.payworks.generated.model.Userbankaccount;

import java.util.ArrayList;

/**
 * Created by Abhinandan on 26/12/17.
 */

public class NotificationsAdapter extends ArrayAdapter<Activity> {

    int groupid;
    ArrayList<Activity> notificationList;
    FragmentActivity context;
    String priorityName,statusName;

    public NotificationsAdapter(FragmentActivity navigationalActivity, int layout_notifications, int message, ArrayList<Activity> notificationList)
    {
        super(navigationalActivity,layout_notifications,message,notificationList);
        Log.e("abhi", "MyProductAdapter: " );
        groupid=layout_notifications;
        this.context = navigationalActivity;
        this.notificationList = notificationList;

    }


    // Hold views of the ListView to improve its scrolling performance
    static class ViewHolder {
        public TextView message;
       /* public TextView accountNum;
        public TextView bankName;
        public TextView bankNum;
        public TextView accountType;
*/







    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        View rowView = convertView;
        Log.e("abhi", "getView:------------------------- " );

        if(rowView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView= inflater.inflate(groupid, parent, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.message= (TextView) rowView.findViewById(R.id.notification_message);
            /*viewHolder.accountNum= (TextView) rowView.findViewById(R.id.account_num);
            viewHolder.accountType= (TextView) rowView.findViewById(R.id.account_type);
            viewHolder.bankName= (TextView) rowView.findViewById(R.id.bank_name);
            viewHolder.bankNum= (TextView) rowView.findViewById(R.id.bank_num);
*/



            rowView.setTag(viewHolder);

        }
        // Set text to each TextView of ListView item
        final Activity activity = getItem(position);
        final ViewHolder holder = (ViewHolder) rowView.getTag();

        if (activity !=null) {
            holder.message.setText(activity.getMessage());
           /* holder.accountType.setText(userbankaccount.getAccounttype());
            holder.accountNum.setText(userbankaccount.getAccountnumber());
            holder.bankName.setText(userbankaccount.getBankname());
            holder.bankNum.setText(userbankaccount.getBankphone());*/





        }



        return rowView;
    }

}
