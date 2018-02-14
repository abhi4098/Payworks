package com.payworks.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.payworks.R;
import com.payworks.api.RetrofitInterface;
import com.payworks.ui.activities.MyDonationsActivity;
import com.payworks.ui.activities.MyInvoicesActivity;
import com.payworks.ui.activities.MyProductActivity;
import com.payworks.ui.activities.MySubscriptionsActivity;
import com.payworks.ui.activities.MyTicketsActivity;
import com.payworks.utils.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Abhinandan on 18/8/17.
 */
public class WithdrawMoneyFragment extends Fragment {

    private static final String TAG = LogUtils.makeLogTag(WithdrawMoneyFragment.class);
   //private RetrofitInterface.UserMyProfileClient MyProfileAdapter;



    @OnClick(R.id.direct_deposit)
    public void directDeposit() {
        Intent activityChangeIntent = new Intent(getActivity(), MyProductActivity.class);
        startActivity(activityChangeIntent);
    }



    public WithdrawMoneyFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_withdraw_money, container, false);
        ButterKnife.bind(this,rootView);
       // setUpRestAdapter();
       // getMyProfileDetails();
        return rootView;
    }



}
