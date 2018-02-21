package com.payworks.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.payworks.R;
import com.payworks.generated.model.Subscription;
import com.payworks.generated.model.Ticket;
import com.payworks.ui.activities.MySubscriptionsActivity;
import com.payworks.ui.activities.MyTicketsActivity;
import com.payworks.utils.PrefUtils;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.google.android.gms.internal.zzahn.runOnUiThread;

/**
 * Created by Abhinandan on 27/12/17.
 */

public class MySubscriptionAdapter extends ArrayAdapter<Subscription> {



    int groupid;
    ArrayList<Subscription> mySubscriptionList;
    Context context;
    String generatedCode ;

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
        public ImageView editSubscriptionBtn;
        public ImageView generateCodeBtn;





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
            viewHolder.editSubscriptionBtn= (ImageView) rowView.findViewById(R.id.edit_product);
            viewHolder.generateCodeBtn= (ImageView) rowView.findViewById(R.id.generate_code);



            rowView.setTag(viewHolder);

        }
        // Set text to each TextView of ListView item
        final Subscription subscription = getItem(position);
        final MySubscriptionAdapter.ViewHolder holder = (MySubscriptionAdapter.ViewHolder) rowView.getTag();

        if (subscription !=null) {


            holder.subsName.setText(subscription.getSubscriptionname());
            holder.subsPrice.setText(subscription.getSubscriptionprice());
            holder.subsTax.setText(subscription.getSubscriptiontax());
            holder.subsSold.setText(subscription.getSold());
            holder.subsPeriod.setText(subscription.getSubscriptiontrialperiod());
            holder.subsUpdateDate.setText(subscription.getUpdatedDate());
            holder.generateCodeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {

                    String itemId = subscription.getId();
                    Log.e("abhi", "onClick: ...." +itemId );
                    String customerEmailId = PrefUtils.getEmail(getContext());
                    String action = "subscription";
                    String url1 = "https://www.payworks.bs/processor.html?product=";
                    String url2 = "&member=";
                    String url3 = "&action=";
                    String url4 = "&send=yes";
                    String baseUrl = url1.concat(itemId).concat(url2).concat(customerEmailId).concat(url3).concat(action).concat(url4);
                    OkHttpClient client = new OkHttpClient();
                    String urlTemplate = "http://tinyurl.com/api-create.php?url=%s";
                    String uri = String.format(urlTemplate, URLEncoder.encode(baseUrl));
                    Log.e("abhi", "onClick: " +uri );
                    Request request = new Request.Builder()
                            .url(uri)
                            .build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.e("abhi", "onFailure: " +e.getMessage() );
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            if (response.isSuccessful()) {
                                generatedCode = response.body().string();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        openDialog(generatedCode,view,subscription.getSubscriptionname());
                                    }
                                });


                            }
                            else
                            {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getContext(),"Error encountered",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                        }
                    });



                }
            });


            holder.editSubscriptionBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                   /* Intent i = new Intent(getContext(), AddProductActivity.class);
                    i.putExtra("PRODUCT_NAME",product.getProductname());
                    i.putExtra("PRODUCT_PRICE",product.getProductprice());
                    i.putExtra("PRODUCT_SHIPPING",product.getProductshipping());
                    i.putExtra("PRODUCT_DESCRIPTION",product.getProductdescription());
                    i.putExtra("PRODUCT_BUTTON",product.getProductbutton());
                    i.putExtra("PRODUCT_ID",product.getId());
                    Log.e("abhi", "onClick: product_id" +product.getId());
                    i.putExtra("PRODUCT_FEE",product.getPaidby());
                    Log.e("abhi", "onClick: fee" +product.getPaidby());
                    i.putExtra("INTENT_FROM","EditButton");

                    getContext().startActivity(i);
*/
                }
            });




        }



        return rowView;
    }
    private void openDialog(final String generatedCode, View view, String name) {
        Log.e("abhi", "openDialog:............... "+generatedCode );
        LayoutInflater li = LayoutInflater.from((Activity) view.getContext());
        View promptsView = li.inflate(R.layout.prompts, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                (Activity) view.getContext());

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText ettinyUrl = (EditText) promptsView
                .findViewById(R.id.editTextDialogUserInput);

        final TextView entitiyName = (TextView) promptsView
                .findViewById(R.id.tv_Name);

        final TextView nameTag = (TextView) promptsView
                .findViewById(R.id.name_tag);

        nameTag.setText("Subscription Name: ");
        entitiyName.setText(name);
        ettinyUrl.setText(generatedCode);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Share",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                Intent intent = new Intent(Intent.ACTION_SEND);
                                intent.setType("text/plain");
                                intent.putExtra(Intent.EXTRA_TEXT, generatedCode);
                                intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Check out this subscription link!");
                                getContext().startActivity(Intent.createChooser(intent, "Share"));
                                dialog.cancel();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#1ea9e1"));
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#1ea9e1"));
    }
}
