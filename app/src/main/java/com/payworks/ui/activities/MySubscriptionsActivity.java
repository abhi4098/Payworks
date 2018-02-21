package com.payworks.ui.activities;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.payworks.R;
import com.payworks.api.ApiAdapter;
import com.payworks.api.RetrofitInterface;
import com.payworks.generated.model.Donation;
import com.payworks.generated.model.Invoice;
import com.payworks.generated.model.MerchantData;
import com.payworks.generated.model.MerchantDonationResponse;
import com.payworks.generated.model.MerchantSubscriptionsResponse;
import com.payworks.generated.model.Subscription;
import com.payworks.ui.adapters.MyDonationsAdapter;
import com.payworks.ui.adapters.MyInvoicesAdapter;
import com.payworks.ui.adapters.MySubscriptionAdapter;
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

public class MySubscriptionsActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tv_app_title)
    TextView tvAppTitle;

    @BindView(R.id.listview)
    ListView listview;

    @BindView(R.id.notification_icon)
    ImageView notificationIcon;

    @BindView(R.id.search_item)
    EditText etSearch;

    private RetrofitInterface.UserMySubscriptionClient MyMerchantAdapter;
    MySubscriptionAdapter mySubscriptionAdapter;
    ArrayList<Subscription>  mySubscriptionList = null;
    ArrayList<Subscription> searchMyList = null;

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_my_subscription;
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
        tvAppTitle.setText(R.string.my_subscriptions);
        notificationIcon.setVisibility(View.GONE);
        setUpRestAdapter();
        getMySubscriptions();
        setSearchFunctionality();
    }

    private void setSearchFunctionality() {

        etSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (mySubscriptionList != null) {
                    //manageCategoriesAdapter.getFilter().filter(s.toString());
                    filterSearch(s.toString());
                }

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {
                if (mySubscriptionList != null) {
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
        for (int i = 0; i < mySubscriptionList.size(); i++) {
            String data = mySubscriptionList.get(i).getSubscriptionname();
            if (data.toLowerCase().startsWith(constraint.toString())) {
                Subscription subscription = new Subscription();
                subscription.setSubscriptionname(mySubscriptionList.get(i).getSubscriptionname());
                subscription.setSubscriptionprice(mySubscriptionList.get(i).getSubscriptionprice());
                subscription.setUpdatedDate(mySubscriptionList.get(i).getUpdatedDate());
                subscription.setSubscriptiontrialperiod(mySubscriptionList.get(i).getSubscriptiontrialperiod());
                subscription.setSold(mySubscriptionList.get(i).getSold());
                subscription.setSubscriptiontax(mySubscriptionList.get(i).getSubscriptiontax());


                searchMyList.add(subscription);

            }
        }


        mySubscriptionAdapter = new MySubscriptionAdapter(this, R.layout.layout_my_subscriptions, R.id.subs_name, searchMyList);
        listview.setAdapter(mySubscriptionAdapter);
        LoadingDialog.cancelLoading();
        listview.setDivider(new ColorDrawable(getResources().getColor(R.color.lighter_gray)));
        listview.setDividerHeight(1);
        listview.setTextFilterEnabled(true);

    }

    private void getMySubscriptions() {
        LoadingDialog.showLoadingDialog(this,"Loading...");
        Call<MerchantSubscriptionsResponse> call = MyMerchantAdapter.merchantsData(new MerchantData("usersubscriptions", PrefUtils.getUserId(this),"83Ide@$321!"));
        if (NetworkUtils.isNetworkConnected(this)) {
            call.enqueue(new Callback<MerchantSubscriptionsResponse>() {

                @Override
                public void onResponse(Call<MerchantSubscriptionsResponse> call, Response<MerchantSubscriptionsResponse> response) {

                    if (response.isSuccessful()) {
                        setSubscriptions(response);
                        LoadingDialog.cancelLoading();

                        Log.e("abhi", "onResponse: donations--"+response.body().getSubscription().size() );

                    }
                }

                @Override
                public void onFailure(Call<MerchantSubscriptionsResponse> call, Throwable t) {
                    LoadingDialog.cancelLoading();
                }


            });

        } else {
            SnakBarUtils.networkConnected(this);
        }
    }

    private void setSubscriptions(Response<MerchantSubscriptionsResponse> response) {
        mySubscriptionList = new ArrayList<>();
        for (int i = 0; i < response.body().getSubscription().size(); i++) {
            Subscription subscription = new Subscription();


            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy");
            String inputDateStr=response.body().getSubscription().get(i).getCreatedDate();
            Date date = null;
            try {
                date = inputFormat.parse(inputDateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String outputDateStr = outputFormat.format(date);

            subscription.setSubscriptionname(response.body().getSubscription().get(i).getSubscriptionname());
            subscription.setSubscriptionprice(response.body().getSubscription().get(i).getSubscriptionprice());
            subscription.setUpdatedDate(outputDateStr);
            subscription.setSubscriptiontrialperiod(response.body().getSubscription().get(i).getSubscriptiontrialperiod());
            subscription.setSold(response.body().getSubscription().get(i).getSold());
            subscription.setSubscriptiontax(response.body().getSubscription().get(i).getSubscriptiontax());
            subscription.setId(response.body().getSubscription().get(i).getId());
            subscription.setPaidby(response.body().getSubscription().get(i).getPaidby());
            subscription.setSubscriptionbutton(response.body().getSubscription().get(i).getSubscriptionbutton());


            Log.e("abhi", "setUserProducts: =========" );

            mySubscriptionList.add(subscription);
        }

        mySubscriptionAdapter = new MySubscriptionAdapter(this, R.layout.layout_my_subscriptions, R.id.subs_name, mySubscriptionList);
        listview.setAdapter(mySubscriptionAdapter);
        LoadingDialog.cancelLoading();
        listview.setDivider(new ColorDrawable(getResources().getColor(R.color.lighter_gray)));
        listview.setDividerHeight(1);
        listview.setTextFilterEnabled(true);


    }


    private void setUpRestAdapter() {
        MyMerchantAdapter = ApiAdapter.createRestAdapter(RetrofitInterface.UserMySubscriptionClient.class, BASE_URL, this);

    }

}
