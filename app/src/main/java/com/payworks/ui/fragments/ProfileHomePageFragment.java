package com.payworks.ui.fragments;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.payworks.api.ApiEndPoints.BASE_URL;


public class ProfileHomePageFragment extends Fragment {
    private static final String TAG = "ProfileHomePageFragment";
    private RetrofitInterface.UserWalletClient UserWalletAdapter;
    private RetrofitInterface.UserTransactionsClient MyTransactionAdapter;
    private RetrofitInterface.UserReceivedMoneyRequestClient UserReceivedMoneyRequestAdapter;
    Fragment fragment = null;


    @BindView(R.id.transactions)
    TextView tvTransaction;

    @BindView(R.id.received_request)
    TextView tvReceivedRequests;

    @OnClick(R.id.ll_merchants)
    public void payMoney() {

        //Toast.makeText(getApplicationContext(),"Feature will be implemented soon",Toast.LENGTH_SHORT).show();
        fragment = new MerchantFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        TextView tvAppTitle = (TextView) getActivity().findViewById(R.id.tv_app_title);
        ImageView ivBackIcon = (ImageView) getActivity().findViewById(R.id.back_icon);
        tvAppTitle.setText("MERCHANTS");
        ivBackIcon.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.ll_my_transactions)
    public void myTransactions() {

        //Toast.makeText(getApplicationContext(),"Feature will be implemented soon",Toast.LENGTH_SHORT).show();
        fragment = new MyTransactionsFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        TextView tvAppTitle = (TextView) getActivity().findViewById(R.id.tv_app_title);
        ImageView ivBackIcon = (ImageView) getActivity().findViewById(R.id.back_icon);
        tvAppTitle.setText("MY TRANSACTIONS");
        ivBackIcon.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.ll_withdraw_money)
    public void withdrawMoney() {

        //Toast.makeText(getApplicationContext(),"Feature will be implemented soon",Toast.LENGTH_SHORT).show();
        fragment = new WithdrawMoneyFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        TextView tvAppTitle = (TextView) getActivity().findViewById(R.id.tv_app_title);
        ImageView ivBackIcon = (ImageView) getActivity().findViewById(R.id.back_icon);
        tvAppTitle.setText("WITHDRAW MONEY");
        ivBackIcon.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.ll_request_money)
    public void requestMoney() {

        /*Intent activityChangeIntent = new Intent(getActivity(), RequestMoneyActivity.class);
        startActivity(activityChangeIntent);*/
        Toast.makeText(getActivity(),"its comming soon",Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.ll_add_money)
    public void addMoney() {

        Intent activityChangeIntent = new Intent(getActivity(), AddMoneyActivity.class);
        activityChangeIntent.putExtra("PATH", "homeFragment");
        startActivity(activityChangeIntent);
    }

    @OnClick(R.id.ll_send_money)
    public void sendMoney() {

       /* Intent activityChangeIntent = new Intent(getActivity(), RecievedMoneyRequestActivity.class);
        startActivity(activityChangeIntent);*/
       Toast.makeText(getActivity(),"its comming soon",Toast.LENGTH_SHORT).show();
    }

    public ProfileHomePageFragment() {
        // Required empty public constructor
    }



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