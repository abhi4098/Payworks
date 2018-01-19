package com.payworks.ui.activities;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.payworks.R;
import com.payworks.api.ApiAdapter;
import com.payworks.api.ApiEndPoints;
import com.payworks.api.RetrofitInterface;
import com.payworks.generated.model.Donation;
import com.payworks.generated.model.MyProfile;
import com.payworks.generated.model.MyWalletResponse;
import com.payworks.generated.model.ReceiveMoneyRequests;
import com.payworks.generated.model.ReceiveMoneyRequestsResponse;
import com.payworks.generated.model.Receivedrequest;
import com.payworks.ui.adapters.MyDonationsAdapter;
import com.payworks.ui.adapters.ReceivedMoneyRequestAdapter;
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
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecievedMoneyRequestActivity extends BaseActivity {



    private RetrofitInterface.UserReceivedMoneyRequestClient UserReceivedMoneyRequestAdapter;
    private RetrofitInterface.UserWalletClient UserWalletAdapter;
    String walletBalance;
    //String walletBalance, addAmount,addCoupon;
    ReceivedMoneyRequestAdapter receivedMoneyRequestAdapter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_app_title)
    TextView tvAppTitle;
    @BindView(R.id.listview)
    ListView listview;

    @BindView(R.id.wallet_balance)
    TextView tvWalletBalance;

    @BindView(R.id.notification_icon)
    ImageView notificationIcon;
    ArrayList<Receivedrequest> myReceivedRequestList = null;

   /* @BindView(R.id.enter_amount)
    EditText etEnterAmount;

    @BindView(R.id.enter_coupon)
    EditText etEnterCoupon;

    @BindView(R.id.wallet_balance)
    TextView tvWalletBalance;*/



    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_recieved_money_request;
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
        tvAppTitle.setText(R.string.received_money_request_title);
        notificationIcon.setVisibility(View.GONE);
        setUpRestAdapter();
        getWalletBalance();
        getReceivedMoneyRequests();

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpRestAdapter();
        getWalletBalance();
        getReceivedMoneyRequests();
    }

    private void getWalletBalance() {
        LoadingDialog.showLoadingDialog(this,"Loading...");
        Call<MyWalletResponse> call = UserWalletAdapter.userWallet(new MyProfile("walletbalance", PrefUtils.getUserId(this),"83Ide@$321!"));
        if (NetworkUtils.isNetworkConnected(this)) {
            call.enqueue(new Callback<MyWalletResponse>() {

                @Override
                public void onResponse(Call<MyWalletResponse> call, Response<MyWalletResponse> response) {

                    if (response.isSuccessful()) {
                        walletBalance = response.body().getWalletbalance();
                        tvWalletBalance.setText(walletBalance);

                        LoadingDialog.cancelLoading();



                    }
                }

                @Override
                public void onFailure(Call<MyWalletResponse> call, Throwable t) {
                    LoadingDialog.cancelLoading();
                }


            });

        } else {
            SnakBarUtils.networkConnected(this);
            LoadingDialog.cancelLoading();
        }
    }


    private void getReceivedMoneyRequests() {
        LoadingDialog.showLoadingDialog(this,"Loading...");
        Call<ReceiveMoneyRequestsResponse> call = UserReceivedMoneyRequestAdapter.receiveMoneyRequestData(new ReceiveMoneyRequests("usermoneyrequests", PrefUtils.getUserId(this),"83Ide@$321!"));
        if (NetworkUtils.isNetworkConnected(this)) {
            call.enqueue(new Callback<ReceiveMoneyRequestsResponse>() {

                @Override
                public void onResponse(Call<ReceiveMoneyRequestsResponse> call, Response<ReceiveMoneyRequestsResponse> response) {

                    if (response.isSuccessful()) {

                        Log.e("abhi", "onResponse: recieved request" );
                        setReceivedMoneyRequests(response);
                        LoadingDialog.cancelLoading();



                    }
                }

                @Override
                public void onFailure(Call<ReceiveMoneyRequestsResponse> call, Throwable t) {
                    LoadingDialog.cancelLoading();
                }


            });

        } else {
            SnakBarUtils.networkConnected(this);
            LoadingDialog.cancelLoading();
        }
    }

    private void setReceivedMoneyRequests(Response<ReceiveMoneyRequestsResponse> response) {

        myReceivedRequestList = new ArrayList<>();
        for (int i = 0; i < response.body().getReceivedrequests().size(); i++) {
            Receivedrequest receivedrequest = new Receivedrequest();

            receivedrequest.setAmount(response.body().getReceivedrequests().get(i).getAmount());

            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy");
            String inputDateStr=response.body().getReceivedrequests().get(i).getDuedate();
            Date date = null;
            try {
                date = inputFormat.parse(inputDateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String outputDateStr = outputFormat.format(date);
            Log.e("abhi", "setReceivedMoneyRequests: due date........."+response.body().getReceivedrequests().get(i).getDuedate() + " " +outputDateStr );
            receivedrequest.setDuedate(outputDateStr);
            receivedrequest.setFullname(response.body().getReceivedrequests().get(i).getFullname());
            receivedrequest.setPriority(response.body().getReceivedrequests().get(i).getPriority());
            receivedrequest.setId(response.body().getReceivedrequests().get(i).getId());
            receivedrequest.setStatus(response.body().getReceivedrequests().get(i).getStatus());



            myReceivedRequestList.add(receivedrequest);
        }

        receivedMoneyRequestAdapter = new ReceivedMoneyRequestAdapter(this, R.layout.layout_received_money_request, R.id.payee_name, myReceivedRequestList,walletBalance);
        listview.setAdapter(receivedMoneyRequestAdapter);
        LoadingDialog.cancelLoading();
        listview.setDivider(new ColorDrawable(getResources().getColor(R.color.lighter_gray)));
        listview.setDividerHeight(1);
        listview.setTextFilterEnabled(true);


    }

    private void setUpRestAdapter() {
        UserWalletAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.UserWalletClient.class, ApiEndPoints.BASE_URL, this);
        UserReceivedMoneyRequestAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.UserReceivedMoneyRequestClient.class, ApiEndPoints.BASE_URL, this);

    }



}

