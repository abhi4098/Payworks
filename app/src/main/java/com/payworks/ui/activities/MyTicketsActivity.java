package com.payworks.ui.activities;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.payworks.R;
import com.payworks.api.ApiAdapter;
import com.payworks.api.RetrofitInterface;
import com.payworks.generated.model.Donation;
import com.payworks.generated.model.Invoice;
import com.payworks.generated.model.MerchantData;
import com.payworks.generated.model.MerchantDonationResponse;
import com.payworks.generated.model.MerchantTicketsResponse;
import com.payworks.generated.model.Ticket;
import com.payworks.ui.adapters.MyDonationsAdapter;
import com.payworks.ui.adapters.MyInvoicesAdapter;
import com.payworks.ui.adapters.MyTicketAdapter;
import com.payworks.utils.LoadingDialog;
import com.payworks.utils.NetworkUtils;
import com.payworks.utils.PrefUtils;
import com.payworks.utils.SnakBarUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.payworks.api.ApiEndPoints.BASE_URL;

public class MyTicketsActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tv_app_title)
    TextView tvAppTitle;

    @BindView(R.id.listview)
    ListView listview;

    @BindView(R.id.search_item)
    EditText etSearch;

    private RetrofitInterface.UserMyTicketsClient MyMerchantAdapter;
    MyTicketAdapter myTicketAdapter;
    ArrayList<Ticket> myTicketList = null;
    ArrayList<Ticket> searchMyList = null;

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_my_tickets;
    }

    @Override
    public int getNavigationIconId() {
        return R.drawable.ic_keyboard_arrow_left_white_24dp;
    }

    @Override
    public void onNavigationIconClick(View v) {
    onBackPressed();
    }

    @Override
    public String getActivityTitle() {
        return null;
    }

    @Override
    public boolean focusAtLaunch() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        tvAppTitle.setText(R.string.my_tickets);
        setUpRestAdapter();
        getMyTickets();
        setSearchFunctionality();
    }

    private void setSearchFunctionality() {

        etSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (myTicketList != null) {
                    //manageCategoriesAdapter.getFilter().filter(s.toString());
                    filterSearch(s.toString());
                }

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {
                if (myTicketList != null) {
                    // MyDonationsAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void filterSearch(String constraint) {
        constraint = constraint.toString().toLowerCase();
        searchMyList =new ArrayList<>();
        for (int i = 0; i < myTicketList.size(); i++) {
            String data = myTicketList.get(i).getTicketname();
            if (data.toLowerCase().startsWith(constraint.toString())) {
                Ticket ticket = new Ticket();
                ticket.setTicketname(myTicketList.get(i).getTicketname());
                ticket.setTicketprice(myTicketList.get(i).getTicketprice());
                ticket.setUpdatedDate(myTicketList.get(i).getUpdatedDate());
                ticket.setTickettax(myTicketList.get(i).getTickettax());
                ticket.setTicketavailable(myTicketList.get(i).getTicketavailable());
                ticket.setSold(myTicketList.get(i).getSold());


                searchMyList.add(ticket);

            }
        }


        myTicketAdapter = new MyTicketAdapter(this, R.layout.layout_my_tickets, R.id.ticket_name, searchMyList);
        listview.setAdapter(myTicketAdapter);
        LoadingDialog.cancelLoading();
        listview.setDivider(new ColorDrawable(getResources().getColor(R.color.lighter_gray)));
        listview.setDividerHeight(1);
        listview.setTextFilterEnabled(true);

    }

    private void getMyTickets() {
        LoadingDialog.showLoadingDialog(this,"Loading...");
        Call<MerchantTicketsResponse> call = MyMerchantAdapter.merchantsData(new MerchantData("usertickets", PrefUtils.getUserId(this),"83Ide@$321!"));
        if (NetworkUtils.isNetworkConnected(this)) {
            call.enqueue(new Callback<MerchantTicketsResponse>() {

                @Override
                public void onResponse(Call<MerchantTicketsResponse> call, Response<MerchantTicketsResponse> response) {

                    if (response.isSuccessful()) {
                        setTickets(response);
                        LoadingDialog.cancelLoading();

                        Log.e("abhi", "onResponse: donations--"+response.body().getTicket().size() );

                    }
                }

                @Override
                public void onFailure(Call<MerchantTicketsResponse> call, Throwable t) {
                    LoadingDialog.cancelLoading();
                }


            });

        } else {
            SnakBarUtils.networkConnected(this);
        }
    }

    private void setTickets(Response<MerchantTicketsResponse> response) {
        myTicketList = new ArrayList<>();
        for (int i = 0; i < response.body().getTicket().size(); i++) {
            Ticket ticket = new Ticket();


            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy");
            String inputDateStr=response.body().getTicket().get(i).getCreatedDate();
            Date date = null;
            try {
                date = inputFormat.parse(inputDateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String outputDateStr = outputFormat.format(date);

            ticket.setTicketname(response.body().getTicket().get(i).getTicketname());
            ticket.setTicketprice(response.body().getTicket().get(i).getTicketprice());
            ticket.setUpdatedDate(outputDateStr);
            ticket.setTickettax(response.body().getTicket().get(i).getTickettax());
            ticket.setTicketavailable(response.body().getTicket().get(i).getTicketavailable());
            ticket.setSold(response.body().getTicket().get(i).getSold());

            Log.e("abhi", "setUserProducts: =========" );

            myTicketList.add(ticket);
        }

        myTicketAdapter = new MyTicketAdapter(this, R.layout.layout_my_tickets, R.id.ticket_name, myTicketList);
        listview.setAdapter(myTicketAdapter);
        LoadingDialog.cancelLoading();
        listview.setDivider(new ColorDrawable(getResources().getColor(R.color.lighter_gray)));
        listview.setDividerHeight(1);
        listview.setTextFilterEnabled(true);


    }


    private void setUpRestAdapter() {
        MyMerchantAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.UserMyTicketsClient.class, BASE_URL, this);

    }


}
