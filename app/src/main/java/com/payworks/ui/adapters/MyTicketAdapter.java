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
import com.payworks.generated.model.Donation;
import com.payworks.generated.model.Ticket;
import com.payworks.ui.activities.MyDonationsActivity;
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
 * Created by Abhinandan on 26/12/17.
 */

public class MyTicketAdapter extends ArrayAdapter<Ticket> {


    int groupid;
    ArrayList<Ticket> myTicketList;
    Context context;
    String generatedCode;

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
        public ImageView editTicketBtn;
        public ImageView generateCodeBtn;





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
            viewHolder.editTicketBtn= (ImageView) rowView.findViewById(R.id.edit_product);
            viewHolder.generateCodeBtn= (ImageView) rowView.findViewById(R.id.generate_code);


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
            holder.generateCodeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {

                    String itemId = ticket.getId();
                    Log.e("abhi", "onClick: ...." +itemId );
                    String customerEmailId = PrefUtils.getEmail(getContext());
                    String action = "ticket";
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
                                        openDialog(generatedCode,view,ticket.getTicketname());
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


            holder.editTicketBtn.setOnClickListener(new View.OnClickListener() {
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

        nameTag.setText("Ticket Name: ");
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
                                intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Check out this donation link!");
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
