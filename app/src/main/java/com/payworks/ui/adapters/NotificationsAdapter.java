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

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

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
        public TextView notificationName;
        public TextView notificationTime;
        /*public TextView accountType;*/







    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        View rowView = convertView;
        Log.e("abhi", "getView:------------------------- " );

        if(rowView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView= inflater.inflate(groupid, parent, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.message= (TextView) rowView.findViewById(R.id.notification_message);
            viewHolder.notificationName= (TextView) rowView.findViewById(R.id.notification_name);
            viewHolder.notificationTime= (TextView) rowView.findViewById(R.id.notification_time);
           // viewHolder.bankNum= (TextView) rowView.findViewById(R.id.bank_num);



            rowView.setTag(viewHolder);

        }
        // Set text to each TextView of ListView item
        final Activity activity = getItem(position);
        final ViewHolder holder = (ViewHolder) rowView.getTag();

        if (activity !=null) {
            holder.message.setText(activity.getMessage());
            holder.notificationName.setText(activity.getFirstName().concat(" ").concat(activity.getLastName()));
            holder.notificationTime.setText(changeFormat(activity.getCreateDate()));
/*
            holder.bankName.setText(userbankaccount.getBankname());
            holder.bankNum.setText(userbankaccount.getBankphone());
*/





        }



        return rowView;
    }

    private String changeFormat(String updatedAt) {

        if (updatedAt != null) {

            String DATE_PATTERN_SERVICE = "yyyy-MM-dd HH:mm:ss";

            DateTimeFormatter utcFormatter = DateTimeFormat
                    .forPattern(DATE_PATTERN_SERVICE)
                    .withLocale(Locale.US)
                    .withZoneUTC();
            DateTimeZone indianZone = DateTimeZone.forID("Asia/Kolkata");
            DateTimeFormatter indianZoneFormatter = utcFormatter.withZone(indianZone);

            String utcText = updatedAt;
            DateTime parsed = utcFormatter.parseDateTime(utcText);
            String indianText = indianZoneFormatter.print(parsed);

            DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


            if (dayIsYesterday(indianText)) {
                return "Yesterday";
            } else if (dateIsToday(indianText)) {
                Date newDate = null;
                try {
                    newDate = originalFormat.parse(indianText);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                SimpleDateFormat format1 = new SimpleDateFormat("hh:mm a");
                String date = format1.format(newDate);
                String str = date.replace("AM", "am").replace("PM", "pm");
                return str;
            } else {
                Date newDate = null;
                try {
                    newDate = originalFormat.parse(indianText);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                SimpleDateFormat format1 = new SimpleDateFormat("dd MMM yy");
                String date = format1.format(newDate);
                return date;
            }

        } else
            return null;
    }

    private boolean dateIsToday(String indianText) {

        DateTime yesterday = new DateTime();
        String dateYesterday = yesterday.toString();
        String dateOnlyYesterday = dateYesterday.substring(0, dateYesterday.indexOf("T"));
        String dateOnlyInputText = indianText.substring(0, dateYesterday.indexOf("T"));
        return dateOnlyYesterday.equals(dateOnlyInputText);
    }

    public static boolean dayIsYesterday(String indianText) {
        DateTime yesterday = new DateTime().minusDays(1);
        String dateYesterday = yesterday.toString();
        String dateOnlyYesterday = dateYesterday.substring(0, dateYesterday.indexOf("T"));
        String dateOnlyInputText = indianText.substring(0, dateYesterday.indexOf("T"));
        return dateOnlyYesterday.equals(dateOnlyInputText);

    }


}
