package com.payworks.ui.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.payworks.R;
import com.payworks.generated.model.Activity;
import com.payworks.generated.model.Userbankaccount;
import com.payworks.utils.PrefUtils;

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

import static com.payworks.api.ApiEndPoints.BASE_URL_FOR_IMAGE;

/**
 * Created by Abhinandan on 26/12/17.
 */

public class NotificationsAdapter extends ArrayAdapter<Activity> {

    int groupid;
    ArrayList<Activity> notificationList;
    FragmentActivity context;
    String priorityName,statusName;
    private String profilePicUrl;
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
        public de.hdodenhof.circleimageview.CircleImageView notificationImage;







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
            viewHolder.notificationImage= (de.hdodenhof.circleimageview.CircleImageView) rowView.findViewById(R.id.person_image);



            rowView.setTag(viewHolder);

        }
        // Set text to each TextView of ListView item
        final Activity activity = getItem(position);
        final ViewHolder holder = (ViewHolder) rowView.getTag();

        if (activity !=null) {
            holder.message.setText(activity.getMessage());
            holder.notificationName.setText(activity.getFirstName().concat(" ").concat(activity.getLastName()));
            holder.notificationTime.setText(changeFormat(activity.getCreateDate()));
            if (activity.getProfilePic() != null) {
                profilePicUrl =activity.getProfilePic();
                String profilePictureUrlComplete = BASE_URL_FOR_IMAGE + profilePicUrl;
                //PrefUtils.storeUserImage(profilePictureUrlComplete,getContext());
                Log.e("abhi", "onResponse: image link............"+ profilePictureUrlComplete);
                setProfilePicURL(profilePictureUrlComplete,holder);
            }





        }



        return rowView;
    }

    private void setProfilePicURL(String profilepicUrlComplete, final ViewHolder holder) {
        Glide.with(getContext()).load(profilepicUrlComplete).asBitmap().centerCrop().dontAnimate().dontTransform().listener(new RequestListener<String, Bitmap>() {
            @Override
            public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                //imageProgressBar.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                //imageProgressBar.setVisibility(View.GONE);
                return false;
            }
        })
                .into(new BitmapImageViewTarget(holder.notificationImage) {
                    @Override
                    protected void setResource(Bitmap bitmap) {
                        Bitmap output;

                        if (bitmap.getWidth() > bitmap.getHeight()) {
                            output = Bitmap.createBitmap(bitmap.getHeight(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
                        } else {
                            output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getWidth(), Bitmap.Config.ARGB_8888);
                        }

                        Canvas canvas = new Canvas(output);

                        final int color = 0xff424242;
                        final Paint paint = new Paint();
                        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

                        float r = 0;

                        if (bitmap.getWidth() > bitmap.getHeight()) {
                            r = bitmap.getHeight() / 2;
                        } else {
                            r = bitmap.getWidth() / 2;
                        }

                        paint.setAntiAlias(true);
                        canvas.drawARGB(0, 0, 0, 0);
                        paint.setColor(color);
                        canvas.drawCircle(r, r, r, paint);
                        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                        canvas.drawBitmap(bitmap, rect, rect, paint);
                        holder.notificationImage.setImageBitmap(output);
                        //imageProgressBar.setVisibility(View.GONE);

                    }
                });
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
