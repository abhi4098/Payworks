package com.payworks.ui.adapters;

/**
 * Created by Abhinandan on 21/9/17.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.payworks.R;
import com.payworks.generated.model.Donation;
import com.payworks.generated.model.Receivedrequest;
import com.payworks.ui.activities.AddMoneyActivity;
import com.payworks.ui.activities.MyDonationsActivity;
import com.payworks.ui.activities.OtpVerificationScreenActivity;
import com.payworks.ui.activities.RecievedMoneyRequestActivity;

import java.util.ArrayList;


public class ReceivedMoneyRequestAdapter extends ArrayAdapter<Receivedrequest> {

    int groupid;
    ArrayList<Receivedrequest> myrecReqList;
    Context context;
    String userPriority;
    String walletBalance;


    public ReceivedMoneyRequestAdapter(RecievedMoneyRequestActivity recievedMoneyRequestActivity, int layout_received_money_request, int payee_name, ArrayList<Receivedrequest> myrecReqList, String walletBalance) {
        super(recievedMoneyRequestActivity,layout_received_money_request,payee_name,myrecReqList);
        groupid=layout_received_money_request;
        this.context = recievedMoneyRequestActivity;
        this.myrecReqList = myrecReqList;
        this.walletBalance = walletBalance;
    }

    // Hold views of the ListView to improve its scrolling performance
    static class ViewHolder {
        public TextView payeeName;
        public TextView payableAmount;
        public TextView priority;
        public TextView dueDate;
        public Button btnPayNow;





    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        View rowView = convertView;
        Log.e("abhi", "getView:------------------------- " );

        if(rowView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView= inflater.inflate(groupid, parent, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.payeeName= (TextView) rowView.findViewById(R.id.payee_name);
            viewHolder.payableAmount= (TextView) rowView.findViewById(R.id.payable_amount);
            viewHolder.priority= (TextView) rowView.findViewById(R.id.user_priority);
            viewHolder.dueDate= (TextView) rowView.findViewById(R.id.user_due_date);
            viewHolder.btnPayNow= (Button) rowView.findViewById(R.id.pay_button);


            rowView.setTag(viewHolder);

        }
        // Set text to each TextView of ListView item
        final Receivedrequest receivedrequest = getItem(position);
        final ViewHolder holder = (ViewHolder) rowView.getTag();

        if (receivedrequest !=null) {


            holder.payeeName.setText(receivedrequest.getFullname());
            holder.payableAmount.setText(receivedrequest.getAmount());

           if (receivedrequest.getPriority()!=null)

           {
               switch (receivedrequest.getPriority()) {
                   case "0":
                       userPriority = "Low";
                       break;
                   case "1":
                       userPriority = "Normal";
                       break;
                   default:
                       userPriority = "High";
                       break;
               }
               holder.priority.setText(userPriority);
           }


            if (receivedrequest.getDuedate() != null) {
                String date = receivedrequest.getDuedate();
                String[] splited = date.split("\\s+");
                String userDueDate = splited[0];
                holder.dueDate.setText(userDueDate);
            }

            holder.btnPayNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    double payableAmount = Double.parseDouble(receivedrequest.getAmount());
                    double userWalletBalance = Double.parseDouble(walletBalance);
                    Log.e("abhi", "onClick: button "  +payableAmount + " wallet" + userWalletBalance);

                    if ( payableAmount > userWalletBalance)
                    {
                        View popupView = LayoutInflater.from(getContext()).inflate(R.layout.layout_popup, null);
                        final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
                        Button btnDismiss = (Button) popupView.findViewById(R.id.btn_close_popup);
                        Button btnAddMoney = (Button) popupView.findViewById(R.id.add_money_button);
                        btnDismiss.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupWindow.dismiss();
                            }
                        });
                        btnAddMoney.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent activityChangeIntent = new Intent(getContext(), AddMoneyActivity.class);
                                activityChangeIntent.putExtra("AMOUNT", receivedrequest.getAmount());
                                activityChangeIntent.putExtra("PATH", "receivedRequest");
                                getContext().startActivity(activityChangeIntent);
                                popupWindow.dismiss();
                            }
                        });
                        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

                        //popupWindow.showAsDropDown(popupView, 0, 0);
                    }
                    else
                    {

                        Log.e("abhi", "user pay id: =========" +receivedrequest.getId() );
                        Intent activityChangeIntent = new Intent(getContext(), OtpVerificationScreenActivity.class);
                        activityChangeIntent.putExtra("PAYID", receivedrequest.getId());

                        getContext().startActivity(activityChangeIntent);
                    }
                }
            });



        }



        return rowView;
    }



}

