package com.payworks.ui.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.payworks.R;
import com.payworks.api.ApiAdapter;
import com.payworks.api.ApiEndPoints;
import com.payworks.api.RetrofitInterface;
import com.payworks.generated.model.MyProfile;
import com.payworks.generated.model.MyProfileResponse;
import com.payworks.ui.activities.MyProfileActivity;
import com.payworks.utils.LoadingDialog;
import com.payworks.utils.NetworkUtils;
import com.payworks.utils.PrefUtils;
import com.payworks.utils.SnakBarUtils;


import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileHomePageFragment extends Fragment {
    private static final String TAG = "ProfileHomePageFragment";
    private RetrofitInterface.UserWalletClient UserWalletAdapter;


    @BindView(R.id.wallet_balance)
    TextView tvWalletBalance;




    public ProfileHomePageFragment() {
        // Required empty public constructor
    }


    @Override
    public void onResume() {
        super.onResume();
      /*  QUERY_NOTIFICATION_COUNT = 0;
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
        }*/
    }


    @Override
    public void onPause() {
        super.onPause();
       /* PrefUtils.setActiveConsultationScreenVisible(getActivity(), false);
        getActivity().unregisterReceiver(queryReceiver);
        getActivity().unregisterReceiver(chatMessageReceiver);
        getActivity().unregisterReceiver(networkReceiver);*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_home_page, container, false);
        Log.e(TAG, "onCreateView: " );
        ButterKnife.bind(this, view);
        setUpRestAdapter();
        getWalletBalance();
        return view;

    }

    private void getWalletBalance() {
        LoadingDialog.showLoadingDialog(getActivity(),"Loading...");
        Call<MyProfileResponse> call = UserWalletAdapter.userWallet(new MyProfile("walletbalance", PrefUtils.getUserId(getActivity()),"83Ide@$321!"));
        if (NetworkUtils.isNetworkConnected(getActivity())) {
            call.enqueue(new Callback<MyProfileResponse>() {

                @Override
                public void onResponse(Call<MyProfileResponse> call, Response<MyProfileResponse> response) {

                    if (response.isSuccessful()) {
                        Log.e(TAG, "onResponse: " +response.body() );
                        tvWalletBalance.setText(response.message());
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






}