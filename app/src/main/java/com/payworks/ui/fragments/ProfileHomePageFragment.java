package com.payworks.ui.fragments;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.payworks.R;
import com.payworks.api.ApiAdapter;
import com.payworks.api.ApiEndPoints;
import com.payworks.api.RetrofitInterface;
import com.payworks.generated.model.MyTransactions;
import com.payworks.generated.model.MyTransactionsResponse;
import com.payworks.generated.model.ReceiveMoneyRequests;
import com.payworks.generated.model.ReceiveMoneyRequestsResponse;
import com.payworks.ui.activities.AddCardDetailActivity;
import com.payworks.ui.activities.AddMoneyActivity;
import com.payworks.ui.activities.MyDonationsActivity;
import com.payworks.ui.activities.RecievedMoneyRequestActivity;
import com.payworks.ui.activities.RequestMoneyActivity;
import com.payworks.utils.LoadingDialog;
import com.payworks.utils.NetworkUtils;
import com.payworks.utils.PrefUtils;
import com.payworks.utils.SnakBarUtils;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.payworks.api.ApiEndPoints.BASE_URL;


public class ProfileHomePageFragment extends Fragment {
    private static final String TAG = "ProfileHomePageFragment";
    private RetrofitInterface.UserWalletClient UserWalletAdapter;
    private RetrofitInterface.UserTransactionsClient MyTransactionAdapter;
    private RetrofitInterface.UserReceivedMoneyRequestClient UserReceivedMoneyRequestAdapter;


    @BindView(R.id.transactions)
    TextView tvTransaction;

    @BindView(R.id.received_request)
    TextView tvReceivedRequests;

    @OnClick(R.id.ll_request_money)
    public void requestMoney() {

        Intent activityChangeIntent = new Intent(getActivity(), RequestMoneyActivity.class);
        startActivity(activityChangeIntent);
    }

    @OnClick(R.id.ll_add_money)
    public void addMoney() {

        Intent activityChangeIntent = new Intent(getActivity(), AddMoneyActivity.class);
        activityChangeIntent.putExtra("PATH", "homeFragment");
        startActivity(activityChangeIntent);
    }

    @OnClick(R.id.ll_send_money)
    public void sendMoney() {

        Intent activityChangeIntent = new Intent(getActivity(), RecievedMoneyRequestActivity.class);
        startActivity(activityChangeIntent);
    }

    public ProfileHomePageFragment() {
        // Required empty public constructor
    }


   /* @Override
    public void onResume() {
        super.onResume();
      *//*  QUERY_NOTIFICATION_COUNT = 0;
        PrefUtils.setActiveConsultationScreenVisible(getActivity(), true);
        getActivity().registerReceiver(networkReceiver, new IntentFilter("internet_connectivity_check"));
        getActivity().registerReceiver(queryReceiver, new IntentFilter(DocFirebaseMessagingService.INTENT_FILTER));
        getActivity().registerReceiver(chatMessageReceiver, new IntentFilter(ChatBroadCastReceiver.CHAT_FILTER));
        if (NetworkUtils.isNetworkConnected(getActivity())) {
            getOpenQuerynumber();
        } else {
            setScreenFromDatabse();
            if (getActivity() != null)
                SnakBarUtils.networkConnected(getActivity());
        }*//*
    }
*/

    /*@Override
    public void onPause() {
        super.onPause();
       *//* PrefUtils.setActiveConsultationScreenVisible(getActivity(), false);
        getActivity().unregisterReceiver(queryReceiver);
        getActivity().unregisterReceiver(chatMessageReceiver);
        getActivity().unregisterReceiver(networkReceiver);*//*
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_home_page, container, false);
        ButterKnife.bind(this, view);
        setUpRestAdapter();
        getUserTransactions();
        getReceivedMoneyRequests();
        return view;

    }

/*
    private void getWalletBalance() {
        LoadingDialog.showLoadingDialog(getActivity(),"Loading...");
        Call<MyProfileResponse> call = UserWalletAdapter.userWallet(new MyProfile("walletbalance", PrefUtils.getUserId(getActivity()),"83Ide@$321!"));
        if (NetworkUtils.isNetworkConnected(getActivity())) {
            call.enqueue(new Callback<MyProfileResponse>() {

                @Override
                public void onResponse(Call<MyProfileResponse> call, Response<MyProfileResponse> response) {

                    if (response.isSuccessful()) {
                        Log.e(TAG, "onResponse: " +response.body() );
                        //tvWalletBalance.setText(response.body().toString());
                        LoadingDialog.cancelLoading();



                    }
                }

                @Override
                public void onFailure(Call<MyProfileResponse> call, Throwable t) {
                    Log.e(TAG, "onFailure: ------------" +t.toString());
                    LoadingDialog.cancelLoading();
                }


            });

        } else {
            SnakBarUtils.networkConnected(getActivity());
            LoadingDialog.cancelLoading();
        }
    }

    private void setUpRestAdapter() {
        UserWalletAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.UserWalletClient.class, ApiEndPoints.BASE_URL, getActivity());
       // QueryNotificationAdapterForHome = ApiAdapter.createRestAdapter(RetrofitInterface.QueryNotificationClient.class, ApiEndPoints.BASE_URL, getActivity());
    }
*/


    private void getUserTransactions() {
        LoadingDialog.showLoadingDialog(getActivity(),"Loading...");
        Call<MyTransactionsResponse> call = MyTransactionAdapter.userTransactions(new MyTransactions("usertransactions", PrefUtils.getUserId(getActivity()),"83Ide@$321!"));
        if (NetworkUtils.isNetworkConnected(getActivity())) {
            call.enqueue(new Callback<MyTransactionsResponse>() {

                @Override
                public void onResponse(Call<MyTransactionsResponse> call, Response<MyTransactionsResponse> response) {

                    if (response.isSuccessful()) {
                        Log.e(TAG, "onResponse: " +response.body().getUsertransactions().size() );
                        String transactions = String.valueOf(response.body().getUsertransactions().size());
                        tvTransaction.setText(transactions);
                        //setUserTransaction(response,view);
                        LoadingDialog.cancelLoading();



                    }
                }

                @Override
                public void onFailure(Call<MyTransactionsResponse> call, Throwable t) {
                    Log.e("abhi", "onFailure: my transactions------------" +t.toString());
                    LoadingDialog.cancelLoading();
                }


            });

        } else {
            SnakBarUtils.networkConnected(getActivity());
            LoadingDialog.cancelLoading();
        }
    }
    private void setUpRestAdapter() {
        MyTransactionAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.UserTransactionsClient.class, BASE_URL, getActivity());
        UserReceivedMoneyRequestAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.UserReceivedMoneyRequestClient.class, ApiEndPoints.BASE_URL, getActivity());
    }

    private void getReceivedMoneyRequests() {
        LoadingDialog.showLoadingDialog(getActivity(),"Loading...");
        Call<ReceiveMoneyRequestsResponse> call = UserReceivedMoneyRequestAdapter.receiveMoneyRequestData(new ReceiveMoneyRequests("usermoneyrequests", PrefUtils.getUserId(getActivity()),"83Ide@$321!"));
        if (NetworkUtils.isNetworkConnected(getActivity())) {
            call.enqueue(new Callback<ReceiveMoneyRequestsResponse>() {

                @Override
                public void onResponse(Call<ReceiveMoneyRequestsResponse> call, Response<ReceiveMoneyRequestsResponse> response) {

                    if (response.isSuccessful()) {
                          if (response.body().getMsg().equals("success"))
                          {
                            String receivedRequests = String.valueOf(response.body().getReceivedrequests().size());
                            tvReceivedRequests.setText(receivedRequests);
                        }
                        LoadingDialog.cancelLoading();



                    }
                }

                @Override
                public void onFailure(Call<ReceiveMoneyRequestsResponse> call, Throwable t) {
                    LoadingDialog.cancelLoading();
                }


            });

        } else {
            SnakBarUtils.networkConnected(getActivity());
            LoadingDialog.cancelLoading();
        }
    }

}