package com.payworks.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cooltechworks.creditcarddesign.CardEditActivity;
import com.cooltechworks.creditcarddesign.CreditCardUtils;
import com.cooltechworks.creditcarddesign.CreditCardView;
import com.payworks.R;
import com.payworks.api.ApiAdapter;
import com.payworks.api.ApiEndPoints;
import com.payworks.api.RetrofitInterface;
import com.payworks.generated.model.MyProfile;
import com.payworks.generated.model.MyWalletResponse;
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

public class AddMoneyActivity extends BaseActivity {



    private RetrofitInterface.UserWalletClient UserWalletAdapter;
    String walletBalance, addAmount,addCoupon;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_app_title)
    TextView tvAppTitle;

    @BindView(R.id.enter_amount)
    EditText etEnterAmount;

    @BindView(R.id.enter_coupon)
    EditText etEnterCoupon;

    @BindView(R.id.wallet_balance)
    TextView tvWalletBalance;


    @OnClick(R.id.credit_debit_card)
    public void openAddCardsDetailsActivity() {

        addAmount = etEnterAmount.getText().toString();
        addCoupon= etEnterCoupon.getText().toString();

        Intent activityChangeIntent = new Intent(this, AddCardDetailActivity.class);
        activityChangeIntent.putExtra("AMOUNT", addAmount);
        activityChangeIntent.putExtra("COUPON", addCoupon);
        startActivity(activityChangeIntent);
    }


    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_add_money;
    }

    @Override
    public int getNavigationIconId() {
        return R.drawable.ic_keyboard_arrow_left_white_24dp;
    }

    @Override
    public void onNavigationIconClick(View v) {

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
        tvAppTitle.setText(R.string.add_money_title);
        setUpRestAdapter();
        getWalletBalance();

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

    private void setUpRestAdapter() {
        UserWalletAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.UserWalletClient.class, ApiEndPoints.BASE_URL, this);
        // QueryNotificationAdapterForHome = ApiAdapter.createRestAdapter(RetrofitInterface.QueryNotificationClient.class, ApiEndPoints.BASE_URL, getActivity());
    }



}

